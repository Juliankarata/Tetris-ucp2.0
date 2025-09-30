package com.tetris;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Juego juego = new Juego(tablero, reloj, rnd);

        // Al iniciar, intenta colocar una pieza aleatoria, pero no puede porque la fila superior está llena
        assertThrows(IllegalStateException.class, () -> juego.iniciar(),
            "Debe lanzar IllegalStateException cuando no hay espacio para colocar la pieza inicial (jugador pierde)");
    }
}
