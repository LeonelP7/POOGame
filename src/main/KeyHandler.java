/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author ASUS TUF
 */
public class KeyHandler implements KeyListener {

    private GamePanel gp;

    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    //DEBUG
    private boolean showDebugText = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //Estado: titulo
        if (gp.getGameState() == gp.getTitleState()) {
            titleState(code);
        } //Estado: jugando
        else if (gp.getGameState() == gp.getPlayState()) {
            playState(code);
        } //Estado: pausa
        else if (gp.getGameState() == gp.getPauseState()) {
            pauseState(code);
        } //Estado: dialogo
        else if (gp.getGameState() == gp.getDialogueState()) {
            dialogueState(code);

        }//Estado: estado del jugador
        else if (gp.getGameState() == gp.getCharacterState()) {
            characterState(code);

        }//Estado: opciones
        else if (gp.getGameState() == gp.getOptionState()) {
            optionsState(code);

        }//Estado: fin del juego
        else if (gp.getGameState() == gp.getGameOverState()) {
            gameOverState(code);

        }
    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {

            gp.getUi().setCommandNumber(gp.getUi().getCommandNumber() - 1);
            if (gp.getUi().getCommandNumber() < 0) {
                gp.getUi().setCommandNumber(2);
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.getUi().setCommandNumber(gp.getUi().getCommandNumber() + 1);
            if (gp.getUi().getCommandNumber() > 2) {
                gp.getUi().setCommandNumber(0);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.getUi().getCommandNumber() == 0) {
                gp.setGameState(gp.getPlayState());
                gp.playMusic(0);
            }
            if (gp.getUi().getCommandNumber() == 1) {
                //añadir luego
            }
            if (gp.getUi().getCommandNumber() == 2) {
                System.exit(0);
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.setGameState(gp.getPauseState());
        }
        if (code == KeyEvent.VK_C) {
            gp.setGameState(gp.getCharacterState());
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.setGameState(gp.getOptionState());
        }
        //debug
        if (code == KeyEvent.VK_T) {
            if (!showDebugText) {
                showDebugText = true;
            } else if (showDebugText) {
                showDebugText = false;
            }
        }

        //recarga el mapa
        if (code == KeyEvent.VK_R) {
            switch (gp.getCurrentMap()) {
                case 0:
                    gp.getTileM().loadMap("/maps/worldV3.txt", 0);
                    break;
                case 1:
                    gp.getTileM().loadMap("/maps/interior01.txt", 1);
                    break;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.setGameState(gp.getPlayState());
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.setGameState(gp.getPlayState());
        }
    }

    public void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.setGameState(gp.getPlayState());
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNumber = 0;
        switch (gp.getUi().getSubState()) {
            case 0:
                maxCommandNumber = 5;
                break;
            case 3:
                maxCommandNumber = 1;
                break;

        }

        if (code == KeyEvent.VK_W) {
            gp.getUi().setCommandNumber(gp.getUi().getCommandNumber() - 1);
            gp.playSE(8);
            if (gp.getUi().getCommandNumber() < 0) {
                gp.getUi().setCommandNumber(maxCommandNumber);
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.getUi().setCommandNumber(gp.getUi().getCommandNumber() + 1);
            gp.playSE(8);
            if (gp.getUi().getCommandNumber() > maxCommandNumber) {
                gp.getUi().setCommandNumber(0);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (gp.getUi().getSubState() == 0) {
                if (gp.getUi().getCommandNumber() == 1 && gp.getMusic().getVolumeScale() > 0) {
                    gp.getMusic().setVolumeScale(gp.getMusic().getVolumeScale() - 1);
                    gp.getMusic().checkVolume();
                    gp.playSE(8);
                }

                if (gp.getUi().getCommandNumber() == 2 && gp.getSe().getVolumeScale() > 0) {
                    gp.getSe().setVolumeScale(gp.getSe().getVolumeScale() - 1);
                    gp.playSE(8);
                }
            }
        }

        if (code == KeyEvent.VK_D) {
            if (gp.getUi().getSubState() == 0) {
                if (gp.getUi().getCommandNumber() == 1 && gp.getMusic().getVolumeScale() < 5) {
                    gp.getMusic().setVolumeScale(gp.getMusic().getVolumeScale() + 1);
                    gp.getMusic().checkVolume();
                    gp.playSE(8);
                }
                if (gp.getUi().getCommandNumber() == 2 && gp.getSe().getVolumeScale() < 5) {
                    gp.getSe().setVolumeScale(gp.getSe().getVolumeScale() + 1);
                    gp.playSE(8);
                }
            }
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.setGameState(gp.getPlayState());
        }
        if (code == KeyEvent.VK_W) {
            if (gp.getUi().getSlotRow() != 0) {
                gp.getUi().setSlotRow(gp.getUi().getSlotRow() - 1);
                gp.playSE(8);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.getUi().getSlotCol() != 0) {
                gp.getUi().setSlotCol(gp.getUi().getSlotCol() - 1);
                gp.playSE(8);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.getUi().getSlotRow() != 3) {
                gp.getUi().setSlotRow(gp.getUi().getSlotRow() + 1);
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_D) {
            if (gp.getUi().getSlotCol() != 4) {
                gp.getUi().setSlotCol(gp.getUi().getSlotCol() + 1);
                gp.playSE(8);
            }

        }
        if (code == KeyEvent.VK_ENTER) {
            gp.getPlayer().selectItem();
        }

    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.getUi().setCommandNumber(gp.getUi().getCommandNumber() - 1);
            if (gp.getUi().getCommandNumber() < 0) {
                gp.getUi().setCommandNumber(1);
            }
            gp.playSE(8);
        }
        if (code == KeyEvent.VK_S) {
            gp.getUi().setCommandNumber(gp.getUi().getCommandNumber() + 1);
            if (gp.getUi().getCommandNumber() > 1) {
                gp.getUi().setCommandNumber(0);
            }
            gp.playSE(8);
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.getUi().getCommandNumber() == 0) {
                gp.setGameState(gp.getPlayState());
                gp.retry();
                gp.playMusic(0);
            } else if (gp.getUi().getCommandNumber() == 1) {
                gp.setGameState(gp.getTitleState());
                gp.restart();

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isShowDebugText() {
        return showDebugText;
    }

    public void setShowDebugText(boolean checkDrawTime) {
        this.showDebugText = checkDrawTime;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public void setEnterPressed(boolean enterPressed) {
        this.enterPressed = enterPressed;
    }

}
