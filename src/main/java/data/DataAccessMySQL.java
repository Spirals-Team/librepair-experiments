/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import entity.Author;
import entity.Book;
import entity.City;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.ICity;
import interfaces.IDataAccessor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andreas
 */
public class DataAccessMySQL implements IDataAccessor {

    private Connection conn = null;
    private DBConnectorMySQL mysql = null;
    private String name = "DataAccessMySQL";
    private String tableName = "BooksTable";

    public DataAccessMySQL() {
        mysql = new DBConnectorMySQL();
        conn = mysql.getConn();
    }

    public ResultSet getUserStory1Query(String city, Statement st) {
        try {
            String query = "SELECT bookTitle,fullName FROM BooksTable WHERE name = '" + city + "'";

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getUserStory2Query(String bookTitle, Statement st) {
        try {
            String query = "SELECT name,lat,lon FROM BooksTable WHERE bookTitle = '" + bookTitle + "'";
            System.out.println(query);

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getUserStory3Query(String authorFullName, Statement st) {
        try {
            String query = "SELECT bookTitle,fullName,name,lat,lon FROM BooksTable WHERE fullName = '" + authorFullName + "'";

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getUserStory4Query(double lat, double lon, Statement st) {
        try {
            String query = "SELECT bookTitle,fullName,name,lat,lon FROM BooksTable WHERE lat = " + lat + " AND lon = " + lon;
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<IBook> getBooksByCityName(String cityName) throws NotFoundExceptionMapper {
        List<IBook> list = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = getUserStory1Query(cityName, st);
            while (rs.next()) {
                String bookTitle = rs.getString("bookTitle");
                String authorFullName = rs.getString("fullName");
                Author author = new Author(authorFullName);
                IBook book = new Book(bookTitle, author);
                list.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<ICity> getCitiesByBookTitle(String bookTitle) throws NotFoundExceptionMapper {
        List<ICity> list = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = getUserStory2Query(bookTitle, st);
            while (rs.next()) {
                String cityName = rs.getString("name");
                Double lat = rs.getDouble("lat");
                Double lon = rs.getDouble("lon");
                ICity city = new City(cityName, lat, lon);
                list.add(city);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessMySQL.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    public List<IBook> getMentionedCitiesByAuthorName(String authorName) throws NotFoundExceptionMapper {
        List<IBook> list = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        HashMap<String, IBook> bookMap = new HashMap();
        try {
            st = conn.createStatement();
            rs = getUserStory3Query(authorName, st);
            while (rs.next()) {
                String bookTitle = rs.getString("bookTitle");
                String cityName = rs.getString("name");
                Double lat = rs.getDouble("lat");
                Double lon = rs.getDouble("lon");
                String authorFullName = rs.getString("fullName");
                Author author = new Author(authorFullName);
                if (!bookMap.containsKey(bookTitle)) {
                    IBook book = new Book(bookTitle, author);
                    List<ICity> cityList = new ArrayList();
                    ICity city = new City(cityName, lat, lon);
                    cityList.add(city);
                    book.setCities(cityList);
                    bookMap.put(cityName, book);
                } else {
                    IBook book = bookMap.get(bookTitle);
                    List<ICity> cityList = book.getCities();
                    ICity city = new City(cityName, lat, lon);
                    cityList.add(city);
                    book.setCities(cityList);
                    bookMap.put(cityName, book);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (IBook book : bookMap.values()) {
            list.add(book);
        }
        return list;
    }

    @Override
    public List<IBook> getBooksByGeolocation(double lat, double lon) throws NotFoundExceptionMapper {
        List<IBook> list = new ArrayList();
        Statement st = null;
        ResultSet rs = null;
        HashMap<String, IBook> bookMap = new HashMap();
        try {
            st = conn.createStatement();
            rs = getUserStory4Query(lat, lon, st);
            while (rs.next()) {
                String bookTitle = rs.getString("bookTitle");
                String cityName = rs.getString("name");
                Double thisLat = rs.getDouble("lat");
                Double thisLon = rs.getDouble("lon");
                String authorFullName = rs.getString("fullName");
                Author author = new Author(authorFullName);
                if (!bookMap.containsKey(bookTitle)) {
                    IBook book = new Book(bookTitle, author);
                    List<ICity> cityList = new ArrayList();
                    ICity city = new City(cityName, thisLat, thisLon);
                    cityList.add(city);
                    book.setCities(cityList);
                    bookMap.put(cityName, book);
                } else {
                    IBook book = bookMap.get(bookTitle);
                    List<ICity> cityList = book.getCities();
                    ICity city = new City(cityName, lat, lon);
                    cityList.add(city);
                    book.setCities(cityList);
                    bookMap.put(cityName, book);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (IBook book : bookMap.values()) {
            list.add(book);
        }
        return list;
    }

    @Override
    public String getName() {
        return name;
    }

}
