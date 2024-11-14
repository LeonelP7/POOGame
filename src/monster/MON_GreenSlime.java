/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import object.OBJ_CoinBronze;
import object.OBJ_Heart;

/**
 *
 * @author ASUS TUF
 */
public class MON_GreenSlime extends Entity{

    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        type = type_monster;
        defense = 0;
        attack = 3;
        exp = 2;
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        
        getMonImage();
    }
    
    public void getMonImage(){
        up1 = setUp("/monster/greenslime_down_1",gp.getTileSize(),gp.getTileSize());
        up2 = setUp("/monster/greenslime_down_2",gp.getTileSize(),gp.getTileSize());
        down1 = setUp("/monster/greenslime_down_1",gp.getTileSize(),gp.getTileSize());
        down2 = setUp("/monster/greenslime_down_2",gp.getTileSize(),gp.getTileSize());
        left1 = setUp("/monster/greenslime_down_1",gp.getTileSize(),gp.getTileSize());
        left2 = setUp("/monster/greenslime_down_2",gp.getTileSize(),gp.getTileSize());
        right1 = setUp("/monster/greenslime_down_1",gp.getTileSize(),gp.getTileSize());
        right2 = setUp("/monster/greenslime_down_2",gp.getTileSize(),gp.getTileSize());
    }

    @Override
    public void setAction() {
       actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; //escoge un numero al azar entre 1 y 100

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.getPlayer().getDirection();
    }

    @Override
    public void checkDrop() {
        
        //A la hora de morir
        int i = new Random().nextInt(100)+1;
        
        //Establecer lo que el suelta el monstruo
        if(i < 25){
            dropItem(new OBJ_CoinBronze(gp));
        }
        if(i >= 25 && i < 50){
            //aqui deberia de soltar pegamento
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
    }
    
    
    
}
