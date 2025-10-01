package com.tetris;

import java.util.Random;


public class Tetris {
    private final Juego juego;

    /** Constructor normal: crea Board/Reloj/Random por defecto. */
    public Tetris(int ancho, int alto) {
        this.juego = new Juego(ancho, alto);
    }

    /** Constructor para tests: inyecta Board/Reloj/Random determinísticos. */
    public Tetris(Board board, Reloj reloj, Random rnd) {
        this.juego = new Juego(board, reloj, rnd);
    }

    /** Inicia el juego (spawnea primera pieza si hay espacio). */
    public void start() {
        juego.iniciar();
    }

    /** Avanza un paso lógico del juego. */
    public void tick() {
        juego.avanzarTick();
    }

    /** Rota pieza activa a la izquierda. */
    public void rotateLeft() {
        juego.rotateLeft();
    }

    /** Rota pieza activa a la derecha. */
    public void rotateRight() {
        juego.rotateRight();
    }

    /** Devuelve snapshot del estado actual (con copia defensiva de la grilla). */
    public Estado state() {
        boolean[][] grillaCopia = juego.getTablero().obtenerTablero(); // copia defensiva
        Pieza pieza = juego.getTablero().obtenerPiezaActual();         // puede ser null
        int lineCount = juego.getTablero().getLineCount();
        int ticks = juego.getReloj().getTicks();                        // usamos int, como pediste
        boolean enEjecucion = juego.isEnEjecucion();

        return new Estado(grillaCopia, pieza, lineCount, ticks, enEjecucion);
    }

    /** Record de estado para UI/tests. */
    public static record Estado(
        boolean[][] grilla,
        Pieza piezaActual,
        int lineCount,
        int ticks,
        boolean enEjecucion
    ) {}
}
