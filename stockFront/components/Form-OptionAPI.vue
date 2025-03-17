<script lang="js">
import axios from 'axios';
import ResultPresentation from './ResultPresentation.vue';

  export default {
    components: {
      ResultPresentation
    },
    data() {
      return {
        consumptionResult: null,
        weekDayNames: ['Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'],
        weeklyConsumption: ref([3,2,1,3,3,4,4]),
        deliveryDelay: 2,
        intialStock: 6,
        stock: this.intialStock,
      }
    },
    computed: {
      tableData() {
        if(this.consumptionResult !== null){
          return this.consumptionResult.filter(item => (item.quantityToOrder > 0 || item.quantityReceived > 0))
        }
        return null
      },
      lineValues() {
        if(this.consumptionResult !== null) {
          return this.consumptionResult.map((dailyResponse) => {
            const formattedDate = dailyResponse.day.split('T')[0].split('-');
            const chartDate = new Date(formattedDate[0], formattedDate[1]-1, formattedDate[2])
            this.stock = dailyResponse.stock;

            return [chartDate.getTime(), this.stock]
          })
        }
        return null
      },
    },
    methods: {
      resetResults() {
        this.consumptionResult = null
      },
      async handleFormSubmit(event) {
        event.preventDefault();
        

        const {data: response } = await axios({
          url: 'http://localhost:8080/stock/calculate', 
          method: 'POST',
          headers: {
              'Content-Type': 'application/json',
          },
          data: {
            weeklyConsumption: this.weeklyConsumption,
            deliveryDelay: this.deliveryDelay
          }
        });

        if(response){
          this.consumptionResult = response
        }
      }
    },
  }
</script>

<template>
  <div v-if="consumptionResult == null">
    <form @submit="handleFormSubmit">
      <v-btn type="submit" variant="tonal" color="primary">
        Lancer le calcul avec OtionAPI
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
    <ResultPresentation :tableData="this.tableData" :graphData="this.lineValues" />
  </div>
</template>