import java.util.*;

public class LazyBinarySearchTree{
    
    // Private-nested TreeNode class.
    private class TreeNode{
        int key; // Holds the key value of the node.
        TreeNode leftChild; // Pointer to left child of a specific node.
        TreeNode rightChild; // Pointer to right child of a specific node.
        boolean deleted; // Flag that determines the status of a node within the tree (true = deleted, false = not deleted).

        // Default constructor.
        public TreeNode(){
        }

        // Constructor with a specified key.
        public TreeNode(int key){
            this.key = key;
            leftChild = null;
            rightChild = null;
            deleted = false;
        }

        // Constructor with all the specified TreeNode characteristics (deleted is automatically set to false when a node is created).
        public TreeNode(int key, TreeNode leftChild, TreeNode rightChild){
            this.key = key;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            deleted = false;
        }


    }

    private TreeNode root; // Holds the contents of the root node in the BST.
    private TreeNode lastSearchedNode; // Holds the contents of the last searched node, meaning it is one node behind the root (node performing the search).
    private int numNodes; // Holds the number of nodes contained within the tree.

    // Default constructor for LazyBinarySearchTree.
    public LazyBinarySearchTree(){
        root = null;
        lastSearchedNode = null;
        numNodes = 0;
    }

    // Getter method for root.
    public TreeNode getRoot(){
        return root;
    }

    // Getter method for lastSearchedNode.
    public TreeNode getPreviousNode(){
        return lastSearchedNode;
    }

    // Returns the the current size of the BST.
    public int size(){
        return numNodes;
    }

    // This method finds the node with the smallest key contained within the BST.
    public int findMin(){
        // If the current BST is empty, return -1.
        if(isTreeEmpty()){
            return -1;
        }

        TreeNode possibleMin = null; // Holds the pointer that points to the initial minimum key value (before it is determined if the node is marked as deleted).
        TreeNode currentNode = root; // Holds the pointer performing the search.

        // Search the left subtree for the node with the smallest key value until a leaf node is reached.
        while(currentNode.leftChild != null){
            // If the currentNode is not marked as deleted, set the possibleMin to currentNode.
            if(!(markedAsDeleted(currentNode))){
                possibleMin = currentNode;
            }

            // Traverse to the current node's leftChild in the current left subtree.
            currentNode = currentNode.leftChild;
        }

        // If the currentNode pointer points to a min-value that is marked as deleted, return the key of the node that possibleMin is pointing to.
        if(markedAsDeleted(currentNode)){
            return possibleMin.key;
        } 
        else{ // Otherwise, return the key of currentNode.
            return currentNode.key;
        }
    }
    
    // This method finds the node with the largest key value contained within the BST.
    public int findMax(){
        // If the current BST is empty, return -1.
        if(isTreeEmpty()){
            return -1;
        }

        TreeNode possibleMax = null; // Holds the pointer that points to the initial max key value (before it is determined if the node is marked as deleted).
        TreeNode currentNode = root; // Holds the pointer performing the search.

        // Search the right subtree for the node with the largest key value until a leaf node is reached.
        while(currentNode.rightChild != null){
            // If the currentNode is not marked as deleted, set the possibleMax to currentNode.
            if(!(markedAsDeleted(currentNode))){
                possibleMax = currentNode;
            }

            // Traverse to the current node's rightChild in the current right subtree.
            currentNode = currentNode.rightChild;
        }

        // If the currentNode pointer points to a max value that is marked as deleted, return the key of the node that possibleMax is pointing to.
        if(markedAsDeleted(currentNode)){
            return possibleMax.key;
        } 
        else{ // Otherwise, return the key of currentNode.
            return currentNode.key;
        }
    }

    // This method determines if the current node being searched is marked as deleted.
    private boolean markedAsDeleted(TreeNode node){
        return node.deleted;
    }

    // This method determines if the current BST is empty.
    private boolean isTreeEmpty(){
        return root == null;
    }

