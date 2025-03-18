<script setup lang="js">
import axios from 'axios';
import ResultPresentation from './ResultPresentation.vue';
import { ref } from 'vue'

const consumptionResult = ref(null)
const lineValues = ref()
const tableData = ref()
const intialStock = 6;
const weeklyConsumption = ref([3,2,1,3,3,4,4]);
const deliveryDelay = ref(2);
const stock = ref(intialStock);
const weekDayNames = ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'];

async function handleFormSubmit(event) {
  event.preventDefault();

  const {data: response } = await axios({
    url: 'http://localhost:8080/stock/calculate', 
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    data: {
      weeklyConsumption: weeklyConsumption.value,
      deliveryDelay: deliveryDelay.value
    }
  });

  if(response){
    consumptionResult.value = response
    tableData.value = response
        .filter(item => (item.quantityToOrder > 0 || item.quantityReceived > 0))
        .map((item) => {
              return {...item,
                day: getDayFromResponseDate(item.day)
          }})
    lineValues.value = response.map((dailyResponse) => {
      const formattedDate = getDayFromResponseDate(dailyResponse.day).split('-');
      const chartDate = new Date(formattedDate[0], formattedDate[1]-1, formattedDate[2])
      stock.value = dailyResponse.stock;

      return [chartDate.getTime(), stock.value]
    })
  }
  
}

function getDayFromResponseDate(givenDate){
  return givenDate.split('T')[0];
}

function resetResults() {
  consumptionResult.value = null
}

</script>

<template>
    <div v-if="consumptionResult == null">
      <form @submit="handleFormSubmit">
        <v-btn type="submit" variant="tonal" color="primary">
          Lancer le calcul avec CompositionAPI
        </v-btn>
        <v-row justify="center">
          <v-col cols="auto">
            <v-card style="margin: auto" width="700" class="flex">
              <v-card-text>
                <div>
                  <h3>Délai de livraison après commande : </h3>
                
                  <v-slider
                    :max="7"
                    :min="2"
                    show-ticks="always"
                    step="1"
                    tick-size="3"
                    v-model="deliveryDelay"
                  >
                    <template v-slot:append>
                      <v-text-field
                        v-model="deliveryDelay"
                        density="compact"
                        style="width: 80px"
                        type="number"
                        variant="outlined"
                      ></v-text-field>
                    </template>
                  </v-slider>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="auto">
            <v-card style="margin: auto" width="700" class="flex">
              <v-card-text>
                <v-row><h3>Profil de consommation (combien de yaourts sont consommés par jour)</h3></v-row>
                <v-row>
                  <div v-for="(value, index) in weeklyConsumption" :key="index">
                    <v-col>
                      <p>{{ weekDayNames[index] }}</p>
                      <v-slider
                        v-model="weeklyConsumption[index]"
                        :max="50"
                        :min="0"
                        :step="1"
                        style="min-width: 300px;"
                      >
                        <template v-slot:append>
                          <v-text-field
                            v-model="weeklyConsumption[index]"
                            density="compact"
                            style="width: 80px"
                            type="number"
                            variant="outlined"
                          ></v-text-field>
                        </template>
                      </v-slider>
                    </v-col>
                  </div>      
                </v-row>      
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </form>
    </div>
    <div v-else>
      <v-btn variant="tonal" color="orange-darken-2" v-on:click="resetResults">
        Recommencer
      </v-btn>
      <result-presentation :tableData="tableData" :graphData="lineValues" />
    </div>
</template>