// File: src/test/java/com/tetris/JuegoTest.java
package com.tetris;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class JuegoTest {

    @Test
    void iniciarGeneraPiezaYTickAvanzaReloj_es() {
        Board b = new Board(6, 10);
        Reloj r = new Reloj();
        Random rnd = new Random(123); 
        Tetris juego = new Tetris(b, r, rnd);

        assertNull(b.obtenerPiezaActual());
    Tetris.iniciar(juego);
        assertNotNull(b.obtenerPiezaActual(), "Al iniciar debe haber una pieza actual");

        int t0 = r.getTicks();
        juego.avanzarTick();
        assertEquals(t0 + 1, r.getTicks(), "Cada tick de juego incrementa el reloj");
    }

    @Test
    void piezasAleatorias() {
        Random rnd = new Random(42); // semilla fija para reproducibilidad
        Tetris juego = new Tetris(new Board(6, 10), new Reloj(), rnd);

        // Generar varias piezas aleatorias y verificar que sean instancias de las clases esperadas
        for (int i = 0; i < 20; i++) {
            Pieza p = juego.getRandomPiece();
            assertTrue(
                p instanceof PieceStick ||
                p instanceof PieceSquare ||
                p instanceof PieceT ||
                p instanceof PieceL ||
                p instanceof PieceDog,
                "La pieza generada debe ser una instancia válida de pieza"
            );
        }
    }

    // Test: el juego corre y elimina una línea, sigue en ejecución
    @Test
    void juegoCorriendoEliminaLineaYSigue() {
        Board board = new Board(4, 4);
        Reloj reloj = new Reloj();
        // FakeRandom: PieceStick (I) vertical, sin rotación
        FakeRandom rnd = new FakeRandom().enqueue(0, 0);
        Tetris juego = new Tetris(board, reloj, rnd);
        // Deja la última fila casi llena
        boolean[][] inicial = {
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {true,  true,  true,  false}
        };
        board.establecerTablero(inicial);
        Tetris.iniciar(juego);
        // Mover la I a la derecha para completar la fila
        while (board.moverDerecha()) {}
        // Dejarla caer
        while (board.obtenerPiezaActual() != null) {
            board.moverAbajo();
        }
        // Debe haber eliminado una línea y el juego sigue
        assertEquals(1, board.getLineCount(), "Debe eliminar una línea");
        assertNotNull(juego.state().piezaActual(), "Debe haber una nueva pieza en juego");
        assertTrue(juego.state().enEjecucion(), "El juego debe seguir en ejecución");
    }

    // Test: el juego termina (pierde) al no poder spawnear una pieza
    @Test
    void juegoPierdeAlNoHaberEspacio() {
        Board board = new Board(4, 4);
        Reloj reloj = new Reloj();
        // FakeRandom: PieceSquare (O)
        FakeRandom rnd = new FakeRandom().enqueue(1, 0);
        Tetris juego = new Tetris(board, reloj, rnd);
        // Tablero lleno arriba, no hay espacio para spawnear
        boolean[][] lleno = {
            {true, true, true, true},
            {true, true, true, true},
            {false, false, false, false},
            {false, false, false, false}
        };
        board.establecerTablero(lleno);
        Tetris.iniciar(juego);
        // El juego debe estar en estado terminado
        assertTrue(juego.state().estaTerminado(), "El juego debe estar terminado (perdido)");
        assertNull(juego.state().piezaActual(), "No debe haber pieza activa tras perder");
    }
}

