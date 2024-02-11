/* 
    Esta aplicación consiste en desarrollar una aplicación que simule un juego de cartas similar a “Uno”
 */
package uno;

import Formularios.MenuPrincipal;

/**
 *
 * @author Fernández
 */
public class Uno {

    public static void main(String[] args) {
        
        Archivo archivo = new Archivo();
        archivo.leerArchivo(); 
        
        MenuPrincipal ventana = new MenuPrincipal();
        ventana.setVisible(true);
    }
}
