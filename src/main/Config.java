/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS TUF
 */
public class Config {
    
    private GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }
    
    public void saveConfig(){
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            
            //guardar volumen de la musica
            bw.write(String.valueOf(gp.getMusic().getVolumeScale()));
            bw.newLine();
            
            //guardar el volumen de los efectos de sonido
            bw.write(String.valueOf(gp.getSe().getVolumeScale()));
            bw.newLine();
            
            
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadConfig(){
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();
            
            //volumen de la musica
            gp.getMusic().setVolumeScale(Integer.parseInt(s));
            
            //volumen de los efectos de sonido
            s = br.readLine();
            gp.getSe().setVolumeScale(Integer.parseInt(s));
            
            br.close();
        } catch (FileNotFoundException ex) {
           ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
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
