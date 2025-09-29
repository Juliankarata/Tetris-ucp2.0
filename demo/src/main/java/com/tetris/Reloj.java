// File: src/main/java/com/tetris/Reloj.java
package com.tetris;

/**
 * Reloj simple que solo cuenta ticks lógicos.
 */
public class Reloj {
    private int ticks = 0;

    /** Avanza un tick. */
    public void tick() { ticks++; }

    /** Devuelve la cantidad de ticks transcurridos. */
    public int getTicks() { return ticks; }

    /** Reinicia el contador (útil en tests/reinicios). */
    public void reset() { ticks = 0; }
}
