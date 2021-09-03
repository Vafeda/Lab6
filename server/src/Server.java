import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    public Server (int port){
        this.port = port;

    }
    public void run() throws IOException {
        Socket clientSocket;
        this.serverSocket = new ServerSocket(port);
        while (true) {
             clientSocket = serverSocket.accept();

        }
    }

    public boolean clientHandler(Socket clientSocket) throws IOException {
        ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());

        return true;
    }
}
