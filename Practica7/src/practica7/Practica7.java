/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victoriapenas
 */
public class Practica7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //array de encabezados
        String [] encabezados = {"-----------------------","Cartelera de CineFBMoll",
            "-----------------------","-----", "Año:", "Director: ",
            "Duracion: ", "Sinopsis: ", "Reparto: ", "Sesión: ", "horas"};
                
        mostrarMenu(encabezados);
    }
    
    public static void mostrarMenu(String [] encabezados){
        Scanner lector = new Scanner(System.in);
        int opcion;
        boolean salir = false;
        do{
            System.out.println("---------------------- M E N U ----------------------");
            System.out.println("1. Lectura y escritura del fichero de cartelera byte a byte (byte Streams).");
            System.out.println("2. Lectura y escritura de fichero de cartelera carácter a carácter (character Streams).");
            System.out.println("3. Lectura y escritura de fichero línea a línea con buffers (character Streams).");
            System.out.println("4. Salir");
            System.out.println("Dime una opcion: ");
            opcion = lector.nextInt();
            switch(opcion){
                case 1:
                    leoBytes(encabezados);
                    break;
                case 2:
                    leoChars(encabezados);
                    break;
                case 3:
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("La opcion indicada no existe, indica otra.");
            }
        }while(salir == false);
    }
    
    public static String pedirRuta(String origenEntrada){
        Scanner lector = new Scanner(System.in);
        System.out.println("Dime la ruta del fichero de " + origenEntrada);
        String ruta = lector.nextLine();
        return ruta;
    }
    
    /*con este método leo byte a byte pero no puedo especificar el encoding a utf-8 
    y en la consola me lo imprime mal, sin embargo, al haber guardado el archivo de texto
    con el encoding correctamente, se transcribe bien*/
    public static void leoBytes(String [] encabezados){
        String origen = pedirRuta("origen");
        String destino = pedirRuta("destino");
        //con el siguiente metodo podré añadir un salto de linea
        int letra;
        int aux = 0;//contador de #
        int pos = 3;//para imprimir los encabezados,empezamos en la posicion 3 de la array
        //con las siguientes clases indico a java que leo y escribo en utf-8
        try (FileInputStream reader = new FileInputStream(origen);
               FileOutputStream writer = new FileOutputStream(destino, true)){
            escribirCabecera(encabezados,destino);
            /*el primer valor es una peli, por lo que imprimo las lineas que van delante del titulo*/
            writer.write(encabezados[pos].getBytes());
            do{
                letra = reader.read();
                System.out.print((char)letra);
                if ((char) letra == '#'){
                    /*si aux vale 0 es que nos encontramos en el titulo y
                    tengo que imprimir las lineas del final del titulo*/
                    if (aux == 0){
                        writer.write(encabezados[pos].getBytes()); //escribo las lineas del final del titulo
                        pos++;
                        writer.write(("\n".getBytes()));//pongo un salto de linea
                        writer.write(encabezados[pos].getBytes()); //escribo el siguiente encabezado
                        pos++;
                    }        
                    else{
                        writer.write(("\n".getBytes()));
                        writer.write(encabezados[pos].getBytes());
                        pos++;
                    }
                    aux++;
                }
                else if((char) letra == '{'){ //es una nueva peli, reseteo auxiliares
                    aux = 0;
                    pos = 3;
                    writer.write(("\n".getBytes()));
                    writer.write(encabezados[pos].getBytes());
                }
                else if (letra != -1){
                    writer.write(letra);
                }
            }while(letra != -1);          
        } catch (UnsupportedEncodingException ex) {
            ex = new UnsupportedEncodingException("El encoding indicado no es correcto.");
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            try {
                throw new ErrorderutaException(101);
            } catch (ErrorderutaException ex1) {                    
                System.out.println(ex1.getMensaje());
                registrarErrores(ex1.getMensaje(),ex1.getStackTrace());
            }
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
    }
    
    //con este método mantengo el formato de encode UTF-8 en consola y en fichero destino
    public static void leoBytesVersion2(String [] encabezados){
        String origen = pedirRuta("origen");
        String destino = pedirRuta("destino");
        //con el siguiente metodo podré añadir un salto de linea
        String nuevaLinea = System.getProperty("line.separator");
        int letra;
        int aux = 0;//contador de #
        int pos = 3;//para imprimir los encabezados,empezamos en la posicion 3 de la array
        //con las siguientes clases indico a java que leo y escribo en utf-8
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(origen), "UTF-8");
               OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(destino, true), StandardCharsets.UTF_8)){
            escribirCabecera(encabezados,destino);
            /*el primer valor es una peli, por lo que imprimo las lineas que van delante del titulo*/
            writer.write(encabezados[pos]);
            do{
                letra = reader.read();
                System.out.print((char)letra);
                if ((char) letra == '#'){
                    /*si aux vale 0 es que nos encontramos en el titulo y
                    tengo que imprimir las lineas del final del titulo*/
                    if (aux == 0){
                        writer.write(encabezados[pos]); //escribo las lineas del final del titulo
                        pos++;
                        writer.append(nuevaLinea);//pongo un salto de linea
                        writer.write(encabezados[pos]); //escribo el siguiente encabezado
                        pos++;
                    }        
                    else{
                        writer.append(nuevaLinea);
                        writer.write(encabezados[pos]);
                        pos++;
                    }
                    aux++;
                }
                else if((char) letra == '{'){ //es una nueva peli, reseteo auxiliares
                    aux = 0;
                    pos = 3;
                    writer.append(nuevaLinea);
                    writer.write(encabezados[pos]);
                }
                else if (letra != -1){
                    writer.write(letra);
                }
            }while(letra != -1);          
        } catch (UnsupportedEncodingException ex) {
            ex = new UnsupportedEncodingException("El encoding indicado no es correcto.");
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            try {
                throw new ErrorderutaException(101);
            } catch (ErrorderutaException ex1) {                    
                System.out.println(ex1.getMensaje());
                registrarErrores(ex1.getMensaje(),ex1.getStackTrace());
            }
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
    }
    
    public static void leoChars(String [] encabezados){
        String origen = pedirRuta("origen");
        String destino = pedirRuta("destino");
        //con el siguiente metodo podré añadir un salto de linea
        String nuevaLinea = System.getProperty("line.separator");
        int letra;
        int aux = 0;//contador de #
        int pos = 3;//para imprimir los encabezados,empezamos en la posicion 3 de la array
        //con las siguientes clases indico a java que leo y escribo en utf-8
        File entrada = new File(origen);
        try (FileReader reader = new FileReader(entrada);FileWriter writer = new FileWriter(destino)){
            escribirCabecera(encabezados,destino);
            do{
                letra = reader.read();
                System.out.print((char)letra);
                if ((char) letra == '#'){
                    /*si aux vale 0 es que nos encontramos en el titulo y
                    tengo que imprimir las lineas del final del titulo*/
                    if (aux == 0){
                        writer.write(encabezados[pos]); //escribo las lineas del final del titulo
                        pos++;
                        writer.append(nuevaLinea);//pongo un salto de linea
                        writer.write(encabezados[pos]); //escribo el siguiente encabezado
                        pos++;
                    }        
                    else{
                        writer.append(nuevaLinea);
                        writer.write(encabezados[pos]);
                        pos++;
                    }
                    aux++;
                }
                else if((char) letra == '{'){ //es una nueva peli, reseteo auxiliares
                    aux = 0;
                    pos = 3;
                    writer.append(nuevaLinea);
                    writer.write(encabezados[pos]);
                }
                else if (letra != -1){
                    writer.write(letra);
                }
            }while(letra != -1);          
        } catch (UnsupportedEncodingException ex) {
            ex = new UnsupportedEncodingException("El encoding indicado no es correcto.");
            System.out.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            try {
                throw new ErrorderutaException(101);
            } catch (ErrorderutaException ex1) {                    
                System.out.println(ex1.getMensaje());
                registrarErrores(ex1.getMensaje(),ex1.getStackTrace());
            }
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado.");
        }
    }
    
    public static void escribirCabecera(String [] encabezados, String destino) throws IOException{
        String nuevaLinea = System.getProperty("line.separator");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(destino, true), StandardCharsets.UTF_8)){
            for (int i = 0; i<3; i++){
                writer.write(encabezados[i]);//escribo hasta las primeras lineas del titulo
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }    
    
    public static void registrarErrores(String errorMessage, StackTraceElement [] pila){
        String nuevaLinea = System.getProperty("line.separator");
        try(OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("errores.txt", true), StandardCharsets.UTF_8)){
            registrarFecha(writer);
            writer.write(errorMessage);
            writer.append(nuevaLinea);//salto de linea
            for (int i = 0; i<pila.length;i++){
                writer.write(pila[i].toString());//el metodo toString envia las pila de ejecución
                writer.append(nuevaLinea);
            }
        } catch (IOException ex) {
            System.out.println("se ha producido un error inesperado");
        }
    }
    
    public static void registrarFecha(OutputStreamWriter writer) throws IOException{
        //fuente: http://lineadecodigo.com/java/obtener-la-hora-en-java/
        Calendar calendario = Calendar.getInstance();//creo instancia del calendario gregoriano
        int [] fecha = {calendario.get(Calendar.DATE),calendario.get(Calendar.MONTH),calendario.get(Calendar.YEAR),
        calendario.get(Calendar.HOUR_OF_DAY),calendario.get(Calendar.MINUTE),calendario.get(Calendar.SECOND)};
        char separador1 = '/';
        char separador2 = ' ';
        char separador3 = ':';
        for (int i=0; i<fecha.length;i++){
            writer.write(Integer.toString(fecha[i]));
            if(i<2){
                writer.write(separador1);
            }
            else if(i == 2){
                writer.write(separador2);
            }
            else if (i < fecha.length-1){
                writer.write(separador3);
            }
            else{
                writer.write(separador2);
            }
        }
    }
}
