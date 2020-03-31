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
import java.util.Locale;

/**
 *
 * @author victoriapenas
 */
public class ErrorderutaException extends Exception{
    private String mensaje;
    private int codError;

    public ErrorderutaException() {
    }
    
    public ErrorderutaException(String mensaje, int codError) {
        this.setMensaje(mensaje);
        this.setCodError(codError);
    }
    
    public ErrorderutaException(int codError) {
        if (codError == 101){
            this.setMensaje("La ruta de origen indicada no existe");
        }
        else if (codError == 102){
            this.setMensaje("No has introducido una ruta");
        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getCodError() {
        return codError;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setCodError(int codError) {
        this.codError = codError;
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
