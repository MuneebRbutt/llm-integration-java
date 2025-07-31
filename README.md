# **GeminiClient – LLM API Integration in Java**

This Java project demonstrates how to integrate and interact with the **Gemini LLM** (Large Language Model) API from [OpenRouter.ai](https://openrouter.ai) to get AI chat completions. It offers a simple command-line interface (CLI) where users can chat with the LLM, track their question history, and are limited to a specific number of questions.

---

## **✨ Features**

- Connects to Gemini API (`google/gemini-flash-1.5` model) using RESTful HTTP POST.
- API key-based authentication using environment variable `GEMINI_API_KEY`.
- Multi-user support based on usernames.
- Tracks user questions with a per-user limit of **5**.
- Stores and displays **chat history** for each user.
- Validates user inputs:
  - Minimum length requirement
  - Must contain at least one letter
  - Must end with a question mark `?`
- Simple and interactive CLI menu.
- Clean architecture using `LLMService` interface for LLM abstraction.

---

## **🛠 Prerequisites**

- **Java 8** or above
- Internet connection
- API key from [OpenRouter.ai](https://openrouter.ai) or any Gemini-compatible provider

### **Set Environment Variable**

**Linux/macOS:**
```bash
export GEMINI_API_KEY="your_api_key_here"

## **For windows:**
set GEMINI_API_KEY=your_api_key_here

📁 Project Structure
LLMService.java
Defines an interface for large language model services:

## String getChatResponse(String userMessage) throws Exception;
GeminiClient.java
Implements LLMService. Handles:

HTTP requests

Authentication

JSON parsing

Returns chat response from Gemini API

LLMChatApp.java
Provides the CLI. Users can:

Enter a username and start chatting
View previous chat history
Exit the program

▶️ How to Run:
Clone or download this repository.
Ensure GEMINI_API_KEY is set as described above.
Compile Java files (make sure gson.jar is included in your classpath):
 javac -cp gson-<version>.jar org/example/*.java

Run the main class:
 java -cp .;gson-<version>.jar org.example.LLMChatApp

💡 Usage
Press 1 – Enter username and start chatting (limit: 5 questions per user)
Press 2 – View chat history for a user
Press 3 – Exit the application

📏 Chatting Guidelines
Questions must:
Be at least 5 characters long
Contain at least one letter
End with a ?
Type exit at any time to end the chat session.

💬 Example Interaction
📢 Press 1 to start interacting with the LLM
📜 Press 2 to view previous chat history
🚪 Press 3 to exit

Enter your choice: 1
👤 Enter your username: alice
👋 Hello, alice! You’ve already asked 0 question(s).
💬 Type 'exit' to end the chat.

📝 You: What is the capital of France?
🤖 LLM: The capital of France is Paris.

📝 You: exit
👋 Chat ended.

❗ Error Handling
❌ API key is missing. Please set the GEMINI_API_KEY environment variable.
Then:
    Invalid API responses are shown in the console.
    Input validation messages guide users to ask valid questions.

📦 Dependencies
Gson – Used for JSON parsing.
Add the gson-<version>.jar to your classpath when compiling and running.

📎 Notes
Uses synchronous HttpURLConnection.
The model and endpoint are hardcoded but can be modularized.
In-memory storage for chat history (reset on every run).
The LLMService interface allows easy extension to other LLM providers.

📄 License
This project is open-source and free to use.

Happy chatting with Gemini! 🚀
