import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * CodeAlpha Java Programming Internship - Task 3
 * Artificial Intelligence Chatbot (console)
 *
 * A rule-based chatbot that uses simple NLP preprocessing (lowercasing,
 * punctuation stripping, stop-word removal) and keyword scoring to match
 * user input against a knowledge base of frequently asked questions.
 * Unknown questions can be "taught" to the bot interactively, which
 * persists new rules to a file so the bot keeps learning between runs.
 */
public class AIChatbot {

    // ---------- A single FAQ rule: keywords -> response ----------
    static class Rule {
        final List<String> keywords;
        final String response;

        Rule(List<String> keywords, String response) {
            this.keywords = keywords;
            this.response = response;
        }
    }

    private static final String KNOWLEDGE_FILE = "chatbot_knowledge.txt";
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "a", "an", "the", "is", "are", "am", "was", "were", "do", "does", "did",
            "i", "you", "your", "my", "me", "to", "of", "in", "on", "for", "with",
            "what", "how", "can", "could", "would", "will", "please", "tell", "about",
            "it", "this", "that", "there"
    ));

    private static final List<Rule> knowledgeBase = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadDefaultKnowledge();
        loadKnowledgeFromFile();

        System.out.println("=========================================");
        System.out.println("   CodeAlpha - AI FAQ Chatbot");
        System.out.println("=========================================");
        System.out.println("Ask me a question, or type 'help' for topics, 'bye' to exit.\n");

        while (true) {
            System.out.print("You: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String lower = input.toLowerCase();
            if (lower.equals("bye") || lower.equals("exit") || lower.equals("quit")) {
                System.out.println("Bot: Goodbye! Have a great day.");
                break;
            }
            if (lower.equals("help") || lower.equals("topics")) {
                printTopics();
                continue;
            }

            respond(input);
        }
        scanner.close();
    }

    // ---------- Preprocessing ----------
    private static List<String> tokenize(String text) {
        String cleaned = text.toLowerCase().replaceAll("[^a-z0-9\\s]", " ");
        List<String> tokens = new ArrayList<>();
        for (String word : cleaned.split("\\s+")) {
            if (!word.isBlank() && !STOP_WORDS.contains(word)) {
                tokens.add(word);
            }
        }
        return tokens;
    }

    // ---------- Core matching logic ----------
    private static void respond(String input) {
        List<String> tokens = tokenize(input);

        Rule bestRule = null;
        int bestScore = 0;

        for (Rule rule : knowledgeBase) {
            int score = 0;
            for (String keyword : rule.keywords) {
                if (tokens.contains(keyword)) score++;
            }
            if (score > bestScore) {
                bestScore = score;
                bestRule = rule;
            }
        }

        if (bestRule != null && bestScore > 0) {
            System.out.println("Bot: " + bestRule.response);
        } else {
            System.out.println("Bot: I'm not sure I understand that yet.");
            offerToLearn(input, tokens);
        }
    }

    private static void offerToLearn(String originalInput, List<String> tokens) {
        if (tokens.isEmpty()) return;
        System.out.print("Bot: Would you like to teach me how to answer that? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (!answer.equals("y") && !answer.equals("yes")) return;

        System.out.print("Bot: What should I say in response to that? ");
        String response = scanner.nextLine().trim();
        if (response.isEmpty()) {
            System.out.println("Bot: No response given, nothing learned.");
            return;
        }

        Rule newRule = new Rule(new ArrayList<>(tokens), response);
        knowledgeBase.add(newRule);
        saveRuleToFile(newRule);
        System.out.println("Bot: Got it! I'll remember that for next time.");
    }

    private static void printTopics() {
        System.out.println("Bot: Here are some things you can ask me about:");
        System.out.println("  - hours (opening hours)");
        System.out.println("  - location (where you are based)");
        System.out.println("  - contact (how to reach support)");
        System.out.println("  - pricing / cost (about pricing)");
        System.out.println("  - services (what you offer)");
        System.out.println("  - name (who I am)");
        System.out.println("  - thanks / hello (small talk)");
        System.out.println("Type 'bye' to exit.\n");
    }

    // ---------- Default FAQ knowledge base ----------
    private static void loadDefaultKnowledge() {
        addRule("Hello! How can I help you today?", "hello", "hi", "hey", "greetings");
        addRule("You're welcome! Happy to help.", "thanks", "thank", "thankyou");
        addRule("My name is CodeAlpha Bot, your FAQ assistant.", "name", "who", "yourself");
        addRule("We're open Monday to Saturday, 9 AM to 6 PM.", "hours", "open", "opening", "time", "timing");
        addRule("We're based online, so you can reach us from anywhere.", "location", "where", "address", "based");
        addRule("You can reach support at services@codealpha.tech.", "contact", "support", "email", "reach", "help");
        addRule("Pricing depends on the plan; contact support for a detailed quote.", "price", "pricing", "cost", "fee", "charge");
        addRule("We offer internships, mentorship, and project-based learning in software development.",
                "service", "services", "offer", "provide", "internship", "internships");
        addRule("I'm a simple rule-based chatbot that matches keywords in your question to a response.",
                "chatbot", "bot", "work", "nlp", "ai");
    }

    private static void addRule(String response, String... keywords) {
        knowledgeBase.add(new Rule(new ArrayList<>(Arrays.asList(keywords)), response));
    }

    // ---------- Persistence: learned rules survive between runs ----------
    private static void loadKnowledgeFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(KNOWLEDGE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\|", 2);
                if (parts.length != 2) continue;
                List<String> keywords = new ArrayList<>(Arrays.asList(parts[0].split(",")));
                knowledgeBase.add(new Rule(keywords, parts[1]));
            }
        } catch (IOException e) {
            // No learned-knowledge file yet; that's fine on first run.
        }
    }

    private static void saveRuleToFile(Rule rule) {
        try (FileWriter writer = new FileWriter(KNOWLEDGE_FILE, true)) {
            writer.write(String.join(",", rule.keywords) + "|" + rule.response + "\n");
        } catch (IOException e) {
            System.out.println("Bot: (Couldn't save that for next time: " + e.getMessage() + ")");
        }
    }
}
