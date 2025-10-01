package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TetrisEdgeCasesTest {
    @Test
    void gameOverDespuesDeVariosTicks() {
        Board tablero = new Board(2, 2);
        Reloj reloj = new Reloj();
        FakeRandom rnd = new FakeRandom().enqueue(0, 0, 0, 0, 0, 0, 0, 0); // siempre PieceStick
        Tetris t = new Tetris(tablero, reloj, rnd);
        t.start();
        int ticks = 0;
        while (t.state().enEjecucion() && ticks < 10) {
            t.tick();
            ticks++;
        }
        assertFalse(t.state().enEjecucion(), "El juego debe terminar cuando no hay espacio para nuevas piezas");
    }

    @Test
    void combinacionesAleatoriasDePiezasYRotaciones() {
        Board tablero = new Board(4, 4);
        Reloj reloj = new Reloj();
        FakeRandom rnd = new FakeRandom().enqueue(0, 1, 1, 2, 2, 3, 3, 0, 4, 2, 0, 3);
        Tetris t = new Tetris(tablero, reloj, rnd);
        t.start();
        assertNotNull(t.state().piezaActual(), "Debe haber una pieza tras iniciar");
        // Rotar y mover varias veces
        for (int i = 0; i < 4; i++) {
            t.rotarDerecha();
            t.moveLeft();
            t.moveRight();
        }
        assertNotNull(t.state().piezaActual(), "La pieza debe seguir activa si no ha aterrizado");
    }
}
