/*
 * Proyecto: Diseño de una Interfaz Gráfica de Usuario (GUI) Intuitiva
 * Autor: Cesar Ricardo Caro Sánchez
 * Correo: cesar.caro@uptc.edu.co
 * Institución: Universidad Pedagógica y Tecnológica de Colombia
 * Facultad: Seccional Sogamoso, Ingeniería de Sistemas y Computación
 * Año: 2025
 * Descripción: Implementación de una herramienta gráfica para dibujar curvas de Bézier
 *              utilizando coordenadas paramétricas en Java.
 */

package curvadebezier;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;

/**
 * Clase principal que implementa una interfaz gráfica para dibujar curvas de Bézier.
 */
public class CurvaBezier extends JFrame {

    // Listas para almacenar las coordenadas de los puntos de control
    private ArrayList<Double> px = new ArrayList<>(); // Coordenadas X
    private ArrayList<Double> py = new ArrayList<>(); // Coordenadas Y

    // Componentes de la interfaz gráfica
    private JLabel lienzo;           // Área donde se dibuja la curva
    private JTextField campoPunto;   // Campo para ingresar coordenadas
    private JButton btnAgregar;      // Botón para agregar puntos
    private JButton btnDibujar;      // Botón para trazar la curva
    private JButton btnLimpiar;      // Botón para limpiar el lienzo
    private final int ANCHO = 600;   // Ancho del lienzo en píxeles
    private final int ALTO = 600;    // Alto del lienzo en píxeles
    private final int ESCALA = 20;   // Factor de escala (píxeles por unidad)
    private int puntoSeleccionado = -1; // Índice del punto seleccionado para mover

