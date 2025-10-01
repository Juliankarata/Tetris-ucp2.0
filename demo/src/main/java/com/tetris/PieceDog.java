// File: src/main/java/com/tetris/PieceDog.java
package com.tetris;

/**
 * Pieza Z (apodada "perro").
 * Bounding box de 3x3.
 *
 * Orientaciones:
 * - 0 y 2: Z horizontal
 *   [ ][X][X]
 *   [X][X][ ]
 *
 * - 1 y 3: Z vertical
 *   [X][ ]
 *   [X][X]
 *   [ ][X]
 */
public class PieceDog extends Pieza {
    public PieceDog(){ super(3,3); }

    @Override
    public boolean[][] obtenerForma(){
        boolean[][] forma = new boolean[3][3];
        switch(orientacion){
            case 0:
            case 2: // horizontal
                forma[0][1] = true;
                forma[0][2] = true;
                forma[1][0] = true;
                forma[1][1] = true;
                break;
            case 1:
            case 3: // vertical
                forma[0][0] = true;
                forma[1][0] = true;
                forma[1][1] = true;
                forma[2][1] = true;
                break;
        }
        return forma;
    }
}
