<template>
  <div>
    <table
      class="w-full border-collapse bg-white text-left text-sm text-gray-500"
    >
      <thead class="bg-gray-100">
        <tr>
          <th scope="col" class="px-6 py-4 font-medium text-gray-900">ID</th>
          <th scope="col" class="px-4 py-4 font-medium text-gray-900">
            Título
          </th>
          <th scope="col" class="px-4 py-2 font-medium text-gray-900">Autor</th>
        </tr>
      </thead>
      <tbody class="divide-y divide-gray-300 border-t border-gray-100">
        <tr v-for="libro in libros" :key="libro.id" class="hover:bg-gray-50">
          <td class="px-6 py-4 w-16">{{ libro.id }}</td>
          <td class="px-4 py-4 w-48">{{ libro.titulo }}</td>
          <td class="px-4 py-4 w-40">{{ libro.autor }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    libros: {
      type: Array,
      default: () => [],
    },
  },
};
</script>
