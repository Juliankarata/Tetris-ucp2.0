// File: src/main/java/com/tetris/Board.java
package com.tetris;

import java.util.Arrays;

public class Board {
    private final int ancho;
    private final int alto;
    private final boolean[][] grilla; // true = celda ocupada fija

    private Pieza piezaActual;
    private int piezaFila;     // fila superior donde está la pieza actual
    private int piezaColumna;  // columna izquierda donde está la pieza actual

    private int lineCount = 0; // líneas eliminadas acumuladas

    public Board(int ancho, int alto){
        if (ancho <= 0 || alto <= 0) throw new IllegalArgumentException("Tamaño inválido");
        this.ancho = ancho;
        this.alto = alto;
        this.grilla = new boolean[alto][ancho];
        for(int i=0; i<alto; i++) Arrays.fill(this.grilla[i], false);
    }

    public int getAncho(){ return ancho; }
    public int getAlto(){ return alto; }
    public int getLineCount(){ return lineCount; }

    /** Posición actual de la pieza (útil para tests/UI) */
    public int getPiezaFila(){ return piezaFila; }
    public int getPiezaColumna(){ return piezaColumna; }

    /** Representación en texto (grilla fija + pieza actual superpuesta) */
    public String formato(){
        StringBuilder sb = new StringBuilder();

        // Copia de la grilla
        boolean[][] temp = new boolean[alto][ancho];
        for (int r = 0; r < alto; r++) {
            System.arraycopy(grilla[r], 0, temp[r], 0, ancho);
        }

        // Superponer la pieza actual
        if (piezaActual != null) {
            boolean[][] forma = piezaActual.obtenerForma();
            for (int r = 0; r < forma.length; r++) {
                for (int c = 0; c < forma[0].length; c++) {
                    if (!forma[r][c]) continue;
                    int grFila = piezaFila + r;
                    int grCol  = piezaColumna + c;
                    if (grFila >= 0 && grFila < alto && grCol >= 0 && grCol < ancho) {
                        temp[grFila][grCol] = true;
                    }
                }
            }
        }

        // Dibujar
        for (int r = 0; r < alto; r++) {
            for (int c = 0; c < ancho; c++) {
                sb.append(temp[r][c] ? 'X' : '.');
            }
            if (r < alto - 1) sb.append('\n');
        }
        return sb.toString();
    }

    // ---------- Spawn de pieza ----------
    /** Intenta poner la pieza actual centrada arriba. Devuelve si pudo. */
    public boolean tryPonerPiezaActual(Pieza pieza) {
        if (pieza == null) return false;
        this.piezaActual = pieza;
        boolean[][] forma = pieza.obtenerForma();
        int anchoForma = forma[0].length;

        this.piezaFila = 0;
        this.piezaColumna = Math.max(0, (ancho - anchoForma) / 2);

        if (!puedeEn(piezaFila, piezaColumna, piezaActual)) {
            this.piezaActual = null; // revertir
            return false;
        }
        return true;
    }

    /** Pone la pieza actual o lanza excepción (game over). */
    public void ponerPiezaActual(Pieza pieza){
        if (!tryPonerPiezaActual(pieza)) {
            throw new IllegalStateException("No hay espacio para spawnear la pieza (game over).");
        }
    }

    public Pieza obtenerPiezaActual(){ return piezaActual; }

    // ---------- Movimiento vertical ----------
    /** Baja la pieza 1 celda. Si no puede, la fija y limpia líneas. */
    public boolean moverAbajo(){
        if(piezaActual == null) return false;
        if(puedeMoverAbajo()){
            piezaFila++;
            return true;
        } else {
            fijarPiezaEnGrilla();
            int eliminadas = eliminarLineasCompletas();
            lineCount += eliminadas;
            piezaActual = null;
            return false;
        }
    }

    public boolean puedeMoverAbajo(){
        if (piezaActual == null) return false;
        return puedeEn(piezaFila + 1, piezaColumna, piezaActual);
    }

    // ---------- Movimiento lateral ----------
    public boolean moverIzquierda(){
        if (piezaActual == null) return false;
        if (puedeEn(piezaFila, piezaColumna - 1, piezaActual)) {
            piezaColumna--;
            return true;
        }
        return false;
    }

