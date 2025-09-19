// File: src/main/java/com/tetris/PiezaJ.java
package com.tetris;

public class PiezaJ extends Pieza {
    public PiezaJ(){
        super(3,3);
    }

    @Override
    public boolean[][] obtenerForma(){
        boolean[][] base = new boolean[3][3];
        switch(orientacion){
            case 0: // J normal
                base[0][1] = true;
                base[1][1] = true;
                base[2][1] = true; base[2][0] = true;
                break;
            case 1: // J rotada 90
                base[1][0] = true;
                base[1][1] = true; base[1][2] = true;
                base[0][2] = true;
                break;
            case 2: // J rotada 180
                base[0][0] = true; base[0][1] = true;
                base[1][0] = true;
                base[2][0] = true;
                break;
            case 3: // J rotada 270
                base[0][0] = true;
                base[1][0] = true; base[1][1] = true; base[1][2] = true;
                break;
        }
        return base;
    }
}
