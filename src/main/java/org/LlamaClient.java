package org;
import java.net.http.*;
import java.net.URI;

/**
 * Sends prompts to the Groq APi.
 */

public class LlamaClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;

    public LlamaClient(String apiKey) {
        this.apiKey = apiKey;
    }

     // sends prompt to Llama model and returns model reply
    public String ask(String prompt) {
        // characters (eg quotes, backslashes, and newlines) that would break json formatting
        String safePrompt = prompt
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");

        // json sent to Groq API (using llama-3.1-8b-instant model)
        String body = """
        {
          "model": "llama-3.1-8b-instant",
          "messages": [
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(safePrompt);

        // build HTTP request to get model response
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        try {
            // send request and get model response
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            // extracts "content" field from returned json
            return extractContent(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // extracts content message returned by model
    private String extractContent(String json) {
        // eg {...,"content":"Hello world",...}
        // start finds the start of the content value (index of H in the given example)
        int start = json.indexOf("\"content\":\"") + 11;
        // finds where next " appears
        int end = json.indexOf("\"", start);
        // catches edge cases
        if (start < 11 || end < start) return "";
        // returns substring (Hello world in the given example)
        return json.substring(start, end);
    }
}
