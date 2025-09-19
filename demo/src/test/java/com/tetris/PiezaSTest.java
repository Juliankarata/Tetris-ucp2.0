package com.tetris;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PiezaSTest {

    @Test
    public void testConstructor() {
        PiezaS pieza = new PiezaS();
        assertEquals(3, pieza.filas);
        assertEquals(3, pieza.columnas);
        assertEquals(0, pieza.getOrientacion());
    }

    @Test
    public void testObtenerFormaOrientacion0() {
        PiezaS pieza = new PiezaS();
        boolean[][] expected = {
            {false, true, true},
            {true, true, false},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion1() {
        PiezaS pieza = new PiezaS();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {true, false, false},
            {true, true, false},
            {false, true, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion2() {
        PiezaS pieza = new PiezaS();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, true, true},
            {true, true, false},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion3() {
        PiezaS pieza = new PiezaS();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {true, false, false},
            {true, true, false},
            {false, true, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testRotarDerecha() {
        PiezaS pieza = new PiezaS();
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
        PiezaS pieza = new PiezaS();
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
        PiezaS pieza = new PiezaS();
        assertEquals(0, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(1, pieza.getOrientacion());
    }
}
