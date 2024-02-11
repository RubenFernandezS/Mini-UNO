/*
  Este formulario tiene toda la parte de la lógica empleada en el juego
 */
package Formularios;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import uno.Archivo;
import uno.Carta;
import uno.Baraja;
import uno.Jugador;
import uno.Normales;

/**
 *
 * @author Fernández
 */
public class Juego extends javax.swing.JFrame {

    /**
     * Creates new form Juego
     */
    Baraja coleccion = new Baraja();
    Archivo archivo = new Archivo();

    private ArrayList<Carta> mazo;
    private JButton[] botones_J1 = new JButton[7]; // Para las 7 cartas que puede tener el jugador 1 como máximo
    private JButton[] botones_J2 = new JButton[8]; // Para las 8 cartas que puede tener el jugador 2 como máximo

    private Carta cartaActual = null;
    private Carta cartaLanzadaJugador = null;

    private int jugadorActual = 1;

    private int numeroCartaActual = -1;
    private int numeroCartaLanzada = -1;
    private String colorActual = "";
    private String colorLanzado = "";

    private int turno = 0;
    int tamanio = 0;

    private ArrayList<Carta> mazoJugador1 = new ArrayList<>(); // Mazo del jugador 1
    private ArrayList<Carta> mazoJugador2 = new ArrayList<>(); // Mazo del jugador 2

    private Object opcionColores[] = {"Escoja una Opción", "Rosado", "Café", "Blanco", "Gris"};
    private String opcion;

    public Juego() {
        initComponents();
        
        botones_J1[0] = btnCarta1;
        botones_J1[1] = btnCarta2;
        botones_J1[2] = btnCarta3;
        botones_J1[3] = btnCarta4;
        botones_J1[4] = btnCarta5;
        botones_J1[5] = btnCarta6;
        botones_J1[6] = btnCarta7;

        botones_J2[0] = btnCarta9;
        botones_J2[1] = btnCarta10;
        botones_J2[2] = btnCarta11;
        botones_J2[3] = btnCarta12;
        botones_J2[4] = btnCarta13;
        botones_J2[5] = btnCarta14;
        botones_J2[6] = btnCarta15;
        botones_J2[7] = btnCarta16;
    }

    public void iniciarJuego() {
        for (int i = 0; i < 7; i++) {
            if (i < 5) {
                botones_J1[i].setVisible(true);
                botones_J1[i].setEnabled(true);
            } else {
                botones_J1[i].setVisible(false);
                botones_J1[i].setEnabled(false);
            }
        }
        for (int i = 0; i < 8; i++) {
            botones_J2[i].setEnabled(false);
            if (i < 5) {
                botones_J2[i].setVisible(true);
            } else {
                botones_J2[i].setVisible(false);
            }
        }
        lblColorActual.setText("");
        btnPasarTurno.setEnabled(false);
        btnPedirCarta.setEnabled(false);
        btnPedirCarta2.setEnabled(false);

        jugadorActual = 1;
        lblJugador.setText(String.valueOf(jugadorActual));
        turno = 1;
        lblTurnos.setText(String.valueOf(turno));

        coleccion.crearCartas();
        coleccion.barajarMazo();

        mazo = coleccion.getMazoCartas();

        repartirCartas();
    }

    public void nombre1_pais1(String nombre, String pais) {
        lblNombreJugador1.setText(nombre);
        lblPais1.setText(pais);
    }

    public void nombre2_pais2(String nombre2, String pais2) {
        lblNombreJugador2.setText(nombre2);
        lblPais2.setText(pais2);
    }

    // Reparte las 5 cartas a cada jugador
    public void repartirCartas() {
        for (int i = 0; i < 10; i++) {

            if (i % 2 == 0) {
                mazoJugador1.add(mazo.get(i));                
            } else { 
                mazoJugador2.add(mazo.get(i));
            }
            mazo.remove(i);
            mazo.add(i, null);
        }

        for (int i = 0; i < 5; i++) {
            botones_J1[i].setIcon(mazoJugador1.get(i).getImagen());
            botones_J2[i].setIcon(mazoJugador2.get(i).getImagen());
        }
    }

