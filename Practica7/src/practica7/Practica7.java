/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Scanner;
import static practica7.ErrorderutaException.registrarErrores;
import static practica7.Pelicula.menuObjPeli;

/**
 * <h1>Paquete Practica7</h1>
 * <p><b>Practica7</b> implementa un programa para la lectura y escritura de ficheros en
 * tres formatos: byte a byte, caracter a caracter y linea a linea mediante buffers.</p>
 * 
 * @author victoriapenas
 * @version 1.0
 * @since 2020-03-21
 */
public class Practica7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /**
         *  Array de Strings con los titulos del encabezado.
         */
        final String [] encabezados = {"-----------------------","Cartelera de CineFBMoll",
            "-----------------------","-----", "Año:", "Director: ",
            "Duracion: ", "Sinopsis: ", "Reparto: ", "Sesión: ", "horas"};
        
        mostrarMenu(encabezados);
    }
    
    /**
     * <h2>Método para imprimir el menu por consola.</h2>
     * <p>El método <b>mostrarMenu</b> imprime por consola un menu con las
     * diferentes opciones que se pueden realizar en este programa.<p>
     * 
     * @param encabezados Array de Strings con los titulos del encabezado.
     */
    public static void mostrarMenu(String [] encabezados){
        Scanner lector = new Scanner(System.in);
        int opcion;
        boolean salir = false;
        do{
            System.out.println("---------------------- M E N U ----------------------");
            System.out.println("1. Lectura y escritura del fichero de cartelera byte a byte (byte Streams).");
            System.out.println("2. Lectura y escritura de fichero de cartelera carácter a carácter (character Streams).");
            System.out.println("3. Lectura y escritura de fichero línea a línea con buffers (character Streams).");
            System.out.println("4. Tratamiento de objetos.");
            System.out.println("5. Salir");
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
                    leoLineas(encabezados);
                    break;
                case 4:
                    menuObjPeli();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("La opcion indicada no existe, indica otra.");
            }
        }while(salir == false);
    }
    
    /**
     * <h2>Método para obtener la ruta del archivo de escritura o de lectura.</h2>
     * <p>El método <b>pedirRuta</b> pide por teclaro dónde se encuentra ubicado
     * el archivo desde dónde se tiene que hacer una lectura de un fichero o en
     * qué archivo se debe realizar la escritura.</p>
     * 
     * @param origenEntrada Condicion para identificar si la ruta que debe calcular
     * es de origen, es decir, para lectura o de salida, es decir, para escritura
     * @return devuelve la ruta dónde está ubicado el fichero.
     * 
     * ErrorderutaException Excepcion que ocurre en el caso de que no
     * se indique la ruta con la ubicación del archivo. Todos los errores quedan
     * registrados en un fichero de logs.
     * 
     * @see practica7.ErrorderutaException#registrarErrores(java.lang.String, java.lang.StackTraceElement[]) 
     * 
     */
    public static String pedirRuta(String origenEntrada){
        Scanner lector = new Scanner(System.in);
        System.out.println("Dime la ruta del fichero de " + origenEntrada);
        String ruta = null;
        while ((ruta == null) || (ruta.trim().isEmpty())) {
            ruta = lector.nextLine();
            if ((ruta == null) || (ruta.trim().isEmpty())){
                try {
                    throw new ErrorderutaException(102);
                } catch (ErrorderutaException ex) {
                    System.out.println(ex.getMensaje());
                    registrarErrores(ex.getMensaje(),ex.getStackTrace());
                }
            }            
        }
        return ruta;
    }
    
    /**
     * <h2>Método para leer byte a byte y escribir los bytes leido a un fichero
     * mediante el uso de objetos de tipo FileInputStream y FileOutputStream.</h2>
     * <p>El método <b>leoBytes</b> realiza un lectura byte a byte y la escritura
     * de los bytes leidos a los ficheros indicados por el usuario mediante el
     * método pedirRuta. Además, escribe los títulos del encabezado en el fichero
     * de salida mediante el método escribirCabecera.</p>
     * <p>En este método no se puede especificar el encoding, por ejemplo utf-8,
     * por lo tanto, al imprimir los bytes leidos por consola no tiene en cuenta
     * los caracteres con acentos y no los imprime correctamente. Sin embargo,
     * si el archivo de origen está guardado con el encoding especificando, se
     * transcribe respetándo el formato de origen.</p>
     * 
     * @see #pedirRuta(java.lang.String) 
     * @see #escribirCabecera(java.lang.String[], java.lang.String)
     * @see java.io.FileInputStream
     * @see java.io.FileOutputStream
     * 
     * @param encabezados Array de Strings con los titulos del encabezado.
     * FileNotFoundException Excepcion por no encontrar la ruta indicada
     * IOException Excepcion derivada del uso de los objetos BufferedReader
     * 
     * @see practica7.ErrorderutaException#registrarErrores(java.lang.String, java.lang.StackTraceElement[]) 
     */
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
                    writer.write(encabezados[pos].getBytes());//después del ultimo caracter escribo la ultima string
                    aux = 0;
                    pos = 3;
                    writer.write(("\n".getBytes()));
                    writer.write(encabezados[pos].getBytes());
                }
                else if (letra != -1){
                    writer.write(letra);
                }
                else if(letra == -1){
                    writer.write(encabezados[pos].getBytes());//después del ultimo caracter escribo la ultima string
                }
            }while(letra != -1);          
        } catch (FileNotFoundException ex) {
            try {
                throw new ErrorderutaException(101);
            } catch (ErrorderutaException ex1) {                    
                System.out.println(ex1.getMensaje());
                registrarErrores(ex1.getMensaje(),ex1.getStackTrace());
            }
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más detalles:");
            System.out.println(ex.getCause());
        }
    }
    
     /**
     * @deprecated No cumple con los objetivos de la practica. Usar el método leoBytes
     * @see #leoBytes(java.lang.String[])
     * 
     * <h2>Método para leer caracter a caracter y escribir mediante el uso de objetos
     * OutputStreamWriter, InputStreamReader, FileInputStream, FileOutputStream.</h2>
     * <p>El método <b>leoBytesVersion2</b> realiza un lectura y escritura mediante el
     * encode UTF-8, de los ficheros indicados por el usuario mediante el método pedirRuta.
     * De esta forma tanto al imprimir por consola como al escribir en el fichero de 
     * destino se respesta el encode y los caracteres con acento se visualizan correctamente.
     * Además, escribe los títulos del encabezado en el fichero de salida mediante
     * el método escribirCabecera.</p>
     * 
     * @see #pedirRuta(java.lang.String)
     * @see #escribirCabecera(java.lang.String[], java.lang.String) 
     * @see java.io.OutputStreamWriter
     * @see java.io.InputStreamReader
     * @see java.io.FileInputStream
     * @see java.io.FileOutputStream
     * 
     * @param encabezados Array de Strings con los titulos del encabezado
     * 
     * FileNotFoundException Excepcion por no encontrar la ruta indicada
     * ErrorderutaException Excepcion por no encontrar la ruta indicada
     * UnsupportedEncodingException Excepcion por utilizar un encode incorrecto
     * IOException Excepcion derivada del uso de los objetos BufferedReader
     * 
     * @see practica7.ErrorderutaException#registrarErrores(java.lang.String, java.lang.StackTraceElement[])
     */
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
                    writer.write(encabezados[pos]);//después del ultimo caracter escribo la ultima string
                    aux = 0;
                    pos = 3;
                    writer.append(nuevaLinea);
                    writer.write(encabezados[pos]);
                }
                else if (letra != -1){
                    writer.write(letra);
                }
                else if(letra == -1){
                    writer.write(encabezados[pos]);//después del ultimo caracter escribo la ultima string
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
            System.out.println("Ha ocurrido un error inesperado. Más detalles:");
            System.out.println(ex.getCause());
        }
    }
    
    /**
     * 
     * <h2>Método para leer caracter a caracter y escribir..</h2>
     * <p>El método <b>leoChars</b> realiza un lectura y escritura desde y hacia
     * los ficheros indicados por el usuario mediante el método pedirRuta. En el fichero
     * de destino, se escriben los títulos del encabezado mediante el método escribirCabecera.
     * mediante el uso de objetos file.</p>
     * 
     * @see #pedirRuta(java.lang.String) 
     * @see #escribirCabecera(java.lang.String[], java.lang.String)
     * @see java.io.File
     * @see java.io.FileReader
     * @see java.io.FileWriter
     * 
     * @param encabezados Array de Strings con los titulos del encabezado.
     * FileNotFoundException Excepcion por no encontrar la ruta indicada
     * ErrorderutaException Excepcion por no encontrar la ruta indicada
     * UnsupportedEncodingException Excepcion por utilizar un encode incorrecto
     * IOException Excepcion derivada del uso de los objetos BufferedReader
     * 
     * @see practica7.ErrorderutaException#registrarErrores(java.lang.String, java.lang.StackTraceElement[]) 
     */
    public static void leoChars(String [] encabezados){
        String origen = pedirRuta("origen");
        String destino = pedirRuta("destino");
        //con el siguiente metodo podré añadir un salto de linea
        String nuevaLinea = System.getProperty("line.separator");
        int letra;
        int aux = 0;//contador de #
        int pos = 3;//para imprimir los encabezados,empezamos en la posicion 3 de la array
        File entrada = new File(origen);
        try (FileReader reader = new FileReader(entrada);FileWriter writer = new FileWriter(destino,true)){
            escribirCabecera(encabezados,destino);
            writer.write(encabezados[pos]);//imprimo las lineas del principio de la peli
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
                    writer.write(encabezados[pos]);//después del ultimo caracter escribo la ultima string
                    aux = 0;
                    pos = 3;
                    writer.append(nuevaLinea);
                    writer.write(encabezados[pos]);
                }
                else if (letra != -1){
                    writer.write(letra);
                }
                else if (letra == -1){
                    writer.write(encabezados[pos]);//después del ultimo caracter escribo la ultima string
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
            System.out.println("Ha ocurrido un error inesperado. Más detalles:");
            System.out.println(ex.getCause());
        }
    }
    
     /**
     * 
     * <h2>Método para leer lineas de texto y transcribirlas a un documento.</h2>
     * <p>El método <b>leoLineas</b> realiza un lectura y escritura de textos de documentos
     * indicados por el usuario en el método pedirRuta() mediante el uso de objetos
     * buffers y file.
     * Además, en el fichero de destino, se escriben los títulos del encabezado
     * mediante el métodoescribirCabecera.</p>
     * 
     * @see #pedirRuta(java.lang.String) 
     * @see #escribirCabecera(java.lang.String[], java.lang.String)
     * @see java.io.BufferedReader
     * @see java.io.BufferedWriter
     * @see java.io.File
     * @see java.io.FileReader
     * @see java.io.FileWriter
     * 
     * @param encabezados Array de Strings con los titulos del encabezado.
     * 
     * FileNotFoundException Excepcion por no encontrar la ruta indicada
     * ErrorderutaException Excepcion por no encontrar la ruta indicada
     * IOException Excepcion derivada del uso de los objetos BufferedReader y
     * BufferedWritter. Todos los errores quedan registrados en un fichero de salida.
     * 
     * @see practica7.ErrorderutaException#registrarErrores(java.lang.String, java.lang.StackTraceElement[]) 
     * 
     */
    public static void leoLineas(String [] encabezados){
        String origen = pedirRuta("origen");
        String destino = pedirRuta("destino");
        String linea = "";
        String [] peliculas = null;
        String [] textosPeli = null;
        int pos = 3;//para imprimir los encabezados,empezamos en la posicion 3 de la array
        File entrada = new File(origen);
        File salida = new File(destino);
        try (BufferedReader lector = new BufferedReader(new FileReader(entrada));
                BufferedWriter writer = new BufferedWriter(new FileWriter(salida, true))){
            escribirCabecera(encabezados,destino);
            do{
                linea = lector.readLine();//leo hasta q encuentra un salto de linea
                if (linea != null){//tengo que poner este if, porque sino me salta la excepcion nullpointer exception
                    peliculas = linea.split("\\{"); // el simbolo { es un caracter reservado y con \\ lo escapamos
                    for (int i = 0;i<peliculas.length;i++){
                        pos = 3;//reseteo la variable auxiliar antes de cada peli
                        textosPeli = peliculas[i].split("#");
                        for (int j = 0;j<textosPeli.length;j++){
                            writer.write(encabezados[pos]);//escribo los encabezados
                            writer.write(textosPeli[j]); //escribo el texto
                            if(j==0){
                                writer.write(encabezados[pos]);//escribo las lineas detras del titulo
                            }
                            pos++;
                            if(j == textosPeli.length-1){
                                writer.write(encabezados[pos]);//después del ultimo caracter escribo la ultima string
                            }
                            writer.newLine();
                        }
                    }
                }
            }while(linea != null); //cuando llegamos al final del fichero, el buffer devuelve un null      
        } catch (FileNotFoundException ex) {
            try {
                throw new ErrorderutaException(101);
            } catch (ErrorderutaException ex1) {                    
                System.out.println(ex1.getMensaje());
                registrarErrores(ex1.getMensaje(),ex1.getStackTrace());
            }
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más detalles:");
            System.out.println(ex.getCause());
        }
    }
    
    /**
     * <h2>Método para transcribir Strings a un documento de salida.</h2>
     * 
     * @param encabezados Array de Strings con los titulos del encabezado.
     * @param destino Ruta del fichero de destino.
     * 
     * @throws IOException Se puede producir una excepción derivada del uso del objeto 
     * @see java.io.OutputStreamWriter
     */
    public static void escribirCabecera(String [] encabezados, String destino) throws IOException{
        String nuevaLinea = System.getProperty("line.separator");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(destino, true), StandardCharsets.UTF_8)){
            for (int i = 0; i<3; i++){
                writer.write(encabezados[i]);
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }
}
