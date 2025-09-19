package com.tetris;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PiezaZTest {

    @Test
    public void testConstructor() {
        PiezaZ pieza = new PiezaZ();
        assertEquals(3, pieza.filas);
        assertEquals(3, pieza.columnas);
        assertEquals(0, pieza.getOrientacion());
    }

    @Test
    public void testObtenerFormaOrientacion0() {
        PiezaZ pieza = new PiezaZ();
        boolean[][] expected = {
            {true, true, false},
            {false, true, true},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion1() {
        PiezaZ pieza = new PiezaZ();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, true, false},
            {true, true, false},
            {true, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion2() {
        PiezaZ pieza = new PiezaZ();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {true, true, false},
            {false, true, true},
            {false, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testObtenerFormaOrientacion3() {
        PiezaZ pieza = new PiezaZ();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        pieza.rotarDerecha();
        boolean[][] expected = {
            {false, true, false},
            {true, true, false},
            {true, false, false}
        };
        assertTrue(Arrays.deepEquals(expected, pieza.obtenerForma()));
    }

    @Test
    public void testRotarDerecha() {
        PiezaZ pieza = new PiezaZ();
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
        PiezaZ pieza = new PiezaZ();
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
        PiezaZ pieza = new PiezaZ();
        assertEquals(0, pieza.getOrientacion());
        pieza.rotarDerecha();
        assertEquals(1, pieza.getOrientacion());
    }
}
