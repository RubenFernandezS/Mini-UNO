
/* Esta clase es para las cartas en general, va a tener un atributo idCarta que corresponde a 
un número del 1 al 50 y además le agregue un atributo imagen para asignarle una imagen a cada carta
*/

package uno;

import javax.swing.ImageIcon;
public abstract class  Carta  {
    
    protected int idCarta; 
    protected ImageIcon imagen; 

    public Carta(int idCarta, ImageIcon imagen) {
        this.idCarta = idCarta; 
        this.imagen = imagen;
    }   

    public Carta() {
        this.idCarta = 0; 
        this.imagen = null;
    }
    
    // Set y get    
    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }
  
}
