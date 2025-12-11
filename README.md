# CSC508FinalProject

## Names: Parshana Sekhon, Sadie Fisher, Mio Nakagawa

## MQTT-Based Battleship
Our project is to implement an online version of the board game Battleship. The premise of the game is as follows:
1. Two players place “ships” of varying dimensions over a grid representation of a sea. Player 1 and Player 2 each have their own “sea” with their own ships. Points on the grid can be specified by a letter referring to the column and a number referring to the row, i.e. C12.
2. Players take turns selecting points on the other player’s grid, where they think the other player has a ship placed. If they “miss” (the point they guess is not occupied by a ship), the other player takes their turn. If they get a “hit” (pick a point that is occupied by all or part of a ship), they get to guess another point until they “miss”.
3. The game continues until one player has “sunk” (hit all points occupied by) all of the other player’s ships.
4. During each player's turn, they can get a ship guess placement from an LLM model (that has knowledge of the player's hits, misses, and sunken ships), which will show up as a pop-up if "Ask AI" is clicked on the bottom right corner.

Game Link (Rules): https://www.thesprucecrafts.com/the-basic-rules-of-battleship-411069 

## Running the Application
### 1. Clone
Clone the repository:

```bash
git clone https://github.com/Parshana007/CSC508FinalProject.git
```
### 2. Run on two separate terminals or computers
Each instance represents a player of Battleship.

```bash
mvn exec:java -Dexec.mainClass="org.Main"
mvn exec:java -Dexec.mainClass="org.Main"
```

### 2. Enter player name and room code 
The room code must be the same for the two players that are playing against each other. The room code can be anything of the players' choice, and does not have to be a number.





