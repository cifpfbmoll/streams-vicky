/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica7;

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
    
}
