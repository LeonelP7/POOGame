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
        mapTileNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
        
        getTileImage();
        loadMap("/maps/map01.txt");
    }
    
    public void getTileImage(){
        
        try{
            
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")));
            
            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")));
            
            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")));
            
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
            
            while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
                
                String line = br.readLine();
                while(col < gp.getMaxScreenCol()){
                    
                    //line.split(" ") separa la linea segun el parametro que se le proporcione 
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    
                    col++;
                }
                if(col == gp.getMaxScreenCol()){
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
        
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        
        while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
            
            int tileNumber = mapTileNum[col][row];
            
            g2.drawImage(tile[tileNumber].getImage(), x, y, gp.getTileSize(), gp.getTileSize(), null);
            
            col++;
            x += gp.getTileSize();
            
            if(col == gp.getMaxScreenCol()){
                col = 0;
                x = 0;
                row++;
                y += gp.getTileSize();   
            }
        }
        
    }
    
    
    
}


