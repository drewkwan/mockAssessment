package vttp.mockAssessment.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClientConnection implements Runnable {
    private Socket sock;
    private String docRoot;
    private InputStream is;
    private DataInputStream dis;
    private OutputStream os;
    private DataOutputStream dos;

    public HttpClientConnection(Socket sock) throws IOException {
        is = sock.getInputStream();
        dis = new DataInputStream(is);
        os = sock.getOutputStream();
        dos = new DataOutputStream(os);
    }

    public String read() throws IOException {
        return dis.readUTF();
    }

    public void write(String msg) throws IOException {
        dos.writeUTF(msg);
        dos.flush();
    }

    @Override
    public void run() {
        System.out.println("Starting a client thread");
          //Prints out that this is a get method
      
  } 
    
    
}
