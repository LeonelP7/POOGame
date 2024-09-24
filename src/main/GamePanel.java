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
import object.SuperObject;
import tile.TileManager;

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
    
    //Configuracion del mundo
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    //Variables que no se usaron
//    private final int worldWidth = tileSize*maxWorldCol;
//    private final int worldHeight = tileSize*maxScreenRow;

    //FPS
    private int fps = 60;

    private TileManager tileM = new TileManager(this);
    private KeyHandler keyH = new KeyHandler();
    private Sound music = new Sound();
    private Sound se = new Sound();
    private CollisionChecker cChecker = new CollisionChecker(this);
    private AssetSetter aSetter = new AssetSetter(this);
    private UI ui = new UI(this);
    private Thread gameThread;
    
    //Entidad y objetos
    private Player player = new Player(this, keyH);   
    //este array basicamente indica la cantidad de objetos distintos que pueden haber en el mundo
    private SuperObject obj[] = new SuperObject[10];


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
    
    public void setUpGame(){
        aSetter.setObject();
        
        //reproduce BlueBoyAdveture.wav
        playMusic(0);
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
//            if(timer >= 1000000000){
//                System.out.println("FPS: "+drawCount);
//                timer = 0;
//                drawCount = 0;
//            }

        }
    }

    public void update() {
        
        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //Tiles
        tileM.draw(g2);
        
        //objetos
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        
        //jugador
        player.draw(g2);
        
        //UI
        ui.draw(g2);

        g2.dispose();
    }
    
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void stopMusic(){
        music.stop();;
    }
    
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

            
    
            
            
            
            
            
            
    //GETTERS Y SETTERS        
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

    public int getMaxWorldCol() {
        return maxWorldCol;
    }

    public int getMaxWorldRow() {
        return maxWorldRow;
    }

//    public int getWorldWidth() {
//        return worldWidth;
//    }
//
//    public int getWorldHeight() {
//        return worldHeight;
//    }

    public Player getPlayer() {
        return player;
    }

    public KeyHandler getKeyH() {
        return keyH;
    }

    public CollisionChecker getcChecker() {
        return cChecker;
    }

    public void setcChecker(CollisionChecker cChecker) {
        this.cChecker = cChecker;
    }

    public TileManager getTileM() {
        return tileM;
    }

    public void setTileM(TileManager tileM) {
        this.tileM = tileM;
    }

    public SuperObject[] getObj() {
        return obj;
    }

    public void setObj(SuperObject[] obj) {
        this.obj = obj;
    }

    public Sound getSound() {
        return music;
    }

    public void setSound(Sound sound) {
        this.music = sound;
    }

    public Sound getSe() {
        return se;
    }

    public void setSe(Sound se) {
        this.se = se;
    }

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }
    
    
    
    
    

}
