/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.model;

import java.util.ArrayList;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class Cancion {
    
    /*
    CADA KEY DE MIDI CORRESPONDE A UNA FRECUENCIA
    https://www.inspiredacoustics.com/en/MIDI_note_numbers_and_center_frequencies
    */
    
    private ArrayList<Double> patternHz = new ArrayList(); // Frecuencia en Hercios
    private ArrayList<Double> timingQn = new ArrayList();  // Quarter Notes
    private double lengthInSecs;    // Duración en segundos
    private float tempo;

    public Cancion(float tempo, ArrayList noteOn, ArrayList noteOff, ArrayList keyNote, int ticksPerSecond) {
    
        this.tempo = tempo;
        
        int tam = noteOff.size();
        long ultimoTick = (long) noteOff.get(tam-1);
        
        // Calculamos la duración en segundos de la canción
        this.lengthInSecs = (double)((double)ultimoTick/(double)ticksPerSecond);
        System.out.println("CANCION @ Constructor -> Duración en segundis: " + this.lengthInSecs);
        
        // Rellenamos el patternHz
        for(int i = 0; i < noteOn.size(); i++) {
            
            double key = midiToFreqs((int)keyNote.get(i));
            
            patternHz.add(key);
            System.out.println("CANCION @ Constructor -> Pattern[" + i + "] = " + keyNote.get(i));
        }
        
        /*
            Rellenamos el vector de timings en quarter notes
            Recordamos el valor que tenía cada quarter note
        
            Redonda: 4
            Blanca: 2
            Negra: 1
            Corchea: 0.5
            Semicorchea: 0.25
        */
        
        for(int i = 0; i < noteOn.size(); i++) {
            
            long duracionEnTicks = (long)noteOff.get(i) - (long)noteOn.get(i);
            double duracion = (double)duracionEnTicks / (double)ticksPerSecond;
            timingQn.add(duracion);
            
            System.out.println("CANCION @ Constructor -> Timing[" + i + "] = " + this.timingQn.get(i));
            
        }
    }
    
    public ArrayList<Double> getPatternHz() {
        return patternHz;
    }

    public void setPatternHz(ArrayList pattern) {
        this.patternHz = pattern;
    }

    public ArrayList<Double> getTimingQn() {
        return timingQn;
    }

    public void setTimingQn(ArrayList timing) {
        this.timingQn = timing;
    }

    public double getLengthInSecs() {
        return lengthInSecs;
    }

    public void setLengthInSecs(double length) {
        this.lengthInSecs = length;
    }
    
    public float getTempo() {
        return this.tempo;
    }
    
    
    // https://www.inspiredacoustics.com/en/MIDI_note_numbers_and_center_frequencies
    public double midiToFreqs(int key) {

        double freq = 0.00;

        switch((int)key) {
            case 79: freq = 783.99; break;
            case 78: freq = 739.99; break;
            case 77: freq = 698.46; break;
            case 76: freq = 659.26; break;
            case 75: freq = 622.25; break;
            case 74: freq = 587.33; break;
            case 73: freq = 554.37; break;
            case 72: freq = 523.25; break;
            case 71: freq = 493.88; break;
            case 70: freq = 466.16; break;
            case 69: freq = 440.00; break;
            case 68: freq = 415.30; break;
            case 67: freq = 392.00; break;
            case 66: freq = 369.99; break;
            case 65: freq = 349.23; break;
            case 64: freq = 329.63; break;
            case 63: freq = 311.13; break;
            case 62: freq = 293.66; break;
            case 61: freq = 277.18; break;
            case 60: freq = 261.63; break;
            case 59: freq = 246.94; break;
            case 58: freq = 233.08; break;
            case 57: freq = 220.00; break;
            case 56: freq = 207.65; break;
            case 55: freq = 196.00; break;
            default: freq = -1; break;

        }

        return freq;
    }
    
    

    
    
    
}
