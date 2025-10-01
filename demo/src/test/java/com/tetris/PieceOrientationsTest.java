package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PieceOrientationsTest {
    @Test
    void pieceStickTodasLasOrientaciones() {
        PieceStick pieceStick = new PieceStick();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = pieceStick.obtenerForma();
            assertNotNull(forma);
            pieceStick.rotarDerecha();
        }
    }
    @Test
    void squareTodasLasOrientaciones() {
        PieceSquare square = new PieceSquare();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = square.obtenerForma();
            assertNotNull(forma);
            square.rotarDerecha();
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
    void piecedogTodasLasOrientaciones() {
        PieceDog piecedog = new PieceDog();
        for (int i = 0; i < 4; i++) {
            boolean[][] forma = piecedog.obtenerForma();
            assertNotNull(forma);
            piecedog.rotarDerecha();
        }
    }
}
