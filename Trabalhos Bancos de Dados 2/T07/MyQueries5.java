package com.oracle.tutorial.jdbc;


import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.io.FileReader; 

import java.io.BufferedReader; 

import java.io.IOException; 

import java.util.Scanner; 



public class MyQueries5 {

  

  Connection con;

  JDBCUtilities settings;  

  

  public MyQueries5(Connection connArg, JDBCUtilities settingsArg) {

    this.con = connArg;

    this.settings = settingsArg;

  }



    public static void insertRow(Connection con) throws SQLException, IOException {
        String sql = "INSERT INTO debito (numero_debito, valor_debito, motivo_debito, data_debito, numero_conta, nome_agencia, nome_cliente) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Inserir a primeira tupla
            pstmt.setInt(1, 2000);
            pstmt.setDouble(2, 150.0);
            pstmt.setInt(3, 1);
            pstmt.setDate(4, Date.valueOf("2014-01-23"));
            pstmt.setInt(5, 46248);
            pstmt.setString(6, "UFU");
            pstmt.setString(7, "Carla Soares Sousa");
            pstmt.executeUpdate();

            // Inserir a segunda tupla
            pstmt.setInt(1, 2001);
            pstmt.setDouble(2, 200.0);
            pstmt.setInt(3, 2);
            pstmt.setDate(4, Date.valueOf("2014-01-23"));
            pstmt.setInt(5, 26892);
            pstmt.setString(6, "Glória");
            pstmt.setString(7, "Carolina Soares Souza");
            pstmt.executeUpdate();

            // Inserir a terceira tupla
            pstmt.setInt(1, 2002);
            pstmt.setDouble(2, 500.0);
            pstmt.setInt(3, 3);
            pstmt.setDate(4, Date.valueOf("2014-01-23"));
            pstmt.setInt(5, 70044);
            pstmt.setString(6, "Cidade Jardim");
            pstmt.setString(7, "Eurides Alves da Silva");
            pstmt.executeUpdate();
        }
    }





  public static void main(String[] args) {

    JDBCUtilities myJDBCUtilities;

    Connection myConnection = null;

    if (args[0] == null) {

      System.err.println("Properties file not specified at command line");

      return;

    } else {

      try {

        myJDBCUtilities = new JDBCUtilities(args[0]);

      } catch (Exception e) {

        System.err.println("Problem reading properties file " + args[0]);

        e.printStackTrace();

        return;

      }

    }



    try {

      myConnection = myJDBCUtilities.getConnection();



 	MyQueries5.insertRow(myConnection);



    } catch (SQLException e) {

      JDBCUtilities.printSQLException(e);

    } catch (IOException e) { e.printStackTrace(); } finally {

      JDBCUtilities.closeConnection(myConnection);

    }



  }

}
