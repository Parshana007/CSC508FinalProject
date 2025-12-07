package org;
// public class AIPlayer extends Player {

public class TestAI {
    public static void main(String[] args) {
        LlamaClient llama = new LlamaClient(Config.get("GROQ_API_KEY"));

        String reply = llama.ask("Say hello!");
        System.out.println("AI says: " + reply);
    }
}
