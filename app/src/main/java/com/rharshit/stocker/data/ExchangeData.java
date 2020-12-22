package com.rharshit.stocker.data;

import com.google.gson.annotations.SerializedName;
import com.rharshit.stocker.base.data.BaseData;

import org.jetbrains.annotations.NotNull;

public class ExchangeData extends BaseData {
    private String name;
    private String acronym;
    private String mic;
    private String country;
    @SerializedName("country_code")
    private String countryCode;
    private String city;
    private String website;
    private Timezone timezone;
    private Currency currency;

    public ExchangeData(boolean success) {
        super(success);
    }

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

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public static class Timezone {
        private String timezone;
        private String abbr;
        @SerializedName("abbr_dst")
        private String abbrDst;

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public String getAbbr() {
            return abbr;
        }

        public void setAbbr(String abbr) {
            this.abbr = abbr;
        }

        public String getAbbrDst() {
            return abbrDst;
        }

        public void setAbbrDst(String abbrDst) {
            this.abbrDst = abbrDst;
        }
    }

    public static class Currency {
        private String code;
        private String symbol;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @NotNull
        @Override
        public String toString() {
            return code + "/" + symbol;
        }
    }
}
