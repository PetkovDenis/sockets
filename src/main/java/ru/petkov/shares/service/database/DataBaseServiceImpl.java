package ru.petkov.shares.service.database;

import ru.petkov.shares.entity.ShareM;
import ru.petkov.shares.service.api.ShareServiceImpl;
import ru.tinkoff.piapi.contract.v1.Share;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataBaseServiceImpl implements DataBaseService {

    private final ShareServiceImpl shareService;

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "1";

    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public DataBaseServiceImpl(ShareServiceImpl shareService) {
        this.shareService = shareService;
    }

    @Override
    public List<ShareM> getShares() throws SQLException {

        Connection connection = DriverManager.getConnection(url, user, password);
        preparedStatement = connection.prepareStatement("SELECT * FROM shares");
        resultSet = preparedStatement.executeQuery();
        List<ShareM> shareMList = new ArrayList<>();
        while (resultSet.next()) {
            ShareM shareM = new ShareM();
            shareM.setName(resultSet.getString(1));
            shareM.setTicker(resultSet.getString(2));
            shareM.setPrice(resultSet.getInt(3));
            shareMList.add(shareM);
        }
        return shareMList;
    }


    @Override
    public String save(String ticker) throws ExecutionException, InterruptedException, SQLException {
        List<Share> list = shareService.getList();

        Share share = list.stream().filter(s -> s.getTicker().equals(ticker)).findFirst().orElse(null);

        Connection connection = DriverManager.getConnection(url, user, password);

        BigDecimal price = shareService.getPrice(share);

        String query = "INSERT INTO shares(name, ticker, price) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, share.getName());
        preparedStatement.setString(2, share.getTicker());
        preparedStatement.setBigDecimal(3, price);

        int i = preparedStatement.executeUpdate();

        String result = null;

        if (i == 1 ){
            result = "Share with ticker ["+ticker+"] was added \n\r";
        }else {
            result = "Share with ticker ["+ticker+"] not added \n\r";
        }
        return result;
    }
}
