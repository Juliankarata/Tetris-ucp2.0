// File: src/main/java/com/tetris/Pieza.java
package com.tetris;

/**
 * Clase base abstracta para todas las piezas.
 * - Cada pieza sabe "dibujar" su forma actual como una matriz booleana.
 * - La orientación se maneja en pasos de 90°: 0,1,2,3 (rotaciones).
 * - Las subclases solo definen cómo varía la forma según orientacion.
 */
public abstract class Pieza {
    // Tamaño de la matriz de la forma (alto x ancho); puede ser 2x2, 3x3 o 4x4 según la pieza.
    protected int filas;
    protected int columnas;
    // Orientación actual: 0,1,2,3 (cada tick de rotación es un giro de 90°).
    protected int orientacion;

    /** Constructor protegido: las subclases informan sus dimensiones base. */
    protected Pieza(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        this.orientacion = 0; // Por defecto, empieza sin rotación.
    }

    /**
     * Debe devolver una matriz booleana con la forma ocupada por la pieza.
     * - true = celda ocupada por un bloque de la pieza
     * - false = vacío
     * - El tamaño de la matriz debe coincidir con (filas x columnas).
     */
    public abstract boolean[][] obtenerForma();

    /** Rota 90° a la derecha (clockwise). */
    public void rotarDerecha(){
        orientacion = (orientacion + 1) % 4;
    }

    /** Rota 90° a la izquierda (counter-clockwise). */
    public void rotarIzquierda(){
        orientacion = (orientacion + 3) % 4;
    }

    /** Devuelve la orientación actual (0..3). */
    public int getOrientacion(){
        return orientacion;
    }
}


//pieza (abstracta)

//Qué es: la superclase de todas las piezas. Define el contrato y el estado común.

//Estado interno:

//filas, columnas: tamaño de la “caja” donde se dibuja la forma (2×2, 3×3 o 4×4).

//orientacion: entero en {0,1,2,3} (giros de 90°).

//Métodos clave:

//obtenerForma(): devuelve una matriz booleana filas×columnas con true en las celdas ocupadas.

//rotarDerecha(), rotarIzquierda(): cambian orientacion módulo 4.

//getOrientacion(): consulta de orientación actual.

//Para qué se usa: separar la lógica de la forma de la pieza del resto del sistema. La pieza no sabe del tablero ni de su posición; solo sabe dibujarse según su orientación.

//Cómo se usa: el Board pregunta a la pieza su forma actual para proyectarla sobre la grilla y validar encaje, colisión, etc.