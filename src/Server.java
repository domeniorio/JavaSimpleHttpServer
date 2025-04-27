import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int portNumber;
    private ServerSocket serverSocket;

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        this.serverSocket = new ServerSocket(this.portNumber);
    }

    public void start() throws IOException{
        while(true){
            Socket client = this.serverSocket.accept();
            WorkerThread t = new WorkerThread(client);
            t.run();
        }
    }
}
