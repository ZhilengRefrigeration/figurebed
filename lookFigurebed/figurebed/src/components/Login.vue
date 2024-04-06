<template>
  <div id="building" v-bind:style="{background: 'url(' + serverUrl + '/ran/show?fileName=p1181.png)'}">
    <el-container>
      <el-main style="height: 80vh; display: flex; justify-content: center; align-items: center;">
        <el-row class="centered-row">
          <el-col :span="24" :offset="18">
            <el-form :model="form" label-width="auto" label-position="left" :size="'large'"  class="grid-content ep-bg-purple-light">
              <el-form-item label-width="">
                <el-text type="warning" style="font-family: 'Agency',serif;" size="large"><el-text type="danger">❤</el-text>{{form.text}}</el-text>
              </el-form-item>
              <el-form-item label="用户名">
                <el-input :prefix-icon="User" maxlength="10" placeholder="请输入用户名" type="" v-model="form.username"/>
              </el-form-item>
              <el-form-item label="密码">
                <el-input prefix-icon="Lock" maxlength="10" type="password"  placeholder="请输入密码" v-model="form.password"/>
              </el-form-item>
              <el-button plain :type="dataIsOk() ? 'danger' : 'success'"
                         :disabled="dataIsOk()"
                         @click="login"

              >{{dataIsOk() ? '用户名未填' : 'Master 登录'}}</el-button>
            </el-form>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import { onMounted } from 'vue'
import axios from "axios";
import {User} from "@element-plus/icons-vue";
import { ElMessage } from 'element-plus'
import {serverUrl} from '@/static/config.json';
import router from "@/config/router.js";
const form = reactive({
  username: '',
  password: '',
  text:'Hello Mater!'
})
onMounted(() => {
  axios.get('https://api.xygeng.cn/one')
      .then(data => {
        data = data.data
        if (data.code === 200) {
          if (data.data.content.length < 35) {
            form.text = data.data.content;
          }
        }else{
          this.$message("服务器错误");
        }
      })
      .catch(err=>{
        this.$message(err);
      })
})
const login = () => {
  if(dataIsOk()){
    ElMessage.warning("用户名和密码未填！")
  }else {
    let loginData = {
      userName: form.username,
      passWord: form.password
    }
    axios.post(serverUrl+"/master/login", JSON.stringify(loginData), {headers:{
        'Content-Type': 'application/json; charset=utf-8'
      }})
        .then(res=>{
          res = res.data
          if(res.code === 200){
            localStorage.setItem('token',res.data.tokenValue)
            ElMessage.success("登录成功 欢迎Master!")
            router.push("/home")
          }else{
            ElMessage.warning(res.msg)
          }
        })
        .catch((err)=>{
          console.log(err)
          ElMessage.error("服务器错误！")
        })
  }
}
const dataIsOk = () => {
  return (form.username.length < 3 || form.password.length < 3);
}
</script>

<style scoped>
#building{
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>