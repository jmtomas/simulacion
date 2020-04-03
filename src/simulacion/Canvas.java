package simulacion;

import ast.Nodo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    private ArrayList<Nodo> nodos;

    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(ArrayList<Nodo> nodos) {
        this.nodos = nodos;
    }

    public Canvas() {
        this.nodos = new ArrayList();
        this.setBackground(Color.white);
        this.setFont(new java.awt.Font("Monospaced", 1, 16));
    }

    private void dibujarFlecha(Graphics2D g, Nodo o, Nodo d) {
        double m = modulo(o, d) - d.D / 2;
        double a1 = angulo(o, d);
        double x1 = o.X + m * Math.cos(a1);
        double y1 = o.Y + m * Math.sin(a1);
        g.drawLine(o.X, o.Y, (int) x1, (int) y1);
        m = 15;
        double a2 = a1 + 0.5;
        double x2 = x1 - m * Math.cos(a2);
        double y2 = y1 - m * Math.sin(a2);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        a2 = a1 - 0.5;
        x2 = x1 - m * Math.cos(a2);
        y2 = y1 - m * Math.sin(a2);
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

    private double modulo(Nodo o, Nodo d) {
        return Math.sqrt(Math.pow(d.Y - o.Y, 2) + Math.pow(d.X - o.X, 2));
    }

    private double angulo(Nodo o, Nodo d) {
        try {
            return Math.atan2((d.Y - o.Y), (d.X - o.X));
        } catch (java.lang.ArithmeticException e) {
            return (d.X - o.X) >= 0 ? Math.PI / 2 : 3 * Math.PI / 2;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        nodos.forEach((i) -> {
            g2d.setColor(Color.red);
            i.getPredecesores().forEach((j) -> {
                dibujarFlecha(g2d, j, i);
            });
        });
        nodos.stream().map((i) -> {
            g2d.setColor(Color.white);
            g2d.fillOval(i.X - i.D / 2, i.Y - i.D / 2, i.D, i.D);
            return i;
        }).map((i) -> {
            g2d.setColor(Color.black);
            g2d.drawOval(i.X - i.D / 2, i.Y - i.D / 2, i.D, i.D);
            return i;
        }).forEachOrdered((i) -> {
            g2d.setColor(Color.black);
            g2d.drawString(i.getNombre(), i.X - i.D / 2 + 10, i.Y + 6);
        });
    }

    public void ubicarNodos() {
        int cantidadNiveles = cantidadNiveles(nodos);
        int nodosPorNivel[] = new int[cantidadNiveles];
        nodos.forEach((i) -> {
            nodosPorNivel[i.getNivel()]++;
        });
        int deltaY = this.getHeight() / (cantidadNiveles + 1);
        for (int i = 0; i < cantidadNiveles; i++) {
            int deltaX = this.getWidth() / (nodosPorNivel[i] + 1);
            int lugar = 1;
            for (Nodo j : nodos) {
                if (j.getNivel() == i) {
                    j.X = lugar * deltaX;
                    j.Y = (i + 1) * deltaY;
                    j.D = (j.getNombre().length() * 10) + 20;
                    lugar++;
                }
            }
        }
    }

    private int cantidadNiveles(ArrayList<Nodo> nodos) {
        int nivel = 0;
        for (Nodo i : nodos) {
            if (i.getNivel() > nivel) {
                nivel = i.getNivel();
            }
        }
        return nivel + 1;
    }
}
