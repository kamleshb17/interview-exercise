package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.BuyPriceListenerImpl;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSourceThread;
import com.acme.mytrader.price.SellPriceListenerImpl;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {
    private final ExecutionService tradeExecutionService;
    private final PriceSourceThread sourceListner;
    private Thread thread;

    public TradingStrategy(final ExecutionService tradeExecutionService, final PriceSourceThread sourceListner) {
        this.tradeExecutionService = tradeExecutionService;
        this.sourceListner = sourceListner;

    }

    public void buy(final String security, final double price, final int volume) throws InterruptedException {
        final PriceListener priceListener = new BuyPriceListenerImpl(security, price, volume,
                tradeExecutionService, false);
        sourceListner.addPriceListener(priceListener);
        thread = new Thread(sourceListner);
        thread.start();
        thread.join();
        sourceListner.removePriceListener(priceListener);

    }

    public void sell(final String security, final double price, final int volume) throws InterruptedException {
        final PriceListener priceListener = new SellPriceListenerImpl(security, price, volume,
                tradeExecutionService, false);
        sourceListner.addPriceListener(priceListener);
        thread = new Thread(sourceListner);
        thread.start();
        thread.join();
        sourceListner.removePriceListener(priceListener);
    }
}
