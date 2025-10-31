/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author crist
 */
public class Logica {

    // Clase Pila genérica
    public static class Pila<T> {
        private Stack<T> stack;

        public Pila() {
            stack = new Stack<>();
        }

        public void push(T elemento) {
            stack.push(elemento);
        }

        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("La pila está vacía");
            }
            return stack.pop();
        }

        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("La pila está vacía");
            }
            return stack.peek();
        }

        public boolean isEmpty() {
            return stack.isEmpty();
        }

        public int size() {
            return stack.size();
        }
    }

    // Método para verificar si un carácter es un operador
    private static boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Método para obtener la precedencia de un operador
    private static int precedencia(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    // Método para convertir expresión infija a posfija
    public static List<String> infijaAPosfija(String expresion) throws Exception {
        Pila<Character> pila = new Pila<>();
        List<String> salida = new ArrayList<>();
        int i = 0;

        while (i < expresion.length()) {
            char c = expresion.charAt(i);

            if (Character.isDigit(c)) {
                // Leer el número completo
                StringBuilder num = new StringBuilder();
                while (i < expresion.length() && Character.isDigit(expresion.charAt(i))) {
                    num.append(expresion.charAt(i));
                    i++;
                }
                salida.add(num.toString());
                continue; // No incrementar i aquí porque ya se hizo en el bucle
            } else if (c == '(') {
                pila.push(c);
            } else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    salida.add(pila.pop().toString());
                }
                if (!pila.isEmpty() && pila.peek() == '(') {
                    pila.pop(); // Remover el '('
                } else {
                    throw new Exception("Paréntesis no balanceados");
                }
            } else if (esOperador(c)) {
                while (!pila.isEmpty() && precedencia(pila.peek()) >= precedencia(c)) {
                    salida.add(pila.pop().toString());
                }
                pila.push(c);
            } else if (c != ' ') {
                throw new Exception("Carácter inválido: " + c);
            }
            i++;
        }

        while (!pila.isEmpty()) {
            if (pila.peek() == '(') {
                throw new Exception("Paréntesis no balanceados");
            }
            salida.add(pila.pop().toString());
        }

        return salida;
    }

    // Método para evaluar expresión posfija
    public static double evaluarPosfija(List<String> posfija) {
        Pila<Double> pila = new Pila<>();

        for (String token : posfija) {
            if (Character.isDigit(token.charAt(0))) {
                pila.push(Double.parseDouble(token));
            } else {
                double b = pila.pop();
                double a = pila.pop();
                switch (token.charAt(0)) {
                    case '+':
                        pila.push(a + b);
                        break;
                    case '-':
                        pila.push(a - b);
                        break;
                    case '*':
                        pila.push(a * b);
                        break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException("División por cero");
                        }
                        pila.push(a / b);
                        break;
                }
            }
        }

        return pila.pop();
    }

    // Método principal para evaluar expresión infija
    public static double evaluarExpresion(String expresion) throws Exception {
        List<String> posfija = infijaAPosfija(expresion);
        return evaluarPosfija(posfija);
    }
}
