package ast;

import java.util.ArrayList;

public abstract class Expresion {

    protected ArrayList<Nodo> predecesores;

    public Expresion() {
        predecesores = new ArrayList();
    }

    public ArrayList<Nodo> getPredecesores() {
        return predecesores;
    }

    public void removerAtajos() {
        ArrayList<Nodo> nodos = (ArrayList<Nodo>) predecesores.clone();
        for (Nodo i : nodos) {
            for (Nodo j : nodos) {
                if (!i.equals(j) && i.esAncestro(j)) {
                    predecesores.remove(i);
                }
            }
        }
    }

    protected abstract ArrayList<Nodo> origenPrecedencia();

    protected void destinoPrecedencia(ArrayList<Nodo> e) {
        predecesores.addAll(e);
    }

    protected void preceder(Expresion e) {
        e.destinoPrecedencia(this.origenPrecedencia());
    }

    public void asignarPrecedencias() {
    }
;
}
