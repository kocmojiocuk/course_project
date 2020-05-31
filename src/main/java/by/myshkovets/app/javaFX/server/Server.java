package by.myshkovets.app.javaFX.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        try {
            //TODO альтернативный порт
            ServerSocket serverSocket = new ServerSocket(8080);
            while(true){
                Socket socket = serverSocket.accept();
                if(socket.isConnected()){
                    new Handler(socket);
                }
            }
        } catch (IOException e) {
        }

    }




}
