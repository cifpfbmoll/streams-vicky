/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.io.BufferedReader;
import java.lang.ClassNotFoundException;
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
import java.io.StreamCorruptedException;
import java.util.Scanner;
import static practica7.ErrorderutaException.registrarErrores;
import static practica7.Practica7.pedirRuta;

/**
 * <h1>Clase Pelicula</h1>
 * <p>La clase Pelicula está formada por los siguientes atributos privados:</p>
 * <ul>
 * <li>titulo</li>
 * <li>año</li>
 * <li>director</li>
 * <li>duracion</li>
 * <li>sinopsis</li>
 * <li>reparto</li>
 * <li>sesion</li>
 * </ul>
 * 
 * @author victoriapenas
 * @version 1.0
 * @since 2020-03-21
 */
public class Pelicula implements Serializable{
    private String titulo;
    private int year;
    private String director;
    private int duracion;
    private String sinopsis;
    private String reparto;
    private String sesion;

    /**
     * <h2>Contructor vacío</h2>
     */
    public Pelicula() {
    }

    /**
     * <h2>Contructor con parámetros</h2>
     * @param titulo titulo de la pelicula
     * @param year año de producción de la pelicula
     * @param director director de la pelicula
     * @param duracion duración de la película en minutos
     * @param sinopsis guion de la pelicula
     * @param reparto actores principales de la pelicula
     * @param sesion horario de transmision de la pelicula
     */
    public Pelicula(String titulo, int year, String director, int duracion, String sinopsis, String reparto, String sesion) {
        this.setTitulo(titulo);
        this.setYear(year);
        this.setDirector(director);
        this.setDuracion(duracion);
        this.setSinopsis(sinopsis);
        this.setReparto(reparto);
        this.setSesion(sesion);
    }
    /**
     * <h2>Constructor copia</h2>
     * @param peli Recibe un objeto de tipo Pelicula
     */
    public Pelicula(Pelicula peli) {
        this.setTitulo(peli.getTitulo());
        this.setYear(peli.getYear());
        this.setDirector(peli.getDirector());
        this.setDuracion(peli.getDuracion());
        this.setSinopsis(peli.getSesion());
        this.setReparto(peli.getReparto());
        this.setSesion(peli.getSesion());
    }

    /**
     * getter del atributo titulo
     * @return devuelve un string con el titulo de la pelicula
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * setter del atributo Pelicula
     * @param titulo recibe un string con el titulo de la pelicula
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * getter del atributo year, el año de la pelicula
     * @return devuelve un entero con el año de la pelicula
     */
    public int getYear() {
        return year;
    }

    /**
     * setter del atributo year
     * @param year recibe un entero con el año de producción de la pelicula
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * getter del atributo director
     * @return devuelve un string con el director de la pelicula
     */
    public String getDirector() {
        return director;
    }

    /**
     * setter del atributo director
     * @param director recibe una string con el nombre del director de la pelicula
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * getter del atributo duracion
     * @return devuelve un entero con la duracion de la pelicula en minutos
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * setter del atributo duracion
     * @param duracion recibe un entero con la duración de la pelicula en minutos
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * getter del atributo sinopsis
     * @return devuelve una string con la sinopsis de la pelicula
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * setter del atributo sinopsis
     * @param sinopsis recibe un string con la sinopsis de la pelicula
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * getter del atributo reparto
     * @return devuelve un string con el reparto de la pelicula
     */
    public String getReparto() {
        return reparto;
    }

    /**
     * setter del atributo reparto
     * @param reparto recibe un string con el listado de actores principales de la pelicula
     */
    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    /**
     * getter del atributo sesion
     * @return devuelve una string con la sesion en la que se reproducirá la pelicula
     * en formato hh:mm
     */
    public String getSesion() {
        return sesion;
    }

    /**
     * setter del atributo sesion
     * @param sesion recibe la sesion  en la que se reproducirá la pelicula
     * formato hh:mm
     */
    public void setSesion(String sesion) {
        this.sesion = sesion;
    }
    
