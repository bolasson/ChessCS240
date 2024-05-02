
# Java Fundamentals:
- **Pointer Problem**: Solved the pointer problem by creating references.
- **Data Types**: Data types are always the same size to make the code more portable.
- **Boolean Check**: `if(x = 1)` is a compile error, meaning an integer is not equal to a boolean. Must use `if(x == 1)`.
- **Dynamic Linking**: Once it is compiled, it's good to go. It uses a JVM to load and reference any external objects.
- **Hybrid Nature**: Java is a hybrid compiled/interpreted language.
- **Performance**: Generally slightly slower than C++.

## Compiled Code
- **Description**: Uses C to write the code, then uses a compiler to make the executable work for the machine it's on.
- **Speed**: Fast execution.

## Interpreted Code
- **Description**: If you have an interpreter on your machine, then it runs on your machine.
- **Speed**: Slower execution compared to compiled code.

# Java Architecture:
- **Compilation Process**: Source code -> Compiler (rules of the machine it runs on) -> Java Byte Code (an imaginary machine specification) -> Interpreter (JVM, minimal translation).
- **JVM Operations**: JVM keeps track of statistics, and if it determines compiling a different way would be faster, it changes the compile strategy.
- **The HotSpot Virtual Machine**: Determines the statistics and what to change when compiling.

# Writing, Compiling, and Running Java Code:
- **Class Requirement**: Can't create standalone functions, everything must be a class.
- **Compilation Steps**: `something.java -> COMPILE -> something.class`.
- **The Main Method**: `public static void main(String[] args)` or `public static void main(String... args)` (the second is for CSVs).
- **Top Level Class**: 
  - **Example**: `public class SimpleJavaClass`
  - **Visibility**: Can be public or not specified.
  - **Naming Convention**: The class name must be the same name as the `.java file` it is in.
  - **Execution**: Not every class must have a main method, but if it has one, it can be executed.
  - **Method Inside Class**: A function inside a class is called a method.
- **Naming Consistency**: Always name `toString` and `equals` methods the same for consistency across classes.
- **Dynamic Linking**: How do you access/find other classes? Quick answer: It goes and finds them on its own.
- **Troubleshooting Tip**: If the code is not working but looks right, Invalidate Caches, Rebuild Project.
```