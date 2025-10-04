# 💰 Pagos Service

**Puerto**: 8085  
**Repositorio**: pagos-service  
**Base de Datos**: PostgreSQL (tabla `pagos`)

---

## 📋 Descripción

Microservicio responsable de la gestión del sistema de pagos de arriendos. Maneja el registro, seguimiento y cambio de estados de pagos mensuales.

---

## 🎯 Responsabilidades

- ✅ **Registrar pagos** de arriendos mensuales
- ✅ **Gestión de estados**: PENDIENTE, PAGADO, VENCIDO, PARCIAL
- ✅ **Cambiar estados**: Marcar como pagado/vencido (Admin)
- ✅ **Filtrado**: Por contrato, arrendatario
- ✅ **Cálculo de mora**: Tracking de pagos vencidos

---

## 🗄️ Modelo de Datos

### Tabla: `pagos`

| Campo | Tipo | Restricción | Descripción |
|-------|------|-------------|-------------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único |
| `contrato_id` | BIGINT | NOT NULL | ID del contrato (FK lógica) |
| `arrendatario_id` | BIGINT | NOT NULL | ID del arrendatario (FK lógica) |
| `fecha` | DATE | NOT NULL | Fecha del pago |
| `valor` | DECIMAL(15,2) | NOT NULL | Monto del pago |
| `metodo_pago` | VARCHAR(50) | | EFECTIVO, TRANSFERENCIA, TARJETA, CHEQUE |
| `estado` | VARCHAR(50) | DEFAULT 'PENDIENTE' | PENDIENTE, PAGADO, VENCIDO, PARCIAL |
| `comprobante` | VARCHAR(255) | | Número de comprobante |
| `mes_correspondiente` | VARCHAR(50) | | Mes al que corresponde el pago |
| `valor_mora` | DECIMAL(15,2) | | Valor de mora si aplica |
| `observaciones` | TEXT | | Notas adicionales |
| `fecha_registro` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Fecha de creación |

---

## 🔌 Endpoints

### 1. **POST** `/api/pagos`
Registrar un nuevo pago.

**Request Body**:
```json
{
  "contratoId": 1,
  "arrendatarioId": 7,
  "fecha": "2025-10-05",
  "valor": 1500000,
  "metodoPago": "TRANSFERENCIA",
  "mesCorrespondiente": "Octubre 2025",
  "comprobante": "TRF-001234",
  "observaciones": "Pago puntual del mes"
}
```

**Response 201 Created**:
```json
{
  "id": 1,
  "contratoId": 1,
  "arrendatarioId": 7,
  "fecha": "2025-10-05",
  "valor": 1500000,
  "metodoPago": "TRANSFERENCIA",
  "estado": "PENDIENTE",
  "mesCorrespondiente": "Octubre 2025",
  "comprobante": "TRF-001234",
  "fechaRegistro": "2025-10-05T10:00:00"
}
```

**Métodos de Pago**: EFECTIVO | TRANSFERENCIA | TARJETA | CHEQUE

---

### 2. **GET** `/api/pagos`
Listar todos los pagos del sistema.

**Response 200 OK**: Array de pagos

---

### 3. **GET** `/api/pagos?contrato={id}`
Filtrar pagos por contrato.

**Ejemplo**:
```
GET /api/pagos?contrato=1
```

**Response 200 OK**: Historial de pagos del contrato

---

### 4. **GET** `/api/pagos?arrendatario={id}`
Filtrar pagos por arrendatario.

**Ejemplo**:
```
GET /api/pagos?arrendatario=7
```

**Response 200 OK**: Pagos del arrendatario

---

### 5. **PUT** `/api/pagos/{id}/estado?estado={ESTADO}`
**✨ IMPLEMENTADO HOY** - Cambiar estado de un pago.

**Query Parameters**:
- `estado`: PENDIENTE | PAGADO | VENCIDO | PARCIAL

**Ejemplo**:
```
PUT /api/pagos/1/estado?estado=PAGADO
```

**Response 200 OK**:
```json
{
  "id": 1,
  "estado": "PAGADO",
  "mensaje": "Estado actualizado exitosamente"
}
```

**Validaciones**:
- ✅ El pago debe existir
- ✅ Estado debe ser válido
- ✅ **No se puede cambiar de PAGADO a otro estado**

**Estados**:
- **PENDIENTE**: Pago registrado, esperando confirmación
- **PAGADO**: Pago confirmado ✅
- **VENCIDO**: Pago fuera de fecha 🔴
- **PARCIAL**: Pago parcial recibido

---

## 📊 Casos de Uso

### 1. Flujo Completo de Pago

```bash
# 1. Arrendatario registra pago
curl -X POST http://localhost:8085/api/pagos \
  -H "Content-Type: application/json" \
  -d '{
    "contratoId": 1,
    "arrendatarioId": 7,
    "fecha": "2025-10-05",
    "valor": 1500000,
    "metodoPago": "TRANSFERENCIA",
    "mesCorrespondiente": "Octubre 2025",
    "comprobante": "TRF-001234"
  }'
# Response: { "id": 5, "estado": "PENDIENTE", ... }

# 2. Admin verifica el pago
curl "http://localhost:8085/api/pagos/5"

# 3. Admin marca como PAGADO
curl -X PUT "http://localhost:8085/api/pagos/5/estado?estado=PAGADO"
# Response: { "estado": "PAGADO", "mensaje": "Estado actualizado..." }
```

### 2. Marcar Pago como Vencido

```bash
curl -X PUT "http://localhost:8085/api/pagos/6/estado?estado=VENCIDO"
```

### 3. Ver Historial de Pagos de un Contrato

```bash
curl "http://localhost:8085/api/pagos?contrato=1"
```

---

## 🐛 Errores Comunes

### Error: "No se puede cambiar de PAGADO a otro estado"
**Causa**: Intento de modificar un pago ya confirmado  
**Solución**: Los pagos PAGADOS son inmutables

### Error: "Estado inválido"
**Causa**: Estado no permitido  
**Solución**: Usar solo: PENDIENTE, PAGADO, VENCIDO, PARCIAL

---

## 🚀 Mejoras Futuras

- [ ] **Pagos Automáticos**: Integración con pasarelas de pago
- [ ] **Recibos PDF**: Generación automática
- [ ] **Recordatorios**: Notificaciones antes del vencimiento
- [ ] **Historial de Cambios**: Auditoría de estados

---

## 🔗 Referencias

- **API Completa**: [../API.md](../API.md)
- **Postman**: [../../microservicios/postman-collections/](../../microservicios/postman-collections/)
- **GitHub**: [https://github.com/durregou/Inmotrack](https://github.com/durregou/Inmotrack)

---

**Autor**: [David Urrego](https://github.com/durregou)  
**Endpoints**: 6 endpoints (incluye Cambiar Estado ✨)

