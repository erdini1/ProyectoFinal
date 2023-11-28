import { createRouter, createWebHistory } from 'vue-router';
import store from '../store';
import UsuariosList from '../components/UsuariosList.vue';
import LibrosList from '../components/LibrosList.vue';
import PrestamosList from '../components/PrestamosList.vue';
import RegistroForm from '../components/Auth/RegistroForm.vue';
import LoginForm from '../components/Auth/LoginForm.vue';

const routes = [
  { path: '/', redirect: '/usuarios' },
  {
    path: '/usuarios',
    component: UsuariosList, meta: { requiresAuth: true },
  },
  { path: '/libros', component: LibrosList, meta: { requiresAuth: true } },
  {
    path: '/prestamos', component: PrestamosList, meta: { requiresAuth: true }
  },
  {
    path: '/login',
    component: LoginForm,
  },
  {
    path: '/registro',
    component: RegistroForm,
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  console.log('isAuthenticated:', store.getters.isAuthenticated);
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.getters.isAuthenticated) {
      next('/login');
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;