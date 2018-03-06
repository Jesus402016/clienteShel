/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufps.cliente.chat;

/**
 *
 * @author Jesus
 */
public class controlador extends Thread {
   private chat chat;
   private String nombre="";
   private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   private boolean activo=true;
 
   
 public controlador(String nombre){
   this.chat=new chat(nombre);
 }
 
   @Override
 public void run(){
      
          while(activo){ 
              System.out.print("");
              if (this.chat.getContectados()) {
                   try {
                  if(nombre.equals("")){ 
                  System.out.print("Con quien desea chatear?");
                  nombre=br.readLine();
                          }                  
                      System.out.print("Nuevo Mensaje:");
                      String mensaje = br.readLine();
                      enviarMensaje(mensaje, nombre);
                  } catch (IOException ex) {
                      nombre="";
                      Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
        }
 }
//Metodo para enviar Mensaje al destino 
 public void enviarMensaje(String mensaje,String nombre ){
     this.nombre=nombre;
     
       try {
           this.chat.enviarMensaje(mensaje, nombre);
       } catch (IOException ex) {
          System.out.println("Error al enviar mensaje");
       }
    
 
     
 }
 
 
    
 
 
 
}
