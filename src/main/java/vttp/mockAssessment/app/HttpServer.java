package vttp.mockAssessment.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.print.event.PrintEvent;

public class HttpServer {
    
    private int port;
    private String docRoot;

    public HttpServer(int port, String docRoot) {
        this.port = port;
        this.docRoot = docRoot;
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(this.port);
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        if (!verifyDocRoot()) {
            server.close();
            System.exit(1);
        }
        while (true) { //keeps the server running

            System.out.println("Waiting for client connection");
            Socket sock = server.accept(); //type in localhost to connect, i.e. localhost:3000
            System.out.println("Connected...");
            HttpClientConnection thr = new HttpClientConnection(sock);
            threadPool.submit(thr);
            System.out.println("Submitted to threadpool");

            //Prints out that this is a get method
            InputStreamReader isr = new InputStreamReader(sock.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String request = br.readLine(); 
            if (!request.isEmpty()) {
                System.out.println("PRINTING REQ >>>>>>>>>> ");
                System.out.println(request);
                System.out.println("<<<<<<<<<< PRINTING REQ");
            }

            if (request.contains("GET")){
                System.out.println("tuerihs");
                
            }

            if (!request.contains("GET")) {
                System.out.println("HTTP/1.1 405 Method Not Allowed");
                OutputStream os = sock.getOutputStream(); //what your server is sending back to the client
                os.write("HTTP/1.1 405 Method Not Allowed\r\n\r\n".getBytes()); //what is \r //404 not found //instead of get maybe do like a return
                os.write("\r\n".getBytes());
                os.write("<method name> not supported\r\n".getBytes()); 
                os.write("\r\n\r\n".getBytes());
                os.flush();
                System.out.println("Client connection closed!");
                br.close();
                os.close();
            }
        }
        
    }

 

    public boolean verifyDocRoot() {
        String[] paths = this.docRoot.split(":");
        for (String p : paths) {
            File f = new File(p);
            if (!f.exists()) {
                System.out.printf("File not found. Check %s.\n", p);
                return false;
                }
            if (!f.isDirectory()) {
                System.out.printf("File is not directory. Check %s.\n", p);
             }
        } 

        return true;
    }
}
