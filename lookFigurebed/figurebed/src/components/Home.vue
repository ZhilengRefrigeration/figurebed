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
        <!-- 使用过渡组件包裹侧边栏 -->
        <transition name="slide">
          <el-aside v-if="!isHidden" width="200px">
            <Aside></Aside>
          </el-aside>
        </transition>
        <!-- 主内容区域 -->
        <el-main :style="{ width: isHidden ? '100%' : 'calc(100% - 200px)' }">
          <router-view></router-view>
        </el-main>
        <!-- 控制侧边栏显示隐藏的按钮 -->
        <el-button :icon="isHidden?'Fold':'Expand'" class="hide-button" type="success" @click="toggleSidebar">
          {{ isHidden ? '显示侧边栏' : '隐藏侧边栏' }}
        </el-button>
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
  },
  data() {
    return {
      isHidden: false
    };
  },
  methods: {
    toggleSidebar() {
      this.isHidden = !this.isHidden;
    }
  }
}
</script>

<style scoped>
.el-aside {
  background-color: skyblue;
  height: calc(100vh - 60px);
}
.el-main {
  height: calc(100vh - 60px);
  padding: 0;
}
.el-container {
  height: 100vh;
}
.el-header {
  background-color: skyblue;
}
.hide-button {
  position: absolute;
  top: 10px;
  right: 10px;
}

/* 定义侧边栏显示隐藏的过渡效果 */
.slide-enter-active, .slide-leave-active {
  transition: margin-left 0.5s;
}
.slide-enter, .slide-leave-to {
  margin-left: -200px;
}
</style>