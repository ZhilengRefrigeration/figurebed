<template>
  <div class="common-layout">
    <el-container>
      <div class="com">
        <!-- 面包屑 -->
        <div class="head">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>固定图库管理</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <el-card class="box-card">
          <el-form :inline="true" label-width="auto" style="max-width: 600px">
            <el-form-item label="图片名">
              <el-input type="text" v-model="this.pageBean.search"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="Search" @click="search">搜索一下</el-button>
            </el-form-item>
          </el-form>
          <div class="demo-image">

            <el-upload
                v-model:file-list="listImg"
                :action="serverUrl+'/pin/massUpload'"
                :headers="{ 'token': this.token}"
                list-type="picture-card"
                :name="'fileName'"
                :multiple=true
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                :on-error="handleError"
                :on-preview="handlePictureCardPreview"
            >
              <el-text type="success">上传图片</el-text>
              <template #file="{ file }">
                <div class="file-info-container">
                  <el-text type="info">{{file.name}}</el-text>
                  <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
                  <span class="el-upload-list__item-actions">
          <span
              class="el-upload-list__item-preview"
              @click="handlePictureCardPreview(file)"
          >
            <el-icon><zoom-in /></el-icon>
          </span>
          <span
              class="el-upload-list__item-delete"
              @click="handleDownload(file)"
          >
            <el-icon><Download /></el-icon>
          </span>
          <span
              class="el-upload-list__item-delete"
              @click="handleRemove(file)"
          >
            <el-icon><Delete /></el-icon>
          </span>
        </span>
                </div>
              </template>
            </el-upload>

          </div>
          <el-dialog v-model="dialogVisible">
            <img w-full :src="dialogImageUrl" alt="Preview Image" style="max-width: 100%; height: auto;"/>
          </el-dialog>
          <br/>
          <el-pagination
              v-model:current-page="this.pageBean.pageNum"
              v-model:page-size="this.pageBean.pageSize"
              :page-sizes="[10, 20,30,40, 50]"
              :background="true"
              layout="total, sizes, prev, pager, next, jumper"
              :total="Number(this.imgSize)"
              @size-change="search"
              @current-change="search"
          />
        </el-card>
      </div>
    </el-container>
  </div>
</template>
<script>
import {ElMessage} from 'element-plus';
import {serverUrl} from '@/static/config.json';
import {Delete, Download, Search, ZoomIn} from "@element-plus/icons-vue";
export default {
  components: {Delete, Download, ZoomIn},
  data(){
    return{
      pageBean:{
        pageNum:1,
        pageSize:30,
        search:""
      },
      form:{},
      token:localStorage.getItem('token'),
      listImg:[],
      imgSize:1,
      serverUrl:serverUrl,
      fileName:'',
      dialogImageUrl:{},
      dialogVisible: false,
    }
  },created() {
    this.$axios.post("/pin/getImg", this.pageBean)
    .then(res => {
      if(res.data.code === 200){
        this.imgSize = res.data.msg
        res.data.data.forEach(item=>{this.listImg.push({"name":item.split('=')[1],"url":item})})
      }else{
          this.$router.push({path:'/'})
        ElMessage.warning(res.data.msg)
      }
    }).catch(()=>{
      ElMessage.error("服务器错误！")
    })
  },
  methods:{
    handleRemove(uploadFile){
      this.$axios.post("/pin/delImg", [{name:uploadFile.name}])
          .then(res => {
            if(res.data.code === 200){
              const index = this.listImg.findIndex(item => item.name === uploadFile.name && item.url === uploadFile.url);
              if (index !== -1) {
                this.listImg.splice(index, 1);
              }
              ElMessage.success(uploadFile.name+"删除成功")
            }
          })
      return true;
    },
    handleSuccess(response, file) {
      for (const key in response.data) {
       if(response.data[key] ==='上传失败'){
         this.listImg.push({"name":key,"url":response.data[key]})
         ElMessage.error(key+"文件"+response.data[key])
       }else{
         ElMessage.success(key+"文件上传成功")
       }
      }
    },
    handleError() {
      ElMessage.error("服务器错误")
    },
    search(){
      this.$axios.post("/pin/getImg", this.pageBean)
          .then(res => {
            if(res.data.code === 200){
              this.listImg=[];
              res.data.data.forEach(item=>{this.listImg.push({"name":item.split('=')[1],"url":item})})
            }else{
              ElMessage.warning(res.data.msg)
            }
          }).catch(()=>{
        ElMessage.error("服务器错误！")
      })
    },
    handlePictureCardPreview(uploadFile){
      this.dialogImageUrl = uploadFile.url;
      this.dialogVisible = true
    },
    handleDownload(file) {
      fetch(file.url)
          .then(response => {
            return response.blob();
          })
          .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', file.name);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(url);
          })
          .catch(error => {
            console.error('错误:', error);
          });
    }


  }

}
</script>
<style scoped>
.demo-image .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 20%;
  box-sizing: border-box;
  vertical-align: top;
}
.demo-image .block:last-child {
  border-right: none;
}
.demo-image .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
.file-info-container {
  display: flex; /* 设置为Flex布局 */
  flex-direction: column; /* 垂直排列 */
  align-items: center; /* 水平居中 */
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
