/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;

import static Practica7Ejercicio3.Alumno.calcularTotales;
import static Practica7Ejercicio3.Alumno.crearFicheroObjAlumnos;
import static Practica7Ejercicio3.Alumno.imprimirObjAlumno;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Scanner;
/**
 *
 * @author victoriapenas
 */
public class Main {
    
    public static void main(String[] args) {
        
        //encabezados        
        String [] boletinTitulos = {"-------------------------------------------",
            "Boletín de notas CIFP FBMOLL","-------------------------------------------",
        "Alumno: ","------------------------------   -------",
        "Módulo                            Nota","------------------------------   -------",
        "Lenguaje de marcas","Programación","Entornos de desarrollo","Base de datos",
        "Sistemas informáticos","FOL","-------------------------------------------",
        "Nº de módulos aprobados: ","Nº de módulos suspendidos: ","Nº de módulos convalidados: ",
        "-------------------------------------------","Fecha: ", "Lugar: Palma de Mallorca"};
        try {
            mostrarMenu(boletinTitulos);
        } catch (FileNotFoundException ex) {
            System.out.println("No se ha encontrado el fichero de salida");
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más info:");
            System.out.println(ex.getCause());
        }
        
    }
    
    public static void mostrarMenu(String [] boletinTitulos) throws FileNotFoundException,IOException{
        Scanner lector = new Scanner(System.in);
        int opcion;
        boolean salir = false;
        do{
            System.out.println("---------------------- M E N U ----------------------");
            System.out.println("1. Generar boletines de notas de los alumnos");
            System.out.println("2. Crear y mostrar fichero de objetos con os datos de los alumnos");
            System.out.println("3. Salir");
            System.out.println("Dime una opcion: ");
            opcion = lector.nextInt();
            switch(opcion){
                case 1:
                    generarBoletines(boletinTitulos);
                    break;
                case 2:
                    crearFicheroObjAlumnos();
                    imprimirObjAlumno(boletinTitulos);
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("La opcion indicada no existe, indica otra.");
            }
        }while(salir == false);
    }
    
    public static void generarBoletines(String [] boletinTitulos) throws FileNotFoundException, IOException{
        String linea = "";
        String [] notasAlumno = null;
        File entrada = new File("notas.txt");
        String docSalida = "";
        try (BufferedReader lector = new BufferedReader(new FileReader(entrada))){ //TO DO mail a Rafa
            do{
                linea = lector.readLine();
                if (linea != null){//tengo que poner este if, porque sino me salta la excepcion nullpointer exception
                    notasAlumno = linea.split(" ");
                    /*creo el fichero de salida a partir de los datos del alumno*/
                    docSalida = (generarFicheroSalida(notasAlumno)+".txt");
                    escribirCabecera(boletinTitulos, notasAlumno, docSalida);
                    escribirContenido(boletinTitulos, notasAlumno, docSalida);
                    escribirResumen(boletinTitulos,notasAlumno,docSalida);
                }
            } while (linea != null); //cuando llegamos al final del fichero, el buffer devuelve un null
            System.out.println("Ficheros creados con éxito.");
        }
    }
    
    public static String generarFicheroSalida(String [] datosAlumno){
        String docSalida = "";
        for (int i = 0;i<3;i++){//el nombre completo está desde la posicion 0 a las 2
            docSalida += datosAlumno[i];
        }
        return docSalida;
    }
    
    public static void escribirCabecera(String [] boletinTitulos, String [] datosAlumno, String ficheroSalida) throws FileNotFoundException, IOException{
        String nuevaLinea = System.getProperty("line.separator");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ficheroSalida), StandardCharsets.UTF_8)){
            for (int i = 0; i<7; i++){
                writer.write(boletinTitulos[i]);
                if (i==3){
                    for(int j = 0; j<3;j++){//escribo el nombre del alumno
                        writer.write(datosAlumno[j] + " ");
                    }
                }
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }
                
    public static void escribirContenido(String [] boletinTitulos, String [] notasAlumno, String ficheroSalida) throws FileNotFoundException, IOException{
        String nuevaLinea = System.getProperty("line.separator");
        int pos = 3; //auxiliar para escribir las notas de los alumnos, empezamos en la posición 3 de la array
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ficheroSalida, true), StandardCharsets.UTF_8)){
            for (int i = 7; i<=12; i++){//hasta la posicion 12 están los titulos del contenido
                /*con la funcion rightpad pongo los espacios de la derecha,
                siendo 35 la longitud total que tiene que acer la linea*/
                writer.write(rightpad(boletinTitulos[i],35));
                writer.write(notasAlumno[pos]);
                pos++;
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }
    
    public static void escribirResumen(String [] boletinTitulos, String [] notasAlumno, String ficheroSalida) throws FileNotFoundException, IOException{
        String nuevaLinea = System.getProperty("line.separator");
        int pos = 0; //auxiliar para recorrer la array notasTotales
        int [] notasTotales = calcularTotales(notasAlumno);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ficheroSalida, true), StandardCharsets.UTF_8)){
            for (int i = 13; i<boletinTitulos.length; i++){
                writer.write(boletinTitulos[i]);
                if (i > 13 && i < 17){//distinto a 13, porque en la posicion 13 solo quiero imprimir na linea divisoria
                    writer.write(Integer.toString(notasTotales[pos]));//convierto a String para poder escribir
                    pos++;
                }
                if(i == 18){//en la posicion 18 de la array registro la fecha
                    writer.write(registrarFecha());
                }
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }
    
    public static String registrarFecha() throws IOException{
        //fuente: http://lineadecodigo.com/java/obtener-la-hora-en-java/
        Calendar calendario = Calendar.getInstance();
        String fecha = calendario.get(Calendar.DATE)+"/"+
                (calendario.get(Calendar.MONTH) + 1)+"/"+calendario.get(Calendar.YEAR);
            
        return fecha;
    }
    
    /*esta función me rellena espacios a la derecha*/
    public static String rightpad(String string, int length) {
        return String.format("%-" + length + "." + length + "s", string);
    }
    /*esta función me rellena espacios a la izquierda*/
    private String leftpad(String text, int length) {
        return String.format("%" + length + "." + length + "s", text);
    }
}
