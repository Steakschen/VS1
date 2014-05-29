package vs1.bsp2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carsten
 */
public class SocketServerHandler extends Thread {

    private Socket s;

    public SocketServerHandler(Socket s) {
        this.s = s;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream())) ){
            // lesen
            String text = in.readLine();
            
            // schreiben
            out.write(text.toUpperCase());
            out.newLine();
            out.flush();
            
            // aufrauumen automatisch try with ressources
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
