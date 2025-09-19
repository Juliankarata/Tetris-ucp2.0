package com.tetris;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PiezaJTest {

    @Test
    public void testConstructor() {
        PiezaJ pieza = new PiezaJ();
        assertEquals(3, pieza.filas);
        assertEquals(3, pieza.columnas);
        assertEquals(0, pieza.getOrientacion());
    }

    @Test
    public void testObtenerFormaOrientacion0() {
        PiezaJ pieza = new PiezaJ();
        boolean[][] expected = {
            {false, true, false},
            {false, true, false},
            {true, true, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion1() {
        PiezaJ pieza = new PiezaJ();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, false, true},
            {true, true, true},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion2() {
        PiezaJ pieza = new PiezaJ();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {true, true, false},
            {true, false, false},
            {true, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion3() {
        PiezaJ pieza = new PiezaJ();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {true, false, false},
            {true, true, true},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testRotarDerecha() {
        PiezaJ pieza = new PiezaJ();
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
        PiezaJ pieza = new PiezaJ();
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
        PiezaJ pieza = new PiezaJ();
        assertEquals(0, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(1, pieza.getOrientacion());
    }
}
