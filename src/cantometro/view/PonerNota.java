/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.view;

import cantometro.model.Alumno;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class PonerNota extends JFrame {
    
    private JPanel panel;
    private JButton aceptar_button;
    private JButton cancelar_button;
    private JLabel label;
    
    public PonerNota(Alumno a, float nota) {
        
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setTitle("Examen: " + a.getNombre() + " " + a.getApellidos());
        
        panel = new JPanel();
        label.setText("Puntuación Obtenida: " + nota + "\n¿Guardar?");
        aceptar_button = new JButton("EXAMINAR");
        cancelar_button = new JButton("CERRAR");
        
        aceptar_button.setActionCommand("EX_GUARDAR");
        cancelar_button.setActionCommand("EX_CANCELAR");
        
        aceptar_button.setVisible(true);
        cancelar_button.setVisible(true);
        
        panel.add(aceptar_button);
        panel.add(cancelar_button);
        panel.setVisible(true);
        
        this.add(panel);
        this.setVisible(true);
    
    }
    
    public void setActionListener(ActionListener al) {
        
        this.aceptar_button.addActionListener(al);
        this.cancelar_button.addActionListener(al);
        
    }
    
}
