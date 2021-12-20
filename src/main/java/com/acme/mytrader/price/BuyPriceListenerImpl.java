package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;

/*
 used for buy security trades
 */
public class BuyPriceListenerImpl implements PriceListener {
    private final String tradeSecurity;
    private final double buyTrigger;
    private final int buyQuantity;
    private final ExecutionService executionService;
    private boolean buyTrade;

    public BuyPriceListenerImpl(final String tradeSecurity, final double buyTrigger, final int buyQuantity,
                                final ExecutionService executionService, final boolean buyTrade) {
        this.tradeSecurity = tradeSecurity;
        this.buyTrigger = buyTrigger;
        this.buyQuantity = buyQuantity;
        this.executionService = executionService;
        this.buyTrade = buyTrade;
    }

    public String getTradeSecurity() {
        return tradeSecurity;
    }

    public double getBuyTrigger() {
        return buyTrigger;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public boolean isBuyTrade() {
        return buyTrade;
    }

    @Override
    public void priceUpdate(final String security, final double price) {
        if (validateBuyRequest(security, price)) {
            executionService.buy(security, price, buyQuantity);
            buyTrade = true;
        }
    }

    /*
     we verify the buy request depending on price and registred trade security
     */
    private boolean validateBuyRequest(final String security, final double price) {
        return (!buyTrade) && this.tradeSecurity.equals(security) && (price < this.buyTrigger);
    }
}
