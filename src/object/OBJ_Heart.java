/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class OBJ_Heart extends SuperObject{
    
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "key";
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
            
            image = uTool.scaleImage(image, gp.getTileSize(), gp.getTileSize());
            image2 = uTool.scaleImage(image2, gp.getTileSize(), gp.getTileSize());
            image3 = uTool.scaleImage(image3, gp.getTileSize(), gp.getTileSize());
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