    /**
     * <h2>Menu principal con diferentes opciones</h2>
     * <p>Desde este menu se dan diferentes opciones para la lectura de ficheros,
     * creación de objetos de tipo Pelicula y escritura de objetos de tipo Pelicula.</p>
     * 
     * @see #crearFicheroObjPelicula() 
     * @see #leerEscribirObjPelicula() 
     * @see #imprimirObj() 
     * @see #crearObjporTeclado() 
     * 
     */
    public static void menuObjPeli(){
        Scanner lector = new Scanner(System.in);
        int opcion;
        boolean salir = false;
        do{
            System.out.println("---------------------- M E N U ----------------------");
            System.out.println("1. Lectura línea a línea y escritura con objetos.");
            System.out.println("2. Lectura de objetos y escritura de objetos.");
            System.out.println("3. Lectura de objetos y escritura por consola.");
            System.out.println("4. Lectura por consola y escritura de objetos.");
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
                    imprimirObj();
                    break;
                case 4:
                    crearObjporTeclado();
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
     * <h2>Método para crear un fichero de objetos de tipo Pelicula</h2>
     * <p>Se creará un documento en el proyecto con la información que se llamará:
     * peliculasObj</p>
     * 
     * Algunos de los métodos que se utilizan son:
     * 
     * @see practica7.Practica7#pedirRuta(java.lang.String)
     * @see practica7.ErrorderutaException#registrarErrores
     * 
     * Se utilizan las siguientes clases:
     * @see java.io.File
     * @see java.io.FileOutputStream
     * @see java.io.FileReader
     * @see java.io.BufferedReader
     * @see java.io.ObjectOutputStream
     * 
     * Se tratan las siguientes excepciones:
     * 
     * @see java.io.FileNotFoundException
     * @see java.io.IOException
     * @see practica7.ErrorderutaException
     * Todos los errores quedan registrados en un fichero de logs en el mismo proyecto.
     */
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
        System.out.println("Se ha creado el fichero: peliculasObj con los objetos");
    }
    
     /**
     * <h2>Método para leer un fichero de objetos de tipo Pelicula y escribirlos en otro fichero</h2>
     * <p>El fichero de lectura se llamará: peliculasObj y el fichero de escritura: newDocObjPelis.
     * Ambos ficheros estarán ubicados en la carpeta del proyecto</p>
     * 
     * Algunos de los métodos que se utilizan son:
     * @see practica7.ErrorderutaException#registrarErrores
     * 
     * Se utilizan las siguientes clases:
     * @see java.io.File
     * @see java.io.FileInputStream
     * @see java.io.FileOutputStream
     * @see java.io.ObjectOutputStream
     * @see java.io.ObjectInputStream
     * 
     * Se tratan las siguientes excepciones:
     * @see java.io.FileNotFoundException
     * @see java.lang.ClassNotFoundException
     * @see EOFException
     * @see practica7.ErrorderutaException
     * @see java.io.IOException
     * Todos los errores quedan registrados en un fichero de logs en el mismo proyecto.
     * 
     */
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
                writerObj.writeObject(peli); //escribo la Pelicula que he leido en el fichero destino
            }
        } catch (FileNotFoundException ex) {
            try {
                throw new ErrorderutaException(101);
            } catch (ErrorderutaException ex1) {                    
                System.out.println(ex1.getMensaje());
                registrarErrores(ex1.getMensaje(),ex1.getStackTrace());
            }
        } catch (ClassNotFoundException ex) {
            ex = new ClassNotFoundException("La clase que se está intentado leer no existe");
            System.out.println(ex.getMessage());
        } catch (EOFException ex) {
            /*Esta excepcion hay que ponerla manualmente siempre! el IDE no la pide, hay que ponerla a mano.
            Indica el fin de lectura del fichero*/
            System.out.println("Se ha creado el fichero: newDocObjPelis con los"
                + " objetos leidos del fichero peliculasObj");
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más detalles:");
            System.out.println(ex.getCause());
        }  finally {
            try {
                ficheroOrigen.close();
                lectorObj.close();
                ficheroDestino.close();
                writerObj.close();
            } catch (IOException ex) {
                System.out.println("Ha ocurrido un error inesperado. Más detalles:");
                System.out.println(ex.getCause());
            }
        }
    }
    
     /**
     * <h2>Método para imprimir por consola un objeto tipo Pelicula</h2>
     * <p>Este método lee los objetos de tipo Peliculas almacenados en el documento: newDocObjPelis,
     * que está ubicado en la carpeta del proyecto y los imprime por consola con un formato especifico</p>
     * 
     * @see practica7.ErrorderutaException#registrarErrores
     * 
     * Se utilizan las siguientes clases:
     * @see java.io.File
     * @see java.io.FileInputStream
     * @see java.io.ObjectInputStream
     * 
     * Se tratan las siguientes excepciones:
     * 
     * @see java.io.FileNotFoundException
     * @see java.lang.ClassNotFoundException
     * @see EOFException
     * @see practica7.ErrorderutaException
     * @see java.io.StreamCorruptedException
     * @see java.io.IOException
     * Todos los errores quedan registrados en un fichero de logs en el mismo proyecto.
     * 
     */
    public static void imprimirObj(){
        File origen = new File ("newDocObjPelis");
        FileInputStream ficheroOrigen = null;
        ObjectInputStream lectorObj = null;
        try {
            ficheroOrigen = new FileInputStream(origen);//ruta origen
            lectorObj = new ObjectInputStream(ficheroOrigen); //lector de objetos
            System.out.println("--------------------------------------\n" +
                    "Cartelera de CineFBMoll\n" + "--------------------------------------");
            while (true){ //mientras haya objetos sigue leyendo. Cuando ya no hay objetos el lector lanza una excepción
                Pelicula peli = (Pelicula) lectorObj.readObject(); // creo una peli, leo una instancia de la clase Pelicula
                System.out.println("titulo: " + peli.getTitulo());
                System.out.println("año: " + peli.getYear());
                System.out.println("director: " + peli.getDirector());
                System.out.println("duracion: " + peli.getDuracion());
                System.out.println("sinopsis: " + peli.getSinopsis());
                System.out.println("reparto: " + peli.getReparto());
                System.out.println("sesion: " + peli.getSesion());
                System.out.println("*******************************************");
            }
        } catch (FileNotFoundException ex) {
            try {
                throw new ErrorderutaException(101);
            } catch (ErrorderutaException ex1) {                    
                System.out.println(ex1.getMensaje());
                registrarErrores(ex1.getMensaje(),ex1.getStackTrace());
            }
        }catch (EOFException ex) {//excepcion que debemos controlar al leer objetos
            /*Esta excepcion hay que ponerla manualmente siempre! el IDE no la pide, hay que ponerla a mano*/
            System.out.println("Fin de fichero");
        } catch (ClassNotFoundException ex) {
            ex = new ClassNotFoundException("La clase que se está intentado leer no existe");
            System.out.println(ex.getMessage());
        } catch (StreamCorruptedException e){//fuente: http://www.chuidiang.org/java/ficheros/ObjetosFichero.php
            System.out.println("Esta excepcion ocurre cuando más de una instancia de la clase ObjectInputStream"
                    + "abre el mismo fichero.");
        }
        catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más detalles:");
            System.out.println(ex.getCause());
        } finally {
            try {
                ficheroOrigen.close();
                lectorObj.close();
            } catch (IOException ex) {
                System.out.println("Ha ocurrido un error inesperado. Más detalles:");
                System.out.println(ex.getCause());
            }
        }
    }
    
     /**
     * <h2>Método que solicita los datos de un objeto de tipo Peliculo</h2>
     * <p>Este método pide por teclado los datos de un objeto de tipo Pelicula.</p>
     * 
     * @return devuelvo un objeto de tipo Pelicula
     */
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
        System.out.println("Pelicula creada.");
        return this;
    }
    
     /**
     * <h2>Método para crear objeto tipo Pelicula y añadirlo en un fichero</h2>
     * <p>Este método crea un objeto de tipo Pelicula y lo añade al fichero newDocObjPelis,
     * que está ubicado en la carpeta del proyecto.</p>
     * 
     * Algunos de los métodos que se utilizan:
     * @see #pedirDatos()
     * @see practica7.ErrorderutaException#registrarErrores
     * 
     * Se utilizan las siguientes clases:
     * @see java.io.File
     * @see java.io.FileInputStream
     * @see java.io.FileOutputStream
     * @see java.io.ObjectInputStream
     * @see java.io.ObjectOutputStream
     * 
     * Se tratan las siguientes excepciones:
     * @see java.io.FileNotFoundException
     * @see practica7.ErrorderutaException
     * @see java.io.IOException
     * Todos los errores quedan registrados en un fichero de logs en el mismo proyecto.
     * 
     */
    public static void crearObjporTeclado(){
        Pelicula peli = new Pelicula();
        File destino = new File ("newDocObjPelis");
        FileOutputStream ficheroDestino = null;
        ObjectOutputStream writerObj = null;
        peli = peli.pedirDatos();
        try {
            ficheroDestino = new FileOutputStream(destino);//da problemas la adición ,true
            writerObj = new ObjectOutputStream(ficheroDestino); //escritor de objetos
            writerObj.writeObject(peli); //escribo la Pelicula que he leido en el fichero destino
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
                ficheroDestino.close();
                writerObj.close();
            } catch (IOException ex) {
                System.out.println("Ha ocurrido un error inesperado. Más detalles:");
                System.out.println(ex.getCause());
            }
        }
        System.out.println("Pelicula insertada en el fichero newDocObjPelis");
    }
}
