package com.example.playce;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.*;

@CrossOrigin
@RestController
public class ResultController {
    private static final String ADDRESS_NOT_GIVEN = "Address not given";
    private static final String NO_TYPE_GIVEN = "No type given";
    private static final String SELECT_WHERE_CATEGORY_IS = "select * from playces where category=\"";

    @RequestMapping("/result")
    public Result generateResult(@RequestParam(value = "name", defaultValue = "Firestone Grill") String name) {
        return new Result(name, 1, 1, ADDRESS_NOT_GIVEN, 35.2862, -120.654, "No category");
    }

    @RequestMapping("/getPlayceResult")
    public Result generatePlayceResult(@RequestParam(value = "name", defaultValue = "Firestone Grill") String playceName) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String query = "select * from playces where name=?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_3cf2d9a2c001143?reconnect=true", "bd9b14204c0c56", "2daf5b5d");

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, playceName);

            rs = pstmt.executeQuery();
            rs.next();
            return new Result(rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7));
        } catch (Exception e) {
            return new Result(e.toString(), 0, 0, ADDRESS_NOT_GIVEN, 0, 0, NO_TYPE_GIVEN);
        } finally {
            closeConnections(rs, pstmt, con);
        }
    }

    @RequestMapping(path = "/questionnaire", method = RequestMethod.POST)
    public MultipleResults generateResults(@RequestBody Questionnaire questionnaire) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList < String > colNames = new ArrayList < > ();
        ArrayList < String > colValues = new ArrayList < > ();
        String priceString = questionnaire.getPrice();
        int price = priceString != null ? priceString.length() : 0;
        boolean isRestaurant = questionnaire.getCategory().equals("restaurant");
        boolean isShopping = questionnaire.getCategory().equals("shopping");
        boolean isRecreation = questionnaire.getCategory().equals("recreation");

        String activitiesOver21 = questionnaire.getActivitiesOver21();
        String restaurantType = questionnaire.getRestaurantType();
        String specialty = questionnaire.getSpecialty();
        String ethnicity = questionnaire.getEthnicity();
        boolean useRating = questionnaire.getUseRating() == "Yes";

        String activeActivities = questionnaire.getActiveActivities();
        String inactiveActivities = questionnaire.getInactiveActivities();
        String shoppingCategories = questionnaire.getShoppingCategories();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_3cf2d9a2c001143?reconnect=true", "bd9b14204c0c56", "2daf5b5d");
            stmt = con.createStatement();

            colNames.add(SELECT_WHERE_CATEGORY_IS);
            colValues.add(questionnaire.getCategory());

            if (isRestaurant) {
               parseRestaurants(colNames, colValues, activitiesOver21, restaurantType, specialty, ethnicity, price);
            }
            if (isRecreation) {
                colNames.add("\" and subcategory=\"");
                colValues.add(activeActivities != null ? activeActivities : inactiveActivities);
            }
            if (isShopping) {
                colNames.add("\" and subsubcategory=\"");
                colValues.add(specialty != null ? specialty : ethnicity);
            }

            if (useRating) {
                colNames.add("\" sort by rating;\"");
                colValues.add(shoppingCategories);
            }

            String query = createQuery(colNames, colValues);
            rs = stmt.executeQuery(query);

            while (!rs.next() && colNames.size() > 1) {
                colNames.remove(colNames.size() - 1);
                colValues.remove(colValues.size() - 1);
                query = createQuery(colNames, colValues);
                rs.close();
                rs = stmt.executeQuery(query);
            }

            MultipleResults.MultipleResultsBuilder multR = MultipleResults.builder();

            int count = 0;
            Result[] r = new Result[20];
            while (rs.next() && count < 20) {
                r[count] = (new Result(rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7)));
                count++;
            }

            return multR.results(r).build();
        } catch (Exception e) {
            Result[] r = new Result[1];
            r[0] = new Result(e.toString(), 0, 0, ADDRESS_NOT_GIVEN, 0, 0, NO_TYPE_GIVEN);
            closeConnections(rs, stmt, con);
            return new MultipleResults(r);
        } finally {
            closeConnections(rs, stmt, con);
        }
    }

    private void parseRestaurants(
          ArrayList colNames,
          ArrayList colValues,
          String activitiesOver21,
          String restaurantType,
          String specialty,
          String ethnicity,
          int price) {
          colNames.add("\" and subcategory=\"");
          colValues.add(activitiesOver21 != null ? activitiesOver21 : restaurantType);
          colNames.add("\" and subsubcategory=\"");
          colValues.add(specialty != null ? specialty : ethnicity);
         colNames.add("\" and price<=\"");
         colValues.add(Integer.toString(price));
    }

    private void closeConnections(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) { /* ignored */ }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) { /* ignored */ }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) { /* ignored */ }
        }
    }

    private double calculateDistance(double lat1, double long1, double lat2, double long2) {

        int earthRadiusMi = 3959;
        //fGunction sourced from stack overflow
        //it calculates linear distance between two specified coordinates
        double dLat = degreesToRadians(lat2 - lat1);
        double dLon = degreesToRadians(long2 - long1);
        double latitude1 = degreesToRadians(lat1);
        double latitude2 = degreesToRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(latitude1) * Math.cos(latitude2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadiusMi * c;
    }

    private static double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private String createQuery(ArrayList < String > colNames, ArrayList < String > colValues) {
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < colNames.size(); i++) {
            query.append(colNames.get(i));
            query.append(colValues.get(i));
        }

        query.append("\"");
        return query.toString();
    }
}
