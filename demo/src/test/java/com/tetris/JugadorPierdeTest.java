package com.tetris;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class JugadorPierdeTest {

    @Test
    public void testJugadorPierdeCuandoNoPuedeColocarPieza() {
        Board tablero = new Board(4, 4);
        // Llenar la fila superior para que no quepa ninguna pieza
        boolean[][] tableroLleno = {
            {true, true, true, true},
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false}
        };
        tablero.establecerTablero(tableroLleno);

        Reloj reloj = new Reloj();
        Random rnd = new Random(123);
        Tetris juego = new Tetris(tablero, reloj, rnd);

        Tetris.iniciar(juego);

        // El juego debe quedar detenido y no debe haber pieza activa
        org.junit.jupiter.api.Assertions.assertFalse(juego.isEnEjecucion(), "El juego debe quedar detenido si no hay espacio para la pieza inicial");
        org.junit.jupiter.api.Assertions.assertNull(tablero.obtenerPiezaActual(), "No debe haber pieza activa si no pudo spawnear");
    }
}
