# Wordle in Java  

---
Author: Zhengxiao Wu  
QMUL Number: 200978936  
BUPT Number: 2020213235  

---


Wordle in Java is a parody of [Wordle in the New York Times](https://www.nytimes.com/games/wordle/index.html). The original version is a web game developed by Josh Wardle. This project uses Java and its Swing framework to implement similar functionality.  

## Requirements
- OpenJDK 18+  
The rest of the versions of Java may work, but I have not tested them.  

## Build and run
```bash
cd Wordle_MiniProject_ZhengxiaoWu
javac -d class src/*
java -cp class Main
```
A window will then be displayed to select the file containing the words, select the appropriate txt to run.  
It is recommended that you select the `5_digit_combined_wordlist.txt`, from [WordleCompetition](https://github.com/Kinkelin/WordleCompetition/blob/main/data/official/combined_wordlist.txt).  

## How to play  
### Game rules
- The words in the game are all five digits, and the game will automatically choose a word from the list of words as the answer.  
- You can make 6 word guesses and the color of each letter border will change after each word guess:  
  - <span style="color:green">Green</span> indicates that the letter is correct and in the correct position;
  - <span style="color:yellow">Yellow</span> indicates that it is in the answer but in the incorrect position;
  - <span style="color:gray">Gray</span> means that it is not in the answer at all.  
- As with the original Wordle, recurring letters may still be grayed out, for example when the answer is `RAPID`, typing <span style="color:yellow">A</span><span style="color:gray">P</span><span style="color:green">P</span><span style="color:gray">L</span><span style="color:gray">E</span> will result in the first P being grayed out and the second P green to indicate that there is only one letter P in the answer.  
- If the correct word is guessed within 6 times, then the game is won; otherwise, the game is lost.  

### Game operations  
- After selecting the word file, the main screen of the game has a title and three buttons. Click the Start button to start the game.  
- Use the keyboard's A-Z keys to enter letters. You can use the Backspace key to delete the last letter entered on the same line.  
- After entering a complete five-digit word, press Enter to check the word. Each letter will change color according to the rule. If you are unable to perform the check, check the text prompt below the word box.  
- If you really can't guess the word, you can press the Answer button to get the target word by cheating.  

## Other features  
Run the game with parameters to change the maximum number of attempts and word length of the game:  
```bash
java -cp class Main $MAX_TRIES $WORD_LENGTH
```
For example, the following command will start a game with a word length of 7 and 8 attempts. Loading `7_digit_wordlist.txt` will allow you to play the game.  