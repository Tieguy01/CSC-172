Ethan Shahan Lab 6 Dynamic Programming
Lab: 17 Tues/Thursday 4:50-6:05

Contact Info:
    Email: eshahan@u.rochester.edu

Step 2:
    To solve this problem, maybe you could just keep subtracting the largest denomination that is less than or equal to the amount from the amount until the amount is 0, these denominations could be kept track of in an array the size of the amount of possible different denominations that adds one to the position of a denomination each time that denomination is subtracted from the total, then at the end this array could be used to print out every denomination used to make change, printing that denomination the number of times specified in the array.

Code Explanation:
    In this lab code was created to make change for a specified amount given to the command line. Change is given in the form of an array of denominations that add up to the amount specified. These denominations could include any combinations of 100000s, 5000s, 2000s, 1000s, 500s, 100s, 25s, 10s, 5s, or 1s depending on the amount specified to create change for that amount.

    Two classes were used to accomplish this task. RecursiveLab6.java makes change for the specfied amount simply by using recursion, as outlined by the Lab in steps 3-5. This method of making change has a very poor runtime as a result of its excessive unnecessary recursive calls, causing it to run in exponential time proportional to the amount given in the command line. DynamicLab6.java makes change for the specified amount utilizing recursion along with dynamic programming, as outlined in step 6. This method of making change is much more time efficient than the other method since it keeps track of when change has been made for a certain amount during the recursive processing, allowing much fewer recursive calls to have to be made since the calls now have a library of changes they can check instead of having to start from scratch on each call, allowing the programming to now run in linear time proportional to the amount specified. 

Compile and Run Information:        
    From the commandline, inside the directory of the project:
        ...\Lab6> javac *.java
        ...\Lab6> java Lab6.RecursiveLab6 <amount>
        or
        ...\Lab6> java Lab6.DynamicLab6 <amount>
        // this assumes that the set classpath extends to the directory that precedes the package Lab6
        // the int amount argument is optional, and should be used to specify the amount that change should be made for. If not specified, the default amount is 63