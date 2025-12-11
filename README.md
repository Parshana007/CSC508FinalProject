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

### 3. Enter player name and room code 
The room code must be the same for the two players that are playing against each other. The room code can be anything of the players' choice, and does not have to be a number.


## GUI 
Welcome Screen:
<img width="1196" height="602" alt="Screenshot 2025-12-10 at 8 48 20 PM" src="https://github.com/user-attachments/assets/ed231a6c-8abb-4ae8-a833-b27ac1f3a9cd" />

Placement Screen (where player decides where they want to place their ships)
<img width="1195" height="596" alt="Screenshot 2025-12-10 at 8 48 51 PM" src="https://github.com/user-attachments/assets/5efe8f52-293a-4274-9a06-e1098ac1d8e1" />

Guessing Screens:
For Player1:
<img width="1197" height="597" alt="Screenshot 2025-12-10 at 8 50 01 PM" src="https://github.com/user-attachments/assets/60d3b017-4982-4580-92cf-f53a9b42cc50" />

For Player2:
<img width="1197" height="604" alt="Screenshot 2025-12-10 at 8 50 45 PM" src="https://github.com/user-attachments/assets/eafec11f-b4ee-4a7b-9ad7-ccc64256707b" />

AI Recommendation
<img width="1195" height="598" alt="Screenshot 2025-12-10 at 8 51 19 PM" src="https://github.com/user-attachments/assets/87ef9376-c631-425e-8422-71a3e801f62e" />





