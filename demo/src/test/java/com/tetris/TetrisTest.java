// File: src/test/java/com/tetris/TetrisTest.java
package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TetrisTest {

    @Test
    void startDebeSpawnearPiezaYQuedarEnEjecucion() {
        Tetris t = new Tetris(10, 20);

        // Antes de iniciar no tenemos estado “vivo”, pero al iniciar debe haber pieza
        t.start();
        Tetris.Estado st = t.state();

        assertTrue(st.enEjecucion(), "Tras start() el juego debe estar en ejecución");
        assertNotNull(st.piezaActual(), "Debe existir una pieza activa tras start()");
        assertNotNull(st.grilla(), "La grilla debe estar disponible en el estado");
        assertEquals(20, st.grilla().length, "Alto de grilla esperado");
        assertEquals(10, st.grilla()[0].length, "Ancho de grilla esperado");
    }

    @Test
    void tickDebeIncrementarElReloj() {
        Tetris t = new Tetris(10, 20);
        t.start();
        long t0 = t.state().ticks();

        t.tick(); // avanzar 1 paso lógico

        long t1 = t.state().ticks();
        assertEquals(t0 + 1, t1, "Cada tick() debe incrementar el reloj del juego en +1");
    }

    @Test
    void rotateLeftRightCambianOrientacionYVuelvenAlOrigen() {
        Tetris t = new Tetris(10, 20);
        t.start();

        Pieza p0 = t.state().piezaActual();
        assertNotNull(p0, "Debe haber una pieza para poder rotar");
        int oriInicial = p0.getOrientacion();

        t.rotateRight();
        int ori1 = t.state().piezaActual().getOrientacion();
        assertEquals((oriInicial + 1) % 4, ori1, "rotateRight() debe sumar +1 mod 4");

        t.rotateLeft();
        int ori2 = t.state().piezaActual().getOrientacion();
        assertEquals(oriInicial, ori2, "rotateLeft() tras rotateRight() debe volver a la orientación inicial");
    }

    @Test
    void stateDevuelveCopiaDeLaGrillaNoUnaVistaViva() {
        Tetris t = new Tetris(6, 8);
        t.start();

        boolean[][] g1 = t.state().grilla();
        assertNotNull(g1);
        // Mutamos la copia local
        g1[0][0] = !g1[0][0];

        // Volvemos a pedir el estado: no debería reflejar nuestra mutación local
        boolean[][] g2 = t.state().grilla();
        assertNotNull(g2);

        assertEquals(8, g2.length);
        assertEquals(6, g2[0].length);

        assertNotEquals(g1[0][0], g2[0][0],
                "La grilla de state() debe ser copia defensiva; mutar g1 no debe afectar el estado real");
    }

    @Test
    void startEnTableroDemasiadoChicoDebeFallarGameOver() {
        // En 1x1 ninguna pieza cabe: el juego no puede iniciar (game over inmediato)
        Tetris t = new Tetris(1, 1);
        t.start();
        Tetris.Estado st = t.state();

        assertFalse(st.enEjecucion(), "Si no hay espacio para spawnear, el juego debe quedar detenido");
        assertNull(st.piezaActual(), "No debe haber pieza activa tras el intento fallido de spawn");
        // ticks = 0 porque aún no llamamos a tick()
        assertEquals(0L, st.ticks(), "No debería haberse avanzado el reloj todavía");
    }
}
