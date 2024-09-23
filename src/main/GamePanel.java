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
public class GamePanel extends JPanel implements Runnable{
    
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
    
    Thread gameThread;

    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        //mejora el rendimiento a la hora de renderizar
        this.setDoubleBuffered(true);
    }

    public void startGameThread(){
        
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    
    @Override
    public void run() {
        
        while(gameThread != null){
            
            //que se hace en este loop?
            
            //1 Actualizar; actualizar informacion como la posicion del personaje
            
            //2 Dibujar: dibujar la pantalla con la informacion actualizada
        }
    }
    
    
    
}
