package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;
import java.lang.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookDAO {

    public boolean addBook(Book book){
        String query="INSERT INTO books (title,author,category,is_issued) VALUES(?,?,?,?)";

        try(Connection conn= DBConnection.getConnection();
            PreparedStatement stmt=conn.prepareStatement(query)){

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3,book.getCategory());
            stmt.setBoolean(4,book.isIssued());

            int rows=stmt.executeUpdate();
            return rows>0;
        }
        catch (SQLException e){
            System.out.println("Error adding book: "+e.getMessage());
            return false;
        }
    }

    public List<Book> getAllBooks(){
        List<Book> books=new ArrayList<>();
        String query="SELECT * FROM books";

        try(Connection conn=DBConnection.getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery(query)) {

            while (rs.next()){
                Book book=new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getBoolean("is_issued")

                );
                books.add(book);
            }
        }
        catch(SQLException e){
            System.out.println("Error retrieving Books : "+e.getMessage());
        }
        return books;
    }

    public boolean deleteBookByID(int bookId){
        String query="DELETE FROM books WHERE id=?";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement stmt=conn.prepareStatement(query)){

            stmt.setInt(1,bookId);
            int rows=stmt.executeUpdate();
            return rows>0;
        }
        catch(SQLException e){
            System.out.println("Error deleting book : "+e.getMessage());
            return false;
        }
    }

    public List<Book> searchBooksByTitle(String titleKeyword){
        List<Book> books=new ArrayList<>();
        String query="SELECT * FROM books WHERE title ILIKE ?";

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement stmt=conn.prepareStatement(query)){

            stmt.setString(1,"%"+titleKeyword+"%");
            ResultSet rs=stmt.executeQuery();

            while (rs.next()){
                Book book=new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getBoolean("is_issued")
                );
                books.add(book);
            }
        }
        catch (SQLException e){
            System.out.println("Error searching books : "+e.getMessage());
        }

        return books;
    }

    public Book getBookById(int id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setIssued(rs.getBoolean("is_issued"));
                return book;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean issueBook(int bookId, int userId) {
        String query = "UPDATE books SET is_issued = TRUE, issued_to_user_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean returnBook(int bookId) {
        String query = "UPDATE books SET is_issued = FALSE, issued_to_user_id = NULL WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Book> getIssuedBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE is_issued = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setIssued(true);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
