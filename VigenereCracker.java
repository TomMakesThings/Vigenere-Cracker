/* Vigenere Cracker */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class VigenereCracker {
	private String ciphertext1 = "";
	private String ciphertext2 = "";
	private String key = "";
	private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<String> keys1 = new ArrayList<String>();
	private ArrayList<String> keys2 = new ArrayList<String>();
	
	/* Takes two ciphertexts and a file path */
	public VigenereCracker(String c1, String c2, String filePath) {
		super();
		this.ciphertext1 = c1;
		this.ciphertext2 = c2;
		// Reads words from file
		this.setWords(filePath);
		// Finds possible key combinations for ciphertext
		this.findPossibleKeys(1);
		this.findPossibleKeys(2);
		// Find matches in key sets
		this.findMatchingKeys();
	}
	
	/* Takes a file path to set the list of words */
	private void setWords(String filePath) {
		ArrayList<String> words = new ArrayList<String>();
	    try {
	        File file = new File(filePath);
	        Scanner scanner = new Scanner(file);  
	        while (scanner.hasNextLine()) {
	          String data = scanner.nextLine();
	          words.add(data);
	        }
	        scanner.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred");
	        e.printStackTrace();
	      }
	    if (words.size() > 0) {
	    	// Removes dot from end of list
	    	if (words.get(words.size()-1).length() != 10) {
	    		words.remove(words.size() - 1);
	    	}
	    	this.words = words;
	    	System.out.println("Added list of words");
	    }
	    else {
	    	System.out.println("No words found");
	    }
	}
	
	private void findPossibleKeys(int number) {
		Iterator<String> i = this.words.iterator();
		ArrayList<String> keys = new ArrayList<String>();
		String ciphertext = "";
		
		if (number == 1) {
			ciphertext = this.ciphertext1;
		}
		else {
			ciphertext = this.ciphertext2;
		}
		
		// Iterates through words in word list
		while (i.hasNext()) {
			String word = i.next();
			StringBuffer key = new StringBuffer();
			// Iterates through 10 letters in word
			for (int j = 0; j < 10; j++) {
				// Difference between characters at same position
				int difference = ciphertext.charAt(j) - word.charAt(j);
				if (difference < 0) {
					difference += 26;
				}
				// Converts difference to letter
				char letter = (char) (difference + 65);
				key.append(letter);
			}
			// Adds key to list of possible keys
			keys.add(key.toString());
		}
		// Checks that keys added
		if (keys.size() > 0) {
			if (number == 1) {
				// Updates list of possible keys
				this.keys1 = keys;
				System.out.println("Created list of possible keys for ciphertext 1");
			}
			else {
				this.keys2 = keys;
				System.out.println("Created list of possible keys for ciphertext 2");
			}
		}
		else {
			System.out.println("No keys found");
		}
	}
	
	private void findMatchingKeys() {
		Iterator<String> i = this.keys1.iterator();
		ArrayList<String> matchingKeys = new ArrayList<String>();
		
		while (i.hasNext()) {
			String key1 = i.next();
			
			Iterator<String> j = this.keys2.iterator();
			while (j.hasNext()) {
				String key2 = j.next();
				if (key1.equals(key2)) {
					matchingKeys.add(key1);
				}
			}
		}
		
		if (matchingKeys.size() > 0) {
			this.key = matchingKeys.get(0);
			System.out.println("Key found: " + this.key);
		}
		else {
			System.out.println("No matching keys");
		}
	}
	
	public static void main(String[] args) {
		VigenereCracker cracker = new VigenereCracker("AFZAYMSFCU", "HNVXJKMKIG", "10letterwordslist.txt");
	}
}
