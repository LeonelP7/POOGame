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
    
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        name = "chest";
        down1 = setUp("/objects/chest");
    }
    
}
