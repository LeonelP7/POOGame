/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.AlphaComposite;
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

    private KeyHandler keyH;

    //variables para la posicion del jugador en la pantalla
    private final int screenX;
    private final int screenY;

    //contador para actualizar el sprite si se queda quieto el personaje
    private int standCounter;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.getScreenWidth() / 2 - (gp.getTileSize() / 2);
        screenY = gp.getScreenHeight() / 2 - (gp.getTileSize() / 2);
        
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
        
        //estado del jugador
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {

        up1 = setUp("/player/boy_up_1");
        up2 = setUp("/player/boy_up_2");
        down1 = setUp("/player/boy_down_1");
        down2 = setUp("/player/boy_down_2");
        left1 = setUp("/player/boy_left_1");
        left2 = setUp("/player/boy_left_2");
        right1 = setUp("/player/boy_right_1");
        right2 = setUp("/player/boy_right_2");
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

            //revisa la colision del tile
            collisionOn = false;
            gp.getcChecker().checkTile(this);

            //revisa la colision de objetos
            int objectIndex = gp.getcChecker().checkObject(this, true);
            pickUpObject(objectIndex);

            //revisa la colision con npcs
            int npcIndex = gp.getcChecker().checkEntity(this, gp.getNpc());
            interactNPC(npcIndex);
            
            //revisa la colision con monstruos
            int monsterIndex = gp.getcChecker().checkEntity(this, gp.getMonster());
            contactMonster(monsterIndex);
            
            //revisa los eventos
            gp.geteHandler().checkEvent();
            gp.getKeyH().setEnterPressed(false);
            
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

            //esto hace que el sprite cambie cada 12 frames
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;

            }
        } else {
            standCounter++;

            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }
        
        //frames de invulnerabilidad para el jugador
        if(invincible){
            invincibleCounter++;
            if (invincibleCounter>=60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }

    }
    
    public void contactMonster(int i){
        if (i != 999){
            if (!invincible) {
                life--;
                invincible = true;
            }
            
        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (gp.getKeyH().isEnterPressed()) {
                gp.setGameState(gp.getDialogueState());
                gp.getNpc()[i].speak();
            }
        }
        
    }

    @Override
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
        
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);
        
        //restablecer alpha (opacidad)
         g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    
}
