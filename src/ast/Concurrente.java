package ast;

import java.util.ArrayList;

public class Concurrente extends Bloque {

    @Override
    public void asignarPrecedencias() {
        cuerpo.forEach((i) -> {
            predecesores.forEach((j) -> {
                j.preceder(i);
            });
            i.asignarPrecedencias();
        });
    }

    @Override
    protected ArrayList<Nodo> origenPrecedencia() {
        if (cuerpo.isEmpty()) {
            return predecesores;
        } else {
            ArrayList<Nodo> precedencias = new ArrayList();
            cuerpo.forEach((i) -> {
                precedencias.addAll(i.origenPrecedencia());
            });
            return precedencias;
        }
    }

}
