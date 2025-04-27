import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  {
        try{
            Server server;
            if(args.length > 0) server = new Server(Integer.parseInt(args[0]));
            else server = new Server(1234);
            server.start();
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}