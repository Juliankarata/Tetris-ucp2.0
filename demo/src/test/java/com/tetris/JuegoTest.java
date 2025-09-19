package com.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class JuegoTest {

    @Test
    public void testConstructor() {
        Juego juego = new Juego(10, 20);
        assertNotNull(juego.getTablero());
        assertNotNull(juego.getReloj());
        assertFalse(juego.isEnEjecucion());
        assertEquals(10, juego.getTablero().getAncho());
        assertEquals(20, juego.getTablero().getAlto());
    }

    @Test
    public void testIniciar() {
        Juego juego = new Juego(10, 20);
        juego.iniciar();
        assertTrue(juego.isEnEjecucion());
        assertNotNull(juego.getTablero().obtenerPiezaActual());
    }

    @Test
    public void testAvanzarTick() {
        Juego juego = new Juego(10, 20);
        juego.iniciar();
        int contadorInicial = juego.getReloj().getContador();
        juego.avanzarTick();
        assertEquals(contadorInicial + 1, juego.getReloj().getContador());
    }

    // Removed testCrearPiezaAleatoria because crearPiezaAleatoria() is private and cannot be tested directly
}
