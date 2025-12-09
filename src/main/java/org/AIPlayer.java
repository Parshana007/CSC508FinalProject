//package csc508;
package org;
// public class AIPlayer extends Player {

public class AIPlayer {
    public static void main(String[] args) {
        // creates new LlamaClient using api key from config
        LlamaClient llama = new LlamaClient(Config.get("GROQ_API_KEY"));

//        String boardState = """
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//? ? ? ? ? ? ? ? ? ?
//""";
        // prompt sent to model
        String prompt = """
You are an AI playing Battleship Game on a 10x10 grid.
The enemy grid looks like ..., X means this and that ...
With 1-10 on the x axis and A-J on the y axis.
Return ONLY one coordinate in the format (x-axisy-axis).
Do NOT write code or explanations.
It HAS to be A-J and 01-10 inclusive.
Example: A10
""";

        // sends prompt to model and get model answer
        String nextMove = llama.ask(prompt);

        System.out.println("AI chooses: " + nextMove);

    }
}
