/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Alberto Aloy Pérez
 */
public class Model {
    
    private ArrayList<Profesor> profesores = new ArrayList();
    private ArrayList<Alumno> alumnos = new ArrayList();
    private Profesor profesorActivo = null;
    
    private Examen examen = null;
    
    public Model() {
    
        initProfesores();
        initAlumnos();
    
    }
    
    public void initProfesores() {
        
        profesores.add(new Profesor("demo0", ""));
        profesores.add(new Profesor("demo1", ""));
        profesores.add(new Profesor("demo2", ""));
        profesores.add(new Profesor("demo3", ""));
        profesores.add(new Profesor("demo4", ""));
        profesores.add(new Profesor("demo5", ""));
        profesores.add(new Profesor("demo6", ""));
        profesores.add(new Profesor("demo7", ""));
        profesores.add(new Profesor("demo8", ""));
        profesores.add(new Profesor("demo9", ""));
        
        // cheat profe
        profesores.add(new Profesor("", ""));
        
        System.out.println("MODEL @ initProfesores -- Inicializado correctamente");
        
    }
    
    public void initAlumnos() {
    
        alumnos.add(new Alumno("Lorem",          "Lorem",        "001",  "1º Elemental"));
        alumnos.add(new Alumno("Ipsum",          "Nec",          "002",  "1º Elemental"));
        alumnos.add(new Alumno("Dolor",          "Mollis",       "003",  "1º Elemental"));
        alumnos.add(new Alumno("Sit",            "Sem",          "004",  "1º Elemental"));
        alumnos.add(new Alumno("Amet",           "Tincidunt",    "005",  "1º Elemental"));
        alumnos.add(new Alumno("Consectetur",    "Altu",         "006",  "1º Elemental"));
        alumnos.add(new Alumno("Adipiscing",     "Donec",        "007",  "1º Elemental"));
        alumnos.add(new Alumno("Elit",           "Ullamcorper",  "008",  "1º Elemental"));
        alumnos.add(new Alumno("Sed",            "Rincidunt",    "009",  "1º Elemental"));
        alumnos.add(new Alumno("Dignissim",      "Tortor",       "010",  "1º Elemental"));
        alumnos.add(new Alumno("Mollis",         "Nec",          "011",  "1º Elemental"));
        alumnos.add(new Alumno("Urna",           "Vehicula",     "012",  "1º Elemental"));
        alumnos.add(new Alumno("Idert",          "Justo",        "013",  "1º Elemental"));
        alumnos.add(new Alumno("Sagittis",       "Pellentesque", "014",  "1º Elemental"));
        alumnos.add(new Alumno("Nulla",          "Vulputate",    "015",  "1º Elemental"));
        alumnos.add(new Alumno("Iaculis",        "In",           "016",  "1º Elemental"));
        alumnos.add(new Alumno("Non",            "Hac",          "017",  "1º Elemental"));
        alumnos.add(new Alumno("Suspendisse",    "Habitasse",    "018",  "1º Elemental"));
        alumnos.add(new Alumno("Potenti",        "Platea",       "019",  "1º Elemental"));
        alumnos.add(new Alumno("In",             "Dictumst",     "020",  "1º Elemental"));
        alumnos.add(new Alumno("Hac",            "Donec",        "021",  "1º Elemental"));
        alumnos.add(new Alumno("Habitasse",      "Eget",         "022",  "1º Elemental"));
        alumnos.add(new Alumno("Platea",         "Tellus",       "023",  "1º Elemental"));
        alumnos.add(new Alumno("Dictumst",       "Sapien",       "024",  "1º Elemental"));
        alumnos.add(new Alumno("Aenean",         "Vivamus",      "025",  "1º Elemental"));
        
        System.out.println("MODEL @ initAlumnos -- Inicializado correctamente");
    
    }
    
    public void loginProfesor(ArrayList al) {
        
        String user = (String) al.get(0);
        String pass = (String) al.get(1);        
        
        profesorActivo = new Profesor(user, pass);
        
    }
    
    public void logoutProfesor() {
        this.profesorActivo = null;
    }
    
    public boolean altaProfesor(ArrayList al) {
        
        String user = (String) al.get(0);
        String pass = (String) al.get(1);
        
        return profesores.add(new Profesor(user, pass));
    }
    
    public boolean altaAlumno(ArrayList al) {
        
        String nombre = (String) al.get(0);
        String apellidos = (String) al.get(1);
        String nia = (String) al.get(2);
        String curso = (String) al.get(3);
        
        return alumnos.add(new Alumno(nombre, apellidos, nia, curso));
        
    }
    
    public boolean comprobarProfesor(ArrayList al) {
        
        boolean existe = false;
        
        String user = (String) al.get(0);
        String pass = (String) al.get(1);
        
        for(int i = 0; i < profesores.size(); i++) {
            
            if(profesores.get(i).comprobarLogin(user, pass)) {
                existe = true;
                break;
            }
        }
        
        return existe;
        
    }
    
    public boolean registrarAlumno(String nombre, String apellidos, String nia, String curso) {
        
        Alumno al = new Alumno(nombre, apellidos, nia, curso);
        // Datos del nuevo alumno OK
        
        return (alumnos.add(al));
    }
    
