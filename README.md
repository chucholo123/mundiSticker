# Mundisticker API - Documentación de Endpoints

Mundisticker es una plataforma para el intercambio y venta de stickers coleccionables, utilizando arquitectura hexagonal y geolocalización.

## 1. Usuarios (`/api/v1/usuarios`)

Gestión de perfiles de usuario y búsqueda por cercanía.

### Registrar Usuario
*   **Método:** `POST`
*   **Cuerpo (JSON):**
```json
{
  "nombre": "Juan Pérez",
  "contrasena": "securePass123",
  "latitud": -12.046374,
  "longitud": -77.042793
}
```
*   **Respuesta (201 Created):**
```json
{
  "id": "5b39c136-2aaa-43fa-9f81-5dbcd8c11770",
  "nombre": "Juan Pérez",
  "latitud": -12.046374,
  "longitud": -77.042793
}
```

### Buscar Usuarios Cercanos
*   **Método:** `GET`
*   **Query Params:** `latitud`, `longitud`, `distancia` (opcional, defecto 5000m).
*   **Ejemplo:** `/api/v1/usuarios/cercanos?latitud=-12.0&longitud=-77.0&distancia=10000`

---

## 2. Stickers (`/api/v1/stickers`)

Catálogo maestro de stickers disponibles.

### Listar Todos
*   **Método:** `GET`
*   **Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "numero": "ARG 10",
    "nombre": "Lionel Messi",
    "seleccion": "Argentina",
    "imagenUrl": "http://cdn.mundisticker.com/messi.png"
  }
]
```

---

## 3. Inventario (`/api/v1/inventario`)

Gestión de stickers repetidos y faltantes de cada usuario.

### Agregar al Inventario
*   **Método:** `POST`
*   **Path:** `/api/v1/inventario/usuario/{usuarioId}`
*   **Cuerpo (JSON):**
```json
{
  "idSticker": 1,
  "tipo": "REPETIDA",
  "cantidad": 3
}
```

### Buscar Usuarios con un Sticker Repetido
*   **Método:** `GET`
*   **Path:** `/api/v1/inventario/repetido/{stickerId}`
*   **Respuesta (200 OK):** `["uuid-usuario-1", "uuid-usuario-2"]`

---

## 4. Intercambios (`/api/v1/intercambios`)

Gestión de propuestas de intercambio abiertas o directas.

### Proponer Intercambio
*   **Método:** `POST`
*   **Cuerpo (JSON):**
```json
{
  "idUsuarioEmisor": "uuid-emisor",
  "idUsuarioReceptor": null, 
  "stickersOfrecidos": [1, 2, 5],
  "stickersBuscados": [10, 11]
}
```
*   **Nota:** Si `idUsuarioReceptor` es `null`, el intercambio es **ABIERTO**.

### Unirse a Intercambio Abierto
*   **Método:** `POST`
*   **Path:** `/api/v1/intercambios/{id}/unirse?receptorId={uuid}`
*   **Cuerpo (JSON):**
```json
{
  "stickersOfrecidos": [10, 11]
}
```

### Acciones de Estado
*   `PATCH /api/v1/intercambios/{id}/aceptar` (Completa la transacción y actualiza inventarios).
*   `PATCH /api/v1/intercambios/{id}/rechazar`
*   `PATCH /api/v1/intercambios/{id}/cancelar`

---

## 5. Ventas (`/api/v1/ventas`)

Publicación de stickers para venta directa o abierta.

### Publicar Venta
*   **Método:** `POST`
*   **Cuerpo (JSON):**
```json
{
  "idUsuarioEmisor": "uuid-vendedor",
  "idUsuarioReceptor": "uuid-comprador-opcional",
  "stickers": [1, 5, 9],
  "precio": 25.50
}
```

### Comprar Venta Abierta
*   **Método:** `POST`
*   **Path:** `/api/v1/ventas/{id}/comprar?compradorId={uuid}`

### Completar Venta
*   **Método:** `PATCH`
*   **Path:** `/api/v1/ventas/{id}/completar`
*   **Nota:** Dispara los hooks de actualización de inventario para vendedor y comprador.
