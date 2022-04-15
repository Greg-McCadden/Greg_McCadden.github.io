import java.io.*;
import java.util.*;

/* 
How to run program from cmd (windows):
1st command: cd FilePath 
2nd command: javac DriverClass.java
3rd command: java DriverClass inputFileName outputFileName

Note: For the 1st command, all 4 files (DriverClass.java, LazyBinarySearchTree.java, inputfile.txt, outputfile.txt) must be in the same folder. 
*/

public class DriverClass{
    public static void main(String [] args){
        // input file name = args[0]; output file name = args[1].
        LazyBinarySearchTree BST = new LazyBinarySearchTree(); // Creates an instance of LazyBinarySearchTree.
        Scanner inputFile; // Object that reads data from input file.
        PrintWriter outputFile; // Object that writes data to the output file.
        
        // Begin to read input file and write to the output file.
        try{
            inputFile = new Scanner(new File(args[0])); // Creates an instance of Scanner and opens the input file for reading.
            outputFile = new PrintWriter(new File(args[1])); // Creates an instance of PrintWriter and creates an output file for writing.
            //inputFile = new Scanner(new File("inputfile.txt")); // Creates an instance of Scanner and opens the input file for reading.
            //outputFile = new PrintWriter(new File("outputfile.txt")); // Creates an instance of PrintWriter and creates an output file for writing.

            // Begin to read input file data.
            while(inputFile.hasNext()){
                try{
                    String currentLine = inputFile.nextLine(); // Read in the contents of the current line.
                    String[] BSTOperations = currentLine.split(":"); // This string array holds the BST operations from the input file that include an ":"

                    // If the size of BSTOperations is == 2, then BSTOperations[1] will contain an int that needs to be stored as the key for operations on the BST.
                    if(BSTOperations.length == 2){
                        int key = Integer.valueOf(BSTOperations[1]); // Stores the key.

                        // Preform the specified BST operation that is stored in BSTOperations[0] and write the result to the output file.
                        if(BSTOperations[0].equals("Insert")){
                            outputFile.println(BST.insert(key));
                        }
                        else if(BSTOperations[0].equals("Delete")){
                            outputFile.println(BST.delete(key));
                        }
                        else if(BSTOperations[0].equals("Contains")){
                            outputFile.println(BST.contains(key));
                        }
                        else{
                            throw new Exception("Error: invalid BST operation.");
                        }
                    }
                    else{ // Otherwise the BST operation does not contain a key. Thus, the length of BSTOperations is == 1 and the BST operation is stored at index 0.
                        if(BSTOperations[0].equals("FindMin")){
                            outputFile.println(BST.findMin());
                        }
                        else if(BSTOperations[0].equals("FindMax")){
                            outputFile.println(BST.findMax());
                        }
                        else if(BSTOperations[0].equals("PrintTree")){
                            outputFile.println(BST.toString());
                        }
                        else if(BSTOperations[0].equals("Height")){
                            outputFile.println(BST.height());
                        }
                        else if(BSTOperations[0].equals("Size")){
                            outputFile.println(BST.size());
                        }
                        else{
                            throw new Exception("Error in Line: " + BSTOperations[0]);
                        }
                    }
                }
                catch(IllegalArgumentException x){ // Catches an invalid key (the IllegalArgumentException is thrown in the LazyBinarySearchTree class).
                    outputFile.println(x.getMessage()); // Print the error message to the output file
                }
                catch(Exception x){ // Catches an invalid BST operation.
                    outputFile.println(x.getMessage()); // Write the error message to the output file.
                }
            }
            // Close input and output files.
            inputFile.close();
            outputFile.close();
        }
        catch(FileNotFoundException x){ // Catches the exception where the input file name does not exist.
            System.out.println("Error: input file \"" + args[0] + "\" cannot be found.");
            //System.out.println("Error: input file \"inputfile.txt\" cannot be found.");
            System.exit(1); // Terminate the program.
        }

        System.exit(1); // Terminate the program.
    }
}