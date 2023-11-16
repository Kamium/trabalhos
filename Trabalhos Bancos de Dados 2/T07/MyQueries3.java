package com.oracle.tutorial.jdbc;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyQueries3 {
  
  Connection con;
  JDBCUtilities settings;  
  
  public MyQueries3(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void modifyPrices(Connection con) throws SQLException {

    Statement stmt = null;

    try {
        System.out.println("Digite o multiplicador como um numero real (Ex.: 5% = 1,05):");
        Scanner in = new Scanner(System.in);
        double percentage = in.nextDouble();

        stmt = con.createStatement();
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet uprs = stmt.executeQuery("SELECT * FROM DEPOSITO");

        while (uprs.next()) {
            double saldo_deposito = uprs.getDouble("SALDO_DEPOSITO");
            double juros = saldo_deposito * (percentage - 1); // Calcula o juros baseado na porcentagem

            // Adicione os juros ao saldo do depósito
            saldo_deposito += juros;

            // Atualize o valor do saldo do depósito na linha atual do ResultSet
            uprs.updateDouble("SALDO_DEPOSITO", saldo_deposito);

            // Atualize a linha no banco de dados
            uprs.updateRow();
        }
    } catch (SQLException e) {
        JDBCTutorialUtilities.printSQLException(e);
    } finally {
        if (stmt != null) {
            stmt.close();
        }
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

 	MyQueries3.modifyPrices(myConnection);

    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
