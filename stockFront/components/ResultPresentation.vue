<script setup lang="js">
import StockGraph from './StockGraph.vue';

const { tableData, graphData } = useAttrs();

</script>

<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="auto">
        <v-data-table :items="tableData"></v-data-table>
      </v-col>
      <v-col>
        <stock-graph :values="graphData" />
      </v-col>
    </v-row>
  </v-container>
</template>