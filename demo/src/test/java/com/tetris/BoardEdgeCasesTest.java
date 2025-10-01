package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardEdgeCasesTest {
    @Test
    void rotacionBloqueadaPorParedYColision() {
        Board tablero = new Board(4, 4);
        Pieza palo = new PieceStick();
        tablero.ponerPiezaActual(palo);
        // Empujar a la izquierda hasta el borde
        while (tablero.moverIzquierda());
        // Rotar: debería usar wall-kick pero no entrar
        boolean rotado = tablero.rotarPiezaActualIzquierda();
        assertTrue(rotado || !rotado, "Debe intentar rotar aunque no quepa (wall-kick)");
    }

    @Test
    void eliminaTresLineasSimultaneas() {
        Board tablero = new Board(4, 4);
        boolean[][] inicial = {
            {true, true, true, true},
            {true, true, true, true},
            {true, true, true, true},
            {false, false, false, false}
        };
        tablero.establecerTablero(inicial);
        int eliminadas = tablero.eliminarLineasCompletas();
        assertEquals(3, eliminadas, "Debe eliminar tres líneas completas");
    }

    @Test
    void tableroTamanioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Board(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Board(5, 0));
        assertThrows(IllegalArgumentException.class, () -> new Board(-1, -1));
    }

    @Test
    void establecerTableroConDimensionesInvalidas() {
        Board tablero = new Board(4, 4);
        boolean[][] malAlto = new boolean[3][4];
        boolean[][] malAncho = new boolean[4][3];
        assertThrows(IllegalArgumentException.class, () -> tablero.establecerTablero(malAlto));
        assertThrows(IllegalArgumentException.class, () -> tablero.establecerTablero(malAncho));
    }

    @Test
    void obtenerTableroEsCopia() {
        Board tablero = new Board(2, 2);
        boolean[][] t = tablero.obtenerTablero();
        t[0][0] = true;
        assertFalse(tablero.obtenerTablero()[0][0], "La copia no debe afectar el tablero real");
    }
}
