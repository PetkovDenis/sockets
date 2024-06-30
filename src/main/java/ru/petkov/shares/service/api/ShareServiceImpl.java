package ru.petkov.shares.service.api;

import ru.petkov.shares.entity.ShareM;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static ru.tinkoff.piapi.core.utils.MapperUtils.quotationToBigDecimal;

public class ShareServiceImpl implements ShareService{

    private final InvestApi api = InvestApi.create("t.-up4Z3AEhW3ZiuToIROi4c7phs4lO4TAa_ZAO3cTnx1nU8wxXJAXdS8uHoy7TomNRJTKmIbDrQiU2topBQg49g");
    private final InstrumentsService instrumentsService = api.getInstrumentsService();

    @Override
    public Optional<Share> getShareByTicker(String ticker)  {
        Optional<Share> shareOptional;
        try {
            shareOptional = instrumentsService.getAllShares().get().stream().filter(share -> share.getTicker().equals(ticker)).findFirst();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return shareOptional;
    }

    @Override
    public BigDecimal getPrice(Share share){
        BigDecimal price = null;
        if(!share.getName().isEmpty() && !share.getTicker().isEmpty()){
            List<LastPrice> lastPricesSync = api.getMarketDataService().getLastPricesSync(Collections.singleton(share.getFigi()));
            price = quotationToBigDecimal(lastPricesSync.get(0).getPrice());
        }
        return price;
    }


    @Override
    public List<Share> getList() throws ExecutionException, InterruptedException {
        List<Share> shares = instrumentsService.getAllShares().get();
        List<Share> ruShares = null;

        if (!shares.isEmpty()){
            ruShares = new ArrayList<>();
            for (Share share : shares) {
                if (share.getCountryOfRisk().equals("RU")) {
                    ruShares.add(share);
                }
            }
            return ruShares;
        }
        return ruShares;
    }

    @Override
    public ShareM toShare(Share share) {
        ShareM shareM = new ShareM();
        shareM.setName(share.getName());
        shareM.setTicker(share.getTicker());
        BigDecimal price = getPrice(share);
        shareM.setPrice(price.intValue());
        return shareM;
    }

    @Override
    public List<ShareM> toShareMList(List<Share> shares) {
        return null;
    }

}
