package com.yoghurt.stockmanagement;

import java.util.Date;

public record ConsumptionDetail(Date day, int quantityConsumed, int quantityToOrder, int quantityReceived, int stock) {
    
}
