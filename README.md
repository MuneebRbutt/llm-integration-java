# 🤖 LLM Integration Console App (Java)

This is a **Java console-based application** that allows users to interact with different **Large Language Models (LLMs)** such as **Gemini** and **OpenAI**, in a clean, extendable architecture. It supports username tracking, question limits, and dynamic model selection.

---

## 🚀 Features

- ✅ Choose between **Gemini** and **OpenAI** for chatting
- 👤 Track **user question count** (limited to 5 questions)
- 🔒 **Filter out gibberish or invalid questions**
- 🔁 Option to return to main menu or exit anytime
- 🧩 **Easily add new LLMs** in the future with minimal changes
- 🔐 Uses `.env` file for API key security (via `dotenv-java`)
- ☁️ Built for **modularity** and clean interface separation


## 🛠️ Project Structure

LLM-Integration/
│
├── src/main/java/org/example/
│ ├── Main.java # Console interface
│ ├── LLMService.java # Interface for any LLM
│ ├── LLMClient.java # Handles which LLM service to use
│ ├── GeminiService.java # Gemini API integration
│ ├── OpenAiService.java # OpenAI API integration
│
├── .env # Stores API keys securely
├── .gitignore
└── README.md


---

## ⚙️ Setup Instructions


1. Clone this Repository

```bash
git clone https://github.com/your-username/llm-integration.git
cd llm-integration

2. Install Dependencies
Make sure you have:
Java 17+
IntelliJ IDEA or any Java IDE
Maven

3. Add this to your pom.xml:
<dependencies>
    <!-- GSON -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>

    <!-- dotenv-java -->
    <dependency>
        <groupId>io.github.cdimascio</groupId>
        <artifactId>dotenv-java</artifactId>
        <version>3.0.0</version>
    </dependency>
</dependencies>

4. Add API Keys
Create a .env file in the root:
GEMINI_API_KEY=your_gemini_key_here
OPENAI_API_KEY=your_openai_key_here


Running the App
# Compile and run
mvn compile
mvn exec:java -Dexec.mainClass="org.example.Main"

💡 How to Add a New LLM (e.g., Claude, Mistral, etc.)
1. To add a new chatbot model:

Create a new class that implements LLMService
public class ClaudeService implements LLMService {
    @Override
    public String getChatCompletion(String prompt) {
        // Your Claude API integration logic here
        return "Claude’s reply";
    }
}

2.Update Main.java:
Add another option in the chooseLLM() method:

System.out.println("3. Claude");  
// Add in chooseLLM() method
return switch (choice) {
    case "1" -> new GeminiService();
    case "2" -> new OpenAiService();
    case "3" -> new ClaudeService();  // Add this line
    default -> {
        System.out.println("Invalid choice.");
        yield null;
    }
};
✅ Done! No need to change LLMClient or any other class.


# 📷 Sample Console Output

Press 1 to interact with the LLM
Press 2 to exit the program
> 1
Enter your username: muneeb
Welcome, muneeb! You can ask up to 5 questions.

Choose an LLM:
1. Gemini
2. OpenAI
> 1

Ask your question:
> What is the capital of France?
💬 LLM Response: The capital of France is Paris.

📄 License
This project is open-source and free to use. No license restrictions. Any updations would be appreciated!








