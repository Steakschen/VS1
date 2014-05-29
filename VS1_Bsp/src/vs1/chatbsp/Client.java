package vs1.chatbsp;

import java.net.*;
import java.io.*;
import java.util.*;
 
public class Client {
 
   private String ip;
   private int port;
 
   public Client(String ip, int port) {
      this.ip=ip;
      this.port=port;
   }
 
   private void startClient() throws Exception {
      Socket s;
      String buffer, name, targetname, msg;
      PrintWriter out;
      BufferedReader in;
      int n;
      s = new Socket(ip, port);
      buffer = null;
      out = new PrintWriter(new DataOutputStream(s.getOutputStream()));      
      in = new BufferedReader(new InputStreamReader(System.in));            
      System.out.println("\n\nClient gestartet...\n\n");      
      name = null;      
      while (name == null || name.equals("")) {
         System.out.print("Client-Name eingeben: ");
         name = in.readLine();
         name = name.trim();
      }
      out.println(name);
      out.flush();
      new ClientBody(s.getInputStream()).start();            
      System.out.print("\nText eingeben -> <zielrechner> <message># ");      
      while (! (buffer = in.readLine().trim()).equals("stop")) {         
         if ((n = buffer.indexOf(" ", 0)) < 0) {
            System.out.print("\nUngueltige Eingabe! Text eingeben -> <zielrechner> <message># ");
            continue;
         }
         System.out.print("\nText eingeben -> <zielrechner> <message># ");         
         targetname = buffer.substring(0, n);
         msg = buffer.substring(n + 1, buffer.length());   
         out.println(targetname + "|" + msg);
         out.flush();   
      }
      System.out.println("\n\nClient gestoppt...\n\n");
      in.close();
      out.close();
   }
 
   public static void main(String[] x) throws Exception {
      if (x.length != 2) {
         System.out.println("#java Client <server-ip> <port>");
         System.exit(0);
      }      
      new Client(x[0], Integer.parseInt(x[1])).startClient();   
   }
}
 
 
class ClientBody extends Thread {
   private InputStream i;
   public ClientBody(InputStream i) {
      this.i=i;
   }
 
   public void run() {
      String buffer;
      BufferedReader in;
      int n;
      try {
         in = new BufferedReader(new InputStreamReader(i));
         while ((buffer = in.readLine()) != null) {            
            if ((n = buffer.indexOf("users|", 0)) > -1) {
               buffer = buffer.substring(n + "users|".length(), buffer.length());
               buffer = buffer.replace('|', ',');
               System.out.println("\n\n==>Angemeldete User: " + buffer);            
            } else
               System.out.println("\n\n==>Eingang von " + buffer);
            System.out.print("\nText eingeben -> <zielrechner> <message># ");      
         }
         
      } catch (Exception e) {}   
   }
}
