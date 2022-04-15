// two arguments; the names of the input and output files
import java.io.*;
import java.util.*;

public class DriverClassV2 {
	public static void main(String[] args) {
		Scanner inFile;
		PrintStream outFile;
		LayzBinarySearchTreeV2 layz = new LayzBinarySearchTreeV2();
		
		try {
			
			inFile = new Scanner(new File(args[0]));
			outFile = new PrintStream(new File(args[1]));
			
			while(inFile.hasNext()) {
				try {
					// read the current line from the input file
					String line = inFile.nextLine();
					
					// a string array containing an operation to be performed on the layz BST and a key if specified
					String[] request = line.split("[:]");
					
					if(request.length == 2) {
						int key = Integer.valueOf(request[1]);
						
						switch(request[0]) {
							// invoke the insert method to add a new key to the tree and write true or false to the output file depending on what the method returns
							case "Insert":
								outFile.println(layz.insert(key));
								break;
							// invoke the delete method to remove a key from the tree and write true or false to the output file depending on what the method returns
							case "Delete":
								outFile.println(layz.delete(key));
								break;
							// invoke the contains method to check whether a key exists in the tree and write true or false to the output file depending on what the method returns		
							case "Contains":
								outFile.println(layz.contains(key));
								break;
							default:
								throw new Exception("The operation read is not valid. Please try again later!");
						}
					} else {
						// for cases, in which the size of the array is equal to one
						switch(request[0]) {
							// invoke the toString method to write the contents of the tree in pre-order to the output file
							case "PrintTree":
								outFile.println(layz.toString());
								break;
							// invoke finMin method to write the value of the minimum element in the tree to the output file
							case "FindMin":
								outFile.println(layz.findMin());
								break;
							// invoke the findMax method to write the value of the maximum element in the tree to the output file
							case "FindMax":
								outFile.println(layz.findMax());
								break;
							// invoke the height method to write the heigth of the tree to the output file
							case "Height":
								outFile.println(layz.height());
								break;
						    // invoke the size method to write the number of nodes presented in the tree to the output file	
							case "Size":
								outFile.println(layz.size());
								break;
							// invalid operation
							default:
								throw new Exception("Error in Line: " + request[0]);
						}
					}
					
				} catch (IllegalArgumentException e) {
					outFile.println(e.getMessage());
	
				} catch (Exception e) {
					outFile.println(e.getMessage());
				}	
			}
			// close the files for possible leaking problems (good practice)
			inFile.close();
			outFile.close();
		
		} catch (FileNotFoundException e) {
			System.out.println("File " + args[0] + " does not exist. Please try again later!");
			System.exit(1);
		}
	}
}