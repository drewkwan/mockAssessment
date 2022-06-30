package vttp.mockAssessment.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args ) throws IOException
    {
        String docRoot = "static";
        //File input reader to go and look at the path. Set the path as a root folder. read folder as path. 
        //You want the user to input something under the docRoot flag. Read the flag and give it to the file system
        //Take the string and use the function to write the file to the docRoot folder. 
        int port = 3000;

        /*
        if (args.length > 0) {
            //Look for the flag --port to indicate the port
            port = Integer.parseInt(args[0]);
            //Look for the flag --docRoot 
            if (args.length>1) 
                docRoot = args[1]; //This code recognises the position of the arg, but if I leave like my first arg balnk (i.e. port, will it default?)

        }
        */

        //This code below recognises the port and the docroot as flags --. Args will be passed as --port 8080
        if (args.length>0) {
            final Map<String, List<String>> params = new HashMap<>();
            
            List<String> options = null;
            for (int i = 0; i < args.length; i++) {
                final String a = args[i];
            
                if (a.charAt(0) == '-') {
                    if (a.length() < 2) {
                        System.err.println("Error at argument " + a);
                        return;
                    }
            
                    options = new ArrayList<>();
                    params.put(a.substring(1), options);
                    System.out.println("a: " + a);
                    
                }
                else if (options != null) {
                    options.add(a);
                    System.out.println("options: "+ options);
                }
                else {
                    System.err.println("Illegal parameter usage");
                    return;
                }
                System.out.println("p: " + params);        

            }
                try{
                    port = Integer.parseInt(params.get("-port").get(0));
                } catch (Exception e){
                    port = 3000;
                }
                try{
                    docRoot = params.get("-docRoot").get(0);
                } catch (Exception e) {
                    docRoot = "./static" ;
                }

        } System.out.printf("Server started at port: %s\n", port);
    

        HttpServer server = new HttpServer(port, docRoot);
        server.start();
    }
}
