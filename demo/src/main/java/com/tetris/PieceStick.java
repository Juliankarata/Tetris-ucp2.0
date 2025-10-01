// File: src/main/java/com/tetris/PieceStick.java
package com.tetris;

/**
 * Pieza I (stick o palo).
 * Bounding box de 4x4 para que la rotación sea simétrica.
 *
 * Orientaciones:
 * - 0 y 2: vertical
 *   [ ][X][ ][ ]
 *   [ ][X][ ][ ]
 *   [ ][X][ ][ ]
 *   [ ][X][ ][ ]
 *
 * - 1 y 3: horizontal
 *   [ ][ ][ ][ ]
 *   [X][X][X][X]
 *   [ ][ ][ ][ ]
 *   [ ][ ][ ][ ]
 */
public class PieceStick extends Pieza {
    public PieceStick(){ super(4,4); } // bounding box 4x4

    @Override
    public boolean[][] obtenerForma() {
        boolean[][] m = new boolean[4][4];
        if (orientacion % 2 == 0) { // vertical
            m[0][1] = m[1][1] = m[2][1] = m[3][1] = true;
        } else { // horizontal
            m[1][0] = m[1][1] = m[1][2] = m[1][3] = true;
        }
        return m;
    }
}
