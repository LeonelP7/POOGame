/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package enviroment;

import java.awt.Graphics2D;
import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class EnviromentManager {
    
    private GamePanel gp;
    private Lighting lighting;

    public EnviromentManager(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setup(){
        
        lighting = new Lighting(gp);
    }
    
    public void update(){
        lighting.update();
    }
    
    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }
}
