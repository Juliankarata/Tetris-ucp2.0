
package com.tetris;

/**
 * Reloj lógico simple.
 * - Cada tick() avanza un paso en la simulación.
 * - No mide tiempo real, solo cuenta "pasos" lógicos.
 */
public class Reloj {
    private int ticks = 0; // contador de ticks acumulados

    /** Avanza un tick (suma +1 al contador). */
    public void tick() {
        ticks++;
    }

    /** Devuelve la cantidad de ticks transcurridos. */
    public int getTicks() {
        return ticks;
    }

    /** Reinicia el contador a cero. */
    public void reset() {
        ticks = 0;
    }
}
