/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class OBJ_Key extends Entity{
    
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "key";
        down1 = setUp("/objects/key");
        
    }
    
    
}
