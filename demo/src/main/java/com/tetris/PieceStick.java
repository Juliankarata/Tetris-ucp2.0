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

//PieceStick (I / Palo)

//Qué es: la pieza I, representada dentro de una caja 4×4 para que la rotación sea cómoda.

//Cómo funciona:

//Orientaciones 0 y 2: horizontal (cuatro true en la segunda fila de la caja).

//Orientaciones 1 y 3: vertical (cuatro true en la tercera columna).

//Por qué 4×4: usar una caja más grande evita “recortes” al rotar y simplifica los chequeos de encaje en el tablero (el “dibujo” siempre cabe en su caja sin desbordar).