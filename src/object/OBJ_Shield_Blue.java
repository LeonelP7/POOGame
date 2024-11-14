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
public class OBJ_Shield_Blue extends Entity{
    
    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        type = type_shield;
        name = "Escudo Azul";
        down1 = setUp("/objects/shield_blue", gp.getTileSize(), gp.getTileSize());
        defenseValue = 2;
        description = "["+name+"]"+"\nYas...";
    }
    
    
}
