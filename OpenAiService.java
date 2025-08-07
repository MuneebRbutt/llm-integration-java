package org.example;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv; // Adding to fetch environment variables

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenAiService implements LLMService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private static final String apiKey = dotenv.get("OPENAI_API_KEY");

    @Override
    public String getChatResponse(String prompt) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", prompt);

            JsonArray messages = new JsonArray();
            messages.add(message);

            JsonObject payload = new JsonObject();
            payload.add("messages", messages);
            string modelName = "gpt-3.5-turbo";
            payload.addProperty("model", modelName); // or gpt-4 if you have access

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HTTP error code: " + responseCode);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices.size() > 0) {
                    JsonObject firstChoice = choices.get(0).getAsJsonObject();
                    JsonObject messageObject = firstChoice.getAsJsonObject("message");
                    return messageObject.get("content").getAsString();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error getting response from OpenAI.";
    }
}

