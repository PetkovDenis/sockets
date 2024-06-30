package ru.petkov.shares.service.server;

import ru.petkov.shares.service.api.ShareServiceImpl;
import ru.petkov.shares.service.database.DataBaseServiceImpl;
import ru.tinkoff.piapi.contract.v1.Share;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class ServerService {

    private final ShareServiceImpl shareService;
    private final DataBaseServiceImpl dataBaseService;

    public ServerService(ShareServiceImpl shareService, DataBaseServiceImpl dataBaseService) {
        this.shareService = shareService;
        this.dataBaseService = dataBaseService;
    }


    public void dialogue(Socket socket) throws ExecutionException, InterruptedException, IOException, SQLException {

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        Scanner scanner = new Scanner(inputStream);

        String result = null;

        while (scanner.hasNext()) {
            result = scanner.next();
            switch (result) {
                case "stop":
                    socket.close();
                    break;
                case "list":
                    list(bufferedWriter);
                    break;
                case "add":
                    saveShareByTicker(scanner, bufferedWriter);
                    break;
            }
        }
    }

    private void list(BufferedWriter bufferedWriter) throws ExecutionException, InterruptedException, IOException {
        List<Share> list = shareService.getList();
        for (Share share : list) {
            bufferedWriter.write("Name [" + share.getName() + "] \n\r" +
                    "Ticker [" + share.getTicker() + "] \n\r" +
                    "Price [" + shareService.getPrice(share) + "] \n\n\r");
            bufferedWriter.flush();
        }
    }

    private String saveShareByTicker(Scanner scanner, BufferedWriter bufferedWriter) throws SQLException, ExecutionException, InterruptedException, IOException {
        String result = null;
        while (scanner.hasNext()) {
            String ticker = scanner.next();
            result = dataBaseService.save(ticker);
            bufferedWriter.write(result);
            bufferedWriter.flush();
            break;
        }
        return result;
    }
}
