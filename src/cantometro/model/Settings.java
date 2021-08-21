/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.model;

import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;
import java.util.ArrayList;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class Settings extends JFrame {
    
    private final boolean supportsRecording = true;
    private final boolean supportsPlayback = false;
    private final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        
    private Vector<Mixer.Info> info; // Todas las interfaces del mixer
    private Vector<Mixer.Info> entradas; // Sólo las interfaces que sirven de entrada
    private ArrayList<PitchEstimationAlgorithm> algos; 
    private PitchProcessor.PitchEstimationAlgorithm algo;
    private Mixer mixer;
    
    public Settings() throws LineUnavailableException {
        
        info = new Vector<Mixer.Info>();
        algos = new ArrayList<PitchEstimationAlgorithm>();
        entradas = new Vector<Mixer.Info>();
        
        for (PitchEstimationAlgorithm value : PitchEstimationAlgorithm.values()) {
            algos.add(value);
        }
        
        // Capturamos todas las interfaces
        info = getMixerInfo(supportsPlayback, supportsRecording);
        
        // Filtramos las interfaces únicamente de grabación
        for(int i = 0; i < info.size(); i++) {
            
            Mixer mix = AudioSystem.getMixer(info.get(i));
            if(comprobarMixer(mix)) {
                entradas.add(info.get(i));
            }
            
        }
        
        // Inicializamos mixer con la primera línea de entrada
        mixer = AudioSystem.getMixer(entradas.get(0));
        
        // Inicializamos Algoritmo con el primer algoritmo
        algo = (PitchEstimationAlgorithm)algos.get(0);
        
        System.out.println("SETTINGS @ Constructor: ALGO = " + algo.toString());
        System.out.println("SETTINGS @ Constructor: MIXER = " + mixer.getMixerInfo().getName());
        

    }
    
    public PitchProcessor.PitchEstimationAlgorithm getAlgorithm() {
        return this.algo;
    }
    
    public Mixer getMixer() {
        return this.mixer;
    }
    
    // Función que devuelve los Mixers filtrados por Grabación y Reproducción
    public Vector<Mixer.Info> getMixerInfo(final boolean supportsPlayback, final boolean supportsRecording) throws LineUnavailableException {
        
        final Vector<Mixer.Info> infos = new Vector<Mixer.Info>();
        final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        
        for (final Mixer.Info mixerinfo : mixers) {
            
            if(comprobarMixer(AudioSystem.getMixer(mixerinfo))) {
                if (supportsRecording && AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0) {
                    // Mixer capable of recording audio if target LineWavelet length != 0
                    infos.add(mixerinfo);
                } else if (supportsPlayback && AudioSystem.getMixer(mixerinfo).getSourceLineInfo().length != 0) {
                    // Mixer capable of audio play back if source LineWavelet length != 0
                    infos.add(mixerinfo);
                }
            }
        }
        return infos;
    }
    
    private boolean comprobarMixer(Mixer mixer) throws LineUnavailableException {
            
            boolean validar = true;
            final AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
		final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
		
            
            try {
                mixer.getLine(dataLineInfo);
            }
            catch (SecurityException | LineUnavailableException | IllegalArgumentException e) {
                validar = false;
            }
            
            return validar;
            
        }
    
    public ArrayList getInputs() throws LineUnavailableException {
        
        ArrayList inputs = new ArrayList();
        
        for (final Mixer.Info mixerinfo : entradas) {
            
            inputs.add(mixerinfo.toString());

        }
        return inputs;
        
        
    }
    
    public ArrayList getAlgos() {
        
        ArrayList al = new ArrayList();
        
        for(int i = 0; i < algos.size(); i++) {
            
            al.add(algos.get(i));
        }
        
        return al;
        
    }

    public boolean cambiarSettings(String input, String algo) {
        
        boolean okInput = false;
        boolean okAlgo = false;
        
        for(int i = 0; i < algos.size(); i++) {
            
            PitchEstimationAlgorithm alg = algos.get(i);
            
            if(alg.toString().equals(algo)) {
                this.algo = alg;
                okAlgo = true;
                break;
            }
            
            
        }
        
        for(int i = 0; i < entradas.size(); i++) {
            
            Mixer mix = (Mixer) AudioSystem.getMixer(entradas.get(i));
            
            if(mix.getMixerInfo().toString().equals(input)) {
                mixer = mix;
                okInput = true;
                break;
            }
            
        }
        
        System.out.println("SETTINGS @ Cambiar: ALGO = " + okAlgo);
        System.out.println("SETTINGS @ Cambiar: MIXER = " + okInput);
        
        return (okInput && okAlgo);
        
        
    }
    
    
}
