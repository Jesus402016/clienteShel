/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ufps.cliente.DTO.usuario;

/**
 *
 * @author Jesus
 */
public class chat extends Thread {
    
    private usuario usuario;
    private String host="127.0.0.1";
    private int puerto=3000;
    private boolean activo;
    private int conectados;

   
    
    
    public chat(String nombre){
       this.usuario=new usuario(nombre);
       this.start();
    }
    
       
    
    @Override
    public void run(){
        try {
            usuario.setSocket(new Socket(host,puerto));
            this.usuario.setEnviar(new ObjectOutputStream(usuario.getSocket().getOutputStream()));
            this.usuario.setRecibir(new ObjectInputStream(usuario.getSocket().getInputStream()));
            this.enviarSolicitud();
            this.escuchar();
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }
    
    //metodo encargado enviar solicitud
    public void enviarSolicitud() throws IOException{
        String usuario=this.usuario.getNombre();
        String []list=new String[2];
        list[0]="CONEXION";
        list[1]=this.usuario.getNombre();
        this.usuario.getEnviar().writeObject(list);
        
    }
    
    public void crearUsuario(String nombre) throws IOException, ClassNotFoundException{
        String list[]=new String[1];
        list[0]="CONECTADOS";
        this.usuario.getEnviar().writeObject(host);
        Object o= this.usuario.getRecibir().readObject();
        String []li=(String[])o;
        for (int i = 0; i <li.length; i++) {
            if(li[i].equals(nombre)){
                System.out.print("no se puede crear usuario de dos nombres");
            }
        }
    }
    public void enviarMensaje(String mensaje,String destino) throws IOException{
            String[] c = new String[4];
            c[0] = "MENSAJE";
            c[1] = this.usuario.getNombre();
            c[2]=destino;
            c[3]=mensaje;
            this.usuario.getEnviar().writeObject(c);
        
    }
    
    public void escuchar(){
        activo=true;
        while(activo){
            try {
                Object aux=this.usuario.getRecibir().readObject();
                String list[]=(String [])aux;
                if(list[0].equals("MENSAJE")){
                System.out.print("\n"+list[1]+":"+list[3]); 
               // responder(list[1]);
                }
                else if(list[0].equals("CONECTADOS"))
                  {
                   conectados(list);
                  }
                else if(list[0].equals("DESCONECTAR"))
                  {
                System.out.println(list[1]+" "+"Salió");
                  }
            } catch (IOException ex) {
                System.out.println("se perdio la conexion con el servidor");
                activo=false;
            } catch (ClassNotFoundException ex) {
                
            }
        }
    }
    
    //Metodo que retorna todos los conectados
    public void conectados(String []list){
           String cad="Conectados:"+"("
                            ;
                    for (int i = 1; i < list.length; i++) {
                       cad+=list[i]+""+",";
                    }
                    cad+=")";
                    System.out.println(cad); 
                    this.setConectados(list.length);
         //System.out.print(getConectados());
    }
     public boolean getContectados() {
        if(this.getConectados()>2){
            return true;
        }else{
            return false;
        }
    }
     
     public void responder(String nombre) throws IOException{       
              
          System.out.print("Responder:");
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          String mensaje=br.readLine();
          this.enviarMensaje(mensaje,nombre);
        
      
   }
     

    /**
     * @return the conectados
     */
    public int getConectados() {
        return conectados;
    }

    /**
     * @param conectados the conectados to set
     */
    public void setConectados(int conectados) {
        this.conectados = conectados;
    }
}
