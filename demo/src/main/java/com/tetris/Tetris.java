package com.tetris;

import java.util.Random;

/**
 * Fachada principal del juego (Tetris + Juego unificados).
 * Expone: start(), tick(), rotarIzquierda(), rotarDerecha(), moveLeft(), moveRight(), state().
 * Permite inyección de Board/Reloj/Random para tests.
 */
public class Tetris {

    // --- Dependencias y estado ---
    private final Board board;
    private final Reloj reloj;
    private final Random random;
    private boolean enEjecucion;

    // --- Constructores ---
    /** Constructor normal: crea Board/Reloj/Random por defecto. */
    public Tetris(int ancho, int alto) {
        this(new Board(ancho, alto), new Reloj(), new Random());
    }

    /** Constructor para tests: inyecta Board/Reloj/Random determinísticos. */
    public Tetris(Board board, Reloj reloj, Random rnd) {
        if (board == null || reloj == null || rnd == null)
            throw new IllegalArgumentException("Dependencias nulas en Tetris");
        this.board = board;
        this.reloj = reloj;
        this.random = rnd;
        this.enEjecucion = false;
    }

    // --- Ciclo de vida ---
    /** Inicia el juego (spawnea primera pieza si hay espacio). */
    public void start() { iniciar(); }
    public void iniciar() {
        if (enEjecucion) return;
        enEjecucion = true;
        reloj.reset();
        if (!crearYColocarPiezaAleatoria()) {
            enEjecucion = false; // game over instantáneo si no entra
        }
    }

    public void finalizar() { enEjecucion = false; }

    // --- Avance del juego ---
    /** Avanza un paso lógico del juego. */
    public void tick() { avanzarTick(); }
    public void avanzarTick() {
        if (!enEjecucion) return;
        reloj.tick();

        if (board.obtenerPiezaActual() == null) {
            if (!crearYColocarPiezaAleatoria()) {
                enEjecucion = false; // no entra → game over
            }
            return; // no se aplica gravedad en el mismo tick del spawn
        }

        // gravedad: si no baja, el Board la fija y limpia líneas
        board.moverAbajo();
    }

    // --- Controles ---
    public boolean rotarIzquierda()  { return board.rotarActualIzquierda(); }
    public boolean rotarDerecha()    { return board.rotarActualDerecha(); }
    public boolean moveLeft()        { return board.moverIzquierda(); }
    public boolean moveRight()       { return board.moverDerecha(); }

    // --- Estado para UI/tests ---
    public Estado state() {
        boolean[][] grillaCopia = board.obtenerTablero();  // copia defensiva
        Pieza pieza = board.obtenerPiezaActual();          // puede ser null
        int lineCount = board.getLineCount();
        int ticks = reloj.getTicks();
        return new Estado(grillaCopia, pieza, lineCount, ticks, enEjecucion);
    }

    public static record Estado(
        boolean[][] grilla,
        Pieza piezaActual,
        int lineCount,
        int ticks,
        boolean enEjecucion
    ) {}

    // --- Generación de piezas ---
    /** Método público útil para tests de aleatoriedad. */
    public Pieza getRandomPiece() { return crearPiezaAleatoria(); }

    private boolean crearYColocarPiezaAleatoria() {
        Pieza p = crearPiezaAleatoria();
        // Rotación aleatoria 0..3
        int rotaciones = random.nextInt(4);
        for (int i = 0; i < rotaciones; i++) p.rotarDerecha();
        // Usamos la versión "try" para detectar game over
        return board.tryPonerPiezaActual(p);
    }

    private Pieza crearPiezaAleatoria() {
        // Selección simple: I, O, T, L, Z (Perro)
        switch (random.nextInt(5)) {
            case 0: return new PieceStick();   // I (palo)
            case 1: return new PieceSquare();  // O (cuadrado)
            case 2: return new PieceT();       // T
            case 3: return new PieceL();       // L
            default: return new PieceDog();    // Z (perro)
        }
    }

    // --- Getters útiles (compatibilidad) ---
    public Board getTablero()       { return board; }
    public Reloj getReloj()         { return reloj; }
    public boolean isEnEjecucion()  { return enEjecucion; }
}
