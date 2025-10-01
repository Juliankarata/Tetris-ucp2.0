// File: src/main/java/com/tetris/Juego.java
package com.tetris;

import java.util.Random;

/**
 * Fachada del juego: orquesta reloj, tablero y generación de piezas.
 * - iniciar()/finalizar(): control del ciclo de juego
 * - avanzarTick(): aplica gravedad por tick
 * - rotateLeft/Right(): delega en Board con validación
 * - getRandomPiece(): útil para tests con Random inyectable
 */
public class Juego {
    private final Board tablero;
    private final Reloj reloj;
    private final Random random;

    private boolean enEjecucion;

    // Constructor principal (inyectable para tests)
    public Juego(Board tablero, Reloj reloj, Random random){
        if (tablero == null || reloj == null || random == null)
            throw new IllegalArgumentException("Dependencias nulas en Juego");
        this.tablero = tablero;
        this.reloj = reloj;
        this.random = random;
        this.enEjecucion = false;
    }

    // Conveniencia: crea dependencias por defecto
    public Juego(int ancho, int alto){
        this(new Board(ancho, alto), new Reloj(), new Random());
    }

    // ---------- Ciclo de vida ----------
    public void iniciar(){
        if (enEjecucion) return;
        enEjecucion = true;
        reloj.reset();
        // Spawnear primera pieza; si no hay espacio, termina el juego
        if (!crearYColocarPiezaAleatoria()) {
            enEjecucion = false; // game over instantáneo
        }
    }

    public void finalizar(){
        enEjecucion = false;
    }

    /** Alias por compatibilidad con el UML si lo necesitás */
    public void start(){ iniciar(); }

    // ---------- Avance del juego ----------
    /**
     * Avanza un "tick" lógico:
     * - si no hay pieza activa, intenta spawnear una nueva
     * - si hay pieza, intenta bajar una fila (gravedad)
     *   (si no puede, Board la fija y limpia líneas; el spawn se hará en el próximo tick)
     */
    public void avanzarTick(){
        if(!enEjecucion) return;
        reloj.tick();

        if (tablero.obtenerPiezaActual() == null) {
            // Intentar spawnear nueva pieza; si no entra, game over
            if (!crearYColocarPiezaAleatoria()) {
                enEjecucion = false;
            }
            return; // no bajamos en el mismo tick del spawn
        }

        // Hay pieza activa: gravedad
        boolean bajo = tablero.moverAbajo();
        // Si no bajó, Board la fijó; el próximo tick spawneará otra
        // (no hacemos nada más aquí)
    }

    /** Alias por compatibilidad con el UML */
    public void tick(){ avanzarTick(); }

    // ---------- Controles ----------
    public boolean rotateLeft(){
        return tablero.rotarActualIzquierda();
    }

    public boolean rotateRight(){
        return tablero.rotarActualDerecha();
    }

    public boolean moveLeft(){
        return tablero.moverIzquierda();
    }

    public boolean moveRight(){
        return tablero.moverDerecha();
    }

    // ---------- Generación de piezas ----------
    private boolean crearYColocarPiezaAleatoria(){
        Pieza p = crearPiezaAleatoria();
        // Rotación aleatoria 0..3
        int rotaciones = random.nextInt(4);
        for(int i=0; i<rotaciones; i++) p.rotarDerecha();
        // Usamos la versión "try" para detectar game over
        return tablero.tryPonerPiezaActual(p);
    }

    private Pieza crearPiezaAleatoria(){
        // Selección simple: I, O, T, L, Z (Perro)
        switch(random.nextInt(5)){
            case 0: return new PieceStick();   // I (palo)
            case 1: return new PieceSquare();  // O (cuadrado)
            case 2: return new PieceT();       // T
            case 3: return new PieceL();       // L
            default: return new PieceDog();    // Z (perro)
        }
    }

    /** Método público útil para tests de aleatoriedad */
    public Pieza getRandomPiece(){
        return crearPiezaAleatoria();
    }

    // ---------- Getters ----------
    public Board getTablero(){ return tablero; }
    public Reloj getReloj(){ return reloj; }
    public boolean isEnEjecucion(){ return enEjecucion; }
}