    // Ganador al terminar los 3 turnos
    public void ganadorPorJuego(ArrayList<Carta> mazoJugador1, ArrayList<Carta> mazoJugador2) {
        int contador1 = 0;
        int contador2 = 0;

        if (turno > 3) {
            // Cuenta las cartas de el mazo del jugador 1
            for (Carta carta1 : mazoJugador1) {
                if (carta1 != null) {
                    contador1++;
                }
            }
            // Cuenta las cartas de el mazo del jugador 2
            for (Carta carta1 : mazoJugador2) {
                if (carta1 != null) {
                    contador2++;
                }
            }
            asignarPuntos(contador1, contador2);
            int continuarJugando = JOptionPane.showConfirmDialog(null, "¿Desea Jugar de Nuevo?");
            jugarDeNuevo(continuarJugando); // 
        }
    }

    // Le asigna los puntos al ganador (el que tiene menos carta y los escribe en el jlabel correspondiente)
    public void asignarPuntos(int contador1, int contador2) {
        int puntos = 0;
        if (contador1 < contador2) {
            puntos = (Integer.parseInt(lblPuntaje1.getText()) + 10);
            lblPuntaje1.setText(String.valueOf(puntos));
        } else if (contador1 > contador2) {
            puntos = (Integer.parseInt(lblPuntaje2.getText()) + 10);
            lblPuntaje2.setText(String.valueOf(puntos));
        } else {
            puntos = (Integer.parseInt(lblPuntaje1.getText()) + 5);
            lblPuntaje1.setText(String.valueOf(puntos));
            puntos = (Integer.parseInt(lblPuntaje2.getText()) + 5);
            lblPuntaje2.setText(String.valueOf(puntos));
        }
    }

    // Si la respuesta es SI entonces limpia todo para comenzar de nuevo, sino guarda al ganador
    public void jugarDeNuevo(int continuarJugando) {
        if (continuarJugando == 0) {

            this.mazoJugador1.removeAll(mazoJugador1);
            this.mazoJugador2.removeAll(mazoJugador2);
            this.mazo.removeAll(mazo);

            cartaActual = null;
            cartaLanzadaJugador = null;
            colorActual = "";
            numeroCartaActual = -1;
            colorLanzado = "";
            numeroCartaLanzada = -1;

            btnCartaActual.setIcon(null);

            for (JButton boton : botones_J1) {
                boton.setIcon(null);
            }
            for (JButton boton : botones_J2) {
                boton.setIcon(null);
            }
            iniciarJuego();

        } else {
            determinarGanadorDefinitivo(); 

            MostrarPuntos mostrar = new MostrarPuntos();
            mostrar.setVisible(true);
            this.dispose();
        }
    }

    // Determina quien ganó después de todas las partidas
    public void determinarGanadorDefinitivo() {
        Jugador jugador1;
        if (Integer.parseInt(lblPuntaje1.getText()) > Integer.parseInt(lblPuntaje2.getText())) {
            JOptionPane.showMessageDialog(null, "Gano el jugador 1");

            jugador1 = new Jugador(lblNombreJugador1.getText(), lblPais1.getText(), lblPuntaje1.getText());
            Archivo.listaGanadores.add(jugador1);
        } else if (Integer.parseInt(lblPuntaje1.getText()) < Integer.parseInt(lblPuntaje2.getText())) {
            JOptionPane.showMessageDialog(null, "Gano el jugador 2");

            jugador1 = new Jugador(lblNombreJugador2.getText(), lblPais1.getText(), lblPuntaje2.getText());
            Archivo.listaGanadores.add(jugador1);

        } else {
            JOptionPane.showMessageDialog(null, "Empataron");
            jugador1 = new Jugador(lblNombreJugador1.getText() + " | " + lblNombreJugador2.getText(), lblPais1.getText(), lblPuntaje1.getText());
            Archivo.listaGanadores.add(jugador1);
        }
        archivo.almacenar();
    }

    public void activarBotonesCambioTurnoJugador2() {
        for (JButton boton : botones_J1) {
            boton.setEnabled(false);
        }

        for (JButton boton : botones_J2) {
            boton.setEnabled(true);
        }
        btnPasarTurno.setEnabled(false);
        btnPedirCarta.setEnabled(true);
        btnPedirCarta2.setEnabled(true);
    }

