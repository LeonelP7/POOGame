/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author ASUS TUF
 */
public class Sound {
    
    private Clip clip;
    //vector con los sonidos del juego
    private URL soundURL[] = new URL[30];
    private FloatControl fc;
    private int volumeScale = 3;
    private float volume;
    
    public Sound() {
        //soundURL[0] = getClass().getResource("/sound/pixsouls.wav");
        soundURL[0] = getClass().getResource("/sound/deutschland.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/sound/levelup.wav");
        soundURL[8] = getClass().getResource("/sound/cursor.wav");
        soundURL[9] = getClass().getResource("/sound/cuttree.wav");
    }
    
    public void setFile(int i) {
        
        try {

            //Esto es un formato para abrir un archivo de audio en java
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
            
        } catch (Exception e) {
        }
    }
    
    public void play() {
        clip.start();
    }
    
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop() {
        clip.stop();
    }
    
    public void checkVolume() {
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;
            case 1:
                volume = -20f;
                break;
            case 2:
                volume = -12f;
                break;
            case 3:
                volume = -5f;
                break;
            case 4:
                volume = 1f;
                break;
            case 5:
                volume = 6f;
                break;
        }
        
        fc.setValue(volume);
    }

    //GETTERS Y SETTERS
    public Clip getClip() {
        return clip;
    }
    
    public void setClip(Clip clip) {
        this.clip = clip;
    }
    
    public URL[] getSoundURL() {
        return soundURL;
    }
    
    public void setSoundURL(URL[] soundURL) {
        this.soundURL = soundURL;
    }
    
    public FloatControl getFc() {
        return fc;
    }
    
    public void setFc(FloatControl fc) {
        this.fc = fc;
    }
    
    public int getVolumeScale() {
        return volumeScale;
    }
    
    public void setVolumeScale(int volumeScale) {
        this.volumeScale = volumeScale;
    }
    
    public float getVolume() {
        return volume;
    }
    
    public void setVolume(float volume) {
        this.volume = volume;
    }
    
}
