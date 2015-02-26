import java.io.*;
import java.net.*;
import java.util.Scanner;

    public class TCPClient {
    	
    	private final static int TEXT = 1;
    	private final static int IMAGE = 2;
    	private final static int VIDEO = 3;
    	private final static int MUSIC = 4;
    	private final static int[] INPUTS = {TEXT, IMAGE, VIDEO, MUSIC};
    	
    	private final static String ROUTER_ADDRESS = "10.99.3.144";
    	private final static String SERVER_ADDRESS = "10.99.3.144";
    	private final static String CLIENT_ADDRESS = "10.99.22.254";
    	private final static int PORT = 5555;	
    	
        public static void main(String[] args) throws IOException {
        	
        	int fileType = inputType();			
         
			if(fileType == TEXT){
	    	
				sendTextFile(); 
			}
			
			else if(fileType == IMAGE){
				
				sendImageFile();
			}
			
			else if(fileType == VIDEO){
				
				sendVideoFile();
			}
			
			else if(fileType == MUSIC){
				sendMusicFile();
			}
        
		
			// closing connections

       }
        
       private static int inputType(){
  		   int fileType = 0;
  		   boolean correctInput = false;
  		   while(!correctInput){
  			   Scanner in = new Scanner(System.in);
  			   System.out.println("Client \n----------------------\n1) Text File \n2) Image File \n3) Video File \n4) Music File");
  		   
  			   if(in.hasNextInt()){
  				  fileType = in.nextInt();
  				  if(fileType > 0 && fileType < INPUTS.length){
  					  correctInput = true;
  				  }
  			   }
  		   }
  		   return fileType;
  	   }	

       private static void sendTextFile() throws IOException{
    	   Scanner sin = new Scanner(System.in);
    	   System.out.print("Name of Text file: ");
    	   String textFile = sin.nextLine();
    	   sin.close();
    	   Reader reader = new FileReader("src/"+textFile); 
    	   BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
       	
    	   // Variables for setting up connection and communication
           Socket Socket = null; // socket to connect with ServerRouter
           PrintWriter out = null; // for writing to ServerRouter
           BufferedReader in = null; // for reading from ServerRouter
           String routerName = ROUTER_ADDRESS; // ServerRouter host name
           int SockNum = PORT; // port number
  			
          // Tries to connect to the ServerRouter
           try {
              Socket = new Socket(routerName, SockNum);
              out = new PrintWriter(Socket.getOutputStream(), true);
              in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
           } 
               catch (UnknownHostException e) {
                 System.err.println("Don't know about router: " + routerName);
                 System.exit(1);
              } 
               catch (IOException e) {
                 System.err.println("Couldn't get I/O for the connection to: " + routerName);
                 System.exit(1);
              }
    	   // Variables for message passing	 
    	   String fromServer; // messages received from ServerRouter
           String fromUser; // messages sent to ServerRouter
           String address = SERVER_ADDRESS; // destination IP (Server)
           //long t0, t1, t;
         	
           //ROUTER COMMUNICATION
   		   // Communication process (initial sends/receives
           out.println(address);// initial send (IP of the destination Server)
           fromServer = in.readLine();//initial receive from router (verification of connection)
           System.out.println("ServerRouter: " + fromServer);
           
           //CLIENT TO SERVER
           //out.println(CLIENT_ADDRESS); // Client sends the IP of its machine as initial send
           out.println(TEXT); //Sends the file type to server
           
           //t0 = System.currentTimeMillis();
    	   
    	   while ((fromServer = in.readLine()) != null) {
    		   System.out.println("Server: " + fromServer);
				//t1 = System.currentTimeMillis();
				if (fromServer.equals("BYE.")) // exit statement
					break;
				//t = t1 - t0;
				//System.out.println("Cycle time: " + t + "ms");
          
			
            fromUser = fromFile.readLine(); // reading strings from a file
            if (fromUser != null) {
               System.out.println("Client: " + fromUser);
               out.println(fromUser); // sending the strings to the Server via ServerRouter
					//t0 = System.currentTimeMillis();
            }
         }
    	   	fromFile.close();
			out.close();
			in.close();
			Socket.close();
       }
       private static void sendImageFile()
       {
       	 Scanner sin = new Scanner(System.in);
    	   System.out.print("Name of Image file: ");
    	   String imgFile = sin.nextLine();
    	   sin.close();
    	 	
    	   //****************************************
    	   //****************************************
    	   //***  B U F F E R I N G   I M A G E   ***
    	   //****************************************
    	   //****************************************
    	
    	   InputStreamReader reader = new FileReader("src/"+imgFile); 
    	   
    	   //Way 1
    	   //BufferedImage fromFile = ImageIO.read(ImageIO.createImageInputStream(reader));
    	   
    	   //Way 2
    	   //BufferedInputStream fromFile = new BufferedInputStream(new FileInputStream("src/"+imgFile));
    	  
    	   //Way 3
    	   /* BufferedImage fromFile = null;
           try
           {
               fromFile = ImageIO.read(new File("src/"+imgFile));
           }
           catch (IOException e)
           {
           }
    	   */
    	   
    	   
    	
    	   Socket Socket = null; // socket to connect with ServerRouter
           PrintWriter out = null; // for writing to ServerRouter
           BufferedReader in = null; // for reading from ServerRouter
           String routerName = ROUTER_ADDRESS; // ServerRouter host name
           int SockNum = PORT; // port number
  			
           // Tries to connect to the ServerRouter
           try 
           {
        	   Socket = new Socket(routerName, SockNum);
        	   out = new PrintWriter(Socket.getOutputStream(), true);
        	   in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
           } 
           catch (UnknownHostException e)
           {
        	   System.err.println("Don't know about router: " + routerName);
               System.exit(1);
           } 
           catch (IOException e)
           {
               System.err.println("Couldn't get I/O for the connection to: " + routerName);
               System.exit(1);
           }
    	   
           
           //******************************************
           // Variables for message passing	          *
           //******************************************
           
    	   String fromServer; // messages received from ServerRouter
           Image fromUser; // messages sent to ServerRouter
           String address = SERVER_ADDRESS; // destination IP (Server)
           //long t0, t1, t;
           
           
           //************************
           //ROUTER COMMUNICATION
   		   //****************************
                      
           // Communication process (initial sends/receives
           //****************************************************
           
           out.println(address);// initial send (IP of the destination Server)
           fromServer = in.readLine();//initial receive from router (verification of connection)
           System.out.println("ServerRouter: " + fromServer);
           
           //CLIENT TO SERVER
           //out.println(CLIENT_ADDRESS); // Client sends the IP of its machine as initial send
           //************************************************************************************
           out.println(IMAGE); //Sends the file type to server
           
           //t0 = System.currentTimeMillis();
    	   
    	   while ((fromServer = in.readLine()) != null)
    	   {
    		   System.out.println("Server: " + fromServer);
    		   //t1 = System.currentTimeMillis();
    		   //t = t1 - t0;
    		   //System.out.println("Cycle time: " + t + "ms");
          
			
    		   fromUser = fromFile.readLine(); // reading strings from a file
    		   if (fromUser != null) 
    		   {
    			   System.out.println("Client: " + fromUser);
    			   out.println(fromUser); // sending the strings to the Server via ServerRouter
					//t0 = System.currentTimeMillis();
    		   }
    	   }
    	   	fromFile.close();
			out.close();
			in.close();
			Socket.close();
       	
       }
       private static void sendVideoFile(){}
       private static void sendMusicFile(){}
    }
