// File: src/main/java/com/tetris/Reloj.java
package com.tetris;

public class Reloj {
    private int contadorTicks = 0;

    // Avanza un tick
    public void tick() {
        contadorTicks++;
    }

    // Devuelve la cantidad de ticks transcurridos
    public int getContador() {
        return contadorTicks;
    }
}
