Aplicación desarrollada en Java con Netbeans.

Es un juego similar al UNO para 2 jugadores y solo consta de 3 turnos para cada jugador,
gana el jugador con más puntos al final de los 3 turnos.
El juego contiene 40 cartas, cada una de estas debe contener un número (de 0 a 9) y un
color (Rosado, café, blanco y gris), existen 10 cartas por cada color.
Además, existen 10 cartas arcoiris las cuales son especiales como se explicará más
adelante.
El juego consiste en que un jugador debe tirar una carta que sea del mismo color o número
que la carta que lanzó el jugador anterior, en caso de que no tenga una de estas cartas y
cuente con una carta arcoiris puede utilizarla para cambiar el color actual, para estos casos
el siguiente jugador deberá lanzar una carta necesariamente del mismo color y no debe
tomarse en cuenta el último número que contenia la carta anterior a la carta arcoiris. En
caso de que un jugador no tenga una carta con el mismo número, color o carta arcoiris debe
tomar una carta de la pila de cartas si su carta no cumple con las caracteristicas anteriores
el siguiente jugador debe lanzar. Se juegan 3 turnos, al finalizar estos el jugado que tiene
menos cartas gana 10 puntos, en caso que los 2 jugadores tengan la misma cantidad se le
asignan 5 puntos a cada uno.
El flujo del juego es el siguiente:
1. Se barajan las 50 cartas.
2. Se reparten 5 cartas a cada jugador.
3. El jugador 1 inicia la partida, debe lanzar cualquier carta de los que le fueron
repartidas.
4. El jugador 2 juega de acuerdo a las reglas del juego.
5. Al finalizar los 3 turnos se otorgan los puntos al ganador o en caso de empate se le
dan a ambos. Un turno finaliza cuando ambos jugadores han lanzado su carta.
6. Debe preguntar si desea jugar otra partida esta se realiza con los mismos jugadores
si indica que ya no sea jugar más debe guardar en un archivo llamado “Puntaje.dat”
donde se debe escribir la fecha actual, el nombre del ganador que obtuvo más puntos
luego de todas las partidas realizadas (en caso de empate debe agregar el nombre de
los jugadores) y el puntaje acumulado.
