// File: src/main/java/com/tetris/PieceT.java
package com.tetris;


public class PieceT extends Pieza {
    public PieceT(){ super(3,3); }

    @Override
    public boolean[][] obtenerForma(){
        boolean[][] b = new boolean[3][3];
        switch(orientacion){
            case 0: // base abajo
                b[0][1] = true;
                b[1][0] = b[1][1] = b[1][2] = true;
                break;
            case 1: // base izquierda
                b[0][0] = b[1][0] = b[2][0] = true;
                b[1][1] = true;
                break;
            case 2: // base arriba
                b[2][1] = true;
                b[1][0] = b[1][1] = b[1][2] = true;
                break;
            case 3: // base derecha
                b[0][1] = true;
                b[1][1] = true;
                b[2][1] = true;
                b[1][0] = true;
                break;
        }
        return b;
    }
}
