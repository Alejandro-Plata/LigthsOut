/***
 * @author Alejandro Plata Cortés
 *
 * Este tablero cuenta con una clase interna, denominada "Casilla", lo que permite
 * organizar mejor el código, así como dotarle de un acceso privado para que
 * no se pueda acceder a ella desde fuera de esta clase.
 */

public class Tablero {

    private int longitud; //Longitud del tablero (como es cuadrado, será lxl)
    private int casillasActivas;
    private Casilla tablero[][];
    int contadorCasillasActivas;



    //Constructor en caso de que no se indique el número de casillas activas
    public Tablero(int longitud) {
        setLongitud(longitud);
        this.casillasActivas = (int) (Math.random() * (longitud * longitud)); // Número aleatorio entre 0 y longitud^2 - 1
        tablero = new Casilla[longitud][longitud]; // Inicializamos el tablero

        inicializarTablero(tablero);
        pintarTablero();
    }

    //Constructor indicando el número de casillas activas
    public Tablero (int longitud, int casillasActivas){
        setLongitud(longitud);
        setCasillasActivas(casillasActivas); //Inicializa de forma personalizada el número de casillas activa
        tablero = new Casilla[longitud][longitud];

        inicializarTablero(tablero);
        pintarTablero();
    }

    /* Getters y setters */

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        //Verifica que la longitud del tablero esté entre 4x4 y 9x9
        if (longitud < 4 || longitud > 9) {
            throw new IllegalArgumentException("La longitud del tablero debe estar entre 4 y 9");
        }

        this.longitud = longitud;
    }

    public int getCasillasActivas() {
        return casillasActivas;
    }

    public void setCasillasActivas(int casillasActivas) {

        //Verifica que el número de casillas activas sea factible
        if (casillasActivas < 0 || casillasActivas >= (this.longitud * this.longitud)) {
            throw new IllegalArgumentException("El número de casillas activas debe ser positivo y menor de " + this.longitud * this.longitud);
        }

        this.casillasActivas = casillasActivas;
    }

    /* Métodos */

    /**
     * Para pintar el tablero, es necesario que las casillas
     */
    private void pintarTablero() {
        for (int i = 0; i < tablero.length; i++) {

            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print(tablero[i][j].estado == Casilla.Estado.ENCENDIDO ? "\uD83D\uDD32 " : "\uD83D\uDD33 ");
            }
            System.out.println();
        }
    }

    private int estadoAleatorio() {
        return Math.random() < 0.5 ? 0 : 1;
        //Podría utilizar Math.Round y castearlo a int, pero así lo veo más limpio
    }

    public void inicializarTablero (Casilla tablero [][]) {

        do {
            contadorCasillasActivas = 0; //Reiniciamos el contador para cada iteración
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[0].length; j++) {

                    if (tablero[i][j] == null || contadorCasillasActivas < casillasActivas) { // Si la casilla no está creada
                        if (contadorCasillasActivas < casillasActivas) {
                            tablero[i][j] = new Casilla(estadoAleatorio());
                        } else {
                            tablero[i][j] = new Casilla(); // Inicializa la casilla a apagada
                        }
                    }

                    // Contar si la casilla está encendida
                    if (tablero[i][j].estado == Casilla.Estado.ENCENDIDO) {
                        contadorCasillasActivas++;
                    }
                }
            }


        } while (contadorCasillasActivas < casillasActivas);

    }

    //Clase interna para mejorar la organización del código
    private class Casilla {
        private Estado estado;

        //Constructor de casilla
        public Casilla (int estado) {
            this.estado = setCasilla(estado);
        }
        public Casilla () { this.estado = Casilla.Estado.APAGADO; }

        public Estado setCasilla(int estado) {

            if (estado == 0) {
                return Estado.ENCENDIDO;
            }
            return Estado.APAGADO;
        }

        public void cambiarEstado(Casilla casilla) {
            casilla.estado = (casilla.estado == Estado.APAGADO) ? Estado.ENCENDIDO : Estado.APAGADO;
        }

        public enum Estado {
            ENCENDIDO,
            APAGADO
        }

    }


}
