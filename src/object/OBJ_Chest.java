/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class OBJ_Chest extends Entity{
    
    private Entity loot;
    private boolean opened;
    
    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.loot = loot;
        setItemValues();
    }

    public OBJ_Chest(GamePanel gp, int col, int row, Entity loot) {
        super(gp, col, row);
        this.loot = loot;
        setItemValues();
    }
    
    @Override
    public void setItemValues() {
        type = type_obstacle;
        name = "chest";
        image = setUp("/objects/chest",gp.getTileSize(),gp.getTileSize());
        image2 = setUp("/objects/chest_opened",gp.getTileSize(),gp.getTileSize()); 
        down1 = image;
        opened = false;
        collision = true;
        
        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void interact() {
        
        gp.setGameState(gp.getDialogueState());
        
        if(!opened){
            gp.playSE(3);
            
            StringBuilder sb = new StringBuilder();
            sb.append("Abres el cofre y encuentras " + loot.getName() + "!");
            
            if(!gp.getPlayer().canObtainItem(loot)){
                sb.append("\n...Pero ya no puedes llevar mas!");
            }
            else {
                sb.append("\nHas obtenido " + loot.getName() + "!");
                down1 = image2;
                opened = true;
            }
            gp.getUi().setCurrentDialogue(sb.toString());
        }
        else{
            gp.getUi().setCurrentDialogue("Esta vacio pelao");
        }
    }
    
    
    
    
    
    
    
}
