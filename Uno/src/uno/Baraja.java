/* Esta clase contiene la colección donde se almacena la baraja
 Y tiene los métodos para crear las 50 cartas y barajarlas
*/

package uno;

import static java.lang.Math.random;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Baraja {

    private ArrayList<Carta> mazoCartas; 
    private static final String colores[] = {"Rosado", "Café", "Blanco", "Gris"}; 

    private ImageIcon imagen;  

    public Baraja(ArrayList<Carta> mazoCartas) {
        this.mazoCartas = mazoCartas;
    }

    public Baraja() {
        this.mazoCartas = new ArrayList<Carta>(); 
    }

    public ArrayList<Carta> getMazoCartas() {
        return mazoCartas;
    }

    public void setMazoCartas(ArrayList<Carta> mazoCartas) {
        this.mazoCartas = mazoCartas;
    }

    // Este método Crea las 50 cartas con sus números, colores e id
    public void crearCartas() {
        int contador = 0;

        for (int i = 0; i < 5 ; i++) { 
            for (int j = 0; j < 10; j++) { 

                if (contador < 40) {
                    imagen = new ImageIcon(getClass().getResource("/Imagenes/carta (" + contador + ").png"));
                    contador++;
                    mazoCartas.add(new Normales(j, colores[i], contador, imagen)); 
                    setMazoCartas(mazoCartas);
                } else {
                    imagen = new ImageIcon(getClass().getResource("/Imagenes/carta (40).png"));
                    contador++;
                    mazoCartas.add(new Arcoiris (contador, imagen)); 
                    setMazoCartas(mazoCartas);
                }               
            }
        }
    }

    // Este método baraja el mazo de cartas
    public void barajarMazo() {
        Carta temporal = null;
        int numAleatorio = 0;
        for (int i = 0; i < mazoCartas.size(); i++) {
            numAleatorio = (int) (random() * mazoCartas.size());
            temporal = mazoCartas.get(i);
            mazoCartas.set(i, mazoCartas.get(numAleatorio));
            mazoCartas.set(numAleatorio, temporal); 
        }
    }
}
