package ru.petkov.shares.service.api;

import ru.petkov.shares.entity.ShareM;
import ru.tinkoff.piapi.contract.v1.Share;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ShareService  {

    Optional<Share> getShareByTicker(String name);

    List<Share> getList() throws ExecutionException, InterruptedException;

    BigDecimal getPrice (Share share);

    ShareM toShare(Share share);

    List<ShareM> toShareMList(List<Share> shares);

}
