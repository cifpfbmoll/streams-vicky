/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;
import static Practica7Ejercicio3.metodosGenerales.cabeceraBoletin;
import static Practica7Ejercicio3.metodosGenerales.contenidoBoletin;
import static Practica7Ejercicio3.metodosGenerales.registrarFecha;
import static Practica7Ejercicio3.metodosGenerales.resumenBoletin;
import static Practica7Ejercicio3.metodosGenerales.rightpad;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author victoriapenas
 */
public class Alumno implements Serializable {
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String [] notas;

    public Alumno() {
    }
    
    public Alumno(Alumno alumno) {
        this.setNombre(alumno.getNombre());
        this.setPrimerApellido(alumno.getPrimerApellido());
        this.setSegundoApellido(alumno.getSegundoApellido());
        this.setNotas(alumno.getNotas());
    }
    
    //me creo este contructor especial para poder crear objetos a partir de una array con todos los datos
    public Alumno (String [] datosAlumno){
        String [] notasAlumno = new String [datosAlumno.length-3];//en las tres primeras posiciones tengo el nombre, el resto de datos son notas
        int pos = 3;//auxiliar para recorrer las notas. A partir de la posicion 3 tengo las notas
        this.setNombre(datosAlumno[0]);//en la posicion 0 tengo el nombre
        this.setPrimerApellido(datosAlumno[1]);//en la posicion 1 tengo el primer apellido
        this.setSegundoApellido(datosAlumno[2]); //en la posicion 2 tengo el segundo apellido
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

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }



    public String[] getNotas() {
        return notas;
    }

    public void setNotas(String[] notas) {
        this.notas = notas;
    }
    
