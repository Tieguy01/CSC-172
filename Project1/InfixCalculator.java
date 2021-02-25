package Project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InfixCalculator {

    public static Pattern numbers = Pattern.compile("\\d+(\\.\\d+)?"); // pattern used to check whether a string is a number

    public static Queue<String> stringConverter(String expression) {
        Queue<String> dividedExpression = new Queue<>(); // Queue to store the given expression broken up into tokens without spaces
        Scanner scan = new Scanner(expression);

        // go through each bit of the expression separated by spaces
        while (scan.hasNext()) {
            // dividedExpression.viewQueue();
            // System.out.println();
            String next = scan.next();
            Pattern operators = Pattern.compile("s|c|t|\\!|\\^|\\*|\\/|\\%|\\+|\\-|\\<|\\>|\\=|\\&|\\||\\(|\\)"); // pattern used to check whether a string is any of the operators 
            //https://www.baeldung.com/java-check-string-number
            
            // if the given bit of the expression is a number or an oparator, enqueue it
            if (numbers.matcher(next).matches() || operators.matcher(next).matches()) {
                dividedExpression.enqueue(next);

            } else {

                // otherwise, search through each character of the bit of the expression with a blank delimiter
                Scanner partScan = new Scanner(next);
                partScan.useDelimiter("");

                while (partScan.hasNext()) {
                    String nxt = partScan.next();
                    // System.out.println(nxt);

                    // for each character of the bit, if it is a number or a character, enqueue it
                    if (numbers.matcher(nxt).matches() || operators.matcher(nxt).matches()) {
                        dividedExpression.enqueue(nxt);
                    }
                }
                partScan.close();

            }
        }
        scan.close();

        // dividedExpression.viewQueue();
        // System.out.println();
        return dividedExpression;
    }
    
    public static Queue<String> infixToPostfix(Queue<String> infix) {
        Stack<String> operatorStack = new Stack<>(); // stack to temporarily store the operators
        Queue<String> postfixQueue = new Queue<>(); // queue to store the final postfix expression

        while (!infix.isEmpty()) {

            String token = infix.dequeue();
            // System.out.println(token + ":");

            // if the given token is an operand, enqueue it
            if (numbers.matcher(token).matches()) {
                postfixQueue.enqueue(token);

            // if the given token is a '(', push it onto the stack
            } else if (token.indexOf("(") != -1) {
                operatorStack.push(token);

            // if the given token is a '(', pop and enqueue each operator on the stack until a '(' is found
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

            // otherwise the token is assumed to be an operator
            } else {
                
                // if the stack is empty or there is a '(' on the top of the stack, push the operator on the stack
                if (operatorStack.isEmpty() || (operatorStack.getHead().indexOf("(") != -1)) {
                    operatorStack.push(token);

                } else {
                    int tokenPrecedence = findPredence(token);
                    int lastPrecedence = findPredence(operatorStack.getHead());

                    // if the current operator has a higher precedence than the operator on the top of the stack
                    // or the current operator has the same precedence as the operator on the top of the stack and the current operator is right associative
                    // push the operator on the stack
                    if ((tokenPrecedence > lastPrecedence) || 
                            ((tokenPrecedence == lastPrecedence) && isRightAssociative(token))) {
                        operatorStack.push(token);

                    } else {

                        // otherwise, keep popping and enqueueing every operator on the top of the stack as long as
                        // the current operator has a lower precedence than the operator on the top of the stack
                        // or the current operator has the same precedence as the operator on the top of the stack and the current operator is not right associative
                        while(!operatorStack.isEmpty() && 
                                ((tokenPrecedence < lastPrecedence) || 
                                    ((tokenPrecedence == lastPrecedence) && !isRightAssociative(token)))) {
                            postfixQueue.enqueue(operatorStack.pop());
                            if (!operatorStack.isEmpty()) lastPrecedence = findPredence(operatorStack.getHead());
                        }
                        operatorStack.push(token);

                    }
                }
            }

            // System.out.print("Stack: ");
            // operatorStack.viewStack();
            // System.out.println();
            // System.out.print("Queue: ");
            // postfixQueue.viewQueue();
            // System.out.println();
        }

        // pop and enqueue the remaining operators on the stack
        while (!operatorStack.isEmpty()) {
            postfixQueue.enqueue(operatorStack.pop());
        }

        // System.out.print("Queue: ");
        // postfixQueue.viewQueue();
        // System.out.println();
        return postfixQueue;
    }

    // method to return the precedence of a given operator
    public static int findPredence(String token) {

        switch(token) {
            case "s":
            case "c":
            case "t":
                return 8;
            case "!":
                return 7;
            case "^":
                return 6;
            case "*":
            case "/":
            case "%":
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

    // method to return whether a given operator is right associative
    public static boolean isRightAssociative(String token) {

        switch(token) {
            case "s":
            case "c":
            case "t":
            case "!":
            case "^":
                return true;
            default:
                return false;
        }
        
     }

    public static String postfixEval(Queue<String> postfix) {
        Stack<String> evalStack = new Stack<>(); // stack used in evaluation of postfix expression
        Pattern singleOperators = Pattern.compile("s|c|t|\\!"); // pattern used to check whether an operator operates on a single operand

        while (!postfix.isEmpty()) {
            String token = postfix.dequeue();
            // System.out.println(token + ": ");
        
            // if the given token is an operand, push it on the stack
            if (numbers.matcher(token).matches()) {
                evalStack.push(token.toString());

            } else {

                double num2 = Double.parseDouble(evalStack.pop());
                double newNum = 0.0;
                
                // if the given token is an operator that operates on a single operand, evaluate the next number in the stack with that operator
                if (singleOperators.matcher(token).matches())  {
                
                    switch(token) {
                        case "s":
                            newNum = Math.sin(num2);
                            break;
                        case "c":
                            newNum = Math.cos(num2);
                            break;
                        case "t":
                            newNum = Math.tan(num2);
                            break;
                        case "!":
                            if ((int)num2 == 1) newNum = 0;
                            else  if ((int)num2 == 0) newNum = 1;
                            break;
                    }

                // otherwise it is assumed that the given token is an operator that operates on two operands
                // so, pop another operand from the stack and evaluate both of them with the given operator
                } else {

                    double num1 = Double.parseDouble(evalStack.pop());

                    switch(token) {
                        case "^":
                            newNum = Math.pow(num1, num2);
                            break;
                        case "*":
                            newNum = num1 * num2;
                            break;
                        case "/":
                            newNum = num1 / num2;
                            break;
                        case "%":
                            newNum = num1 % num2;
                            break;
                        case "+":
                            newNum = num1 + num2;
                            break;
                        case "-":
                            newNum = num1 - num2;
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

                evalStack.push(String.valueOf(newNum)); // push the newly evaluated number onto the stack
            }

            // System.out.print("Stack: ");
            // evalStack.viewStack();
            // System.out.println();
        }

        String finalValue = String.format("%.2f", Double.parseDouble(evalStack.pop())); // formats the result to have two decimal places
        return finalValue;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException{
        Scanner scan = new Scanner(new File(args[0]));
        FileWriter writer = new FileWriter(new File(args[1]));

        // evaluates each line of an input file and writes the result to an output file
        while (scan.hasNextLine()) {
            writer.write(postfixEval(infixToPostfix(stringConverter(scan.nextLine()))) + "\n");
            // System.out.println(postfixEval(infixToPostfix(stringConverter(scan.nextLine()))));
        }

        writer.close();
        scan.close();

        // System.out.println(postfixEval(infixToPostfix("!((1<3)&(2>4)|1)")));
        // System.out.println(postfixEval(infixToPostfix("2-1+1")));
        // System.out.println(postfixEval(infixToPostfix(stringConverter("(1 + 3 * 7)"))));
        // System.out.println(postfixEval(infixToPostfix(stringConverter("(1 - 2) - ((((4 ^ 5) * 3) * 6) / (7 ^ (2 ^ 2)))"))));
        // System.out.println(postfixEval(infixToPostfix(stringConverter("s(2 * 2) + c(8 / 2) + tan(2 + 2)"))));
        // stringConverter("! * / + - < > = & | ( ) 1 1.1 0.1 11.1 11 -1 -10 -1.1 (-1)");
    }
}