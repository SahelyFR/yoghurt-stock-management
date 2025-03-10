package com.yoghurt.stockmanagement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("stock")
public class StockController {

    private CalculatorService calculatorService;

    public StockController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @PostMapping("calculate")
    public Set<ConsumptionDetail> calculate(@RequestBody RequestDto requestBody) {       
        Set<ConsumptionDetail> result = calculatorService.calculate(requestBody);
        // System.out.println("Résultat de la requête");
        // System.out.println(result);
        return result;
    }
    

}
