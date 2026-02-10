
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class BookLibrary {
    static ArrayList<Book> books = new ArrayList<>();
    static HashMap<UUID, Book> bookMap = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("************************");
            System.out.println("Welcome to Book Library");
            System.out.println("************************");
            System.out.println("1. Enter new book (title/author): ");
            System.out.println("2. View a list of all books: ");
            System.out.println("3. Find a book by title or author: ");
            System.out.println("4. Mark a book as read/unread: ");
            System.out.println("5. Remove a book from the library: ");
            System.out.println("6. Quit the application: ");
            System.out.println("************************");
            System.out.print("Please enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> newBook();
                    case 2 -> allList();
                    case 3 -> findBook();
                    case 4 -> markAsRead();
                    case 5 -> removeBook();
                    case 6 -> isRunning = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number (1-6).");
                scanner.nextLine();
            }
        }
    }

    static void newBook() {
        System.out.println("************************");
        System.out.print("Please enter the title: ");
        String inputTitle = scanner.nextLine();

        System.out.println("************************");
        System.out.print("Please enter the author: ");
        String inputAuthor = scanner.nextLine();

        Book myBook = new Book(inputTitle, inputAuthor);
        books.add(myBook);
        bookMap.put(myBook.getId(), myBook);
        System.out.println("************************");
        System.out.println("Book added! ID: " + myBook.getId());
        System.out.println("************************");
    }

    static void allList() {
        if (books.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }
        for (Book b : books) {
            String status = b.isRead() ? "[Read]" : "[Unread]";
            System.out.println(status + " \"" + b.getTitle() + "\" (" + b.getAuthor() + ") ID: " + b.getId());
        }
    }

    static void findBook() {
        System.out.print("Search by: 1. UUID | 2. Title | 3. Author: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Book foundBook = null;

        if (choice == 1) {
            System.out.print("Please enter the book ID: ");
            try {
                UUID id = UUID.fromString(scanner.nextLine());
                foundBook = bookMap.get(id);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid UUID format.");
                return;
            }
        } else if (choice == 2) {
            System.out.print("Please enter the Title: ");
            String searchTitle = scanner.nextLine();
            for (Book b : books) {
                if (b.getTitle().equalsIgnoreCase(searchTitle)) {
                    foundBook = b;
                    break;
                }
            }
        } else if (choice == 3) {
            System.out.print("Please enter the Author: ");
            String searchAuthor = scanner.nextLine();
            for (Book b : books) {
                if (b.getAuthor().equalsIgnoreCase(searchAuthor)) {
                    foundBook = b;
                    break;
                }
            }
        } else {
            System.out.println("Invalid search choice.");
            return;
        }

        if (foundBook != null) {
            System.out.println("Found: " + foundBook.getTitle() + " by " + foundBook.getAuthor());
        } else {
            System.out.println("Book not found.");
        }
    }

    static void markAsRead() {
        System.out.print("Please enter the book ID: ");
        String inputId = scanner.nextLine();

        try {
            UUID id = UUID.fromString(inputId);
            Book bookToUpdate = bookMap.get(id);

            if (bookToUpdate != null) {
                if (bookToUpdate.isRead()) {
                    System.out.println("Book is already marked as read. Mark as unread? (yes/no): ");
                    if (scanner.nextLine().equalsIgnoreCase("yes")) {
                        bookToUpdate.setRead(false);
                        System.out.println("Status changed to [Unread].");
                    }
                } else {
                    bookToUpdate.setRead(true);
                    System.out.println("Book \"" + bookToUpdate.getTitle() + "\" has been marked as [Read].");
                }
            } else {
                System.out.println("Book with this ID not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid input. Please enter a valid book ID.");
        }
    }

    static void removeBook() {
        if (books.isEmpty()) {
            System.out.println("Library is already empty.");
            return;
        }
        System.out.print("Remove book by: 1. UUID | 2. Clear library: ");
        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input. Returning to main menu.");
            scanner.nextLine();
            return;
        }

        if (choice == 1) {
            System.out.print("Please enter the book ID which you would like to remove: ");
            String inputId = scanner.nextLine();
            try {
                UUID id = UUID.fromString(inputId);
                Book bookToRemove = bookMap.get(id);

                if (bookToRemove != null) {
                    bookMap.remove(id);
                    books.remove(bookToRemove);
                    System.out.println("Book removed successfully.");
                } else {
                    System.out.println("Book with this ID not found.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid input. Please enter a valid book ID.");
            }
        } else if (choice == 2) {
            books.clear();
            bookMap.clear();
            System.out.println("Books have been cleared.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
