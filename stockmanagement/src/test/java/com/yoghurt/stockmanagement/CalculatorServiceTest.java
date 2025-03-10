package com.yoghurt.stockmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {
    
    CalculatorService calculatorService = new CalculatorService();

    @Test
    @DisplayName("When using 2 yoghurts per day each day of the week then it should return ...")
    void test1() {
        int[] weeklyConsumption = {3,3,3,3,3,4,4};
        int deliveryDelay = 2;

        int expectedConsuptionSum = 1196; // sum(weeklyConsumption) *52(weeks)
        int expectedOrderSum = 595; // 23yaourts /2 (en pack) *52(weeks) -6/2(stock depart en pack)

        Set<ConsumptionDetail> result = calculatorService
            .calculate(new RequestDto(weeklyConsumption, deliveryDelay));
        
        int resultConsumptionSum = result.stream()
            .map(detail -> detail.quantityConsumed())
            .reduce(0, Integer::sum);

        int resultOrderSum = result.stream()
            .map(detail -> detail.quantityToOrder())
            .reduce(0, Integer::sum);

        assertEquals(expectedConsuptionSum, resultConsumptionSum);
        assertEquals(expectedOrderSum, resultOrderSum);

    }
}
