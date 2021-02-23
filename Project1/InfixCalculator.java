package Project1;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InfixCalculator {

    public static Pattern numbers = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static Queue<String> stringConverter(String expression) {
        Queue<String> dividedExpression = new Queue<>();
        
        Scanner scan = new Scanner(expression);
        while (scan.hasNext()) {
            dividedExpression.viewQueue();
            System.out.println();
            String next = scan.next();
            Pattern operators = Pattern.compile("\\!|\\*|\\/|\\+|\\-|\\<|\\>|\\=|\\&|\\||\\(|\\)"); //https://www.baeldung.com/java-check-string-number
            
            if (numbers.matcher(next).matches() || operators.matcher(next).matches()) {
                dividedExpression.enqueue(next);
            } else {
                Scanner partScan = new Scanner(next);
                partScan.useDelimiter("");

                while (partScan.hasNext()) {
                    String nxt = partScan.next();
                    if (numbers.matcher(nxt).matches() || operators.matcher(nxt).matches()) {
                        dividedExpression.enqueue(nxt);
                    }
                }
                partScan.close();

            }
        }
        scan.close();

        dividedExpression.viewQueue();
        System.out.println();
        return dividedExpression;
    }
    
    public static Queue<String> infixToPostfix(Queue<String> infix) {
        Stack<String> operatorStack = new Stack<>();
        Queue<String> postfixQueue = new Queue<>();

        while (!infix.isEmpty()) {

            String token = infix.dequeue();
            System.out.println(token + ":");

            if (numbers.matcher(token).matches()) {
                postfixQueue.enqueue(token);
            } else if (token.indexOf("(") != -1) {
                operatorStack.push(token);
            } else if (token.indexOf(")") != -1) {

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
                
                if (operatorStack.isEmpty() || (operatorStack.getHead().indexOf("(") != -1)) {
                    operatorStack.push(token);
                } else {
                    int tokenPrecedence = findPredence(token);
                    int lastPrecedence = findPredence(operatorStack.getHead());

                    if ((tokenPrecedence > lastPrecedence) || 
                            ((tokenPrecedence == lastPrecedence) && isRightAssociative(operatorStack.getHead()))) {
                        operatorStack.push(token);
                    } else {

                        while(!operatorStack.isEmpty() && 
                                ((tokenPrecedence < lastPrecedence) || 
                                    ((tokenPrecedence == lastPrecedence) && !isRightAssociative(operatorStack.getHead())))) {
                            postfixQueue.enqueue(operatorStack.pop());
                            if (!operatorStack.isEmpty()) lastPrecedence = findPredence(operatorStack.getHead());
                        }
                        operatorStack.push(token);

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

    public static int findPredence(String token) {
        switch(token) {
            case "!":
                return 6;
            case "*":
            case "/":
                return 5;
            case "+":
            case "-":
                return 4;
            case "<":
            case ">":
                return 3;
            case "=":
                return 2;
            case "&":
                return 1;
            case "|":
                return 0;
            case "(":
                return -1;
            default:
                return 0;
        }
    }

    public static boolean isRightAssociative(String token) {
        switch(token) {
            case "!":
                return true;
            default:
                return false;
        }
     }

    public static String postfixEval(Queue<String> postfix) {
        Stack<String> evalStack = new Stack<>();

        while (!postfix.isEmpty()) {
            String token = postfix.dequeue();
            System.out.println(token + ": ");

            if (numbers.matcher(token).matches()) {
                evalStack.push(token.toString());
            } else {

                double num2 = Double.parseDouble(evalStack.pop());
                double newNum = 0.0;

                if (token.indexOf("!") != -1)  {
                    if ((int)num2 == 1) newNum = 0;
                    else  if ((int)num2 == 0) newNum = 1;
                } else {

                    double num1 = Double.parseDouble(evalStack.pop());

                    switch(token) {
                        case "+":
                            newNum = num1 + num2;
                            break;
                        case "-":
                            newNum = num1 - num2;
                            break;
                        case "*":
                            newNum = num1 * num2;
                            break;
                        case "/":
                            newNum = num1 / num2;
                            break;
                        case "<":
                            if (num1 < num2) newNum = 1;
                            else newNum = 0;
                            break;
                        case ">":
                            if (num1 > num2) newNum = 1;
                            else newNum = 0;
                            break;
                        case "=":
                            if (num1 == num2) newNum = 1;
                            else newNum = 0;
                            break;
                        case "&":
                            if (((int)num1 == 1) && ((int)num2 == 1)) newNum = 1;
                            else newNum = 0;
                            break;
                        case "|":
                            if (((int)num1 == 1) || ((int)num2 == 1)) newNum = 1;
                            else newNum = 0;
                            break;
                    }
                }

                evalStack.push(String.valueOf(newNum));
            }

            System.out.print("Stack: ");
            evalStack.viewStack();
            System.out.println();
        }

        return evalStack.pop();
    }

    public static void main(String[] args) {
        // System.out.println(postfixEval(infixToPostfix("!((1<3)&(2>4)|1)")));
        // System.out.println(postfixEval(infixToPostfix("2-1+1")));
        // System.out.println(postfixEval(infixToPostfix(stringConverter("(1 + 3 * 2)"))));
        System.out.println(postfixEval(infixToPostfix(stringConverter("!((1 < 3) & (2 > 4) | 1)"))));
        // stringConverter("! * / + - < > = & | ( ) 1 1.1 0.1 11.1 11");
    }
}