    public void activarBotonesCambioTurnoJugador1() {
        for (JButton boton : botones_J1) {
            boton.setEnabled(true);
        }

        for (JButton boton : botones_J2) {
            boton.setEnabled(false);
        }

        btnPasarTurno.setEnabled(false);

        if (turno == 1 && jugadorActual == 1) {
            btnPedirCarta.setEnabled(false);
            btnPedirCarta2.setEnabled(false);
        } else {
            btnPedirCarta.setEnabled(true);
            btnPedirCarta2.setEnabled(true);
        }
    }

    // Para obtener el color de la carta
    public String obtenerColor(Carta e) {
        Normales n1;

        if (e instanceof Normales) {
            n1 = (Normales) e;
            return n1.getColor();
        } else {
            return null;
        }
    }

    //Para obtener el número de la carta
    public int obtenerNumero(Carta e) {
        Normales n1;

        if (e instanceof Normales) {
            n1 = (Normales) e;
            return n1.getNumero();
        } else {
            return -1;
        }
    }

    // Pide y asigna una nueva carta al jugador
    public void pedirCarta() {
        for (int i = 0; i < 50; i++) {
            if (jugadorActual == 1 && mazo.get(i) != null) {
                tamanio = mazoJugador1.size();
                mazoJugador1.add(mazo.get(i));

                botones_J1[tamanio].setIcon(mazoJugador1.get(tamanio).getImagen());
                botones_J1[tamanio].setVisible(true);

                mazo.remove(i); 
                mazo.add(i, null); 
                break;
            }
            if (jugadorActual == 2 && mazo.get(i) != null) {
                tamanio = mazoJugador2.size();
                mazoJugador2.add(mazo.get(i));

                botones_J2[tamanio].setIcon(mazoJugador2.get(tamanio).getImagen());
                botones_J2[tamanio].setVisible(true);

                mazo.remove(i);
                mazo.add(i, null);
                break;  
            }
        }
    }

    // Cuando un jugador lanza una carta Arcoíris debe escoger el color con el que se sigue jugando
    public void cambioColor_Numero() {
        opcion = (String) JOptionPane.showInputDialog(this, "Selecciona un color", "Cambio de Color",
                JOptionPane.QUESTION_MESSAGE, null, opcionColores, opcionColores[0]);
        if (opcion == null || opcion == "Escoja una Opción") {
            JOptionPane.showMessageDialog(null, "Debe escoger una opción válida para continuar");
            cambioColor_Numero();
        } else {
            colorActual = opcion; 
            numeroCartaActual = -1;                   
        }
    }
    
    //Para los turnos del jugador 1
    private void TurnoJugador1(int i){
        if (cartaActual == null) {

            cartaActual = mazoJugador1.get(i);
            btnCartaActual.setIcon(cartaActual.getImagen()); 
            mazoJugador1.set(i, null); 

            if (cartaActual instanceof Normales) {
                numeroCartaActual = obtenerNumero(cartaActual); 
                colorActual = obtenerColor(cartaActual); 
            } else {
                cambioColor_Numero();
            }

            botones_J1[i].setIcon(null);
            botones_J1[i].setVisible(false);

            jugadorActual = 2;
            lblJugador.setText(String.valueOf(jugadorActual));
            lblColorActual.setText(colorActual);
            activarBotonesCambioTurnoJugador2();
        } else {  
            cartaLanzadaJugador = mazoJugador1.get(i);
            colorLanzado = obtenerColor(cartaLanzadaJugador);
            numeroCartaLanzada = obtenerNumero(cartaLanzadaJugador);

            if (cartaLanzadaJugador instanceof Normales) {
                if ((colorLanzado.equals(colorActual)) || (numeroCartaLanzada == numeroCartaActual)) {

                    cartaActual = mazoJugador1.get(i);
                    btnCartaActual.setIcon(cartaActual.getImagen()); 
                    mazoJugador1.set(i, null); 

                    colorActual = obtenerColor(cartaActual); 
                    numeroCartaActual = obtenerNumero(cartaActual); 

                    botones_J1[i].setIcon(null);
                    botones_J1[i].setVisible(false);

                    jugadorActual = 2;
                    lblJugador.setText(String.valueOf(jugadorActual));
                    lblColorActual.setText(colorActual);

                    activarBotonesCambioTurnoJugador2();
                } else {
                    JOptionPane.showMessageDialog(null, "Colores o Números Distintos");
                }
            } else { 
                cambioColor_Numero();
                cartaActual = mazoJugador1.get(i);
                btnCartaActual.setIcon(cartaActual.getImagen()); 
                mazoJugador1.set(i, null); 
                
                botones_J1[i].setIcon(null);
                botones_J1[i].setVisible(false);

                jugadorActual = 2;
                lblJugador.setText(String.valueOf(jugadorActual));
                lblColorActual.setText(colorActual);

                activarBotonesCambioTurnoJugador2();
            }
        }
    }
    
