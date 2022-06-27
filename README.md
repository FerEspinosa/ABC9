# Hello world!

This is an android app that helps children to practice as they are learning to read and write (In spanish).
It is the project I am using to learn to program android apps.

I am using 100% java.
There are 3 Activities: HomeActivity, NivelActivity and GameOverActivity. Each of them has MVP architechtural pattern. 

Some user's stories already met by the app:

- The game shows an image, and the user is prompt to complete the word corresponding with that image, 
  with one or more of the 4 options the player gets to choose from.

- There are 2 display modes that are shown randomly every turn: 
- In the first one, the user must write the whole word with the sylables shown.
  Once the program has put all the correct syllables on different random answer buttons, the program fill the empty answer buttons with random incorrect syllables.
  These must not repeat themselves nor the correct syllables.
- In the second display mode, the answer is shown with a missing letter. The user must choose the correct letter from the 4 given ones to complete the word.
  Once the program has put the correct letters on random answer buttons, it should fill the rest of the buttons with random incorrect letters. 
  These must not repeat themselves nor the correct letters.
  
- Every word correspond to at least one level. 
- When the player accumulates 4 correct answers, the player levels up.
- Only random words from the corresponding level will show up in every turn.

- If the player makes a mistake, he/she loses one of three stars (lives). Then the game shows another image with its corresponding word to solve.

- When the player makes 3 mistakes, the game is over. 
- The score is shown on the "game over screen". 
- From there, the player can play again or go to the homescreen.
- The maximun posible score divided by 10, decides wich of the 10 predetermined messages are shown to the player. 

- HomeActivity has a button that shows/hide the options menu.
- The player can choose to play in lowercase or uppercase mode.
- The user can turn the music and sound on and off, separately.


Roadmap

// technic debt:
- Build unit testing with Mockito
- Build UI testing with Espresso

// Future features:

- The user should be able to create an account on a local DB. (playername, level, records)

- There should be 2 game modes: The existing one as a Marathon. 
  The second game mode should separate all the words from the single level into different playable levels/lessons (think of the way duolingo shows its lessons)
  Words will be grouped together in different units. For example, the first unit could contain the words MAMA and PAPA. And the level could consist in solving those two words like 5 or 6 times. Then, the next level will contain words with the same syllables as the previous level/s, plus another syllable. For example the word MAPA (that contains the same syllables from level 1), and the word CAMA (with the new syllable "CA"), and so forth.
This will make the game sessions smaller, so that the kids don't get bored. And also, it would let them see their progress as they advance from one level to another.

- User should be able to create their own "cards" (set of: word, correct syllables, level, image), that would be added to new levels.

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
