package ast;

import java.util.ArrayList;

public class P extends Sincronizacion {

    public P(String nombre) {
        super(nombre);
    }

    @Override
    protected ArrayList<Nodo> origenPrecedencia() {
        ArrayList<Nodo> ret = new ArrayList();
        ret.add(argumento);
        return ret;
    }

}
