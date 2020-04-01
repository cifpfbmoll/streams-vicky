/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static practica7.ErrorderutaException.registrarErrores;
import static practica7.Practica7.pedirRuta;

/**
 *
 * @author victoriapenas
 */
public class Pelicula implements Serializable{
    private String titulo;
    private int year;
    private String director;
    private int duracion;
    private String sinopsis;
    private String reparto;
    private String sesion;

    public Pelicula() {
    }

    public Pelicula(String titulo, int year, String director, int duracion, String sinopsis, String reparto, String sesion) {
        this.setTitulo(titulo);
        this.setYear(year);
        this.setDirector(director);
        this.setDuracion(duracion);
        this.setSinopsis(sinopsis);
        this.setReparto(reparto);
        this.setSesion(sesion);
    }
    
    public Pelicula(Pelicula peli) {
        this.setTitulo(peli.getTitulo());
        this.setYear(peli.getYear());
        this.setDirector(peli.getDirector());
        this.setDuracion(peli.getDuracion());
        this.setSinopsis(peli.getSesion());
        this.setReparto(peli.getReparto());
        this.setSesion(peli.getSesion());
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }
    
    public Pelicula pedirDatos(){
        Scanner lector = new Scanner (System.in);
        System.out.print("Dime el titulo: ");
        this.setTitulo(lector.nextLine());
        System.out.print("Dime el año: ");
        this.setYear(Integer.parseInt(lector.nextLine()));
        System.out.print("Dime el director: ");
        this.setDirector(lector.nextLine());
        System.out.print("Dime la duracion: ");
        this.setDuracion(Integer.parseInt(lector.nextLine()));
        System.out.print("Escribe la sinopsis: ");
        this.setSinopsis(lector.nextLine());
        System.out.print("Dime el reparto: ");
        this.setReparto(lector.nextLine());
        System.out.print("Dime la sesión: ");
        this.setSesion(lector.nextLine());
        return this;
    }
    
    public static void menuObjPeli(){
        Scanner lector = new Scanner(System.in);
        int opcion;
        boolean salir = false;
        do{
            System.out.println("---------------------- M E N U ----------------------");
            System.out.println("1. Lectura línea a línea y escritura con objetos.");
            System.out.println("2. Lectura de objetos y escritura de objetos.");
            System.out.println("3. Lectura de objetos y escritura por consola.");
            System.out.println("4. Lectura por consola y escritura de objetos. (añadirá objetos al final del fichero existente)");
            System.out.println("5. Volver al menú principal.");
            System.out.println("Dime una opcion: ");
            opcion = lector.nextInt();
            switch(opcion){
                case 1:
                    crearFicheroObjPelicula();
                    break;
                case 2:
                    leerEscribirObjPelicula();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("La opcion indicada no existe, indica otra.");
            }
        }while(salir == false);
    }
    
    public static void crearFicheroObjPelicula(){
        String origen = pedirRuta("origen"); //pido archivo de texto para leer
        File entrada = new File(origen); //ruta del fichero de lectura
        BufferedReader lector = null; //lector del fichero de origen
        String linea = ""; //auxiliar de lectura
        String [] peliculas = null; //cada string será un objeto
        String [] textosPeli = null; //cada string almacena un atributo del objeto
        File docDestino = new File("peliculasObj"); //en este doc escribiremos el objeto
        FileOutputStream docObj = null; //esta clase la necesito para escribir el objeto
        ObjectOutputStream writer = null; //este es mi escritor de objetos
        try {
            docObj = new FileOutputStream(docDestino);
            writer = new ObjectOutputStream(docObj);
            lector = new BufferedReader(new FileReader(entrada)); //envio al buffer el fichero de lectura
            do{
                linea = lector.readLine();
                if (linea != null){
                    peliculas = linea.split("\\{"); // el simbolo { es un caracter reservado y con \\ lo escapamos
                    for (int i = 0;i<peliculas.length;i++){
                        textosPeli = peliculas[i].split("#"); //cada # es un atributo
                        //creo una peli con parámetros
                        writer.writeObject(new Pelicula(textosPeli[0],Integer.parseInt(textosPeli[1]),textosPeli[2],
                        Integer.parseInt(textosPeli[3]),textosPeli[4],textosPeli[5],textosPeli[6]));
                    }
                }
            }while(linea == null);
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
        } finally {
            try {
                docObj.close();
                writer.close();
                lector.close();
            } catch (IOException ex) {
                System.out.println("Ha ocurrido un error inesperado. Más detalles:");
                System.out.println(ex.getCause());
            }
        }
    }
    
    public static void leerEscribirObjPelicula(){
        File origen = new File ("peliculasObj");
        File destino = new File ("newDocObjPelis");
        FileInputStream ficheroOrigen = null;
        ObjectInputStream lectorObj = null;
        FileOutputStream ficheroDestino = null;
        ObjectOutputStream writerObj = null;
        try {
            ficheroOrigen = new FileInputStream(origen);//ruta origen
            lectorObj = new ObjectInputStream(ficheroOrigen); //lector de objetos
            ficheroDestino = new FileOutputStream(destino,true); //ruta destino
            writerObj = new ObjectOutputStream(ficheroDestino); //escritor de objetos
            while (true){ //mientras haya objetos sigue leyendo. Cuando ya no hay objetos el lector lanza una excepción
                Pelicula peli = (Pelicula) lectorObj.readObject(); // creo una peli, leo una instancia de la clase Pelicula
                writerObj.writeObject(peli); //escribo la Pelicula que he leido
            }
        } catch (FileNotFoundException ex) {
            //TO DO
        } catch (ClassNotFoundException ex) {
            //TO DO
        } catch (EOFException ex) {
            /*Esta excepcion hay que ponerla manualmente siempre! el IDE no la pide, hay que ponerla a mano*/
            System.out.println("Fin de fichero");
        } catch (IOException ex) {
            //TO DO
        }  finally {
            try {
                ficheroOrigen.close();
                lectorObj.close();
            } catch (IOException ex) {
                System.out.println("Ha ocurrido un error inesperado. Más detalles:");
                System.out.println(ex.getCause());
            }
        }
    }
}
