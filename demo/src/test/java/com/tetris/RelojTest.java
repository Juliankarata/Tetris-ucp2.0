// File: src/test/java/com/tetris/RelojTest.java
package com.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class RelojTest {

    @Test
    void sePuedeCrearUnReloj_es() {
        Reloj r = new Reloj();
        assertEquals(0, r.getTicks(), "Al iniciar, los ticks deben ser 0");
    }

    @Test
    void tickIncrementaEnUno() {
        Reloj r = new Reloj();
        //Se aplica el constructor, objeto reloj, y este mismo posee el componente de tick
        r.tick();
        assertEquals(1, r.getTicks(), "Después de un tick, debe valer 1");
        r.tick();
        assertEquals(2, r.getTicks(), "Después de dos ticks, debe valer 2");
    }

    @Test
    void resetVuelveACero() {
        Reloj r = new Reloj();
        r.tick(); r.tick(); r.tick();
        assertEquals(3, r.getTicks());
        r.reset();
        assertEquals(0, r.getTicks());
    }
}
