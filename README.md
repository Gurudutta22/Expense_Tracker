# Spendwise — Personal Expense Tracker

A complete, runnable full-stack **expense tracker**. Add expenses by category, set monthly budgets,
and see a live dashboard: total spent this month, spending by category with budget progress, your top
category, and recent transactions. Built with **Java 17 + Spring Boot + Spring Data JPA**, an
embedded **H2** database (nothing to install), and a plain HTML/CSS/JavaScript UI served by the app.

---

## What you need: Java (JDK) 17 or newer
Check with `java -version`. If it's missing or older, install the free **Eclipse Temurin JDK 21**
from https://adoptium.net. You do **NOT** need MySQL, Node.js, or even Maven (a wrapper is included).
The first run needs internet once (to download libraries); after that it runs offline.

## How to run — pick ONE

**Option A — IntelliJ IDEA (easiest):** install IntelliJ IDEA Community (free), do **File → Open**
and select the unzipped `expense-tracker` folder, wait for it to import (first time ~1–2 min), open
`src/main/java/com/expensetracker/ExpenseTrackerApplication.java`, and click the green **▶ Run**.
Then open **http://localhost:8080**.

**Option B — Terminal (uses the included wrapper):** open a terminal in the `expense-tracker` folder:
```
./mvnw spring-boot:run        (macOS / Linux)
mvnw.cmd spring-boot:run      (Windows)
```
Then open **http://localhost:8080**. (macOS/Linux: if it says "permission denied", run `chmod +x mvnw` once.)

**Option C — if you already have Maven:** `mvn spring-boot:run`.

---

## How to use it
The app starts pre-loaded with sample expenses and budgets for the current month.
1. **Add an expense:** enter an amount, pick a category, add a description and date, click **Add
   expense**. The dashboard and transaction list update instantly.
2. **Watch the dashboard:** "Spent this month," "Budget left," your "Top category," and a
   **spending-by-category** breakdown with progress bars. A category turns red and shows **OVER** when
   you exceed its budget.
3. **Set a budget:** choose a category and a monthly limit, click **Save budget**.
4. **Delete a transaction:** click the **×** next to it.

Browse the live database any time at **http://localhost:8080/h2-console**
(JDBC URL: `jdbc:h2:mem:expensetracker`, user `sa`, no password).

## Try this to test it
Add an expense of **₹5000** in **SHOPPING** for today. Watch "Spent this month" jump, SHOPPING climb
in the breakdown and likely turn red with an **OVER** badge (its budget is ₹4000), and a new row
appear at the top of Recent transactions. Then set the SHOPPING budget to ₹10000 and watch it go back
under budget.

---

## Project structure
```
expense-tracker/
├── mvnw, mvnw.cmd, .mvn/        Maven wrapper (run without installing Maven)
├── pom.xml
├── src/main/java/com/expensetracker/
│   ├── ExpenseTrackerApplication.java   app entry point (scheduling + async enabled)
│   ├── model/      Expense, Budget, Category               (JPA entities + enum)
│   ├── dto/        ExpenseRequest, BudgetRequest, CategoryTotal, CategorySummary, DashboardSummary
│   ├── repository/ ExpenseRepository (with GROUP BY query), BudgetRepository
│   ├── service/    ExpenseService, BudgetService, DashboardService, AuditService (@Async), InsightsService (@Scheduled)
│   ├── config/     DataInitializer (seeds sample data)
│   └── web/        ExpenseController (REST endpoints)
└── src/main/resources/
    ├── application.properties      H2 by default; MySQL config included (commented)
    └── static/index.html           the dashboard UI (HTML + CSS + JS)
```

## REST API
| Method | Endpoint | Purpose |
|--------|----------|---------|
| GET    | `/api/categories`     | list expense categories |
| GET    | `/api/expenses`       | recent expenses |
| POST   | `/api/expenses`       | add an expense `{description, amount, category, spentOn}` |
| DELETE | `/api/expenses/{id}`  | delete an expense |
| GET    | `/api/dashboard`      | summary: totals, per-category breakdown, budgets |
| GET    | `/api/budgets`        | list budgets |
| POST   | `/api/budgets`        | set a budget `{category, monthlyLimit}` |

## Switching to MySQL (optional)
Open `src/main/resources/application.properties`, comment the H2 block, and uncomment the MySQL block
(set your password). The MySQL driver is already in `pom.xml`.

---
*Note:* with the default in-memory H2 database, data resets when you stop the app and the sample data
is seeded again next start. Switch to MySQL to keep data permanently.
