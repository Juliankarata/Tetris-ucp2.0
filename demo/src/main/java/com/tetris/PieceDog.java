// File: src/main/java/com/tetris/PieceDog.java
package com.tetris;

/** Z (apodada "perro") */
public class PieceDog extends Pieza {
    public PieceDog(){ super(3,3); }

    @Override
    public boolean[][] obtenerForma(){
        boolean[][] base = new boolean[3][3];
        switch(orientacion){
            case 0:
            case 2: // horizontal Z
                base[1][0] = base[1][1] = true;
                base[0][1] = base[0][2] = true;
                break;
            case 1:
            case 3: // vertical
                base[0][0] = true;
                base[1][0] = base[1][1] = true;
                base[2][1] = true;
                break;
        }
        return base;
    }
}


//PieceDog (Z / “perro”)

//Qué es: la pieza Z (apodo “perro”) en caja 3×3.

//Cómo funciona: alterna entre:

//Orientaciones 0/2: “Z” horizontal (dos arriba desplazados a la derecha, dos abajo desplazados a la izquierda).

//Orientaciones 1/3: “Z” vertical.

//Rol: útil para testear colisiones diagonales/adyacentes, y limpieza de líneas por su huella escalonada.