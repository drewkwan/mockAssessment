package vttp.mockAssessment.app;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.text.html.HTML;

public class HttpClientConnection implements Runnable {
    private Socket sock;
    private String docRoot;
    private InputStream is;
    private DataInputStream dis;
    private OutputStream os;
    private DataOutputStream dos;


    public HttpClientConnection(Socket sock, String docRoot) throws IOException {
        this.sock = sock;
        this.docRoot = docRoot;
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
             //Prints out that this is a get method
             
            try {
                InputStreamReader isr;
                isr = new InputStreamReader(sock.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String request = br.readLine(); 
                String methodType = "";
                String resourceFile = "";

                if (!request.isEmpty()) {
                    System.out.println("PRINTING REQ >>>>>>>>>> ");
                    System.out.println(request);
                    System.out.println("<<<<<<<<<< PRINTING REQ");
                    
                    String[] commands = request.split(" ");
                    methodType = commands[0];
                    resourceFile = commands[1];
                
                }
                
                /*if (request.contains("GET")){ //test
                 System.out.println("tuerihs");
                 
                } */
                
                //Action 1 completed
                if (!request.contains("GET")) {
                    System.out.println("HTTP/1.1 405 Method Not Allowed");
                    OutputStream os = sock.getOutputStream(); //what your server is sending back to the client
                    os.write("HTTP/1.1 405 Method Not Allowed\r\n\r\n".getBytes()); //what is \r //404 not found //instead of get maybe do like a return
                    os.write("\r\n".getBytes());
                    os.write(methodType.getBytes());
                    os.write(" not supported\r\n".getBytes()); 
                    os.write("\r\n\r\n".getBytes());
                    os.flush();
                    System.out.println("Client connection closed!");
                    br.close();
                    os.close();
                }
                //Action 2,3 and 4
                String[] paths = this.docRoot.split(":");
                String trimmedResource = resourceFile.replace("/", ""); 
                if (trimmedResource == "") {
                    trimmedResource = "index.html";
                }
                boolean checkResource = false;
                for (String p : paths) {
                    String resourcePath = p + File.separator + trimmedResource;
                    File fileName = new File(resourcePath);
                    //String html="";
                    System.out.println("File name is: >>> " + fileName);
                    if (fileName.exists()) {
                        //Action 4
                        if (resourceFile.contains(".png")) {
                            System.out.println("HTTP/1.1 200 OK");
                            InputStream ips = new FileInputStream(fileName);
                            OutputStream os = sock.getOutputStream();
                            os.write("HTTP/1.1 200 OK\r\n\r\n".getBytes()); 
                            os.write("\r\n".getBytes());
                            os.write("Content-Type: image/png\r\n".getBytes());
                            os.write("\r\n".getBytes());
                            os.write(ips.readAllBytes()); //the html has to be href or it won't work
                            os.write("\r\n\r\n".getBytes());
                            os.flush();
                            System.out.println("Client connection closed!");
                            br.close();
                            os.close();
                        }
                        //Action 3
                        else {
                            System.out.println("HTTP/1.1 200 OK");
                            InputStream ips = new FileInputStream(fileName);
                            OutputStream os = sock.getOutputStream();
                            os.write("HTTP/1.1 200 OK\r\n\r\n".getBytes()); 
                            os.write("\r\n".getBytes());
                            os.write(ips.readAllBytes()); //the html has to be href or it won't work
                            os.write("\r\n\r\n".getBytes());
                            os.flush();
                            System.out.println("Client connection closed!");
                            br.close();
                            os.close();
                   
                        }
                        checkResource = true;
                        break;
                    }
                    
                } 
                //action 2
                if (!checkResource) {
                    System.out.println("HTTP/1.1 404 Not Allowed");
                    OutputStream os = sock.getOutputStream(); //what your server is sending back to the client
                    os.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes()); //what is \r //404 not found //instead of get maybe do like a return
                    os.write("\r\n".getBytes());
                    os.write(trimmedResource.getBytes());
                    os.write(" not found\r\n".getBytes()); 
                    os.write("\r\n\r\n".getBytes());
                    os.flush();
                    System.out.println("Client connection closed!");
                    br.close();
                    os.close();
                }

                //We close connection but never exit thread. Come back to this.
                
                //get the index.html
                
                //Action 3


              } catch (IOException e) {
                e.printStackTrace();
            }
      
  } 
    
    
}
