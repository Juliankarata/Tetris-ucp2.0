package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PieceOrientationsTest {
    @Test
    void paloTodasLasOrientaciones() {
        PieceStick palo = new PieceStick();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = palo.obtenerForma();
            assertNotNull(forma);
            palo.rotarDerecha();
        }
    }
    @Test
    void cuadradoTodasLasOrientaciones() {
        PieceSquare cuadrado = new PieceSquare();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = cuadrado.obtenerForma();
            assertNotNull(forma);
            cuadrado.rotarDerecha();
        }
    }
    @Test
    void tTodasLasOrientaciones() {
        PieceT t = new PieceT();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = t.obtenerForma();
            assertNotNull(forma);
            t.rotarDerecha();
        }
    }
    @Test
    void lTodasLasOrientaciones() {
        PieceL l = new PieceL();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = l.obtenerForma();
            assertNotNull(forma);
            l.rotarDerecha();
        }
    }
    @Test
    void perroTodasLasOrientaciones() {
        PieceDog perro = new PieceDog();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = perro.obtenerForma();
            assertNotNull(forma);
            perro.rotarDerecha();
        }
    }
}
