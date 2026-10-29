# 📚 Library Management System

[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
[](https://www.google.com/search?q=)

<img alt="vlcsnap-2025-07-30-23h14m17s861" src="https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/12cbe8a4-f55c-4b40-85bb-d8e1405e7b84/djnt626-a0ea26d6-f48e-4b70-b63f-935167ff215b.gif?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzEyY2JlOGE0LWY1NWMtNGI0MC04NWJiLWQ4ZTE0MDVlN2I4NFwvZGpudDYyNi1hMGVhMjZkNi1mNDhlLTRiNzAtYjYzZi05MzUxNjdmZjIxNWIuZ2lmIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.nNXj06xpuDc9EBR6OICAj7c7IZ5XAdcbE3skb-c6aTM" />

A simple desktop application for managing a library's books and borrowing records. This system is developed in Java using Swing for the graphical user interface. It allows librarians to add, edit, and delete books, as well as manage borrowing and returning books.

-----

## 📚 Project Overview

This application provides a comprehensive solution for basic library management tasks, including:

  * **Book Management:** Add, edit, delete, and search for books by ISBN, title, author, or category.
  * **Borrowing & Returning:** Record new book loans and process returns.
  * **Record Keeping:** View all, active, and overdue borrowing records.
  * **Reporting:** Generate reports on library inventory and borrowing activity, including overdue books.

-----

## 🛠 Tech Stack

  * **Language:** Java
  * **GUI Framework:** Swing
  * **Data Structures:** `ArrayList` for storing books and borrow records.
  * **Date & Time:** `java.time.LocalDate` for handling dates.

-----

## ⚙️ Installation

To run this application, you need to have a Java Development Kit (JDK) installed. The project can be compiled and run directly from the command line or an IDE like IntelliJ IDEA or Eclipse.

### Command Line

```bash
# Compile all sources
javac -d out src/*.java

# Run the application
java -cp out LibraryManagementSystem
```

### IDE

1.  Open the project folder in IntelliJ IDEA, Eclipse or VS Code.
2.  Mark `src/` as the source folder.
3.  Run the `main` method in the `LibraryManagementSystem` class.

-----

## 📊 Visuals

| Plot Description                                         | Image |
|----------------------------------------------------------|-------|
| Main Screen                                        | ![Geographical Plot](https://github.com/user-attachments/assets/c6227180-050d-49c7-a96d-1a66362f98fb) |
| Borrow Screen    | ![Pairplot](https://github.com/user-attachments/assets/00db22e1-ae83-43f8-a5f4-ece380d8c1f8) |
| Record Screen                    | ![Histograms](https://github.com/user-attachments/assets/119cccef-d0bd-4f67-b83e-2fb4194b8d0d) |
| Report Screen   | ![Jet Graph](https://github.com/user-attachments/assets/36722a53-c118-40b7-b909-5b1a052af363) |


-----


## 🧪 How to Use the Application

The application features a tabbed interface for different functionalities:

  * **Books Tab:**

      * **Add Book:** Click "Add Book" to open a dialog for entering book details (ISBN, Title, Author, Category, Copies).
      * **Edit Book:** Select a book from the table and click "Edit Book" to modify its details.
      * **Delete Book:** Select a book and click "Delete Book" to remove it from the library's catalog.
      * **Search:** Use the search bar to find books.

  * **Borrow/Return Tab:**

      * **Borrow Book:** Enter the ISBN, borrower's name, and email to record a new loan.
      * **Return Book:** Enter the ISBN and borrower's name to mark a book as returned.

  * **Records Tab:**

      * View all borrowing records, including returned books.
      * Filter records to show only "Active" (currently borrowed) or "Overdue" books.

  * **Reports Tab:**

      * **Inventory Report:** Generates a detailed list of all books, their total copies, and available copies.
      * **Borrowing Report:** Provides statistics on total, active, and overdue borrowing records, along with a list of all overdue books.

## 🧩 Design Patterns (CCSW 413 Course Project)

This fork refactors the original single-file application by applying six GoF design patterns.

| Stage | Category | Pattern | Classes |
|---|---|---|---|
| 1 | Creational | Singleton | `LibraryManager` |
| 1 | Creational | Factory Method | `TabFactory`, `BooksTabFactory`, `BorrowReturnTabFactory`, `RecordsTabFactory`, `ReportsTabFactory`, `LibraryPanel` |
| 2 | Structural | Facade | `CirculationFacade`, `InputValidator`, `MessageService` |
| 2 | Structural | Adapter | `BookTableAdapter`, `BorrowRecordTableAdapter` |
| 3 | Behavioral | Observer | `LibraryObserver`, `LibraryManager` (subject), `BooksPanel`, `RecordsPanel` |
| 3 | Behavioral | Strategy | `RecordFilterStrategy`, `AllRecordsFilter`, `ActiveRecordsFilter`, `OverdueRecordsFilter` |

Each stage is tagged in git (`stage1`, `stage2`, `stage3`); `stage0` is the original code split into one class per file.

## 📁 Project Structure

```bash
src/
├── LibraryManagementSystem.java   # Main window
├── Book.java, BorrowRecord.java   # Model
├── LibraryManager.java            # Singleton + Observer subject
├── LibraryPanel.java              # Base class for all tabs
├── *Panel.java                    # Books, Borrow/Return, Records, Reports tabs
├── *TabFactory.java               # Factory Method
├── CirculationFacade.java         # Facade (+ InputValidator, MessageService)
├── *TableAdapter.java             # Adapter
├── LibraryObserver.java           # Observer
└── *RecordsFilter.java            # Strategy (+ RecordFilterStrategy)
```

-----

## 👨‍💻 Author

Original application:

  * **Ali Akbar Khan**
  * **Email**: aliakbarkhana79@gmail.com
  * **LinkedIn**: [aliakbar-khan](https://www.linkedin.com/in/aliiakbarkhan)

-----

## 🔒 License

This project is for educational and personal use. All rights are reserved.
