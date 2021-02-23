package Project1;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InfixCalculator {

    public static Queue<String> stringConverter(String expression) {
        Queue<String> dividedExpression = new Queue<>();
        
        Scanner scan = new Scanner(expression);
        while (scan.hasNext()) {
            dividedExpression.viewQueue();
            System.out.println();
            String next = scan.next();
            Pattern pattern = Pattern.compile("(-?\\d+(\\.\\d+)?)|(\\!|\\*|\\/|\\+|\\-|\\<|\\>|\\=|\\&|\\||\\(|\\))"); //https://www.baeldung.com/java-check-string-number
            
            if (pattern.matcher(next).matches()) {
                dividedExpression.enqueue(next);
            } else {
                Scanner partScan = new Scanner(next);
                partScan.useDelimiter("");

                while (partScan.hasNext()) {
                    String nxt = partScan.next();
                    if (pattern.matcher(nxt).matches()) {
                        dividedExpression.enqueue(nxt);
                    }
                }
                partScan.close();

            }
        }
        scan.close();

        dividedExpression.viewQueue();
        return dividedExpression;
    }
    
    public static Queue<String> infixToPostfix(String infix) {
        Stack<String> operatorStack = new Stack<>();
        Queue<String> postfixQueue = new Queue<>();

        for (int i = 0; i < infix.length(); i++) {
            Character token = infix.charAt(i);
            System.out.println(token + ":");

            if (Character.getNumericValue(token) >= 0) {
                postfixQueue.enqueue(token.toString());
            } else if (token == '(') {
                operatorStack.push(token.toString());
            } else if (token == ')') {

                boolean parenFound = false;
                while (!parenFound) {
                    String poppedOp = operatorStack.pop();
                    if (poppedOp.indexOf("(") != -1) {
                        parenFound = true;
                    } else {
                        postfixQueue.enqueue(poppedOp);
                    }
                }

            } else {

                if (operatorStack.isEmpty() || (operatorStack.getHead().indexOf("(") == -1)) {
                    operatorStack.push(token.toString());

                } else {
                    int tokenPrecedence = findPredence(token);
                    int lastPrecedence = findPredence(operatorStack.getHead().charAt(0));

                    if ((tokenPrecedence > lastPrecedence) || ((tokenPrecedence == lastPrecedence) && isRightAssociative(operatorStack.getHead().charAt(0)))) {
                        operatorStack.push(token.toString());

                    } else {

                        while(!operatorStack.isEmpty() && ((tokenPrecedence < lastPrecedence) || ((tokenPrecedence == lastPrecedence) && !isRightAssociative(operatorStack.getHead().charAt(0))))) {
                            postfixQueue.enqueue(operatorStack.pop());
                        }
                        operatorStack.push(token.toString());

                    }
                }
            }

            System.out.print("Stack: ");
            operatorStack.viewStack();
            System.out.println();
            System.out.print("Queue: ");
            postfixQueue.viewQueue();
            System.out.println();
        }

        while (!operatorStack.isEmpty()) {
            postfixQueue.enqueue(operatorStack.pop());
        }

        System.out.print("Queue: ");
        postfixQueue.viewQueue();
        System.out.println();
        return postfixQueue;
    }

    public static int findPredence(char token) {
        switch(token) {
            case '!':
                return 6;
            case '*':
            case '/':
                return 5;
            case '+':
            case '-':
                return 4;
            case '<':
            case '>':
                return 3;
            case '=':
                return 2;
            case '&':
                return 1;
            case '|':
                return 0;
            // case '(':
            //     return -1;
            default:
                return 0;
        }
    }

    public static boolean isRightAssociative(char token) {
        switch(token) {
            case '!':
                return true;
            default:
                return false;
        }
     }

    public static String postfixEval(Queue<String> postfix) {
        Stack<String> evalStack = new Stack<>();

        while (!postfix.isEmpty()) {
            Character token = postfix.dequeue().charAt(0);
            // System.out.print(token + ": ");
            // evalStack.viewStack();
            // System.out.println();

            if (Character.getNumericValue(token) >= 0) {
                evalStack.push(token.toString());
            } else {

                int num2 = Integer.parseInt(evalStack.pop());
                int newNum = 0;

                if (token == '!')  {
                    if (num2 == 1) newNum = 0;
                    else  if (num2 == 0) newNum = 1;
                } else {

                    int num1 = 
                    Integer.parseInt(evalStack.pop());

                    switch(token) {
                        case '+':
                            newNum = num1 + num2;
                            break;
                        case '-':
                            newNum = num1 - num2;
                            break;
                        case '*':
                            newNum = num1 * num2;
                            break;
                        case '/':
                            newNum = num1 / num2;
                            break;
                        case '<':
                            if (num1 < num2) newNum = 1;
                            else newNum = 0;
                            break;
                        case '>':
                            if (num1 > num2) newNum = 1;
                            else newNum = 0;
                            break;
                        case '=':
                            if (num1 == num2) newNum = 1;
                            else newNum = 0;
                            break;
                        case '&':
                            if ((num1 == 1) && (num2 == 1)) newNum = 1;
                            else newNum = 0;
                            break;
                        case '|':
                            if ((num1 == 1) || (num2 == 1)) newNum = 1;
                            else newNum = 0;
                            break;
                    }
                }

                evalStack.push(String.valueOf(newNum));
            }
        }

        return evalStack.pop();
    }

    public static void main(String[] args) {
        // System.out.println(postfixEval(infixToPostfix("!((1<3)&(2>4)|1)")));
        // System.out.println(postfixEval(infixToPostfix("2-1+1")));

        stringConverter("!(3 * (1 + 6) = 63 / 3");
        // stringConverter("! * / + - < > = & | ( ) 1 1.1 0.1 11.1 11");
    }
}