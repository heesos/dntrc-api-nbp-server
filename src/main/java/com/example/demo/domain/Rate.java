package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Domain class representing Rate
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public abstract class Rate {

    /**
     * Number of the table
     */
    private String no;

    /**
     * Publication date
     */
    private LocalDate effectiveDate;
}
