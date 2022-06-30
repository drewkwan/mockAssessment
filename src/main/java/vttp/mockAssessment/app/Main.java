package vttp.mockAssessment.app;

import java.io.IOException;

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

        if (args.length > 0) {
            //Look for the flag --port to indicate the port
            port = Integer.parseInt(args[0]);
            //Look for the flag --docRoot 
            if (args.length>1) 
                docRoot = args[1]; //This code recognises the position of the arg, but if I leave like my first arg balnk (i.e. port, will it default?)

        }

        System.out.printf("Server started at port: %s\n", port);

        HttpServer server = new HttpServer(port, docRoot);
        server.start();
    }
}
