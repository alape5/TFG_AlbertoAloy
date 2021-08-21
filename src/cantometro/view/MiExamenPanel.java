/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class MiExamenPanel extends JPanel {
        
    public final int LIMITE_HORIZONTAL = 629;

    private ArrayList<Double> miPattern = new ArrayList<Double>();
    private ArrayList<Double> timingQs = new ArrayList<Double>();
    private ArrayList<Double> startQs = new ArrayList<Double>();
    private int tolerance = 40; // by default
    private Double totalQs = 0.0;

    /**************************************************************************/
    public double patternLengthInSeconds;//in seconds
    public double currentMarker;
    public int x; 
    private double score;
    private double tpqn; // Time per Quarter Note

    private ArrayList<Double> timing = new ArrayList<Double>();
    private ArrayList<Double> pattern = new ArrayList<Double>();


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
    ArrayList<Double> startTimeStamps = new ArrayList();
    ArrayList<Double> freqs = new ArrayList();
    ArrayList<Double> iStart = new ArrayList();
    /**
     * @param timing*
     * @param pattern***********************************************************************/

    public MiExamenPanel(ArrayList timing, ArrayList pattern, float bpm) {
        
        this.timing = timing;
        this.pattern = pattern;

        this.setPreferredSize(new java.awt.Dimension(630, 450));

        for(int i = 0; i < pattern.size(); i++) {
            this.miPattern.add(htop((double)this.pattern.get(i)));
        }

        //System.out.println("MiExamenCreado ---- PATTERN SIZE: " + miPattern.size());

        this.timingQs = timing;
        tpqn = 60/(double)bpm; // time (in seconds) per quarter note

        // Calculamos el total de quarter Notes
        for(int i = 0; i < timingQs.size(); i++) {
            totalQs += timingQs.get(i);
        }

        // Calculamos los Quarter de inicio
        startQs.add(0.0);
        for(int i = 1; i < timingQs.size(); i++) {
            startQs.add(startQs.get(i-1) + timingQs.get(i-1));
        }
        
        // Calculamos los ideal TimeStamps de START
        iStart.add(0.0);
        for(int i = 1; i < timing.size(); i++) {
/*
            System.out.println("Calculadora de Timestamps: " + i);
            System.out.println("iStart[i-1] = " + iStart.get(i-1) + "\t" + "timing[i-1] = " + timing.get(i-1) + "\t" + "TPQN = " + tpqn);
*/
            iStart.add(iStart.get(i-1) + (double)timing.get(i-1) * tpqn);


        }


    }

    @Override
    public void paint(final Graphics gg) {

        final Graphics2D g = (Graphics2D) gg;

        g.setColor(new Color(255, 255, 204));
        g.fillRect(0, 0, 629, 449);
        g.setColor(Color.red);

        // Pasamos a calcular los inicios de las notas
        double ppqn = 630/totalQs;

        g.setColor(Color.GRAY);

        for(int i = 0; i < miPattern.size(); i++) {

            int x = (int) (startQs.get(i) * ppqn);
            int y = iv(miPattern.get(i));
            int anchoRect = (int) (timingQs.get(i) * ppqn);
            int altoRect = tolerance;

            if(i == miPattern.size() - 1) {

                g.fillRect(x, y, anchoRect-1, altoRect);
                g.setColor(Color.BLACK);
                g.drawRect(x,y,anchoRect-1, altoRect);
            }

            else {

                g.fillRect(x,y,anchoRect, altoRect);
                g.setColor(Color.BLACK);
                g.drawRect(x,y,anchoRect, altoRect);
            }

            g.setColor(Color.GRAY);
        }
        
        /*********** SECCION DEL INPUT DEL ALUMNO ************/
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(1));
        for(int i = 0; i < freqs.size(); i++) {
            
            if(freqs.get(i) != -1) {
                int y_nota = (int) invHtop(freqs.get(i));
                int x_nota = (int)(((startTimeStamps.get(i))/patternLengthInSeconds) *this.LIMITE_HORIZONTAL);
                g.fillRect(x_nota,y_nota+2, 4, 4);
            }
            
        }
        

        /*********** SECCION DEL MARKER ************/
        x = (int) currentMarker;
        
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(4));
        g.drawLine(x,   0, x,   445);



    }

    // hertzs to pixels
    public double htop(Double hz) {
        return ((hz-190)/(790-190)) * 450;
    }
    
    public double invHtop(Double hz) {
        return ((1-(hz-190)/(790-190))) * 450;
    }

    // Función para invertir el eje de los pitches
    public int iv(Double pitch) {

        return (int) (450 - pitch);


    }

    public void setMarker(double timeStamp,double frequency){
        
        currentMarker = (timeStamp / patternLengthInSeconds) * this.LIMITE_HORIZONTAL+1;

        /*
            Ignorar cualquier frecuencia fuera de rango
            En este caso también conservamos la frecuencia -1 (no detección)
            ya que la interpretaremos como silencio.
        */
        if((frequency > 190 && frequency < 790) || frequency == -1){

            freqs.add(frequency);
            startTimeStamps.add(timeStamp);
        }

        this.repaint();
    }

    public void setPl(double seconds) {

        this.patternLengthInSeconds = seconds;
        System.out.println("PATTERN LENGTH CAMBIADO: " + seconds);
    }
    
    public void resetCurrentMarker() {
        this.currentMarker = 0;
    }
    
    public float score(){
                
        score = 0.0;

        int j = 0; // índice para controlar el acceso al vcector timing

        for(int i = 0; i < startTimeStamps.size(); i++) {

            double frecuency = freqs.get(i);

            //System.out.println("PARA J = " + j + " Muestra: " + frecuency + "\t" + "Desvío: " + (frecuency-pattern.get(j)));

            // Si la frecuencia entra dento de la tolerancia la contaremos como válida
            //if(Math.abs(frecuency-pattern.get(j)) < HERTZ_DEVIATION)
            if(Math.abs(frecuency-pattern.get(j)) < tolerance)
            score++;

            /*
                Se comprueba el siguiente timestamp
                Protección limites del vector
            */
            if((i+1) < startTimeStamps.size() && (j+1) < timing.size()) {

                /*
                    Para cambiar de iStart --> Con esto obtendremos la frecuencia ideal siguiente
                    ya que tenemos el incide J para movernos tanto dentro de iStart como pattern 
                    que contiene la frecuencia deseada.
                */

                if(startTimeStamps.get(i+1) >= iStart.get(j+1)) {
                    j++;
                }
            }
        }

/*       
            System.out.println("COMPROBACION FINALIZADA:");
            System.out.println("Número de muestras: " + startTimeStamps.size() + "\t" + "Muestras válidas: " + score + "\t" + "Nota sobre 10: " + ((double)(score*10)/(double)startTimeStamps.size()));
*/      
        score = (double)(score*10)/(double)startTimeStamps.size();
        
        return (float) score;

    }
    
    public void setTolerance(int tolerance) {
        
        this.tolerance = tolerance;
        
    }
    
    public void reset() {
        this.score = 0;
        this.startTimeStamps.clear();
        this.freqs.clear();
        this.currentMarker = 0;
        this.repaint();
    }




}
