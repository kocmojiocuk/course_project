package by.myshkovets.app.javaFX.server;

import by.myshkovets.app.javaFX.message.MessageType;
import by.myshkovets.app.javaFX.entity.tables.Goal;
import by.myshkovets.app.javaFX.entity.tables.StatisticsData;
import by.myshkovets.app.javaFX.dao.ConnectorDB;
import by.myshkovets.app.javaFX.entity.account.Account;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class Handler extends Thread {
    private static Logger logger = Logger.getLogger(Handler.class);
    private BufferedReader reader;
    private PrintWriter writer;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private static final String MESSAGE_TYPE = MessageType.MESSAGE_TYPE.toString();
    private static final String IS_EXIST = MessageType.IS_EXIST.toString();
    private static final String SIGN_UP_CONFIRMATION = MessageType.SIGN_UP_CONFIRMATION.toString();
    private static final String SIGN_UP = MessageType.SIGN_UP.toString();
    private static final String SIGN_IN = MessageType.SIGN_IN.toString();
    private static final String ACCOUNT = MessageType.ACCOUNT.toString();
    private static final String GET_STATISTICS = MessageType.GET_STATISTICS.toString();
    private static final String GET_GOALS = MessageType.GET_GOALS.toString();
    private static final String ADD_NEW_STATISTICS_DATA = MessageType.ADD_NEW_STATISTICS_DATA.toString();
    private static final String UPDATE_STATISTICS_DATA = MessageType.UPDATE_STATISTICS_DATA.toString();
    private static final String DELETE_STATISTICS_DATA = MessageType.DELETE_STATISTICS_DATA.toString();
    private static final String MAKE_NEW_GOAL = MessageType.MAKE_NEW_GOAL.toString();
    private static final String UPDATE_GOAL_DATA = MessageType.UPDATE_GOAL_DATA.toString();
    private static final String DELETE_GOAL_DATA = MessageType.DELETE_GOAL_DATA.toString();


    public Handler(Socket clientSocket) {
        super();
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            //in = new ObjectInputStream(clientSocket.getInputStream());
            //out = new ObjectOutputStream(clientSocket.getOutputStream());
            socket = clientSocket;
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        super.run();

        while (true) {
            JSONObject outputMessage = new JSONObject();
            Gson gson = new Gson();
            MessageType type = null;
            JSONObject inputMessage = null;
            String inputString;


            try {

                inputString = reader.readLine();
                inputMessage = new JSONObject(inputString);
                type = MessageType.valueOf((String) inputMessage.get(MESSAGE_TYPE));
            } catch (IOException e) {
                logger.error("Не удалось считать данные с клиента");
            }
            switch (type) {


                case SIGN_IN:
                    outputMessage.put(MESSAGE_TYPE, SIGN_UP_CONFIRMATION);

                    Account account =   gson.fromJson((String) inputMessage.get(SIGN_IN), Account.class);
                    Optional<Account> optionalAccount = ConnectorDB.getAccountDAO().getAccount(account);

                    if (optionalAccount.isPresent()) {
                        outputMessage.put(IS_EXIST, true);
                        Account fullAccount = optionalAccount.get();
                        outputMessage.put(ACCOUNT, gson.toJson(fullAccount));
                        logger.info("Вход: эксперт " + fullAccount.getInfo() + " вошёл в систему.");
                    } else {
                        outputMessage.put(IS_EXIST, false);
                        logger.info("Вход: попытка входа в систему под логином \"" + account.getLogin() + "\"");

                    }
                    writer.println(outputMessage.toString());
                    break;


                case SIGN_UP:
                    outputMessage.put(MESSAGE_TYPE, SIGN_UP_CONFIRMATION);
                    Account newAccount = gson.fromJson((String) inputMessage.get(SIGN_UP), Account.class);
                    int id = ConnectorDB.getAccountDAO().create(newAccount);
                    if (id == 0) {
                        outputMessage.put(SIGN_UP_CONFIRMATION, true);
                        logger.info("Регистрация: добавлен новый эксперт" + newAccount.getInfo() + '.');
                    } else {
                        outputMessage.put(SIGN_UP_CONFIRMATION, false);
                        logger.info("Регистрация: эксперт не добавлен, логин \"" + newAccount.getLogin() + "\" уже существует.");
                    }
                    writer.println(outputMessage.toString());
                    break;


                case GET_STATISTICS:
                    outputMessage.put(MESSAGE_TYPE, GET_STATISTICS);
                    try {
                        ObjectOutputStream out  = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(ConnectorDB.getTableDateDAO().findAll());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


                case GET_GOALS:
                    outputMessage.put(MESSAGE_TYPE, GET_GOALS);
                    try {
                        ObjectOutputStream out  = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(ConnectorDB.getGoalsDAO().findAll());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


                case ADD_NEW_STATISTICS_DATA:
                    outputMessage.put(MESSAGE_TYPE, ADD_NEW_STATISTICS_DATA);
                    StatisticsData data = gson.fromJson((String)inputMessage.get(ADD_NEW_STATISTICS_DATA), StatisticsData.class);
                    try {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        int i = ConnectorDB.getTableDateDAO().create(data);
                        out.writeObject(i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


                case UPDATE_STATISTICS_DATA:
                    outputMessage.put(MESSAGE_TYPE, UPDATE_STATISTICS_DATA);
                    StatisticsData statistics = gson.fromJson((String)inputMessage.get(UPDATE_STATISTICS_DATA), StatisticsData.class);
                    ConnectorDB.getTableDateDAO().update(statistics);
                    break;


                case DELETE_STATISTICS_DATA:
                    outputMessage.put(MESSAGE_TYPE, DELETE_STATISTICS_DATA);
                    writer.println(outputMessage.toString());
                    try {
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        List<StatisticsData> statisticsData = (List) in.readObject();
                        ConnectorDB.getTableDateDAO().delete(statisticsData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;


                case MAKE_NEW_GOAL:
                    outputMessage.put(MESSAGE_TYPE, MAKE_NEW_GOAL);
                    writer.println(outputMessage.toString());
                    try {
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        Goal goal = (Goal) in.readObject();
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        int id3 = (int)ConnectorDB.getGoalsDAO().create(goal);
                        out.writeObject(id3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;



                case UPDATE_GOAL_DATA:
                    outputMessage.put(MESSAGE_TYPE, UPDATE_GOAL_DATA);
                    writer.println(outputMessage.toString());
                    try {
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        Goal goal = (Goal) in.readObject();
                        ConnectorDB.getGoalsDAO().update(goal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;


                case DELETE_GOAL_DATA:
                    outputMessage.put(MESSAGE_TYPE, DELETE_GOAL_DATA);
                    writer.println(outputMessage.toString());
                    try {
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        Goal goal = (Goal) in.readObject();
                        ConnectorDB.getGoalsDAO().delete(goal);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }




        }

    }
}


