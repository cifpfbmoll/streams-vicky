/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author victoriapenas
 */
public class Alumno {
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
        String [] notas = new String [6];//la inicializo en 6 porque habrá siempre notas de 6 asignaturas
        int pos = 3;//auxiliar para recorrer las notas. A partir de la posicion 3 tengo las notas
        this.setNombre(datosAlumno[0]);//en la posicion 0 tengo el nombre
        this.setApellidos(datosAlumno[1] + " " + datosAlumno[2]);//en las posiciones 1 y 2 tengo los apellidos
        for (int i = 0; i<notas.length;i++){
            notas[i] = datosAlumno[pos];  
            pos++;
        }
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
}
