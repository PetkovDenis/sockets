package ru.petkov.shares.test.service.database;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.petkov.shares.entity.ShareM;
import ru.petkov.shares.service.api.ShareServiceImpl;
import ru.petkov.shares.service.database.DataBaseServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataBaseServiceTest {


    private final ShareServiceImpl shareService  = new ShareServiceImpl();
    private final DataBaseServiceImpl dataBaseService =  new DataBaseServiceImpl(shareService);

    @Test
    public void getShares() throws SQLException, ClassNotFoundException {
        List<ShareM> shares = dataBaseService.getShares();
        for (ShareM shareM : shares){
            System.out.println(shareM.getName());
        }
    }

    @Test
    public void saveShare() throws SQLException, ExecutionException, InterruptedException {
        dataBaseService.save("MRKY");
    }

}
