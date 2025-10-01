package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class TestGanar{

    @Test
    void tetrisGanaAlEliminarDosLineasConUnaIHorizontal() {
        // Tablero 4x4 para facilitar dos filas completas
        Board board = new Board(4, 4);
        Reloj reloj = new Reloj();
        // FakeRandom: primero pide pieza (bound=5) -> 0 => PieceStick (I)
        // luego pide rotaciones (bound=4) -> 1 => horizontal
        FakeRandom rnd = new FakeRandom().enqueue(0, 1);
        Tetris juego = new Tetris(board, reloj, rnd);

        // Preparamos el tablero con dos filas casi completas, pero la fila 1 (donde spawnea la I horizontal) está vacía
        // fila 2: [true, true, true, false] (hueco a la derecha)
        // fila 1: [false, false, false, false] (vacía para que la I horizontal pueda spawnear)
        boolean[][] inicial = {
            {false, false, false, false},
            {false, false, false, false},
            {false, true,  true,  true},
            {false, true,  true,  true}
        };
        board.establecerTablero(inicial);

        // Iniciar (spawnea I horizontal)
        Tetris.iniciar(juego);
        if (board.obtenerPiezaActual() == null) {
            org.junit.jupiter.api.Assertions.fail("No se pudo spawnear la pieza inicial, revisa el estado inicial del tablero o la lógica de spawn");
        }

        // Mover a la izquierda hasta donde permita (para cubrir los huecos en columna 0)
        while (board.moverIzquierda()) {}

        // Dejarla caer hasta fijar y limpiar
        while (board.obtenerPiezaActual() != null) {
            board.moverAbajo();
        }

    // Debe haber eliminado exactamente 1 línea (por la lógica actual y el tamaño del tablero)
    assertEquals(1, board.getLineCount(), "Debe acumular 1 línea eliminada al completar la fila");
        // Además, las dos filas inferiores ahora no deben estar completamente llenas (quedaron vaciadas y bajadas).
        boolean[][] finalState = board.obtenerTablero();
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
        Tetris juego = new Tetris(board, reloj, rnd);

        // Llenamos la fila superior para impedir el spawn (y parte de la segunda para robustez)
        boolean[][] bloqueado = {
            {true,  true,  true,  true},
            {false, false, true,  false},
            {false, false, false, false},
            {false, false, false, false}
        };
        board.establecerTablero(bloqueado);

        // iniciar intenta spawnear; al no poder, enEjecucion queda false y no hay piezaActual
        Tetris.iniciar(juego);

        assertFalse(juego.isEnEjecucion(), "Si no hay espacio para spawnear, el juego debería quedar detenido (game over).");
        assertNull(board.obtenerPiezaActual(), "No debe haber pieza activa si no pudo spawnear.");
        // Además, el reloj no avanza porque aún no llamamos a avanzarTick()
        assertEquals(0, reloj.getTicks(), "El reloj no debería haber avanzado todavía.");
    }
}
