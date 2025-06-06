# Vynils-App

Aplicación móvil desarrollada en **Kotlin** para crear, gestionar y explorar álbumes musicales y artistas, además de asignar premios a artistas.

---

## Descripción

**Vynils-App** permite a los usuarios:
- Crear álbumes musicales.
- Crear premios

Con una interfaz moderna basada en **Jetpack Compose** e integración a servicios backend mediante **Volley**.

---

## Características
- 📀 Crear álbumes.
- 🏆 Crear premios.
- 🌐 Comunicación con APIs usando **Volley**.
- 🎨 UI responsiva con **Jetpack Compose**.

---

## Instalación

1. Clonar el repositorio:

```bash
git clone https://github.com/tu_usuario/Vynils-App.git
```

2. Abrir el proyecto en **Android Studio** y sincronizar.

3. Ejecutar en un emulador o dispositivo físico.

---

## Configuración del Backend

La URL del backend se puede cambiar en el archivo:

```
app/src/main/java/network/NetworkServiceAdapter.kt
```

Actualmente, la app utiliza la siguiente URL para conectarse:

```
http://10.0.2.2:3000/
```


### Cambiar la IP del Backend en caso de HTTP

Si tu backend no usa HTTPS, es necesario modificar la URL que la app usa para hacer las peticiones a la API.

1. **Ubicación del archivo `network_security_config.xml`**:
   - El archivo `network_security_config.xml` se encuentra en la siguiente ruta:  
     `app/src/main/res/xml/network_security_config.xml`

2. **Instrucciones para cambiar la URL**:
   - Abre el archivo `network_security_config.xml`.
   - Busca la sección donde se configura la URL del backend.
   - Cambia la URL de `https://` a `http://`, si tu servidor no tiene configurado HTTPS.
   
   Ejemplo:
   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <network-security-config>
       <!-- Cambia la URL de backend si es HTTP -->
       <domain includeSubdomains="true">http://your-backend-ip.com</domain>
   </network-security-config>


Si deseas apuntar a otro backend, simplemente modifica la constante correspondiente dentro de `NetworkServiceAdapter.kt`.

---

## Tecnologías

- **Kotlin**
- **Jetpack Compose**
- **Volley** para conexión a APIs
- **MVVM** como patrón de arquitectura
- **Android SDK**



# 📄 Cómo correr los tests en Vinyls App

## ⚙️ Requisitos
- Un emulador Android configurado o un dispositivo físico.
- Gradle configurado correctamente (`./gradlew` debe funcionar sin errores).

## ▶️ Cómo correr los tests

### 1. Desde Android Studio

- Abre el proyecto en Android Studio.
- Navega a la carpeta:  
  `app/src/androidTest/java/com/example/vinyls`
- Abre el archivo de tests, por ejemplo:  
  `CrearPremioScreenTest.kt`
- Haz clic derecho sobre el archivo o sobre el nombre del test (`@Test`) que quieras correr.
- Selecciona **Run 'NombreDelTest'** o **Run 'CrearPremioScreenTest'**.
- También puedes correr **todos los tests** haciendo clic derecho sobre la carpeta `androidTest` y eligiendo **Run 'All Test'**.




