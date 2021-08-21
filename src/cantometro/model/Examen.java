/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class Examen {
    
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static String OS = null;
    
    
    /*********************************************************************************/
    
    private double patternLengthInSeconds;//in seconds

    private double patternLengthInQuarterNotes;
    private double tpqn; // Time per Quarter Note

    //private static final double HERTZ_DEVIATION = 30.0; // Definimos la tolerancia en HERCIOS


    ArrayList<Double> pattern = new ArrayList(); // in cents

    /*
        Para el timing tendremos que tomar como referencia el sistema inglés de notación musical:
        La unidad de referencia es el "quarter note", es decir la corchea. Por lo tanto, las figuras
        musicales quedarán de la siguiente manera:

        Redonda: 4
        Blanca: 2
        Negra: 1
        Corchea: 0.5
        Semicorchea: 0.25

    */
    ArrayList<Double> timing = new ArrayList(); //in quarter notes
    ArrayList<Double> startTimeStamps;
    ArrayList<Double> freqs;
    ArrayList<Double> iStart = new ArrayList();
        
    
    
    
    
    /*********************************************************************************/
    
    public Examen(String cancion, Alumno alumno) throws InvalidMidiDataException, IOException, MidiUnavailableException, LineUnavailableException {
        
        
        
        /*
            En este momento ya tenemos un fichero midi pasado por el usuario
            la idea es convertir el fichero midi en un objeto de tipo Canción.
            Para ello necesitamos diferentes datos:

            BPM : Esencial como ritmo de la canción
            Resolución de Ticks: Ticks por segundo de la canción
            Notas: Expresadas como pareja (Tick, nota)

            Este último tendrá una complicación:
            Guardaremos el tick de 

        */
        
    
    }
    
    
    

    
    public static String toLocalString(Object info) {
        if(!isWindows())
                return info.toString();
        //String defaultEncoding = Charset.defaultCharset().toString();
        //try
        //{
            return info.toString();
            //return new String(info.toString().getBytes("windows-1252"), StandardCharsets.US_ASCII);
            //return new String(info.toString().getBytes("windows-1252"), defaultEncoding);
        //}
        /*
        catch(UnsupportedEncodingException ex)
        {
                return info.toString();
        }
        */
    }
    
    public static String getOsName() {
        return System.getProperty("os.name");
    }
    
    public static boolean isWindows() {
       return getOsName().startsWith("Windows");
    }
    /*
    public ArrayList getInputs() throws LineUnavailableException {
        return settings.getInputs();
    }
    
    public ArrayList getAlgos() {
        return settings.getAlgos();
    }
    
    public boolean cambiarSettings(String input, String algo) {
        return settings.cambiarSettings(input, algo); 
    }
    
    public Cancion getCancion() {
        
        return this.cancion;
    }
    
    public Alumno getAlumno() {
        
        return this.alumno;
    }
    
    public ArrayList getTimingQn() {
        return this.cancion.getTimingQn();
    }
    
    public ArrayList getPatternHz() {
        return this.cancion.getPatternHz();
    }
    
    
    
    /*****************************************************************************/
    
   
    
    
    
}
