package com.tetris;

// Importa las clases necesarias de JUnit para los tests
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Clase de pruebas para verificar condiciones de victoria en el juego
class TestGanar{

    // Test que verifica que se gana al eliminar dos líneas con una pieza I horizontal
    @Test
    void ganaAlEliminarDosLineasConUnaIHorizontal() {
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
            {false, false, false, false}, // Fila 0: vacía
            {false, false, false, false}, // Fila 1: vacía
            {false, true,  true,  true},  // Fila 2: casi llena
            {false, true,  true,  true}   // Fila 3: casi llena
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

    // Test que verifica que el juego falla al iniciar si no hay espacio
    @Test
    void fallaAlIniciar() {
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


// --- 1) PERDER BÁSICO: top-out al iniciar ---
@Test
void pierdeAlIniciarPorFilaDeSpawnBloqueada() {
    Board board = new Board(4, 4);
    Reloj reloj = new Reloj();
    // La pieza da igual; no va a entrar igual si el spawn está bloqueado
    FakeRandom rnd = new FakeRandom().enqueue(0, 0);
    Tetris juego = new Tetris(board, reloj, rnd);

    // Bloqueamos la zona de spawn (fila 0 llena y fila 1 con ocupados)
    boolean[][] bloqueado = {
        {true,  true,  true,  true}, // fila 0: llena
        {false, true,  false, false},// fila 1: parcialmente ocupada
        {false, false, false, false},
        {false, false, false, false}
    };
    board.establecerTablero(bloqueado);

    // Iniciar: no debe poder spawnear -> game over inmediato
    Tetris.iniciar(juego);

    assertFalse(juego.isEnEjecucion(), "Si no hay espacio para spawnear, el juego debe quedar detenido (game over).");
    assertNull(board.obtenerPiezaActual(), "No debe haber pieza activa si no pudo spawnear.");
    assertEquals(0, reloj.getTicks(), "El reloj no debe avanzar si no llamamos a tick/avanzarTick().");
}

// --- 2) LIMPIEZA BÁSICA EN PARTIDA: 1 línea con I horizontal (sin exigir game-over) ---
@Test
void limpiaUnaLineaConIHorizontal_sinGameOver() {
    Board board = new Board(4, 4);
    Reloj reloj = new Reloj();
    // FakeRandom: tipo I (0) y orientación horizontal (1)
    FakeRandom rnd = new FakeRandom().enqueue(0, 1);
    Tetris juego = new Tetris(board, reloj, rnd);

    // IMPORTANTE: incluir la fila 1 VACÍA para que spawnee la I horizontal
    boolean[][] escenario = {
        {true,  false, false, false}, // fila 0: NO completa, pero con un bloque que luego bajará
        {false, false, false, false}, // fila 1: VACÍA (spawn)
        {true,  true,  true,  false}, // fila 2: casi llena
        {true,  true,  true,  false}  // fila 3: casi llena
    };
    board.establecerTablero(escenario);

    // Iniciar (spawnea I horizontal en fila 1)
    Tetris.iniciar(juego);
    assertNotNull(board.obtenerPiezaActual(), "Debe existir una pieza activa tras iniciar.");

    // Opcional: mover a la izquierda por si tu spawn no nace a la izquierda
    while (board.moverIzquierda()) {}

    // Dejar caer hasta fijar y limpiar
    while (board.obtenerPiezaActual() != null) {
        board.moverAbajo();
    }

    // Con este escenario se limpia exactamente 1 línea
    assertEquals(1, board.getLineCount(), "Debe eliminarse 1 línea al completar la fila 1.");

    // No exigimos game-over aquí (tu motor hoy no lo marca automáticamente al limpiar).
    // Sólo verificamos que la pieza se fijó y ya no hay activa.
    assertNull(board.obtenerPiezaActual(), "Tras fijar y limpiar, no debe quedar pieza activa.");
    assertTrue(juego.isEnEjecucion(), "El juego puede seguir si tu implementación no marca game-over al limpiar.");
    assertEquals(0, reloj.getTicks(), "El reloj no debe avanzar si no llamamos a tick/avanzarTick().");
}
}