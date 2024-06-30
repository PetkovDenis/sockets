package ru.petkov.shares.service.database;

import ru.petkov.shares.entity.ShareM;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DataBaseService {

    String save(String ticker) throws ExecutionException, InterruptedException, SQLException;
    List<ShareM> getShares() throws SQLException;

}
