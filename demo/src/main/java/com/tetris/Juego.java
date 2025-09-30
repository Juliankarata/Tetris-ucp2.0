// File: src/main/java/com/tetris/Juego.java
package com.tetris;

import java.util.Random;

public class Juego {
    private final Board tablero;
    private final Reloj reloj;
    private boolean enEjecucion;
    private final Random random;

    public Juego(int ancho, int alto){
        this(new Board(ancho, alto), new Reloj(), new Random());
    }

    // Sobrecarga útil para tests (inyectar Random determinístico)
    public Juego(Board tablero, Reloj reloj, Random random){
        this.tablero = tablero;
        this.reloj = reloj;
        this.random = random;
        this.enEjecucion = false;
    }

    public void iniciar(){
        enEjecucion = true;
        crearYColocarPiezaAleatoria();
    }

    public void avanzarTick(){
        if(!enEjecucion) return;
        reloj.tick();
        if(tablero.obtenerPiezaActual() == null) {
            crearYColocarPiezaAleatoria();
        }
        tablero.moverAbajo();
    }

    private void crearYColocarPiezaAleatoria(){
        Pieza p = crearPiezaAleatoria();
        // Rotación aleatoria 0..3
        int rotaciones = random.nextInt(4);
        for(int i=0; i<rotaciones; i++) p.rotarDerecha();
        tablero.ponerPiezaActual(p);
    }

    private Pieza crearPiezaAleatoria(){
        // SOLO 5 PIEZAS: I, O, T, L, Z (Perro)
        switch(random.nextInt(5)){
            case 0: return new PieceStick();   // I (palo)
            case 1: return new PieceSquare();  // O (cuadrado)
            case 2: return new PieceT();       // T
            case 3: return new PieceL();       // L
            default: return new PieceDog();    // Z (perro)
        }
    }

    public Board getTablero(){ return tablero; }
    public Reloj getReloj(){ return reloj; }
    public boolean isEnEjecucion(){ return enEjecucion; }
}



//Juego


//Qué es: el orquestador del bucle del juego.

//Estado interno:

//Board tablero: donde se juega.

//Reloj reloj: cuenta los ticks.

//boolean enEjecucion: si está “corriendo”.

//Random random: para aleatoriedad controlada (útil inyectarlo en tests).

//Qué hace:

//iniciar(): marca enEjecucion = true y crea/coloca la primera pieza.

//avanzarTick(): si está en ejecución:

//reloj.tick()

//si no hay pieza activa (la anterior se fijó): crea una nueva

//pide al Board bajar la pieza (moverAbajo())

//Creación de piezas:

//crearPiezaAleatoria(): elige entre 5 tipos (I/O/T/L/Z-perro).

//crearYColocarPiezaAleatoria(): además del tipo, aplica rotación aleatoria (0..3) antes de llamar a tablero.ponerPiezaActual(...).

//Para qué se usa: encapsula el “loop” del juego y deja al tablero la física/colisión. De esta forma, Juego queda muy legible: “sumar tick, asegurar pieza, pedir que baje”.

//Cómo testearlo fácil:

//Inyectá un Random con semilla fija y verificá que:

//iniciar() deja una pieza activa.

//avanzarTick() incrementa reloj y, eventualmente, cuando la pieza se fija, spawnea otra.