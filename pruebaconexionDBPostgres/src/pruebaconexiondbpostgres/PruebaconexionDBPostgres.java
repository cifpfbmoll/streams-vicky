/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaconexiondbpostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victoriapenas
 */
public class PruebaconexionDBPostgres {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/daw", "victoriapenas", "");){
            System.out.println("Conectado correctamente a la Base de Datos");
        } catch (SQLException ex) {
            System.out.println(ex.getSQLState());
            System.out.println(ex.getMessage());
            System.out.println("No se ha podido conectar a la base de datos");
        }

    }
    
}
