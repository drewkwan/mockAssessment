package vttp.mockAssessment.app;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        while (true) {

            System.out.println("Waiting for client connection");
            Socket sock = server.accept();
            System.out.println("Connected...");
            HttpClientConnection thr = new HttpClientConnection(sock);
            threadPool.submit(thr);
            System.out.println("Submitted to threadpool");
            
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
