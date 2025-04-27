import javax.imageio.IIOException;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HTTPRequestDecoder {

    private String header;
    private String method;
    private String resource;
    private String version;

    public HTTPRequestDecoder(String header){
        this.header = header;
        this.split();
    }

    public String getHeader() {
        return header;
    }

    public String getResource() {
        return resource;
    }

    public String getVersion() {
        return version;
    }

    public String getMethod() {
        return method;
    }

    private void split(){
        String[] params=header.split(" ");
        if(params.length < 3){
            this.method = " ";
            this.resource = " ";
            this.version = " ";
        } else {
            this.method = params[0];
            this.resource = params[1];
            this.version = params[2];
        }

    }

    public void sendResponse(DataOutputStream toClient) throws IOException {
        if(!this.method.equals("GET")){
            toClient.writeBytes("HTTP/1.0 400 Bad Request\r\n");
            toClient.writeBytes("Content-Type: text/html; charset=utf-8\r\n");
            toClient.writeBytes("\r\n");
            toClient.writeBytes("<html><body><h1>Bad Request</h1></body></html>");
            return;
        }

        File f = new File(this.getResource().substring(1));
        if(!f.exists()){
            toClient.writeBytes("HTTP/1.0 404 Not Found\r\n");
            toClient.writeBytes("Content-Type: text/html; charset=utf-8\r\n");
            toClient.writeBytes("\r\n");
            toClient.writeBytes("<html><body><h1>Not Found</h1></body></html>");
            return;
        }

        FileInputStream file = new FileInputStream(f);

        byte[] buffer = new byte[4096];
        int bytesRead;

        toClient.writeBytes("HTTP/1.0 200 OK\r\n");
        toClient.writeBytes("Content-Type: text/html; charset=utf-8\r\n");
        toClient.writeBytes("\r\n");

        while ((bytesRead = file.read(buffer)) != -1) {
            toClient.write(buffer, 0, bytesRead);
        }
    }


}
