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
public class OBJ_Heart extends Entity{
    
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        type = type_pickUpOnly;
        name = "key";
        value = 2;
        down1 = setUp("/objects/heart_full",gp.getTileSize(),gp.getTileSize());
        image=setUp("/objects/heart_full",gp.getTileSize(),gp.getTileSize());
        image2=setUp("/objects/heart_half",gp.getTileSize(),gp.getTileSize());
        image3=setUp("/objects/heart_blank",gp.getTileSize(),gp.getTileSize());
        
    }
    
    public OBJ_Heart(GamePanel gp, int col, int row) {
        super(gp,col,row);
        type = type_pickUpOnly;
        name = "key";
        value = 2;
        down1 = setUp("/objects/heart_full",gp.getTileSize(),gp.getTileSize());
        image=setUp("/objects/heart_full",gp.getTileSize(),gp.getTileSize());
        image2=setUp("/objects/heart_half",gp.getTileSize(),gp.getTileSize());
        image3=setUp("/objects/heart_blank",gp.getTileSize(),gp.getTileSize());
        
    }

    @Override
    public void use(Entity entity) {
        gp.playSE(2);
        gp.getUi().addMessage("Vida + " + value);
        entity.setLife(entity.getLife()+value);
    }
    
     
    
}