    // This method determines if the key being inserted into the BST has a valid range (1 <= key <= 99).
    // This method will throw a IllegalArgumentException if the key has an invalid range, indicating in the message which method the error orginated from.
    private void validKeyRange(int key, String methodName){
        if(key < 1 || key > 99){
            throw new IllegalArgumentException("Error in " + methodName + ": IllegalArgumentException raised");
        }
    }

    // This method marks a specified node as deleted (helper method for delete()).
    private void markNodeAsDeleted(){
        lastSearchedNode.deleted = true;
    }

    // This method marks a specific node as not deleted (helper method for delete()).
    private void unmarkNodeAsDeleted(){
        lastSearchedNode.deleted = false;
    }

    // This method inserts a new node (with a unique key) to a leaf node within the BST.
    // Returns a boolean value, indicating is the node was successfully inserted or not.
    public boolean insert(int key){
        // Determine if the specified key is contained within the valid range (1 <= key <= 99).
        validKeyRange(key, "insert");

        // If the BST is empty, create the 1st node as the root.
        if(isTreeEmpty()){
            root = new TreeNode(key);
            numNodes++; // Increase the number of nodes contained within the BST.
            return true;
        }

        // If specified key is already contained within the BST, return false.
        if(contains(key)){
            return false;
        }
        else{ // Otherwise, the specified key is not a duplicate.
            // If the specified key is associated with a deleted node, un-mark the deleted node.
            if(lastSearchedNode != null){
                unmarkNodeAsDeleted();
            }
            else{ // Otherwise, insert a new node with the specified key and insert it at a leaf node.
                TreeNode currentNode = root; // Holds the pointer that determines where the new node is to be inserted.
                TreeNode parentNode = null; // Holds the pointer to the parent node of the new node.

                // Traverse the BST until a suitable leaf node location to insert the new node is found.
                while(currentNode != null){
                    parentNode = currentNode; // Set the parentNode to currentNode after each loop iteration to determine if the currentNode should be inserted as a right or left child of the leaf node.
                    
                    // If the specified key is less than the key at the current node, traverse to its leftChild.
                    if(key < currentNode.key){
                        currentNode = currentNode.leftChild;
                    }
                    else{ // Otherwise, traverse to currentNode's right child.
                        currentNode = currentNode.rightChild;
                    }
                }

                // If the specified key is less than the key of the parent node (of the new node to be inserted), create the new node as a leftChild of the parent node.
                if(key < parentNode.key){
                    parentNode.leftChild = new TreeNode(key);
                }
                else{ // Otherwise, create the new node as a right child of the parent node.
                    parentNode.rightChild = new TreeNode(key);
                }

                numNodes++; // Increase the number of nodes contained within the BST.
            }
        }

        // If this point has been reached, a new node has been successfully created and inserted into the BST or a deleted node with the specified key has been marked as not deleted.
        // Thus, return true.
        return true;
    }

    // This method determines if a node with a specified key is already contained within the BST.
    public boolean contains(int key){
        // Determine if the specified key is contained within the valid range (1 <= key <= 99).
        validKeyRange(key, "contains");

        // If the current BST is empty, return false.
        if(isTreeEmpty()){
            return false;
        }

        TreeNode currentNode = root; // Holds the pointer performing the search (initially points to root).

        // Search the BST for the node with the specified key until a leaf node is reached.
        while(currentNode != null){
            // If the search key is less than the key at the current node, traverse to its leftChild.
            if(key < currentNode.key){
                currentNode = currentNode.leftChild;
            }
            else if(key > currentNode.key){ // If the search key is greater than the key at the current node, traverse to its rightChild.
                currentNode = currentNode.rightChild;
            }
            else{ // Otherwise, the search key has been found.
                // Save the last searched node for future use.
                lastSearchedNode = currentNode;

                // If the search key has been found but the node is marked as deleted, return false.
                if(markedAsDeleted(currentNode)){
                    return false;
                }
                else{ // Otherwise, return true.
                    return true;
                }
            }
        }

        // If this point is reached, the search key is not contained within the BST.
        lastSearchedNode = null;
        return false;
    }

