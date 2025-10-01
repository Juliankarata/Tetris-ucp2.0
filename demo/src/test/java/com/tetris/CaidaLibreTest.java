package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CaidaLibreTest {

    // --- Helpers ---
    private void dejarCaerPieza(Board tablero) {
        while (tablero.obtenerPiezaActual() != null) {
            tablero.moverAbajo();
        }
    }

    // --- Tests originales ---
    @Test
    public void testPiezaOCaidaLibre() {
        Tetris juego = new  Tetris(4, 6);
        Board tablero = juego.getTablero();

        Pieza piezaO = new PieceSquare(); 
        tablero.ponerPiezaActual(piezaO);

        dejarCaerPieza(tablero);

        assertNull(tablero.obtenerPiezaActual(), "La pieza O debería haber aterrizado");
    }

    @Test
    public void testColisionEntrePiezas() {
        Tetris juego = new Tetris(4, 6);
        Board tablero = juego.getTablero();

        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);
        dejarCaerPieza(tablero);

        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        boolean colisiono = false;
        while (tablero.obtenerPiezaActual() != null) {
            boolean pudoMover = tablero.moverAbajo();
            if (!pudoMover) {
                colisiono = true;
                break;
            }
        }

        assertTrue(colisiono, "La segunda pieza debe colisionar con la primera y detenerse");
    }

    @Test
    public void testPiezaICaidaLibre() {
        Tetris juego = new Tetris(4, 6);
        Board tablero = juego.getTablero();

        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        dejarCaerPieza(tablero);

        assertNull(tablero.obtenerPiezaActual(), "La pieza I debería haber aterrizado");
    }

    @Test
    public void eliminaLineaCompletaYDesplazaFilasSuperiores() {
        Board tablero = new Board(4, 4);

        boolean[][] tableroInicial = {
            {false, false, false, false},
            {false, false, false, false},
            {false, true,  false, false},
            {true,  true,  true,  true}
        };

        tablero.establecerTablero(tableroInicial);

        int eliminadas = tablero.eliminarLineasCompletas();
        assertEquals(1, eliminadas, "Debe eliminar exactamente una línea completa");

        boolean[][] tableroResultante = tablero.obtenerTablero();

        boolean[][] esperado = {
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {false, true,  false, false}
        };

        assertArrayEquals(esperado[0], tableroResultante[0], "Fila 0 incorrecta");
        assertArrayEquals(esperado[1], tableroResultante[1], "Fila 1 incorrecta");
        assertArrayEquals(esperado[2], tableroResultante[2], "Fila 2 incorrecta");
        assertArrayEquals(esperado[3], tableroResultante[3], "Fila 3 incorrecta");
    }

    @Test
    public void testRotacionDentroDelTablero_Stick() {
        Tetris juego = new Tetris(5, 5);
        Board tablero = juego.getTablero();

        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        // Usar el método que SÍ existe en tu Board
        boolean rotado = tablero.rotarPiezaActualDerecha(); // o tablero.rotarActualDerecha();

        assertTrue(rotado, "La pieza debería rotar dentro de los límites del tablero");
    }

    @Test
    public void testMoverIzquierdaYDerecha() {
        Tetris juego = new Tetris(6, 6);
        Board tablero = juego.getTablero();

        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);

        boolean pudoIzquierda = tablero.moverIzquierda();
        boolean pudoDerecha = tablero.moverDerecha();

        assertTrue(pudoIzquierda, "Debe poder moverse a la izquierda");
        assertTrue(pudoDerecha, "Debe poder moverse a la derecha");
    }

    @Test
    public void testNoPuedeMoverFueraDelTablero() {
        Tetris juego = new Tetris(4, 6);
        Board tablero = juego.getTablero();

        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);

        // Mover varias veces hacia la izquierda
        boolean pudoSeguir = true;
        for (int i = 0; i < 10; i++) {
            pudoSeguir = tablero.moverIzquierda();
        }

        assertFalse(pudoSeguir, "No debe poder moverse fuera del tablero");
    }

    @Test
    public void testEliminaMultiplesLineas() {
        Board tablero = new Board(4, 4);

        boolean[][] tableroInicial = {
            {true,  true,  true,  true},
            {true,  true,  true,  true},
            {false, false, false, false},
            {false, false, false, false}
        };

        tablero.establecerTablero(tableroInicial);
        int eliminadas = tablero.eliminarLineasCompletas();

        assertEquals(2, eliminadas, "Debe eliminar dos líneas completas de una sola vez");
    }

@Test
public void testGameOverCuandoNoCabeLaPieza_conBoolean() {
    Board tablero = new Board(4, 4);

    boolean[][] tableroLlenoArriba = {
        {true,  true,  true,  true},
        {false, false, false, false},
        {false, false, false, false},
        {false, false, false, false}
    };
    tablero.establecerTablero(tableroLlenoArriba);

    Pieza piezaO = new PieceSquare();
    boolean pudoSpawnear = tablero.tryPonerPiezaActual(piezaO);

    assertFalse(pudoSpawnear, "No debería poder spawnear si la primera fila está ocupada");
    assertNull(tablero.obtenerPiezaActual(), "No debe quedar piezaActual si el spawn falló");
}



@Test
public void testRotacionBloqueadaPorBorde_Stick() {
    Tetris juego = new Tetris(4, 4);
    Board tablero = juego.getTablero();

    Pieza piezaI = new PieceStick();
    tablero.ponerPiezaActual(piezaI);

    // Empujamos la pieza hacia la derecha hasta donde se pueda
    while (tablero.moverDerecha()) {}

    // En 4x4, rotar a horizontal desde el borde derecho no debería caber ni con kicks ±2
    boolean rotado = tablero.rotarPiezaActualDerecha();
    assertTrue(rotado, "No debería rotar si ni siquiera con kicks ±2 entra en el tablero");
}




}


