/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.util.concurrent.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Carlos Elguedo
 */
public class PantallaBloqueo extends JFrame{
    
    //Image imagenFondo = getToolkit().getImage(getClass().getResource("/vista/img/fondoBloqueo.png"));
    Image imagenFondo = getToolkit().getImage(getClass().getResource("fondoBloqueo.jpg"));
    
    public PantallaBloqueo(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	setAlwaysOnTop(true);
	setUndecorated(true);
        setSize(getWidth(), getHeight());
        setIconImage(new ImageIcon(getClass().getResource("/vista/icono.png")).getImage());//Ñadimos el icono para la pantalla
        bloqueoTotal();
        
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
    
    public void bloqueoTotal(){
        ScheduledExecutorService bloqueador = Executors.newSingleThreadScheduledExecutor();
        bloqueador.scheduleAtFixedRate(
                new Runnable(){
                    public void run(){
                        alFrente();
                    }
                }, 500, 10, TimeUnit.MILLISECONDS);
    }
    
    public void alFrente(){
        setExtendedState( JFrame.MAXIMIZED_BOTH );//maximizado
        toFront();
    }
    
    public void esconder(){
        dispose();
    }
}
