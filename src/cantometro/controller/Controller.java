/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.controller;

import cantometro.model.Model;
import cantometro.view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class Controller implements ActionListener {
    
    private View vista;
    private Model model;
    
    public Controller(View v, Model m) {
        
        this.vista = v;
        this.model = m;
        
        v.setListeners(this);
        
    }
    
    /*******************************************************
     * Los comandos siguien el siguiente sistema:
     * 
     * VENTANA_COTON
     * 
     * Excepto la ventana de LOGIN, las demás ventanas usarán abreviaciones:
     * (NP) --- NewProfe
     * (NA) --- NewAlumno
     * (CP) --- ControlPanel
     * (LA) --- ListarAlumnos
     * (SA) --- SeleccionarAlumno
     * (EX) --- Examen
     * 
     * @param evt
    ***************************************************************/
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        
        String command = evt.getActionCommand();
    
        // VENTANA LOGIN
            if(command.equals("LOGIN_ENTRAR"))     login();
            if(command.equals("LOGIN_ALTA"))       loginVentanaNewProfe();

        // VENTANA NUEVO PROFESOR
            if(command.equals("NP_REGISTRAR"))     altaProfesor();
            if(command.equals("NP_VOLVER"))        newProfeVolver();

        // VENTANA PANEL DE CONTROL
            if(command.equals("CP_NEWALUMNO"))     controlPanelNewAlumno();
            if(command.equals("CP_LISTARALUMNOS")) controlPanelListarAlumnos();
            if(command.equals("CP_EVALUAR"))       controlPanelEvaluar();
            if(command.equals("CP_EXPORTAR"))      controlPanelExportar();
            if(command.equals("CP_LOGOUT"))        controlPanelLogout();

        // VENTANA NUEVO ALUMNO
            if(command.equals("NA_CERRAR"))        newAlumnoCerrar();
            if(command.equals("NA_BORRAR"))        newAlumnoBorrar();
            if(command.equals("NA_CREAR"))         newAlumnoAltaAlumno();

        // VENTANA LISTAR ALUMNO
            if(command.equals("LA_VOLVER"))         listarAlumnosVolver();

        // VENTANA SELECCIONAR ALUMNO
            if(command.equals("SA_EXAMINAR"))      seleccionarAlumnoExaminar();
            if(command.equals("SA_CERRAR"))        seleccionarAlumnoCerrar();

        // VENTANA FILE CHOOSER
            if(command.equals(JFileChooser.APPROVE_SELECTION)) { 
                try { fileChooserFicheroSeleccionado(); }
                catch (InvalidMidiDataException | IOException | MidiUnavailableException | LineUnavailableException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(command.equals(JFileChooser.CANCEL_SELECTION)) { fileChooserCancelar(); }

        // VENTANA EXAMEN
            if(command.equals("EX_SETTINGS"))      try {
                examenSettings();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(command.equals("EX_CERRAR"))        examenCerrar();
            if(command.equals("EX_START"))         examenStart();

        // VENTANA SETTINGS
            if(command.equals("ST_APLICAR"))        settingsAplicar();
            if(command.equals("ST_CANCEL"))         settingsCancelar();
            
        // PONER NOTA
            if(command.equals("EX_ACEPTAR"))    examenTerminadoPonerNota();
            if(command.equals("EX_CANCELAR"))   examenTerminadoCerrar();
        

        
        
    }
    
    public void login() {
        
        boolean profesorExiste = false;
        ArrayList fields = null;
        
        fields = vista.getLoginFields();
        profesorExiste = model.comprobarProfesor(fields);

        if(profesorExiste) {
            vista.ventanaLogin(false);
            vista.ventanaControlPanel(true);
            model.loginProfesor(fields);
        }
        else {
            JOptionPane.showMessageDialog(null, "Login: FAIL\nUsuario y/o contraseña incorrectos");
        }
        
        
    }

    private void loginVentanaNewProfe() {
        vista.ventanaLogin(false);
        vista.ventanaNewProfe(true);
    }

    private void altaProfesor() {
        
        ArrayList nuevoProfesor = vista.getNewProfeFields();
        boolean profesorExiste = model.comprobarProfesor(nuevoProfesor);
        
        if(!profesorExiste) {
            vista.ventanaNewProfe(false);
            vista.ventanaLogin(true);
            JOptionPane.showMessageDialog(null, "Registrar Profesor: OK\nProfesor registrado correctamente!");
            model.altaProfesor(nuevoProfesor);
        }
        else {
            JOptionPane.showMessageDialog(null, "Registrar Profesor: FAIL\nEl profesor que intentas registrar ya existe");
        }
        
        
    }

    private void newProfeVolver() {
        vista.ventanaNewProfe(false);
        vista.ventanaLogin(true);
    }

    private void controlPanelNewAlumno() {
        vista.ventanaNewAlumno(true);
        vista.ventanaLogin(false);
    }

    private void controlPanelListarAlumnos() {
        
        ArrayList<ArrayList> alumnos;
        
        alumnos = model.getAlumnos();
        vista.listarAlumnosTabla(alumnos);
        vista.ventanaControlPanel(false);
        vista.ventanaListarAlumnos(true);
    }

    private void controlPanelEvaluar() {
        
        ArrayList alumnos;
        alumnos = model.getStringAlumnos();
        vista.listarAlumnosComboBox(alumnos);
        vista.ventanaSeleccionarAlumno(true);
    }

    private void controlPanelExportar() {
        if(model.escribirCSV())
            JOptionPane.showMessageDialog(null, "Exportacíon realizada con éxito!");
        
        else
            JOptionPane.showMessageDialog(null, "Error no controlado en la exportación!");
    }

    private void controlPanelLogout() {
        model.logoutProfesor();
        vista.ventanaControlPanel(false);
        vista.ventanaLogin(true);
    }

    private void newAlumnoCerrar() {
        vista.ventanaNewAlumno(false);
    }

    private void newAlumnoBorrar() {
        vista.flushNewAlumno();
    }

    private void newAlumnoAltaAlumno() {
        ArrayList nuevoAlumno = vista.getNewAlumnoFields();
        
        boolean alumnoExiste = model.comprobarAlumno(nuevoAlumno);
        
        if(!alumnoExiste) {
            model.altaAlumno(nuevoAlumno);
            JOptionPane.showMessageDialog(null, "Registrar Alumno: OK\nAlumno registrado correctamente!");
        }
        
        else
            JOptionPane.showMessageDialog(null, "Registrar Alumno: FAIL\nEl alumno que intentas registrar ya existe");
        
        
        vista.ventanaNewAlumno(false);
    }
    
    private void listarAlumnosVolver() {
    
        vista.ventanaListarAlumnos(false);
        vista.ventanaControlPanel(true);
    
    }
    
    private void seleccionarAlumnoExaminar() {
        
        vista.ventanaControlPanel(false);
        vista.ventanaSeleccionarAlumno(false);
        vista.ventanaFileChooser(true);
    
    }
    
    private void seleccionarAlumnoCerrar() {
        
        vista.ventanaSeleccionarAlumno(false);
        vista.ventanaControlPanel(true);
        
    }
    
    private void fileChooserFicheroSeleccionado() throws InvalidMidiDataException, IOException, MidiUnavailableException, LineUnavailableException {
        
        String pathCancion = vista.getFicheroSeleccionado();
        String alumno = vista.getAlumnoSeleccionado();
        
        boolean okFichero = model.comprobarFichero(pathCancion);
        
        if(okFichero) {
            
            vista.ventanaFileChooser(false);
            vista.crearVentanaExamen(model.getAlumnoByName(alumno), pathCancion);
            vista.ventanaExamen(true);

        }

        else
            JOptionPane.showMessageDialog(null, "Seleccionar Fichero: FAIL\nSeleccione un archivo midi válido.");

    }
    
    private void fileChooserCancelar() {
        
        vista.ventanaFileChooser(false);
        vista.ventanaControlPanel(true);
    
    }

    private void examenSettings() throws LineUnavailableException {
        
        ArrayList<ArrayList> algoInputs  = vista.getAlgoInputs();
        ArrayList algos = algoInputs.get(0);
        ArrayList inputs = algoInputs.get(1);
        
        vista.listarAlgoritmosComboBox(algos);
        vista.listarInputsComboBox(inputs);
        vista.ventanaSettings(true);
    
    }
    
    private void examenCerrar() {
        
        vista.ventanaExamen(false);
        vista.ventanaControlPanel(true);
    
    }
    
    private void examenStart() {
    
        System.out.println("EXAMEN: Start Capturado");
        vista.switchButtonText();

        
        
        

    
    }
    
    private void settingsAplicar() {
    
        System.out.println("APLICANDO SETTINGS");
        
        ArrayList newSettings = vista.getSettingsSeleccionados();
        
        String algo = (String) newSettings.get(0);
        String input = (String) newSettings.get(1);
        
        if(vista.cambiarSettings(input, algo)) {
            JOptionPane.showMessageDialog(null, "Settings: OK\nSettings cambiados correctamente!");
            vista.ventanaSettings(false);
        }
        else {
            JOptionPane.showMessageDialog(null, "Settings: FAIL\nNo se cambiarán los ajustes.");
            vista.ventanaSettings(false);
        }
    
    }
    
    private void settingsCancelar() {
    
        vista.ventanaSettings(false);
    
    }
    
    public void examenTerminadoPonerNota() {
        
        System.out.println("EVENTO PONER NOTA CAPTURADO DESDE EL CONTROLLER");
        
    }
    
    public void examenTerminadoCerrar() {
        
        System.out.println("EVENTO PONER NOTA CAPTURADO DESDE EL CONTROLLER");
        
    }
    
}
