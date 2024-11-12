/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;

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
        type = 2;
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        
        getMonImage();
    }
    
    public void getMonImage(){
        up1 = setUp("/monster/greenslime_down_1");
        up2 = setUp("/monster/greenslime_down_2");
        down1 = setUp("/monster/greenslime_down_1");
        down2 = setUp("/monster/greenslime_down_2");
        left1 = setUp("/monster/greenslime_down_1");
        left2 = setUp("/monster/greenslime_down_2");
        right1 = setUp("/monster/greenslime_down_1");
        right2 = setUp("/monster/greenslime_down_2");
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
    
    
    
}
