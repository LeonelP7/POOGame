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
public class OBJ_Sword_Normal extends Entity{
    
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        name = "Normal Sword";
        down1 = setUp("/objects/sword_normal", gp.getTileSize(), gp.getTileSize());
        attackValue = 1;
    }
    
}
