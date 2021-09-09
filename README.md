# Vigenère Cipher Cracker

### What is the Vigenère Cipher?

This is a type of substitution cipher used to encrypt plaintext. The key is a word that is repeated to the length of the plaintext. Each letter is assigned a numeric value and the plaintext and key are added to obtain the ciphertext. For example if the word to encrypt is MONATHEOCTOCAT and the key is GIT, then:

|                | Character      | Numeric                                                         |
| :------------- | :------------- | :-------------------------------------------------------------- |
| **Plaintext**  | MONATHEOCTOCAT | M:12 O:14 N:13 A:0 T:19 H:7 E:4 O:14 C:2 T:19 O:14 C:2 A:0 T:19 |
| **Key**        | GITGITGITGITGI | G:6 I:8 T:19 G:6 I:8 T:19 G:6 I:8 T:19 G:6 I:8 T:19 G:6 I:8     |
| **Ciphertext** | SWGGBAKWVZWVGB | S:18 W:22 G:6 G:6 B:1 A:0 K:10 W:22 V:21 Z:25 W:22 V:21 G:6 B:1 |

<br>

### Breaking the Cipher

The Vigenère cipher is hard to crack providing a key is never reused. However, given two ciphertexts encrypted under the same key and a list of potential plaintext solutions it becomes possible to obtain the key through brute force as demonstrated in [VigenereCracker.java](https://github.com/TomMakesThings/Vigenere-Cracker/blob/main/VigenereCracker.java).

#### Algorithm Steps:

- Read in the text file containing a list of words and two ciphertexts (C1 and C2)
- Create a list of potential keys for each ciphertext
  - The ciphertext is compared to each word in the text file by calculating the difference between the numerical values of letters in the same positions.
  - Each numerical difference is converted to a character.
  - The characters are joined to form a candidate key that is added to a list.
- The lists of potential keys for C1 and C2 are compared to find a match
  - If a single match is found then this must be the reused key.
  - If multiple keys are found then the original key cannot be determined. This may occur when one or both of the ciphertexts are short.
  - If no keys are found then the plaintext for one or both of the ciphertexts is not in the word list.
- Once a key is obtained, the ciphertext can be decrypted
  - If multiple keys are found then decryptions for each key are displayed.
