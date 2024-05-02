This is some content changing.
Just for the assignment, but I'll probably also take class notes here.
And any other notes as applicable.

Java Fundamentals:
    <li>Solved the pointer problem by creating references
    <li>Data types are always the same size to make the code more portable.
    <li>if(x = 1) is a compile error, meaning an integer is not equal to a boolean. Must use if(x == 1).
    <li>Dynamic linking - once it is complied, it's good to go. It uses a JBM to load and reference any external objections
    <li>Is a hybrid compiled/interpreted language.
    <li>Generally slightly slower than C++.

Compiled code uses C to write the code, then uses a compiler to make the executable work for the machine it's in.
Compiled code is fast.

Interpreted code if you have an interpreter on your machine then it runs on your machine.
Interpreted code is slow.

Java Architecture:
    <li>Source code -> Compiler (rules of the machine it runs on (machine specification) -> Java Byte Code (an imaginary machine specification (how big an int should be)) -> Interpreter (JVM, not much translation).
    <li>JVM keeps track of statistics, then if it determines compiling it a different way would compile faster, and changes the compile strategy to be faster.
    <li>The HotSpot Virtual Machine - Determines the statistics and what to change when compiling.
    
Writing, Compiling, and Running Java Code:
    <li>Can't create standalone functions, everything must be a class. Most of the time, a script must have 1 or more classes.
    <li>something.java -> COMPILE -> something.class.
    <li>The Main Method: public static void main(String [] args) || public static void main(String...args) (second is for csv's)
    <li>Top level class
        <li>Can be public or not specified.
        <li>The class name must be the same name as the .java file it is in.
        <li>Not every class must have a main method, but if it has a main, it can be executed.
        <li>