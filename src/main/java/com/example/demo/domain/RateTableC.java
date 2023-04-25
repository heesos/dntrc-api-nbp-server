package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain representing Rate values from table C
 */
@Setter
@Getter
@NoArgsConstructor
public class RateTableC extends Rate {
    /**
     * Buy currency value
     */
    private float bid;

    /**
     * Sell currency value
     */
    private float ask;
}
