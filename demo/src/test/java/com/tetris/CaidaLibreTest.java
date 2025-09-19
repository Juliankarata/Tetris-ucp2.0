package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaidaLibreTest {

    @Test
    public void testPiezaOCaidaLibre() {
        Juego juego = new Juego(4, 6);
        Tablero tablero = juego.getTablero();

        Pieza piezaO = new PiezaO(); 
        tablero.ponerPiezaActual(piezaO);

        boolean aterrizo = false;
        for (int i = 0; i < 20; i++) {
            tablero.moverAbajo();
            if (tablero.obtenerPiezaActual() == null) {
                aterrizo = true;
                break;
            }
        }

        assertTrue(aterrizo, "La pieza O debería aterrizar (caída libre)");
    }

    @Test
    public void testPiezaICaidaLibre() {
        Juego juego = new Juego(4, 6);
        Tablero tablero = juego.getTablero();

        Pieza piezaI = new PiezaI();
        tablero.ponerPiezaActual(piezaI);

        boolean aterrizo = false;
        for (int i = 0; i < 20; i++) {
            tablero.moverAbajo();
            if (tablero.obtenerPiezaActual() == null) {
                aterrizo = true;
                break;
            }
        }

        assertTrue(aterrizo, "La pieza I debería aterrizar (caída libre)");
    }
}