package com.tetris;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;

public class CaidaLibreTest {

    @Test
    public void testPiezaOCaidaLibre() {
        Juego juego = new Juego(4, 6);
        Board tablero = juego.getTablero();

        Pieza piezaO = new PieceSquare(); 
        tablero.ponerPiezaActual(piezaO);

        boolean aterrizo = false;
        for (int i = 0; i < 20; i++) {
            tablero.moverAbajo();
            if (tablero.obtenerPiezaActual() == null) {
                aterrizo = true;
                break;
            }
        }

        assertTrue(aterrizo, "La pieza O debería aterrizar (caída libre)");
    }

    @Test
    public void testColisionEntrePiezas() {
        Juego juego = new Juego(4, 6);
        Board tablero = juego.getTablero();


        Pieza piezaO = new PieceSquare();
        tablero.ponerPiezaActual(piezaO);
        while (tablero.obtenerPiezaActual() != null) {
            tablero.moverAbajo();
        }

        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);
        boolean colisiono = false;
        for (int i = 0; i < 20; i++) {
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
        Juego juego = new Juego(4, 6);
        Board tablero = juego.getTablero();

        Pieza piezaI = new PieceStick();
        tablero.ponerPiezaActual(piezaI);

        boolean aterrizo = false;
        for (int i = 0; i < 20; i++) {
            tablero.moverAbajo();
            if (tablero.obtenerPiezaActual() == null) {
                aterrizo = true;
                break;
            }
        }

        assertTrue(aterrizo, "La pieza I debería aterrizar (caída libre)");
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

        assertArrayEquals(esperado[0], tableroResultante[0], "La fila 0 debe quedar vacía");
        assertArrayEquals(esperado[1], tableroResultante[1], "La fila 1 debe quedar vacía");
        assertArrayEquals(esperado[2], tableroResultante[2], "La fila 2 debe quedar vacía");
        assertArrayEquals(esperado[3], tableroResultante[3], "La fila 3 debe contener la pieza desplazada");

        // Comprobación adicional: la fila completamente vacía ahora está al tope
        assertFalse(tableroResultante[0][0], "La primera celda debe estar vacía luego del corrimiento");
    }

    // Additional tests covering requested concepts:

    @Test
    public void testConstructorAndComponents() {
        Widget w = new Widget("gizmo", 7);
        assertEquals("gizmo", w.getName());
        assertEquals(7, w.getValue());
        // components: widget has a subcomponent
        assertNotNull(w.getComponent());
        assertEquals("part-A", w.getComponent().getId());
    }

    @Test
    public void testOverrideAndPolymorphism() {
        Animal a = new Dog();
        assertEquals("woof", a.speak()); // overridden method invoked via base reference
        assertTrue(a instanceof Dog);
        assertTrue(a instanceof Animal);
    }

    @Test
    public void testInterfaceAndMethodWithInterface() {
        Movable m = new Car();
        assertEquals("car moved", m.move());
        assertTrue(m instanceof Movable);
    }

    @Test
    public void testOverloading() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
        assertEquals(10, c.add(2, 3, 5));
    }

    @Test
    public void testDoubleEncapsulationPrivateFieldAndPrivateMethod() throws Exception {
        SecretBox box = new SecretBox();
        box.setSecret("xyz");
        // public getter works
        assertEquals("xyz", box.getSecret());
        // access private method via reflection
        Method m = SecretBox.class.getDeclaredMethod("revealSecret");
        m.setAccessible(true);
        String revealed = (String) m.invoke(box);
        assertEquals("xyz!", revealed);
        // access private field via reflection
        Field f = SecretBox.class.getDeclaredField("secret");
        f.setAccessible(true);
        assertEquals("xyz", f.get(box));
    }

    @Test
    public void testAbstractClassAndMethod() {
        Shape s = new Square(3);
        assertEquals(9, s.area());
        assertTrue(s instanceof Shape);
        assertTrue(s instanceof Square);
    }

    @Test
    public void testClassInstancesAndInheritanceChain() {
        Base b = new Derived();
        assertEquals("derived", b.name());
        assertTrue(b instanceof Base);
        assertTrue(b instanceof Derived);
    }

    // Helper classes for tests

    // Constructor & Components
    static class Widget {
        private final String name;
        private final int value;
        private final Component component;

        public Widget(String name, int value) {
            this.name = name;
            this.value = value;
            this.component = new Component("part-A");
        }

        public String getName() { return name; }
        public int getValue() { return value; }
        public Component getComponent() { return component; }
    }

    static class Component {
        private final String id;
        public Component(String id) { this.id = id; }
        public String getId() { return id; }
    }

    // Inheritance & Overriding & Polymorphism
    static class Animal {
        public String speak() { return "silent"; }
    }

    static class Dog extends Animal {
        @Override
        public String speak() { return "woof"; }
    }

    // Interface
    interface Movable {
        String move();
    }

    static class Car implements Movable {
        public String move() { return "car moved"; }
    }

    // Overloading
    static class Calculator {
        public int add(int a, int b) { return a + b; }
        public int add(int a, int b, int c) { return a + b + c; }
    }

    // Double encapsulation: private field and private method
    static class SecretBox {
        private String secret;

        public void setSecret(String s) { this.secret = s; }
        public String getSecret() { return this.secret; }

        @SuppressWarnings("unused")
        private String revealSecret() { return secret + "!"; }
    }

    // Abstract class and implementation
    static abstract class Shape {
        public abstract int area();
    }

    static class Square extends Shape {
        private final int side;
        public Square(int side) { this.side = side; }
        public int area() { return side * side; }
    }

    // Classes and inheritance chain
    static class Base {
        public String name() { return "base"; }
    }

    static class Derived extends Base {
        @Override
        public String name() { return "derived"; }
    }
}