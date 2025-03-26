package LigthsOut;

/***
 * @author Alejandro Plata Cortés
 * Este tablero cuenta con una clase interna, denominada "Casilla", lo que permite
 * organizar mejor el código, así como dotarle de un acceso privado para que
 * no se pueda acceder a ella desde fuera de esta clase.
 */

public class Tablero {

    private int casillasActivas;
    private int longitud; //Longitud del tablero (como es cuadrado, será lxl)
    private final Casilla[][] tablero;

    /* * * * * * * * * * * *
     *    Constructores    *
     * * * * * * * * * * * */

    //Constructor en caso de que no se indique el número de casillas activas
    public Tablero(int longitud) {
        setLongitud(longitud);
        setCasillasActivas ((int) (Math.random() * (longitud * longitud))); // Número aleatorio entre 0 y longitud^2 - 1

        tablero = new Casilla[longitud][longitud];

        inicializarTablero(tablero);
        pintarTablero();
    }

    //Constructor indicando el número de casillas activas
    public Tablero (int longitud, int casillasActivas){
        setLongitud(longitud);
        setCasillasActivas(casillasActivas); //Inicializa de forma personalizada el número de casillas activa

        this.tablero = new Casilla[longitud][longitud];

        inicializarTablero(this.tablero);
        pintarTablero();
    }

    /* * * * * * * * * * * *
     *  Getters y Setters  *
     * * * * * * * * * * * */

    public int getLongitud() {
        return longitud;
    }

    //Verifica que la longitud del tablero esté entre 4x4 y 9x9
    public void setLongitud(int longitud) {

        if (longitud < 4 || longitud > 9) {
            throw new IllegalArgumentException("La longitud del tablero debe estar entre 4 y 9");
        }

        this.longitud = longitud;
    }

    public int getCasillasActivas() {
        return casillasActivas;
    }

    //Verifica que el número de casillas activas esté dentro del rango de casillas
    public void setCasillasActivas(int casillasActivas) {

        if (casillasActivas < 0 || casillasActivas >= (this.longitud * this.longitud)) {
            throw new IllegalArgumentException("El número de casillas activas debe ser positivo y menor de " + this.longitud * this.longitud);
        }

        this.casillasActivas = casillasActivas;
    }

    /* * * * * * * * * * * *
     *       Métodos       *
     * * * * * * * * * * * */

    private int estadoAleatorio() { return Math.random() < 0.5 ? 0 : 1; } //Podría utilizar Math.Round y castearlo a int, pero así lo veo más limpio

    public Casilla[][] getTablero() {
        return tablero;
    }

    public void inicializarTablero (Casilla[][] tablero) {

        int contadorCasillasActivas;

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

    private void pintarTablero() {

        for (Casilla[] casillas : tablero) {
            for (int j = 0; j < tablero[0].length; j++) {

                System.out.print(casillas[j].estado == Casilla.Estado.ENCENDIDO ? "\uD83D\uDD32 " : "\uD83D\uDD33 ");

            }
            System.out.println();
        }

    }

    //Devuelve falso si alguna de las luces está encendida
    public boolean verificarTablero (Casilla[][] tablero) {

        for (Casilla[] casillas : tablero) {
            for (int j = 0; j < tablero[0].length; j++) {

                if (casillas[j].estado == Casilla.Estado.ENCENDIDO) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método que cambia el estado de las luces
     * @param fila -> Indica la fila de la luz que se desea apagar o encender
     * @param col -> Indica la columna de la luz que se desea apagar o encender
     * @param tablero -> Tablero sobre el que se desea apagar o encender una luz
     * Me llevó bastante tiempo configurar que las luces
     * cambiaran de la forma en que lo hacen en el LightsOut.
     * Traté de hacerlo con bucles, pero quedaba código difícilmente
     * legible y poco mantenible, además de poco elegante.
     * El problema principal se encontraba en que hay 3 zonas claramente diferenciadas
     * en el tablero en términos de cómo se modifica:
     * las esquinas, los laterales y la zona central.
     * Por tanto, me enfoqué en ello, pero seguía dando lugar a un código muy repetitivo.
     * Tras un tiempo largo de prueba y error, advertí que no era necesario diferenciar el tablero
     * en 3 zonas, sino que con 2 era más que suficiente. Podía tratar los laterales y las esquinas
     * como uno, simplificando el problema enormemente. Puede resultar evidente, pero la realidad es
     * que tardé en percatarme de ello.
     */
    public void cambiarLuces(int fila, int col, Tablero.Casilla[][] tablero) {

        // Cambia la casilla presionada
        tablero[fila][col].cambiarEstado(tablero[fila][col]);

        // Cambia la de arriba si no está en la primera fila
        if (fila > 0) {
            tablero[fila - 1][col].cambiarEstado(tablero[fila - 1][col]);
        }

        // Cambia la de abajo si no está en la última fila
        if (fila < tablero.length - 1) {
            tablero[fila + 1][col].cambiarEstado(tablero[fila + 1][col]);
        }

        // Cambia la de la izquierda si no está en la primera columna
        if (col > 0) {
            tablero[fila][col - 1].cambiarEstado(tablero[fila][col - 1]);
        }

        // Cambia la de la derecha si no está en la última columna
        if (col < tablero[0].length - 1) {
            tablero[fila][col + 1].cambiarEstado(tablero[fila][col + 1]);
        }
    }

    /* * * * * * * * * * * *
     *    Clase Casilla    *
     * * * * * * * * * * * */

    //Clase interna para mejorar la organización del código
    public class Casilla {

        private Estado estado;

        /* * * * * * * * * * * *
         *    Constructores    *
         * * * * * * * * * * * */

        public Casilla (int estado) {
            setCasilla(estado);
        }


        public Casilla () { this.estado = Casilla.Estado.APAGADO; }


        /* * * * * * * * * * * *
         *  Getters y Setters  *
         * * * * * * * * * * * */


        public void setCasilla(int estado) { this.estado = estado == 0 ? Estado.ENCENDIDO : Estado.APAGADO; }


        public void cambiarEstado(Casilla casilla) { casilla.estado = (estado == Estado.APAGADO) ? Estado.ENCENDIDO : Estado.APAGADO; }


        /* * * * * * * * * * * *
         *     ENUMERADOR      *
         * * * * * * * * * * * */

        //Indica si la casilla está apagada o encendida. También podría haber utilizado un booleano, pero esto me permetía posibles ampliaciones.
        public enum Estado {
            ENCENDIDO,
            APAGADO
        }

    }

}
