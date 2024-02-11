/*
 Esta clase tiene los atributos de jugador
*/
package uno;

import java.io.Serializable;
import java.util.Date;


public class Jugador implements Serializable {
  
    private String nombre; 
    private String pais;
    private String puntaje;
    
    private Date fecha = new Date ();
 
    public Jugador(String nombre, String pais, String puntaje) {
        this.nombre = nombre;
        this.pais = pais;
        this.puntaje = puntaje;    
    }
    
    public Jugador() {
        this.nombre = "";
        this.pais = "";
        this.puntaje = "";  
    }
 
    // Set y get     
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    public Date getFecha() { 
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }  
  
}
