package ast;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Bloque extends Expresion {
    private static final ArrayList<Nodo> NODOS = new ArrayList();

    public static ArrayList<Nodo> listarNodos() {
        return NODOS;
    }
    
    protected LinkedList<Expresion> cuerpo;

    public Bloque() {
        cuerpo = new LinkedList();
    }
    
    public static void liberarNodos() {
        NODOS.clear();
    }

    public void add(Nodo e) throws DuplicateNodeException {
        if (NODOS.isEmpty() || !NODOS.contains(e)) {
            NODOS.add(e);
            cuerpo.add(e);
        } else {
            throw new DuplicateNodeException();
        }
    }
    
    public void add(Expresion e) {
        cuerpo.add(e);
    }

    public LinkedList<Expresion> getCuerpo() {
        return cuerpo;
    }

}
