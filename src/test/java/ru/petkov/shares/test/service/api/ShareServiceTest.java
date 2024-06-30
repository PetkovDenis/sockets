package ru.petkov.shares.test.service.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.petkov.shares.service.api.ShareServiceImpl;
import ru.tinkoff.piapi.contract.v1.Share;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShareServiceTest {

    private  final ShareServiceImpl shareService = new ShareServiceImpl();

    @Test
    public void getList() throws ExecutionException, InterruptedException {
        List<Share> list = shareService.getList();
        if(!list.isEmpty()){
            for(Share share: list){
                System.out.println(share.getName());
            }
        }
    }
}
