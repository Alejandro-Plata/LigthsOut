import java.util.Timer;
import java.util.TimerTask;

public class Partida {

    private Timer temporizador;
    private int segundos;
    private boolean partidaTerminada;
    int i = 0;

    public Partida(int segundos) {
        this.temporizador = new Timer();
        this.segundos = segundos;
    }

    /**
     * Este método inicia un temporizador utilizando la clase Timer, que recibe una
     * instancia de TimerTask. Para simplificarlo, he implementado esta tarea como
     * una clase anónima.
     *
     * Las clases anónimas pueden acceder a variables locales del método exterior,
     * siempre que estas sean constantes. Como quería contar la cantidad de segundos que pasan
     * y modificar esta variable desde la clase anónima, necesitaba usar una estructura
     * mutable.
     *
     * Para ello, he utilizado un array, ya que la referencia del array se mantiene constante
     * y su contenido puede modificarse dentro de la clase anónima.
     *
     * @param segundos
     */



    private void iniciarTemporizador(int segundos) {
        final int[] contadorSegundos = {0};

        while(contadorSegundos[0] <= segundos) {
            temporizador.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    contadorSegundos[0]++;
                }
            }, 0, 1000);
        }

        temporizador.cancel(); //Cerramos el hilo que maneja el temporizador

        terminarPartida();

    }

    public void menu (){

        System.out.println("\nMenú de opciones\n"
                + "1. Lights (Encender o apagar luces).\n"
                + "2. Guardar partida.\n"
                + "Para finalizar la partida, ingresa '00'.");
    }

//    public boolean esGanador() {
//        int contadorMinasMarcadas = 0;
//        for (int i = 0; i < this.filas; i++) {
//            for (int j = 0; j < this.filas; j++) {
//                if (tablero[i][j].tieneMina() && tablero[i][j].isMarcada()){
//                    contadorMinasMarcadas++; //Si la mina está marcada y tiene una bomba
//                }
//            }
//        }
//        if(contadorMinasMarcadas == numeroMinas){ //Si has marcado todas las minas, has ganado :)
//            return true;
//        }
//        return false;
//    }
//
//    public void iniciarPartida() {
//
//        Scanner teclado = new Scanner(System.in);
//        while (!juegoTerminado) {
//            mostrarTablero();
//            menu();
//            int opcion = teclado.nextInt();
//            switch (opcion) {
//
//                case 1 -> {
//                    System.out.println("\nIntroduce la fila y columna a abrir (separados por un espacio):");
//                    int fila = teclado.nextInt();
//                    int col = teclado.nextInt();
//
//                    if (fila < 0 || fila >= this.filas || col < 0 || col >= this.filas) {
//                        System.out.println("Coordenadas inválidas. Inténtalo de nuevo.");
//                        continue; //Pasa a la siguiente iteración del bucle directamente
//                    }
//
//                    if (tablero[fila][col].isAbierta()) {
//                        System.out.println("Esta casilla ya ha sido descubierta. Prueba con otra.");
//                        continue; //Pasa a la siguiente iteración del bucle directamente
//                    }
//
//                    // Si la casilla tiene mina, el juego se termina.
//                    if (tablero[fila][col].tieneMina()) {
//                        System.out.println("¡Boooooooooooooom! \u001B[31mHas perdido.\u001B[0m");
//                        abrirMinas();
//                        mostrarTablero();
//                        juegoTerminado = true;
//                    } else {
//                        // Revela la casilla y sus vecinas.
//                        abrirCasilla(fila, col);
//                    }
//
//                }
//
//                case 2 -> {
//                    System.out.println("\nIntroduce la fila y columna a marcar (separados por un espacio):");
//                    int fila = teclado.nextInt();
//                    int col = teclado.nextInt();
//
//                    if (fila < 0 || fila >= this.filas || col < 0 || col >= this.filas) {
//                        System.out.println("Coordenadas inválidas. Inténtalo de nuevo.");
//                        continue; //Pasa a la siguiente iteración del bucle directamente
//                    }
//
//                    if (tablero[fila][col].isAbierta()) {
//                        System.out.println("Esta casilla ya ha sido descubierta. Prueba con otra.");
//                        continue; //Pasa a la siguiente iteración del bucle directamente
//                    }
//
//                    marcarCasilla(fila, col); //Marcamos o desmarcamos la casilla
//
//                    if (esGanador()) { //Verificamos si ha ganado o no
//                        System.out.println("\u001B[32m¡Felicidades! Has ganado el juego.\u001B[0m");
//                        abrirMinas();
//                        mostrarTablero();
//                        juegoTerminado = true;
//                    }
//
//                }
//                case 3 -> {
//
//                    System.out.println("\nIntroduce la fila y columna en la que quieras poner o quitar tu duda (separados por un espacio):");
//                    int fila = teclado.nextInt();
//                    int col = teclado.nextInt();
//
//                    if (fila < 0 || fila >= this.filas || col < 0 || col >= this.filas) {
//                        System.out.println("Coordenadas inválidas. Inténtalo de nuevo.");
//                        continue; //Pasa a la siguiente iteración del bucle directamente
//                    }
//
//                    if (tablero[fila][col].isAbierta()) {
//                        System.out.println("Esta casilla ya ha sido descubierta. Prueba con otra.");
//                        continue; //Pasa a la siguiente iteración del bucle directamente
//                    }
//
//                    dudaCasilla(fila, col);
//
//                }
//
//                default -> System.out.println("\n\u001B[31mValor inválido\u001B[0m");
//
//            }
//
//        }
//
//
//    }
//
    public void prueba() {

        partidaTerminada = false;
        iniciarTemporizador(10);
        do {
            System.out.println(i);
            i++;
        } while (!partidaTerminada);
    }

    private void terminarPartida() {
        System.out.println("¡¡¡TIEMPO!!!");
        partidaTerminada = true;
    }

}
