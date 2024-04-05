<template>
  <div class="aside" style="width: 100%;height: 100%;">
    <el-row class="tac" style="height: 100%; width: 100%;">
      <el-col :span="24" style="height: 100%; width: 100%;">
        <el-menu
            mode="vertical"
            default-active="1"
            class="el-menu-vertical-demo"
            background-color="rgb(48,65,86)"
            text-color="#fff"
            router
            active-text-color="rgb(24,144,255)"
            style="height: 100%; width: 100%;">
          <!-- 首页 -->
          <el-menu-item index="/home/main">
            <el-icon>
              <House/>
            </el-icon>
            <span>信息统计</span>
          </el-menu-item>
          <!-- 资产管理 -->
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Expand /></el-icon>
              <span>固定图库</span>
            </template>
            <el-menu-item index="/home/pin">
              <el-icon><PictureFilled /></el-icon>
              固定图库管理
            </el-menu-item>
          </el-sub-menu>
          <!-- 规则管理 -->
          <el-sub-menu index="3">
            <template #title>
              <el-icon><Expand /></el-icon>
              <span>随机图库</span>
            </template>
            <el-menu-item index="/home/ran"> <el-icon><PictureFilled /></el-icon>随机图库管理</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </el-row>

  </div>
</template>
<script setup>
import {Expand, House, PictureFilled} from "@element-plus/icons-vue";
</script>
