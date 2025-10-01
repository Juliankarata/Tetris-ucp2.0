package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

class TestGanar{

    @Test
    void tetrisGanaAlEliminarDosLineasConUnaIHorizontal() {
        // Tablero 4x4 para facilitar dos filas completas
        Board board = new Board(4, 4);
        Reloj reloj = new Reloj();
        // FakeRandom: primero pide pieza (bound=5) -> 0 => PieceStick (I)
        // luego pide rotaciones (bound=4) -> 0 => sin rotar
        FakeRandom rnd = new FakeRandom().enqueue(0, 0);
        Juego juego = new Juego(board, reloj, rnd);

        // Preparamos el tablero con dos filas casi completas (faltando 1 celda en cada una).
        // Queremos que una I horizontal complete ambas a la vez.
        // Estado (true = ocupado):
        // fila 3: [true, true, true, false]
        // fila 2: [true, true, true, false]
        boolean[][] inicial = {
            {false, false, false, false},
            {false, false, false, false},
            {true,  true,  true,  false},
            {true,  true,  true,  false}
        };
        board.establecerTablero(inicial);

        // Iniciar (spawnea I sin rotación; en nuestra I sin rotación = vertical.
        // PERO ¡OJO!: nuestra PieceStick define vertical cuando orientacion%2==0, horizontal cuando ==1.
        // Como pedimos 0 rotaciones, está vertical. La vamos a rotar a horizontal manualmente.
        juego.iniciar();
        assertNotNull(board.obtenerPiezaActual(), "Debe existir pieza al iniciar");

        // Rotar a horizontal (derecha) y mover a la derecha para cubrir los huecos finales (columna 3).
        // Con nuestra PieceStick, horizontal ocupa la fila 'piezaFila+1' desde col..col+3
        // Queremos que la I horizontal caiga para llenar las columnas [0..3] en fila 2 y 3;
        // los huecos están en col=3, así que centramos la pieza de forma que su extremo derecho llegue a col=3.
        juego.rotateRight(); // ahora horizontal
        // Mover a la derecha hasta donde permita
        while (board.moverDerecha()) {}

        // Dejarla caer hasta fijar y limpiar
        while (board.obtenerPiezaActual() != null) {
            board.moverAbajo();
        }

        // Debe haber eliminado exactamente 2 líneas
        assertEquals(2, board.getLineCount(), "Debe acumular 2 líneas eliminadas al completar las dos filas");
        // Además, las dos filas inferiores ahora no deben estar completamente llenas (quedaron vaciadas y bajadas).
        boolean[][] finalState = board.obtenerTablero();
        // chequeo simple: ninguna fila completamente llena
        for (int r = 0; r < board.getAlto(); r++) {
            boolean completa = true;
            for (int c = 0; c < board.getAncho(); c++) {
                if (!finalState[r][c]) { completa = false; break; }
            }
            assertFalse(completa, "No debería quedar una fila completa tras limpiar");
        }
    }

    @Test
    void tetrisFallaAlIniciar() {
        Board board = new Board(4, 4);
        Reloj reloj = new Reloj();
        // No importa la pieza, ninguna entra si la fila 0 está llena y la 1 también en parte
        FakeRandom rnd = new FakeRandom().enqueue(1, 0); // O y 0 rotaciones, por ejemplo
        Juego juego = new Juego(board, reloj, rnd);

        // Llenamos la fila superior para impedir el spawn (y parte de la segunda para robustez)
        boolean[][] bloqueado = {
            {true,  true,  true,  true},
            {false, false, true,  false},
            {false, false, false, false},
            {false, false, false, false}
        };
        board.establecerTablero(bloqueado);

        // iniciar intenta spawnear; al no poder, enEjecucion queda false y no hay piezaActual
        juego.iniciar();

        assertFalse(juego.isEnEjecucion(), "Si no hay espacio para spawnear, el juego debería quedar detenido (game over).");
        assertNull(board.obtenerPiezaActual(), "No debe haber pieza activa si no pudo spawnear.");
        // Además, el reloj no avanza porque aún no llamamos a avanzarTick()
        assertEquals(0, reloj.getTicks(), "El reloj no debería haber avanzado todavía.");
    }
}
