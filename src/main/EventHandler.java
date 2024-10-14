/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Rectangle;

/**
 *
 * @author ASUS TUF
 */
public class EventHandler {

    private GamePanel gp;
    private Rectangle eventRect;
    private int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    /*
    para calcular facilmente la fila y la columna viendo desde el ide
    rowEvento = rowIde-1
    colEvento = colIde/3 - 1
     */
    public void checkEvent() {

        if (hit(16, 27, "right") == true) {
            damagePit(gp.getDialogueState());
        }
        if (hit(12, 23, "up")) {
            healingPool(gp.getDialogueState());
        }
    }

    public boolean hit(int eventRow, int eventColumn, String reqDirection) {

        boolean hit = false;

        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();
        eventRect.x += eventColumn * gp.getTileSize();
        eventRect.y += eventRow * gp.getTileSize();

        if (eventRect.intersects(gp.getPlayer().getSolidArea())) {
            if (gp.getPlayer().getDirection().equalsIgnoreCase(reqDirection)
                    || reqDirection.equalsIgnoreCase("any")) {
                hit = true;
            }
        }

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState) {
        gp.setGameState(gameState);
        gp.getUi().setCurrentDialogue("Te haz doblado el tobillo! :(");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
    }

    public void healingPool(int gameState) {

        if (gp.getKeyH().isEnterPressed()) {
            gp.setGameState(gameState);
            gp.getUi().setCurrentDialogue("Haz recuperado salud! :)");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
        }
        
    }

    //GETTERS Y SETTERS
    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

}
