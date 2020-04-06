/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import static practica7.Practica7.pedirRuta;

/**
 *
 * @author victoriapenas
 */
public class Main {
    
    public static void main(String[] args) {
        
        //encabezados
        String [] boletinTitulos = {"---------------------------------------------",
            "Boletín de notas CIFP FBMOLL","---------------------------------------------",
        "Alumno: ","------------------------------   -------", "Módulo                            Nota",
        "------------------------------   -------", "Lenguaje de marcas", "Programación",
        "Entornos de desarrollo","Base de datos","Sistemas informáticos","FOL",
        "---------------------------------------------","Nº de módulos aprobados: ",
        "Nº de módulos suspendidos: "," Nº de módulos convalidados: ", "-------------------------------------------",
        "Fecha: ", "Lugar: Palma de Mallorca"};
        
        generarBoletines(boletinTitulos);
    }
    
    public static void generarBoletines(String [] boletinTitulos){
        String linea = "";
        String [] notasAlumno = null;
        File entrada = new File("notas");
        BufferedWriter writer = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(entrada))){
            do{
                lector.readLine();
                if (linea != null){//tengo que poner este if, porque sino me salta la excepcion nullpointer exception
                    notasAlumno = linea.split(" ");
                    /*instancia aqui el buffer porque necesitaba la array de
                    alumnos para crear la nomenclatura del fichero de salida*/
                    escribircabecera(boletinTitulos, notasAlumno, generarFicheroSalida(notasAlumno));
                    writer = new BufferedWriter(new FileWriter(generarFicheroSalida(notasAlumno)));
                    //TO DO escribir notas pendiente me quedo aqui
                }
            } while (linea != null); //cuando llegamos al final del fichero, el buffer devuelve un null
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String generarFicheroSalida(String [] datosAlumno){
        String docSalida = "";
        for (int i = 0;i<datosAlumno.length;i++){
            docSalida += datosAlumno[i];
        }
        return docSalida;
    }
    
    public static void escribircabecera(String [] boletinTitulos, String [] datosAlumno, String ficheroSalida){
        String nuevaLinea = System.getProperty("line.separator");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ficheroSalida, true), StandardCharsets.UTF_8)){
            for (int i = 0; i<7; i++){
                writer.write(boletinTitulos[i]);
                writer.append(nuevaLinea);//con esto añado un salto de linea
                if (i==3){
                    for(int j = 0; j<3;i++){//escribo el nombre del alumno
                        writer.write(datosAlumno[i]);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el fichero de salida");
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más info:");
            System.out.println(ex.getCause());
        }
    }
    
}
