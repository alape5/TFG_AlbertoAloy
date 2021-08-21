/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import javax.sound.midi.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.JOptionPane;

/**
 *
 * @author alberto
 */
public class Profesor {
    
    private String user = "";
    private String pass = "";
    
    public Profesor(String nombre, String pass) {
    
        this.user = nombre;
        this.pass = pass;
    
    }
    
    public boolean comprobarLogin(String user, String pass) { 
        return user.equals(this.user) && pass.equals(this.pass);
    }
    
}


/*
FILECHOOSER: Fichero seleccionado --> C:\Users\Alberto Aloy Pérez\Desktop\oques.midi
FILECHOOSER: OK - Fichero midi encontrado
FUNC(OperarMidi): OK - Fichero recibido: C:\Users\Alberto Aloy Pérez\Desktop\oques.midi
FUNC(OperarMidi): Sequence: javax.sound.midi.Sequence@6c9e7247
FUNC(OperarMidi): Tracks 2
FUNC(OperarMidi): Tipo de división: PPQ
FUNC(OperarMidi): Resolución 96
FUNC(OperarMidi): TEMPO 120.0
*/