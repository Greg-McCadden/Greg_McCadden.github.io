import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainClass{
    public static void main(String [] args){
        int selection = 0; // Holds the selection of the user in the product menu.
        boolean correctUserInput = true; // Flag that controls the execution of the try-catch block that ensures the user enters correct input (an int between 1 & 7).
        LinkedList<Product> newLinkedList = new LinkedList<Product>(); // Create a new linked list, with the object type being Product.

        try(Scanner input = new Scanner(System.in)){ // Create a scanner object to read in user input for the product menu. The "try-with-resources" syntax is used to close the scanner object.
            while(selection != 7){ // While loop that continuously displays the product menu until the user selects 7 (Done). 
                selection = 0; // Reset the value of selection before taking in new input.
                System.out.println("\nProduct menu: Please select a option (1-7)\n");
                System.out.println("1. Make Empty\n2. Find ID\n3. Insert At Front\n4. Delete From Front\n5. Delete ID\n6. Print All Records\n7. Done\n"); // Display product menu.
                
                do{ // This do while loop ensures the user enters an int value between 1 & 7.
                    try{
                        System.out.print("Your selection: ");
                        selection = input.nextInt(); // Reads in user's selection for product menu (int value).
                        while(selection < 1 || selection > 7){ // Input validation loop (user enters an invalid int menu selection).
                            System.out.print("Invalid input...please enter an integer between 1 and 7: ");
                            selection = input.nextInt(); // Reads in user's selection for product menu (int value).
                        }
                        correctUserInput = false; // Break do-while loop execution because the user has entered valid input.
                    }
                    catch(InputMismatchException ex){
                        System.out.println("Invaild input...please enter an integer between 1 and 7.\n");
                        input.nextLine(); // Discard current input so user can enter new input. 
                    }
                } while(correctUserInput);

                // 1. Menu option: Make Empty
                if(selection == 1){ // If,else if,else structure that jumps to the designated method, depending on the user's menu selection.
                    correctUserInput = true; // Reset try-catch input flag.
                    int areYouSure = 0; // Holds the value determining if the user intends to delete the current list (default is no).
                    System.out.print("\nAre you sure you want to delete the current list? (type 1 for yes or 0 for no)\n"); // Ensures the user intends to delete all the contents of the current list.
                    do{ // This do while loop ensures the user enters an int value of 1 or 0.
                        try{
                            System.out.print("Your selection: ");
                            areYouSure = input.nextInt(); // Reads in user's selection.
                            while(areYouSure > 1 || areYouSure < 0){ // Input validation loop (user did not enter 1 or 0).
                                System.out.print("Invalid input...please enter 1 for yes or 0 for no: ");
                                areYouSure = input.nextInt(); // Reads in user's selection.
                            }
                            correctUserInput = false; // Break do-while loop execution because the user has entered valid input.
                        }
                        catch(InputMismatchException ex){
                            System.out.println("Invaild input...please enter 1 for yes or 0 for no.\n");
                            input.nextLine(); // Discard current input so user can enter new input. 
                        }
                    } while(correctUserInput);

                    if(areYouSure == 1){ // If user entered 1, empty the current list.
                        newLinkedList.makeEmpty();
                        System.out.println("\nThe current list has been succcessfully deleted.");
                    }
                    else{ // Otherwise, leave current list intact.
                        System.out.println("\nAborting...");
                    }
                }

                // 2. Menu option: Find ID
                else if(selection == 2){
                    int productID = 0;
                    System.out.println("Please enter the product ID number to be searched.");
                    correctUserInput = true;
                    do{ // This do while loop ensures the user enters an int value.
                        try{
                            System.out.print("Your selection: ");
                            productID = input.nextInt(); // Reads in user's selection.
                            correctUserInput = false; // Break do-while loop execution because the user has entered valid input.
                        }
                        catch(InputMismatchException ex){
                            System.out.println("Invaild input...please enter an integer\n");
                            input.nextLine(); // Discard current input so user can enter new input. 
                        }
                    } while(correctUserInput);
                    Product productContents = newLinkedList.findID(productID); // productContents holds the contents of the specified product ID (if found).
                    if(productContents == null){ // If the productContents == null, then the linked list is empty or the user entered ID number is not contained within the linked list.
                        System.out.println("\nError: the Product ID " + productID + " is not contained within the list.");
                    }
                    else{ // ID number has been found within the list.
                        System.out.println("\nProduct contents:");
                        productContents.printID();
                    }
                }

                // 3. Menu option: Insert At Front
                else if(selection == 3){
                    int productID = 0;
                    String productName;
                    String supplierName;

                    System.out.println("Enter Product ID.");
                    correctUserInput = true;
                    do{ // This do while loop ensures the user enters an int value.
                        try{
                            System.out.print("Your selection: ");
                            productID = input.nextInt(); // Reads in user's selection.
                            correctUserInput = false; // Break do-while loop execution because the user has entered valid input.
                        }
                        catch(InputMismatchException ex){
                            System.out.println("Invaild input...please enter an integer\n");
                            input.nextLine(); // Discard current input so user can enter new input. 
                        }
                    } while(correctUserInput);
                    input.nextLine();
                    System.out.print("Enter Product Name: ");
                    productName = input.nextLine();
                    System.out.print("Enter Supplier Name: ");
                    supplierName = input.nextLine();
                    Product newProduct = new Product(productID, productName, supplierName); // Create a new product object with the user specified data fields.
                    boolean successfullInsert = newLinkedList.insertAtFront(newProduct); // Flag that determines whether the new product was successfully inserted or not.
                    if(successfullInsert == false){ // If the product ID the user entered already exists within the linked list, display an error message.
                        System.out.println("\nError: product with the ID " + productID + " already exists.");
                    }
                    else{
                        System.out.println("\nProduct has been successfully added.");
                    }
                }

                // 4. Menu option: Delete From Front 
                else if(selection == 4){
                    Product productContents = newLinkedList.deleteFromFront(); // Store the contents of the deleted product.
                    if(productContents == null){ // If currentNode has the value of null, then the linked list is empty. 
                        System.out.println("\nError: the current list is empty");
                    }
                    else{
                        System.out.println("\nContents of the deleted Product:");
                        productContents.printID(); // Display the deleted product's contents.
                    }
                }

                // 5. Menu option: Delete ID
                else if(selection == 5){
                    int productID = 0;
                    System.out.println("Enter product ID to be deleted.");
                    correctUserInput = true;
                    do{ // This do while loop ensures the user enters an int value.
                        try{
                            System.out.print("Your selection: ");
                            productID = input.nextInt(); // Reads in user's selection.
                            correctUserInput = false; // Break do-while loop execution because the user has entered valid input.
                        }
                        catch(InputMismatchException ex){
                            System.out.println("Invaild input...please enter an integer\n");
                            input.nextLine(); // Discard current input so user can enter new input. 
                        }
                    } while(correctUserInput);
                    try{ // This try-catch block is used to display an error message when the linked list is empty. If this is the case, a NoSuchElementException is thrown in the delete() method.
                        Product productContents = newLinkedList.delete(productID); // Store the contents of the deleted product.
                        if(productContents == null){ // If the product ID was not found within linked list, display error message.
                            System.out.println("\nError: Product ID "+ productID + " is not contained within the list.");
                        }
                        else{ // Otherwise, the product ID was found and successfully deleted.
                            System.out.println("\nContents of the deleted Product:");
                            productContents.printID();  
                        }
                    }
                    catch(NoSuchElementException ex){
                        System.out.println("\nError: the current list is empty");
                    }    
                }

                // 6. Menu option: Print All Records
                else if(selection == 6){
                    newLinkedList.printAllRecords(); // Print the contents of all the products contained within the linked list.
                }

                // 7. Menu option: Done
                else{ // selection == 7
                    System.out.println("\nProgram terminating...have a nice day!\n"); // Display a goodbye message.
                }
            }
        }
    }
} 