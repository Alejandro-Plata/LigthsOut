package LigthsOut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Partida {

    private boolean partidaTerminada;
    private int tiempoMaximo;
    private int contadorSegundos;
    private String modo;
    private Tablero tablero;
    private Timer temporizador;

    /* * * * * * * * * * * *
     *    Constructores    *
     * * * * * * * * * * * */

    //Constructor que solo recibe la longitud de la matriz, con tiempo infinito
    public Partida(int longitud) {
        this.temporizador = new Timer();
        this.tablero = new Tablero(longitud);
    }
    //Constructor que recibe la longitud y el número de casillas activas, con tiempo infinito
    public Partida(int longitud, int casillasActivas) {
        this.temporizador = new Timer();
        this.tablero = new Tablero(longitud, casillasActivas);
    }
    //Constructor que recibe la longitud, el número de casillas activas y el tiempo de partida
    public Partida(int tiempoMaximo, int longitud, int casillasActivas) {
        this.temporizador = new Timer();
        this.tiempoMaximo = tiempoMaximo;
        this.tablero = new Tablero(longitud, casillasActivas);
    }

    /* * * * * * * * * * * *
     *  Getters y Setters  *
     * * * * * * * * * * * */

    //Getter personalizado que permite saber cuántos segundos quedan (a implementar en la partida)
    public int getSegundosRestantes () {
        return this.tiempoMaximo - this.contadorSegundos;
    }

    public String getModo() {
        return this.modo;
    }

    public void setModo(String modo) {

        if (!(modo.equalsIgnoreCase("ALEATORIO") || modo.equalsIgnoreCase("PERSONALIZADO"))){
            throw new IllegalArgumentException("El modo debe ser 'Aleatorio' o 'Personalizado', no se conoce el modo " + modo);
        }
        this.modo = modo;

    }

    public void setTiempoMaximo(int tiempoMaximo) {

        if (tiempoMaximo < 0) {
            throw new IllegalArgumentException("La duración de la partida no puede ser negativa");
        }

        this.tiempoMaximo = tiempoMaximo;
    }


    /* * * * * * * * * * * *
     *       Métodos       *
     * * * * * * * * * * * */

    private void configurarJuego () throws IOException {

        BufferedReader lector = new BufferedReader(new FileReader("config.txt"));
        String texto;
        String [] dividirTexto;
        Integer [] contenidoTablero = new Integer[this.tablero.getLongitud() * this.tablero.getLongitud()];
        //Actualiza el texto dentro del bucle, lee línea a línea el documento
        while ((texto = lector.readLine()) != null) {

            if (texto.startsWith("Tamaño:")) {

                this.tablero.setLongitud(Integer.parseInt(texto.split(":")[1].trim())); //Separamos el texto por los 2 puntos y le quitamos los espacios en blanco

            } else if (texto.startsWith("Modo:")) {

                setModo(texto.split(":")[1].trim());

            } else if (texto.startsWith("Casillas activas:")) {

                if (this.modo.equalsIgnoreCase("Aleatorio")) { //Si no es aleatorio, se ignorará
                    this.tablero.setCasillasActivas(Integer.parseInt(texto.split(":")[1].trim()));
                }

            } else if (texto.startsWith("Duración:")) {

                setTiempoMaximo(Integer.parseInt(texto.split(":")[1].trim()));

            } else if (texto.startsWith("Tablero:\n")) {

                if (this.modo.equalsIgnoreCase("Personalizado")) { //Si no es aleatorio, se ignorará

                    while ((texto = lector.readLine()) != null) {

                    }

                }


            }

        }


    }

    public boolean esGanador() {
        return true;
    }

    public void iniciarPartida(Tablero.Casilla[][] tablero) {

        Scanner teclado = new Scanner(System.in);
        while (!partidaTerminada) {
            menu();
            int opcion = teclado.nextInt();
            switch (opcion) {

                case 1 -> {
                    System.out.println("\nIntroduce la fila y columna de la luz que desea apagar o encender. (separados por un espacio):");
                    int fila = teclado.nextInt();
                    int col = teclado.nextInt();

                    if (fila < 0 || fila >= tablero.length || col < 0 || col >= tablero.length) {
                        System.out.println("Coordenadas incorrectas, inténtalo de nuevo.");
                        continue; //Pasa a la siguiente iteración del bucle directamente
                    }

                    if (this.tablero.verificarTablero(tablero)) {
                        partidaTerminada = true;
                    } else {



                    }

                }

                case 2 -> {

                }
                case 00 -> {
                    terminarPartida();
                }

                default -> System.out.println("\n\u001B[31mValor inválido\u001B[0m");

            }

        }


    }

    /**
     * Este método inicia un temporizador utilizando la clase Timer, que recibe una
     * instancia de TimerTask. Esta instancia la he hecho utilizando una clase anónima,
     * que tiene su propia impleentación del método run.
     *
     * Concretamente, esta implementación termina la partida una vez pasado el tiempo indicado, en segundos,
     * en la instanciación de la Partida.
     */
    public void iniciarTemporizador() {
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                contadorSegundos++; //Aumenta el contador de segundos cada segundo
                if (contadorSegundos >= tiempoMaximo) {
                    temporizador.cancel(); // Cierra el flujo del temporizador
                    System.out.println("¡¡¡TIEMPO!!!");
                    partidaTerminada = true; // Termina la partida
                }
            }
        };
        temporizador.scheduleAtFixedRate(tarea, 0, 1000); // Ejecutar cada segundo
    }

    public void menu (){

        System.out.println("\nMenú de opciones\n"
                + "1. Lights (Encender o apagar luces).\n"
                + "2. Cargar fichero.\n"
                + "00. Finalizar partida.");
    }

    private void terminarPartida() {
        partidaTerminada = true;
        System.out.println(esGanador() ? "¡Has apagado todas las luces, enhorabuena!" : "Una pena que no quieras continuar");
    }


}
