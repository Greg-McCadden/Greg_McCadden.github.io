// addition of comparator interface ??
import java.util.*;

public class LayzBinarySearchTreeV2 {
	private TreeNode root;
	private TreeNode lastSearchedNode;
	private int size;
	
	public LayzBinarySearchTreeV2() {
		size = 0;
	}
	
	// return the height.
	public int size() {
		return size;
	}
	
	public boolean insert(int key) {
		// check the validity of the specified key
		isValidKey(key, "insert");
		
		// if root is null, set a node associated with the specified key to the root node
		if(root == null) {
			root = createNewNode(key);
			// increment the size
			size++;
			return true;
		}
		
		// check whether the specified key is associated with any previously deleted nodes in the tree
		if(!contains(key)) {
			if(lastSearchedNode != null) {
				// undelete the deleted node associated with the specified key
				unMarkLastSearchedNode();
			} else {
				// create a new node associated with the specified key
				insertAtLeaf(key);
				// increment the size
				size++;
			}
		} else {
			// if the contains method returns true, it means there is already a duplicate node associated with the specified key in the tree
			return false;
		}
		
		// insertion is successfully accomplished
		return true;
	}
	
	// creates a new node
	private TreeNode createNewNode(int key) {
	    return new TreeNode(key);
	}
	
	// insert at the leaf node
	private void insertAtLeaf(int key) {
		TreeNode parent = null;
		TreeNode current = root;
		
		while(current != null) {
			parent = current;
			
			if(key < current.key) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
		}
		
		// insert the new node
		if(key < parent.key)
			parent.leftChild = createNewNode(key);
		else
			parent.rightChild = createNewNode(key);
		
	}

	public boolean delete(int key) {
		// check the validity of the specified key
		isValidKey(key, "delete");
				
		// cannot remove from an empty tree
		if(root == null)
			return false;
		
		if(contains(key))
			markLastSearchedNode();
		else
			// either the specified element is not in the tree or is already marked as deleted
			return false;
		
		// deletion is successfully accomplished
		return true;
			
	}
	
	public boolean contains(int key) {
		// check the validity of the specified key
		isValidKey(key, "contains");
		
		if(root == null)
			return false;
		
		TreeNode current = root;
		
		while(current != null) {
			if(key < current.key) {
				current = current.leftChild;
				
			} else if (key > current.key){
				current = current.rightChild;
				
			} else {
				// record the last visited node for future use
				lastSearchedNode = current;
				
				if(isMarked(current))
					return false;
				else
					return true;
			}
		}
		
		// the searched key is not associated with any of the nodes present in the tree
		lastSearchedNode = null;
		// key is not present 
		return false;
	}
	
	private void isValidKey(int key, String methodName){
		// check the value of key
		if(key < 1 || key > 99)
			throw new IllegalArgumentException("Error in " + methodName + ": IllegalArgumentException raised");
	}
	
	// for recovering previously deleted nodes
	private void unMarkLastSearchedNode() {
		lastSearchedNode.deleted = false;
	}
	
	// for deletion
	private void markLastSearchedNode() {
		lastSearchedNode.deleted = true;
	}
	
	private boolean isMarked(TreeNode node) {
		return node.deleted;
	}
	
	public int findMin() {
		if(root == null)
			return -1;
		
		TreeNode minBeforeDeleted = null;
		TreeNode current = root;
		
		while(current.leftChild != null) {
			
			if(!isMarked(current)) {
				minBeforeDeleted = current;
			} 
			
			current = current.leftChild;
		}
		
		return isMarked(current) ? minBeforeDeleted.key : current.key;
	};
	
	public int findMax() {
		if(root == null)
			return -1;
		
		TreeNode maxBeforeDeleted = null;
		TreeNode current = root;
		
		while(current.rightChild != null) {
			
			if(!isMarked(current)) {
				maxBeforeDeleted = current;
			} 
			
			current = current.rightChild;
		}
		
		return isMarked(current) ? maxBeforeDeleted.key : current.key;
	};
	
	// the height of a leaf can either be 0 or 1 depending on whether edges or nodes are being counted in the tree.
	//https://stackoverflow.com/questions/4065439/height-of-a-tree-with-only-one-node
	public int height() {
		if(root == null)
			return 0;
		
		Queue<TreeNode> q = new LinkedList<>();
		TreeNode current = root; 
		// add the root node to the queue
		q.offer(current);
		
		// initial heigth is 1
		int height = -1;
		
		while(!q.isEmpty()) {
			int size = q.size();
			
			// remove the nodes exist on the current level from the queue
			for(int i = 0; i < size; ++i) {
				TreeNode node = q.poll();
				
				if(node.leftChild != null)
					q.offer(node.leftChild);
				
				if(node.rightChild != null)
					q.offer(node.rightChild);
			}
			
			height++;
		}
		
		return height;
	}
	
	// print the tree in pre order
	public String toString() {
		StringBuilder preOrder = new StringBuilder();
		Stack<TreeNode> stack = new Stack<>();
		TreeNode current = root;
		
		// push the root's key to the stack
		stack.push(current);
		// traverse the rest of the tree in pre order
		while(!stack.isEmpty()) {
			TreeNode node = stack.pop();
			
			// check if the current node is marked as deleted
			if(isMarked(node)) {
				preOrder.append("*");
			}
			
			// push the right child of the current node, if present
			if(node.rightChild != null)
				stack.push(node.rightChild);
			
			// push the left child of the current node, if present
			if(node.leftChild != null) 
				stack.push(node.leftChild);
			
			// append the key value of the current node followed by a space
		    preOrder.append(node.key + (!stack.isEmpty()? " " : ""));
			
		}
		
		// return the pre order traversal of the tree as a string
		return preOrder.toString();
	}
	
	private class TreeNode {
		protected int key;
		protected TreeNode leftChild;
		protected TreeNode rightChild;
		protected boolean deleted;
		
		public TreeNode (int key) {
			this.key = key;   
		}
		
		@SuppressWarnings("unused")
		public TreeNode (int key, TreeNode leftChild, TreeNode rightChild) {
			this.key = key;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
		}
		
	}
}