    /**
     * Constructor de la clase. Inicializa la ventana y los componentes gráficos.
     */
    public CurvaBezier() {
        // Configuración básica de la ventana
        setTitle("Curva de Bézier Simple");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Inicialización del lienzo
        lienzo = new JLabel();
        lienzo.setBounds(150, 10, ANCHO, ALTO);
        add(lienzo);
        actualizarLienzo(false); // Dibuja el lienzo inicial sin curva

        // Eventos del ratón para interactuar con el lienzo
        lienzo.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Clic izquierdo: seleccionar punto
                    seleccionarPunto(e.getX(), e.getY());
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Clic derecho: eliminar punto
                    eliminarPunto(e.getX(), e.getY());
                }
            }
            public void mouseReleased(MouseEvent e) {
                puntoSeleccionado = -1; // Soltar el punto al liberar el ratón
            }
        });
        lienzo.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                moverPunto(e.getX(), e.getY()); // Mover punto mientras se arrastra
            }
        });

        // Campo de texto para ingresar coordenadas
        campoPunto = new JTextField();
        campoPunto.setBounds(10, 10, 50, 25);
        campoPunto.addActionListener(e -> agregarPunto()); // Agregar punto con Enter
        add(campoPunto);

        // Botón "Agregar"
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(10, 40, 100, 25);
        btnAgregar.addActionListener(e -> agregarPunto());
        btnAgregar.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) agregarPunto();
            }
        });
        add(btnAgregar);

        // Botón "Dibujar"
        btnDibujar = new JButton("Dibujar");
        btnDibujar.setBounds(10, 70, 100, 25);
        btnDibujar.addActionListener(e -> actualizarLienzo(true));
        add(btnDibujar);

        // Botón "Limpiar"
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(10, 100, 100, 25);
        btnLimpiar.addActionListener(e -> limpiarLienzo());
        add(btnLimpiar);

        setVisible(true); // Mostrar la ventana
    }

    /**
     * Agrega un punto de control desde el campo de texto.
     */
    private void agregarPunto() {
        try {
            String[] coords = campoPunto.getText().split(",");
            double x = Double.parseDouble(coords[0]); // Coordenada X
            double y = Double.parseDouble(coords[1]); // Coordenada Y
            px.add(x);
            py.add(y);
            campoPunto.setText(""); // Limpiar campo
            actualizarLienzo(false); // Actualizar lienzo sin curva
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Escribe: X,Y (ej. 2,3)");
        }
    }

    /**
     * Selecciona un punto para moverlo basado en la posición del clic.
     * @param x Coordenada X del clic en píxeles
     * @param y Coordenada Y del clic en píxeles
     */
    private void seleccionarPunto(int x, int y) {
        for (int i = 0; i < px.size(); i++) {
            int pxPixel = (int) (px.get(i) * ESCALA + ANCHO / 2);
            int pyPixel = (int) (ALTO / 2 - py.get(i) * ESCALA);
            if (Math.abs(pxPixel - x) < 10 && Math.abs(pyPixel - y) < 10) {
                puntoSeleccionado = i; // Marcar punto como seleccionado
                return;
            }
        }
    }

    /**
     * Elimina un punto tras confirmar con el usuario.
     * @param x Coordenada X del clic en píxeles
     * @param y Coordenada Y del clic en píxeles
     */
    private void eliminarPunto(int x, int y) {
        for (int i = 0; i < px.size(); i++) {
            int pxPixel = (int) (px.get(i) * ESCALA + ANCHO / 2);
            int pyPixel = (int) (ALTO / 2 - py.get(i) * ESCALA);
            if (Math.abs(pxPixel - x) < 10 && Math.abs(pyPixel - y) < 10) {
                int opcion = JOptionPane.showConfirmDialog(this,
                        "¿Eliminar punto P" + (i + 1) + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    px.remove(i);
                    py.remove(i);
                    actualizarLienzo(false); // Redibujar sin curva
                }
                return;
            }
        }
    }

    /**
     * Mueve un punto seleccionado a una nueva posición.
     * @param x Nueva coordenada X en píxeles
     * @param y Nueva coordenada Y en píxeles
     */
    private void moverPunto(int x, int y) {
        if (puntoSeleccionado != -1) {
            px.set(puntoSeleccionado, (double) (x - ANCHO / 2) / ESCALA);
            py.set(puntoSeleccionado, (double) (ALTO / 2 - y) / ESCALA);
            actualizarLienzo(false); // Actualizar lienzo sin curva
        }
    }

    /**
     * Limpia todos los puntos de control y redibuja el lienzo.
     */
    private void limpiarLienzo() {
        px.clear();
        py.clear();
        actualizarLienzo(false);
    }

    /**
     * Actualiza el lienzo con puntos y, opcionalmente, la curva.
     * @param dibujarCurva Indica si se debe trazar la curva de Bézier
     */
    private void actualizarLienzo(boolean dibujarCurva) {
        BufferedImage imagen = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
        Graphics g = imagen.getGraphics();

        // Fondo blanco
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ANCHO, ALTO);

        // Dibujar cuadrícula
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < ANCHO; i += ESCALA) {
            g.drawLine(i, 0, i, ALTO); // Líneas verticales
        }
        for (int i = 0; i < ALTO; i += ESCALA) {
            g.drawLine(0, i, ANCHO, i); // Líneas horizontales
        }
        g.setColor(Color.BLACK);
        g.drawLine(ANCHO / 2, 0, ANCHO / 2, ALTO); // Eje Y
        g.drawLine(0, ALTO / 2, ANCHO, ALTO / 2); // Eje X

        // Dibujar puntos y líneas entre ellos
        dibujarPuntos(g);

        // Dibujar curva si se solicita y hay suficientes puntos
        if (dibujarCurva && px.size() >= 2) {
            dibujarCurva(g);
        }

        lienzo.setIcon(new ImageIcon(imagen)); // Actualizar imagen en el lienzo
    }

    /**
     * Dibuja los puntos de control y las líneas que los conectan.
     * @param g Objeto Graphics para dibujar
     */
    private void dibujarPuntos(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < px.size(); i++) {
            int x = (int) (px.get(i) * ESCALA + ANCHO / 2);
            int y = (int) (ALTO / 2 - py.get(i) * ESCALA);
            g.fillOval(x - 5, y - 5, 10, 10); // Dibujar punto como círculo
            g.drawString("P" + (i + 1), x + 5, y - 5); // Etiqueta del punto
            if (i > 0) { // Línea al punto anterior
                int xAnterior = (int) (px.get(i - 1) * ESCALA + ANCHO / 2);
                int yAnterior = (int) (ALTO / 2 - py.get(i - 1) * ESCALA);
                g.setColor(Color.ORANGE);
                g.drawLine(xAnterior, yAnterior, x, y);
                g.setColor(Color.BLUE);
            }
        }
    }

    /**
     * Traza la curva de Bézier usando la ecuación paramétrica.
     * @param g Objeto Graphics para dibujar
     */
    private void dibujarCurva(Graphics g) {
        g.setColor(Color.RED);
        double t = 0;
        while (t <= 1) {
            double x = funcionParametrica(px, t) * ESCALA + ANCHO / 2;
            double y = ALTO / 2 - funcionParametrica(py, t) * ESCALA;
            g.fillRect((int) x, (int) y, 1, 1); // Dibujar píxel de la curva
            t += 0.0001; // Incremento pequeño para suavidad
        }
    }

    /**
     * Calcula la posición en la curva para un valor de t usando la ecuación de Bézier.
     * @param puntos Lista de coordenadas (X o Y)
     * @param t Parámetro entre 0 y 1
     * @return Posición calculada
     */
    private double funcionParametrica(ArrayList<Double> puntos, double t) {
        double resultado = 0;
        int n = puntos.size() - 1; // Grado de la curva
        for (int i = 0; i <= n; i++) {
            resultado += puntos.get(i) * combinaciones(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i);
        }
        return resultado;
    }

    /**
     * Calcula el coeficiente binomial (combinaciones de n en i).
     * @param n Número total de elementos
     * @param i Número de elementos seleccionados
     * @return Coeficiente binomial
     */
    private int combinaciones(int n, int i) {
        return factorial(n) / (factorial(i) * factorial(n - i));
    }

    /**
     * Calcula el factorial de un número.
     * @param n Número entero no negativo
     * @return Factorial de n
     */
    private int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    /**
     * Método principal para ejecutar la aplicación.
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        new CurvaBezier();
    }
}