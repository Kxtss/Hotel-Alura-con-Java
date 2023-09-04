## Índice

* [Hotel Alura](#HotelA)

* [Base de datos](#DB)

* [Documentación (Javadoc)](#Documentación)

* [Referencias](#Referencias)

* [Conclusión](#Conclusión)

<br>

# HotelA
Este programa se hizo en base al <strong>Challenge ONE Back End - Java Sprint 01: Crea tu propia aplicación Desktop con conexión a la Base de Datos.</strong>.
Fue hecho con Java Swing, modelo de JDBC, (WindowBuilder) extensión "WindowBuilder", Eclipse EE IDE 2023-06 (Eclipse Enterprise), Java JDK 17 y Maven con las dependencias de mysql-connector-java v8.0.33, c3p0 v0.9.5.5, mchange-commons-java v0.2.20, jcalendar v1.4 y jbcrypt v0.4.

<br>

![Menu principal](https://github.com/Kxtss/Hotel-Alura-con-Java/assets/126207113/67f7b971-24f5-424a-9883-86ee74e9b1cb)

# DB

El archivo <i>Query_hotel_alura</i> contiene la creacion de la base de datos, las tablas y asi sus tipos que contiene cada fila, en la  tabla huesped hay un <strong>ON DELETE CASCADE</strong> que sirve para borrar el id relacionado que hay, osea, si en la tabla reserva hay un id 58 y en la tabla huesped esta referenciado en idReserva, estos se borraran a  la par ya sea desde reserva o huesped. En la tabla login el Usuario es <strong>UNIQUE</strong> esto ayuda a que no puede haber usuarios repetidos con el mismo nombre.

# Documentación
En el <strong>index.html</strong> vienen los paquetes que contiene el proyecto y toda la información necesaria para saber.

![Paquetes informacion](https://github.com/Kxtss/Hotel-Alura-con-Java/assets/126207113/52848d43-a260-4125-afb6-8426533d7783)

En el paquete de controller (<i>Package.controller</i>) contiene la información documentada de las clases que brindan la impementación de la lógica dada en el DAO para posteriormente ser usada en las vistas.

![Paquete controller](https://github.com/Kxtss/Hotel-Alura-con-Java/assets/126207113/a16c615d-c333-40c2-b879-0cfdd7996326)

En el paquete de DAO (<i>Package.dao</i>) contiene la información documentada de las clases que brindan apoyo a las clases controladoras de la lógica. Estos almacenan los valores requeridos para que los Controladores funcionen correctamente en la aplicación.

![Paquete dao](https://github.com/Kxtss/Hotel-Alura-con-Java/assets/126207113/ba676acb-cfa3-45a8-b90f-56e6d09a51c1)

En el paquete de factory (<i>Package.factory</i>) contiene la lógica para poder conectar a la base de datos.

![Paquete factory](https://github.com/Kxtss/Hotel-Alura-con-Java/assets/126207113/4e87efcc-684d-4343-ac33-ae7cedf0339c)

En el paquete security (<i>Package.security</i>) contiene la lógica para poder hacer valido el ingreso a la aplicacion en la interfaz de Login

![Paquete login](https://github.com/Kxtss/Hotel-Alura-con-Java/assets/126207113/01b32092-1f2b-413a-a9d1-b590769dc262)

En el paquete pruebas (<i>Package.pruebas</i>) contiene la logica de las clases pero que funciona en consola para poder probar los metodos

![Paquete pruebas](https://github.com/Kxtss/Hotel-Alura-con-Java/assets/126207113/b5fa692e-2ad8-411d-b92f-691c3ffd387d)

<em>Cada clase contiene una documentación detallada para que funciona cada método</em>

# Referencias
<ul>
  <li><a href="https://www.eclipse.org/downloads/">Descargar Eclipse IDE</a></li>
  <li><a href="https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.33">mysql-connector-java</a></li>
  <li><a href="https://mvnrepository.com/artifact/com.mchange/c3p0/0.9.5.5">c3p0</a></li>
  <li><a href="https://mvnrepository.com/artifact/com.toedter/jcalendar/1.4">JCalendar</a></li>
  <li><a href="https://mvnrepository.com/artifact/com.mchange/mchange-commons-java/0.2.20">mchange-commons-java</a></li>
  <li><a href="https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4">JBCrypt</a></li>
  <li><a href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">Java JDK 17</a></li>
</ul>

# Conclusión
Esté fue mi proyecto, hay cosas que mejorar como el uso de la guardar las reservas y huespedes ya que de momento esta aplicación guarda la reserva al instante al hacer click en el botón siguiente y esto no debería ser así porque a un uso muy frecuente de la aplicación puede que este ponga los idReserva mal en el Huesped en la base de datos y esto sería un problema pero busque por un tiempo la solucion y de momento no encontre, a no ser que los formularios esten en la misma interfaz y se pueda hacer un uso mejor del guardado ya que este guardaria a la vez los registros.
