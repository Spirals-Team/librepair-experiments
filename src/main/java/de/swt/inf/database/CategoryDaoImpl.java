package de.swt.inf.database;

import de.swt.inf.model.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private Connection connection;

    public CategoryDaoImpl(Connection connection){this.connection = connection;}

    public Category getCategory(int id) {
        try{
            String query = "SELECT * FROM category WHERE CATEGORY_ID = " + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            Category tempCat = new Category();
            tempCat.setName(rs.getString("Name"));
            tempCat.setId(rs.getInt("CATEGORY_ID"));
            tempCat.setColor(rs.getShort("Color"));

            return tempCat;
        }catch (SQLException e){
            System.err.println(e);
        }
        return null;
    }

    public Category getCategory(Category category) {
        try{
            String query = "SELECT * FROM category WHERE CATEGORY_ID = " + category.getId();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            Category tempCat = new Category();
            tempCat.setName(rs.getString("Name"));
            tempCat.setId(rs.getInt("CATEGORY_ID"));
            tempCat.setColor(rs.getShort("Color"));

            return tempCat;
        }catch (SQLException e){
            System.err.println(e);
        }
        return null;
    }

    public List<Category> getAllCategories(){
        List<Category> alleCategories = new ArrayList<Category>();
        try{
            String query = "SELECT * FROM category";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next() == true){
                Category tempCat = new Category();
                tempCat.setId(rs.getInt("CATEGORY_ID"));
                tempCat.setName(rs.getString("Name"));
                tempCat.setColor(rs.getShort("Color"));
                alleCategories.add(tempCat);
            }
            return alleCategories;
        }catch(SQLException e){
            System.err.println(e);
        }
        return alleCategories;
    }

    public Category getCategoryByName(String name){
        try{
            String query = "SELECT * FROM category WHERE Name ='" + name;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            Category tempCat = new Category();
            rs.next();
            tempCat.setId(rs.getInt("CATEGORY_ID"));
            tempCat.setName(rs.getString("Name"));
            tempCat.setColor(rs.getShort("Color"));
            tempCat.setIcon(rs.getString("Icon"));
            return tempCat;
        }catch(SQLException e){
            System.err.println(e);
        }
        return null;
    }

    public boolean updateCategory(Category category) {
        try{
            String query = "UPDATE category SET Name = '" + category.getName() + "', Color ='" + category.getColor() +
                    "' WHERE CATEGORY_ID = '" + category.getId() + "'";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }


    public boolean deleteCategory(int id) {
        try{
            String query = "DELETE FROM category WHERE CATEGORY_ID = " + id;
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }

    public boolean deleteCategory(Category category){
        try{
            String query = "DELETE FROM category WHERE CATEGORY_ID = " + category.getId();
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch (SQLException e){
            System.err.println(e);
        }
        return false;
    }

    public boolean addCategory(Category category) {
        try{
            String query = "INSERT INTO category VALUES ('" + category.getId() + "', '" + category.getName() + "', '" +
                    category.getColor() + "', '" + category.getIcon() + "')";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            return true;
        }catch(SQLException e){
            System.err.println(e);
        }
        return false;
    }
}
