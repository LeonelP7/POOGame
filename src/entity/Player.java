/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;


public class Player extends Entity {

    private GamePanel gp;
    private KeyHandler keyH;

    //variables para la posicion del jugador en la pantalla
    private final int screenX;
    private final int screenY;

    //la cantidad de llaves que tiene el jugador
    private int hasKey;
    
    //contador para actualizar el sprite si se queda quieto el personaje
    private int standCounter;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.getTileSize() * 23;
        worldY = gp.getTileSize() * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        up1 = setUp("boy_up_1");
        up2 = setUp("boy_up_2");
        down1 = setUp("boy_down_1");
        down2 = setUp("boy_down_2");
        left1 = setUp("boy_left_1");
        left2 = setUp("boy_left_2");
        right1 = setUp("boy_right_1");
        right2 = setUp("boy_right_2");
    }
    
    private BufferedImage setUp(String imageName){
        
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.getTileSize(), gp.getTileSize());
            
        } catch (IOException e) {
        }
        return image;
    }

    public void update() {

        if (keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()) {
            //en java la esquina superior izquierda es x:0 y:0
            if (keyH.isUpPressed()) {
                direction = "up";

            } else if (keyH.isDownPressed()) {
                direction = "down";

            } else if (keyH.isLeftPressed()) {
                direction = "left";

            } else if (keyH.isRightPressed()) {
                direction = "right";

            }

            //revisar la colision del tile
            collisionOn = false;
            gp.getcChecker().checkTile(this);

            //revisar la colision de objetos
            int objectIndex = gp.getcChecker().checkObject(this, true);
            pickUpObject(objectIndex);

            //si collisionOn es false, el jugador puede moverse
            if (!collisionOn) {

                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            //esto hace que el sprite cambie cada 10 frames
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;

            }
        }else{
            standCounter++;
            
            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.getObj()[i].getName();

            switch (objectName) {
                case "key":
                    gp.playSE(1);
                    hasKey++;
                    gp.getObj()[i] = null;
                    gp.getUi().showMessage("Has obtenido una llave");
                    break;
                case "door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.getObj()[i] = null;
                        hasKey--;
                        gp.getUi().showMessage("Has abierto una puerta");
                    }else{
                        gp.getUi().showMessage("Necesitas una llave!");
                    }
                    break;
                case "boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.getObj()[i] = null;
                    gp.getUi().showMessage("Eres veloz!!!");
                    break;
                case "chest":
                    gp.getUi().setGameFinished(true);
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }

        }

    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.getTileSize(), gp.getTileSize());

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getHasKey() {
        return hasKey;
    }

    public void setHasKey(int hasKey) {
        this.hasKey = hasKey;
    }

}
