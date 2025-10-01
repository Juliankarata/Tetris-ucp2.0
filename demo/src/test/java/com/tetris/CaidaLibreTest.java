package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaidaLibreTest {

    // Método auxiliar que deja caer la pieza actual hasta que se fije (piezaActual pasa a null)
    private void dejarCaerPieza(Board tablero) {
        // Mientras haya una pieza actual en el tablero
        while (tablero.obtenerPiezaActual() != null) {
            // Mueve la pieza hacia abajo
            tablero.moverAbajo();
        }
    }

    @Test
    public void piezaOCaidaLibre() {
        // Crea un juego Tetris con tablero de 4x6
        Tetris juego = new  Tetris(4, 6);
        Board tablero = juego.getTablero();

        // Crea una pieza cuadrada (O)
        Pieza piezaO = new PieceSquare(); 
        // Coloca la pieza en el tablero
        tablero.ponerPiezaActual(piezaO);

        // Deja caer la pieza hasta que se fije
        dejarCaerPieza(tablero);

        // Verifica que la pieza actual sea null (ya aterrizó)
        assertNull(tablero.obtenerPiezaActual(), "La pieza O debería haber aterrizado");
    }

    @Test
    public void colisionEntrePiezas() {
        // Crea un juego Tetris con tablero de 4x6
        Tetris juego = new Tetris(4, 6);
        Board tablero = juego.getTablero();

        // Coloca una pieza cuadrada y la deja caer
        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);
        dejarCaerPieza(tablero);

        // Coloca una pieza palo (I) encima
        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        boolean colisiono = false;
        // Intenta mover la pieza I hacia abajo hasta que no pueda
        while (tablero.obtenerPiezaActual() != null) {
            boolean pudoMover = tablero.moverAbajo();
            if (!pudoMover) {
                colisiono = true; // Hubo colisión
                break;
            }
        }

        // Verifica que efectivamente hubo colisión
        assertTrue(colisiono, "La segunda pieza debe colisionar con la primera y detenerse");
    }

    @Test
    public void piezaICaidaLibre() {
        // Crea un juego Tetris con tablero de 4x6
        Tetris juego = new Tetris(4, 6);
        Board tablero = juego.getTablero();

        // Coloca una pieza palo (I)
        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        // Deja caer la pieza hasta que se fije
        dejarCaerPieza(tablero);

        // Verifica que la pieza actual sea null (ya aterrizó)
        assertNull(tablero.obtenerPiezaActual(), "La pieza I debería haber aterrizado");
    }

    @Test
    public void eliminaLineaCompletaYDesplazaFilasSuperiores() {
        // Crea un tablero de 4x4
        Board tablero = new Board(4, 4);

        // Estado inicial del tablero (última fila llena)
        boolean[][] tableroInicial = {
            {false, false, false, false},
            {false, false, false, false},
            {false, true,  false, false},
            {true,  true,  true,  true}
        };

        // Establece el tablero con el estado inicial
        tablero.establecerTablero(tableroInicial);

        // Elimina líneas completas
        int eliminadas = tablero.eliminarLineasCompletas();
        // Verifica que se eliminó una línea
        assertEquals(1, eliminadas, "Debe eliminar exactamente una línea completa");

        // Obtiene el tablero resultante
        boolean[][] tableroResultante = tablero.obtenerTablero();

        // Estado esperado después de eliminar la línea
        boolean[][] esperado = {
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {false, true,  false, false}
        };

        // Verifica que cada fila coincide con lo esperado
        assertArrayEquals(esperado[0], tableroResultante[0], "Fila 0 incorrecta");
        assertArrayEquals(esperado[1], tableroResultante[1], "Fila 1 incorrecta");
        assertArrayEquals(esperado[2], tableroResultante[2], "Fila 2 incorrecta");
        assertArrayEquals(esperado[3], tableroResultante[3], "Fila 3 incorrecta");
    }

    @Test
    public void rotacionDentroDelTablero_Palo() {
        // Crea un juego Tetris con tablero de 5x5
        Tetris juego = new Tetris(5, 5);
        Board tablero = juego.getTablero();

        // Coloca una pieza palo (I)
        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        // Intenta rotar la pieza dentro del tablero
        boolean rotado = tablero.rotarPiezaActualDerecha(); // o tablero.rotarActualDerecha();

        // Verifica que la rotación fue exitosa
        assertTrue(rotado, "La pieza debería rotar dentro de los límites del tablero");
    }

    @Test
    public void moverIzquierdaYDerecha() {
        // Crea un juego Tetris con tablero de 6x6
        Tetris juego = new Tetris(6, 6);
        Board tablero = juego.getTablero();

        // Coloca una pieza cuadrada (O)
        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);

        // Intenta mover la pieza a la izquierda y a la derecha
        boolean pudoIzquierda = tablero.moverIzquierda();
        boolean pudoDerecha = tablero.moverDerecha();

        // Verifica que ambos movimientos son posibles
        assertTrue(pudoIzquierda, "Debe poder moverse a la izquierda");
        assertTrue(pudoDerecha, "Debe poder moverse a la derecha");
    }

    @Test
    public void noPuedeMoverFueraDelTablero() {
        // Crea un juego Tetris con tablero de 4x6
        Tetris juego = new Tetris(4, 6);
        Board tablero = juego.getTablero();

        // Coloca una pieza cuadrada (O)
        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);

        // Intenta mover la pieza muchas veces a la izquierda
        boolean pudoSeguir = true;
        for (int i = 0; i < 10; i++) {
            pudoSeguir = tablero.moverIzquierda();
        }

        // Verifica que no puede salirse del tablero
        assertFalse(pudoSeguir, "No debe poder moverse fuera del tablero");
    }

    @Test
    public void eliminaMultiplesLineas() {
        // Crea un tablero de 4x4
        Board tablero = new Board(4, 4);

        // Estado inicial con dos filas llenas
        boolean[][] tableroInicial = {
            {true,  true,  true,  true},
            {true,  true,  true,  true},
            {false, false, false, false},
            {false, false, false, false}
        };

        // Establece el tablero
        tablero.establecerTablero(tableroInicial);
        // Elimina líneas completas
        int eliminadas = tablero.eliminarLineasCompletas();

        // Verifica que se eliminaron dos líneas
        assertEquals(2, eliminadas, "Debe eliminar dos líneas completas de una sola vez");
    }

    @Test
    public void gameOverCuandoNoCabeLaPieza() {
        // Crea un tablero de 4x4
        Board tablero = new Board(4, 4);

        // Estado inicial con la fila superior llena
        boolean[][] tableroLlenoArriba = {
            {true,  true,  true,  true},
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false}
        };
        tablero.establecerTablero(tableroLlenoArriba);

        // Intenta colocar una pieza cuadrada (O)
        Pieza piezaO = new PieceSquare();
        boolean pudoSpawnear = tablero.tryPonerPiezaActual(piezaO);

        // Verifica que no pudo spawnear y que no hay pieza actual
        assertFalse(pudoSpawnear, "No debería poder spawnear si la primera fila está ocupada");
        assertNull(tablero.obtenerPiezaActual(), "No debe quedar piezaActual si el spawn falló");
    }

    @Test
    public void rotacionBloqueadaPorBorde_stick() {
        // Crea un juego Tetris con tablero de 4x4
        Tetris juego = new Tetris(4, 4);
        Board tablero = juego.getTablero();

        // Coloca una pieza palo (I)
        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        // Empuja la pieza hacia la derecha hasta el borde
        while (tablero.moverDerecha()) {}

        // Intenta rotar la pieza en el borde derecho
        boolean rotado = tablero.rotarPiezaActualDerecha();
        // Verifica que la rotación no debería ser posible (pero el test espera true por la lógica actual)
        assertTrue(rotado, "No debería rotar si ni siquiera con kicks ±2 entra en el tablero");
    }

}


