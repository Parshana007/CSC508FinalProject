//package csc508;
package org;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Point;
import java.util.function.Function;

/**
 * Handles creation of the prompt to send to the Groq API based on the current game board.
 */

public class AI {
    private LlamaClient llama;

    public AI() {
        llama = new LlamaClient(Config.get("GROQ_API_KEY"));
    }

    public static char numberToLetter(int n) {
        char[] letters = {'A','B','C','D','E','F','G','H','I','J'};
        return letters[n];
    }

    public String getRecommendation() {
        List<Point> hitList = Blackboard.getInstance().getOpponentState().getHits();
        List<Point> missList = Blackboard.getInstance().getOpponentState().getMisses();
        List<Ship> sunkList = Blackboard.getInstance().getOpponentState().getSunkShips();

        Function<Point, String> toBattleship = p -> {
            char col = numberToLetter(p.x);
            int row = p.y + 1;
            return col + String.valueOf(row);
        };
        String hits = hitList.isEmpty() ? "empty" : hitList.stream().map(toBattleship).collect(Collectors.joining(", "));
        String misses = missList.isEmpty() ? "empty" : missList.stream().map(toBattleship).collect(Collectors.joining(", "));
        String sunk = sunkList.isEmpty() ? "empty" : sunkList.stream()
                .map(ship -> ship.getCoordinates().stream().map(toBattleship).collect(Collectors.joining("-")))
                .collect(Collectors.joining(", "));

        // prompt sent to model
        String prompt = """
You are an AI agent playing Battleship on a 10x10 grid.

Your task is to choose the NEXT coordinate to fire at.

GAME RULES / CONSTRAINTS:
- The grid uses columns A–J and rows 1–10.
- Coordinates are written as column letter + row number, e.g., B2.
- You MUST return exactly ONE coordinate.
- Do NOT repeat a coordinate that is already a HIT or a MISS.
- Do NOT choose coordinates belonging to already SUNK ships.
- Output MUST be only the coordinate, no words, no explanation.

CURRENT KNOWLEDGE:
Hits so far: H = %HITS%
Misses so far: M = %MISSES%
Sunk ships: S = %SUNKSHIPS%

STRATEGY REQUIREMENTS:
- If there are adjacent hits forming a line, continue targeting along that line.
- If there is a single isolated hit, check the 4 orthogonal neighbors (up, down, left, right) that are still valid.
- If no hits exist, choose a reasonable search coordinate (e.g., checkerboard pattern).
- You must avoid any coordinate already in H, M, or S.

RETURN FORMAT:
Column letter (A-J) + row number (1-10), e.g., B2.
Output MUST be exactly one coordinate, nothing else.
""";

        String finalPrompt = prompt
                .replace("%HITS%", hits)
                .replace("%MISSES%", misses)
                .replace("%SUNKSHIPS%", sunk);
        System.out.println(finalPrompt);

        // sends prompt to model and get model answer
        String nextMove = llama.ask(finalPrompt);

        return nextMove;

    }
}
