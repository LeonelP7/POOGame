/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;

/**
 *
 * @author ASUS TUF
 */
public class EventHandler {

    private GamePanel gp;
    private EventRect eventRect[][][];
    private int tempMap, tempCol, tempRow;

    //variables para condicionar a un evento a no ocurrir repetidamente
    private int previousEventX, previousEventY;
    private boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        this.eventRect = new EventRect[gp.getMaxMap()][gp.getMaxWorldCol()][gp.getMaxWorldRow()];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.getMaxMap() && col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].setEventRectDefaultX(eventRect[map][col][row].x);
            eventRect[map][col][row].setEventRectDefaultY(eventRect[map][col][row].y);

            col++;
            if (col == gp.getMaxWorldCol()) {
                col = 0;
                row++;

                if (row == gp.getMaxWorldRow()) {
                    row = 0;
                    map++;
                }
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
        if (distance > gp.getTileSize()) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 27, 16, "right") == true) {
                damagePit(gp.getDialogueState());
            } else if (hit(0, 23, 12, "up")) {
                healingPool(gp.getDialogueState());
            } else if (hit(0, 10, 39, "any")) {
                teleport(1, 12, 13);
            } else if (hit(1, 12, 13, "any")) {
                teleport(0, 10, 39);
            } else if (hit(1, 12, 9, "up")) {
                speak(gp.getNpc()[1][0]);
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
    public boolean hit(int map, int col, int row, String reqDirection) {

        boolean hit = false;

        if (map == gp.getCurrentMap()) {
            gp.getPlayer().getSolidArea().x += gp.getPlayer().getWorldX();
            gp.getPlayer().getSolidArea().y += gp.getPlayer().getWorldY();
            eventRect[map][col][row].x += col * gp.getTileSize();
            eventRect[map][col][row].y += row * gp.getTileSize();

            if (eventRect[map][col][row].intersects(gp.getPlayer().getSolidArea()) && !eventRect[map][col][row].isEventDone()) {
                if (gp.getPlayer().getDirection().equalsIgnoreCase(reqDirection)
                        || reqDirection.equalsIgnoreCase("any")) {
                    hit = true;

                    previousEventX = gp.getPlayer().getWorldX();
                    previousEventY = gp.getPlayer().getWorldY();
                }
            }

            gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
            gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();
            eventRect[map][col][row].x = eventRect[map][col][row].getEventRectDefaultX();
            eventRect[map][col][row].y = eventRect[map][col][row].getEventRectDefaultY();
        }

        return hit;
    }

    public void damagePit(int gameState) {
        gp.setGameState(gameState);
        gp.playSE(6);
        gp.getUi().setCurrentDialogue("Te haz doblado el tobillo! :(");
        gp.getPlayer().setLife(gp.getPlayer().getLife() - 1);
        canTouchEvent = false;

        //Evento hecho si no se quiere que se repita el evento
        //eventRect[col][row].setEventDone(true);
    }

    public void healingPool(int gameState) {

        if (gp.getKeyH().isEnterPressed()) {
            gp.setGameState(gameState);
            gp.getPlayer().setAttackCancel(true);
            gp.getUi().setCurrentDialogue("Haz recuperado salud! :)\nTu progreso se ha guardado");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            gp.getaSetter().setMoster();
            gp.getSaveLoad().save();
        }

    }

    public void teleport(int map, int col, int row) {

        gp.setGameState(gp.getTransitionState());
        gp.setCurrentMap(map);
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gp.playSE(11);
    }

    public void speak(Entity entity) {
        if (gp.getKeyH().isEnterPressed()) {
            gp.setGameState(gp.getDialogueState());
            gp.getPlayer().setAttackCancel(true);
            entity.speak();
        }
    }

    //GETTERS Y SETTERS
    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public int getTempMap() {
        return tempMap;
    }

    public void setTempMap(int tempMap) {
        this.tempMap = tempMap;
    }

    public int getTempCol() {
        return tempCol;
    }

    public void setTempCol(int tempCol) {
        this.tempCol = tempCol;
    }

    public int getTempRow() {
        return tempRow;
    }

    public void setTempRow(int tempRow) {
        this.tempRow = tempRow;
    }

    public int getPreviousEventX() {
        return previousEventX;
    }

    public void setPreviousEventX(int previousEventX) {
        this.previousEventX = previousEventX;
    }

    public int getPreviousEventY() {
        return previousEventY;
    }

    public void setPreviousEventY(int previousEventY) {
        this.previousEventY = previousEventY;
    }

}