    //Para los turnos del jugador 2
    private void TurnosJugador2(int i){
        cartaLanzadaJugador = mazoJugador2.get(i);
        colorLanzado = obtenerColor(cartaLanzadaJugador);
        numeroCartaLanzada = obtenerNumero(cartaLanzadaJugador);

        if (cartaLanzadaJugador instanceof Normales) {
            if ((colorLanzado.equals(colorActual)) || (numeroCartaLanzada == numeroCartaActual)) {

                cartaActual = mazoJugador2.get(i);
                btnCartaActual.setIcon(cartaActual.getImagen()); 
                mazoJugador2.set(i, null); 

                colorActual = obtenerColor(cartaActual); 
                numeroCartaActual = obtenerNumero(cartaActual);

                botones_J2[i].setIcon(null);
                botones_J2[i].setVisible(false);

                jugadorActual = 1; 
                lblJugador.setText(String.valueOf(jugadorActual));

                turno++;
                lblTurnos.setText(String.valueOf(turno));

                if (turno > 3) {
                    lblColorActual.setText(colorActual);
                    ganadorPorJuego(mazoJugador1, mazoJugador2);
                } else {
                    lblColorActual.setText(colorActual);
                    activarBotonesCambioTurnoJugador1();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Colores o Números Distintos");
            }
        } else { 
            cambioColor_Numero();
            cartaActual = mazoJugador2.get(i);
            btnCartaActual.setIcon(cartaActual.getImagen()); 
            mazoJugador2.set(i, null);

            botones_J2[i].setIcon(null);
            botones_J2[i].setVisible(false);

            jugadorActual = 1; 
            lblJugador.setText(String.valueOf(jugadorActual));

            turno++;
            lblTurnos.setText(String.valueOf(turno));

            if (turno > 3) {
                lblColorActual.setText(colorActual);
                ganadorPorJuego(mazoJugador1, mazoJugador2);
            } else {
                lblColorActual.setText(colorActual);
                activarBotonesCambioTurnoJugador1();
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        btnCarta1 = new javax.swing.JButton();
        btnCarta2 = new javax.swing.JButton();
        btnCarta3 = new javax.swing.JButton();
        btnCarta5 = new javax.swing.JButton();
        btnCarta6 = new javax.swing.JButton();
        btnCarta4 = new javax.swing.JButton();
        btnCarta7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnPedirCarta2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblJugador = new javax.swing.JLabel();
        btnPedirCarta = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblTurnos = new javax.swing.JLabel();
        btnPasarTurno = new javax.swing.JButton();
        lblTurnos1 = new javax.swing.JLabel();
        btnCarta10 = new javax.swing.JButton();
        btnCarta11 = new javax.swing.JButton();
        btnCarta12 = new javax.swing.JButton();
        btnCarta14 = new javax.swing.JButton();
        btnCarta15 = new javax.swing.JButton();
        btnCarta16 = new javax.swing.JButton();
        btnCarta9 = new javax.swing.JButton();
        btnCarta13 = new javax.swing.JButton();
        btnVolver1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblNombreJugador1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblPuntaje1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNombreJugador2 = new javax.swing.JLabel();
        lblPuntaje2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblPais1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblPais2 = new javax.swing.JLabel();
        btnCartaActual = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblColorActual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UNO");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 420, 141, 41));

        btnCarta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 94, 134));

        btnCarta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 94, 134));

