import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Partida {

    private Timer temporizador;
    private int segundos;
    private int contadorSegundos;
    private Tablero tablero;
    private boolean partidaTerminada;
    int i = 0;

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
    public Partida(int segundos, int longitud, int casillasActivas) {
        this.temporizador = new Timer();
        this.segundos = segundos;
        this.tablero = new Tablero(longitud, casillasActivas);
    }

    /*
    * Iba a crear un constructor que permitiera crear una partida solo con la longitud del array
    * y el número de casillas activas, pero Java no permite crear dos constructores con el mismo
    * número y tipo de parámetros, aunque tengan nombres diferentes, ya que no puede diferenciarlos.
    */


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
                if (contadorSegundos >= segundos) {
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

    public boolean esGanador() {

    }

    public void iniciarPartida(Tablero[][] tablero) {

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

                    if (tablero.verificarTablero(this.tablero)) {

                        juegoTerminado = true;
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

    private void terminarPartida() {
        partidaTerminada = true;
        System.out.println(esGanador() ? "¡Has apagado todas las luces, enhorabuena!" : "Una pena que no quieras continuar");
    }

}
