package com.tetris;

public class PiezaZ extends Pieza {
    public PiezaZ(){
        super(3,3);
    }

    @Override
    public boolean[][] obtenerForma(){
        boolean[][] base = new boolean[3][3];
        switch(orientacion){
            case 0:
            case 2: // horizontal
                base[0][0] = true; base[0][1] = true;
                base[1][1] = true; base[1][2] = true;
                break;
            case 1:
            case 3: // vertical
                base[0][1] = true;
                base[1][0] = true; base[1][1] = true;
                base[2][0] = true;
                break;
        }
        return base;
    }
}
