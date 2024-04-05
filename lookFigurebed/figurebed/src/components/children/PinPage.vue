<template>
  <div class="common-layout">
    <el-container>
      <div class="com">
        <!-- 面包屑 -->
        <div class="head">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>固定图库管理</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <el-card class="box-card">
          <!-- 提交的表单 -->

        </el-card>
      </div>
    </el-container>
  </div>
</template>
<script>
import { ElMessage } from 'element-plus';
export default {

}
</script>
<style scoped>
.demo-pagination-block {
  margin-left: 30%;
}

.button {
  display: inline;
}

.el-button--text {
  margin-right: 15px;
}

.el-select {
  width: 300px;
}

.el-input {
  width: 300px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}

.head {
  margin: 12px;
}

.com {
  height: 100%;
  padding: 0;
  background-color: aliceblue;
}

.box-card {
  height: 100%;
  width: 95%;
  margin: 0 auto;
}
</style>
