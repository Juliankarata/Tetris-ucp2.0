package com.tetris;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PiezaLTest {

    @Test
    public void testConstructor() {
        PiezaL pieza = new PiezaL();
        assertEquals(3, pieza.filas);
        assertEquals(3, pieza.columnas);
        assertEquals(0, pieza.getOrientacion());
    }

    @Test
    public void testObtenerFormaOrientacion0() {
        PiezaL pieza = new PiezaL();
        boolean[][] expected = {
            {true, false, false},
            {true, false, false},
            {true, true, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion1() {
        PiezaL pieza = new PiezaL();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, false, false},
            {false, false, true},
            {true, true, true}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion2() {
        PiezaL pieza = new PiezaL();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, true, true},
            {false, false, true},
            {false, false, true}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion3() {
        PiezaL pieza = new PiezaL();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {true, true, true},
            {true, false, false},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testRotarDerecha() {
        PiezaL pieza = new PiezaL();
        pieza.rotarDerecha();
        assertEquals(1, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(2, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(3, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(0, pieza.getOrientacion());
    }

    @Test
    public void testRotarIzquierda() {
        PiezaL pieza = new PiezaL();
        pieza.rotarIzquierda();
        assertEquals(3, pieza.getOrientacion());
        pieza.rotarIzquierda();
        assertEquals(2, pieza.getOrientacion());
        pieza.rotarIzquierda();
        assertEquals(1, pieza.getOrientacion());
        pieza.rotarIzquierda();
        assertEquals(0, pieza.getOrientacion());
    }

    @Test
    public void testGetOrientacion() {
        PiezaL pieza = new PiezaL();
        assertEquals(0, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(1, pieza.getOrientacion());
    }
}
