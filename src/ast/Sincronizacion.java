package ast;

import java.util.ArrayList;

public abstract class Sincronizacion extends Expresion {

    private static final ArrayList<Semaforo> SEMAFOROS = new ArrayList();
    protected Semaforo argumento;

    public static void liberarSemaforos() {
        SEMAFOROS.clear();
    }

    public Sincronizacion(String nombre) {
        Semaforo s = new Semaforo(nombre);
        if (SEMAFOROS.isEmpty() || !SEMAFOROS.contains(s)) {
            SEMAFOROS.add(s);
            argumento = s;
        } else {
            argumento = SEMAFOROS.get(SEMAFOROS.indexOf(s));
        }
    }

    @Override
    protected void destinoPrecedencia(ArrayList<Nodo> e) {
        predecesores.addAll(e);
        argumento.predecesores.addAll(e);
    }
}
