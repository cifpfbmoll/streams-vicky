/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

/**
 * <h1>Esta excepción se ha creado para controlar si el usuario no introduce 
 * una ruta por teclado y si la ruta indicada no existe.</h1>
 * 
 * @author victoriapenas
 * @version 1.0
 * @since 2020-03-01
 */
public class ErrorderutaException extends Exception{
    private String mensaje;
    private int codError;

    /**
     * <h2>Contructor vacio</h2>
     */
    public ErrorderutaException() {
    }
    
    /**
     * <h2>Constructor con parámetros</h2>
     * @param mensaje Mensaje de error.
     * @param codError Codigo de error asociado al atributo mensaje.
     */
    public ErrorderutaException(String mensaje, int codError) {
        this.setMensaje(mensaje);
        this.setCodError(codError);
    }
    
    /**
     * Constructor para setear un mensaje u otro en función del codigo de error indicado.
     * @param codError codError se utiliza para setear un valor al atributo mensaje
     * en función del código indicado.
     */
    public ErrorderutaException(int codError) {
        if (codError == 101){
            this.setMensaje("La ruta de origen indicada no existe");
        }
        else if (codError == 102){
            this.setMensaje("No has introducido una ruta");
        }
    }

    /**
     * getter del atributo mensaje.
     * @return devuelve un string con el mensaje.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * getter del atributo codError.
     * @return devuelve un entero con el codigo del error.
     */
    public int getCodError() {
        return codError;
    }

    /**
     * setter del atributo mensaje.
     * @param mensaje Mensaje asociado a la instancia.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * setter del atributo codError.
     * @param codError codigo de error asociado a la instancia.
     */
    public void setCodError(int codError) {
        this.codError = codError;
    }
    
    /**
     * <h2>Método para registrar en un fichero todos los errores que se producen
     * en el programa.</h2>
     * <p>Cualquier excepcion que se produzca en el programa es documentada en 
     * un fichero de salida llamado errores.txt. Se registra la fecha y la hora
     * en la cual se ha producido el error, el mensaje de error y la pila de ejecución.</p>
     * 
     * @see #registrarFecha(java.io.OutputStreamWriter) 
     * 
     * @param errorMessage Mensaje de error
     * @param pila Pila es las trazas de ejecución desde dónde se ha producido la excepción.
     */
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
            System.out.println("Ha ocurrido un error inesperado. Más detalles:");
            System.out.println(ex.getCause());
        }
    }
    
    /**
     * <h2>Método para registrar la fecha y la hora.</h2>
     * <p>El método <b>registrarFecha</b> trasncribe en el fichero de errores.txt
     * generado en el método {@link #registrarErrores} la fecha y la hora en el que se
     * ha producido un error. Para obtener los datos se ha utilizado la clase Calendar.</p>
     * 
     * @see java.util.Calendar
     * 
     * @param writer Objeto de escritura que se utilizará.
     * 
     * @throws IOException Este método lanza una excepcion de tipo IOException
     * que se debe controlar.
     */
    public static void registrarFecha(OutputStreamWriter writer) throws IOException{
        //fuente: http://lineadecodigo.com/java/obtener-la-hora-en-java/
        Calendar calendario = Calendar.getInstance();
        int month = calendario.get(Calendar.MONTH) + 1; //enero empieza en 0, no hago el cálculo en la array porqué lo calcula mal
        int [] fecha = {calendario.get(Calendar.DATE),month,calendario.get(Calendar.YEAR),
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
