package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GeminiService implements LLMService {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String apiKey = System.getenv("GEMINI_API_KEY");

    @Override
    public String getChatResponse(String userPrompt) throws Exception {
        if (apiKey == null || apiKey.isEmpty()) {
            return "❌ Gemini API key missing. Please set GEMINI_API_KEY.";
        }

        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setRequestProperty("HTTP-Referer", "http://localhost");
        conn.setDoOutput(true);

        String escapedPrompt = userPrompt.replace("\"", "\\\"");

        String inputJson = "{\n" +
                "  \"model\": \"google/gemini-flash-1.5\",\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"role\": \"user\",\n" +
                "      \"content\": \"" + escapedPrompt + "\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(inputJson.getBytes());
            os.flush();
        }

        int statusCode = conn.getResponseCode();
        InputStream stream = (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        conn.disconnect();

        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonArray choices = jsonResponse.getAsJsonArray("choices");

        if (choices != null && choices.size() > 0) {
            JsonObject firstChoice = choices.get(0).getAsJsonObject();
            JsonObject message = firstChoice.getAsJsonObject("message");
            return message.get("content").getAsString();
        }

        return "⚠️ No valid response from Gemini.";
    }
}
