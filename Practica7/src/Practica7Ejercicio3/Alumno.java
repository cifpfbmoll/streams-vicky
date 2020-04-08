/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;
import static Practica7Ejercicio3.Main.registrarFecha;
import static Practica7Ejercicio3.Main.rightpad;
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
import java.io.StreamCorruptedException;
import practica7.ErrorderutaException;
import static practica7.ErrorderutaException.registrarErrores;

/**
 *
 * @author victoriapenas
 */
public class Alumno implements Serializable {
    private String nombre;
    private String apellidos;
    private String [] notas;

    public Alumno() {
    }

    public Alumno(String nombre, String apellidos, String[] notas) {
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setNotas(notas);
    }
    
    public Alumno(Alumno alumno) {
        this.setNombre(alumno.getNombre());
        this.setApellidos(alumno.getApellidos());
        this.setNotas(alumno.getNotas());
    }
    
    //me creo este contructor especial para poder crear objetos a partir de una array con todos los datos
    public Alumno (String [] datosAlumno){
        String [] notasAlumno = new String [datosAlumno.length-3];//en las tres primeras posiciones tengo el nombre, el resto de datos son notas
        int pos = 3;//auxiliar para recorrer las notas. A partir de la posicion 3 tengo las notas
        this.setNombre(datosAlumno[0]);//en la posicion 0 tengo el nombre
        this.setApellidos(datosAlumno[1] + " " + datosAlumno[2]);//en las posiciones 1 y 2 tengo los apellidos
        for (int i = 0; i<notasAlumno.length;i++){
            notasAlumno[i] = datosAlumno[pos]; 
            pos++;
        }
        this.setNotas(notasAlumno);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String[] getNotas() {
        return notas;
    }

    public void setNotas(String[] notas) {
        this.notas = notas;
    }
    
    public static void crearFicheroObjAlumnos(){
        File entrada = new File("notas.txt"); //ruta del fichero de lectura
        BufferedReader lector = null; //lector del fichero de origen
        String linea = ""; //auxiliar de lectura
        String [] datosAlumno = null; //cada string almacena un atributo del objeto
        File docDestino = new File("alumnosObj"); //en este doc escribiremos el objeto
        FileOutputStream docObj = null; //esta clase la necesito para escribir el objeto
        ObjectOutputStream writer = null; //este es mi escritor de objetos
        Alumno alumno;
        try {
            docObj = new FileOutputStream(docDestino);
            writer = new ObjectOutputStream(docObj);
            lector = new BufferedReader(new FileReader(entrada)); //envio al buffer el fichero de lectura
            do{
                linea = lector.readLine();
                if (linea != null){
                    datosAlumno = linea.split(" ");
                    alumno = new Alumno(datosAlumno);
                    for (int i = 0;i<datosAlumno.length;i++){
                        writer.writeObject(alumno);
                    }
                }
            }while(linea == null);
        } catch (FileNotFoundException ex) {
            System.out.println("fichero de salida no encontrado");
        } catch (IOException ex) {
            System.out.println("Ha ocurrido un error inesperado. Más info:");
            System.out.println(ex.getCause());
        }
    }
    
    public static void imprimirObjAlumno(String [] boletinTitulos){
        File origen = new File ("alumnosObj");
        FileInputStream ficheroOrigen = null;
        ObjectInputStream lectorObj = null;
        try {
            ficheroOrigen = new FileInputStream(origen);//ruta origen
            lectorObj = new ObjectInputStream(ficheroOrigen); //lector de objetos
            while (true){ //mientras haya objetos sigue leyendo. Cuando ya no hay objetos el lector lanza una excepción
                Alumno alumno = (Alumno) lectorObj.readObject(); // instancio un alumno, y leo la instancia
                imprimirCabecera(alumno, boletinTitulos);
                imprimirNotas(alumno, boletinTitulos);
                imprimirResumen(alumno,boletinTitulos);
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
    
    public static void imprimirCabecera(Alumno alumno, String [] boletinTitulos){
        for (int i = 0; i<7; i++){//hasta la posicion 7 de la array están los datos de la cabecera
            System.out.print(boletinTitulos[i]);
            if (i==3){
                System.out.print(alumno.getNombre() + " " + alumno.getApellidos());
            }
            System.out.println("");//salto de linea. Lo pongo asi para que me encaje el nombre del alumno
        }
    }
    
    public static void imprimirNotas(Alumno alumno, String [] boletinTitulos){
        int aux = 0;//auxiliar para recorrer la array de notas
        for (int i = 7; i<=12; i++){//hasta la posicion 12 están los titulos del contenido
            /*con la funcion rightpad pongo los espacios de la derecha,
            siendo 35 la longitud total que tiene que acer la linea. esta funcion está en el main*/
            System.out.print(rightpad(boletinTitulos[i],35));
            System.out.println(alumno.getNotas()[aux]);
            aux++;
        }
    }
      
    public static void imprimirResumen(Alumno alumno, String [] boletinTitulos) throws IOException{
        int aux = 0;//auxiliar para recorrer la array de notas
        int [] notasTotales = calcularTotales(alumno.getNotas());//este método está en el main porque en la primera parte del ejercicio no necesitaba la clase alumno, ¿seria correcto? ver con rafa
        for (int i = 13; i<boletinTitulos.length; i++){
            System.out.print(boletinTitulos[i]);
            if (i > 13 && i < 17){//distinto a 13, porque en la posicion 13 solo quiero imprimir na linea divisoria
                System.out.print(notasTotales[aux]);
                aux++;
            }
            if(i == 18){//en la posicion 18 de la array registro la fecha
                System.out.print(registrarFecha());
            }
            System.out.println("");//con esto añado un salto de linea
        }
        System.out.println("**********************************************");
        System.out.println("**********************************************\n");
    }
    
    public static int [] calcularTotales(String [] notasAlumno){
        int aprobados = 0, suspensos = 0, convalidaciones = 0;
        int [] total = null;
        //empezamos en 3, porqué en las tres primeras posiciones está el nombre del alumno
        for (int i = 3; i<notasAlumno.length;i++){
            if (notasAlumno[i].equals("c-5")){
                convalidaciones++;
            }
            else if (Integer.parseInt(notasAlumno[i])<5){
                suspensos++;
            }
            else{
                aprobados++;
            }
        }
        //si no asignamos el valor a la array al momento de declararla, es necesaria instanciarla a posteriori
        total = new int[]{aprobados,suspensos,convalidaciones};
        
        return total;
    }
}
