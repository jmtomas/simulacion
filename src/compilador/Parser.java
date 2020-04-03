package compilador;

import ast.DuplicateNodeException;
import ast.P;
import ast.Nodo;
import ast.Secuencial;
import ast.Expresion;
import ast.V;
import ast.Bloque;
import ast.Concurrente;
import ast.Sincronizacion;
import java.util.Stack;
import compilador.Token.Simbolo;

public class Parser {

    private final Bloque ast;
    private final Stack<Simbolo> stack;
    private final Lexer lexer;

    public Parser(Lexer lexer) throws WrongSyntaxException, DuplicateNodeException {
        stack = new Stack();
        stack.push(Simbolo.FIN);
        stack.push(Simbolo.EXPRESIONES);
        Bloque.liberarNodos();
        Sincronizacion.liberarSemaforos();
        this.lexer = lexer;
        ast = parse(new Secuencial());
    }

    private Bloque parse(Bloque ASTParcial) throws WrongSyntaxException, DuplicateNodeException {
        while (lexer.hasToken() && !stack.isEmpty()) {
            Token input = lexer.peekToken();
            Simbolo sim = stack.peek();
            if (input.getType().equals(sim)) {
                stack.pop();
                lexer.nextToken();
                switch (input.getType()) {
                    case NODO:
                        ASTParcial.add(new Nodo(input.getData()));
                        break;
                    case PSEMAFORO:
                        ASTParcial.add(new P(input.getData()));
                        break;
                    case VSEMAFORO:
                        ASTParcial.add(new V(input.getData()));
                        break;
                    case BEGIN:
                        Secuencial s = new Secuencial();
                        ASTParcial.add(parse(s));
                        break;
                    case COBEGIN:
                        Concurrente c = new Concurrente();
                        ASTParcial.add(parse(c));
                        break;
                    default:
                        return ASTParcial;
                }
            } else {
                aplicarRegla(input, sim);
            }
        }
        return ASTParcial;
    }

    public Expresion getAst() {
        return ast;
    }

    private void aplicarRegla(Token input, Simbolo sim) throws WrongSyntaxException {
        switch (sim) {
            case EXPRESIONES:
                reglaExpresiones(input);
                break;
            case EXPRESION:
                reglaExpresion(input);
                break;
            case SECUENCIAL:
                reglaSecuencial(input);
                break;
            case CONCURRENTE:
                reglaConcurrente(input);
                break;
            default:
                throw new WrongSyntaxException();
        }
    }

    private void reglaExpresiones(Token input) {
        switch (input.getType()) {
            case NODO:
            case BEGIN:
            case COBEGIN:
            case PSEMAFORO:
            case VSEMAFORO:
                stack.push(Simbolo.EXPRESION);
                break;
            default:
                stack.pop();
        }
    }

    private void reglaExpresion(Token input) throws WrongSyntaxException {
        stack.pop();
        switch (input.getType()) {
            case NODO:
                stack.push(Simbolo.NODO);
                break;
            case BEGIN:
                stack.push(Simbolo.SECUENCIAL);
                break;
            case COBEGIN:
                stack.push(Simbolo.CONCURRENTE);
                break;
            case PSEMAFORO:
                stack.push(Simbolo.PSEMAFORO);
                break;
            case VSEMAFORO:
                stack.push(Simbolo.VSEMAFORO);
                break;
            default:
                throw new WrongSyntaxException();
        }
    }

    private void reglaSecuencial(Token input) throws WrongSyntaxException {
        switch (input.getType()) {
            case BEGIN:
                stack.pop();
                stack.push(Simbolo.END);
                stack.push(Simbolo.EXPRESIONES);
                stack.push(Simbolo.BEGIN);
                break;
            default:
                throw new WrongSyntaxException();
        }
    }

    private void reglaConcurrente(Token input) throws WrongSyntaxException {
        switch (input.getType()) {
            case COBEGIN:
                stack.pop();
                stack.push(Simbolo.COEND);
                stack.push(Simbolo.EXPRESIONES);
                stack.push(Simbolo.COBEGIN);
                break;
            default:
                throw new WrongSyntaxException();
        }
    }

}
