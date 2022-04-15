IDE/text editor used: Visual Studio Code

Instructions: use "MainClass.java" to run the program.

When selecting a menu option, if the user enters invalid input for the menu options (not an intger or a number not 1-7), a input validation loop will execute until the user enters a valid menu option.
The menu will be continously displayed after each menu selection until the user enters 7 (Done) to terminate the progam.

Different scenarios for the menu options in MainClass.java:
1. Make Empty
	a. When the user selects this option, a "Are you sure?" prompt will appear asking if the user if they really intend to delete the linked list and all its contents (0 for no and 1 for yes).
		a1. If the user enters invalid input (a non-integer, not 0, or not 1), a input validation loop will execute until the user selects a valid option.
	   	a2. If the user enters 1, the linked list will be deleted.
	   	a3. If the user enters 0, the linked list will not be deleted.

2. Find ID
	a. If the linked list is empty, an error message will be displayed.
	b. If the product ID the user entered is not contained within the linked list, an error message will be displayed.
	c. If the product ID the user entered is contained within the list, the product contents associated with the specified product ID will be displayed.

3. Insert At Front:
	a. If the linked list is empty, the product will be inserted into the empty list.
	b. If the user tries to enter a product with a product ID that is already contained within the list, an error message will be displayed an this product will not be added to the linked list.
	c. If the user enters a product with a product ID that is not contained within the linked list, the product will be added to the front of the list.

4. Delete From Front
	a. If the linked list is empty, an error message will be displayed.
	b. If the linked list is not empty, the product's contents will be displayed and then the product will be deleted.

5. Delete ID
	a. If the linked list is empty, an error messages will be displayed. 
	b. If the user tries to delete a product with a product ID that is not contained within the linked list, an error message will be displayed.
	c. If the user enters a product with a product ID that is contained within the linked list, the product's contents will be displayed and then the product will be deleted.

6. Print All Records
	a. If the linked list is empty, an error message will be displayed.
	b. If the linked list is not empty, all the product and its contents will be displayed.

7. Done
	a. This menu selection will display a goodbye message and terminate the program.


Extra methods and their functions:
1. empty(): returns null if the head pointer == null.

2. size(): keeps track of the number of products (nodes) in the linked list.

3. occurrence(): counts the number of duplicate product IDs within the list (there should be none). This method was used intially for debugging and now is used instead of calling the findID() method within the insertAtFront() method.

4. insertAtEnd(): inserts a product at the end of the linked list.

5. The additional getter methods in "Product.java" allow the user to access the product and supplier name instead of just the product ID.

6. The additional setter methods in "Product.java" allow the user to change the product ID, product name, and supplier name.
   These methods are helpful if the user wants to change a product's attributes that is already contained within the linked list, instead of having to delete the product and reinsert it into the list.