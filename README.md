# Diseño de una Interfaz Gráfica de Usuario (GUI) Intuitiva con Curvas de Bézier

## Autor
- **Nombre**: Cesar Ricardo Caro Sánchez  
- **Correo**: cesar.caro@uptc.edu.co  
- **Institución**: Universidad Pedagógica y Tecnológica de Colombia  
- **Facultad**: Seccional Sogamoso, Ingeniería de Sistemas y Computación  
- **Año**: 2025  

## Descripción
Este proyecto implementa una herramienta gráfica en Java para dibujar curvas de Bézier utilizando coordenadas paramétricas. La interfaz permite a los usuarios agregar, mover y eliminar puntos de control de manera interactiva, para luego trazar curvas suaves basadas en la ecuación matemática de Bézier. Es una solución intuitiva diseñada para resolver las limitaciones de herramientas de dibujo tradicionales, ofreciendo precisión y flexibilidad.

## Características
- **Interfaz gráfica**: Ventana con un lienzo de 600x600 píxeles, cuadrícula y ejes cartesianos.
- **Gestión de puntos**:  
  - Agregar puntos mediante coordenadas (formato "X,Y").  
  - Mover puntos con clic izquierdo y arrastre.  
  - Eliminar puntos con clic derecho y confirmación.  
- **Dibujo de curvas**: Traza curvas de Bézier de grado variable según el número de puntos.
- **Visualización**: Puntos en azul, líneas entre puntos en naranja, curva en rojo.

## Requisitos
- **Java**: Versión 8 o superior (JDK instalado).
- **Sistema operativo**: Compatible con Windows, macOS o Linux.

## Instalación y Ejecución
1. Clona o descarga este repositorio
2. Navega al directorio del proyecto
3. Compila el código
4.  Ejecuta la aplicación

## Uso
1. **Agregar puntos**: Escribe coordenadas en el campo de texto (ej. "2,3") y haz clic en "Agregar" o presiona Enter.
2. **Mover puntos**: Haz clic izquierdo sobre un punto y arrástralo.
3. **Eliminar puntos**: Haz clic derecho sobre un punto y confirma la eliminación.
4. **Dibujar curva**: Haz clic en "Dibujar" para trazar la curva de Bézier.
5. **Limpiar**: Haz clic en "Limpiar" para borrar todos los puntos.

## Limitaciones
- El cálculo del factorial usa enteros (`int`), lo que limita el grado de la curva a valores pequeños (n < 13) antes de un desbordamiento.
- La curva no se redibuja automáticamente al mover puntos; requiere presionar "Dibujar".

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

## Contacto
Para preguntas o sugerencias, contacta a cesar.caro@uptc.edu.co.
