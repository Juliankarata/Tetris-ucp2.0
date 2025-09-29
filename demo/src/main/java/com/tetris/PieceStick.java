// File: src/main/java/com/tetris/PieceStick.java
package com.tetris;

public class PieceStick extends Pieza {
    public PieceStick(){ super(4,4); }

    @Override
    public boolean[][] obtenerForma(){
        boolean[][] base = new boolean[4][4];
        switch(orientacion){
            case 0:
            case 2: // horizontal
                base[1][0] = base[1][1] = base[1][2] = base[1][3] = true;
                break;
            case 1:
            case 3: // vertical
                base[0][2] = base[1][2] = base[2][2] = base[3][2] = true;
                break;
        }
        return base;
    }
}
