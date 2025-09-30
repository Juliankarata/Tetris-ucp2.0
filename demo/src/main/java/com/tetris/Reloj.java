// File: src/main/java/com/tetris/Reloj.java
package com.tetris;

/**
 * Reloj simple que cuenta "ticks" lógicos del juego.
 * - Cada tick representa un paso de simulación (no tiempo real).
 * - Requerimiento: poder crearlo, invocar tick() y consultar la cantidad.
 */
public class Reloj {
    // Contador de ticks acumulados desde que se creó o desde el último reset.
    private int ticks = 0;

    /** Avanza un tick. Incrementa el contador en +1. */
    public void tick() { 
        ticks++; 
    }

    /** Devuelve cuántos ticks transcurrieron. */
    public int getTicks() { 
        return ticks; 
    }

    /** Reinicia el contador de ticks. Útil para tests o reinicios de partida. */
    public void reset() { 
        ticks = 0; 
    }
}



// Reloj Qué es: un contador de ticks (pasos lógicos), no tiempo real. Qué hace: tick() suma +1 al contador. 
//getTicks() devuelve el total acumulado. reset() vuelve a 0 (útil para reiniciar o testear).
//Para qué se usa: para que el juego avance en pasos discretos; cada tick se intenta bajar la pieza una fila.
// Cómo funciona internamente: mantiene un entero ticks. 
//No hay temporizadores ni hilos: quien controle el bucle (tu main o tests) llama a avanzarTick() y eso a su vez llama a Reloj.tick(). 
//Invariantes importantes: ticks ≥ 0. Es idempotente por llamada (cada tick() suma exactamente 1).
