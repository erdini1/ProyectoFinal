<template>
  <div
    class="w-full min-h-screen bg-gray-50 flex flex-col sm:justify-center items-center pt-6 sm:pt-0"
  >
    <div class="w-full sm:max-w-md p-5 mx-auto">
      <h2 class="mb-12 text-center text-5xl font-extrabold text-blue-600">Bienvenido</h2>
      <form @submit.prevent="login">
        <div class="mb-4">
          <label class="block mb-1" for="email">Email</label>
          <input
            id="email"
            type="text"
            name="email"
            v-model="email"
            class="py-2 px-3 border border-gray-300 focus:border-blue-300 focus:outline-none focus:ring focus:ring-blue-200 focus:ring-opacity-50 rounded-md shadow-sm disabled:bg-gray-100 mt-1 block w-full"
          />
        </div>
        <div class="mb-4">
          <label class="block mb-1" for="contrasenia">Contraseña</label>
          <input
            id="contrasenia"
            type="password"
            name="contrasenia"
            v-model="contrasenia"
            class="py-2 px-3 border border-gray-300 focus:border-blue-300 focus:outline-none focus:ring focus:ring-blue-200 focus:ring-opacity-50 rounded-md shadow-sm disabled:bg-gray-100 mt-1 block w-full"
          />
        </div>

        <div class="mt-6">
          <button
            type="submit"
            class="w-full inline-flex items-center justify-center px-4 py-2 bg-blue-600 border border-transparent rounded-md font-semibold capitalize text-white hover:bg-blue-700 active:bg-blue-700 focus:outline-none focus:border-blue-700 focus:ring focus:ring-blue-200 disabled:opacity-25 transition"
          >
            Iniciar Sesión
          </button>
        </div>
        <div class="mt-6 text-center">
          <a href="/registro" class="underline">Registrarse</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import env from "../../../env";

export default {
  data() {
    return {
      email: "",
      contrasenia: "",
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post(
          `${env.API_ENDPOINT}/usuarios/login`,
          {
            email: this.email,
            contrasenia: this.contrasenia,
          }
        );

        this.$store.commit("setToken", response.data.mensaje);

        await Swal.fire({
          icon: "success",
          title: "Exito",
          text: "Inicio sesión correctamente",
        });

        this.$router.push("/usuarios");
      } catch (error) {
        if (error.response && error.response.status === 401) {
          await Swal.fire({
            icon: "error",
            title: "Error",
            text: "Credenciales Invalidas",
          });
        } else {
          await Swal.fire({
            icon: "error",
            title: "Error",
            text: "Error al loguearse",
          });
        }
      }
    },
  },
};
</script>
