package com.rharshit.stocker.data;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private List<PortfolioStockData> portfolioStockData;

    public Portfolio() {
        portfolioStockData = new ArrayList<>();
    }

    public Portfolio(List<PortfolioStockData> portfolioStockData) {
        this.portfolioStockData = portfolioStockData;
    }

    public List<PortfolioStockData> getPortfolioStockData() {
        return portfolioStockData;
    }

    public void setPortfolioStockData(List<PortfolioStockData> portfolioStockData) {
        this.portfolioStockData = portfolioStockData;
    }

    public static class PortfolioStockData {
        private String stockSymbol;
        private int totalUnits;
        private long totalValue;

        public PortfolioStockData() {
        }

        public PortfolioStockData(String stockSymbol, int totalUnits, long totalValue) {
            this.stockSymbol = stockSymbol;
            this.totalUnits = totalUnits;
            this.totalValue = totalValue;
        }

        public String getStockSymbol() {
            return stockSymbol;
        }

        public void setStockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
        }

        public int getTotalUnits() {
            return totalUnits;
        }

        public void setTotalUnits(int totalUnits) {
            this.totalUnits = totalUnits;
        }

        public long getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(long totalValue) {
            this.totalValue = totalValue;
        }
    }
}
