package com.tetris;

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
