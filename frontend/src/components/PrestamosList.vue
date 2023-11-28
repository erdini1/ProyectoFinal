<template>
  <div class="m-5">
    <h1 class="font-bold text-4xl mr-4 m-5">Listado de Préstamos</h1>
    <div class="flex items-center justify-between mb-5">
      <button
        @click="abrirModalCrearPrestamo"
        class="bg-blue-500 hover:bg-blue-700 transition-all text-white px-4 py-2 rounded-md w-48 h-10"
      >
        Crear Préstamo
      </button>

      <div class="flex items-center">
        <div class="flex items-center">
          <label for="busqueda" class="text-sm font-bold mr-2">Buscar:</label>
          <input
            v-model="busquedaPorLibro"
            type="text"
            id="busqueda"
            placeholder="Buscar por titulo del libro"
            class="p-2 border-2 border-slate-300 rounded-md bg-gray-50 h-10 mr-2"
          />
        </div>

        <div class="flex items-center">
          <label for="fechaInicio" class="text-sm font-bold mr-2"
            >Rango de Fecha:</label
          >
          <input
            v-model="fechaInicio"
            type="date"
            id="fechaInicio"
            class="p-2 border-2 border-slate-300 rounded-md bg-gray-50 h-10 mr-2"
          />
          <label for="fechaFin" class="text-sm font-bold mr-2"> a </label>
          <input
            v-model="fechaFin"
            type="date"
            id="fechaFin"
            class="p-2 border-2 border-slate-300 rounded-md bg-gray-50 h-10"
          />
          <button
            @click="limpiarFechas"
            class="bg-blue-500 hover:bg-blue-700 transition-all text-white px-4 py-2 rounded-md w-30 h-10 text-sm"
          >
            Limpiar Fechas
          </button>
        </div>
      </div>
    </div>

    <div class="mx-auto w-4/4 rounded-lg border border-gray-200 shadow-md m-5">
      <table
        class="w-full border-collapse bg-white text-left text-sm text-gray-500"
      >
        <thead class="bg-gray-100">
          <tr>
            <th
              scope="col"
              class="px-6 py-4 font-medium text-gray-900"
              style="width: 50px"
            >
              ID
            </th>
            <th
              scope="col"
              class="px-4 py-4 font-medium text-gray-900"
              style="width: 150px"
            >
              Fecha Préstamo
            </th>
            <th
              scope="col"
              class="px-4 py-4 font-medium text-gray-900"
              style="width: 150px"
            >
              Fecha Devolución
            </th>
            <th
              scope="col"
              class="px-10 py-4 font-medium text-gray-900"
              style="width: 300px"
            >
              Libros
            </th>
            <th
              scope="col"
              class="px-6 py-4 font-medium text-gray-900"
              style="width: 100px"
            >
              Acciones
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-300 border-t border-gray-100">
          <tr
            v-for="prestamo in prestamos"
            :key="prestamo.id"
            class="hover:bg-gray-50"
          >
            <td class="px-6 py-4">{{ prestamo.id }}</td>
            <td class="px-4 py-4">{{ prestamo.fprestamo }}</td>
            <td class="px-4 py-4">{{ prestamo.fdevolucion }}</td>
            <td class="px-8 py-4">
              <LibrosAsociados :libros="prestamo.libros" />
            </td>
            <td class="px-6 py-4">
              <div class="flex justify-start gap-4">
                <a
                  @click="eliminarPrestamo(prestamo.id)"
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
                  @click="abrirModalEditarPrestamo(prestamo.id)"
                  x-data="{ tooltip: 'Editar' }"
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
                <a
                  v-if="prestamo.fdevolucion == null"
                  @click="abrirModalFinalizar(prestamo.id)"
                  x-data="{ tooltip: 'Finalizar' }"
                  href="#"
                  class="cursor-pointer"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke-width="1.5"
                    stroke="currentColor"
                    class="w-6 h-6"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      d="M4.5 12.75l6 6 9-13.5"
                    />
                  </svg>
                </a>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- MODAL CREAR -->
    <el-dialog
      v-model="crearPrestamoVisible"
      title="Crear Nuevo Préstamo"
      :z-index="1001"
    >
      <form @submit.prevent="crearPrestamo">
        <div class="mb-4">
          <label for="fechaPrestamo" class="block text-sm font-bold mb-1">
            Fecha Préstamo:
          </label>
          <el-date-picker
            v-model="nuevoPrestamo.fprestamo"
            type="date"
            placeholder="Seleccione una Fecha"
            :size="large"
            format="DD/MM/YYYY"
            :value-format="'DD-MM-YYYY'"
            id="fechaDevolucion"
            :required="true"
            class="w-full border-2 border-slate-300 rounded-md bg-gray-50"
          />
        </div>

        <div class="mb-4">
          <label class="block text-sm font-bold mb-3">Agregar Libro:</label>
          <el-select
            v-model="selectedBook"
            placeholder="Buscar libro"
            filterable
          >
            <el-option
              v-for="libro in librosDisponibles"
              :key="libro.id"
              :label="libro.titulo"
              :value="libro.id"
            />
          </el-select>
          <el-button @click="agregarLibroSeleccionado">Agregar</el-button>
        </div>

        <div class="mb-4">
          <label class="block text-sm font-bold mb-1"
            >Libros Seleccionados:</label
          >
          <ul class="flex flex-wrap gap-2 justify-center text-center">
            <li
              v-for="libro in librosSeleccionados"
              :key="libro.id"
              class="border border-gray-300 rounded-md p-2 flex items-center"
            >
              <span class="mr-2">{{ libro.titulo }}</span>
              <button @click="eliminarLibroSeleccionado(libro)">
                <svg
                  fill="none"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  viewBox="0 0 24 24"
                  class="w-4 h-4"
                >
                  <path d="M6 18L18 6M6 6l12 12"></path>
                </svg>
              </button>
            </li>
          </ul>
        </div>

        <div class="col-span-2">
          <button
            type="submit"
            class="bg-blue-500 hover:bg-blue-700 transition-all text-white px-4 py-2 rounded-md w-72"
          >
            Crear Préstamo
          </button>
        </div>
      </form>
    </el-dialog>

    <!-- MODAL MODIFICAR -->
    <el-dialog
      v-model="editarPrestamoVisible"
      title="Editar Préstamo"
      :z-index="1001"
    >
      <form @submit.prevent="guardarModificacionPrestamo">
        <div class="mb-4">
          <label for="fechaPrestamo" class="block text-sm font-bold mb-1">
            Fecha Préstamo:
          </label>
          <input
            v-model="formattedDate"
            class="border-2 border-gray-300 rounded-md p-2 w-72 focus:outline-none focus:border-blue-500"
            type="date"
            placeholder="Seleccione una Fecha"
          />
        </div>

        <div class="mb-4">
          <label for="fechaDevolucion" class="block text-sm font-bold mb-1">
            Fecha Devolución:
          </label>
          <input
            v-model="formattedDevolucion"
            class="border-2 border-gray-300 rounded-md p-2 w-72 focus:outline-none focus:border-blue-500"
            type="date"
            placeholder="Seleccione una Fecha"
          />
        </div>

        <div class="mb-4">
          <label class="block text-sm font-bold mb-3">Agregar Libro:</label>
          <el-select
            v-model="selectedBook"
            placeholder="Buscar libro"
            filterable
          >
            <el-option
              v-for="libro in librosDisponibles"
              :key="libro.id"
              :label="libro.titulo"
              :value="libro.id"
            />
          </el-select>
          <el-button @click="agregarLibroSeleccionado">Agregar</el-button>
        </div>

        <div class="mb-4">
          <label class="block text-sm font-bold mb-1"
            >Libros Seleccionados:</label
          >
          <ul class="flex flex-wrap gap-2 justify-center text-center">
            <li
              v-for="libro in librosSeleccionados"
              :key="libro.id"
              class="border border-gray-300 rounded-md p-2 flex items-center"
            >
              <span class="mr-2">{{ libro.titulo }}</span>
              <button @click="eliminarLibroSeleccionado(libro)">
                <svg
                  fill="none"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  viewBox="0 0 24 24"
                  class="w-4 h-4"
                >
                  <path d="M6 18L18 6M6 6l12 12"></path>
                </svg>
              </button>
            </li>
          </ul>
        </div>

        <div class="col-span-2">
          <button
            type="submit"
            class="bg-blue-500 hover:bg-blue-700 transition-all text-white px-4 py-2 rounded-md w-72"
          >
            Guardar Cambios
          </button>
        </div>
      </form>
    </el-dialog>

    <!-- MODAL FINALIZAR -->
    <el-dialog v-model="modalFinalizarVisible" title="Finalizar Préstamo" :z-index="1001">
      <div class="mb-4">
        <label for="fechaDevolucion" class="block text-sm font-bold mb-1">
          Fecha Devolución:
        </label>
        <input
          v-model="formattedDevolucion"
          class="border-2 border-gray-300 rounded-md p-2 w-72 focus:outline-none focus:border-blue-500"
          type="date"
          placeholder="Seleccione una Fecha"
        />
      </div>
      <el-button type="primary" @click="guardarFechaDevolucion"
        >Finalizar Prestamo</el-button
      >
    </el-dialog>

  </div>
