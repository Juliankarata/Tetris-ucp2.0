package com.tetris;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testEliminarLineasCompletas() {
        Board tablero = new Board(4, 4);

        boolean[][] tableroInicial = {
            {false, false, false, false},
            {false, false, false, false},
            {false, true,  false, false},
            {true,  true,  true,  true}
        };

        tablero.establecerTablero(tableroInicial);

        int eliminadas = tablero.eliminarLineasCompletas();
        assertEquals(1, eliminadas, "Debe eliminar exactamente una línea completa");

        boolean[][] tableroResultante = tablero.obtenerTablero();

        boolean[][] esperado = {
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {false, true,  false, false}
        };

        for (int i = 0; i < 4; i++) {
            assertArrayEquals(esperado[i], tableroResultante[i], "Fila " + i + " incorrecta");
        }
    }

    @Test
    public void testColisionEntrePiezas() {
        Board tablero = new Board(4, 6);

        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);
        while (tablero.moverAbajo()) {
            // dejar caer piezaO
        }

        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        boolean colisiono = false;
        while (tablero.obtenerPiezaActual() != null) {
            boolean pudoMover = tablero.moverAbajo();
            if (!pudoMover) {
                colisiono = true;
                break;
            }
        }

        assertTrue(colisiono, "La segunda pieza debe colisionar con la primera y detenerse");
    }

    @Test
    public void testMovimientosLateralesYLímites() {
        Board tablero = new Board(4, 6);

        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);

        // Mover a la izquierda hasta que no pueda más
        while (tablero.moverIzquierda()) {}

        // Intentar mover más a la izquierda (debe fallar)
        assertFalse(tablero.moverIzquierda(), "No debe poder moverse fuera del tablero a la izquierda");

        // Mover a la derecha hasta que no pueda más
        while (tablero.moverDerecha()) {}

        // Intentar mover más a la derecha (debe fallar)
        assertFalse(tablero.moverDerecha(), "No debe poder moverse fuera del tablero a la derecha");
    }
}
