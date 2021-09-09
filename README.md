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

- Read in a text file containing a list of words
  - For example [10letterwordslist.txt](https://github.com/TomMakesThings/Vigenere-Cracker/blob/main/10letterwordslist.txt)
- Read in two ciphertexts encrypted with the same key
  - For example C1: `CIYWCUXEEK` and C2: `LLBCXVTOVK`
  - These do not have to be the same length
- Create a list of potential keys for each ciphertext
  - The ciphertext is compared to each word in the text file by calculating the difference between the numerical values of letters in the same positions.
  - Each numerical difference is converted to a character.
  - The characters are joined to form a candidate key that is added to a list.
  - For example C1: `[CIHTGGMJAS, CHYUJQGWEZ, CHYJZGKAAS, CHYJZGKANS, ...]` and C2: `[LLKZBHITRS, LKBAERCGVZ, LKBPUHGKRS, LKBPUHGKES, ...]`
- The lists of potential keys for C1 and C2 are compared to find a match
  - If a single match is found then this must be the reused key.
  - If multiple keys are found then the original key cannot be determined. This may occur when one or both of the ciphertexts are short.
  - If no keys are found then the plaintext for one or both of the ciphertexts is not in the word list.
- Once a key is obtained, the ciphertext can be decrypted
```
Ciphertext 1: CIYWCUXEEK
Ciphertext 2: LLBCXVTOVK
Length of shortest ciphertext: 10
Added list of words
Created list of possible keys for ciphertext 1
Created list of possible keys for ciphertext 2
Key found: TENLETTERS
Plaintext 1: JELLYBEANS
Plaintext 2: SHORTCAKES
```
- If multiple keys are found then decryptions for each key are displayed
```
Ciphertext 1: CIYWC
Ciphertext 2: LLBCXVTOVK
Length of shortest ciphertext: 5
Added list of words
Created list of possible keys for ciphertext 1
Created list of possible keys for ciphertext 2

Multiple matches for key found: [ZRYRJ, WXQEK, WXQEJ, JUBWR, IRKUV, WRQKP, KUNON, JRKCW, JRQQP, JRKCK, JRQUV, LATWQ, JRKUJ, WAHLX, XANWJ, GUHJP, BRKJJ, TENLE, KXYEV, CRYUV]

Decryption for key 1: ZRYRJ
Plaintext 1: DRAFT
Likely match: DRAFTINESS
Plaintext 2: MUDLO?????
Closest matches: [MUDLOGGERS, MUDLOGGING]

Decryption for key 2: WXQEK
Plaintext 1: GLISS
Closest matches: [GLISSADERS, GLISSADING, GLISSANDOS]
Plaintext 2: POLYN?????
Likely match: POLYNOMIAL

Decryption for key 3: WXQEJ
Plaintext 1: GLIST
Closest matches: [GLISTENING, GLISTERING]
Plaintext 2: POLYO?????
Closest matches: [POLYOLEFIN, POLYOMINOS, POLYONYMIC]

...

Decryption for key 18: TENLE
Plaintext 1: JELLY
Closest matches: [JELLYBEANS, JELLYGRAPH, JELLYROLLS]
Plaintext 2: SHORT?????
Closest matches: [SHORTBOARD, SHORTBREAD, SHORTCAKES, SHORTCRUST, SHORTENERS, SHORTENING, SHORTFALLS, SHORTGOWNS, SHORTHAIRS, SHORTHANDS, SHORTHEADS, SHORTHORNS, SHORTLISTS, SHORTSTOPS, SHORTSWORD, SHORTWAVED, SHORTWAVES]

Decryption for key 19: KXYEV
Plaintext 1: SLASH
Closest matches: [SLASHFESTS, SLASHINGLY]
Plaintext 2: BODYC?????
Likely match: BODYCHECKS

Decryption for key 20: CRYUV
Plaintext 1: ARACH
Closest matches: [ARACHNIDAN, ARACHNOIDS]
Plaintext 2: JUDIC?????
Closest matches: [JUDICATION, JUDICATIVE, JUDICATORS, JUDICATORY, JUDICATURE, JUDICIALLY]
```