    public boolean moverDerecha(){
        if (piezaActual == null) return false;
        if (puedeEn(piezaFila, piezaColumna + 1, piezaActual)) {
            piezaColumna++;
            return true;
        }
        return false;
    }

    // ---------- Rotación ----------
    /** Rotación segura con wall-kicks simples (0, -1, +1, -2, +2). */
    public boolean rotarPiezaActualDerecha() {
        return rotarConKicks(true);
    }

    public boolean rotarPiezaActualIzquierda() {
        return rotarConKicks(false);
    }

    /** Conserva métodos con nombre original para compatibilidad */
    public boolean rotarActualDerecha(){ return rotarPiezaActualDerecha(); }
    public boolean rotarActualIzquierda(){ return rotarPiezaActualIzquierda(); }

    private boolean rotarConKicks(boolean derecha){
        if (piezaActual == null) return false;

        // aplicar rotación
        if (derecha) piezaActual.rotarDerecha(); else piezaActual.rotarIzquierda();

        int[] dx = {0, -1, 1, -2, 2}; // wall-kicks simples en X
        for (int d : dx) {
            if (puedeEn(piezaFila, piezaColumna + d, piezaActual)) {
                piezaColumna += d;
                return true;
            }
        }

        // revertir si no entró
        if (derecha) piezaActual.rotarIzquierda(); else piezaActual.rotarDerecha();
        return false;
    }

    // ---------- Validación de encaje ----------
    private boolean puedeEn(int nuevaFila, int nuevaCol, Pieza pieza){
        boolean[][] forma = pieza.obtenerForma();
        int filas = forma.length;
        int cols  = forma[0].length;

        for(int r=0; r<filas; r++){
            for(int c=0; c<cols; c++){
                if(!forma[r][c]) continue;
                int grFila = nuevaFila + r;
                int grCol  = nuevaCol + c;
                // límites
                if(grFila < 0 || grFila >= alto || grCol < 0 || grCol >= ancho) return false;
                // colisión con bloque fijo
                if(grilla[grFila][grCol]) return false;
            }
        }
        return true;
    }

    // ---------- Fijar pieza y limpieza ----------
    private void fijarPiezaEnGrilla(){
        if (piezaActual == null) return;
        boolean[][] forma = piezaActual.obtenerForma();
        for(int r=0; r<forma.length; r++){
            for(int c=0; c<forma[0].length; c++){
                if(!forma[r][c]) continue;
                int grFila = piezaFila + r;
                int grCol  = piezaColumna + c;
                if(grFila >= 0 && grFila < alto && grCol >= 0 && grCol < ancho){
                    grilla[grFila][grCol] = true;
                }
            }
        }
    }

    /** Elimina todas las líneas completas y devuelve cuántas fueron (no acumula). */
    public int eliminarLineasCompletas() {
        int eliminadas = 0;

        for (int r = alto - 1; r >= 0; r--) {
            boolean completa = true;
            for (int c = 0; c < ancho; c++) {
                if (!grilla[r][c]) { completa = false; break; }
            }
            if (completa) {
                eliminadas++;
                // bajar todo lo de arriba una fila
                for (int rr = r; rr > 0; rr--) {
                    System.arraycopy(grilla[rr - 1], 0, grilla[rr], 0, ancho);
                }
                // fila superior vacía
                Arrays.fill(grilla[0], false);
                r++; // re-chequear esta misma fila luego del corrimiento
            }
        }
        return eliminadas;
    }

    // ---------- Utilidades para tests ----------
    /** Carga un estado inicial validando dimensiones (deep copy). */
    public void establecerTablero(boolean[][] tableroInicial) {
        if (tableroInicial == null || tableroInicial.length != alto) {
            throw new IllegalArgumentException("Alto inválido para el estado inicial");
        }
        for (int r = 0; r < alto; r++) {
            if (tableroInicial[r] == null || tableroInicial[r].length != ancho) {
                throw new IllegalArgumentException("Ancho inválido en la fila " + r);
            }
            System.arraycopy(tableroInicial[r], 0, grilla[r], 0, ancho);
        }
    }

    /** Devuelve una copia de la grilla fija (deep copy). */
    public boolean[][] obtenerTablero() {
        boolean[][] copia = new boolean[alto][ancho];
        for (int r = 0; r < alto; r++) {
            System.arraycopy(grilla[r], 0, copia[r], 0, ancho);
        }
        return copia;
    }
}
