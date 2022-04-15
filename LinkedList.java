// The "data" variable in this implementation represents a product object.
import java.util.NoSuchElementException;

public class LinkedList<AnyType extends IDedObject>{

    // This class creates the node object for the linked list.
    // Node<AnyType> is static so the mainFunction() can access it.
    public static class Node<AnyType>{
    
        public Node<AnyType> next;
        public AnyType data;

        public Node(){
        }

        public Node(AnyType data, Node<AnyType> next) {
            this.data = data;
            this.next = next;
        }

        public Node(AnyType newData){
            data = newData;
        }
    }

    // Set the default values of the head and tail pointers to null.
    Node<AnyType> head = null;
    Node<AnyType> tail = null;

    // Default constructor.
    LinkedList(){
    }

    // Method that inserts a new node at the end of the linked list.
    public void insertAtEnd(AnyType data){
        Node<AnyType> newNode = new Node<AnyType>(data); // Create a new node with the given data (product).
        newNode.next = null;

        if(empty()){ // If the linked list is empty, then make a new node as its head.
            head = newNode;
        }
        else{ // Traverse the linked list until the last node is reached and insert the new node there.
            Node<AnyType> lastNode = head;
            while(lastNode.next != null){
                lastNode = lastNode.next;
            }

            lastNode.next = newNode; // Insert the new node at the last node.
        }
    }

    // This function inserts a product at the beginning of the linked list.
    public boolean insertAtFront(AnyType product){
        Node<AnyType> nextPointer = new Node<AnyType>(product, null);
        int productID = product.getID(); // Returns the product ID to ensure that this product ID is not already contained within the linked list.
        Integer objectProductID = Integer.valueOf(productID); // Converts the primitive variable productID to an object using the Integer wrapper class.
        int numOccurrences = occurrence(objectProductID); // Finds the number of occurences of the specified product ID within the list.
        if (numOccurrences == -1) { // If -1 was returned from the occurrence() method, then the linked list is empty.
            head = nextPointer;
            tail = head;
            return true; // Product was successfully added.
        } 
        else if(numOccurrences >= 1){
            return false; // Product ID already exists, thus product does not need to be added.
        }
        else{
            nextPointer.next = head;
            head = nextPointer;
            return true; // Product was successfully added.
        }
        //printAllRecords(); // debugging
    }

    // This function deletes the product at the front of the linked list and displays its contents.
    public AnyType deleteFromFront(){
        if(empty()){ // If the current list is empty, return null.
            return null;
        }
        else{ // Otherwise, delete the 1st product within the linked list.
            AnyType deletedProduct = head.data; // Store the product's contents before it is deleted.
            head = head.next; // Unlink (delete) the 1st product in linked list.
            return deletedProduct; // Return the contents of the deleted product.
        }
    }

    // This function deletes a product with the specified product ID.
    public AnyType delete(int productID) {
        Node<AnyType> currentNode = head; // Store the head node.
        Node<AnyType> previous = null;
        AnyType deletedProduct; // deletedProduct is used store the product's contents before it is deleted if the specified product ID is found within the linked list.

        // CASE 1: If the current list is empty, throw a NoSuchElementException.
        if (empty()){
            throw new NoSuchElementException();
        }
        // CASE 2: If the head node holds the product ID to be deleted.
        if(currentNode != null && currentNode.data.getID() == productID){ 
            deletedProduct = currentNode.data; // Store the product's contents before it is deleted.
            head = currentNode.next; // Delete the product by changing the head node.
            return deletedProduct; // Return the contents of the deleted product.
        }
        // CASE 3: If the product ID is located somewhere other than the head in the linked list.
        while(currentNode != null && currentNode.data.getID() != productID){
            // Search for the product ID to be deleted by keeping track of the previous node, as it is needed to changed currentNode.next.
            // If the current node does not contain the product ID, continue to the next node.
            previous = currentNode;
            currentNode = currentNode.next;
        }
        if(currentNode != null){ // If the product ID is present, it should be contained within currentNode (end of linked list). Thus, currentNode should not be null.
            deletedProduct = currentNode.data; // Store the product's contents before it is deleted.
            previous.next = currentNode.next; // Since the product ID is at currentNode, unlink (delete) currentNode from the linked list.
            return deletedProduct; // Return the contents of the deleted product.
        }
        // CASE 4: If the product ID was not present within currentNode (end of linked list), currentNode should be null (by process of elimination).
        return null;
        //printAllRecords(); // debugging
    }

    // These 2 functions (private and public) find the number of occurences of an product ID number.
    public int occurrence(Integer data) {
        if (empty()){
            return -1; // Represents a flag, indicating the current list is empty.
        }
        return occurence(head, data, 0);
    }
    private int occurence(Node<AnyType> link, Integer data, int occurence) {
        if (link != null) {
            if (link.data.getID() == data)
                ++occurence;
            return occurence(link.next, data, occurence);
        }
        return occurence;
    }

    // These 2 methods (private and public) keep track of the size (number of nodes) of the linked list.
    public int size() {
        return size(head, 0);
    }
    private int size(Node<AnyType> link, int i) {
        if (link == null)
            return 0;
        else {
            int count = 1;
            count += size(link.next, 0);
            return count;
        }
    }

    // These 2 functions (private and public) print all the products (and their contents) within the linked list.
    public void printAllRecords() {
        if (empty()){ // If the head pointer is null, display an error message indicating the list is empty.
            System.out.println("\nError: the current list is empty.");
        }
        else{
            System.out.println("\nContents of all the Products contained within the list:");
            printAllRecords(head);  
        }
    }
    private void printAllRecords(Node<AnyType> link) {
        if (link != null) { // If the current link is not null, print the next product object's contents.
            link.data.printID(); // Print the contents of the linked list.
            printAllRecords(link.next);
        }
    }

    // This method checks to see if the linked list is empty.
    public boolean empty() {
        return head == null;
    }

    // This method empties the current list by setting the head and tail pointer to null.
    public void makeEmpty(){
        head = null;
        tail = null;
    }

    // This function searches for the user specified product ID number and returns the contents of the product with the matching ID (if found).
    public AnyType findID(int ID){
        Node<AnyType> currentNode = head; // Store the head node.
        Node<AnyType> previous = null;
        AnyType foundProduct; // Stores the product's contents if the specified product ID is found within the linked list.

        // CASE 1: If the current list is empty, return null.
        if (empty()){
            return null;
        }
        // CASE 2: If the head node holds the product ID being searched for.
        if(currentNode != null && currentNode.data.getID() == ID){ 
            foundProduct = currentNode.data; // Store the product's contents.
            return foundProduct; // Return the contents of the product.
        }
        // CASE 3: If the product ID is located somewhere other than the head in the linked list.
        while(currentNode != null && currentNode.data.getID() != ID){
            // Search for the product ID by keeping track of the previous node, as it is needed to changed currentNode.next.
            // If the current node does not contain the product ID, continue to the next node.
            previous = currentNode;
            currentNode = currentNode.next;
        }
        if(currentNode != null){ // If the product ID is present, it should be contained within currentNode (end of linked list). Thus, currentNode should not be null.
            foundProduct = currentNode.data; // Store the product's contents.
            return foundProduct; // Return the contents of the product.
        }
        // CASE 4: If the product ID was not present within currentNode (end of linked list), currentNode should be null (by process of elimination).
        return null;
        //printAllRecords(); // debugging
    }
}