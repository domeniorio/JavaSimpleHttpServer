import java.io.*;
import java.net.Socket;

public class WorkerThread extends Thread{
    private Socket client;

    public WorkerThread(Socket client){
        this.client = client;
    }

    public void run() {
        try{
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            DataOutputStream toClient = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
            String request = fromClient.readLine();
            System.out.println(request);
            HTTPRequestDecoder decoder = new HTTPRequestDecoder(request);
            decoder.sendResponse(toClient);

            toClient.close();
            fromClient.close();
            client.close();
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
