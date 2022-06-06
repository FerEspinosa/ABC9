# Hello world!

This is an android app that helps children to practice as they are learning to read and write (In spanish).
It is the project I am using to learn to program android apps.

In the last version (1.0.2.5):

I am using 100% java.
The Level Activity has a MVP architechtural pattern. 
The MainActivity (Home) and GameOverActivity contain all their respective methods within each respective class 
(no architectural pattern nor interfaces)   

user's stories already met by the app:

- The game shows an image, and the user is prompt to complete the word corresponding with that image, 
  with one or more of the 4 options the player gets to choose from.

- There are 2 display modes that are shown randomly every turn: 
- In the first one, the user must write the whole word with the sylables shown.
  Once all the correct syllables are put each on a different random answer button, the program should fill then all the buttons left with random incorrect syllables.
  These must not repeat themselves nor the correct syllables.
- In the second display mode, the answer is shown with a missing letter. The user must choose the correct letter from the 4 given ones to complete the word.
  Once the correct letter is put on a random answer button, the program should fill the rest of the buttons with random letters. 
  These must not repeat themselves nor the correct letter.
  
- Every word correspond to at least one level. 
- When the player accumulates 4 correct answers, the player levels up 
- only random words from the corresponding level will show up in every turn.

- If the player makes a mistake, he/she loses one of three stars (lives). The game shows another image with its corresponding word to solve 

- When the player makes 3 mistakes, the game is over. 
- The score is shown on the "game over screen". 
- From there, the player can play again or go to the homescreen.
- The maximun posible score divided by 10, decides wich of the 10 predetermined messages are shown to the player. 

- The player can choose to play in lowercase or uppercase mode

- The user can turn the music and sound on and off, separately.


Roadmap

// technic debt:
- HomeActivity (actual MainACtivity) should be structured with MVP pattern
- GameOverActivity should be structured with MVP pattern
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

// Even more? 
Yes! I have seen this project go so big in my mind! 
Anyway, I think the roadmap up to this point is already a nice challenge to focus on for a while.

Thank you for reading!
If you have any comments, feel free to contact me at: la.torre.encantada@gmail.com

Fernando Espinosa

