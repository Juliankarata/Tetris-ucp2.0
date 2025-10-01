// File: src/main/java/com/tetris/PieceSquare.java
package com.tetris;

/**
 * Pieza O (cuadrado).
 * Bounding box de 2x2 totalmente llena.
 *
 * Orientaciones:
 * - Todas (0,1,2,3): idénticas, porque es simétrica.
 */
public class PieceSquare extends Pieza {
    public PieceSquare(){ super(2,2); }

    @Override
    public boolean[][] obtenerForma() {
        return new boolean[][] {
            {true, true},
            {true, true}
        };
    }
}
