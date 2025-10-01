
package com.tetris;

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
