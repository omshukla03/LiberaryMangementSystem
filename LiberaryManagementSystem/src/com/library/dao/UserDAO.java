package com.library.dao;

import com.library.model.User;
import com.library.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean addUser(User user){
        String query="INSERT INTO users(name,email) VALUES(?,?)";

        try(Connection conn= DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,user.getName());
            ps.setString(2, user.getEmail());

            int rows=ps.executeUpdate();
            return rows>0;
        }catch (SQLException e){
            System.out.println("Error Adding user: "+e.getMessage());
        }
        return false;
    }

    public List<User>getAllUsers(){
        List<User> users=new ArrayList<>();
        String query="SELECT * FROM users";

        try(Connection conn=DBConnection.getConnection();
            Statement stmt=conn.createStatement();
            ResultSet rs= stmt.executeQuery(query)){

            while (rs.next()){
                User user=new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                users.add(user);
            }
        }catch (SQLException e){
            System.out.println("Error Fetching users: "+e.getMessage());
        }
        return users;
    }

    public User getUserByEmail(String email){
        String query="SELECT * FROM users WHERE email=?";
        User user=null;

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();

            if (rs.next()){
                user=new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e){
            System.out.println("Error Searching User: "+e.getMessage());
        }
        return user;
    }

    public User getUserById(int id){
        String query="SELECT * FROM users WHERE id=?";
        User user=null;

        try(Connection conn=DBConnection.getConnection();
            PreparedStatement ps=conn.prepareStatement(query)){
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();

            if (rs.next()){
                user=new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e){
            System.out.println("Error Searching User by ID: "+e.getMessage());
        }
        return user;
    }
}
