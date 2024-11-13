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
    private EventRect eventRect[][];

    //variables para condicionar a un evento a no ocurrir repetidamente
    private int previousEventX, previousEventY;
    private boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        this.eventRect = new EventRect[gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        int col = 0;
        int row = 0;
        while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].setEventRectDefaultX(eventRect[col][row].x);
            eventRect[col][row].setEventRectDefaultY(eventRect[col][row].y);

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;
            }
        }

    }

    /*
    para calcular facilmente la fila y la columna viendo desde el ide
    rowEvento = rowIde-1
    colEvento = colIde/3 - 1
     */
    public void checkEvent() {

        //revisar que el jugador este a mas de 1 tile de distancia del ultimo evento
        int xDistance = Math.abs(gp.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gp.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > 1) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(27, 16, "right") == true) {
                damagePit(27, 16, gp.getDialogueState());
            }
            if (hit(23, 12, "up")) {
                healingPool(23, 12, gp.getDialogueState());
            }
        }

    }

    /**
     * Metodo para activar un evento
     *
     * @param col columna del mundo en la que se encuentra el evento
     * @param row fila del mundo en la que se encuentra el evento
     * @param reqDirection direccion en la que tiene que ir el jugador para
     * activar el evento
     * @return
     */
    public boolean hit(int col, int row, String reqDirection) {

        boolean hit = false;

        gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
        gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();
        eventRect[col][row].x += col * gp.getTileSize();
        eventRect[col][row].y += row * gp.getTileSize();

        if (eventRect[col][row].intersects(gp.getPlayer().getSolidArea()) && !eventRect[col][row].isEventDone()) {
            if (gp.getPlayer().getDirection().equalsIgnoreCase(reqDirection)
                    || reqDirection.equalsIgnoreCase("any")) {
                hit = true;

                previousEventX = gp.getPlayer().getWorldX();
                previousEventY = gp.getPlayer().getWorldY();
            }
        }

        gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
        gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();
        eventRect[col][row].x = eventRect[col][row].getEventRectDefaultX();
        eventRect[col][row].y = eventRect[col][row].getEventRectDefaultY();

        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.setGameState(gameState);
        gp.playSE(6);
        gp.getUi().setCurrentDialogue("Te haz doblado el tobillo! :(");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
        canTouchEvent = false;

        //Evento hecho si no se quiere que se repita el evento
        //eventRect[col][row].setEventDone(true);
    }

    public void healingPool(int col, int row, int gameState) {

        if (gp.getKeyH().isEnterPressed()) {
            gp.setGameState(gameState);
            gp.getPlayer().setAttackCancel(true);
            gp.getUi().setCurrentDialogue("Haz recuperado salud! :)");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            gp.getaSetter().setMoster();
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
