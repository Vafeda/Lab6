import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private int port;
    private String host;
    ObjectInputStream reader ;
    ObjectOutputStream writer;
    public Client(String host, int port){
        this.host = host;
        this.port = port;

    }
    public void run() throws IOException {
        Socket socket;
        socket = new Socket(host, port);
        ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
        while (true){

        }
    }
}
