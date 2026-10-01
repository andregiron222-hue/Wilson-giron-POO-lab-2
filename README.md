# Laboratorio 2 - Agencia de detectives

Programa de consola en Java, sin dependencias externas. Requiere un JDK 8 o posterior.

## Compilar y ejecutar

Abra una terminal dentro de esta carpeta y ejecute:

```bash
javac -encoding UTF-8 *.java
java Principal
```

En un IDE, abra los cinco archivos Java en el mismo proyecto y ejecute Principal.main().

## Clases

- Principal: menu de 13 opciones, lectura por Scanner y manejo de excepciones.
- Caso: datos del caso, arreglo Ubicacion[5], ArrayList<Pista>, operaciones y reporte.
- Ubicacion: informacion del lugar y validacion de riesgo y estado.
- Pista: informacion y validaciones de una evidencia.
- Validacion: validaciones de texto obligatorio y rangos numericos.

## Decisiones de funcionamiento

- Las posiciones del arreglo son 0, 1, 2, 3 y 4.
- Los textos deben contener al menos un caracter distinto de espacio.
- El estado y tipo de evidencia son textos libres porque el enunciado no fija opciones.
- Los codigos de pistas se comparan sin distinguir mayusculas de minusculas.
- Al modificar una pista se solicitan todos sus datos, incluido el codigo; puede conservarlo.
- Si hay empate en un maximo, se muestra el primer objeto encontrado.
- Un caso nuevo reemplaza el anterior. Los datos se guardan solo en memoria y se pierden al salir.
- Una validacion de negocio fallida conserva los datos anteriores y vuelve al menu.
- Una entrada numerica incorrecta se limpia y se vuelve a solicitar.
- El bloque finally cierra el Scanner al terminar, incluso ante una excepcion.

## Ejemplo para probar

1. Cree el caso con nombre Misterio, codigo C1 y detective Ana.
2. Registre una ubicacion en posicion 0 con riesgo 7.
3. Registre dos pistas: P1 con importancia 8 y confiabilidad 90; P2 con importancia 4 y confiabilidad 60.
4. El reporte debe indicar 1 ubicacion, 4 espacios disponibles, 2 pistas y promedio 6.00 (o 6,00 segun la configuracion regional).
5. Pruebe un codigo de pista repetido, riesgo 11, posicion 5 y texto en una entrada numerica.
6. El programa debe informar cada error y permitir continuar.

## Entrega

Suba los cinco archivos .java a un repositorio publico de GitHub. El laboratorio tambien pide un PDF separado con analisis y UML usando la plantilla del curso; ese documento no forma parte de este paquete.
