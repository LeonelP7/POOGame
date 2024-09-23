/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author ASUS TUF
 */
public class GamePanel extends JPanel implements Runnable {

    //configuracion de pantalla
    private final int originalTileSize = 16; //16x16 tile
    private final int scale = 3;

    //tamaño de las imagenes en pantalla
    private final int tileSize = originalTileSize * scale; //48x48 tile

    //tamaño de la pantalla
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; //768 pixels
    private final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //FPS
    private int fps = 60;

    private Thread gameThread;
    private KeyHandler keyH = new KeyHandler();
    private Player player = new Player(this, keyH);

    //estableciendo la posicion por defecto del jugador
    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight()));
        this.setBackground(Color.BLACK);

        //mejora el rendimiento a la hora de renderizar
        this.setDoubleBuffered(true);

        //añadiendo el keyHandler
        this.addKeyListener(keyH);
        //con esto hacemos que el GamePanel se enfoque en recibir las entradas de teclado
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                //1 Actualizar; actualizar informacion como la posicion del personaje
                update();

                //2 Dibujar: dibujar la pantalla con la informacion actualizada
                repaint();
                
                delta--;
                drawCount++;
            }
            
            //este if y sus variables son para ver los fps
            if(timer >= 1000000000){
                System.out.println("FPS: "+drawCount);
                timer = 0;
                drawCount = 0;
            }

        }
    }

    public void update() {
        
        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);

        g2.dispose();
    }

    public int getOriginalTileSize() {
        return originalTileSize;
    }

    public int getScale() {
        return scale;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

}
