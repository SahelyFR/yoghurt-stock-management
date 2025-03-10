package com.yoghurt.stockmanagement;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.stream.IntStream;

public class Consumption {

    private static final int YOGHURT_PER_PACK = 2;

    private Calendar startDate;
    private int startingStock;
    private int[] weeklyConsumption;
    private int deliveryDelay;

    public Consumption(int[] weeklyConsumption, int deliveryDelay) {
        this.startDate = Calendar.getInstance();
        this.startDate.set(2025, 0, 5);
        this.startingStock = 6;
        this.weeklyConsumption = weeklyConsumption;
        this.deliveryDelay = deliveryDelay;
    }

    public LinkedHashSet<ConsumptionDetail> calculateYearDetails() {
        int inStock = startingStock;
        LinkedHashSet<ConsumptionDetail> yearlyNeed = new LinkedHashSet<ConsumptionDetail>();
        Calendar day = startDate;
        int weeklyNeed = IntStream.of(weeklyConsumption).sum();
        
        for(int i=0; i<52; i++){           
            int quantityToOrder = 0;
            int besoin= weeklyNeed - inStock;

            for(int j=0; j<7; j++) {
                int consumptionDayIndex = j == 0 ? 6 : j - 1;
                if(j == 0) {
                    quantityToOrder = (int) Math.round(besoin / YOGHURT_PER_PACK);
                }

                if(j == deliveryDelay){
                    inStock += (quantityToOrder * YOGHURT_PER_PACK);
                }

                inStock -= weeklyConsumption[consumptionDayIndex];
                System.out.println("Conso du jour(" + j + ") : " + weeklyConsumption[consumptionDayIndex]);
                System.out.println("Stock(" + j + ") : " + inStock);
                yearlyNeed.add(new ConsumptionDetail(day.getTime(), 
                    weeklyConsumption[consumptionDayIndex], 
                    j==0 ? quantityToOrder : 0, 
                    j == deliveryDelay ? quantityToOrder * YOGHURT_PER_PACK : 0,
                    inStock));
                day.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return yearlyNeed;
    }

    /* getters and setters */
    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public int getStartingStock() {
        return startingStock;
    }

    public void setStartingStock(int startingStock) {
        this.startingStock = startingStock;
    }

    public int[] getWeeklyConsumption() {
        return weeklyConsumption;
    }

    public void setWeeklyConsumption(int[] weeklyConsumption) {
        this.weeklyConsumption = weeklyConsumption;
    }

    public int getDeliveryDelay() {
        return deliveryDelay;
    }

    public void setDeliveryDelay(int deliveryDelay) {
        this.deliveryDelay = deliveryDelay;
    }

    
}
