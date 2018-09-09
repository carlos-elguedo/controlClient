
package redes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import vista.PantallaBloqueo;

/**
 *
 * @author Carlos Elguedo - Rafael Castellar
 */
public class Comandos {
    
    String sSistemaOperativo = System.getProperty("os.name"), so ="";
    private PantallaBloqueo pantalla = new PantallaBloqueo();
    
    public Comandos(){
        so = sSistemaOperativo.substring(0, 3);
        System.out.println(so);
    }
            
    
    public  void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        }catch (IOException e) {
            System.out.println("Failed");
        }
    }

    public void desBloquear(){
        pantalla.setVisible(false);
    }
    public void bloquear(){
        pantalla.setVisible(true);
        //exec("rundll32.exe user32.dll,LockWorkStation");    
        //rundll32.exe user32.dll, LockWorkStation
    }
    public void bloquear(int segundos){
        
        try {
            Thread.sleep((segundos*1000));
            pantalla.setVisible(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(Comandos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //exec("rundll32.exe user32.dll,LockWorkStation");        
    }
    public void apagar(){
        if(so.equals("Win")){
            exec("shutdown -s -f -t 0");
        }
        if(so.equals("Mac")){
            exec("sudo hall");
        }
        if(so.equals("Lin")){
            exec("sudo shutdown -h now");
        }
        
            
    }
    public void reiniciar(){
        if(so.equals("Win")){
            exec("shutdown -r -f -t 0");
        }
        if(so.equals("Mac")){
            exec("sudo reboot");
        }
        if(so.equals("Lin")){
            exec("sudo reboot");
        }
    }
}

