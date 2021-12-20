package com.acme.mytrader.price;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class PriceSourceImpl implements PriceSourceThread {
    private List<PriceListener> priceListeners;
    private static final List<String> TRADE_SECURITY = Arrays
            .asList("ASIANPAINTS", "DMART", "IRCTC", "ONGC", "SBI", "TCS");

    private static final double RANGE_MIN = 1.00;
    private static final double RANGE_MAX = 100.00;

    public PriceSourceImpl() {
        priceListeners = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addPriceListener(final PriceListener listener) {
        this.priceListeners.add(listener);
    }

    @Override
    public void removePriceListener(final PriceListener listener) {
        this.priceListeners.remove(listener);
    }

    @Override
    public void run() {
        final Random randomGenerationForTradeAlert = new Random();
        for (int i = 0; i < 10; i++) {
            final String security = TRADE_SECURITY.get(randomGenerationForTradeAlert.nextInt(TRADE_SECURITY.size()));
            final double price = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * randomGenerationForTradeAlert.nextDouble();
            priceListeners.forEach(priceListener -> priceListener.priceUpdate(security, price));
        }

    }
}
