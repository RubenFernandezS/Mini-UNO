
/* Esta clase es para las cartas normales que son de color Rosado, Blanco, Gris y Café
   son 40 cartas, 10 de cada color, con números que van del 0 al 9
*/
package uno;

import javax.swing.ImageIcon;

public class Normales extends Carta {
   private int numero;
   private String color; 

    public Normales(int numero, String color, int IdCarta, ImageIcon imagen) {
        super(IdCarta, imagen);
        this.numero = numero;
        this.color = color;
    }   
    
    public Normales() {
        this.numero = 0;
        this.color = "";
    }

    // Set y Get     
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }  
}
