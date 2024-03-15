package com.example.library.library;

import com.example.library.book.Book;
import com.example.library.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Library library;

    @BeforeEach
    public void setUp() {
        library = new Library();
    }

    @Test
    public void testAddBook() {
        Book book = getBook();
        library.addBook(book);
        assertTrue(library.getBooks().containsKey(book.getId()));
        assertEquals(book, library.getBooks().get(book.getId()));
    }

    @Test
    public void testAddDuplicateBook() {
        Book book = getBook();
        library.addBook(book);
        assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
    }

    @Test
    public void testRemoveBook() {
        Book book = getBook();
        library.addBook(book);
        library.removeBook(book);
        assertFalse(library.getBooks().containsKey(book.getId()));
    }

    @Test
    public void testRemoveNonExistentBook() {
        Book book = getBook();
        assertThrows(IllegalArgumentException.class, () -> library.removeBook(book));
    }

    @Test
    public void testAddReader() {
        Reader reader = getReader();
        library.addReader(reader);
        assertTrue(library.getReaders().containsKey(reader.getId()));
        assertEquals(reader, library.getReaders().get(reader.getId()));
    }

    @Test
    public void testAddDuplicateReader() {
        Reader reader = getReader();
        library.addReader(reader);
        assertThrows(IllegalArgumentException.class, () -> library.addReader(reader));
    }

    @Test
    public void testRemoveReader() {
        Reader reader = getReader();
        library.addReader(reader);
        library.removeReader(reader);
        assertFalse(library.getReaders().containsKey(reader.getId()));
    }

    @Test
    public void testRemoveNonExistentReader() {
        Reader reader = getReader();
        assertThrows(IllegalArgumentException.class, () -> library.removeReader(reader));
    }

    @Test
    public void testBorrowBook() {
        Book book1 = getBook();
        Book book2 = getBook();
        library.addBook(book1);
        library.addBook(book2);
        Reader reader = getReader();
        library.addReader(reader);
        library.borrowBook(book1, reader);
        library.borrowBook(book2, reader);
        List<Book> borrowedBooks = library.getBorrowedBooks().get(reader.getId());
        assertEquals(2, borrowedBooks.size());
        assertEquals(book1, borrowedBooks.get(0));
        assertEquals(book2, borrowedBooks.get(1));
    }

    @Test
    public void testBorrowBookNonExistent() {
        Book book = getBook();
        Reader reader = getReader();
        library.addReader(reader);
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook(book, reader));
    }

    @Test
    public void testBorrowBookNonExistentReader() {
        Book book = getBook();
        library.addBook(book);
        Reader reader = getReader();
        assertThrows(IllegalArgumentException.class, () -> library.borrowBook(book, reader));
    }

    @Test
    public void testReturnBook() {
        Book book = getBook();
        library.addBook(book);
        Reader reader = getReader();
        library.addReader(reader);
        library.borrowBook(book, reader);
        library.returnBook(book, reader);
        assertEquals(0, library.getBorrowedBooks().get(reader.getId()).size());
    }

    @Test
    public void testReturnBookNotBorrowed() {
        Book book1 = getBook();
        Book book2 = getBook();
        library.addBook(book1);
        library.addBook(book2);
        Reader reader = getReader();
        library.addReader(reader);
        library.borrowBook(book1, reader);
        assertThrows(IllegalArgumentException.class, () -> library.returnBook(book2, reader));
    }

    @Test
    public void testReturnBookNonExistentReader() {
        Book book = getBook();
        library.addBook(book);
        Reader reader = getReader();
        assertThrows(IllegalArgumentException.class, () -> library.returnBook(book, reader));
    }

    private Book getBook() {
        return new Book(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    private Reader getReader() {
        return new Reader(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }
}
