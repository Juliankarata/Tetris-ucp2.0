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
    void iniciarGeneraPiezaYTickAvanzaReloj() {
        Board b = new Board(6, 10);
        Reloj r = new Reloj();
        Random rnd = new Random(123); // determinístico
        Juego juego = new Juego(b, r, rnd);

        assertNull(b.obtenerPiezaActual());
        juego.iniciar();
        assertNotNull(b.obtenerPiezaActual(), "Al iniciar debe haber una pieza actual");

        int t0 = r.getTicks();
        juego.avanzarTick();
        assertEquals(t0 + 1, r.getTicks(), "Cada tick de juego incrementa el reloj");
    }

    @Test
    void PiecesAleatory() {
        Random rnd = new Random(42); // semilla fija para reproducibilidad
        Juego juego = new Juego(new Board(6, 10), new Reloj(), rnd);

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
}
