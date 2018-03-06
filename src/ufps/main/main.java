/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufps.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import ufps.cliente.chat;
import ufps.controlador.controlador;

/**
 *
 * @author Jesus
 */
public class main {
     static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     static private controlador con;
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String cad="###########################################"+"\n"
                + "########## BIENVENIDO CHAT 1.0  V###########"+"\n"
                 +"########## Politicas de Uso ################"+"\n"
                 +"###### Ingresa el nombre del usuario #######"+"\n"
                 +"##### con quien quiera chatear #############"+"\n";
        System.out.print(cad);
        System.out.print("\n"+"Ingrese su Nombre de Usuario:");
        String nombre=br.readLine();
        con=new controlador(nombre);
        con.start();
        
    }
    
    
  
}
