package com.tetris;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RelojTest {

    @Test
    public void testConstructor() {
        Reloj reloj = new Reloj();
        assertEquals(0, reloj.getContador());
    }

    @Test
    public void testTick() {
        Reloj reloj = new Reloj();
        reloj.tick();
        assertEquals(1, reloj.getContador());
        reloj.tick();
        assertEquals(2, reloj.getContador());
    }
}
