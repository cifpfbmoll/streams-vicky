/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author victoriapenas
 */
public class Fichero {
    
    public static String pedirRuta(String origenEntrada){
        Scanner lector = new Scanner(System.in);
        System.out.println("Dime la ruta del fichero de " + origenEntrada);
        String ruta = lector.nextLine();
        return ruta;
    }
    
    public static void leerBytes(){
        String origen = pedirRuta("origen");
        String destino = pedirRuta("destino");//TO DO controlar por excepcion que me da una ruta
        int aux = 1;//contador de #
        int letra;
        int pelisTotales;
        String [][] datosPelis;
        int contadorFrases = 0;
        String frases = null;//Auxiliar para ir agregando frases a la array
        //instancio en el try para utilizar el autoclosable
        try (FileInputStream ficheroOrigen = new FileInputStream(origen);
                FileOutputStream ficheroDestino = new FileOutputStream(destino, true)
                ){ //true para que no me sobreescriba cada linea       
            pelisTotales = contarPelis(ficheroOrigen);
            datosPelis = new String [pelisTotales][7]; //calculos el numero de pelis que hay en el fichero y damos por hecho que siempre habrá 7 filas
            do{
                letra = ficheroOrigen.read();
                System.out.println((char)letra);
                if(letra != -1){
                    if((char)letra == '{'){ //porqué tiene que se comilla simple aqui? No me coge la doble comilla
                        //controlo si es el titulo para poner guiones al final
                        frases = frases + "-----";
                        datosPelis[pelisTotales][contadorFrases] = frases;
                        
                    }else if((char)letra == '#'){
                        if (aux == 1 || aux == 7){ //1 para añadir guiones a titulos (siempre estan en estas posiciones
                            frases = frases + "-----\n";
                            datosPelis[pelisTotales][contadorFrases] = frases;
                            contadorFrases++;//en el momento que me encuentro un # voy a la siguiente posicion de la array
                        }
                        else{
                            frases = frases + "\n";
                            datosPelis[pelisTotales][contadorFrases] = frases;
                        }
                        aux++;
                    }
                    else{
                        frases = frases + letra;
                        datosPelis[pelisTotales][contadorFrases] = frases;
                    }
                }
            }while(letra !=-1);
            
            /*
            for (int i = 0;i<datosPelis.length;i++){
                for (int j = 0;j<datosPelis[i].length;j++){
                    System.out.println(datosPelis[i][j]);
                }
            }*/

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mostrarCartelera(){
        System.out.println("-----------------------\n" + "Cartelera de CineFBMoll\n" +
        "-----------------------\n");
    }
    
    public static int contarPelis(FileInputStream ficheroOrigen) throws IOException{
        int contadorPelis = 0;
        int letra;
        do{
            letra = ficheroOrigen.read();
            if((char)letra != '{'){
                contadorPelis++;
            }
        }while(letra != -1);
        
        return contadorPelis;
    }
}
