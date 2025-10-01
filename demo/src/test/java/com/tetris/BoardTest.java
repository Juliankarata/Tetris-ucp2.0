package com.tetris;

// Importa los métodos de aserción de JUnit para verificar condiciones en los tests
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

// Clase de pruebas para el tablero de Tetris
public class BoardTest {

    // Test que verifica la eliminación de líneas completas en el tablero
    @Test
    public void eliminaLineasCompletas() {
        // Crea un tablero de 4x4
        Board tablero = new Board(4, 4);

        // Estado inicial del tablero (última fila llena)
        boolean[][] tableroInicial = {
            {false, false, false, false}, // Fila 0: vacía
            {false, false, false, false}, // Fila 1: vacía
            {false, true,  false, false}, // Fila 2: solo una celda ocupada
            {true,  true,  true,  true}   // Fila 3: completamente llena
        };

        // Establece el tablero con el estado inicial
        tablero.establecerTablero(tableroInicial);

        // Elimina líneas completas y guarda cuántas fueron
        int eliminadas = tablero.eliminarLineasCompletas();
        // Verifica que se eliminó una línea
        assertEquals(1, eliminadas, "Debe eliminar exactamente una línea completa");

        // Obtiene el tablero resultante después de eliminar la línea
        boolean[][] tableroResultante = tablero.obtenerTablero();

        // Estado esperado después de eliminar la línea (la fila llena desaparece y las de arriba bajan)
        boolean[][] esperado = {
            {false, false, false, false}, // Fila 0: vacía
            {false, false, false, false}, // Fila 1: vacía
            {false, false, false, false}, // Fila 2: vacía (antes era la 1)
            {false, true,  false, false}  // Fila 3: la que estaba en la fila 2
        };

        // Verifica que cada fila del tablero resultante coincide con lo esperado
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(esperado[i], tableroResultante[i], "Fila " + i + " incorrecta");
        }
    }

    // Test que verifica la colisión entre piezas en el tablero
    @Test
    public void colisionEntrePiezas() {
        // Crea un tablero de 4x6
        Board tablero = new Board(4, 6);

        // Coloca una pieza cuadrada (O) y la deja caer hasta el fondo
        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);
        while (tablero.moverAbajo()) {
            // Deja caer piezaO hasta que no pueda bajar más
        }

        // Coloca una pieza palo (I) encima de la pieza O
        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        boolean colisiono = false;
        // Intenta mover la pieza I hacia abajo hasta que no pueda
        while (tablero.obtenerPiezaActual() != null) {
            boolean pudoMover = tablero.moverAbajo();
            if (!pudoMover) {
                colisiono = true; // Hubo colisión, la pieza no pudo moverse hacia abajo
                break;
            }
        }

        // Verifica que efectivamente hubo colisión
        assertTrue(colisiono, "La segunda pieza debe colisionar con la primera y detenerse");
    }

    // Test que verifica los movimientos laterales y los límites del tablero
    @Test
    public void movimientosLateralesYLimites() {
        // Crea un tablero de 4x6
        Board tablero = new Board(4, 6);

        // Coloca una pieza cuadrada (O)
        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);

        // Mover la pieza a la izquierda hasta que no pueda más
        while (tablero.moverIzquierda()) {}

        // Intentar mover más a la izquierda (debe fallar porque ya está en el límite)
        assertFalse(tablero.moverIzquierda(), "No debe poder moverse fuera del tablero a la izquierda");

        // Mover la pieza a la derecha hasta que no pueda más
        while (tablero.moverDerecha()) {}

        // Intentar mover más a la derecha (debe fallar porque ya está en el límite)
        assertFalse(tablero.moverDerecha(), "No debe poder moverse fuera del tablero a la derecha");
    }
}
