# ABC9
I'm working on this project with the purpose of learning to program for Android. 
This is an app that helps children to practice as they are learning to read and write.

Here is a description of the app features, expressed as user stories that are met by the app so far:

- The game shows an image, and the user is prompt to complete the word corresponding with that image, 
  with one or more of the 4 options he/she gets to choose from.

- There are 2 display modes that are shown randomly every turn. 
- In the first one, the user must write the whole word with the sylables shown.
  Once all the correct syllables are put on random answer buttons, the program should fill then all the buttons left with random incorrect syllables.
  These must not repeat themselves nor the correct syllables.

- In the second display mode, the answer is shown lacking a letter. The user must choose the correct letter from the 4 given ones to complete the word.
  Once the correct letter is put on a random answer button, the program should fill all the buttons left with random letters. 
  These must not repeat themselves nor the correct letter.
  
- Every word correspond to a level (it is posible for a word to be present in more than one level). When the player accumulate 5 correct answers, he/she levels up. 

- If the user makes a mistake, he/she loses one of three stars (attempts), and the game draws another card (set of image, and correct and incorrect syllables). 

- In the end, when the player makes 3 mistakes, the score is shown. And he/she can restart the game.

- LevelActivity is working on MVP pattern



Roadmap (future features expressed as user stories)

- The user should be able to create an account on a local DB. (playername, level, records)
- The top 10 all players highscore should be kept and shown.
- The rest of App should also work with MVP pattern
- User should be able to create its own "card" (set of: word, syllables, level, image).
- There should be 2 game modes: The existing one as a Marathon. 
  The new game mode should separate all the words from a single level into different playable levels/lessons (e.g. as in duolingo)
- Unit testing with Mockito should be implemented
- The user should be able to create an account with Firebase.
- Every user will be a "tutor account" and every "tutor account" will be able to manage several "student accounts"
- The data base should be stored in Firestore and also persist on the local SQLite database (using Room)
- User should be able to create win prices 
(setting image, description of the price, and condition. e.g: achieving 200 points, or playing 10 days in a row, etc)


Contact: la.torre.encantada@gmail.com

