/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cantometro.main;

import cantometro.controller.Controller;
import cantometro.model.Model;
import cantometro.view.View;

/**
 *
 * @author Alberto Aloy Pérez
 */
public class Cantometro {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        View v = new View();
        Model m = new Model();
        Controller c = new Controller(v, m);
    
    }
    
}
