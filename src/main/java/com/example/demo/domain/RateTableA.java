package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain representing Rate values from table A
 */
@Setter
@Getter
@NoArgsConstructor
public class RateTableA extends Rate {
    /**
     * Average currency rate
     */
    private float mid;
}
