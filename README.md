# Vigenère Cipher Cracker

The Vigenère cipher is a type of substitution cipher used to encrypt plaintext. The key is a word that is repeated to the length of the plaintext. For example if the word to encrypt is MONATHEOCTOCAT and the key is GIT, then:
| **Plaintext**  | **MONATHEOCTOCAT** |
| :------------- | :----------------- |
| **Key**        | **GITGITGITGITGI** |
| **Ciphertext** | **SWGGBAKWVZWVGB** |

If the key is used twice to encode two different words of the same length then it becomes easy to obtain the key and decipher the words.

* First the numerical differences between a given ciphertext and each word in the
 * text file and convert these back to alphabetic characters to produce an array of potential
 * keys. This is repeated for both ciphertexts decrypt the ciphertext and print the plaintext.
