/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaconectiondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author victoriapenas
 */
public class PruebaConectionDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            // TODO code application logic here

            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/daw", "root", "");
            Statement st = con2.createStatement();
            System.out.println("Conectado correctamente a la Base de Datos");
        } catch (SQLException ex) {
            System.out.println(ex.getSQLState());
            System.out.println(ex.getMessage());
            System.out.println("No se ha podido conectar a la base de datos");
        }
    
    }
    
}
