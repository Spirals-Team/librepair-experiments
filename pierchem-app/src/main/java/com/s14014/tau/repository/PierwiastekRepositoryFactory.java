package com.s14014.tau.repository;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class PierwiastekRepositoryFactory{
    public static IPierwiastekRepository getInstance(){

            try{
                String url = "jdbc:hsqldb:hsql://localhost/workdb";
                return new PierwiastekManagerImpl(DriverManager.getConnection(url));
            }

            catch (SQLException e){

                e.printStackTrace();
                return null;
            }


        }
    }
