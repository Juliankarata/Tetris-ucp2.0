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



    /**
     * “Victoria” = alcanzar una meta de líneas limpiadas.
     * No modifica producción ni usa helpers nuevos: se apoya en lineCount() y enEjecucion().
     */
    @Test
    void juegoAlcanzaMetaDeLineas_SeConsideraVictoria() {
        // Tablero chico y determinista
        Board board = new Board(4, 4);
        Reloj reloj = new Reloj();
        // FakeRandom cualquiera: pieza y rotación no importan para limpiar la fila prellena
        FakeRandom rnd = new FakeRandom().enqueue(0, 0); // 0=PieceStick, 0 rotaciones (pero da igual)
        Tetris juego = new Tetris(board, reloj, rnd);

        // Precondición: última fila ya completa (se limpiará cuando se fije la primera pieza)
        boolean[][] inicial = {
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {true,  true,  true,  true}
        };
        board.establecerTablero(inicial);

        // Arranca el juego (usa tu API actual)
        Tetris.iniciar(juego);
        assertTrue(juego.state().enEjecucion(), "Debe iniciar en ejecución");

        // Meta de “victoria” por líneas
        final int META_LINEAS = 1;

        // Dejamos que la pieza caiga y se fije usando el ciclo oficial del juego
        // (sin agregar métodos nuevos ni tocar producción)
        int guardRail = 50; // evita loops infinitos ante errores
        while (juego.state().enEjecucion() && board.getLineCount() < META_LINEAS && guardRail-- > 0) {
            // opcional: intentar moverla para “jugar”; no es necesario para este caso
            // juego.moveRight();
            juego.tick();
        }

        // Aserciones de "victoria" por meta alcanzada
        assertTrue(board.getLineCount() >= META_LINEAS, "Debe alcanzar la meta de líneas limpiadas");
        assertTrue(juego.state().enEjecucion(), "El juego puede continuar tras alcanzar la meta (no hay lógica de fin por victoria)");

        // Extras útiles para depurar si algo cambia en el futuro
        assertNotNull(juego.state().grilla(), "La grilla debe estar disponible en el estado");
        assertTrue(juego.state().ticks() > 0, "Deben haberse consumido algunos ticks");
    }
}


    

