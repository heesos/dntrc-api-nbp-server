package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Domain class representing currency data
 * @param <T> Rates type, depending on table which data is fetched from
 */
@Setter
@Getter
@NoArgsConstructor
public class Currency<T> {

    /**
     * Table which data have been fetched from
     */
    private String table;

    /**
     * Name of the currency
     */
    private String currency;

    /**
     * Three letter currency code (standard ISO 4217)
     */
    private String code;

    /**
     * Rates of the currency
     */
    private List<T> rates;
}
