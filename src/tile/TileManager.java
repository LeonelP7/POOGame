/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class TileManager {
    
    private GamePanel gp;
    //array que contiene los distintos tipos de tiles
    private Tile[] tile;
    
    private int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.getMaxWorldCol()][gp.getMaxWorldRow()];
        
        getTileImage();
        loadMap("/maps/world01.txt");
    }
    
    public void getTileImage(){
        
        try{
            
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")));
            
            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].setCollision(true);
            
            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].setCollision(true);
            
            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png")));
            
            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].setCollision(true);
            
            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png")));
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void loadMap(String filePath){
        
        try {
            /*
            esto es una forma de leer un archivo de texto
            con el InputStream lo "importamos" y con el BufferedReader lo leemos
            */
            
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while(col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()){
                
                String line = br.readLine();
                while(col < gp.getMaxWorldCol()){
                    
                    //line.split(" ") separa la linea segun el parametro que se le proporcione 
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    
                    col++;
                }
                if(col == gp.getMaxWorldCol()){
                    col = 0;
                    row++;
                }
            }
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2){
        
        int worldCol = 0;
        int worldRow = 0;
        
        while(worldCol < gp.getMaxWorldCol() && worldRow < gp.getMaxWorldRow()){
            
            int tileNumber = mapTileNum[worldCol][worldRow];
            
            int worldX = worldCol*gp.getTileSize();
            int worldY = worldRow*gp.getTileSize();
            
            //donde se van a dibujar las imagenes en la plantalla
            int screenX = worldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
            int screenY = worldY - gp.getPlayer().getWorldY() + gp.getPlayer().getScreenY();
            
            if(worldX + gp.getTileSize()> gp.getPlayer().getWorldX()-gp.getPlayer().getScreenX() &&
                    worldX -  gp.getTileSize() < gp.getPlayer().getWorldX()+gp.getPlayer().getScreenX() &&
                    worldY +  gp.getTileSize() > gp.getPlayer().getWorldY()-gp.getPlayer().getScreenY() &&
                    worldY -  gp.getTileSize() < gp.getPlayer().getWorldY()+gp.getPlayer().getScreenY()){
                
                g2.drawImage(tile[tileNumber].getImage(), screenX, screenY,
                        gp.getTileSize(), gp.getTileSize(), null);
            }
            
            worldCol++;
            
            if(worldCol == gp.getMaxWorldCol()){
                worldCol = 0;
                worldRow++;
            }
        }
        
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Tile[] getTile() {
        return tile;
    }

    public void setTile(Tile[] tile) {
        this.tile = tile;
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public void setMapTileNum(int[][] mapTileNum) {
        this.mapTileNum = mapTileNum;
    }
    
    
    
}


