/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author ASUS TUF
 */
public class EventHandler {

    private GamePanel gp;
    private EventRect eventRect[][][];

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
            if (hit(0,27, 16, "right") == true) {
                damagePit(gp.getDialogueState());
            } else if (hit(0,23, 12, "up")) {
                healingPool(gp.getDialogueState());
            }else if(hit(0,10,39,"any")){
                teleport(1,12,13);
            }else if(hit(1,12,13,"any")){
                teleport(0,10,39);
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
            gp.getUi().setCurrentDialogue("Haz recuperado salud! :)");
            gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
            gp.getaSetter().setMoster();
        }

    }
    
    public void teleport(int map, int col, int row){
         
        gp.setCurrentMap(map);
        gp.getPlayer().setWorldX(gp.getTileSize()*col);
        gp.getPlayer().setWorldY(gp.getTileSize()*row);
        previousEventX = gp.getPlayer().getWorldX();
        previousEventY = gp.getPlayer().getWorldY();
        canTouchEvent = false;
        gp.playSE(11);
    }

    //GETTERS Y SETTERS
    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

}
