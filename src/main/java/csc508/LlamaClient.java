package org;
import java.net.http.*;
import java.net.URI;

public class LlamaClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String apiKey;

    public LlamaClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String ask(String prompt) {

        String body = """
        {
          "model": "llama-3.1-8b-instant",
          "messages": [
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(prompt.replace("\"", "'")); // prevent JSON break

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return extractContent(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private String extractContent(String json) {
        int start = json.indexOf("\"content\":\"") + 11;
        int end = json.indexOf("\"", start);
        if (start < 11 || end < start) return "";
        return json.substring(start, end);
    }
}
