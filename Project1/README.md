Ethan Shahan Project 1 Infix Calculator
Lab: 17 Tues/Thursday 4:50-6:05

Contact Info:
    Email: eshahan@u.rochester.edu

Code Explanation:
    The code works by taking in an input file of expressions, evaluating the expression on each line of the file, then outputting the result to an output text file. Each expression first goes through the method stringConverter, which converts the line into a queue in which each token of the expression (operand and operators) is broken up into its own node. Next, the queue is put through the method infixToPostfix, which utilizes the Shunting Yard Algorithm to convert the infix expression contained within the queue into a postfix expression. The postfix expression is then put through the postfixEval method, which takes the expression and evaluates it using a stack, outputting the result as a formatted string ready to be written to the ouput text file.

    Some notable obstacles that I overcame were figuring out how to input/output data to and from a text file as well as how to run java projects through the command line.

    For extra credit I included functioning implementations of the ^, %, sin(), cos(), and tan() operators, though it should be noted that for the trig functions I only used a single character for their implementation, with 's' representing sin, 'c' representing cos, and 't' represening tan. Examples of how these operators function can be seen in my sample input file infix_expr.txt.

Compile and Run Information:
    From the commandline, inside the directory of the project:
        ...\Project1> javac *.java
        ...\Project1> java Project1.InfixCalculator infix_expr.txt postfix_eval.txt
    // this assumes that the set classpath extends to the directory that precedes the package Project1

Additional Resources:
    Baeldung. “Check If a String Is Numeric in Java.” Baeldung, Baeldung, 30 Jan. 2021, www.baeldung.com/java-check-string-number. 
    I also talked about the project with my fellow classmate Matthew Daly
