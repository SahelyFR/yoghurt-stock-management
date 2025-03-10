package com.yoghurt.stockmanagement;

import java.util.LinkedHashSet;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public LinkedHashSet<ConsumptionDetail> calculate(RequestDto request) {
        Consumption yearlyConsumption = new Consumption(request.weeklyConsumption(), request.deliveryDelay());
        return yearlyConsumption.calculateYearDetails();        
    }
    
}
