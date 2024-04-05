<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <Header></Header>
      </el-header>
      <el-container
          style="height: 100vh;
        border: 1px solid #eee;
        margin: 0;
        border: 0;
        padding: 0;" >
        <el-aside width="200px">
          <Aside></Aside>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import Aside from "@/components/children/Aside.vue"
import Header from "@/components/children/Header.vue"
export default {
  components: {
    Aside,
    Header
  }
}
</script>
<style scoped>
.el-aside {
  background-color: skyblue;
  height: calc(100vh - 60px);
}
.el-main {
  width:100vh;
  height: calc(100vh - 60px);
  padding:0;
}
.el-container{
  height: 100vh;
}
.el-header{
  background-color: skyblue;
}
</style>