    // This method marks a node in the BST as deleted (if it exists or is not already marked as deleted).
    public boolean delete(int key){
        // Determine if the specified key is contained within the valid range (1 <= key <= 99).
        validKeyRange(key, "delete");

        // If the BST is empty, return false.
        if(isTreeEmpty()){
            return false;
        }

        // If the specified key is contained within the BST, mark the node with this key (lastSearchedNode) as deleted.
        if(contains(key)){
            markNodeAsDeleted();
            return true;
        }
        else{ // Otherwise, the node with the specified key is already marked as deleted or does not exist in the BST.
            return false;
        }

    }

    
    // These methods calculate the height of the BST, using the recursive implementation.
    public int height(){
        return height(root);
    }
    private int height(TreeNode currentNode){
        // If the current node has reached a leaf node, return -1.
        if(currentNode == null){
            return -1;
        }
        else{
            // Compute the height of the right and left subtrees.
            int leftHeight = height(currentNode.leftChild);
            int rightHeight = height(currentNode.rightChild);

            // Return the largest height-> max(leftHeight, rightHeight).
            // Add +1 to include the height of the root node.
            if(leftHeight > rightHeight){
                return (leftHeight + 1);
            }
            else{
                return (rightHeight + 1);
            }
        }
    }
    

    /*
    // This method calculates the height of the BST.
    public int height(){
        // If the current BST is empty, return 0.
        if(isTreeEmpty()){
            return 0;
        }

        TreeNode currentNode = root; // Holds the pointer that will traverse the BST.
        int leftHeight = 0; // Holds the height of the left subtree.
        int rightHeight = 0; // Holds the height of the right subtree.

        // Calculate the height of the left subtree.
        while(currentNode.leftChild != null){
            currentNode = currentNode.leftChild;
            leftHeight++;
        }

        // Calculate the height of the right subtree.
        while(currentNode.rightChild != null){
            currentNode = currentNode.rightChild;
            rightHeight++;
        }

        // Return the greater of the 2 subtree heights as the height of the BST (also works if leftHeight == rightHeight).
        // height - 1 is returned because both while-loops will terminate when currentNode is pointing to null, not when currentNode.leftChild or currentNode.rightChild is pointing to null. 
        if(leftHeight > rightHeight){
            return leftHeight + 1;
        }
        else{
            return rightHeight + 1;
        }
    }
    */

    // This method performs a pre-order traversal of the BST, printing the key of every node (deleted and not deleted).
    public String toString(){
        // If the BST is empty, return an error message.
        if(isTreeEmpty()){
            return "Error: the BST is empty";
        }

        StringBuilder preorderTraversal = new StringBuilder(); // Use the StringBuilder object to create a string of variable size to display the contents of the BST in pre-order fashion.
        Stack<TreeNode> keyStack = new Stack<>();// Create an empty stack to store the keys of all the nodes in the BST.
        keyStack.push(root); // Push the root node to the empty stack.

        // Loop until the stack is empty.
        while(!keyStack.empty()){
            // Pop the current node from the stack and add its key to the StringBuilder object.
            TreeNode currentNode = keyStack.pop();

            // If the current node is marked as deleted, append an "*" before adding the key to the string.
            if(markedAsDeleted(currentNode)){
                preorderTraversal.append("*");
            }

            preorderTraversal.append(currentNode.key);

            // Push the right child of the popped node onto the stack.
            // The right child must be pushed onto the stack 1st because the left child must be processed 1st (LIFO order).
            if(currentNode.rightChild != null){
                keyStack.push(currentNode.rightChild);
            }

            // Push the left child of the popped node onto the stack.
            if(currentNode.leftChild != null){
                keyStack.push(currentNode.leftChild);
            }

            // If stack is not empty, add a space to the string.
            // This will prevent trailing whitespace.
            if(!keyStack.empty()){
                preorderTraversal.append(" ");
            }
        }

        // Return the preorderTraversal StringBuilder object as a string.
        return preorderTraversal.toString();
    }
}