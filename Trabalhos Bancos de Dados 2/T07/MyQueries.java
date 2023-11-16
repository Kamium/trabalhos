package com.oracle.tutorial.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileReader; 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.util.Scanner; 

public class MyQueries {
  
  Connection con;
  JDBCUtilities settings;  
  
  public MyQueries(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

	public static void getMyData3(Connection con) throws SQLException {
    Statement stmt = null;
    String query = "SELECT " +"C.nome_cliente AS \"Nome do Cliente\", " +        "CO.nome_agencia AS \"Nome da Agência\", " +        "CO.numero_conta AS \"Número da Conta\", "+ "COALESCE(SUM(DP.saldo_deposito), 0) AS \"Soma de Depósitos\", " +        "COALESCE(SUM(EM.valor_emprestimo), 0) AS \"Soma de Empréstimos\" " +"FROM cliente C " +        "LEFT JOIN conta CO ON C.nome_cliente = CO.nome_cliente " +        "LEFT JOIN deposito DP ON CO.numero_conta = DP.numero_conta " + "LEFT JOIN emprestimo EM ON CO.numero_conta = EM.numero_conta " +"GROUP BY \"Nome do Cliente\", \"Nome da Agência\", \"Número da Conta\"";
    
    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            // Recuperar os dados usando índices numéricos
            String nomeCliente = rs.getString(1);
            String nomeAgencia = rs.getString(2);
            Integer numeroConta = rs.getInt(3);
            Double somaDepositos = rs.getDouble(4);
            Double somaEmprestimos = rs.getDouble(5);
            
            System.out.println("Método 1 - Índices numéricos:");
            System.out.println("Nome do Cliente: " + nomeCliente);
            System.out.println("Nome da Agência: " + nomeAgencia);
            System.out.println("Número da Conta: " + numeroConta);
            System.out.println("Soma de Depósitos: " + somaDepositos);
            System.out.println("Soma de Empréstimos: " + somaEmprestimos);
            
            // Recuperar os dados usando alias (cláusulas "AS ...")
            String nomeClienteAlias = rs.getString("Nome do Cliente");
            String nomeAgenciaAlias = rs.getString("Nome da Agência");
            Integer numeroContaAlias = rs.getInt("Número da Conta");
            Double somaDepositosAlias = rs.getDouble("Soma de Depósitos");
            Double somaEmprestimosAlias = rs.getDouble("Soma de Empréstimos");
            
            System.out.println("Método 2 - Alias (AS ...):");
            System.out.println("Nome do Cliente: " + nomeClienteAlias);
            System.out.println("Nome da Agência: " + nomeAgenciaAlias);
            System.out.println("Número da Conta: " + numeroContaAlias);
            System.out.println("Soma de Depósitos: " + somaDepositosAlias);
            System.out.println("Soma de Empréstimos: " + somaEmprestimosAlias);
        }
    } catch (SQLException e) {
        JDBCUtilities.printSQLException(e);
    } finally {
        if (stmt != null) { stmt.close(); }
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

 	MyQueries.getMyData3(myConnection);

    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
