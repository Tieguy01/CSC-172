package Project1;

public class InfixCalculator {

    public static Queue<String> infixToPostfix(String infix) {
        Stack<String> operatorStack = new Stack<>();
        Queue<String> postfixQueue = new Queue<>();

        for (int i = 0; i < infix.length(); i++) {
            Character token = infix.charAt(i);
            System.out.println(token + ":");

            if (Character.getNumericValue(token) >= 0) {
                postfixQueue.enqueue(token.toString());
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

                if (token != '(') {
                    int minPrecedence = findPredence(token);
                    System.out.println("minP: " + minPrecedence);
                    int oPPrecedence;
                    boolean lowPrecFound = false;
                    while (!lowPrecFound && !operatorStack.isEmpty()) {
                        String currentOp = operatorStack.pop();
                        oPPrecedence = findPredence(currentOp.charAt(0));

                        if (minPrecedence < oPPrecedence) {
                            postfixQueue.enqueue(currentOp);
                        } else {
                            operatorStack.push(currentOp);
                            lowPrecFound = true;
                        }
                        
                        System.out.println("oPP: " + oPPrecedence);
                    }
                }
                operatorStack.push(token.toString());

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
            case '(':
                return -1;
            default:
                return 0;
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
        System.out.println(postfixEval(infixToPostfix("!((1<3)&(2>4)|1)")));
        // System.out.println(postfixEval(infixToPostfix("1&0|1")));
    }
}