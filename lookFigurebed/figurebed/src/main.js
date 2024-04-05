import { createApp } from 'vue'
import App from './App.vue'
import variable from "@/config/variable.js";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import serviceAxios from "@/config/axiosConfig.js";
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import router from "@/config/router.js";



const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.config.globalProperties.$axios = serviceAxios;
app.provide('serUrl', variable);
app.use(router)
app.use(ElementPlus)
app.mount('#app');