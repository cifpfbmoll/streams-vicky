/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;

import static Practica7Ejercicio3.Alumno.crearFicheroObjAlumnos;
import static Practica7Ejercicio3.Alumno.generarBoletines;
import static Practica7Ejercicio3.Alumno.imprimirBoletin;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Scanner;

/**
 *
 * @author victoriapenas
 */
public class Main {
    
    public static void main(String[] args) {
        
        try {
            mostrarMenu();
        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el fichero de salida");
        } catch (ClassNotFoundException ex) {
            ex = new ClassNotFoundException("La clase que se está intentado leer no existe");
            System.out.println(ex.getMessage());
        } catch (StreamCorruptedException e){//fuente: http://www.chuidiang.org/java/ficheros/ObjetosFichero.php
            System.out.println("Esta excepcion ocurre cuando más de una instancia de"
                    + "la clase ObjectInputStream abre el mismo fichero.");
        } catch (EOFException ex) {//excepcion que debemos controlar al leer objetos
            /*Esta excepcion hay que ponerla manualmente siempre! el IDE no la pide, hay que ponerla a mano*/
            System.out.println("Fin de fichero");
        } catch (ErrornotaException ex) {
            try {
                System.out.println(ex.getMensaje());
                ex.registarErrores(ex.getMensaje(),ex.getStackTrace());
            } catch (IOException ex1) {
                System.out.println("Ha ocurrido un error inesperado. Más info:");
                System.out.println(ex1.getCause());
            }
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más info:");
            System.out.println(ex.getCause());
        }
        
    }
    
    public static void mostrarMenu() throws FileNotFoundException,IOException, ClassNotFoundException, ErrornotaException{
        Scanner lector = new Scanner(System.in);
        int opcion;
        boolean salir = false;
        do{
            System.out.println("---------------------- M E N U ----------------------");
            System.out.println("1. Generar boletines de notas de los alumnos");
            System.out.println("2. Crear y mostrar fichero de objetos con los datos de los alumnos");
            System.out.println("3. Salir");
            System.out.println("Dime una opcion: ");
            opcion = lector.nextInt();
            switch(opcion){
                case 1:
                    generarBoletines();
                    break;
                case 2:
                    crearFicheroObjAlumnos();
                    imprimirBoletin();
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("La opcion indicada no existe, indica otra.");
            }
        }while(salir == false);
    }
}
