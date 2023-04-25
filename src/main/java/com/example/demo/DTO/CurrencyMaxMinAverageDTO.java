package com.example.demo.DTO;

/**
 * DTO representation of Currency
 * @param max Maximum average currency value
 * @param min Minimum average currency value
 */
public record CurrencyMaxMinAverageDTO(float max, float min) {
}
