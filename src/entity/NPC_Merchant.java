/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import main.GamePanel;
import object.OBJ_Key;
import object.OBJ_PotionRed;

/**
 *
 * @author ASUS TUF
 */
public class NPC_Merchant extends Entity{
    
    public NPC_Merchant(GamePanel gp, int col, int row) {
        super(gp, col, row);
        direction = "down";
        speed = 1;
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width=32;
        solidArea.height = 32;
        

        getNpcImage();
        setDialogues();
        setItems();
                
    }
    
    public void getNpcImage() {
        up1 = setUp("/npc/merchant_down_1",gp.getTileSize(),gp.getTileSize());
        up2 = setUp("/npc/merchant_down_2",gp.getTileSize(),gp.getTileSize());
        down1 = setUp("/npc/merchant_down_1",gp.getTileSize(),gp.getTileSize());
        down2 = setUp("/npc/merchant_down_2",gp.getTileSize(),gp.getTileSize());
        left1 = setUp("/npc/merchant_down_1",gp.getTileSize(),gp.getTileSize());
        left2 = setUp("/npc/merchant_down_2",gp.getTileSize(),gp.getTileSize());
        right1 = setUp("/npc/merchant_down_1",gp.getTileSize(),gp.getTileSize());
        right2 = setUp("/npc/merchant_down_2",gp.getTileSize(),gp.getTileSize());
    }

    public void setDialogues() {

        dialogues[0] = "Mmm... conque me has encontrado...\nTengo algunas cosas que te podrian ser utiles... \nSi tienes dinero claro.";
        dialogues[1] = "Contempla, cabeza de chorlito.";
        
        maxDialogueIndex = 1;

    }

    @Override
    public void speak() {
        super.speak();
        
        gp.setGameState(gp.getTradeState());
        gp.getUi().setNpc(this);
    }
    
    

    @Override
    public void setItems() {
        
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_PotionRed(gp));
//        inventory.add(new OBJ_Key(gp));
//        inventory.add(new OBJ_Key(gp));
    }
    
    
    
}
