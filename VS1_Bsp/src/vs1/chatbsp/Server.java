package vs1.chatbsp;

import java.net.*;
import java.io.*;
import java.util.*;
 
public class Server {
 
   private Hashtable clients;
   private int port;   
   
   public Server(int port) {
      this.port=port;
      clients = new Hashtable();
   }
   
   private void startServerListener() {
      ServerSocket ss ;
      try {
         ss = new ServerSocket(port);
         System.out.println("Server gestartet...");
         while (true)          
            new ServerBody(ss.accept(), this).start();   
      } catch (Exception e) {
         e.printStackTrace();
      }
   
   }   
   
   public void addClient(String name, ServerBody body) {
      clients.put(name, body);
   }
   
   public void removeClient(String name) {
      clients.remove(name);
   }
   
   public String getUsers() {
      String users;
      users="users|";
      for (Enumeration e = clients.keys();e.hasMoreElements();)
         users+=(String) e.nextElement() + "|";
      if (! users.equals("users|"))
         users = users.substring(0, users.length() - 1);
      return users;      
   }
   
   public void broadcast(String name, String msg) throws Exception {
      for (Enumeration e = clients.keys();e.hasMoreElements();)         
         ((ServerBody) clients.get((String) e.nextElement())).send(name + ": " + msg);      
   }
   
   public void send(String name, String targetname, String msg) throws Exception {
      ((ServerBody) clients.get(targetname)).send(name + ": " + msg);      
   }
   
   public boolean isClient(String name) {
      return clients.containsKey(name);
   }
   
   public static void main(String[] x) {   
      if (x.length != 1) {
         System.out.println("#java Server <port>");
         System.exit(0);
      }
      new Server(Integer.parseInt(x[0])).startServerListener();
   }
}
 
 
class ServerBody extends Thread {
 
   private Socket cs;
   private Server server;
   private PrintWriter out;
 
   public ServerBody(Socket cs, Server server) {
      this.cs=cs;
      this.server=server;
   }
 
   public void run() {
      BufferedReader in;
      StringTokenizer str;
      String name, msg, targetname;
      int n;
      try {
               
         in = new BufferedReader (new InputStreamReader(cs.getInputStream()));
         out = new PrintWriter(new DataOutputStream(cs.getOutputStream()));         
         name = in.readLine();         
         server.addClient(name, this);         
         server.broadcast(name, server.getUsers());
         System.out.println("+ Client " + name + " hat sich angemeldet!");         
         for (String buffer;(buffer = in.readLine()) != null;) {
            n = buffer.indexOf("|", 0);
            targetname = buffer.substring(0, n);
            msg = buffer.substring(n + 1, buffer.length());
            if (targetname.equals("all")) {
               server.broadcast(name, msg);
               System.out.println(">Client " + name + " schreibt an alle: " + msg);
            } else if (server.isClient(targetname)) {
               server.send(name, targetname, msg);
               System.out.println(">Client " + name + " schreibt an " + targetname + ": " + msg);
            } else
               this.send("Server: Client " + targetname + " existiert nicht!");
         }
         server.removeClient(name);
         System.out.println("- Client " + name + " hat sich abgemeldet!");
         in.close();
         out.close();               
      } catch (Exception e) {
         e.printStackTrace();
      }
   
   }
 
   public void send(String msg) throws Exception {
      out.println(msg);
      out.flush();
   }
}