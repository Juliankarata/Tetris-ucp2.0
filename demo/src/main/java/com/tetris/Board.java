// File: src/main/java/com/tetris/Board.java
package com.tetris;

import java.util.Arrays;

public class Board {
    private final int ancho;
    private final int alto;
    private boolean[][] grilla; // true = celda ocupada
    private Pieza piezaActual;
    private int piezaFila;     // fila superior donde está la pieza actual
    private int piezaColumna;  // columna izquierda donde está la pieza actual

    public Board(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
        this.grilla = new boolean[alto][ancho];
        for(int i=0; i<alto; i++) Arrays.fill(this.grilla[i], false);
    }

    public int getAncho(){ return ancho; }
    public int getAlto(){ return alto; }

    // Representación en texto (grilla fija + pieza actual)
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

    // Coloca una nueva pieza en el tablero (centrada arriba)
    public void ponerPiezaActual(Pieza pieza){
        this.piezaActual = pieza;
        this.piezaFila = 0;
        this.piezaColumna = Math.max(0, (ancho - pieza.obtenerForma()[0].length) / 2);
        if (!puedeEn(piezaFila, piezaColumna, piezaActual)) {
            throw new IllegalStateException("No hay espacio para spawnear la pieza (game over).");
        }
    }

    public Pieza obtenerPiezaActual(){ return piezaActual; }

    // --------- Movimiento vertical ----------
    public boolean moverAbajo(){
        if(piezaActual == null) return false;
        if(puedeMoverAbajo()){
            piezaFila++;
            return true;
        } else {
            fijarPiezaEnGrilla();
            eliminarLineasCompletas();
            piezaActual = null;
            return false;
        }
    }

    public boolean puedeMoverAbajo(){
        return puedeEn(piezaFila + 1, piezaColumna, piezaActual);
    }

    // --------- Movimiento lateral ----------
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

    // --------- Rotación segura (revierte si no entra) ----------
    public boolean rotarActualDerecha(){
        if (piezaActual == null) return false;
        piezaActual.rotarDerecha();
        if (puedeEn(piezaFila, piezaColumna, piezaActual)) return true;
        piezaActual.rotarIzquierda(); // revertir
        return false;
    }

    public boolean rotarActualIzquierda(){
        if (piezaActual == null) return false;
        piezaActual.rotarIzquierda();
        if (puedeEn(piezaFila, piezaColumna, piezaActual)) return true;
        piezaActual.rotarDerecha(); // revertir
        return false;
    }

    // --------- Validación de encaje ----------
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
                // colisión
                if(grilla[grFila][grCol]) return false;
            }
        }
        return true;
    }

    // Fija la pieza en la grilla
    private void fijarPiezaEnGrilla(){
        boolean[][] forma = piezaActual.obtenerForma();
        for(int r=0; r<forma.length; r++){
            for(int c=0; c<forma[0].length; c++){
                if(forma[r][c]){
                    int grFila = piezaFila + r;
                    int grCol = piezaColumna + c;
                    if(grFila >= 0 && grFila < alto && grCol >= 0 && grCol < ancho){
                        grilla[grFila][grCol] = true;
                    }
                }
            }
        }
    }

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

    // Carga un estado inicial validando dimensiones (deep copy)
    public void establecerTablero(boolean[][] tableroInicial) {
        if (tableroInicial == null || tableroInicial.length != alto) {
            throw new IllegalArgumentException("Alto inválido para el estado inicial");
        }
        for (int r = 0; r < alto; r++) {
            if (tableroInicial[r] == null || tableroInicial[r].length != ancho) {
                throw new IllegalArgumentException("Ancho inválido en la fila " + r);
            }
            if (grilla[r] == null || grilla[r].length != ancho) {
                grilla[r] = new boolean[ancho];
            }
            System.arraycopy(tableroInicial[r], 0, grilla[r], 0, ancho);
        }
    }

    // Devuelve una copia del tablero (deep copy)
    public boolean[][] obtenerTablero() {
        boolean[][] copia = new boolean[alto][ancho];
        for (int r = 0; r < alto; r++) {
            System.arraycopy(grilla[r], 0, copia[r], 0, ancho);
        }
        return copia;
    }
}





//Board (tablero)

//Qué es: el corazón del juego. Mantiene:

//La grilla fija (boolean[alto][ancho]) con los bloques ya asentados.

//La pieza en caída (piezaActual) y su posición (piezaFila, piezaColumna) —la esquina superior izquierda de su “caja”.

//Qué hace:

//Spawnear/colocar una pieza: ponerPiezaActual(pieza) la ubica en la fila 0 y la centra horizontalmente según el ancho de su forma.

//Mover abajo: moverAbajo() intenta bajar la pieza una fila.

//Si puede (no sale ni choca): incrementa piezaFila y devuelve true.

//Si no puede: fija la pieza (pasa su forma a la grilla), limpia líneas completas, pone piezaActual = null y devuelve false.

//Chequear encaje: puedeMoverAbajo() delega en el verificador genérico puedeEn(nuevaFila, nuevaCol, pieza), que proyecta cada true de la forma a coordenadas de grilla y valida límites y colisión con grilla.

//Movimiento lateral (opcional pero útil): moverIzquierda() / moverDerecha() usan puedeEn(...) para validar antes de mover.

//Rotación segura: rotarActualDerecha() / rotarActualIzquierda() aplican la rotación y la revierten si la nueva forma no entra (sin wall-kicks avanzados: suficiente para el TP).

//Fijar pieza: fijarPiezaEnGrilla() copia los true actuales de la forma a la grilla fija en (piezaFila + r, piezaColumna + c).

//Limpiar líneas: eliminarLineasCompletas() recorre de abajo hacia arriba; si una fila está toda en true, la elimina, baja todas las filas superiores una posición,
// vacía la fila 0 y rechequea la misma r (porque ahora contiene lo que bajó).

//Cómo se usa en la práctica:

//El Juego llama a ponerPiezaActual() al spawnear.

//En cada tick, Juego llama a moverAbajo(). Si devuelve false, la pieza se asentó y Juego spawneará una nueva en el próximo tick.

//Controles opcionales (izquierda/derecha/rotar) pueden invocar moverIzquierda(), moverDerecha(), rotarActual...() entre ticks.

//Invariantes y garantías:

//La grilla solo contiene bloques fijos (la pieza en caída nunca se “pinta” ahí hasta fijarse).

//puedeEn(...) garantiza que nunca se salga de [0..alto-1] × [0..ancho-1] ni pise un true fijo.

//La limpieza de líneas conserva el orden de lo que estaba arriba (simplemente desciende).

//Complejidad (aproximada):

//Movimientos y chequeos: O(celdas de la forma), típicamente constante pequeña (≤ 16).

//Limpieza de líneas: O(alto × ancho) en el peor caso (cuando hay que desplazar muchas filas).

//Pitfalls a evitar (y cómo lo resolviste):

//Saltearse líneas al limpiar: se corrige con el r++ después de bajar filas, para re-evaluar la “nueva” fila que cayó en la misma r.

//Dibujar la pieza activa dentro de la grilla: se evitó separando pieza-actual de grilla-fija, simplificando colisión y limpieza.