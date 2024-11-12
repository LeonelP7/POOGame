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
public class OBJ_Door extends Entity{
    
    public OBJ_Door(GamePanel gp) {
        super(gp);
        name = "door";
        down1= setUp("/objects/door");     
        
        collision = true;
    }
}
