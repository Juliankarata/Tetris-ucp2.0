package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColisionPiezasTest {

    /** Helper: deja caer la pieza actual hasta que se fije (piezaActual pasa a null). */
    private void hardDrop(Board b) {
        while (b.obtenerPiezaActual() != null) {
            b.moverAbajo();
        }
    }

    /** Cuenta celdas true en la grilla */
    private int countTrue(boolean[][] g) {
        int k = 0;
        for (int r = 0; r < g.length; r++) {
            for (int c = 0; c < g[0].length; c++) if (g[r][c]) k++;
        }
        return k;
    }

    @Test
    public void colisionVertical_L_contra_Cuadrada() {
        // Tablero con altura suficiente para evitar problemas de spawn
        Board board = new Board(5, 6);

        // 1) Fijar primero un cuadrado en el fondo (bloque "squaredog")
        Pieza square = new PieceSquare();
        board.ponerPiezaActual(square);
        hardDrop(board); // ahora el cuadrado queda fijo

        boolean[][] afterSquare = board.obtenerTablero();
        int ocupadasTrasSquare = countTrue(afterSquare);
        assertTrue(ocupadasTrasSquare >= 4, "El cuadrado debe haber quedado fijado");

        // 2) Spawnear una L y hacerla caer hasta colisionar con el cuadrado
        Pieza piezaL = new PieceL();
        board.ponerPiezaActual(piezaL);

        // Si tu L nace en una orientación que no baja cómodo, esta rotación ayuda
        // (no cambia producción: es tu método real con wall-kicks).
        board.rotarPiezaActualDerecha();

        // Dejarla caer hasta que colisione y se fije
        hardDrop(board);

        // 3) Verificaciones:
        //    - La pieza actual debe ser null (se fijó), señal de colisión/aterrizaje.
        assertNull(board.obtenerPiezaActual(), "La L debe haberse fijado tras colisionar");

        //    - La cantidad de celdas ocupadas aumentó en 4 (tamaño de la L)
        boolean[][] finalGrid = board.obtenerTablero();
        int ocupadasFinal = countTrue(finalGrid);
        assertEquals(ocupadasTrasSquare + 4, ocupadasFinal,
                "Tras caer la L, deben existir 4 celdas ocupadas adicionales");

        //    - Existe al menos un par de celdas apiladas verticalmente (contacto real)
        boolean hayContactoVertical = false;
        for (int r = 0; r < finalGrid.length - 1 && !hayContactoVertical; r++) {
            for (int c = 0; c < finalGrid[0].length && !hayContactoVertical; c++) {
                if (finalGrid[r][c] && finalGrid[r + 1][c]) hayContactoVertical = true;
            }
        }
        assertTrue(hayContactoVertical, "Debe haber contacto vertical entre bloques (colisión real)");
    }

    @Test
    public void colisionLateral_L_contra_bloqueFijo() {
        Board board = new Board(6, 6);

        // 1) Fijamos un cuadrado a la izquierda
        Pieza square = new PieceSquare();
        board.ponerPiezaActual(square);
        // Lo movemos lo más a la izquierda posible para que quede fijo tocando pared
        while (board.moverIzquierda()) {}
        hardDrop(board);

        // 2) Spawneamos una L a la izquierda y tratamos de moverla lateral hacia el cuadrado
        Pieza piezaL = new PieceL();
        board.ponerPiezaActual(piezaL);

        // La empujamos hacia la izquierda hasta que ya no pueda por colisión
        boolean pudoMover = true;
        boolean algunaVezFallo = false;
        while (pudoMover) {
            pudoMover = board.moverIzquierda();
            if (!pudoMover) algunaVezFallo = true; // acá detectamos la colisión lateral
        }
        assertTrue(algunaVezFallo, "La L debe dejar de moverse al colisionar lateralmente con el bloque fijo");

        // 3) Para reforzar: si intentamos seguir moviendo a la izquierda, debe seguir fallando
        assertFalse(board.moverIzquierda(), "No debería poder atravesar el bloque fijo lateral");
    }
}
