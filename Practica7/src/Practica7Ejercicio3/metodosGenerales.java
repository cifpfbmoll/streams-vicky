/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;

import java.io.IOException;
import java.util.Calendar;

/**
 *
 * En esta clase están los métodos generales y útiles para todo el programa
 */
public class metodosGenerales {
    
        public static String registrarFecha() throws IOException{
        //fuente: http://lineadecodigo.com/java/obtener-la-hora-en-java/
        Calendar calendario = Calendar.getInstance();
        String fecha = calendario.get(Calendar.DATE)+"/"+
                (calendario.get(Calendar.MONTH) + 1)+"/"+calendario.get(Calendar.YEAR);
            
        return fecha;
    }
    
    /*esta función me rellena espacios a la derecha*/
    public static String rightpad(String string, int length) {
        //return String.format("%-" + length + "." + length + "s", string);
        /*el % hace referencia al argumento que formatea, el - me justifica el
        texto a la izquierda, el lenght asigna el ancho total, la s indica que es una string*/
        return String.format("%-" + length + "s", string);
    }
}
