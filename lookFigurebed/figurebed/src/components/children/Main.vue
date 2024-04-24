<template>
  <div class="common-layout">
    <el-container>
      <div class="com">
        <!-- 面包屑 -->
        <div class="head">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home/mian' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>信息一览</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <el-card class="box-card">
          <el-form :inline="true">
            <el-form-item><el-text style="width: 170vh"></el-text></el-form-item>
            <el-form-item>
              <el-date-picker
                  v-model="dataSel"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="截至日期"
                  value-format="YYYY-MM-DD"
                  size="default"
              />
            </el-form-item>
            <el-form-item>
              <el-button @click="refreshData" type="primary" icon="RefreshLeft">刷新数据</el-button>
            </el-form-item>
          </el-form>
          <el-row>
            <el-col :span="4"><div/></el-col>
            <el-col :span="20"><div id="line-chart"  style="height: 40vh;width: 100vh; "></div></el-col>
          </el-row>
        </el-card>
      </div>
    </el-container>
  </div>
</template>
<script>
import {ElMessage} from 'element-plus';
import {Delete, Download, RefreshLeft, ZoomIn} from "@element-plus/icons-vue";
import * as echart from "echarts";
export default {
  computed: {
    RefreshLeft() {
      return RefreshLeft
    }
  },
  components: {Delete, Download, ZoomIn},
  data(){
    return{
      dataSel:[],
      dateBean:{
        startDate:new Date(new Date().setMonth(new Date().getMonth()-1)),
        endDate:new Date(),
        search:''
      },
      myChart:{},
      seriesData:[],
      option:{
        title: {
          text: '接口请求数量'
        },
        legend: {
          orient: 'vertical',
          right: 10,
          top: 'center'
        },
        tooltip: {},
        xAxis: {
          type: "category",
          name:'日期',
          data: []
        },
        yAxis: {
            name:'请求量',
        },
        series: [
        ]
      }
    }
  },
  mounted() {
    this.myChart = echart.init(document.getElementById('line-chart'));
    this.refreshData()
  },
  methods:{
    refreshData(){
      if(this.dataSel.length>=2){
        this.dateBean.startDate = this.dataSel[0];
        this.dateBean.endDate = this.dataSel[1];
      }
      this.option.series=[];
      this.option.xAxis.data= [];
      this.$axios.post("/log/getAllLog", this.dateBean, )
          .then(res => {
            if(res.data.code === 200){
              res.data.data.forEach(item=>{
                if(!this.option.xAxis.data.includes(item.interfaceDate)){
                  this.option.xAxis.data.push(item.interfaceDate);
                }
                if(-1 === this.option.series.findIndex(items=>items.name === item.interfaceName)){
                  this.option.series.push({
                    data: [item.interfaceCount],
                    type: 'line',
                    stack: item.interfaceName,
                    name:item.interfaceName
                  });
                }else{
                  for(let i=0;i<this.option.series.length;i++){
                    if(this.option.series[i].name === item.interfaceName){
                      this.option.series[i].data.push(item.interfaceCount);
                    }
                  }
                }
              })
              this.myChart.setOption(this.option);
            }else if(res.data.code === 500){
              ElMessage.error("不能小于一天")
            }else{
              this.$router.push({path:'/'});
              ElMessage.warning(res.data.msg);
            }
          })
          .catch(()=>{
            ElMessage.error("服务器错误！")
          })
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
  position: relative;
}


</style>
