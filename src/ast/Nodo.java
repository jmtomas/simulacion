package ast;

import java.util.ArrayList;
import java.util.Objects;

public class Nodo extends Expresion {

    private final String nombre;
    protected int nivel;
    public int X, Y, D;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.nivel = 0;
        X = Y = D = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivel;
    }

    protected int nivelPredecesores() {
        int nivelMaximo = 0;
        for (Nodo i : predecesores) {
            i.asignarNivel();
            if (i.nivel > nivelMaximo) {
                nivelMaximo = i.nivel;
            }
        }
        return nivelMaximo;
    }

    public void asignarNivel() {
        if (!predecesores.isEmpty()) {
            nivel = nivelPredecesores() + 1;
        }
    }

    public boolean esAncestro(Expresion n) {
        if (n.predecesores.isEmpty()) {
            return false;
        } else if (n.predecesores.contains(this)) {
            return true;
        } else {
            boolean res = false;
            for (int i = 0; i < n.predecesores.size() && !res; i++) {
                res = esAncestro(n.predecesores.get(i));
            }
            return res;
        }
    }

    public void resolverInderecciones() {
        ArrayList<Nodo> p = (ArrayList<Nodo>) predecesores.clone();
        predecesores = new ArrayList();
        p.forEach((i) -> {
            i.resolverInderecciones();
            i.preceder(this);
        });
    }

    public void detectarCiclos() throws CycleDetectedException {
        for (Nodo i : predecesores) {
            for (Nodo j : predecesores) {
                if (i.equals(j) && i.esAncestro(j)) {
                    throw new CycleDetectedException();
                }
            }
        }
    }

    @Override
    protected ArrayList<Nodo> origenPrecedencia() {
        ArrayList<Nodo> ret = new ArrayList();
        ret.add(this);
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (!predecesores.isEmpty()) {
            for (Nodo i : predecesores) {
                str.append(i.nombre);
                str.append(" ");
            }
            str.append("-> ");
        }
        str.append(nombre);
        return str.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nodo other = (Nodo) obj;
        return Objects.equals(this.nombre, other.nombre);
    }

}
