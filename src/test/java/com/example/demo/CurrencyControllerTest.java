package com.example.demo;

import com.example.demo.DTO.CurrencyAverageDTO;
import com.example.demo.DTO.CurrencyMaxBuySellDiffDTO;
import com.example.demo.DTO.CurrencyMaxMinAverageDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CurrencyControllerTest {
    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAverageCurrencyValue_returns_a_value() {
        String currencyCode = "USD";
        LocalDate localDate = LocalDate.of(2023, 4, 24);
        String uri = "http://localhost:" + port + "/api/currency/average/{date}/{code}";

        CurrencyAverageDTO averageDTO = restTemplate.getForObject(uri, CurrencyAverageDTO.class, localDate, currencyCode);

        Assertions.assertEquals(4.1905f, averageDTO.value());
    }

    @Test
    public void getAverageCurrencyValue_throws_Exception_when_wrong_currency_code() {
        String currencyCode = "xyz";
        LocalDate localDate = LocalDate.of(2023, 4, 24);
        String uri = "http://localhost:" + port + "/api/currency/average/{date}/{code}";

        Assert.assertThrows(RestClientException.class, () -> restTemplate.getForObject(uri, CurrencyAverageDTO.class, localDate, currencyCode));
    }

    @Test
    public void getAverageCurrencyValue_throws_Exception_when_date_is_Saturday_or_Sunday() {
        String currencyCode = "USD";
        LocalDate localDate = LocalDate.of(2023, 4, 23);
        String uri = "http://localhost:" + port + "/api/currency/average/{date}/{code}";

        Assert.assertThrows(RestClientException.class, () -> restTemplate.getForObject(uri, CurrencyAverageDTO.class, localDate, currencyCode));
    }

    @Test
    public void getMaxMinCurrencyValue_returns_a_value() {
        String currencyCode = "USD";
        int limit = 100;
        String uri = "http://localhost:" + port + "/api/currency/maxmin/{code}/{limit}";

        CurrencyMaxMinAverageDTO averageDTO = restTemplate.getForObject(uri, CurrencyMaxMinAverageDTO.class, currencyCode, limit);

        Assertions.assertEquals(4.1649f, averageDTO.min());
        Assertions.assertEquals(4.4888f, averageDTO.max());
    }

    @Test
    public void getMaxMinCurrencyValue_throws_Exception_when_currency_not_available() {
        String currencyCode = "xyz";
        int limit = 100;
        String uri = "http://localhost:" + port + "/api/currency/maxmin/{code}/{limit}";

        Assert.assertThrows(RestClientException.class, () -> restTemplate.getForObject(uri, CurrencyAverageDTO.class, currencyCode, limit));
    }

    @Test
    public void getMaxDifferenceValue_returns_a_value() {
        String currencyCode = "USD";
        int limit = 100;
        String uri = "http://localhost:" + port + "/api/currency/maxdiff/{code}/{limit}";
        CurrencyMaxBuySellDiffDTO averageDTO = restTemplate.getForObject(uri, CurrencyMaxBuySellDiffDTO.class, currencyCode, limit);

        Assertions.assertEquals(0.3959999f, averageDTO.maxDifference());
    }

    @Test
    public void getMaxDifferenceValue_throws_Exception_when_unknown_currency() {
        String currencyCode = "xyz";
        int limit = 100;
        String uri = "http://localhost:" + port + "/api/currency/maxdiff/{code}/{limit}";

        Assert.assertThrows(RestClientException.class, () -> restTemplate.getForObject(uri, CurrencyMaxBuySellDiffDTO.class, currencyCode, limit));
    }
}

//I am 100% sure this can be done without invoking the whole server and sending actual requests.
//I believe the best way to do it is to mock actual responses so even in case of BNP api shutdown it is possible to test
//these endpoints.
