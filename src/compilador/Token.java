package compilador;

public class Token {

    public enum Simbolo {
        BEGIN, END, COBEGIN, COEND, VSEMAFORO, PSEMAFORO, NODO, FIN,
        EXPRESIONES, EXPRESION, SECUENCIAL, CONCURRENTE
    }

    private final Simbolo type;
    private final String data;

    public Token(Simbolo type, String data) {
        this.type = type;
        this.data = data;
    }

    public Simbolo getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "{" + "type=" + type + ", data=" + data + '}';
    }

}
