package com.tetris;


public abstract class Pieza implements IRotator {
    protected int filas;
    protected int columnas;
    protected int orientacion;

    protected Pieza(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        this.orientacion = 0;
    }

    public abstract boolean[][] obtenerForma();

    @Override
    public void rotarDerecha(){ orientacion = (orientacion + 1) % 4; }

    @Override
    public void rotarIzquierda(){ orientacion = (orientacion + 3) % 4; }

    public int getOrientacion(){ return orientacion; }
}
