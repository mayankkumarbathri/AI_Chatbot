# AI Chatbot

**CodeAlpha Java Programming Internship — Task 3**

A rule-based FAQ chatbot with simple NLP preprocessing and keyword-match
scoring, able to learn new answers interactively.

## Features
- Preprocessing: lowercasing, punctuation stripping, stop-word removal,
  tokenization.
- Keyword-scoring match against a knowledge base of FAQ rules (topics:
  greetings, hours, location, contact, pricing, services, and more).
- Falls back to "I'm not sure I understand" when nothing scores, and offers
  to be taught a response right there in the conversation.
- Anything you teach it is saved to `chatbot_knowledge.txt` and reloaded on
  the next run, so the bot's knowledge grows over time.
- Type `help` for a list of topics, `bye`/`exit`/`quit` to end.

## Files
- `AIChatbot.java` — single-file program, no external dependencies.

## Compile & run
```bash
javac AIChatbot.java
java AIChatbot
```
Requires a JDK (17+ recommended).

## Suggested GitHub repo name
`CodeAlpha_AIChatbot`

## Note
Written and hand-checked for correct Java syntax; not compiled in the
environment that produced it (no JDK compiler available there). Run the
`javac` step above before relying on it.
