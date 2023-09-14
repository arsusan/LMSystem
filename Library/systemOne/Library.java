package systemOne;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private final List<String> storedLCIDs; // List to store LCIDs

    // Constructor to initialize the Library object
    public Library(String lcidsFile) {
        storedLCIDs = loadLCIDsFromFile(lcidsFile); // Load LCIDs from a file
    }

    // Method to load LCIDs from a file
    private List<String> loadLCIDsFromFile(String fileName) {
        List<String> lcidList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lcidList.add(line.trim()); // Trim any leading/trailing whitespace and add to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading LCID file: " + e.getMessage());
        }
        return lcidList;
    }

    // Method to list available books
    private void listAvailableBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            System.out.println("==============================");
            System.out.println("Available Books:");
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line); // Print each book title
            }
            System.out.println("==============================");
        } catch (IOException e) {
            System.err.println("Error reading book file: " + e.getMessage());
        }
    }

    // Method to purchase books
    private void purchaseBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the book: ");
        String bookName = scanner.nextLine().trim();

        try (FileWriter fw = new FileWriter("purchased_books.txt", true)) {
            fw.write(bookName + "\n");
            fw.flush();
            System.out.println("Book purchased successfully!");
        } catch (IOException e) {
            System.err.println("Error purchasing book: " + e.getMessage());
        }
    }

    // Method to add books
    private void addBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the book: ");
        String bookName = scanner.nextLine().trim();

        try (FileWriter fw = new FileWriter("books.txt", true)) {
            fw.write(bookName + "\n");
            fw.flush();
            System.out.println("Book added successfully!");
        } catch (IOException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    // Method to delete books
    private void deleteBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the book to delete: ");
        String bookName = scanner.nextLine().trim();

        try {
            List<String> books = new ArrayList<>();

            // Read all books from file
            try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    books.add(line);
                }
            }

            // Remove the specified book
            books.remove(bookName);

            // Write updated book list back to file
            try (FileWriter fw = new FileWriter("books.txt")) {
                for (String book : books) {
                    fw.write(book + "\n");
                }
                fw.flush();
                System.out.println("Book deleted successfully!");
            }
        } catch (IOException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }


    // Method to show transaction history
    private void showTransactionHistory() {
        try (BufferedReader br = new BufferedReader(new FileReader("purchased_books.txt"))) {
            System.out.println("==============================");
            System.out.println("Transaction History:");
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(count + ". " + line); // Print each purchased book
                count++;
            }
            System.out.println("==============================");
        } catch (IOException e) {
            System.err.println("Error reading transaction history: " + e.getMessage());
        }
    }

    // Method to start the library management system
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================");
        System.out.println("Welcome to Management System");
        System.out.println("==============================");

        // Prompt for LCID login
        System.out.print("Please enter your LCID: ");
        String enteredLCID = scanner.nextLine().trim();

        if (storedLCIDs.contains(enteredLCID)) {
            // LCID matches, show menu
            showMenu(scanner);
        } else {
            System.out.println("LCID does not match. Access denied.");
        }

        scanner.close(); // Close the scanner
    }

    // Method to display the menu and handle user input
    private void showMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("==============================");
            System.out.println("Menu:");
            System.out.println("1. Purchase Books");
            System.out.println("2. Add Books");
            System.out.println("3. ViewAvailable Books");
            System.out.println("4. Delete Book");
            System.out.println("5. Perform Transaction");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    purchaseBooks();
                    break;
                case 2:
                    addBooks();
                    break;
                case 3:
                    System.out.println("\nHere are the available Books:\n");
                    listAvailableBooks(); // Display available books
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    showTransactionHistory();
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (choice != 6);
    }

    public static void main(String[] args) {
        Library library = new Library("lcids.txt"); // Create a Library object with LCIDs file
        library.start(); // Start the library management system
    }
}