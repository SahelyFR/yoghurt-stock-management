<script setup lang="js">
const { values } = useAttrs();
</script>

<template>
  <highchart 
    :options="{
      chart: {
        zooming: {
          type: 'x',
        }
      },
      title: {
        text: 'Evolution du stock de yaourts (zoomable)'
      },
      xAxis: {
        type: 'datetime'
      },
      yAxis: {
        title: {
          text: 'Stock'
        }
      },
      series: [{
        name: 'Évolution',
        data: values
      }]
    }" />
</template>