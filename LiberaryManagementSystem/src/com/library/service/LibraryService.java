package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.UserDAO;
import com.library.model.Book;
import com.library.model.User;

import java.util.*;

public class LibraryService {

    private BookDAO bookDAO;
    private UserDAO userDAO;

    public LibraryService(){
        this.bookDAO=new BookDAO();
        this.userDAO=new UserDAO();
    }

    public List<Book> getAllBooks(){
        return bookDAO.getAllBooks();
    }

    public boolean deleteBook(int id) {
        return bookDAO.deleteBookByID(id);  // Use your actual method name here
    }


    public List<Book> searchBooks(String keyword){
        return bookDAO.searchBooksByTitle(keyword);
    }

    public boolean addBook(Book book){
        return bookDAO.addBook(book);
    }

    public boolean addUser(User user){
        return userDAO.addUser(user);
    }

    public boolean issueBook(int bookId,int userId){
        Book book=bookDAO.getBookById(bookId);
        if (book==null || book.isIssued()){
            return false;
        }
        boolean issued=bookDAO.issueBook(bookId,userId);
        return issued;
    }

    public boolean returnBook(int bookId){
        Book book=bookDAO.getBookById(bookId);
        if(book==null||!book.isIssued()){
            return false;
        }
        return bookDAO.returnBook(bookId);
    }
    public List<Book> getIssuedBooks(){
        return bookDAO.getIssuedBooks();
    }

}
