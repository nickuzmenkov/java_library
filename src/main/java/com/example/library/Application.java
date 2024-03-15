package com.example.library;

import com.example.library.book.Book;
import com.example.library.library.Library;
import com.example.library.reader.Reader;

public class Application {
    public static void main(String[] args) {
        Library library = new Library();

        Book book1 = new Book("Clean Code", "Robert Martin");
        Book book2 = new Book("Design Patterns", "Erich Gamma");
        Reader reader1 = new Reader("John", "Doe");
        Reader reader2 = new Reader("Jane", "Doe");

        library.addBook(book1);
        library.addBook(book2);
        library.addReader(reader1);
        library.addReader(reader2);

        library.borrowBook(book1, reader1);
        library.borrowBook(book2, reader2);
        library.returnBook(book1, reader1);
    }
}
