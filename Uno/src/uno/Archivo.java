/* Esta clase tiene los métodos para leer y escribir en archivos
 */
package uno;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Fernández
 */
public class Archivo {
    //La lista donde se van a guardar los jugadores ganadores
    public static ArrayList<Jugador> listaGanadores = new ArrayList<Jugador>();

    public Archivo() {
        
    }    
    
    // Este método crea el flujo de entrada en el disco usando el nombre del archivo y la extensión
        public void leerArchivo (){
        try {
            // Lee el archivo usando readObject y lo guarda en el arrayList para ser guardado luego con el método almacenar
            ObjectInputStream leer = new ObjectInputStream(new FileInputStream("Puntaje.txt"));
            listaGanadores = (ArrayList<Jugador>) leer.readObject();
            
            leer.close(); //Cierra 
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error, al abrir el archivo");
            
        } catch (Exception ex) {   
            System.out.println("Error" + ex);
        }
    }
        
        // Este método crea un flujo de salida usando el nombre del archivo y la extensión
    public void almacenar (){
        try {
            // crea el flujo de salida y guarda los datos que estaban almacenados en la listaGanadores en el archivo
            ObjectOutputStream escribir = new ObjectOutputStream(new FileOutputStream("Puntaje.txt"));
            escribir.writeObject(listaGanadores);
            
            escribir.close(); //Cierra
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error, al guardar en el archivo");
            
        } catch (IOException ex) { 
           System.out.println("Error" + ex);
        }
    }
}
