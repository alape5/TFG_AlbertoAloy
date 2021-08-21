/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.view;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import cantometro.model.Alumno;
import cantometro.model.Cancion;
import cantometro.model.Settings;
import static cantometro.model.Examen.NOTE_OFF;
import static cantometro.model.Examen.NOTE_ON;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class Examen_v extends JFrame implements PitchDetectionHandler {
    
    public ArrayList<Double> pattern = new ArrayList<Double>();
    public ArrayList<Double> timing = new ArrayList<Double>();
    
    /***********************************************/
    
    private Settings settings;
    private Alumno alumno;
    private Cancion cancion;
    
    private Mixer mixer = null;
    private PitchProcessor.PitchEstimationAlgorithm algo = null;
    private Thread th = null;
    private AudioDispatcher dispatcher;
    TargetDataLine line = null;
    
    private ActionListener miActionListener;
    
    /***********************************************/
    public Examen_v(Alumno alumno, String cancion) throws InvalidMidiDataException, IOException, MidiUnavailableException, LineUnavailableException {
        
        this.settings = new Settings();
        this.alumno = alumno;
        this.cancion = operarMidi(cancion);
        
        this.pattern = this.cancion.getPatternHz();
        this.timing = this.cancion.getTimingQn();
        this.mixer = settings.getMixer();
        this.algo = settings.getAlgorithm();
        
        initComponents();
        this.examenPanel_panel.setPl(this.cancion.getLengthInSecs());
        this.examenPanel_panel.setTolerance(this.alumno.getTolerance());
        
        
        this.setTitle("Cantometro: Examen --- " + this.alumno.getApellidos() + ", " + this.alumno.getNombre());
        this.setLocationRelativeTo(null);
        
    }
    
    public void setActionListener(ActionListener al) {
        
        this.settings_button.addActionListener(al);
        this.cerrar_button.addActionListener(al);
        this.start_button.addActionListener(al);
        
        this.miActionListener = al;
        
    }
    
    public void empezarPararExamen() {
        
        String texto = start_button.getText();
        if(texto.equals("START")) {
            start_button.setText("STOP");
            setAlgo();
            th.start();
        }
            
        else {
            start_button.setText("START");
            th.stop();
            
            JOptionPane.showMessageDialog(null, "Examen finalizado\nPuntuación obtenida: " + examenPanel_panel.score(), "Cantómetro", JOptionPane.INFORMATION_MESSAGE);
            
            this.alumno.setNota(examenPanel_panel.score());
            
            JOptionPane.showMessageDialog(null, "Nota guardada con éxito, puede repetir el examen o cerrar la ventana.", "Cantómetro", JOptionPane.INFORMATION_MESSAGE);
            
            examenPanel_panel.reset();
            th = null;
            line.stop();
            line.close();
            dispatcher.stop();
            
        }
            
        
    }
    
    public Cancion operarMidi(String fichero) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        
        File ficheroMidi = new File(fichero);
        
        ArrayList noteOn = new ArrayList();
        ArrayList noteOff = new ArrayList();
        ArrayList keyNote = new ArrayList();
        float tempo = 0;
        int resolucion = 0;
        
        System.out.println("FUNC(OperarMidi): OK - Fichero recibido: " + ficheroMidi.toString());
        
        Sequence sequence = MidiSystem.getSequence(ficheroMidi);
        System.out.println("FUNC(OperarMidi): Sequence: " + sequence.toString());
        System.out.println("FUNC(OperarMidi): Tracks " + sequence.getTracks().length);
        System.out.println("FUNC(OperarMidi): Tipo de división: " + calcularTipoDivision(sequence.getDivisionType()));
        System.out.println("FUNC(OperarMidi): Resolución " + sequence.getResolution());
        
        resolucion= sequence.getResolution();
        
        Sequencer seq = MidiSystem.getSequencer();
        seq.setSequence(sequence);
        
        tempo = seq.getTempoInBPM();
        System.out.println("FUNC(OperarMidi): TEMPO " + tempo);
        
        seq.close();
        
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) { 
                MidiEvent event = track.get(i);
                //System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        noteOn.add(event.getTick());
                        keyNote.add(key);
                        
                        
                    } else if (sm.getCommand() == NOTE_OFF) {
                        noteOff.add(event.getTick());
                      
                        
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }
        
        System.out.println("MODEL @ OperarFicheroMidi: Fichero midi operado OK -- Generando Objeto Canción");
        Cancion c = new Cancion(tempo, noteOn, noteOff, keyNote, resolucion);
        if(c != null)
            System.out.println("MODEL @ OperarFicheroMidi: Fichero midi operado OK -- Generando Objeto Canción");
        
        return c;
        
        
    }
    
    public String calcularTipoDivision(float tipo) {
        
        String tipoDivision = "";
        
        if(Sequence.PPQ == tipo) tipoDivision = "PPQ";
        if(Sequence.SMPTE_24 == tipo) tipoDivision = "SMPTE_24";
        if(Sequence.SMPTE_25 == tipo) tipoDivision = "SMPTE_25";
        if(Sequence.SMPTE_30 == tipo) tipoDivision = "SMPTE_30";
        if(Sequence.SMPTE_30DROP == tipo) tipoDivision = "SMPTE_30DRP";
        
        return tipoDivision;
    }
    
    public ArrayList getInputs() throws LineUnavailableException {
        return settings.getInputs();
    }
    
    public ArrayList getAlgos() {
        return settings.getAlgos();
    }
    
    public boolean cambiarSettings(String input, String algo) {
        return this.settings.cambiarSettings(input, algo);
    }
    
    private void setNewMixer(Mixer mixer) throws LineUnavailableException, UnsupportedAudioFileException {

        if(dispatcher!= null){
                dispatcher.stop();
        }

        System.out.println("EXAMEN @ setNewMixer() --> dispatcher parado");
        this.mixer = mixer;

        float sampleRate = 44100;
        int bufferSize = 1536;
        int overlap = 0;

        //textArea.append("Started listening with " + Funciones.toLocalString(mixer.getMixerInfo().getName()) + "\n\tparams: " + threshold + "dB\n");

        final AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
        final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);


        System.out.println("EXAMEN @ setNewMixer() --> Se intenta conseguir un dataline info");
        this.line = (TargetDataLine) mixer.getLine(dataLineInfo);
        System.out.println("EXAMEN @ setNewMixer() --> dataline info conseguido");
        final int numberOfSamples = bufferSize;
        line.open(format, numberOfSamples);
        line.start();
        final AudioInputStream stream = new AudioInputStream(line);

        JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
        // create a new dispatcher
        dispatcher = new AudioDispatcher(audioStream, bufferSize, overlap);

        // add a processor, handle percussion event.
        dispatcher.addAudioProcessor(new PitchProcessor(algo, sampleRate, bufferSize, this));

        // run the dispatcher (on a new thread).
        th = new Thread(dispatcher,"Audio dispatching");
    }
    
    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
        double timeStamp = audioEvent.getTimeStamp();
        float pitch = pitchDetectionResult.getPitch();
        System.out.println("TimeStamp From ExamenV: " + timeStamp);
        
        // El examen finaliza si se excede del tiempo final de la cacnión
        if(timeStamp*1.8 > this.cancion.getLengthInSecs())
        {
            empezarPararExamen();
            System.out.println("Llamado PararExamen");
        }
        
        // Va demasiado lento y se aplica un factor de 1.8 de corrección al timeStamp
        examenPanel_panel.setMarker(timeStamp*1.8, pitch);
        
    }
    
    public void setAlgo() {

        try {
            System.out.println("EXAMEN @ setAlgo --> Se llama a la función setNewMixer()");
            setNewMixer(mixer);
        } catch (LineUnavailableException | UnsupportedAudioFileException e1) {
            System.out.println("EXCEPCION");    
            e1.printStackTrace();
        }

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        bottomPanel_panel = new javax.swing.JPanel();
        start_button = new javax.swing.JButton();
        topPanel_panel = new javax.swing.JPanel();
        settings_button = new javax.swing.JButton();
        cerrar_button = new javax.swing.JButton();
        centerPanel_panel = new javax.swing.JPanel();
        examenPanel_panel = new MiExamenPanel(timing, pattern, this.cancion.getTempo());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));
        setSize(new java.awt.Dimension(0, 0));

        start_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        start_button.setText("START");
        start_button.setActionCommand("EX_START");

        javax.swing.GroupLayout bottomPanel_panelLayout = new javax.swing.GroupLayout(bottomPanel_panel);
        bottomPanel_panel.setLayout(bottomPanel_panelLayout);
        bottomPanel_panelLayout.setHorizontalGroup(
            bottomPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanel_panelLayout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(start_button, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        bottomPanel_panelLayout.setVerticalGroup(
            bottomPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(start_button, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        settings_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        settings_button.setText("SETTINGS");
        settings_button.setActionCommand("EX_SETTINGS");

        cerrar_button.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cerrar_button.setText("CERRAR");
        cerrar_button.setActionCommand("EX_CERRAR");

        javax.swing.GroupLayout topPanel_panelLayout = new javax.swing.GroupLayout(topPanel_panel);
        topPanel_panel.setLayout(topPanel_panelLayout);
        topPanel_panelLayout.setHorizontalGroup(
            topPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settings_button, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(cerrar_button, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );
        topPanel_panelLayout.setVerticalGroup(
            topPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(settings_button, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(cerrar_button, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addContainerGap())
        );

        centerPanel_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("EXAMEN"));

        javax.swing.GroupLayout examenPanel_panelLayout = new javax.swing.GroupLayout(examenPanel_panel);
        examenPanel_panel.setLayout(examenPanel_panelLayout);
        examenPanel_panelLayout.setHorizontalGroup(
            examenPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        examenPanel_panelLayout.setVerticalGroup(
            examenPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout centerPanel_panelLayout = new javax.swing.GroupLayout(centerPanel_panel);
        centerPanel_panel.setLayout(centerPanel_panelLayout);
        centerPanel_panelLayout.setHorizontalGroup(
            centerPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(examenPanel_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        centerPanel_panelLayout.setVerticalGroup(
            centerPanel_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanel_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(examenPanel_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(topPanel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(centerPanel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bottomPanel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(topPanel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(centerPanel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPanel_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JPanel bottomPanel_panel;
    private javax.swing.JPanel centerPanel_panel;
    private javax.swing.JButton cerrar_button;
    private MiExamenPanel examenPanel_panel;
    private javax.swing.JButton settings_button;
    private javax.swing.JButton start_button;
    private javax.swing.JPanel topPanel_panel;


        
        
	
    
}
