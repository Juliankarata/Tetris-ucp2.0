// File: src/main/java/com/tetris/PieceL.java
package com.tetris;


public class PieceL extends Pieza {
    public PieceL(){ super(3,3); }

    @Override
    public boolean[][] obtenerForma() {
        boolean[][] b = new boolean[3][3];
        switch (orientacion) {
            case 0: // 
                b[0][2] = true;
                b[1][0] = b[1][1] = b[1][2] = true;
                break;
            case 1: // 
                b[0][0] = b[1][0] = b[2][0] = true;
                b[2][1] = true;
                break;
            case 2: 
                b[1][0] = b[1][1] = b[1][2] = true;
                b[2][0] = true;
                break;
            case 3: 
                b[0][1] = true;
                b[1][1] = true;
                b[2][1] = b[2][2] = true;
                break;
        }
        return b;
    }
}
