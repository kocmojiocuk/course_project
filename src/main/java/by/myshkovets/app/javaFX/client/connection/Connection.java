package by.myshkovets.app.javaFX.client.connection;

import by.myshkovets.app.javaFX.message.MessageType;
import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Connection {
    private static Logger logger = Logger.getLogger(Connection.class);
    private final static Connection instance = new Connection();
    private BufferedReader reader;
    private PrintWriter writer;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;

    private static final String MESSAGE_TYPE = "messageType";


    private Connection() {
        try {
            Socket socket = new Socket("localhost", 8080);
            if (socket.isConnected()) {
            }


            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(MessageType messageType, Object object) {
        Gson gson = new Gson();
        JSONObject json = new JSONObject();
        json.put(MessageType.MESSAGE_TYPE.toString(), messageType.toString());
        json.put(messageType.toString(), gson.toJson(object));
        writer.println(json.toString());
    }

    public void sendMessage(MessageType messageType) {
        Gson gson = new Gson();
        JSONObject json = new JSONObject();
        json.put(MessageType.MESSAGE_TYPE.toString(), messageType.toString());
        writer.println(json.toString());
    }

    public void sendMessage(Object object) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JSONObject readMessage() {

        String message = null;
        try {
            message = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(message);
    }



    public List<StatisticsDataProperty> readMessageList() {
        List<StatisticsDataProperty> list = null;
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            list = (List) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Object readMessageObject() {
        Object object = null;
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            object =  in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return object;
    }




    public static Connection getInstance() {
        return instance;
    }
}
