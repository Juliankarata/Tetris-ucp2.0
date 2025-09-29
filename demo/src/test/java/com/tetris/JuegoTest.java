// File: src/test/java/com/tetris/JuegoTest.java
package com.tetris;

import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

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
}
