<script setup>
import {ref,onMounted} from "vue";

const  echarts = require('echarts/lib/echarts')
const  mycharts = ref(null)
const  container = ref(null)
onMounted(()=>{
  mycharts.value=echarts.init(container.value)

})
</script>

<template>
<div ref="container" style="width: 700px;height:600px">
</div>
</template>

<style scoped>

</style>