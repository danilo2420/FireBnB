# FireBnb

## Getting Started

### ¿En qué consiste esta aplicación?

Esta aplicación está diseñada para permitir a los usuarios alquilar viviendas de diferente tipo, y ofrecer las suyas propias. Sigue la línea de otras aplicaciones ya establecidas en el mercado, como AirBnb, Booking.com, etc.

### ¿Cómo puedo utilizar esta aplicación?

Se ofrecen tres opciones: 

#### 1. Utilizar la app completamente desplegada  
En este caso, se deberá instalar el archivo .apk que viene incluido en la distribución del proyecto en un dispositivo Android. El dispositivo deberá tener Android 9 (API 28) o superior. La aplicación instalada ya estará configurada para utilizar la API desplegada en internet.

#### 2. Utilizar el emulador de Android Studio con la API desplegada
Para esto, se deberá ejecutar el emulador de Android Studio, y hacer correr la aplicación desde el proyecto de la carpeta 'front'. La aplicación debería conectarse directamente a la API desplegada. Si no, se recomienda comprobar la URL usada en el archivo 'FirebnbRepository.kt'.

#### 3. Utilizar la app en local
Si se desea, o si fallan las primeras dos opciones, se puede probar la app en local. El backend del proyecto (BD + API) viene preparado con un docker-compose.yml para ser dockerizado. Por tanto, se tendrá que entrar en la carpeta 'back' del proyecto, y ejecutar el comando:  
    **docker-compose up**  
Tras esto, el backend estará desplegado en local. Luego, se deberá entrar en el archivo 'FirebnbRepository.kt' en el proyecto de Android, comentar la línea que incluye la url de la API desplegada, y descomentar la línea con la URL que apunta a la máquina local.


## Manual de usuario

### Creación de un usuario
Lo primero que se deberá hacer será crear un usuario propio. Para ello, desde la pantalla de 'Login', se presionará el botón 'Create account', el cual nos redirigirá a una nueva pantalla. Aquí, deberán rellenarse los datos, y pulsar el botón 'Create user'. Esto creará nuestro usuario y nos llevará de nuevo a la pantalla de login.

### La aplicación
Tras introducir nuestras credenciales, entraremos a la parte principal de nuestra aplicación. Esta está compuesta por 4 ventanas básicas, por las cuales podremos navegar usando el menú inferior. A continuación, describiré estas 4 secciones principales de la aplicación.

#### 1. Explore

En esta sección, podremos ver todas las ofertas ofrecidas por otros usuarios. Estas son las viviendas que podemos alquilar. Si nos interesa alguna, podemos pulsar la tarjeta correspondiente. Esto nos llevará a una pantalla con los detalles de la vivienda. Si deseamos alquilarla, podemos pulsar sobre 'Rent this place'. Tras esto, tendremos que introducir las fechas de entrada y de salida. Hecho esto, podremos confirmar el alquiler.

#### 2. My trips

Aquí podremos ver nuestros alquileres. Si pulsamos sobre la tarjeta, podremos ver los detalles del alquiler. También podremos borrarlo si así lo deseamos.

#### 3. My properties

Se aquí listarán las viviendas que nosotros ofertamos. Desde esta ventana, podremos acceder a la ventana de creación de una nueva propiedad, o podremos ver los detalles de una ya creada. A través de la pantalla de detalle, podremos borrar o modificar una vivienda ya existente.

#### 4. My profile

Nuestro perfil con todos nuestros datos. Existe un botón para acceder a la pantalla de modificación de nuestro perfil. Podremos también borrar nuestra cuenta.