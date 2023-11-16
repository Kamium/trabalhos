package com.oracle.tutorial.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyQueries2 {
  
  Connection con;
  JDBCUtilities settings;  
  
  public MyQueries2(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void cursorHoldabilitySupport(Connection conn) throws SQLException {
    DatabaseMetaData dbMetaData = conn.getMetaData();
    
    int[] resultSetTypes = {
        ResultSet.TYPE_FORWARD_ONLY,
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.TYPE_SCROLL_SENSITIVE
    };

    int[] resultSetConcurrencies = {
        ResultSet.CONCUR_READ_ONLY,
        ResultSet.CONCUR_UPDATABLE
    };

    for (int resultSetType : resultSetTypes) {
        for (int resultSetConcurrency : resultSetConcurrencies) {
            String resultSetTypeStr = getResultSetTypeString(resultSetType);
            String resultSetConcurrencyStr = getResultSetConcurrencyString(resultSetConcurrency);

            boolean isSupported = dbMetaData.supportsResultSetConcurrency(resultSetType, resultSetConcurrency);

            System.out.println("Supports " + resultSetTypeStr + " with " + resultSetConcurrencyStr + "? " + isSupported);
        }
    }
}

public static String getResultSetTypeString(int resultSetType) {
    switch (resultSetType) {
        case ResultSet.TYPE_FORWARD_ONLY:
            return "TYPE_FORWARD_ONLY";
        case ResultSet.TYPE_SCROLL_INSENSITIVE:
            return "TYPE_SCROLL_INSENSITIVE";
        case ResultSet.TYPE_SCROLL_SENSITIVE:
            return "TYPE_SCROLL_SENSITIVE";
        default:
            return "Unknown ResultSet Type";
    }
}

public static String getResultSetConcurrencyString(int resultSetConcurrency) {
    switch (resultSetConcurrency) {
        case ResultSet.CONCUR_READ_ONLY:
            return "CONCUR_READ_ONLY";
        case ResultSet.CONCUR_UPDATABLE:
            return "CONCUR_UPDATABLE";
        default:
            return "Unknown ResultSet Concurrency";
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

 	MyQueries.cursorHoldabilitySupport(myConnection);

    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
