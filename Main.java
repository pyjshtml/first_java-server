//For reading files
import java.io.File;
import java.io.FileNotFoundException;
//For server
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.io.OutputStream;
//General
import java.util.Date;
import java.util.Scanner;
class Main{
  public static final int port = 80;
  public static final String host = "192.168.1.3";
  public static final String webroot = "C:\\Users\\Jack\\first_java-server\\sites\\";
  public static String read(String filename) {
    String content = "";
    try {
      File myObj = new File(filename);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        content += data;
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return content;
  }
  public static void main(String[] args) throws Exception {
    ServerSocket server = new ServerSocket(port,50,InetAddress.getByName("192.168.1.3"));
    System.out.println(String.format("Listening on port %d",port));
    while(true){
      try{
        Socket socket = server.accept();
        String httpBody = read(String.format("%sindex.html",webroot));
        String httpHead = String.format("HTTP/1.1 200 OK\r\nContent-Type:text/html; charset:utf-8\r\nContent-Length:%d",httpBody.length());
        String httpResponse = String.format("%s\r\n\r\n%s",httpHead,httpBody);
        socket.getOutputStream().write(httpResponse.getBytes("utf-8"));
        socket.close();
      } catch(Exception e) {System.out.println("Errors");}
    }
  }
}
