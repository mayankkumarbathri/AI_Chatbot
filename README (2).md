# CodeAlpha Java Programming Internship — Tasks 1–4

Four self-contained console programs, one per task. Each is a single `.java`
file with no external dependencies beyond the standard JDK.

## Compiling & running

Each task is one file, so compile and run it directly:

```bash
cd Task1_StudentGradeTracker
javac StudentGradeTracker.java
java StudentGradeTracker
```

Do the same inside each of the other three folders, using that folder's
class name (`StockTradingPlatform`, `AIChatbot`, `HotelReservationSystem`).
Requires a JDK (17+ recommended; written and checked against Java 21 syntax).

## What each program does

- **Task 1 — Student Grade Tracker**: add students, record grades, and view
  per-student and class-wide reports (average, highest, lowest).
- **Task 2 — Stock Trading Platform**: simulated market with 5 stocks, buy/sell,
  a portfolio with cash + holdings, transaction history, a day-advance that
  randomly moves prices, and save/load of the portfolio to `portfolio.txt`.
- **Task 3 — AI Chatbot**: rule-based FAQ bot with basic NLP preprocessing
  (lowercasing, punctuation stripping, stop-word removal) and keyword-match
  scoring. It can be taught new answers at runtime, which are saved to
  `chatbot_knowledge.txt` so it "learns" between runs.
- **Task 4 — Hotel Reservation System**: rooms in three categories
  (Standard/Deluxe/Suite), search availability, book/cancel reservations,
  a simulated payment step, and save/load of reservations to `reservations.txt`.

## Submitting per the CodeAlpha instructions

1. For each task you submit, create a GitHub repo named
   `CodeAlpha_<ProjectName>` (e.g. `CodeAlpha_StudentGradeTracker`) and push
   that task's source code.
2. Record a short video walkthrough and post it on LinkedIn, tagging
   @CodeAlpha, with the GitHub link.
3. Submit through the Submission Form shared in your WhatsApp group.
4. You need a minimum of 2–3 completed tasks for the certificate — all four
   are provided here so you can choose which to submit.

Note: this was written and hand-checked for correct Java syntax, but not
compiled in this environment (no JDK compiler available here) — run the
`javac` step above before you rely on it, and let me know if anything needs
a fix.
