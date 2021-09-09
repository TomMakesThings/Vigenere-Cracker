/* Vigenere Cracker
 * 
 * Enter two ciphertexts encoded with the same key using a Vigenere cipher and a text 
 * file containing a word list of possible solutions. The key (or multiple candidates) 
 * and decrypted plaintexts are then printed.
 * 
 * For more detail see: https://github.com/TomMakesThings/Vigenere-Cracker
 * 
 *  */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class VigenereCracker {
	private String ciphertext1 = "";
	private String ciphertext2 = "";
	private String plaintext1 = "";
	private String plaintext2 = "";
	private String key = "";
	private Integer size = 0;
	private ArrayList<String> words = new ArrayList<String>();
	private ArrayList<String> keys1 = new ArrayList<String>();
	private ArrayList<String> keys2 = new ArrayList<String>();
	private ArrayList<String> candidateKeys = new ArrayList<String>();
	
	/* Takes two ciphertexts and a file path */
	public VigenereCracker(String c1, String c2, String filePath) throws Exception {
		super();
		
		// Read the ciphertext, remove bad characters and convert to uppercase
		this.ciphertext1 = this.filterText(c1);
		this.ciphertext2 = this.filterText(c2);
		// Set size as the length of the shortest ciphertext
		this.size = Math.min(ciphertext1.length(), ciphertext2.length());
		
		System.out.println("Ciphertext 1: " + this.ciphertext1);
		System.out.println("Ciphertext 2: " + this.ciphertext2);
		
		if (this.size > 0) {
			System.out.println("Length of shortest ciphertext: " + this.size);
		}
		else {
			throw new IllegalArgumentException("Invalid ciphertext");
		}
		
		// Reads words from file
		this.setWords(filePath);
		// Finds possible key combinations for ciphertext
		this.findPossibleKeys(this.ciphertext1);
		this.findPossibleKeys(this.ciphertext2);
		// Find matches in key sets
		this.findMatchingKeys();
		// Decrypt the ciphertext
		this.decryptCiphertext();
	}
	
	/* Remove non-alphabetic characters from text */
	private String filterText(String text) {
		text = text.replaceAll("[0-9]","");
		String[] parts = text.split("\\W+");
        String result = new String();
        for(int i = 0; i < parts.length; i++){
           result = result + parts[i];
        }
        return result.toUpperCase();
	}
	
	/* Takes a file path to set the list of words */
	private void setWords(String filePath) {
		ArrayList<String> words = new ArrayList<String>();
	    try {
	        File file = new File(filePath);
	        Scanner scanner = new Scanner(file);  
	        while (scanner.hasNextLine()) {
	          // Read each word from the file
	          String data = scanner.nextLine();
	          // Remove all non-alphabetic characters
	          String word = this.filterText(data);
	          // Add the word if it is long enough
	          if (word.length() >= this.size) {
	        	 words.add(word);
	          }
	        }
	        scanner.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred");
	        e.printStackTrace();
	      }
	    if (words.size() > 0) {
	    	this.words = words;
	    	System.out.println("Added list of words");
	    }
	    else {
	    	System.out.println("No words found");
	    }
	}
	
	/* Compare the ciphertext to each word and record the difference as a potential key */
	private void findPossibleKeys(String ciphertext) {
		Iterator<String> i = this.words.iterator();
		ArrayList<String> keys = new ArrayList<String>();

		// Iterates through words in word list
		while (i.hasNext()) {
			String word = i.next();
			StringBuffer key = new StringBuffer();
			// Iterates through first n letters in word based on length of shortest ciphertext
			for (int j = 0; j < this.size; j++) {
				// Difference between characters at same position
				int difference = ciphertext.charAt(j) - word.charAt(j);
				if (difference < 0) {
					difference += 26;
				}
				// Converts difference to a letter
				char letter = (char) (difference + 65);
				key.append(letter);
			}
			// Adds key to list of possible keys
			keys.add(key.toString());
		}
		// Checks that keys added
		if (keys.size() > 0) {
			if (ciphertext.contentEquals(this.ciphertext1)) {
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
	
	/* Find if the ciphertexts share a candidate key */
	private void findMatchingKeys() {
		// Find keys present for both ciphertexts
		ArrayList<String> matchingKeys = new ArrayList<>(this.keys1);
		matchingKeys.retainAll(this.keys2);
		// Remove any duplicated values
		ArrayList<String> candidateKeys = new ArrayList<String>(new HashSet<String>(matchingKeys));
		
		if (candidateKeys.size() > 0) {
			if (candidateKeys.size() > 1) {
				System.out.println("\nMultiple matches for key found: " + candidateKeys);
			}
			else {
				System.out.println("Key found: " + candidateKeys.get(0));
			}
			this.candidateKeys = candidateKeys;
		}
		else {
			System.out.println("No matching keys");
		}
	}
	
	private void decryptCiphertext() throws Exception {
		if (this.candidateKeys.size() > 0) {
			String ciphertext = "";
			StringBuffer plaintextBuffer;
			
			for (int key_idx = 0; key_idx < this.candidateKeys.size(); key_idx++) {
				
				key = this.candidateKeys.get(key_idx);
				
				if (this.candidateKeys.size() > 1) {
					System.out.println("\nDecryption for key " + (key_idx + 1) + ": " + key);
				}
				
				// Set the ciphertext to decrypt
				for (int i = 0; i < 2; i++) {
					plaintextBuffer = new StringBuffer();
					
					if (i == 0) {
						ciphertext = this.ciphertext1;
					}
					else {
						ciphertext = this.ciphertext2;
					}
					
					for (int j = 0; j < this.size; j++) {
						// Difference between characters at same position
						int difference = ciphertext.charAt(j) - key.charAt(j);
						if (difference < 0) {
							difference += 26;
						}
						// Converts difference to a letter
						char letter = (char) (difference + 65);
						plaintextBuffer.append(letter);
					}
					
					// Extract the string from the string buffer
					String plaintext = plaintextBuffer.toString();
					
					ArrayList<String> possibleMatches = new ArrayList<String>();
					
					// Find whole matching words in given word list
					for (int k = 0; k < this.words.size(); k++) {
						String word = this.words.get(k);
						// Check if plaintext is a substring of a word
						if (word.startsWith(plaintext, 0)) {
							possibleMatches.add(word);
						}
					}
					
					String plaintextPrint = plaintext;
					
					// Add question marks to the plaintext if the key is shorter than the ciphertext
					if (this.ciphertext1.length() != this.ciphertext2.length()) {
						int sizeDifference = ciphertext.length() - this.size;
				        StringBuilder builder = new StringBuilder(sizeDifference);
				        
				        // Add ? for each missing character, e.g. CIPH??
				        for(int l = 0; l < sizeDifference; l++){
				        	builder.append("?");
				        }
				        
				        plaintextPrint = plaintext + builder.toString();
					}
					
					System.out.println("Plaintext " + (i + 1) + ": " + plaintextPrint);
					
					// Check if the plaintext is a substring of a word in the text file and print if so
					if (possibleMatches.size() == 1 && !possibleMatches.get(0).contentEquals(plaintext)) {
						plaintext = possibleMatches.get(0);
						System.out.println("Likely match: " + plaintext);
					}
					// Otherwise print the closest matching words, so long the plaintext is not included
					else if (possibleMatches.size() > 1 && !possibleMatches.contains(plaintext)) {
						System.out.println("Closest matches: " + possibleMatches);
					}
					
					// Set the plaintext that was decrypted using the key
					if (i == 0) {
						this.plaintext1 = plaintext;
					}
					else {
						this.plaintext2 = plaintext;
					}
				}
			}
		}
		else {
			throw new Exception("Cannot decrypt ciphertext as key not found");
		}
	}
	
	public static void main(String[] args) throws Exception {
		VigenereCracker cracker = new VigenereCracker("AFZAYMSFCU", "HNVXJKMKIG", "10letterwordslist.txt");
	}
}
