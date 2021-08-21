/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.view;

import cantometro.controller.Controller;
import cantometro.model.Alumno;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class View {
    
    Controller c;
    
    private Login login;
    private NewProfe newProfe;
    private ControlPanel controlPanel;
    private NewAlumno newAlumno;
    private ListarAlumnos listarAlumnos;
    private SeleccionarAlumno seleccionarAlumno;
    private FileChooser fileChooser;
    private Examen_v examen;
    private Settings settings;
    
    public View() {
       
        login = new Login();
        newProfe = new NewProfe();
        controlPanel = new ControlPanel();
        newAlumno = new NewAlumno();
        listarAlumnos = new ListarAlumnos();
        seleccionarAlumno = new SeleccionarAlumno();
        fileChooser = new FileChooser();
        settings = new Settings();
        
        login.setVisible(true);
    
    }
    
    public void setListeners(Controller c) {
        
        this.c = c;
        
        login.setActionListener(c);
        newProfe.setActionListener(c);
        controlPanel.setActionListener(c);
        newAlumno.setActionListener(c);
        listarAlumnos.setActionListener(c);
        seleccionarAlumno.setActionListener(c);
        fileChooser.setActionListener(c);
        settings.setActionListener(c);
        
        
    }
    
    public void ventanaLogin(boolean visible) { login.setVisible(visible);  }
    public void ventanaNewProfe(boolean visible) { newProfe.setVisible(visible); }
    public void ventanaControlPanel(boolean visible) { controlPanel.setVisible(visible); }
    public void ventanaNewAlumno(boolean visible) { newAlumno.setVisible(visible); }
    public void ventanaListarAlumnos(boolean visible) { listarAlumnos.setVisible(visible); }
    public void ventanaSeleccionarAlumno(boolean visible) { seleccionarAlumno.setVisible(visible); }
    public void ventanaFileChooser(boolean visible) { fileChooser.setVisible(visible); }
    public void ventanaExamen(boolean visible) { examen.setVisible(visible); }
    public void ventanaSettings(boolean visible) { settings.setVisible(visible); }
    
    public ArrayList getLoginFields() { return login.getLoginFields(); }
    public ArrayList getNewProfeFields() { return newProfe.getNewProfeFields(); }
    public ArrayList getNewAlumnoFields() { return newAlumno.getNewAlumnoFields(); }
    public ArrayList getSettingsSeleccionados() { return settings.getSettingsSeleccionados(); }
    public String getFicheroSeleccionado() { return fileChooser.getFichero(); }
    public String getAlumnoSeleccionado() { return seleccionarAlumno.getAlumnoSeleccionado(); }
    
    public void flushNewAlumno() { newAlumno.flush(); }
    public void listarAlumnosTabla(ArrayList<ArrayList> alumnos) { listarAlumnos.populateTable(alumnos); };
    public void listarAlumnosComboBox(ArrayList alumnos) { seleccionarAlumno.populateComboBox(alumnos); };
    public void listarInputsComboBox(ArrayList inputs) { settings.populateComboBoxInputs(inputs); };
    public void listarAlgoritmosComboBox(ArrayList algos) { settings.populateComboBoxAlgoritmos(algos); }
    
    
    public void crearVentanaExamen(Alumno alumno, String cancion) throws InvalidMidiDataException, IOException, MidiUnavailableException, LineUnavailableException {
    
        this.examen = new Examen_v(alumno, cancion);
        this.examen.setActionListener(this.c);
    
    }
    public void switchButtonText() {
        
        examen.empezarPararExamen();
        
        
    }
    public ArrayList<ArrayList> getAlgoInputs() throws LineUnavailableException {
        
        ArrayList<ArrayList> result = new ArrayList<ArrayList>();
        
        result.add(examen.getAlgos());
        result.add(examen.getInputs());
        
        return result;
        
    }
    public boolean cambiarSettings(String input, String algo) {
        return examen.cambiarSettings(input, algo);
    }


    
    
}
