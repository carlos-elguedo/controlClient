/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes;

import vista.VentanaCliente;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author JohnArrietaMac
 */
public class HiloCliente extends Thread {

    // propiedades;
    private String ipServidor;
    private int puerto;
    private VentanaCliente ventana;
    private Socket conexion;
    private boolean estado;
    private String sistemaOp = System.getProperty("os.name");
    private boolean estaBloqueado = false;
    private Comandos c = new Comandos();
    
    // Consctuctor
    public HiloCliente(String ipServidor, int puerto, VentanaCliente ventana) {
        this.ipServidor = ipServidor;
        this.puerto = puerto;
        this.ventana = ventana;
    }

    /**
     * Metodo para conectarse al Serividor
     */
    public void conectar() {
        try {
            // crear una instancia del Socket para crerar una conexion con el servidor
            conexion = new Socket(ipServidor, puerto);
            estado = true;
            //ventana.mostrarMensaje("CONECTADO AL SERVIDOR");
            capturarDatosServidor();
            c.bloquear();
            estaBloqueado = true;

        } catch (IOException ex) {
            ventana.mostrarMensaje("ERROR: No se puede conectar al Servidor");
        }
    }

    /**
     * Metodo para crear un hilo o subproceso que leea o capture datos del
     * seridor
     */
    public void capturarDatosServidor() {
        Thread hiloFlujoEntrada;
        hiloFlujoEntrada = new Thread() {
            
            @Override
            public void run() {
                while (estado == true) {
                    DataInputStream datosEntrada = null;
                    try {
                        
                        datosEntrada = new DataInputStream(conexion.getInputStream());
                        int datosRecibidos = datosEntrada.readInt();
                        boolean entroEnAlguno = false;
                        //ventana.mostrarMensaje("Llego esto: " + boleto);
                        
                        if(datosRecibidos == 1){//Apagar
                            c.apagar();
                            entroEnAlguno = true;
                        }
                        if(datosRecibidos == 2){//Reiniciar
                            c.reiniciar();
                            entroEnAlguno = true;
                        }
                        if(datosRecibidos == 3){//Bloquear
                            if(estaBloqueado == false){                    
                                c.bloquear();
                                estaBloqueado = true;
                                entroEnAlguno = true;
                            }
                        }
                        if(datosRecibidos == 5){//DESBloquear
                            if(estaBloqueado == true){
                                c.desBloquear();
                                estaBloqueado = false;
                                entroEnAlguno = true;
                            }
                        }
                        if(entroEnAlguno == false){
                            ventana.mostrarMensaje("Se Bloqueara en " + (datosRecibidos/60) +  " Minutos");
                            estaBloqueado = true;
                            c.bloquear(datosRecibidos);
                        }
                    } catch (IOException ex) {
                        ventana.mostrarMensaje("ERROR: Servidor parado", 1);
                        //estado = false;
                    }

                }
            }

        };
        hiloFlujoEntrada.start();
    }
    

    /**
     * Metodo para escribir la cedula del jugador y enviarla al servidor
     */
    public void enviarSO() {
        DataOutputStream flujoSalida = null;
        try {
            super.run();
            flujoSalida = new DataOutputStream(conexion.getOutputStream());
            flujoSalida.writeUTF(sistemaOp);
            flujoSalida.flush();
            System.err.println("Envio : " + sistemaOp);
        } catch (IOException ex) {
            ventana.mostrarMensaje("ERROR: Problemas al enviar la cedula el Servidor");
        };

    }

    /**
     * Ejecutar el Hilo
     */
    @Override
    public void run() {
        super.run();
        conectar();
        enviarSO();
    }

    /**
     *
     * @return
     */

    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public VentanaCliente getVentana() {
        return ventana;
    }

    public void setVentana(VentanaCliente ventana) {
        this.ventana = ventana;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
