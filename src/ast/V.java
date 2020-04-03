package ast;

import java.util.ArrayList;

public class V extends Sincronizacion {

    public V(String nombre) {
        super(nombre);
    }

    @Override
    protected ArrayList<Nodo> origenPrecedencia() {
        return predecesores;
    }

}
