package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

/**
 * Tests de Tetris forzando la generación de PieceL mediante Random inyectado.
 * Requiere en Tetris.java el constructor:
 *   public Tetris(Board board, Reloj reloj, Random rnd) { this.juego = new Juego(board, reloj, rnd); }
 */
public class TetrisPieceLTest {

    /** Random determinista: devuelve, en orden, los valores encolados (acotados por bound). */
    static class FakeRandom extends Random {
        private final Deque<Integer> q = new ArrayDeque<>();
        FakeRandom enqueue(int... values) {
            for (int v : values) q.addLast(v);
            return this;
        }
        @Override
        public int nextInt(int bound) {
            if (q.isEmpty()) return 0;
            int v = q.removeFirst();
            if (v < 0) v = 0;
            if (v >= bound) v = v % bound;
            return v;
        }
    }

    @Test
    void iniciarSpawneaLConOrientacion0() {
        // Tablero chico 3x4: la L 3x3 entra perfecta
        Board board = new Board(3, 4);
        Reloj reloj = new Reloj();
        // nextInt(5)=3 -> PieceL ; nextInt(4)=0 -> 0 rotaciones (orientación 0)
        Random rnd = new FakeRandom().enqueue(3, 0);

        Tetris t = new Tetris(board, reloj, rnd);
        t.start();

        Tetris.Estado st = t.state();
        assertTrue(st.enEjecucion(), "Tras start(), el juego debe estar en ejecución");
        assertNotNull(st.piezaActual(), "Debe haberse spawneado una pieza");
        assertTrue(st.piezaActual() instanceof PieceL, "La pieza inicial debe ser PieceL");
        assertEquals(0, st.piezaActual().getOrientacion(), "La L debe iniciar en orientación 0");
    }

    @Test
    void rotarDerechaYLuegoIzquierdaVuelvenALaOrientacionInicial_L() {
        Board board = new Board(3, 4);
        Reloj reloj = new Reloj();
        // Forzar PieceL (3) y 0 rotaciones
        Random rnd = new FakeRandom().enqueue(3, 0);

        Tetris t = new Tetris(board, reloj, rnd);
        t.start();

        int ori0 = t.state().piezaActual().getOrientacion();
        t.rotarDerecha();
        int ori1 = t.state().piezaActual().getOrientacion();
        assertEquals((ori0 + 1) % 4, ori1, "rotateRight() debe sumar +1 mod 4");

        t.rotarIzquierda();
        int ori2 = t.state().piezaActual().getOrientacion();
        assertEquals(ori0, ori2, "rotateLeft() tras rotateRight() debe volver a la orientación inicial");
    }

    @Test
    void unaLPuedeCompletarYLimpiarLinea() {
        // Diseño: en 3x4 vacío, una L en orientación 0 cae hasta el fondo y llena la fila inferior (3 celdas) -> limpia 1 línea
        Board board = new Board(3, 4);
        Reloj reloj = new Reloj();
        // Forzar PieceL y 0 rotaciones (orientación 0)
        Random rnd = new FakeRandom().enqueue(3, 0);

        Tetris t = new Tetris(board, reloj, rnd);
        t.start();

        // Dejar caer hasta que se fije (cuando no pueda bajar más, Board la fija y limpia)
        // t.tick() avanza un paso (y Reloj suma +1)
        while (t.state().piezaActual() != null) {
            t.tick();
        }

        // Debe haber limpiado exactamente 1 línea
        assertEquals(1, t.state().lineCount(), "La caída de la L en 3x4 vacío debe completar y limpiar una línea");
        // Y el juego sigue en ejecución (no es game over)
        assertTrue(t.state().enEjecucion(), "El juego debe continuar tras limpiar la línea");
    }
}
