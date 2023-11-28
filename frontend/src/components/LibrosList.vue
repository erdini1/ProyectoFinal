<template>
  <div class="m-5">
    <h1 class="font-bold text-4xl mr-4 m-5">Listado de Libros</h1>
    <div class="flex items-center justify-between mb-5">
      <button
        @click="abrirModalCrearLibro"
        class="bg-blue-500 hover:bg-blue-700 transition-all text-white px-4 py-2 rounded-md w-48 h-10"
      >
        Crear Libro
      </button>
      <div class="flex items-center">
        <div class="flex items-center">
          <label for="busqueda" class="text-sm font-bold mr-2">Buscar:</label>
          <select
            v-model="campoFiltrado"
            @change="limpiarBusqueda"
            class="p-2 border-2 border-slate-300 rounded-md mr-2 h-10 bg-gray-50"
          >
            <option value="titulo">Título</option>
            <option value="autor">Autor</option>
            <option value="genero">Género</option>
            <option value="estado">Estado</option>
          </select>
          <input
            v-model="busqueda"
            type="text"
            id="busqueda"
            placeholder="Buscar"
            class="p-2 border-2 border-slate-300 rounded-md bg-gray-50 h-10"
          />
        </div>
      </div>
    </div>

    <div class="mx-auto w-4/4 rounded-lg border border-gray-200 shadow-md m-5">
      <table
        class="w-full border-collapse bg-white text-left text-sm text-gray-500"
      >
        <thead class="bg-gray-100">
          <tr>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">ID</th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Título
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Autor
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Género
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Cant Páginas
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Sinopsis
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Estado
            </th>
            <th scope="col" class="px-6 py-4 font-medium text-gray-900">
              Acciones
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-300 border-t border-gray-100">
          <tr v-for="libro in libros" :key="libro.id" class="hover:bg-gray-50">
            <td class="px-6 py-4">{{ libro.id }}</td>
            <td class="px-6 py-4">{{ libro.titulo }}</td>
            <td class="px-6 py-4">{{ libro.autor }}</td>
            <td class="px-6 py-4">{{ libro.genero }}</td>
            <td class="px-6 py-4">{{ libro.num_paginas }}</td>
            <td class="px-6 py-4">{{ libro.sinopsis }}</td>
            <td class="px-6 py-4">{{ libro.estado }}</td>
            <td class="px-6 py-4">
              <div class="flex justify-start gap-4">
                <a
                  v-if="libro.estado !== 'prestado'"
                  @click="eliminarLibro(libro.id)"
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
                  v-if="libro.estado !== 'prestado'"
                  @click="abrirModalModificarLibro(libro.id)"
                  x-data="{ tooltip: 'Editar' }"
                  href="#"
                  class="cursor-pointer"
                  :class="{ 'ml-4': libro.estado === 'Prestado' }"
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

    <!-- MODAL MODIFICAR -->
    <el-dialog v-model="modificarLibroVisible" title="Editar Libro">
      <form @submit.prevent="guardarModificacionLibro">
        <div class="grid grid-cols-2 gap-4">
          <div>
            <div class="mb-4" v-if="libroSeleccionado">
              <label for="titulo" class="block text-sm font-bold mb-1"
                >Título:</label
              >
              <input
                v-model="libroSeleccionado.titulo"
                type="text"
                id="titulo"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>

            <div class="mb-4">
              <label for="autor" class="block text-sm font-bold mb-1"
                >Autor:</label
              >
              <input
                v-model="libroSeleccionado.autor"
                type="text"
                id="autor"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>
          </div>

          <div>
            <div class="mb-4">
              <label for="genero" class="block text-sm font-bold mb-1"
                >Género:</label
              >
              <input
                v-model="libroSeleccionado.genero"
                type="text"
                id="genero"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>

            <div class="mb-4">
              <label for="num_paginas" class="block text-sm font-bold mb-1"
                >Cant Páginas:</label
              >
              <input
                v-model="libroSeleccionado.num_paginas"
                type="number"
                id="num_paginas"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>
          </div>

          <div class="col-span-2 mb-4">
            <label for="sinopsis" class="block text-sm font-bold mb-1"
              >Sinopsis:</label
            >
            <textarea
              v-model="libroSeleccionado.sinopsis"
              id="sinopsis"
              rows="4"
              required
              class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
            ></textarea>
          </div>

          <div class="col-span-2">
            <button
              type="submit"
              class="bg-blue-500 hover:bg-blue-700 transition-all text-white px-4 py-2 rounded-md w-72"
            >
              Guardar Cambios
            </button>
          </div>
        </div>
      </form>
    </el-dialog>

    <!-- MODAL CREAR -->
    <el-dialog v-model="crearLibroVisible" title="Crear Nuevo Libro">
      <form @submit.prevent="crearLibro">
        <div class="grid grid-cols-2 gap-4">
          <div>
            <div class="mb-4">
              <label for="titulo" class="block text-sm font-bold mb-1"
                >Título:</label
              >
              <input
                v-model="nuevoLibro.titulo"
                type="text"
                id="titulo"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>

            <div class="mb-4">
              <label for="autor" class="block text-sm font-bold mb-1"
                >Autor:</label
              >
              <input
                v-model="nuevoLibro.autor"
                type="text"
                id="autor"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>
          </div>

          <div>
            <div class="mb-4">
              <label for="genero" class="block text-sm font-bold mb-1"
                >Género:</label
              >
              <input
                v-model="nuevoLibro.genero"
                type="text"
                id="genero"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>

            <div class="mb-4">
              <label for="num_paginas" class="block text-sm font-bold mb-1"
                >Cant Páginas:</label
              >
              <input
                v-model="nuevoLibro.num_paginas"
                type="number"
                id="num_paginas"
                required
                class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
              />
            </div>
          </div>

          <div class="col-span-2 mb-4">
            <label for="sinopsis" class="block text-sm font-bold mb-1"
              >Sinopsis:</label
            >
            <textarea
              v-model="nuevoLibro.sinopsis"
              id="sinopsis"
              rows="4"
              required
              class="w-full p-2 border-2 border-slate-300 rounded-md bg-gray-50"
            ></textarea>
          </div>

          <div class="col-span-2">
            <button
              type="submit"
              class="bg-blue-500 hover:bg-blue-700 transition-all text-white px-4 py-2 rounded-md w-72"
            >
              Nuevo Libro
            </button>
          </div>
        </div>
      </form>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import { configurarTokenAutorizacion } from "../utils/token.js";
