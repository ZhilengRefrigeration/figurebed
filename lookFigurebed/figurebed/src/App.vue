<template>
  <div id="#app">
    <router-view></router-view>
  </div>
</template>
<script>
export default {

}
</script>
<style>
/*全局的父类高度*/
html {
  height: 100%;
}

/*重置body属性*/
body {
  padding: 0;
  margin: 0;
  /*继承html*/
  height: 100%;
}

/*#app的高度也需要继承*/
#app {
  height: 100%;
}
</style>