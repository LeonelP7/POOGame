/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class Map extends TileManager {

    private BufferedImage worldMap[];
    private boolean miniMapOn;

    public Map(GamePanel gp) {
        super(gp);
        miniMapOn = false;
        createWorldMap();
    }

    public void createWorldMap() {

        worldMap = new BufferedImage[gp.getMaxMap()];
        int worldMapWidth = gp.getTileSize() * gp.getMaxWorldCol();
        int worldMapHeight = gp.getTileSize() * gp.getMaxWorldRow();

        for (int i = 0; i < gp.getMaxMap(); i++) {

            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();

            int col = 0;
            int row = 0;
            int x = 0;
            int y = 0;

            while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {

                int tileNum = mapTileNum[i][col][row];
                x = gp.getTileSize() * col;
                y = gp.getTileSize() * row;
                g2.drawImage(tile[tileNum].getImage(), x, y, null);

                col++;
                if (col == gp.getMaxWorldCol()) {
                    col = 0;
                    row++;
                }

            }
        }

    }

    public void drawFullMapScreen(Graphics2D g2) {

        //color del fondo
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        //dibuja el mapa
        int width = 500;
        int height = 500;
        int x = gp.getScreenWidth() / 2 - width / 2;
        int y = gp.getScreenHeight() / 2 - height / 2;
        g2.drawImage(worldMap[gp.getCurrentMap()], x, y, width, height, null);

        //dibujar jugador
        double scale = (double) (gp.getTileSize() * gp.getMaxWorldCol()) / width;
        int playerX = (int) (x + gp.getPlayer().getWorldX() / scale);
        int playerY = (int) (y + gp.getPlayer().getWorldY() / scale);
        int playerSize = (int) (gp.getTileSize() / scale);
        g2.drawImage(gp.getPlayer().getDown1(), playerX, playerY, playerSize, playerSize, null);

        //sugerencia
        g2.setFont(gp.getUi().getMaruMonica().deriveFont(32f));
        g2.setColor(Color.WHITE);
        g2.drawString("M para salir", 750, 550);
    }

    public void drawMiniMap(Graphics2D g2) {

        if (miniMapOn) {

            //dibuja map;
            int width = 200;
            int height = 200;
            int x = gp.getScreenWidth() - width - 50;
            int y = 50;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.drawImage(worldMap[gp.getCurrentMap()], x, y, width, height, null);

            //dibuja jugador
            double scale = (double) (gp.getTileSize() * gp.getMaxWorldCol()) / width;
            int playerX = (int) (x + gp.getPlayer().getWorldX() / scale);
            int playerY = (int) (y + gp.getPlayer().getWorldY() / scale);
            int playerSize = (int) (gp.getTileSize() / 3);
            g2.drawImage(gp.getPlayer().getDown1(), playerX-6, playerY-6, playerSize, playerSize, null);
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
    
    //GETTERS Y SETTERS

    public boolean isMiniMapOn() {
        return miniMapOn;
    }

    public void setMiniMapOn(boolean miniMapOn) {
        this.miniMapOn = miniMapOn;
    }

}
