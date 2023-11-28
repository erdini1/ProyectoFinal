<template>
  <h1 class="m-5 font-bold text-4xl">Listado de Usuarios</h1>
  <div class="m-5">
    <div class="mx-auto w-2/4 rounded-lg border border-gray-200 shadow-md m-5">
      <table
        class="w-full border-collapse bg-white text-left text-sm text-gray-500"
      >
        <thead class="bg-gray-100">
          <tr>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">ID</th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Nombre
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Email
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Acciones
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-300 border-t border-gray-100">
          <tr
            v-for="usuario in usuarios"
            :key="usuario.id"
            class="hover:bg-gray-50"
          >
            <td class="px-6 py-4">{{ usuario.id }}</td>
            <td class="px-6 py-4">
              {{ usuario.nombre }} {{ usuario.apellido }}
            </td>
            <td class="px-6 py-4">{{ usuario.email }}</td>
            <td class="px-6 py-4">
              <div class="flex justify-start gap-4">
                <a
                  @click="eliminarUsuario(usuario.id)"
                  x-data="{ tooltip: 'Eliminar' }"
                  href="#"
                  class="cursor-pointer"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="1.5"
                    stroke="currentColor"
                    class="h-6 w-6"
                    x-tooltip="tooltip"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
                    />
                  </svg>
                </a>
                <a
                  @click="modificarUsuario(usuario.id)"
                  x-data="{ tooltip: 'Eliminar' }"
                  href="#"
                  class="cursor-pointer"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="1.5"
                    stroke="currentColor"
                    class="h-6 w-6"
                    x-tooltip="tooltip"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L6.832 19.82a4.5 4.5 0 01-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 011.13-1.897L16.863 4.487zm0 0L19.5 7.125"
                    />
                  </svg>
                </a>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <el-dialog v-model="modificarUsuarioVisible" title="Modificar Usuario">
      <form @submit.prevent="guardarModificacion">
        <div class="flex mb-4" v-if="usuarioSeleccionado">
          <div class="w-1/2 pr-2">
            <label for="nombre" class="block text-sm font-bold mb-1"
              >Nombre:</label
            >
            <input
              v-model="usuarioSeleccionado.nombre"
              type="text"
              id="nombre"
              required
              class="w-full p-2 border rounded-md"
            />
          </div>

          <div class="w-1/2 pl-2">
            <label for="apellido" class="block text-sm font-bold mb-1"
              >Apellido:</label
            >
            <input
              v-model="usuarioSeleccionado.apellido"
              type="text"
              id="apellido"
              required
              class="w-full p-2 border rounded-md"
            />
          </div>
        </div>

        <div class="mb-4" v-if="usuarioSeleccionado">
          <label for="email" class="block text-sm font-bold mb-1">Email:</label>
          <input
            v-model="usuarioSeleccionado.email"
            type="email"
            id="email"
            required
            class="w-full p-2 border rounded-md"
          />
        </div>

        <div class="flex items-center justify-center">
          <button
            type="submit"
            class="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600 flex items-center justify-center"
          >
            Guardar Cambios
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="h-6 w-6 ml-2"
              x-tooltip="tooltip"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L6.832 19.82a4.5 4.5 0 01-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 011.13-1.897L16.863 4.487zm0 0L19.5 7.125"
              />
            </svg>
          </button>
        </div>
      </form>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import { ref } from "vue";
import env from "../../env.js";

export default {
  data() {
    return {
      usuarios: [],
      usuarioSeleccionado: null,
      modificarUsuarioVisible: ref(false),
    };
  },

  methods: {
    async eliminarUsuario(usuarioId) {
      try {
        const confirmacion = await Swal.fire({
          title: "¿Estás seguro?",
          text: "Esta acción no se puede deshacer.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Sí, eliminarlo",
          cancelButtonText: "Cancelar"
        });
        if (confirmacion.isConfirmed) {
          await axios.delete(`${env.API_ENDPOINT}/usuarios/${usuarioId}`);

          await Swal.fire({
            icon: "success",
            title: "Exito",
            text: "Usuario eliminado correctamente",
          });

          const index = this.usuarios.findIndex(
            (usuario) => usuario.id === usuarioId
          );

          if (index !== -1) {
            this.usuarios.splice(index, 1);
          }
        }
      } catch (error) {
        console.error("Error al eliminar usuario", error);
      }
    },
    modificarUsuario(usuarioId) {
      this.usuarioSeleccionado = this.usuarios.find(
        (usuario) => usuario.id === usuarioId
      );

      this.modificarUsuarioVisible = true;
    },
    async guardarModificacion() {
      try {
        const correoExistente = this.usuarios.some(
          (usuario) =>
            usuario.email === this.usuarioSeleccionado.email &&
            usuario.id !== this.usuarioSeleccionado.id
        );

        if (correoExistente) {
          await Swal.fire({
            icon: "error",
            title: "Error",
            text: "El correo electrónico ya existe",
          });
          return;
        }

        const response = await axios.put(
          `${env.API_ENDPOINT}/usuarios/${this.usuarioSeleccionado.id}`,
          this.usuarioSeleccionado
        );

        console.log("Usuario modificado con éxito");

        const index = this.usuarios.findIndex(
          (usuario) => usuario.id === this.usuarioSeleccionado.id
        );

        if (index !== -1) {
          Object.assign(this.usuarios[index], response.data);
        }

        this.usuarioSeleccionado = null;

        this.modificarUsuarioVisible = false;
      } catch (error) {
        console.error("Error al modificar usuario", error);
      }
    },
    async cargarUsuarios() {
      try {
        const response = await axios.get(`${env.API_ENDPOINT}/usuarios`);
        this.usuarios = response.data;
      } catch (error) {
        console.error("Error al cargar usuarios", error);
      }
    },
  },
  mounted() {
    this.cargarUsuarios();
  },
};
</script>
