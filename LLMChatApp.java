package org.example;

import java.util.*;
import java.util.regex.Pattern;

public class LLMChatApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Integer> userQuestions = new HashMap<>();
    private static final Map<String, List<String>> userHistory = new HashMap<>();
    private static final int MAX_QUESTIONS = 5;

    private static final LLMService llmService = new GeminiClient();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n📢 Press 1 to start interacting with the LLM");
            System.out.println("📜 Press 2 to view previous chat history");
            System.out.println("🚪 Press 3 to exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleUserChat();
                    break;
                case "2":
                    showHistory();
                    break;
                case "3":
                    System.out.println("👋 Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private static void handleUserChat() {
        System.out.print("👤 Enter your username: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("⚠️ Username cannot be empty!");
            return;
        }

        userQuestions.putIfAbsent(username, 0);
        userHistory.putIfAbsent(username, new ArrayList<>());

        int count = userQuestions.get(username);
        System.out.println("👋 Hello, " + username + "! You’ve already asked " + count + " question(s).");

        startChat(username);
    }

    private static void startChat(String username) {
        System.out.println("💬 Type 'exit' to end the chat.");

        while (true) {
            int asked = userQuestions.get(username);

            if (asked >= MAX_QUESTIONS) {
                System.out.println("🚫 You have reached the maximum limit of " + MAX_QUESTIONS + " questions.");
                return;
            }

            System.out.print("📝 You: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("👋 Chat ended.");
                return;
            }

            if (!isValidQuestion(input)) {
                System.out.println("⚠️ Invalid question. Must be 5+ chars, contain letters, and end with '?'");
                continue;
            }

            try {
                String response = llmService.getChatResponse(input);
                System.out.println("🤖 LLM: " + response);

                userHistory.get(username).add("You: " + input);
                userHistory.get(username).add("LLM: " + response);
                userQuestions.put(username, asked + 1);

            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    private static void showHistory() {
        System.out.print("🔍 Enter your name to view history: ");
        String username = scanner.nextLine().trim();

        List<String> history = userHistory.get(username);
        if (history == null || history.isEmpty()) {
            System.out.println("📭 No chat history found for user: " + username);
            return;
        }

        System.out.println("🗂️ Chat History for " + username + ":");
        history.forEach(System.out::println);
    }

    private static boolean isValidQuestion(String input) {
        return input.length() >= 5 &&
                Pattern.compile("[a-zA-Z]").matcher(input).find() &&
                input.endsWith("?");
    }
}
