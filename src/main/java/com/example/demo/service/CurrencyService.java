package com.example.demo.service;

import com.example.demo.DTO.CurrencyAverageDTO;
import com.example.demo.DTO.CurrencyMaxBuySellDiffDTO;
import com.example.demo.DTO.CurrencyMaxMinAverageDTO;
import com.example.demo.domain.Currency;
import com.example.demo.domain.RateTableA;
import com.example.demo.domain.RateTableC;
import com.example.demo.exception.CurrencyNotFoundException;
import com.example.demo.exception.InvalidLocalDateException;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * Service class responsible for holding business logic and fetching data from NBP
 */
@Service
@AllArgsConstructor
public class CurrencyService {

    private RestTemplate restTemplate;

    /**
     * Gets the average value of provided currency during given day.
     *
     * @param localDate    Date in YYYY-MM-DD format. This date points from which day should the data be fetched from.
     *                     In case of Saturday or Sunday it cannot provide any data.
     * @param currencyCode Three letter currency code (standard ISO 4217)
     * @return DTO with the average value
     */
    public CurrencyAverageDTO getAverageCurrencyValue(LocalDate localDate, String currencyCode) {
        //Java default LocalDate format is YYYY-MM-DD
        try {
            java.util.Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CurrencyNotFoundException("Currency with this code does not exist.");
        }

        if (localDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || localDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new InvalidLocalDateException("Cannot provide output for Saturday or Sunday.");
        }

        String uri = "http://api.nbp.pl/api/exchangerates/rates/A/" + currencyCode + "/" + localDate + "/";
        //RestTemplate restTemplate = new RestTemplate();
        Currency<RateTableA> currency;

        currency = restTemplate.exchange(uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Currency<RateTableA>>() {
                }).getBody();


        return new CurrencyAverageDTO(currency.getRates().get(0).getMid());
    }

    /**
     * Gets the minimum average value and maximum average value of the given currency.
     *
     * @param currencyCode Three letter currency code (standard ISO 4217)
     * @param limit        Maximum amount of returned rates for the given currency
     * @return DTO containing minimum average value and maximum average value.
     */
    public CurrencyMaxMinAverageDTO getMaxMinAverageCurrency(String currencyCode, int limit) {
        try {
            java.util.Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CurrencyNotFoundException("Currency with this code does not exist.");
        }
        if (limit > 255) {
            throw new IllegalArgumentException("Cannot provide limit value more than 255");
        }

        String uri = "http://api.nbp.pl/api/exchangerates/rates/a/" + currencyCode + "/last/" + limit + "/";
        // RestTemplate restTemplate = new RestTemplate();
        Currency<RateTableA> currency;

        currency = restTemplate.exchange(uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Currency<RateTableA>>() {
                }).getBody();

        float max = currency.getRates().stream().max(Comparator.comparing(RateTableA::getMid)).get().getMid();
        float min = currency.getRates().stream().min(Comparator.comparing(RateTableA::getMid)).get().getMid();

        return new CurrencyMaxMinAverageDTO(max, min);
    }

    /**
     * Gets biggest difference for ask and sell rates for given currency
     *
     * @param currencyCode Three letter currency code (standard ISO 4217)
     * @param limit        Maximum amount of returned rates for the given currency
     * @return DTO containing maximum difference value
     */
    public CurrencyMaxBuySellDiffDTO getMaxBuySellDifference(String currencyCode, int limit) {
        try {
            java.util.Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CurrencyNotFoundException("Currency with this code does not exist.");
        }
        if (limit > 255) {
            throw new IllegalArgumentException("Cannot provide limit value more than 255");
        }

        String uri = "http://api.nbp.pl/api/exchangerates/rates/c/" + currencyCode + "/last/" + limit + "/";
        // RestTemplate restTemplate = new RestTemplate();
        Currency<RateTableC> currency;

        currency = restTemplate.exchange(uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Currency<RateTableC>>() {
                }).getBody();

        float max = currency.getRates().stream().max(Comparator.comparing(RateTableC::getAsk)).get().getAsk();
        float min = currency.getRates().stream().min(Comparator.comparing(RateTableC::getBid)).get().getBid();

        return new CurrencyMaxBuySellDiffDTO(max - min);
    }
}