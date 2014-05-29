/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vs1.bsp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server-Seite des simplen Textverteiler-Servers.
 * 
 * @author Wolfgang Knauf
 * 
 */
public class TestServer {
    
   /**Server lauscht auf Port 13000 und nimmt in einer Endlosschleife Antworten entgegen.
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    ServerSocket serverSocket = null;

    //An Port 13000 binden:
    try
    {
      serverSocket = new ServerSocket(13000);
    }
    catch (IOException e)
    {
      System.out.println("Binden an Port  13000 schlug fehl: " + e.getMessage());
      System.exit(-1);
    }
    
    //In einer Endlosschleife auf eingehende Anfragen warten.
    while (true)
    {
      try
      {
        //Blocken, bis eine Anfrage kommt:
        System.out.println ("ServerSocket - accepting");
        Socket clientSocket = serverSocket.accept();
        
        //Wenn die Anfrage da ist, dann wird ein Thread gestartet, der 
        //die weitere Verarbeitung übernimmt.
        System.out.println ("ServerSocket - accept done");
        Thread threadHandler = new Thread(new TestServerHandler(clientSocket) );
        threadHandler.start();
		
		System.out.println ("ServerSocket - Thread started, next client please...");
      }
      catch (IOException e)
      {
        System.out.println("'accept' auf Port 13000 fehlgeschlagen");
        System.exit(-1);
      }
      
    }
  }
}