</template>

<script>
import axios from "axios";
import env from "../../env";
import { configurarTokenAutorizacion } from "../utils/token";
import LibrosAsociados from "./LibrosAsociados.vue";
import Swal from "sweetalert2";
import { format } from "date-fns";

export default {
  data() {
    return {
      prestamos: [],
      librosDisponibles: [],
      nuevoPrestamo: {
        fprestamo: "",
        fdevolucion: "",
        libros: [],
      },
      crearPrestamoVisible: false,
      prestamoSeleccionado: {
        id: null,
        fprestamo: "",
        fdevolucion: "",
        libros: [],
      },
      selectedBook: null,
      librosSeleccionados: [],
      editarPrestamoVisible: false,
      selectedBooksFilter: "",
      availableBooksFilter: "",
      formattedDate: "",
      formattedDevolucion: "",
      busquedaPorLibro: "",
      campoFiltrado: "titulo",
      fechaInicio: "",
      fechaFin: "",
      modalFinalizarVisible: false,
    };
  },
  components: {
    LibrosAsociados,
  },
  methods: {
    agregarLibroSeleccionado() {
      if (this.selectedBook) {
        console.log(this.selectedBook);
        const libroSeleccionado = this.librosDisponibles.find(
          (libro) => libro.id === this.selectedBook
        );
        this.librosSeleccionados.push(libroSeleccionado);
        this.librosDisponibles = this.librosDisponibles.filter(
          (libro) => libro.id !== this.selectedBook
        );
        this.selectedBook = null;
      }
    },
    eliminarLibroSeleccionado(libro) {
      this.librosSeleccionados = this.librosSeleccionados.filter(
        (libroSeleccionado) => libroSeleccionado.id !== libro.id
      );

      this.librosDisponibles.push(libro);
      this.selectedBook = null;
    },
    abrirModalCrearPrestamo() {
      this.crearPrestamoVisible = true;
    },
    async cargarPrestamos() {
      const config = configurarTokenAutorizacion();
      if (!config) {
        return;
      }
      try {
        const response = await axios.get(
          `${env.API_ENDPOINT}/prestamos`,
          config
        );
        this.prestamos = response.data;
      } catch (error) {
        console.error("Error al cargar prestamos", error);
      }
    },
    async cargarLibrosDisponibles() {
      const config = configurarTokenAutorizacion();
      if (!config) {
        return;
      }
      try {
        const response = await axios.get(
          `${env.API_ENDPOINT}/libros/disponibles`,
          config
        );
        this.librosDisponibles = response.data.map((libro) => ({
          id: libro.id,
          titulo: libro.titulo,
        }));
      } catch (error) {
        console.error("Error al cargar libros disponibles", error);
      }
    },
    async crearPrestamo() {
      const librosFormateados = this.librosSeleccionados.map((libro) => ({
        id: libro.id,
      }));

      const nuevoPrestamo = {
        fprestamo: this.nuevoPrestamo.fprestamo,
        libros: librosFormateados,
      };

      const config = configurarTokenAutorizacion();
      if (!config) {
        return;
      }

      try {
        const response = await axios.post(
          `${env.API_ENDPOINT}/prestamos`,
          nuevoPrestamo,
          config
        );

        this.crearPrestamoVisible = false;
        this.librosSeleccionados = [];

        await Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Préstamo creado correctamente",
        });

        this.cargarPrestamos();
      } catch (error) {
        console.error("Error al crear préstamo", error);
        await Swal.fire({
          icon: "error",
          title: "Error",
          text: "Hubo un error al crear el préstamo",
        });
      }
    },
    filterMethod(query, option) {
      return (
        option.label && option.label.toLowerCase().includes(query.toLowerCase())
      );
    },
    async eliminarPrestamo(prestamoId) {
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
          await axios.delete(
            `${env.API_ENDPOINT}/prestamos/${prestamoId}`,
            config
          );
          await Swal.fire({
            icon: "success",
            title: "Éxito",
            text: "Préstamo eliminado correctamente",
          });

          const index = this.prestamos.findIndex(
            (prestamo) => prestamo.id === prestamoId
          );

          // Elimina el préstamo del array existente
          if (index !== -1) {
            this.prestamos.splice(index, 1);
          }
        }
      } catch (error) {
        console.error("Error al eliminar préstamo", error);
      }
    },
    abrirModalEditarPrestamo(prestamoId) {
      const prestamo = this.prestamos.find((p) => p.id === prestamoId);

      if (prestamo) {
        this.prestamoSeleccionado = { ...prestamo };

        this.cargarLibrosDisponibles();

        const librosPrestamo = prestamo.libros;

        this.librosSeleccionados = [...librosPrestamo];

        this.librosDisponibles = this.librosDisponibles.filter(
          (libro) =>
            !librosPrestamo.some(
              (libroPrestamo) => libroPrestamo.id === libro.id
            )
        );

        this.editarPrestamoVisible = true;
      }

      if (this.prestamoSeleccionado.fprestamo) {
        const [day, month, year] =
          this.prestamoSeleccionado.fprestamo.split("-");
        this.formattedDate = `${year}-${month}-${day}`;
      }
      if (this.prestamoSeleccionado.fdevolucion) {
        const [day, month, year] =
          this.prestamoSeleccionado.fdevolucion.split("-");
        this.formattedDevolucion = `${year}-${month}-${day}`;
      }

      this.formattedDevolucion = null;
    },
    async guardarModificacionPrestamo() {
      try {
        const config = configurarTokenAutorizacion();

        if (!config) {
          return;
        }

        const librosFormateados = this.librosSeleccionados.map((libro) => ({
          id: libro.id,
        }));

        this.prestamoSeleccionado.libros = librosFormateados;
        this.prestamoSeleccionado.fprestamo = this.formattedDate
          .split("-")
          .reverse()
          .join("-");

        if (this.formattedDevolucion) {
          this.prestamoSeleccionado.fdevolucion = this.formattedDevolucion
            .split("-")
            .reverse()
            .join("-");
        }

        console.log(this.prestamoSeleccionado);
        const response = await axios.put(
          `${env.API_ENDPOINT}/prestamos/${this.prestamoSeleccionado.id}`,
          this.prestamoSeleccionado,
          config
        );

        const index = this.prestamos.findIndex(
          (prestamo) => prestamo.id === this.prestamoSeleccionado.id
        );
        if (index !== -1) {
          this.prestamos.splice(index, 1, response.data);
        }

        this.editarPrestamoVisible = false;
        this.librosSeleccionados = [];
        this.cargarPrestamos();

        await Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Préstamo modificado correctamente",
        });
      } catch (error) {
        console.error("Error al modificar préstamo", error);

        await Swal.fire({
          icon: "error",
          title: "Error",
          text: "Hubo un error al modificar el préstamo",
        });
      }
    },
    async buscarPrestamos() {
      const config = configurarTokenAutorizacion();
      if (!config) {
        return;
      }

      const params = {};
      if (this.fechaInicio !== "") {
        params.fechaInicio = format(new Date(this.fechaInicio), "dd-MM-yyyy");
      }
      if (this.fechaFin !== "") {
        params.fechaFin = format(new Date(this.fechaFin), "dd-MM-yyyy");
      }

      try {
        const response = await axios.get(`${env.API_ENDPOINT}/prestamos`, {
          config,
          params,
        });

        let prestamosFiltrados = response.data;

        if (this.busquedaPorLibro) {
          prestamosFiltrados = prestamosFiltrados.filter((prestamo) =>
            prestamo.libros.some((libro) =>
              libro.titulo
                .toLowerCase()
                .includes(this.busquedaPorLibro.toLowerCase())
            )
          );
        }

        this.prestamos = prestamosFiltrados;
      } catch (error) {
        console.error("Error al cargar prestamos", error);
      }
    },
    limpiarFechas() {
      this.fechaInicio = "";
      this.fechaFin = "";
      this.buscarPrestamos();
    },
    abrirModalFinalizar(prestamoId) {
      const prestamo = this.prestamos.find((p) => p.id === prestamoId);

      if (prestamo) {
        this.prestamoSeleccionado = { ...prestamo };
        console.log(this.prestamoSeleccionado);

        this.modalFinalizarVisible = true;

        if (this.prestamoSeleccionado.fdevolucion) {
          const [day, month, year] =
            this.prestamoSeleccionado.fdevolucion.split("-");
          this.formattedDevolucion = `${year}-${month}-${day}`;
        }
      }
    },
    cerrarModalFinalizar() {
      this.modalFinalizarVisible = false;
    },
    async guardarFechaDevolucion() {
      const config = configurarTokenAutorizacion();
      if (!config) {
        return;
      }
      try {
        const fdevolucion = this.formattedDevolucion
          .split("-")
          .reverse()
          .join("-");

        const response = await axios.put(
          `${env.API_ENDPOINT}/prestamos/devolucion/${this.prestamoSeleccionado.id}`,
          { fdevolucion },
          config
        );

        this.formattedDevolucion = ""
        this.cargarPrestamos();
        

        await Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Prestamo finalizado correctamente",
        });

        this.modalFinalizarVisible = false;
      } catch (error) {
        console.error("Error al finalizar el préstamo", error);

        await Swal.fire({
          icon: "error",
          title: "Error",
          text: "Hubo un error al finalizar el préstamo",
        });
      }
    },
  },
  watch: {
    busquedaPorLibro: "buscarPrestamos",
    campoFiltrado: "buscarPrestamos",
    fechaInicio: "buscarPrestamos",
    fechaFin: "buscarPrestamos",
  },
  mounted() {
    this.cargarPrestamos();
    this.cargarLibrosDisponibles();
  },
};
</script>
