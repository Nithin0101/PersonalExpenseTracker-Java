import java.io.*;
import java.util.*;

class Expense {
    String category;
    double amount;
    String date;

    Expense(String category, double amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        return date + " | " + category + " | " + amount;
    }
}

public class PersonalExpenseTracker {
    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        loadExpenses();  // Load existing expenses from file

        while (true) {
            System.out.println("\n===== Personal Expense Tracker =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Calculate Total Expenses");
            System.out.println("4. Find Highest Spending Category");
            System.out.println("5. Save & Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addExpense(); break;
                case 2: viewExpenses(); break;
                case 3: calculateTotal(); break;
                case 4: highestSpendingCategory(); break;
                case 5: saveExpenses(); System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void addExpense() {
        System.out.print("Enter Category (Food/Travel/Shopping/etc): ");
        String category = sc.nextLine();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        expenses.add(new Expense(category, amount, date));
        System.out.println("Expense Added!");
    }

    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense e : expenses) {
                System.out.println(e);
            }
        }
    }

    static void calculateTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }
        System.out.println("Total Expenses: " + total);
    }

    static void highestSpendingCategory() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        HashMap<String, Double> map = new HashMap<>();
        for (Expense e : expenses) {
            map.put(e.category, map.getOrDefault(e.category, 0.0) + e.amount);
        }

        String maxCategory = "";
        double maxAmount = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue() > maxAmount) {
                maxCategory = entry.getKey();
                maxAmount = entry.getValue();
            }
        }

        System.out.println("Highest Spending Category: " + maxCategory + " (₹" + maxAmount + ")");
    }

    static void saveExpenses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                bw.write(e.date + "," + e.category + "," + e.amount);
                bw.newLine();
            }
            System.out.println("Expenses saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    static void loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.add(new Expense(parts[1], Double.parseDouble(parts[2]), parts[0]));
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}
