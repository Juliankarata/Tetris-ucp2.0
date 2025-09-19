package com.tetris;

import java.util.Random;

public class Juego {
    private final Tablero tablero;
    private final Reloj reloj;
    private boolean enEjecucion;
    private final Random random;

    public Juego(int ancho, int alto){
        this.tablero = new Tablero(ancho, alto);
        this.reloj = new Reloj();
        this.enEjecucion = false;
        this.random = new Random();
    }

    // Inicia el juego colocando la primera pieza
    public void iniciar(){
        enEjecucion = true;
        crearYColocarPiezaAleatoria();
    }

    // Avanza un tick: el reloj cuenta y la pieza cae una fila
    public void avanzarTick(){
        if(!enEjecucion) return;
        reloj.tick();
        if(tablero.obtenerPiezaActual() == null) {
            crearYColocarPiezaAleatoria();
        }
        tablero.moverAbajo();
    }

    // --- Métodos privados auxiliares ---
    private void crearYColocarPiezaAleatoria(){
        Pieza p = crearPiezaAleatoria();
        // rotación aleatoria antes de ponerla
        int rotaciones = random.nextInt(4);
        for(int i=0;i<rotaciones;i++) p.rotarDerecha();
        tablero.ponerPiezaActual(p);
    }

    private Pieza crearPiezaAleatoria(){
        int n = random.nextInt(7);
        switch(n){
            case 0: return new PiezaI();
            case 1: return new PiezaO();
            case 2: return new PiezaT();
            case 3: return new PiezaL();
            case 4: return new PiezaJ();
            case 5: return new PiezaS();
            default: return new PiezaZ();
        }
    }

    // --- Getters ---
    public Tablero getTablero(){ return tablero; }
    public Reloj getReloj(){ return reloj; }
    public boolean isEnEjecucion(){ return enEjecucion; }
}
