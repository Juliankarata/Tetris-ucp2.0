package com.tetris;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PiezaTTest {

    @Test
    public void testConstructor() {
        PiezaT pieza = new PiezaT();
        assertEquals(3, pieza.filas);
        assertEquals(3, pieza.columnas);
        assertEquals(0, pieza.getOrientacion());
    }

    @Test
    public void testObtenerFormaOrientacion0() {
        PiezaT pieza = new PiezaT();
        boolean[][] expected = {
            {false, true, false},
            {true, true, true},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion1() {
        PiezaT pieza = new PiezaT();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {true, false, false},
            {true, true, false},
            {true, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion2() {
        PiezaT pieza = new PiezaT();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, false, false},
            {true, true, true},
            {false, true, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion3() {
        PiezaT pieza = new PiezaT();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, true, false},
            {true, true, false},
            {false, true, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testRotarDerecha() {
        PiezaT pieza = new PiezaT();
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
        PiezaT pieza = new PiezaT();
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
        PiezaT pieza = new PiezaT();
        assertEquals(0, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(1, pieza.getOrientacion());
    }
}
