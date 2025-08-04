package org.example;
public class LLMFactory {
    public static LLMService getService(String llmName){
        return switch(llmName.toLowerCase()){
            case "gemini" -> new GeminiService();
            case "openai" -> new OpenAIService();
            default -> throw new IllegalArgumentException("❌ Unsupported LLM: " + llmName);
        };
    }
}
