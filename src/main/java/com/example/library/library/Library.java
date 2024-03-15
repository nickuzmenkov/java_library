package com.example.library.library;

import com.example.library.book.Book;
import com.example.library.reader.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library {
    private final HashMap<String, Book> books;
    private final HashMap<String, Reader> readers;
    private final HashMap<String, List<Book>> borrowedBooks;

    public Library() {
        this.books = new HashMap<>();
        this.readers = new HashMap<>();
        this.borrowedBooks = new HashMap<>();
    }

    public HashMap<String, Book> getBooks() {
        return books;
    }

    public HashMap<String, Reader> getReaders() {
        return readers;
    }

    public HashMap<String, List<Book>> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBook(Book book) throws IllegalArgumentException {
        if (books.containsKey(book.getId())) {
            throw new IllegalArgumentException("Cannot add duplicate book: " + book);
        }
        books.put(book.getId(), book);
    }

    public void removeBook(Book book) throws IllegalArgumentException {
        checkBookExists(book);
        books.remove(book.getId());
    }

    public void addReader(Reader reader) throws IllegalArgumentException {
        if (readers.containsKey(reader.getId())) {
            throw new IllegalArgumentException("Cannot add duplicate reader: " + reader);
        }
        readers.put(reader.getId(), reader);
        borrowedBooks.put(reader.getId(), new ArrayList<>());
    }

    public void removeReader(Reader reader) throws IllegalArgumentException {
        checkReaderExists(reader);
        readers.remove(reader.getId());
        borrowedBooks.remove(reader.getId());
    }

    public void borrowBook(Book book, Reader reader) throws IllegalArgumentException {
        checkBookExists(book);
        checkReaderExists(reader);
        addBorrowedBook(book, reader);
        removeBook(book);
    }

    public void returnBook(Book book, Reader reader) throws IllegalArgumentException {
        checkReaderExists(reader);
        removeBorrowedBook(book, reader);
        addBook(book);
    }

    private void checkBookExists(Book book) throws IllegalArgumentException {
        if (!books.containsKey(book.getId())) {
            throw new IllegalArgumentException("No such book: " + book);
        }
    }

    private void checkReaderExists(Reader reader) throws IllegalArgumentException {
        if (!readers.containsKey(reader.getId())) {
            throw new IllegalArgumentException("No such reader: " + reader);
        }
    }

    private void addBorrowedBook(Book book, Reader reader) {
        List<Book> readerLentBooks = borrowedBooks.get(reader.getId());
        readerLentBooks.add(book);
        borrowedBooks.put(reader.getId(), readerLentBooks);
    }

    private void removeBorrowedBook(Book book, Reader reader) throws IllegalArgumentException {
        List<Book> readerLentBooks = borrowedBooks.get(reader.getId());
        if (!readerLentBooks.contains(book)) {
            throw new IllegalArgumentException("No such book in borrowed books: " + book);
        }
        readerLentBooks.remove(book);
        borrowedBooks.put(reader.getId(), readerLentBooks);
    }
}
