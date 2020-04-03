package compilador;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;
import compilador.Token.Simbolo;

public class Lexer {

    private final LinkedList<Token> tokens;

    public Lexer(String codigo) throws UnknownTokenException {
        codigo = codigo.trim();
        tokens = new LinkedList();
        Scanner s = new Scanner(codigo).useDelimiter(Pattern.compile("( *; *|( *\r?\n)+ *| +)"));
        while (s.hasNext()) {
            String input = s.next();
            if (input.equals("begin")) {
                tokens.add(new Token(Simbolo.BEGIN, ""));
            } else if (input.equals("end")) {
                tokens.add(new Token(Simbolo.END, ""));
            } else if (input.equals("cobegin")) {
                tokens.add(new Token(Simbolo.COBEGIN, ""));
            } else if (input.equals("coend")) {
                tokens.add(new Token(Simbolo.COEND, ""));
            } else if (input.matches("P\\([a-z]+\\)")) {
                tokens.add(new Token(Simbolo.PSEMAFORO, input.substring(2, input.length() - 1)));
            } else if (input.matches("V\\([a-z]+\\)")) {
                tokens.add(new Token(Simbolo.VSEMAFORO, input.substring(2, input.length() - 1)));
            } else if (input.matches("[A-Z][a-z]*")) {
                tokens.add(new Token(Simbolo.NODO, input));
            } else {
                throw new UnknownTokenException();
            }
        }
        tokens.add(new Token(Simbolo.FIN, ""));
    }

    public Token nextToken() {
        return tokens.remove();
    }
    
    public Token peekToken() {
        return tokens.peek();
    }
    
    public boolean hasToken() {
        return !tokens.isEmpty();
    }

}
