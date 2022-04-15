Name: Greg McCadden
NetID: GPM200000
Project: Project 2 (Binary Search Trees with Lazy Deletion)
Project Files: LazyBinarySearchTree.java DriverClass.java
File where main method is located: DriverClass.java
Java Version: 17.0.2
Text Editor used: VS Code

NOTE: If you are using a text editor/IDE that is NOT Eclipse, IntelliJ, or NetBeans, you must use the command prompt (windows) to pass the input file name and output file name as arguments to main.
      However, if you are using Eclipse, IntelliJ, or NetBeans, you can just use the built-in debugger tool to pass the 2 file names as arguments to main.

Instructions for how to run the program from the command prompt (windows):
1st command: cd FilePath
2nd command: javac DriverClass.java
3rd command: java DriverClass inputFileName.txt outputFileName.txt

NOTE: For the 1st command, all 4 files (DriverClass.java, LazyBinarySearchTree.java, inputFileName.txt, outputFileName.txt) must be in the same folder.

Presented below is an example of input and output.
Contents of input file:
Insert:50
Insert:47
Insert:80
Insert:7
Insert:99
Insert:99
Insert:1
Insert:-5
Insert:0
Insert:100
Insert:500
Insert:78
Insert:48
Insert:79
PrintTree
Delete:99
Delete:99
Delete:17
Insert:5
Insert
FindMin
FindMax
Contains:99
Contains:300
Contains:7
PrintTree
Height
Size
Insert:99
Insert:15
Insert:45
PrintTree
Delete
Contains
Insert
Hello
Contains:79
Contains:48
Contains:99
Delete:-7

Contents of corresponding output file:
true
true
true
true
true
false
true
Error in insert: IllegalArgumentException raised
Error in insert: IllegalArgumentException raised
Error in insert: IllegalArgumentException raised
Error in insert: IllegalArgumentException raised
true
true
true
50 47 7 1 48 80 78 79 99
true
false
false
true
Error in Line: Insert
1
80
false
Error in contains: IllegalArgumentException raised
true
50 47 7 1 5 48 80 78 79 *99
4
10
true
true
true
50 47 7 1 5 15 45 48 80 78 79 99
Error in Line: Delete
Error in Line: Contains
Error in Line: Insert
Error in Line: Hello
true
true
true
Error in delete: IllegalArgumentException raised


