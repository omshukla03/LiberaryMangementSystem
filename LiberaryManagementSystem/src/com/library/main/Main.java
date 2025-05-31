package com.library.main;

import com.library.dao.BookDAO;
import com.library.dao.UserDAO;
import com.library.model.Book;
import com.library.model.User;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryService libraryService = new LibraryService();
        boolean running = true;

        while (running) {
            System.out.println("\n===== Library Management Menu =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Delete Book");
            System.out.println("4. Add User");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Issued Books");
            System.out.println("8. Search Books");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();

                    Book book = new Book();
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setCategory(category);
                    book.setIssued(false);

                    if (libraryService.addBook(book)) {
                        System.out.println("✅ Book added successfully!");
                    } else {
                        System.out.println("❌ Failed to add book.");
                    }
                    break;

                case 2:
                    List<Book> books = libraryService.getAllBooks();
                    System.out.println("📚 All Books:");
                    for (Book b : books) {
                        System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor() +
                                " | Category: " + b.getCategory() +
                                " | Issued: " + b.isIssued());
                    }
                    break;

                case 3:
                    System.out.print("Enter book ID to delete: ");
                    int deleteId = sc.nextInt();
                    if (libraryService.deleteBook(deleteId)) {
                        System.out.println("✅ Book deleted.");
                    } else {
                        System.out.println("❌ Book not found or failed to delete.");
                    }
                    break;

                case 4:
                    System.out.print("Enter user name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);

                    if (libraryService.addUser(user)) {
                        System.out.println("✅ User added successfully!");
                    } else {
                        System.out.println("❌ Failed to add user.");
                    }
                    break;

                case 5:
                    System.out.print("Enter book ID to issue: ");
                    int bookIdToIssue = sc.nextInt();
                    System.out.print("Enter user ID to issue to: ");
                    int userIdToIssue = sc.nextInt();

                    if (libraryService.issueBook(bookIdToIssue, userIdToIssue)) {
                        System.out.println("✅ Book issued successfully!");
                    } else {
                        System.out.println("❌ Book is already issued or not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter book ID to return: ");
                    int bookIdToReturn = sc.nextInt();

                    if (libraryService.returnBook(bookIdToReturn)) {
                        System.out.println("✅ Book returned successfully!");
                    } else {
                        System.out.println("❌ Book was not issued or not found.");
                    }
                    break;

                case 7:
                    List<Book> issuedBooks = libraryService.getIssuedBooks();
                    System.out.println("📦 Issued Books:");
                    for (Book ib : issuedBooks) {
                        System.out.println(ib.getId() + ": " + ib.getTitle() + " by " + ib.getAuthor());
                    }
                    break;

                case 8:
                    System.out.print("Enter keyword (title/author): ");
                    String keyword = sc.nextLine();
                    List<Book> searchResults = libraryService.searchBooks(keyword);
                    System.out.println("🔍 Search Results:");
                    for (Book sb : searchResults) {
                        System.out.println(sb.getId() + ": " + sb.getTitle() + " by " + sb.getAuthor());
                    }
                    break;

                case 9:
                    running = false;
                    System.out.println("👋 Exiting... Bye!");
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    }

