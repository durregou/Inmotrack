# 🚀 Inicio Rápido - 5 Minutos

Esta es una guía ultra-rápida para empezar. Para detalles completos, consulta [DOCUMENTACION_COMPLETA.md](./DOCUMENTACION_COMPLETA.md).

---

## ✅ Paso 1: Verificar Requisitos

```bash
java -version    # Debe ser 17+
mvn -version     # Debe estar instalado
docker --version # Debe estar corriendo
```

---

## ✅ Paso 2: Compilar (3-5 min)

```bash
cd microservicios
./build-all.sh
```

Espera a ver: `🎉 Todos los microservicios compilados exitosamente!`

---

## ✅ Paso 3: Desplegar (30-60 seg)

```bash
docker-compose up -d
```

---

## ✅ Paso 4: Verificar

```bash
./verificar-servicios.sh
```

O manualmente:
```bash
docker-compose ps
# Debes ver 10 contenedores "Up"
```

---

## ✅ Paso 5: Probar

```bash
# Registrar usuario
curl -X POST http://localhost:8086/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{"correo":"test@test.com","contrasena":"pass123","nombre":"Test","apellido":"User","tipoUsuario":"ARRENDATARIO"}'

# Ver reporte
curl http://localhost:8089/api/reportes/ocupacion
```

---

## 🎉 ¡Listo!

Tu sistema está funcionando en:

- 👥 Usuarios: http://localhost:8086
- 🔔 Notificaciones: http://localhost:8087
- 🔧 Mantenimiento: http://localhost:8088
- 📊 Reportes: http://localhost:8089

---

## 📚 Siguiente Paso

Lee la **[DOCUMENTACION_COMPLETA.md](./DOCUMENTACION_COMPLETA.md)** para conocer todos los endpoints y funcionalidades.

---

## 🆘 ¿Problemas?

```bash
# Ver logs
docker-compose logs -f

# Reiniciar todo
docker-compose restart
```

Consulta la sección "Solución de Problemas" en [DOCUMENTACION_COMPLETA.md](./DOCUMENTACION_COMPLETA.md).

