package vs1.bsp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carsten
 */
public class SocketClient {

    public static void main(String[] args) {

        // Tastatureingaben werden eingelesen:
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //Server-Verbindung aufbauen:
        Socket socketServer = null;
        try {

            //Server verbinden
            socketServer = new Socket("localhost", 10000);
                  
            PrintWriter out = new PrintWriter(socketServer.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socketServer.getInputStream()));
            
            
            //test einlesen
            String textInput = null;
            textInput = reader.readLine();
            System.out.println(textInput);

            //zu server schicken
            out.println(textInput);
            out.flush();
            
            //vom server
            String textServer = in.readLine();
            System.out.println(textServer);
            
        } catch (UnknownHostException ex) {
            System.out.println("UnknownHostException bei Verbindung zu Host 'localhost', Port 10000: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException bei Verbindung zu Host 'localhost', Port 10000: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println ("Exception: " + ex.getMessage());
        }
    }
}
