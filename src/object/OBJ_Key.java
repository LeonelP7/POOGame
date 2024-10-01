/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

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
public class OBJ_Key extends SuperObject{
    
    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "key";
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            image = uTool.scaleImage(image, gp.getTileSize(), gp.getTileSize());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
