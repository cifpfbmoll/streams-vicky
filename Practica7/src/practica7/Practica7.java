/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

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
    
    public static void leoBytes(String [] encabezados){
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
            
        } catch (IOException ex) {
            //TO DO
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
}
