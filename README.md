# ABC9
I'm working on this project with the purpose of learning to program for Android. 
This is an app that helps children to practice as they are learning to read and write.

Here are some app features expressed as user stories that are met by the app so far:

Version: 1.0.2.3

- The game shows an image, and the user is prompt to complete the word corresponding with that image, 
  with one or more of the 4 options the player gets to choose from.

- There are 2 display modes that are shown randomly every turn: 
- In the first one, the user must write the whole word with the sylables shown.
  Once all the correct syllables are put each on a different random answer button, the program should fill then all the buttons left with random incorrect syllables.
  These must not repeat themselves nor the correct syllables.
- In the second display mode, the answer is shown lacking a letter. The user must choose the correct letter from the 4 given ones to complete the word.
  Once the correct letter is put on a random answer button, the program should fill all the buttons left with random letters. 
  These must not repeat themselves nor the correct letter.
  
- Every word correspond to a level (it is posible for a word to be present in more than one level). When the player accumulate 4 correct answers, the player levels up. 

- If the player makes a mistake, he/she loses one of three stars (lives). The game shows another image with its corresponding word to solve 

- In the end, when the player makes 3 mistakes, the score is shown. And he/she can restart the game or go to the homescreen.

- LevelActivity is working with a MVP pattern

- The player can choose to play in lowercase or uppercase mode

- The user can turn the music and sound on and off, separately.


Roadmap

// technic debt:
- HomeActivity (actual MainACtivity) should be structured with MVP pattern
- GameOverActivity should de strictured with MVP pattern
- Build unit testing with Mockito
- Build UI testing with Espresso

// Future features:
- The user should be able to create an account on a local DB. (playername, level, records)
- The top 10 all players highscore should be kept and shown.
- User should be able to create its own "card" (set of: word, syllables, level, image).
- There should be 2 game modes: The existing one as a Marathon. 
  The second game mode should separate all the words from a single level into different playable levels/lessons (as in duolingo for example)

// Firebase:
- The user should be able to create an account with Firebase.
- Every new user will become a "tutor account" and every "tutor account" will be able to manage several "student accounts"
- User should be able to create "win prizes", by setting an image, a description of the prize, and the condition/s. 
  (e.g: "achieving 200 points", or "playing 10 days in a row", etc)
- The data base should be stored in Firestore and also persist on the local SQLite database (using Room)

// More
- Users can share, download, edit, and rate collections of cards* that they create (*a card bundles an image, a word, and its correct sylables)

// so... this will be the public sight limit of this roadmap so far.

Thank you for reading!
If you have any comments, feel free to contact me at: la.torre.encantada@gmail.com

Fernando Espinosa

