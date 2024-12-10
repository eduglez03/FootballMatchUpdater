# Football Match Updater

## Componentes del grupo

Este proyecto fue desarrollado por:

- **Eduardo González Gutiérrez - alu0101461588@ull.edu.es**
- **Ruymán García Martín - alu0101408866@ull.edu.es**
- **Laura González González - alu0101203942@ull.edu.es**

## Introducción

Este proyecto tiene como objetivo implementar un sistema que permita a los usuarios suscribirse a una lista de difusión para recibir notificaciones sobre los resultados en directo de partidos de fútbol. El sistema utiliza el **patrón de diseño Observador**, lo que permite que los usuarios reciban actualizaciones automáticas cuando haya cambios en los partidos, sin necesidad de realizar consultas manuales. El sistema es capaz de actualizar los resultados cada minuto y notificar a los usuarios suscritos.

## ¿Cómo funciona el patrón Observador?

En términos simples, el **patrón Observador** es una técnica que se utiliza cuando quieres que varias personas (en este caso, usuarios) reciban actualizaciones automáticas cuando ocurra algo importante, sin tener que pedirlas constantemente. Imagina que eres suscriptor de una lista que te notifica cuando hay un gol en un partido de fútbol. Cuando algo cambia, como un gol, todos los suscriptores reciben inmediatamente la notificación. 

Así funciona nuestro sistema: cuando se produce un cambio en los resultados de un partido, todos los usuarios suscritos reciben la actualización.

## API utilizada

Este proyecto utiliza la **API-FOOTBALL**, una API pública que proporciona información en tiempo real sobre partidos de fútbol, ligas y competiciones de todo el mundo. Cada vez que se solicita, la API devuelve datos sobre los partidos en curso, como el marcador, los equipos, y eventos importantes (como goles, tarjetas, etc.). Estos datos se procesan y se envían a los usuarios suscritos para mantenerlos informados.

Más información acerca de la API en este [enlace](https://www.api-football.com/)

## Instrucciones para ejecutar el código

1. **Asegúrate de tener **Java 8** o superior instalado en tu máquina.**
2. **Clona este repositorio en tu máquina local.**
   
   ```bash
   git clone https://github.com/tu-usuario/nombre-del-repositorio.git
3. **Navega al directorio del proyecto.**
   
   ```bash
   cd nombre-del-repositorio
5. **Compila el proyecto.**
   
   ```bash
   javac Main.java
7. **Ejecuta el programa.**

   ```bash
   java Main
El sistema comenzará a ejecutarse y se actualizarán los resultados de los partidos cada minuto, notificando a los usuarios suscritos cuando haya cambios.

## Ejecución de la aplicación

Al iniciar la aplicación, se presentará una ventana con el título "Sistema de notificaciones de partidos", que ofrece un menú con tres opciones principales:

<p align="center">
  <img src="https://github.com/user-attachments/assets/121817de-5173-40f7-906b-f1f2e1b284a9" alt="imagen 1" width="300" />
</p>

1. **Mostrar partidos en directo**

   Al seleccionar esta opción, se mostrará una lista con los partidos de fútbol en curso, donde podrás ver el marcador y el tiempo transcurrido de cada partido. Esta tabla se actualizará      automáticamente con la información más reciente proporcionada por la API de fútbol.

<p align="center">
  <img src="https://github.com/user-attachments/assets/79990ee2-774a-49a5-a6c7-b074308d4916" alt="imagen 1" width="300" />
</p>

2. **Registrarme**

   Esta opción permitirá al usuario registrarse para recibir notificaciones sobre los partidos. Al registrarse, el usuario será suscrito a la lista de notificación y recibirá                  actualizaciones automáticas cada vez que haya un cambio en los partidos.

<p align="center">
  <img src="https://github.com/user-attachments/assets/7b5a17a8-03d4-46b3-b08a-8571c71a3ab7" alt="imagen 1" width="300" />
</p>


3. **Salir**

   Al seleccionar esta opción, se cerrará la aplicación y se finalizará el proceso.

## Conclusión
Este proyecto nos ha permitido aplicar el patrón Observador en un sistema real, creando una aplicación que ofrece actualizaciones en tiempo real a los usuarios. A través de este enfoque, hemos aprendido cómo estructurar aplicaciones que reaccionan a cambios de manera eficiente, garantizando que los usuarios siempre tengan acceso a la información más actualizada. Además, trabajar con la API-FOOTBALL nos ha proporcionado una comprensión más profunda sobre el consumo de APIs en proyectos reales.
