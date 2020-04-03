package ast;

import java.util.ArrayList;

public class Secuencial extends Bloque {

    @Override
    public void asignarPrecedencias() {
        if (!cuerpo.isEmpty()) {
            predecesores.forEach((i) -> {
                i.preceder(cuerpo.get(0));
            });
            cuerpo.get(0).asignarPrecedencias();
            if (cuerpo.size() > 1) {
                for (int i = 1; i < cuerpo.size(); i++) {
                    cuerpo.get(i - 1).preceder(cuerpo.get(i));
                    cuerpo.get(i).asignarPrecedencias();
                }
            }
        }
    }

    @Override
    protected ArrayList<Nodo> origenPrecedencia() {
        if (cuerpo.isEmpty()) {
            return predecesores;
        } else {
            return cuerpo.getLast().origenPrecedencia();
        }
    }

}