    public static void crearFicheroObjAlumnos() throws FileNotFoundException, IOException, ErrornotaException{
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
                    comprobarNotas(datosAlumno);//compruebo que no haya alumnos con notas erroneas
                    alumno = new Alumno(datosAlumno);
                    writer.writeObject(alumno);
                }
            }while(linea != null);
        } finally {
            docObj.close();
            writer.close();
        }
    }
    
    public static void imprimirBoletin() throws FileNotFoundException, ClassNotFoundException, IOException{
        File origen = new File ("alumnosObj");
        FileInputStream ficheroOrigen = null;
        ObjectInputStream lectorObj = null;
        try {
            ficheroOrigen = new FileInputStream(origen);//ruta origen
            lectorObj = new ObjectInputStream(ficheroOrigen); //lector de objetos
            while (true){ //mientras haya objetos sigue leyendo. Cuando ya no hay objetos el lector lanza una excepción
                Alumno alumno = (Alumno) lectorObj.readObject(); // instancio un alumno, y leo la instancia
                System.out.println("\n****************NUEVO ALUMNO****************\n");
                alumno.imprimirCabecera();
                alumno.imprimirNotas();
                alumno.imprimirResumen();
            }
        } finally {
            ficheroOrigen.close();
            lectorObj.close();
        }
    }
    
    public void imprimirCabecera(){
        String [] plantilla = cabeceraBoletin();
        for (int i = 0; i<plantilla.length; i++){
            System.out.print(plantilla[i]);
            if (i==3){
                System.out.print(this.getNombre() + " " + this.getPrimerApellido() + " " + this.getSegundoApellido());
            }
            System.out.println("");//salto de linea. Lo pongo asi para que me encaje el nombre del alumno
        }
    }
    
    public void imprimirNotas(){
        String [] plantilla = contenidoBoletin();
        int aux = 0;//auxiliar para recorrer la array de notas
        for (int i = 0; i<plantilla.length; i++){
            /*con la funcion rightpad pongo los espacios de la derecha,
            siendo 35 la longitud total que tiene que acer la linea. esta funcion está en el main*/
            System.out.print(rightpad(plantilla[i],35));
            System.out.println(this.getNotas()[aux]);
            aux++;
        }
    }
      
    public void imprimirResumen() throws IOException{
        String [] plantilla = resumenBoletin();
        int aux = 0;//auxiliar para recorrer la array de notas
        int [] notasTotales = calcularTotales(this.getNotas());
        for (int i = 0; i<plantilla.length; i++){
            System.out.print(plantilla[i]);
            if (i > 0 && i < 4){//Entre las posiciones del 1 al 3 imprimir el sumatorio de notas
                System.out.print(notasTotales[aux]);
                aux++;
            }
            if(i == 5){//en la posicion 5 de la array registro la fecha
                System.out.print(registrarFecha());
            }
            System.out.println("");//con esto añado un salto de linea
        }
    }
    
    public static int [] calcularTotales(String [] notasAlumno){
        int aprobados = 0, suspensos = 0, convalidaciones = 0;
        int [] total = null;
        for (int i = 0; i<notasAlumno.length;i++){//el metodo static le envia solo las notas, el de escritura todo
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
    
    public static void generarBoletines() throws FileNotFoundException, IOException, ErrornotaException{
        String linea = "";
        String [] notasAlumno = null;
        File entrada = new File("notas.txt");
        String docSalida = "";
        try (BufferedReader lector = new BufferedReader(new FileReader(entrada))){ //TO DO mail a Rafa
            do{
                linea = lector.readLine();
                if (linea != null){//tengo que poner este if, porque sino me salta la excepcion nullpointer exception
                    notasAlumno = linea.split(" ");
                    comprobarNotas(notasAlumno);//compruebo que no hay notas negativas ni ceros
                    /*creo el fichero de salida a partir de los datos del alumno*/
                    docSalida = (generarFicheroSalida(notasAlumno)+".txt");
                    escribirCabecera(notasAlumno, docSalida);
                    escribirContenido(notasAlumno, docSalida);
                    escribirResumen(notasAlumno,docSalida);
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
    
    public static void escribirCabecera(String [] datosAlumno, String ficheroSalida) throws FileNotFoundException, IOException{
        String [] plantilla = cabeceraBoletin();
        String nuevaLinea = System.getProperty("line.separator");
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ficheroSalida), StandardCharsets.UTF_8)){
            for (int i = 0; i<plantilla.length; i++){
                writer.write(plantilla[i]);
                if (i==3){// a partir de la posicion 3 debo escribir el nombre del alumno
                    for(int j = 0; j<3;j++){//escribo el nombre del alumno
                        writer.write(datosAlumno[j] + " ");
                    }
                }
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }
                
    public static void escribirContenido(String [] notasAlumno, String ficheroSalida) throws FileNotFoundException, IOException{
        String [] plantilla = contenidoBoletin();
        String nuevaLinea = System.getProperty("line.separator");
        int pos = 3; //auxiliar para escribir las notas de los alumnos, empezamos en la posición 3 de la array
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ficheroSalida, true), StandardCharsets.UTF_8)){
            for (int i = 0; i<plantilla.length; i++){
                /*con la funcion rightpad pongo los espacios de la derecha,
                siendo 35 la longitud total que tiene que acer la linea*/
                writer.write(rightpad(plantilla[i],35));
                writer.write(notasAlumno[pos]);
                pos++;
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }
    
    public static void escribirResumen(String [] datosAlumno, String ficheroSalida) throws FileNotFoundException, IOException{
        String [] plantilla = resumenBoletin();
        String nuevaLinea = System.getProperty("line.separator");
        int pos = 0; //auxiliar para recorrer la array notasTotales
        /*el metodo calcular notas solo debe recibir las notas, por lo tanto,
        con el método extraerNotas depuramos la array elimnando el nombre y dejando unicamente las notas del alumno*/
        String [] notas = extraerNotas(datosAlumno);
        int [] notasTotales = calcularTotales(notas);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ficheroSalida, true), StandardCharsets.UTF_8)){
            for (int i = 0; i<plantilla.length; i++){
                writer.write(plantilla[i]);
                if (i > 0 && i < 4){
                    writer.write(Integer.toString(notasTotales[pos]));//convierto a String para poder escribir
                    pos++;
                }
                if(i == 5){//en la posicion 5 de la array registro la fecha
                    writer.write(registrarFecha());
                }
                writer.append(nuevaLinea);//con esto añado un salto de linea
            }
        }
    }
    
    /*metodo para extraer las notas de una fila de datos del alumno*/
    public static String [] extraerNotas(String [] datosAlumno){
        String [] notas = new String [datosAlumno.length-3];
        int aux = 3;//las notas están a partir de la posicion tres
        for (int i = 0; i<datosAlumno.length-3;i++){//le resto tres para no salirme de rango
            notas[i] = datosAlumno[aux];
            aux++;
        }
        
        return notas;
    }
    
    public static void comprobarNotas(String [] notas) throws ErrornotaException{
        String modulo = "";
        for (int i = 3; i<notas.length;i++){//en las tres primeras posiciones tengo el nombre del alumno
            if (!notas[i].equals("c-5")){
                if (Integer.parseInt(notas[i])<=0){
                    switch (i) {
                        case 3:
                            modulo = "Lenguaje de marcas";
                            break;
                        case 4:
                            modulo = "Programación";
                            break;
                        case 5:
                            modulo = "Entornos de desarrollo";
                            break;
                        case 6:
                            modulo = "Base de datos";
                            break;
                        case 7:
                            modulo = "Sistemas informáticos";
                            break;
                        case 8:
                            modulo = "FOL";
                            break;
                        default:
                            break;
                    }
                    throw new ErrornotaException((notas[0] + " " + notas[1] + " " + notas[2]),
                            Integer.parseInt(notas[i]),modulo);
                }
            }
        }
    }
}
