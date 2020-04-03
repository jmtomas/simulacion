package ast;

import java.util.ArrayList;

public class Semaforo extends Nodo {

    public Semaforo(String name) {
        super(name);
    }

    @Override
    public void asignarNivel() {
        nivel = nivelPredecesores();
    }

    @Override
    protected ArrayList<Nodo> origenPrecedencia() {
        return predecesores;
    }

}
