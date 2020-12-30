package com.rharshit.stocker.data;

import com.google.gson.annotations.SerializedName;
import com.rharshit.stocker.base.data.BaseData;

public class TickerData extends BaseData {
    private String name;
    private String symbol;
    @SerializedName("has_intraday")
    private boolean hasIntraday;
    @SerializedName("has_eod")
    private boolean hasEod;
    private String country;
    @SerializedName("stock_exchange")
    private StockExchange stockExchange;

    public TickerData(boolean success) {
        super(success);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isHasIntraday() {
        return hasIntraday;
    }

    public void setHasIntraday(boolean hasIntraday) {
        this.hasIntraday = hasIntraday;
    }

    public boolean isHasEod() {
        return hasEod;
    }

    public void setHasEod(boolean hasEod) {
        this.hasEod = hasEod;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public StockExchange getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    static class StockExchange {
        private String name;
        private String acronym;
        private String mic;
        private String country;
        @SerializedName("country_code")
        private String countryCode;
        private String city;
        private String website;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAcronym() {
            return acronym;
        }

        public void setAcronym(String acronym) {
            this.acronym = acronym;
        }

        public String getMic() {
            return mic;
        }

        public void setMic(String mic) {
            this.mic = mic;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }
    }
}
