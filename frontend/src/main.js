import App from './App.vue'
import router from './router'
import { createApp } from 'vue'
import store from "./store/index.js"
import "./assets/main.css"
//Element-Plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//PrimeVue
import PrimeVue from 'primevue/config';
import 'primevue/resources/themes/lara-light-green/theme.css'

const app = createApp(App)

app.use(router)
app.use(store);
app.use(ElementPlus)
app.use(PrimeVue);

app.mount('#app')
