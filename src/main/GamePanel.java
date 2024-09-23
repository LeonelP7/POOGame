/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author ASUS TUF
 */
public class GamePanel extends JPanel{
    
    //configuracion de pantalla
    
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3; 
    
    //tamaño de las imagenes en pantalla
    final int tileSize = originalTileSize*scale; //48x48 tile
    
    //tamaño de la pantalla
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize*maxScreenCol; //768 pixels
    final int screenHeight = tileSize*maxScreenRow; //576 pixels

    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        //mejora el rendimiento a la hora de renderizar
        this.setDoubleBuffered(true);
    }
    
    
    
}
