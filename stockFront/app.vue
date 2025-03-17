<script setup lang="ts">
import AppMenu from './components/AppMenu.vue';
import Form from './components/Form.vue';
import FormOptionAPI from './components/Form-OptionAPI.vue';

</script>

<template>
  <NuxtLayout>
    <v-app>
      <AppMenu />
      <v-main class="d-flex align-center justify-center" style="min-height: 300px;">
        <!--<Form></Form>-->
        <FormOptionAPI></FormOptionAPI>
      </v-main>
    </v-app>
  </NuxtLayout>
</template>
