/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JPanel;
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

    //Sistema
    private TileManager tileM = new TileManager(this);
    private KeyHandler keyH = new KeyHandler(this);
    private Sound music = new Sound();
    private Sound se = new Sound();
    private CollisionChecker cChecker = new CollisionChecker(this);
    private AssetSetter aSetter = new AssetSetter(this);
    private EventHandler eHandler = new EventHandler(this);
    private UI ui = new UI(this);
    private Thread gameThread;

    //Entidad y objetos
    private Player player = new Player(this, keyH);
    private Entity obj[] = new Entity[10];//este vector contiene los objetos distintos que pueden haber en el mundo
    private Entity npc[] = new Entity[10];//este vector contiene los npcs distintos que pueden haber en el mundo
    private Entity monster[] = new Entity[20]; 
    private ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE(estado del juego)
    private int gameState;
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private final int characterState = 4;

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

    public void setUpGame() {

        //coloca los objetos y npcs en el mundo
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMoster();

        //reproduce BlueBoyAdveture.wav
        //playMusic(0);
        gameState = titleState;
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
        //long timer = 0;
        int drawCount = 0;

        //game loop
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            //timer += (currentTime - lastTime);
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

        if (gameState == playState) {

            //jugador
            player.update();

            //npc
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            
            //monster
            for (int i = 0; i < monster.length; i++) {
                if(monster[i] != null){
                    if (monster[i].isAlive() && !monster[i].isDying()) {
                        monster[i].update();
                    }  
                    if (!monster[i].isAlive()) {
                        monster[i]=null;
                    } 
                }
            }
        }
        if (gameState == pauseState) {
            //nada por ahora
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //DEBUG
        long drawStart = 0;
        if (keyH.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        //ESTADO: TITULO
        if (gameState == titleState) {

            ui.draw(g2);
        } //OTROS
        else {

            //dibuja los Tiles en pantalla
            tileM.draw(g2);
            
            //agregando las entidades a la lista
            entityList.add(player);
            
            for (int i = 0; i < npc.length; i++) {
                if(npc[i]!=null){
                    entityList.add(npc[i]);
                }
            }
            
            for (int i = 0; i < obj.length; i++) {
                if (obj[i]!=null) {
                    entityList.add(obj[i]);
                }
            }
            
            for (int i = 0; i < monster.length; i++) {
                if (monster[i]!=null) {
                    entityList.add(monster[i]);
                }
            }
            
            //organizar la lista de entidades
            Collections.sort(entityList, new ComparatorByWorldY());
            
            //dibuja las entidades
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();

            //dibuja la UI
            ui.draw(g2);
        }

        //DEBUG
        if (keyH.isShowDebugText()) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            
            g2.setFont(new Font("Arial",Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
            
            g2.drawString("WorldX "+ player.getWorldX(), x, y); y += lineHeight;
            g2.drawString("WorldY "+ player.getWorldY(), x, y);y += lineHeight;
            g2.drawString("Col "+ (player.getWorldX()+player.getSolidArea().x)/tileSize, x, y);y += lineHeight;
            g2.drawString("Row "+ (player.getWorldY()+player.getSolidArea().y)/tileSize, x, y);y += lineHeight;
            
            g2.drawString("Draw time: " + passed, x, y);
            System.out.println("Draw time: " + passed);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();;
    }

    public void playSE(int i) {
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

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getPlayState() {
        return playState;
    }

    public int getPauseState() {
        return pauseState;
    }

    public Entity[] getNpc() {
        return npc;
    }

    public void setNpc(Entity[] npc) {
        this.npc = npc;
    }

    public int getDialogueState() {
        return dialogueState;
    }

    public int getTitleState() {
        return titleState;
    }

    public EventHandler geteHandler() {
        return eHandler;
    }

    public void seteHandler(EventHandler eHandler) {
        this.eHandler = eHandler;
    }

    public Entity[] getObj() {
        return obj;
    }

    public void setObj(Entity[] obj) {
        this.obj = obj;
    }

    public Entity[] getMonster() {
        return monster;
    }

    public void setMonster(Entity[] monster) {
        this.monster = monster;
    }

    public int getCharacterState() {
        return characterState;
    }

    public AssetSetter getaSetter() {
        return aSetter;
    }

    public void setaSetter(AssetSetter aSetter) {
        this.aSetter = aSetter;
    }

    
    
}
