// File: src/main/java/com/tetris/PieceSquare.java
package com.tetris;

/**
 * Pieza O (Cuadrado) — 2x2 llena.
 * - No cambia con la orientación (es simétrica).
 */
public class PieceSquare extends Pieza {
    public PieceSquare(){ 
        super(2,2); 
    }

    @Override
    public boolean[][] obtenerForma(){
        // 2x2 completamente ocupada
        boolean[][] base = new boolean[2][2];
        base[0][0] = true; base[0][1] = true;
        base[1][0] = true; base[1][1] = true;
        return base;
    }
}


//PieceSquare (O / Cuadrado)

//Qué es: la pieza O, un bloque 2×2.

//Cómo funciona: obtenerForma() devuelve siempre una matriz 2×2 llena de true. No se ve afectada por la orientación (es simétrica).

//Rol en el juego: es la pieza más estable; su colisión y rotación son triviales. Útil para pruebas básicas.