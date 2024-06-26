## 📋 Información del Proyecto
- 🛠️ **IDE:** IntelliJ IDEA Ultimate Edition
- ☕ **Java EE 8, Open JDK 22.0.1**
- 🌐 **Tomcat Server 9.0.90**

## 🔄 Pasos para Replicar
- [Video para replicar en local](https://www.jetbrains.com/idea/download/)

### 1. 📥 Descargar [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/download/)
- Descarga e instala la versión Ultimate Edition de IntelliJ IDEA desde el enlace proporcionado.

### 2. 💻 Abrir IntelliJ IDEA Ultimate Edition y obtener el proyecto desde VCS:
- Abre IntelliJ IDEA Ultimate Edition.
- Ve a **VCS** > **Get from Version Control**.
- Pega la URL del repositorio de GitHub actual y clona el proyecto.
    - **Link Git:** [https://github.com/devbrianmedina/InventoryProject.git](https://github.com/devbrianmedina/InventoryProject.git)

### 3. 📥 Descargar y configurar Tomcat 9.0.90
- Descarga [Tomcat 9.0.90](https://tomcat.apache.org/download-90.cgi).
- Descomprime Tomcat en una ruta de tu preferencia.

#### Configurar Tomcat en IntelliJ IDEA:
- Abre IntelliJ IDEA Ultimate Edition.
- Ve a **Run** > **Edit Configurations**.
- Haz clic en el signo **+** y selecciona **Tomcat Server** > **Local**.
- En la sección **Configuration**, configura lo siguiente:
    - **Application Server:** Selecciona la ruta donde descomprimiste Tomcat.
    - **JRE:** Selecciona la ruta del JDK que estás utilizando.
- En la sección **Before launch**:
    - Agrega un nuevo **Build Artifacts** y selecciona el artifact que termina en **war exploded**.
- En la pestaña **Deployment**:
    - Agrega un nuevo **Artifact** y selecciona **war exploded**.
- En la pestaña **Server**:
    - En **URL**, agrega **/login.jsp**.
- Aplica los cambios y guarda la configuración.

### 4. 📂 Importar base de datos
- En la carpeta "SCRIPTS" del proyecto, encontrarás el archivo SQL.
- Importa este archivo a MySQL usando phpMyAdmin o cualquier otro cliente MySQL de tu preferencia.

### 5. 📄 Configurar conexión a la base de datos
- Navega a la ruta **`src/main/java/com.codeteralab.inventoryproject/models/conexion.java`**.
- Abre el archivo [conexion.java](src%2Fmain%2Fjava%2Fcom%2Fcodeteralab%2Finventoryproject%2Fmodels%2Fconexion.java).
- Modifica los datos de conexión a MySQL con tus credenciales.

### 6. ▶️ Ejecutar el proyecto
- Ejecuta el proyecto en IntelliJ IDEA Ultimate Edition.
- Abre tu navegador y accede a la URL configurada para probar la aplicación.

🔑 **Datos de acceso usuarios encontrados en el SQL:**
- **Usuario Administrador:** brian@codeteralab.com
- **Usuario Almacenista:** francisco@codeteralab.com
- **Contraseña:** 1234