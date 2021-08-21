/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.view;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author alberto
 */
public class NewProfe extends javax.swing.JFrame {
    
    public NewProfe() {
        
        initComponents();
        this.setTitle("Cantometro: Alta Profesor");
        this.setLocationRelativeTo(null);
        
    }
    
    public void setActionListener(ActionListener al) {
        
        this.registrar_button.addActionListener(al);
        this.cerrar_button.addActionListener(al);
        
    }
    
    public ArrayList getNewProfeFields() {
        
        ArrayList al = new ArrayList();
        
        al.add(this.user_textField.getText());
        al.add(this.password_passwordField.getText());
        
        return al;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        user_textField = new javax.swing.JTextField();
        password_passwordField = new javax.swing.JPasswordField();
        Label_Usuario = new javax.swing.JLabel();
        Label_Contraseña = new javax.swing.JLabel();
        registrar_button = new javax.swing.JButton();
        Label_NUEVOPROFE = new javax.swing.JLabel();
        cerrar_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Label_Usuario.setText("Usuario:");

        Label_Contraseña.setText("Contraseña: ");

        registrar_button.setText("REGISTRAR");
        registrar_button.setToolTipText("");
        registrar_button.setActionCommand("NP_REGISTRAR");

        Label_NUEVOPROFE.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        Label_NUEVOPROFE.setText("NUEVO PROFESOR");

        cerrar_button.setText("VOLVER");
        cerrar_button.setActionCommand("NP_VOLVER");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(61, Short.MAX_VALUE)
                        .addComponent(Label_NUEVOPROFE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap(70, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Label_Usuario)
                                    .addComponent(Label_Contraseña))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cerrar_button)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(registrar_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(password_passwordField)
                            .addComponent(user_textField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(Label_NUEVOPROFE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(user_textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Usuario))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Contraseña))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registrar_button)
                    .addComponent(cerrar_button))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label_Contraseña;
    private javax.swing.JLabel Label_NUEVOPROFE;
    private javax.swing.JLabel Label_Usuario;
    private javax.swing.JButton cerrar_button;
    private javax.swing.JPasswordField password_passwordField;
    private javax.swing.JButton registrar_button;
    private javax.swing.JTextField user_textField;
    // End of variables declaration//GEN-END:variables
}
