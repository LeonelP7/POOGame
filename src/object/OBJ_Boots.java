/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author ASUS TUF
 */
public class OBJ_Boots extends SuperObject{
    
    public OBJ_Boots() {
        name = "boots";
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
