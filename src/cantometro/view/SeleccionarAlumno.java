/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.view;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class SeleccionarAlumno extends JFrame {
    
    private JPanel panel;
    private JComboBox cb_comboBox;
    private JButton file_button;
    private JButton volver_button;
    
    public SeleccionarAlumno() {
        
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setTitle("Cantometro: Seleccionar alumno");
        
        panel = new JPanel();
        file_button = new JButton("EXAMINAR");
        volver_button = new JButton("CERRAR");
        cb_comboBox = new JComboBox();
        
        file_button.setActionCommand("SA_EXAMINAR");
        volver_button.setActionCommand("SA_CERRAR");
        
        cb_comboBox.setVisible(true);
        file_button.setVisible(true);
        volver_button.setVisible(true);
        
        panel.add(cb_comboBox);
        panel.add(file_button);
        panel.add(volver_button);
        panel.setVisible(true);
        
        this.add(panel);
        
    
    }
    
    public void setActionListener(ActionListener al) {
        this.file_button.addActionListener(al);
        this.volver_button.addActionListener(al);
    }
    
    public void populateComboBox(ArrayList alumnos) {
        
        for(int i = 0; i < alumnos.size(); i++) {
            cb_comboBox.addItem(alumnos.get(i));
        }
        
        
        
    }
    
    public String getAlumnoSeleccionado() {
        
        return cb_comboBox.getSelectedItem().toString();
        
    }
    
    
}
