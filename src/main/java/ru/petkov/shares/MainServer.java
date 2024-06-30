package ru.petkov.shares;

import ru.petkov.shares.service.api.ShareServiceImpl;
import ru.petkov.shares.service.database.DataBaseServiceImpl;
import ru.petkov.shares.service.server.ServerService;
import ru.tinkoff.piapi.contract.v1.Share;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MainServer {

    private final int port = 8881;

    private final ServerService serverService;

    public MainServer(ServerService serverService) {
        this.serverService = serverService;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, SQLException {
        MainServer server = new MainServer(new ServerService(new ShareServiceImpl(),new DataBaseServiceImpl(new ShareServiceImpl())));
        server.start();
    }

    public void start() throws ExecutionException, InterruptedException, SQLException {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключен!");
            serverService.dialogue(socket);
            socket.close();
            serverSocket.close();
        } catch (IOException e){
            e.getMessage();
        }
    }
}

/*
1 - Получить одну акцию ++
2 - Сохранить в бд ++
3 - Получить любые 3 акции и положить их в бд ++
4 - При вводе пользователем: список. Получить всю инфу, о акциях. ++
5 - Подгружать данные актуальные и с бд. И сравнить результат.
Срок до пятницы.

Дополнительно:
Мэин сервере улучшить читаемость кода
Спрятать токен в настройках идеи
 */