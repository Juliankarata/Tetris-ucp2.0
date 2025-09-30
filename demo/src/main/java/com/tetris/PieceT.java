// File: src/main/java/com/tetris/PieceT.java
package com.tetris;

public class PieceT extends Pieza {
    public PieceT(){ super(3,3); }

    @Override
    public boolean[][] obtenerForma(){
        boolean[][] b = new boolean[3][3];
        switch(orientacion){
            case 0: // T con base abajo
                b[0][1] = true;
                b[1][0] = b[1][1] = b[1][2] = true;
                break;
            case 1: // base a la izquierda
                b[0][0] = b[1][0] = b[2][0] = true;
                b[1][1] = true;
                break;
            case 2: // base arriba
                b[2][1] = true;
                b[1][0] = b[1][1] = b[1][2] = true;
                break;
            case 3: // base a la derecha
                b[0][1] = b[1][1] = b[2][1] = true;
                b[1][0] = true;
                break;
        }
        return b;
    }
}

//PieceT (T)

//Qué es: la pieza T en una caja 3×3.

//Cómo funciona: según orientacion, coloca tres bloques en línea (la “base”) 
//y uno centrado por encima/derecha/izquierda según el caso. Las cuatro orientaciones están contempladas.

//Notas: es útil para testear que rotación y encaje se comportan bien, porque su forma no es simétrica como el cuadrado.