        btnCarta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta3ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 94, 134));

        btnCarta5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta5ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 94, 134));

        btnCarta6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta6ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 94, 134));

        btnCarta4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta4ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 94, 134));

        btnCarta7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta7ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta7, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 180, 94, 134));

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        btnPedirCarta2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPedirCarta2.setText("Pedir Carta");
        btnPedirCarta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedirCarta2ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Turno #");

        btnPedirCarta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cartaAtras.png"))); // NOI18N
        btnPedirCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedirCartaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Turno del Jugador ");

        lblTurnos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTurnos.setText("0");

        btnPasarTurno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPasarTurno.setText("Pasar Turno");
        btnPasarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarTurnoActionPerformed(evt);
            }
        });

        lblTurnos1.setText("de 3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(btnPedirCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addComponent(btnPedirCarta2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPasarTurno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTurnos1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPasarTurno, btnPedirCarta2});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTurnos1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnPedirCarta, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPedirCarta2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPasarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPasarTurno, btnPedirCarta2});

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, -1));

        btnCarta10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta10ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta10, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 30, 94, 134));

        btnCarta11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta11ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta11, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, 94, 134));

        btnCarta12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta12ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta12, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 94, 134));

        btnCarta14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta14ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta14, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 180, 94, 134));

        btnCarta15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta15ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta15, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 180, 94, 134));

        btnCarta16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta16ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta16, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 180, 94, 134));

        btnCarta9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta9ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 30, 94, 134));

        btnCarta13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarta13ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarta13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 180, 94, 134));

        btnVolver1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVolver1.setText("Volver al Menú");
        btnVolver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolver1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnVolver1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 420, 141, 41));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        lblNombreJugador1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombreJugador1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText(" Puntaje:");

        lblPuntaje1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText(" Puntaje: ");

        lblNombreJugador2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombreJugador2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblPuntaje2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText(" País:");

        lblPais1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText(" País: ");

        lblPais2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Nombre:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Nombre:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText(" Color Actual :");

        lblColorActual.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPuntaje1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 202, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreJugador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPais1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(52, 52, 52)
                .addComponent(btnCartaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPuntaje2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreJugador2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPais2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(56, 56, 56))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblColorActual, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(484, 484, 484))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreJugador1, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(lblNombreJugador2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPais1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPais2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPuntaje2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPuntaje1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(76, 76, 76))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnCartaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblColorActual, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPedirCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedirCartaActionPerformed
        pedirCarta();
        btnPasarTurno.setEnabled(true);
        btnPedirCarta.setEnabled(false);
        btnPedirCarta2.setEnabled(false);
    }//GEN-LAST:event_btnPedirCartaActionPerformed

    private void btnVolver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolver1ActionPerformed
        MenuPrincipal ventana1 = new MenuPrincipal();
        ventana1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolver1ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCarta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta1ActionPerformed
        TurnoJugador1(0);
    }//GEN-LAST:event_btnCarta1ActionPerformed

    private void btnCarta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta2ActionPerformed
       TurnoJugador1(1);
    }//GEN-LAST:event_btnCarta2ActionPerformed

    private void btnCarta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta3ActionPerformed
        TurnoJugador1(2);
    }//GEN-LAST:event_btnCarta3ActionPerformed

    private void btnCarta4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta4ActionPerformed
        TurnoJugador1(3);
    }//GEN-LAST:event_btnCarta4ActionPerformed

    private void btnCarta5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta5ActionPerformed
        TurnoJugador1(4);
    }//GEN-LAST:event_btnCarta5ActionPerformed

    private void btnCarta6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta6ActionPerformed
        tamanio = mazoJugador1.size() - 1;
        TurnoJugador1(tamanio);
    }//GEN-LAST:event_btnCarta6ActionPerformed
 
    private void btnCarta7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta7ActionPerformed
        tamanio = mazoJugador1.size() - 1;
        TurnoJugador1(tamanio);       
    }//GEN-LAST:event_btnCarta7ActionPerformed

    private void btnCarta9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta9ActionPerformed
        TurnosJugador2(0);
    }//GEN-LAST:event_btnCarta9ActionPerformed

    private void btnCarta10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta10ActionPerformed
        TurnosJugador2(1);
    }//GEN-LAST:event_btnCarta10ActionPerformed

    private void btnCarta11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta11ActionPerformed
        TurnosJugador2(2);
    }//GEN-LAST:event_btnCarta11ActionPerformed

    private void btnCarta12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta12ActionPerformed
        TurnosJugador2(3);
    }//GEN-LAST:event_btnCarta12ActionPerformed

    private void btnCarta13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta13ActionPerformed
        TurnosJugador2(4);
    }//GEN-LAST:event_btnCarta13ActionPerformed

    private void btnPedirCarta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedirCarta2ActionPerformed
        pedirCarta();
        btnPasarTurno.setEnabled(true);
        btnPedirCarta.setEnabled(false);
        btnPedirCarta2.setEnabled(false);
    }//GEN-LAST:event_btnPedirCarta2ActionPerformed

    private void btnCarta14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta14ActionPerformed
        tamanio = mazoJugador2.size() - 1;
        TurnosJugador2(tamanio);
    }//GEN-LAST:event_btnCarta14ActionPerformed

    private void btnCarta15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta15ActionPerformed
        tamanio = mazoJugador2.size() - 1;
        TurnosJugador2(tamanio);
    }//GEN-LAST:event_btnCarta15ActionPerformed

    private void btnCarta16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarta16ActionPerformed
        tamanio = mazoJugador2.size() - 1;
        TurnosJugador2(tamanio);
    }//GEN-LAST:event_btnCarta16ActionPerformed

    private void btnPasarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarTurnoActionPerformed
        if (jugadorActual == 1) {
            activarBotonesCambioTurnoJugador2();
            jugadorActual = 2;
            lblJugador.setText(String.valueOf(jugadorActual));
        } else {
            jugadorActual = 1;
            lblJugador.setText(String.valueOf(jugadorActual));
            turno++;
            lblTurnos.setText(String.valueOf(turno));
            activarBotonesCambioTurnoJugador1();
            ganadorPorJuego(mazoJugador1, mazoJugador2); // para que cuando sea turno 3 se cálcule quien gano
        }
    }//GEN-LAST:event_btnPasarTurnoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.setLocationRelativeTo(null);

        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/iconUno.png")).getImage());

        lblPuntaje1.setText("0");
        lblPuntaje2.setText("0");

        iniciarJuego();
    }//GEN-LAST:event_formWindowOpened

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Juego.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Juego.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Juego.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Juego.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Juego().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarta1;
    private javax.swing.JButton btnCarta10;
    private javax.swing.JButton btnCarta11;
    private javax.swing.JButton btnCarta12;
    private javax.swing.JButton btnCarta13;
    private javax.swing.JButton btnCarta14;
    private javax.swing.JButton btnCarta15;
    private javax.swing.JButton btnCarta16;
    private javax.swing.JButton btnCarta2;
    private javax.swing.JButton btnCarta3;
    private javax.swing.JButton btnCarta4;
    private javax.swing.JButton btnCarta5;
    private javax.swing.JButton btnCarta6;
    private javax.swing.JButton btnCarta7;
    private javax.swing.JButton btnCarta9;
    private javax.swing.JButton btnCartaActual;
    private javax.swing.JButton btnPasarTurno;
    private javax.swing.JButton btnPedirCarta;
    private javax.swing.JButton btnPedirCarta2;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVolver1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblColorActual;
    private javax.swing.JLabel lblJugador;
    private javax.swing.JLabel lblNombreJugador1;
    private javax.swing.JLabel lblNombreJugador2;
    private javax.swing.JLabel lblPais1;
    private javax.swing.JLabel lblPais2;
    private javax.swing.JLabel lblPuntaje1;
    private javax.swing.JLabel lblPuntaje2;
    private javax.swing.JLabel lblTurnos;
    private javax.swing.JLabel lblTurnos1;
    // End of variables declaration//GEN-END:variables
}