    public ArrayList getStringAlumnos() {
    
        ArrayList<String> al = new ArrayList();
        
        for(int i = 0; i < alumnos.size(); i++) {
            
            String nombre = alumnos.get(i).getNombre();
            String apellidos = alumnos.get(i).getApellidos();

            String content = apellidos + ", " + nombre;

            al.add(content);
            
        }
        
        // ORdena los alumnos alfabéticamente por apellidos
        Collections.sort(al);

        return al;
    
    }
    
    public ArrayList<ArrayList> getAlumnos() {
        
        ArrayList todos_alumnos = new ArrayList();
    
        for(int i = 0; i < alumnos.size(); i++) {
            
            ArrayList alumno = new ArrayList();
            
            alumno.add(alumnos.get(i).getNombre());
            alumno.add(alumnos.get(i).getApellidos());
            alumno.add(alumnos.get(i).getNIA());
            alumno.add(alumnos.get(i).getCurso());
            alumno.add(String.valueOf(alumnos.get(i).getNota()));
            
            todos_alumnos.add(alumno);
            
        }
        
        return todos_alumnos;
    
    }
    
    public void mostrarAlumnos() {
        for(int i = 0; i < alumnos.size(); i++) {
            
            System.out.println("--------------------------");
            System.out.println(" Nombre: "      + alumnos.get(i).getNombre());
            System.out.println(" Apellidos: "   + alumnos.get(i).getApellidos());
            System.out.println(" NIA: "         + alumnos.get(i).getNIA());
            System.out.println(" Curso: "       + alumnos.get(i).getCurso());
            System.out.println(" Nota: "        + alumnos.get(i).getNota());
            System.out.println("--------------------------");
            
        }
        
        System.out.println("FUNC(MostrarAlumnos): No hay más alumnos que mostrar");
    }
    
    public boolean comprobarFichero(String fichero) {
        
        boolean okFichero = false;
        
        okFichero = ( fichero.endsWith(".midi") || fichero.endsWith(".mid"));
        
        return okFichero;
        
    }
    
    // Comprueba si un alumno dado en formato Apellidos, Nombre existe en la escuela
    public Alumno alumnoExiste(String al) {
        
        Alumno alumno = null;
                
        for(int i = 0; i < alumnos.size(); i++) {
            
            Alumno a = alumnos.get(i);
            String alumnoVector = a.getApellidos() + ", " + a.getNombre();
            
            if(al.equals(alumnoVector)) {
                
                alumno = a;
                break;
                
            }
            
        }
        
        return alumno;
        
    }
    
    public boolean ponerNota(Alumno al, float nota) {
        
        boolean ok = false;
        
        for(int i = 0; i < alumnos.size(); i++) {
            
            if(al == alumnos.get(i)) {
                
                alumnos.get(i).setNota(nota);
                ok = true;
                break;
                
            }
            
        }
        
        return ok;
        
    }
    
    public boolean escribirCSV() {
        
        boolean conseguido = false;

        try (PrintWriter writer = new PrintWriter(new File("C:\\Users\\Alberto Aloy Pérez\\Desktop\\Ingeniería Informática\\TFG - Trevall Fi de Grau\\test.csv"))) {

          String csv = "NIA,Nombre,Apellidos,Curso,Nota\n";
          
          for(int i = 0; i < alumnos.size(); i++){
              
              csv += (alumnos.get(i).getNIA() + ",");
              csv += (alumnos.get(i).getNombre() + ",");
              csv += (alumnos.get(i).getApellidos() + ",");
              csv += (alumnos.get(i).getCurso() + ",");
              csv += (alumnos.get(i).getNota() + "\n");
              
          }

          writer.write(csv);
          
          conseguido = true;

        } catch (FileNotFoundException e) {
          System.out.println(e.getMessage());
          conseguido = false;
          
        }
        
        return conseguido;

    }
    
    public static double normalizado(double max, double min, double value) {
        
        double resultado = 1 - (value-min)/(max-min);
        
        return resultado;
    }
    
    public Examen getExamen() {
    
        return this.examen;
    
    }
    
    public Alumno getAlumnoByName(String name) {
        
        Alumno a = null;
        
        for(int i = 0; i < alumnos.size(); i++) {
            if(alumnos.get(i).getNombreCompleto().equals(name)) {
                a = alumnos.get(i);
                break;
            }
            
        }
        
        return a;
    }

    public boolean comprobarAlumno(ArrayList nuevoAlumno) {
        
        boolean existe = false;
        
        String nombre = (String) nuevoAlumno.get(0);
        String apellidos = (String) nuevoAlumno.get(1);
        String nia = (String) nuevoAlumno.get(2);
        String curso = (String) nuevoAlumno.get(3);
        
        for(int i = 0; i < alumnos.size(); i++) {
            
            Alumno a = alumnos.get(i);
            
            if(a.getNombre().equals(nombre) && a.getApellidos().equals(apellidos) && a.getNIA().equals(nia) && a.getCurso().equals(curso)) {
                existe = true;
                break;
            }

        }
        
        return existe;
        
    }
    
    
}
