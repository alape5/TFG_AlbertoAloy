/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.model;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class Alumno {
    
    private String nombre;
    private String apellidos;
    private String nia;
    private float nota;
    private String curso; 
    
    public Alumno(String nombre, String apellidos, String nia, String curso) {
        this.nombre = nombre;
        this.nia = nia;
        this.curso = curso;
        this.apellidos = apellidos;
        this.nota = -1;
        
        //System.out.println("ALUMNO: OK - Creación de alumno con nombre: " + nombre + " NIA: " + nia);
    }

    public boolean setNota(float nota) {
        this.nota = nota;
        return true;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public String getNIA() {
        return this.nia;
    }
    
    public float getNota() {
        return this.nota;
    }
    
    public String getCurso() {
        return this.curso;
    }
    
    public String getApellidos() {
        return this.apellidos;
    };
    
    public int getTolerance() {
        
        int tolerance = 0;
        
        switch(this.curso) {
            case "1º Elemental":
                tolerance = 40;
                break;
            case "2º Elemental":
                tolerance = 30;
                break;
            case "3º Elemental":
                tolerance = 25;
                break;
            case "4º Elemental":
                tolerance = 20;
                break;
        }
        
        return tolerance;
        
    }
    
    public String getNombreCompleto() {
        
        return this.apellidos + ", " + this.nombre;
        
    }
    
}
