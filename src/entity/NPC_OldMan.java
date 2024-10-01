/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Random;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {

        super(gp);
        direction = "down";
        speed = 1;

        getImage();
        setDialogues();
    }

    public void getImage() {

        up1 = setUp("/npc/oldman_up_1");
        up2 = setUp("/npc/oldman_up_2");
        down1 = setUp("/npc/oldman_down_1");
        down2 = setUp("/npc/oldman_down_2");
        left1 = setUp("/npc/oldman_left_1");
        left2 = setUp("/npc/oldman_left_2");
        right1 = setUp("/npc/oldman_right_1");
        right2 = setUp("/npc/oldman_right_2");
    }

    public void setDialogues() {

        dialogues[0] = "Holas, patatudo.";
        dialogues[1] = "Entonces... sigues aqui.";
        dialogues[2] = "Asumo que has venido en \nbusqueda de tesoros patatudo.";
        dialogues[3] = "Yo una vez tuve un sueño similar... \nhasta que me dio una flecha en la rodilla.";
        dialogues[4] = "Buena suerte entonces cabeza de chorlito!";

    }

    @Override
    public void speak() {

        super.speak();
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