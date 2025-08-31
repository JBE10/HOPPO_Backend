## Autenticación JWT - Cómo obtener y usar el Bearer Token

### 1) Registrar usuario (solo si aún no tenés uno)
Endpoint: POST `/api/v1/auth/register`

Body (JSON):
```json
{
  "firstname": "Juan",
  "lastname": "Perez",
  "email": "juan@demo.com",
  "password": "Secret123",
  "role": "COMPRADOR"
}
```

curl:
```bash
curl -X POST "http://localhost:8081/api/v1/auth/register" \
  -H "Content-Type: application/json" \
  -d '{"firstname":"Juan","lastname":"Perez","email":"juan@demo.com","password":"Secret123","role":"COMPRADOR"}'
```

### 2) Autenticar y obtener el token
Endpoint: POST `/api/v1/auth/authenticate`

Body (JSON):
```json
{
  "email": "juan@demo.com",
  "password": "Secret123"
}
```

Respuesta exitosa:
```json
{ "access_token": "<JWT>" }
```

curl:
```bash
curl -X POST "http://localhost:8081/api/v1/auth/authenticate" \
  -H "Content-Type: application/json" \
  -d '{"email":"juan@demo.com","password":"Secret123"}'
```

### 3) Usar el token (Bearer) en endpoints protegidos
- Todas las rutas FUERA de `/api/v1/auth/**` requieren header `Authorization: Bearer <JWT>`.

Ejemplos curl:
```bash
# Listar productos
curl "http://localhost:8081/products" \
  -H "Authorization: Bearer <JWT>"

# Crear marca
curl -X POST "http://localhost:8081/brands" \
  -H "Authorization: Bearer <JWT>" \
  -H "Content-Type: application/json" \
  -d '{"name":"Apple"}'
```

### 4) Usarlo en Postman
1. Crear una request a `POST /api/v1/auth/authenticate` con Body (raw JSON) y obtener el `access_token`.
2. En tus demás requests, agregá Header:
   - Key: `Authorization`
   - Value: `Bearer <pegar_access_token>`
   (o usá el tipo `Bearer Token` en la pestaña Authorization y pegá el token)

### 5) Errores comunes y diagnóstico
- 401 Unauthorized: token ausente, mal formado o expirado.
- 403 Forbidden: token presente pero sin permisos o no se aplicó correctamente el header.
- Prefijo exacto: debe ser `Bearer <espacio><token>` (respetar mayúsculas y el espacio).
- Si cambiaste la clave JWT o reiniciaste la app, reautenticá para obtener un token nuevo.

### 6) Configuración relevante
- Puerto: `server.port=8081`
- Propiedades JWT en `application.properties`:
```
application.security.jwt.expiration=86400000
```
