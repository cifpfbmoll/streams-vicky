/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica7Ejercicio3;

/**
 *
 * @author victoriapenas
 */
public class ErrornotaException extends Exception{
    private String nombreCompletoAlumno;
    private int nota;
    private String modulo;
    private String mensaje;

    public ErrornotaException() {
    }

    public ErrornotaException(String nombreCompletoAlumno, int nota, String modulo) {
        this.setNombreCompletoAlumno(nombreCompletoAlumno);
        this.setNota(nota);
        this.setModulo(modulo);
        this.setMensaje(nombreCompletoAlumno,nota, modulo);
    }

    public String getNombreCompletoAlumno() {
        return nombreCompletoAlumno;
    }

    public void setNombreCompletoAlumno(String nombreCompletoAlumno) {
        this.nombreCompletoAlumno = nombreCompletoAlumno;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String nombreCompletoAlumno, int nota, String modulo) {
        this.mensaje = "El alumno "+ nombreCompletoAlumno +" no puede tener un " + nota
                + " en el módulo de " + modulo + ". Debes poner una nota válida";
    }
    
}
