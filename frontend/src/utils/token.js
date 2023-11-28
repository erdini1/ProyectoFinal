
export function configurarTokenAutorizacion() {
  const token = localStorage.getItem('token');

  if (!token) {
    console.error('Token no encontrado. Redirigiendo a la página de inicio de sesión.');
    return null;
  }

  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  return config;
}