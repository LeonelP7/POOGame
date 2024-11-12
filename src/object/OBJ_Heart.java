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
        name = "key";
        image=setUp("/objects/heart_full");
        image2=setUp("/objects/heart_half");
        image3=setUp("/objects/heart_blank");
        
    }
}
