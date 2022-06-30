
@Override
public void run() {
    System.out.println("Starting a client thread");
      //Prints out that this is a get method
      
      InputStreamReader isr = new InputStreamReader(sock.getInputStream());
      BufferedReader br = new BufferedReader(isr);
      String request = br.readLine();
      while (!request.isEmpty()) {
          System.out.println(request);
          request = br.readLine();
      }
      

      
  //Q1: Should these all go into the client handler?

      //Action 1: Check that it is a GET Method
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
      /*
      
      //Action 2: Check if the resource /about.html exists (index?). If it does not, execute action 2 
      if (!request.contains("GET")) { //replace if with resource is not found
          System.out.println("Resource not found");
          OutputStream os = sock.getOutputStream(); //what your server is sending back to the client
          os.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes()); //what is \r //404 not found //instead of get maybe do like a return
          os.write("\r\n".getBytes());
          os.write("<resource name> not found\r\n".getBytes()); 
          os.write("\r\n\r\n".getBytes());
          os.flush();
          System.out.println("Client connection closed!");
          br.close();
          os.close();

      }

      //Action 3: Look through the docRoot directories and identify if the requested resource is present.
      //if (resource exists)
      OutputStream clientOutput = sock.getOutputStream(); //what your server is sending back to the client
      clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes()); //what is \r //404 not found //instead of get maybe do like a return
      clientOutput.write("\r\n".getBytes());
      clientOutput.write("<b>resource contents as bytes</b>".getBytes()); //load website here
      clientOutput.write("\r\n\r\n".getBytes());
      clientOutput.flush();
      System.out.println("Client connection closed!");
      br.close();
      clientOutput.close();
      
      //Action 4: if the resource exists and ends with a .png suffic, send the response bnack to the client.
      OutputStream os = sock.getOutputStream(); //what your server is sending back to the client
      clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes()); //what is \r //404 not found //instead of get maybe do like a return
      clientOutput.write("Content-Type: image/png\r\n".getBytes());
      clientOutput.write("\r\n".getBytes());
      clientOutput.write("<b>resource contents as bytes</b>".getBytes()); //load website here
      clientOutput.write("\r\n\r\n".getBytes());
      clientOutput.flush();
      System.out.println("Client connection closed!");
      br.close();
      clientOutput.close();

      
  } */
} 