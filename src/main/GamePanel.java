/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import util.ComparatorByWorldY;
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
import tiles_interactive.InteractiveTile;

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
    private final int maxScreenCol = 20;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol; //960 pixels
    private final int screenHeight = tileSize * maxScreenRow; //576 pixels
    
    //para pantalla completa
    private int screenWidth2 = screenWidth;
    private int screenHeight2 = screenHeight;

    //Configuracion del mundo
    private final int maxWorldCol = 50;
    private final int maxWorldRow = 50;
    private final int maxMap = 10;
    private int currentMap = 0;

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
    private Config config = new Config(this);

    //Entidad y objetos
    private Player player = new Player(this, keyH);
    private Entity obj[][] = new Entity[maxMap][20];//este vector contiene los objetos distintos que pueden haber en el mundo
    private Entity npc[][] = new Entity[maxMap][10];//este vector contiene los npcs distintos que pueden haber en el mundo
    private Entity monster[][] = new Entity[maxMap][20];
    private InteractiveTile iTile[][] = new  InteractiveTile[maxMap][50];
    private ArrayList<Entity> entityList = new ArrayList<>();
    private ArrayList<Entity> particleList = new ArrayList<>();

    //GAME STATE(estado del juego)
    private int gameState;
    private final int titleState = 0;
    private final int playState = 1;
    private final int pauseState = 2;
    private final int dialogueState = 3;
    private final int characterState = 4;
    private final int optionState = 5;
    private final int gameOverState = 6;

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
        aSetter.setInteractiveTile();

        //reproduce BlueBoyAdveture.wav
        //playMusic(0);
        gameState = titleState;
        
    }
    
    public void retry(){
        
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setNPC();
        aSetter.setMoster();
        aSetter.setInteractiveTile();
    }
    
    public void restart(){
        player.setDefaultValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMoster();
        aSetter.setInteractiveTile();
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
                //1 Actualizar: actualizar informacion como la posicion del personaje
                update();

                //2 Dibujar: dibujar en la bufferedImage(tempScreen) con la informacion actualizada
                repaint();
                //drawToScreen(); // dibuja la bufferedImage(tempScreen) en la pantalla

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
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            
            //monster
            for (int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null){
                    if (monster[currentMap][i].isAlive() && !monster[currentMap][i].isDying()) {
                        monster[currentMap][i].update();
                    }  
                    if (!monster[currentMap][i].isAlive()) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i]=null;
                    } 
                }
            }
            
            //particulas
            for (int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null){
                    if(particleList.get(i).isAlive()){
                        particleList.get(i).update();
                    }
                    
                    if(!particleList.get(i).isAlive()){
                        particleList.remove(i);
                    }
                }
            }
            
            /*
            vector[1].lenght
            con eso le decimos a java que utilice la longitud de la segunda dimension
            de un vector de 2 dimensiones
            */
            
            //tiles interactivos
            for (int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
        }
        if (gameState == pauseState) {
            //nada por ahora
        }
        
        if(gameState == titleState){
            //nada por ahora
            
        }

    }
    
    @Override
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
            
            for (int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }
            
            //agregando las entidades a la lista
            entityList.add(player);
            
            for (int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i]!=null){
                    entityList.add(npc[currentMap][i]);
                }
            }
            
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i]!=null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i]!=null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
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

    public Entity[][] getNpc() {
        return npc;
    }

    public void setNpc(Entity[][] npc) {
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

    public Entity[][] getObj() {
        return obj;
    }

    public void setObj(Entity[][] obj) {
        this.obj = obj;
    }

    public Entity[][] getMonster() {
        return monster;
    }

    public void setMonster(Entity[][] monster) {
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

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<Entity> entityList) {
        this.entityList = entityList;
    }

    public InteractiveTile[][] getiTile() {
        return iTile;
    }

    public void setiTile(InteractiveTile[][] iTile) {
        this.iTile = iTile;
    }

    public ArrayList<Entity> getParticleList() {
        return particleList;
    }

    public void setParticleList(ArrayList<Entity> particleList) {
        this.particleList = particleList;
    }

    public int getOptionState() {
        return optionState;
    }

    public Sound getMusic() {
        return music;
    }

    public void setMusic(Sound music) {
        this.music = music;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public int getGameOverState() {
        return gameOverState;
    }

    public int getMaxMap() {
        return maxMap;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    
    
    
    
}
