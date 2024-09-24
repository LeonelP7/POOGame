/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import object.OBJ_Key;

public class UI {

    private GamePanel gp;
    private Font arial_30;
    private Font arial_70B;
    private BufferedImage keyImage;
    private boolean messageOn = false;
    private String message = "";
    private int messageCounter;
    private boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_70B = new Font("Arial", Font.BOLD, 70);
        OBJ_Key objKey = new OBJ_Key();
        keyImage = objKey.getImage();
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        int x;
        int y;
        int textLegth;
        if (gameFinished) {
            g2.setFont(arial_30);
            g2.setColor(Color.white);

            String text;

            text = "You have found a treasure!";
            //aqui obtenemos la la longitud del texto que se va a mostrar
            textLegth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.getScreenWidth() / 2) - textLegth/2;
            y = (gp.getScreenHeight() / 2) - (gp.getTileSize() * 3);
            g2.drawString(text, x, y);

            g2.setFont(arial_70B);
            g2.setColor(Color.YELLOW);
            text = "Congratulations!";
            textLegth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.getScreenWidth() / 2) - textLegth/2;
            y = (gp.getScreenHeight() / 2) + (gp.getTileSize() * 2);
            g2.drawString(text, x, y);
            
            //detiene el juego
            gp.setGameThread(null);

        } else {
            g2.setFont(getArial_30());
            g2.setColor(Color.white);
            g2.drawImage(getKeyImage(), getGp().getTileSize() / 2, getGp().getTileSize() / 2, getGp().getTileSize(), getGp().getTileSize(), null);
            g2.drawString("x " + getGp().getPlayer().getHasKey(), 76, 60);

            //mensaje
            if (messageOn) {                               
                textLegth = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth();
                x = gp.getScreenWidth() / 2 - textLegth/2;
                y =  (gp.getScreenHeight() / 2) - (gp.getTileSize());

                g2.setFont(g2.getFont().deriveFont(30));
                g2.drawString(message, x, y);

                //con esto hacemos que los mensajes se muestren durante cierto tiempo
                messageCounter++;
                if (messageCounter > 100) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public Font getArial_30() {
        return arial_30;
    }

    public void setArial_30(Font arial_30) {
        this.arial_30 = arial_30;
    }

    public BufferedImage getKeyImage() {
        return keyImage;
    }

    public void setKeyImage(BufferedImage keyImage) {
        this.keyImage = keyImage;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

}
