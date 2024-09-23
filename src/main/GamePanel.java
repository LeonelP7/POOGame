/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

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
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;

    //tamaño de las imagenes en pantalla
    final int tileSize = originalTileSize * scale; //48x48 tile

    //tamaño de la pantalla
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768 pixels
    final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //FPS
    private int fps = 60;

    private Thread gameThread;
    private KeyHandler keyH = new KeyHandler();

    //estableciendo la posicion por defecto del jugador
    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
        //en java la esquina superior izquierda es x:0 y:0
        if (keyH.isUpPressed()) {
            playerY -= playerSpeed;
        } else if (keyH.isDownPressed()) {
            playerY += playerSpeed;
        } else if (keyH.isLeftPressed()) {
            playerX -= playerSpeed;
        } else if (keyH.isRightPressed()) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }

}
