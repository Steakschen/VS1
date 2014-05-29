package vs1.bsp2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carsten
 */
public class SocketServer {

    public static void main(String[] args) {
        
        ServerSocket server = null;
        
        try {
            server = new ServerSocket(10000);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        while (true) {
            try {

                Socket socket = server.accept();
                
                SocketServerHandler thread = new SocketServerHandler(socket);
                thread.start();

            } catch (IOException ex) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
