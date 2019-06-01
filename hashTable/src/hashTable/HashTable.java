package hashTable;
import java.util.Scanner;
/**
 * 
 * @author muzhouchen
 * last modified: May 30, 2019
 * Overview: This program is used to create a HashTable; it allow user to build a HashTable and play with it;
 * Please use this program by following the rules: first enter a positive integer to set the table size, then
 * enter 1 for inserting, 2 for deleting, 3 for printing out the table, 4 for searching, and 5 to end . 
 *
 */

public class HashTable {
	//field: which used to store current size, max size and keys. 
	private int currentSize;
	private int maxSize;
	private Integer[] keys;
	//constructor, initialize current size
	public HashTable(int capacity) {
		currentSize = 0;
		this.maxSize = capacity;
		keys = new Integer[capacity];
	}
	//basic method to get keys; 
	public Integer[] getKeys() {
		return keys;
	}

	//basic method to set the keys;
	public void setKeys(Integer[] keys) {
		this.keys = keys;
	}

	//when the current size / max size is greater than 0.8, we determine the table is full so that we need 
	//to double size the table;
	public boolean isFull() {
		return currentSize/maxSize >= 0.8;
	}
	//this method is to figure out the location of where should we place the key we insert
	//we using quadratic probing which means x +1 , x+4, x+9 etc.
	private int hash(int key) {
		int probing = 1; 
		//if the original location of the key is empty, we just need to place it in; 
		//if the original location of the key is not empty, we keep searching x + probing until we find an empty place.
		while(keys[key%maxSize] != null) {
			key = key + probing * probing;
			probing ++ ;
		}
		return key%maxSize;
	}
	//search method: just use a for loop to search what we need in keys array;
	//return -1 when key is not in the table; 
	public int search(int key) {
		for(int i = 0; i < keys.length; i++) {
			if(keys[i] !=null && keys[i] == key) {
				return i;
			}
		}
		return -1;
	}
	//the delete method, we do a search to get the location of the key and delete it in keys array; 
	public void delete(int key) {
		System.out.println(key);
		int location = search(key);
		System.out.println(location);
		if(location != -1 ) {
			keys[location] = null;
			this.currentSize -= 1;
		}
	}
	//the insert method, get the final location of the key that we insert and place it into the array; 
	public void insert(int key) {
		keys[hash(key)] = key;
		this.currentSize += 1;
		//this is the case when the table is 80% full, we rehash all the keys into a new keys array which 
		//has double size; 
		if(this.isFull()) {
			reHash();
		}
	}
	//the reHash method used to rehash keys when the table is 80% full;
	public void reHash() {
		//create a new space for it; 
		HashTable tempTable = new HashTable(this.maxSize*2);
		//use insert method to insert all keys we have into a tempTable;
		for(int i = 0 ; i < this.keys.length; i++) {
			if(this.keys[i] == null) {
				continue;
			}
			tempTable.insert(keys[i]);
		}
		//reset the maxsize
		this.maxSize = this.maxSize *2;
		//use getKeys to point all keys from tempTable to this.keys;
		this.keys = tempTable.getKeys();
		
	}
	//the print array method; using for loop to print all keys in table, null represent nothing in that spot; 
	public void printArray() {
		System.out.println("Table");
		for(int i = 0 ; i < this.keys.length; i++) {
			System.out.println(i+ "     "+ keys[i]);
		}
		System.out.println("-------------");
	}
	//the main function;
	public static void main(String[]args) {
		//use scanner to catch all input from user; 
		Scanner input = new Scanner(System.in);
		//ask user to enter a positive number; 
		System.out.println("Enter a positive integer to set the table size: ");
		HashTable ht = new HashTable(input.nextInt());
		int choice = 0;
		//use do while loop so that the program stop when user enter a 5; 
		do {										
			input.nextLine();
			System.out.println("1. To insert integer to the table");
			System.out.println("2. To delete integer from table");
			System.out.println("3. Print out the table");
			System.out.println("4. Search location of a integer in the table");
			System.out.println("5. Quit");
			choice = input.nextInt();
			//use switch case to choose what method we need to use by user's input; 
			switch(choice) {
			//5 cases; 
				case 1:
					System.out.println("enter an integer you want to insert: ");
					ht.insert(input.nextInt());
					break;
				case 2:
					System.out.println("enter an integer you want to delete");
					ht.delete(input.nextInt());
					break;
				case 3:
					ht.printArray();
					break;
				case 4:
					System.out.println("enter an integer you want to search: ");
					int result = ht.search(input.nextInt());
					if(result == -1) {
						System.out.println("This integer can not be found in table");
						System.out.println();
					}
					else {
						System.out.println("The integer exists, the location of it is: " + result);
					}
					break;
				case 5:
					System.out.println("<end>");
					input.close();
			}
		}while(choice != 5);
	}
}
