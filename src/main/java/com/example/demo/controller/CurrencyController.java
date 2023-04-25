package com.example.demo.controller;

import com.example.demo.DTO.CurrencyAverageDTO;
import com.example.demo.DTO.CurrencyMaxBuySellDiffDTO;
import com.example.demo.DTO.CurrencyMaxMinAverageDTO;
import com.example.demo.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Rest Controller for Currency entity
 *
 * @author Miłosz
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/currency/")
public class CurrencyController {
    private CurrencyService currencyService;

    /**
     * Gets the average value of provided currency during given day.
     *
     * @param localDate Date in YYYY-MM-DD format. This date points from which day should the data be fetched from.
     *                  In case of Saturday or Sunday it cannot provide any data.
     * @param code      Three letter currency code (standard ISO 4217)
     * @return DTO with the average value
     */

    @Operation(description = "Returns the average value for the given currency at the given date")
    @GetMapping("/average/{date}/{code}")
    public ResponseEntity<CurrencyAverageDTO> getAverageCurrencyValue(@PathVariable("date") LocalDate localDate, @PathVariable("code") String code) {
        return ResponseEntity.ok(currencyService.getAverageCurrencyValue(localDate, code));
    }

    /**
     * Gets the minimum average value and maximum average value of the given currency.
     *
     * @param code  Three letter currency code (standard ISO 4217)
     * @param limit Maximum amount of returned rates for the given currency
     * @return DTO containing minimum average value and maximum average value.
     */
    @Operation(description = "Returns the minimum average value and maximum average value of the given currency")

    @GetMapping("/maxmin/{code}/{limit}")
    public ResponseEntity<CurrencyMaxMinAverageDTO> getMaxMinCurrencyValue(@PathVariable("code") String code, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(currencyService.getMaxMinAverageCurrency(code, limit));
    }

    /**
     * Gets biggest difference for ask and sell rates for given currency
     *
     * @param code  Three letter currency code (standard ISO 4217)
     * @param limit Maximum amount of returned rates for the given currency
     * @return DTO containing maximum difference value
     */
    @Operation(description = "Returns the biggest difference for ask and sell rates for given currency")

    @GetMapping("/maxdiff/{code}/{limit}")
    public ResponseEntity<CurrencyMaxBuySellDiffDTO> getMaxDifferenceValue(@PathVariable("code") String code, @PathVariable("limit") int limit) {
        return ResponseEntity.ok(currencyService.getMaxBuySellDifference(code, limit));
    }
}
