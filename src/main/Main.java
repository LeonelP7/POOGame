/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.JFrame;

/**
 *
 * @author ASUS TUF
 */
public class Main {
    
    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("POOGame");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        //ajusta el tamaño de la ventana a las dimensiones de sus componentes(gamePanel)
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    
}
