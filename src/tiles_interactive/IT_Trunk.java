/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiles_interactive;

import main.GamePanel;

/**
 *
 * @author ASUS TUF
 */
public class IT_Trunk extends InteractiveTile{
    
    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        
        down1 = setUp("/tiles_interactive/trunk", gp.getTileSize(), gp.getTileSize());
        
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.height = 0;
        solidArea.width = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
}