import { ref } from "vue";
import env from "../../env.js";

export default {
  data() {
    return {
      libros: [],
      libroSeleccionado: null,
      modificarLibroVisible: ref(false),
      crearLibroVisible: false,
      busqueda: "",
      campoFiltrado: "titulo",
    };
  },
  methods: {
    async eliminarLibro(libroId) {
      try {
        const config = configurarTokenAutorizacion();

        if (!config) {
          return;
        }

        const confirmacion = await Swal.fire({
          title: "¿Estás seguro?",
          text: "Esta acción no se puede deshacer.",
          icon: "warning",
          showCancelButton: true,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "Sí, eliminarlo",
          cancelButtonText: "Cancelar",
        });

        if (confirmacion.isConfirmed) {
          await axios.delete(`${env.API_ENDPOINT}/libros/${libroId}`, config);
          await Swal.fire({
            icon: "success",
            title: "Exito",
            text: "Libro eliminado correctamente",
          });

          const index = this.libros.findIndex((libro) => libro.id === libroId);

          if (index !== -1) {
            this.libros.splice(index, 1);
          }
        }

      } catch (error) {
        console.error("Error al eliminar libro", error);
      }
    },
    abrirModalModificarLibro(libroId) {
      const libro = this.libros.find((libro) => libro.id === libroId);
      if (libro) {
        this.libroSeleccionado = { ...libro };
        this.modificarLibroVisible = true;
      }
    },
    async guardarModificacionLibro() {
      try {
        const config = configurarTokenAutorizacion();

        if (!config) {
          return;
        }

        const response = await axios.put(
          `${env.API_ENDPOINT}/libros/${this.libroSeleccionado.id}`,
          this.libroSeleccionado,
          config
        );
        console.log("Libro modificado con éxito");

        const index = this.libros.findIndex(
          (libro) => libro.id === this.libroSeleccionado.id
        );

        if (index !== -1) {
          Object.assign(this.libros[index], response.data);
        }

        this.modificarLibroVisible = false;

        this.cargarLibros();

        await Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Libro modificado correctamente",
        });
      } catch (error) {
        console.error("Error al modificar libro", error);

        await Swal.fire({
          icon: "error",
          title: "Error",
          text: "Hubo un error al modificar el libro",
        });
      }
    },
    abrirModalCrearLibro() {
      this.nuevoLibro = {
        titulo: "",
        autor: "",
        genero: "",
        num_paginas: null,
        sinopsis: "",
      };
      this.modificarLibroVisible = false;
      this.crearLibroVisible = true;
    },
    async crearLibro() {
      try {
        const config = configurarTokenAutorizacion();

        if (!config) {
          return;
        }

        const response = await axios.post(
          `${env.API_ENDPOINT}/libros`,
          this.nuevoLibro, 
          config
        );

        this.libros.push(response.data);

        this.nuevoLibro = {
          titulo: "",
          autor: "",
          genero: "",
          num_paginas: null,
          sinopsis: "",
        };
        this.crearLibroVisible = false;

        this.cargarLibros();

        await Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Libro creado correctamente",
        });
      } catch (error) {
        console.error("Error al crear libro", error);

        await Swal.fire({
          icon: "error",
          title: "Error",
          text: "Hubo un error al crear el libro",
        });
      }
    },
    async cargarLibros() {
      const config = configurarTokenAutorizacion();

      if (!config) {
        return;
      }
      try {
        const response = await axios.get(`${env.API_ENDPOINT}/libros`, config);
        this.libros = response.data;
      } catch (error) {
        console.error("Error al cargar libros", error);
      }
    },
    filtrarLibros() {
      if (this.busqueda.trim() !== "") {
        const terminoBusqueda = this.busqueda.trim().toLowerCase();

        switch (this.campoFiltrado) {
          case "titulo":
            this.libros = this.libros.filter((libro) =>
              libro.titulo.toLowerCase().includes(terminoBusqueda)
            );
            break;
          case "estado":
            this.libros = this.libros.filter((libro) =>
              libro.estado.toLowerCase().includes(terminoBusqueda)
            );
            break;
          case "genero":
            this.libros = this.libros.filter((libro) =>
              libro.genero.toLowerCase().includes(terminoBusqueda)
            );
            break;
          case "autor":
            this.libros = this.libros.filter((libro) =>
              libro.autor.toLowerCase().includes(terminoBusqueda)
            );
            break;
          default:
            break;
        }
      } else {
        this.cargarLibros();
      }
    },
    limpiarBusqueda() {
      this.busqueda = "";
      this.filtrarLibros();
    },
  },
  watch: {
    busqueda: function () {
      this.filtrarLibros();
    },
  },
  mounted() {
    this.cargarLibros();
  },
};
</script>
