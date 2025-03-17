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
        int besoin= weeklyNeed - inStock;
        
        for(int i=0; i<52; i++){           
            int quantityToOrder = 0;
            if(i == 0){
                besoin = weeklyNeed;
            }

            for(int j=0; j<7; j++) {
                int consumptionDayIndex = j  == 0 ? 6 : j - 1;
                int realDailyConso = 0;

                if(j == 0) {
                    besoin = weeklyNeed;
                    quantityToOrder = (int) Math.round(besoin / YOGHURT_PER_PACK);
                    System.out.println("Week["+(int)(i+1)+"] : A commander : " + quantityToOrder + " packs = " + quantityToOrder*YOGHURT_PER_PACK + " yaourts");
                }

                if(j == deliveryDelay){
                    inStock += quantityToOrder * YOGHURT_PER_PACK;
                }

                if(inStock - weeklyConsumption[consumptionDayIndex] < 0){
                    inStock = 0;
                } else {
                    inStock -= weeklyConsumption[consumptionDayIndex];
                    realDailyConso = weeklyConsumption[consumptionDayIndex];
                }

                System.out.println("Conso du jour(" + (int)(j+1) + ") : " + realDailyConso + " => Stock : " + inStock);
                
                yearlyNeed.add(new ConsumptionDetail(day.getTime(), 
                    realDailyConso, 
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
