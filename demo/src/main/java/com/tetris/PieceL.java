// File: src/main/java/com/tetris/PieceL.java
package com.tetris;

public class PieceL extends Pieza {
    public PieceL(){ super(3,3); }

    @Override
    public boolean[][] obtenerForma() {
        boolean[][] b = new boolean[3][3];
        switch (orientacion) {
            case 0: // ┘ con base abajo
                b[0][2] = true;
                b[1][0] = b[1][1] = b[1][2] = true;
                break;
            case 1: // └ base a la izquierda
                b[0][0] = b[1][0] = b[2][0] = true;
                b[2][1] = true;
                break;
            case 2: // ┌ base arriba
                b[1][0] = b[1][1] = b[1][2] = true;
                b[2][0] = true;
                break;
            case 3: // ┐ base a la derecha
                b[0][1] = true;
                b[1][1] = true;
                b[2][1] = b[2][2] = true;
                break;
        }
        return b;
    }
}


//PieceL (L)

//Qué es: la pieza en “L”, caja 3×3.

//Cómo funciona: combina una barra de 3 bloques con un bloque adicional en un extremo, rotando por las cuatro orientaciones.

//Rol en tests: ayuda a verificar que rotaciones y movimientos laterales no atraviesen bordes ni celdas ocupadas.