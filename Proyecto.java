//INTEGRANTES
//DAVID SANTIAGO MURCIA BARRERA
//CAMILO ANDRES GUTIERREZ BARRIGA
import java.util.Scanner;

public class Proyecto {

    //DECLARO LOS COLORES USADOS :)
    public static final String ANSI_RED = "\u001B[31m"; //COLOR ROJO
    public static final String ANSI_BLUE = "\033[34m"; //COLOR AZUL
    public static final String ANSI_PURPLE = "\u001B[35m"; //COLOR MORADO
    public static final String ANSI_GREEN = "\u001B[32m"; //COLOR VERDE
    public static final String ANSI_ORANGE = "\u001B[38;5;208m"; //COLOR NARANJA
    public static final String ANSI_YELLOW = "\u001B[33m"; //COLOR AMARILLO
    public static final String ANSI_CYAN = "\u001B[36m";  //COLOR CIAN
    public static final String ANSI_RESET = "\u001B[0m"; //RESTABLECER

    //ARREGLO PARA MARCAR SI UN NUMERO YA FUE PINTADO O NO
    public static boolean[][][] estadoTablero;

    //CORAZON DEL CODIGO :)
    public static void main(String[] args) {
        Scanner datos = new Scanner(System.in);

        //PIDO LA CANTIDAD DE CARTONES QUE SE UTILIZARAN EN EL JUEGO
        System.out.print("Digita la cantidad de cartones: ");
        int can_car = datos.nextInt();

        //VERIFICO QUE SEAN MINIMO DOS CARTONES PARA PODER INCIAR EL JUEGO
        while (can_car < 2) {
            System.out.println("La cantidad de cartones debe ser mayor o igual a dos para poder jugar.");
            System.out.print("Por favor, digita de nuevo la cantidad de cartones: ");
            can_car = datos.nextInt();
        }

        //INICIALIZO MI ESTADO DEL TABLERO
        estadoTablero = new boolean[can_car][5][5];

        //DECLARO MI ARREGLO DONDE GUARDARE LOS NOMBRES DE LOS JUGADORES
        String[] nombres = new String[can_car];

        //DECLARO MI TABLERO, DONDE GUARDARE SOLO LOS NUMEROS DE LOS CARTONES
        int[][][] tablero = new int[can_car][5][5];

        //LLAMO LA FUNCION QUE ME IMPRIME LOS CARTONES SIN NOMBRES, SOLO CON NUMEROS
        imprimeCarton(can_car, tablero);

        //SOLICITO LOS NOMBRES DE LOS JUGADORES
        for (int i = 0; i < can_car; i++) {
            System.out.print("Digita el nombre para el jugador " + (i + 1) + ": ");
            String nombre = datos.next();
            String nombreRecortado = "";

            //RECORRO LOS PRIMEROS 15 CARACTERES DE CADA NOMBRE
            for (int j = 0; j < nombre.length() && j < 15; j++) {
                nombreRecortado += nombre.charAt(j);
            }

            nombres[i] = nombreRecortado;
        }

        //LLAMO LA FUNCION QUE ME SOLICITA LOS NOMBRES Y ME LOS IMPRIME DE NUEVO PERO CON NOMBRES
        imprimirTableroNombres(can_car, tablero, nombres);

        //LLAMO MI MENU CON LOS MODOS DE JUEGO
        tiposJuego(can_car, tablero, nombres);
    }

    //IMPRIME LOS CARTONES DE PRIMERAS APENAS LA PERDONA DIGITA LA CANTIDAD DE CARTONES
    public static void imprimeCarton(int can_car, int[][][] tablero) {
        int cartonesPorFila = 4;//CANTIDAD DE CARTONES POR FILA
        int contador = 1;//CONTADOR DE CARTONES

        //LLENO LOS CARTONES CON NÚMEROS QUE NO SE REPITAN POR COLUMNAS
        for (int j = 0; j < tablero.length; j++) {//RECORRE LOS CARTONES
            boolean esUnico;

            do {
                esUnico = true;

                for (int k = 0; k < 5; k++) {//RECORRE LAS FILAS DE LOS CARONES
                    int min, max;
                    //RANGOS DE CADA COLUMNA

                    if (k == 0) {
                        min = 1;
                        max = 15;
                    } else if (k == 1) {
                        min = 16;
                        max = 30;
                    } else if (k == 2) {
                        min = 31;
                        max = 45;
                    } else if (k == 3) {
                        min = 46;
                        max = 60;
                    } else {
                        min = 61;
                        max = 75;
                    }

                    boolean[] usados = new boolean[76];

                    //LLENAR LAS FILAS CON NÚMEROS ALEATORIOS QUE NO SE REPITAN
                    for (int i = 0; i < 5; i++) {//RECORRE LAS COLUNAS DE LOS CARTONES
                        int num;

                        do {
                            num = (int) (Math.random() * (max - min + 1)) + min;
                        } while (usados[num]);

                        usados[num] = true;
                        tablero[j][i][k] = num;
                    }
                }

                //VERIFICAR SI EL CARTON ES UNICO COMPARANDOLO CON TODOS LOS ANTERIORES
                for (int prev = 0; prev < j; prev++) {
                    boolean sonIguales = true;
                    for (int fila = 0; fila < 5; fila++) {
                        for (int col = 0; col < 5; col++) {
                            if (tablero[j][fila][col] != tablero[prev][fila][col]) {
                                sonIguales = false;
                                break;
                            }
                        }
                        if (!sonIguales) break;
                    }
                    if (sonIguales) {
                        esUnico = false;
                        break;
                    }
                }
            } while (!esUnico);
        }

        //IMPRIMO UN MARCO
        System.out.println();
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|                             " + ANSI_YELLOW + "CARTONES DE JUEGO" + ANSI_RESET + "                              |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println();

        //IMPRIMIR LOS CARTONES SIN NOMBRES, SOLO CON NUMEROS
        for (int j = 0; j < can_car; j = j + cartonesPorFila) {//RECORRE CAARTONES

            //IMPRIMO EL NUMERO PARA CADA CARTON, DEJANDO SU RESPECTIVO ESPACIO
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                if (contador < 10) {
                    System.out.print((contador) + "                   ");
                } else {
                    System.out.print((contador) + "                  ");
                }
                contador++;//AUNMENTA EL CONTADOR DE CARTON
            }
            System.out.println();

            //LINEA SUPERIOR DE CADA CARTON
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("+----------------+  ");
            }
            System.out.println();

            //PALABRA "BINGO" DE CADA CARTON
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("| " + ANSI_BLUE + "B  I  N  G  O" + ANSI_RESET + "  |  ");
            }
            System.out.println();

            //IMPRIMO PARTE NUMERICA DE CADA CARTON
            for (int i = 0; i < 5; i++) {//IMPRIMO LAS FILAS
                for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {//IMPRIME CUATRTO CARTONES POR FILA
                    System.out.print("| ");//IMPRIMO BORDE IZQUIERDO
                    for (int k = 0; k < 5; k++) {//IMPRIMO LOS NUMEROS DE LA COLUMNA
                        int numero = tablero[c][i][k];

                        if (numero < 10) {
                            System.out.print(numero + "  ");//SI EL NUMERO ES DE UN DIGITO, CONCATENO DOS ESPACIO
                        } else {
                            System.out.print(numero + " "); //SI EL NUMERO ES DE DOS DIGITOS, CONCATENO UN ESPACIO
                        }
                    }
                    System.out.print("|  ");//IMPRIMO BORDE DERECHO
                }
                System.out.println();
            }

            // IMPRIMO LINEA INFERIOR DE TODOS LOS CARTONES
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("+----------------+  ");
            }
            System.out.println();
            System.out.println();
        }

        //ESPERO QUE LA PERSONA DIGITE UNA TECLA PARA CONTINUAR CON LOS NOMBRES DE CADA JUGADOR
        esperarTecla();

        System.out.println();
        System.out.println(ANSI_ORANGE + "Digita los nombres de los jugadores: " + ANSI_RESET);
    }

    //IMPRIMO LOS CARTONES CON SUS RESPECTIVOS NOMBRES
    public static void imprimirTableroNombres(int can_car, int[][][] tablero, String[] nombres) {
        int cartonesPorFila = 4;
        int contador = 1; //CONTADOR DE CARTONES

        //IMPRIMO MARCO
        System.out.println();
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|                             " + ANSI_YELLOW + "CARTONES DE JUEGO" + ANSI_RESET + "                              |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println();


        for (int j = 0; j < can_car; j += cartonesPorFila) {

            //IMPRIMO LOS NOMBRES DE LOS JUGADORES CON SU RESPECTIVO NUMERO DE CARTON
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {

                //IMPRIME EL CONTADOR DEL CARTON
                String nombre = nombres[c];

                //SI EL NOMBRE TIENEN MENOS DE 15 CARACTERES AGRGAR LOS ESPACIOS NECESARIOS
                int espaciosFaltantes;

                if (contador < 10) {
                    espaciosFaltantes = 17 - nombre.length();
                } else {
                    espaciosFaltantes = 16 - nombre.length();
                }

                for (int i = 0; i < espaciosFaltantes; i++) {
                    nombre += " ";//AÑADO ESPACIOS
                }

                //IMPRIMO EL NOMBRE CON LOS ESPACIOS AGREGADOS
                System.out.print(contador + " " + ANSI_ORANGE + nombre + ANSI_RESET + " ");
                contador++; // Aumenta el contador
            }

            System.out.println();

            //LINEA SUPERIOR DE LOS CARTONES
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("+----------------+  ");
            }
            System.out.println();

            //PALABRA "BINGO"
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("| " + ANSI_BLUE + "B  I  N  G  O" + ANSI_RESET + "  |  ");
            }
            System.out.println();

            //PARTE NUMERICA DE LOS CARTONES
            for (int i = 0; i < 5; i++) {//IMPRIMOS FILAS
                for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {//4 COARTONES POR FILAS
                    System.out.print("| ");//BORDE IZQUIERDO
                    for (int k = 0; k < 5; k++) {//NUMEROS DE LA COLUMNA
                        int numero = tablero[c][i][k];
                        if (numero < 10) {
                            System.out.print(numero + "  ");//IMPRIMO SU RESPECTIVO ESPACIO
                        } else {
                            System.out.print(numero + " ");//IMPRIMO SU RESPECTIVO ESPACIO
                        }
                    }
                    System.out.print("|  ");//BORDE DERECHO
                }
                System.out.println();
            }

            //LINEA INFERIOR DE TODOS LOS CARTONES
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("+----------------+  ");
            }
            System.out.println();
            System.out.println();
        }
    }

    //MENU DE LOS TIPOS DE JUEGOS
    public static void tiposJuego(int can_car, int[][][] tablero, String[] nombres) {
        Scanner datos = new Scanner(System.in);
        int opcion;//DECLARO LA OPCION PARA EL "SWICH CASE"

        //VALIDO QUE EL USUARIO DIGITE UN NUMERO DEL 1 AL 3, SI NO LO HACE, VOLVERA A PEDIR EL NUMERO
        do {
            System.out.println();
            System.out.println();
            System.out.println("+-----------------" + ANSI_CYAN + "BIENVENIDO A EL BINGO" + ANSI_RESET + "-----------------+");
            System.out.println("|   " + ANSI_ORANGE + "Hay tres tipos de figuras:" + ANSI_RESET + "                          |");
            System.out.println("|            1). La letra O.                            |");
            System.out.println("|            2). La letra X.                            |");
            System.out.println("|            3). El carton completo.                    |");
            System.out.println("+-------------------------------------------------------+");
            System.out.println();
            System.out.print("\t¿Que modo de juego deseas jugar?");
            opcion = datos.nextInt();
        } while (opcion >= 4 || opcion <= 0);

        //DEPENDIENDO DE LA OPCION QUE DIGA LA PERSONA SE EJECUTARA SU RESPECTIVO MODO DE JUEGO
        switch (opcion) {
            case 1:
                //LETRA O
                System.out.println();
                System.out.println("                      " + ANSI_YELLOW + "JUGAREMOS LA LETRA O" + ANSI_RESET);
                letra_O(can_car, tablero, nombres);
                break;
            case 2:
                //LETRA X
                System.out.println();
                System.out.println("                      " + ANSI_YELLOW + "JUGAREMOS LA LETRA X" + ANSI_RESET);
                letra_X(can_car, tablero, nombres);
                break;
            case 3:
                //CARTON COMPLETO
                System.out.println();
                System.out.println("                  " + ANSI_YELLOW + "JUGAREMOS EL CARTON COMPLETO" + ANSI_RESET);
                cartonCompleto(can_car, tablero, nombres);
                break;
            default:
                //SI DIGITA UNA OPCION NO VALIDA
                System.out.println("\tOPCION NO VALIDA!");
                break;
        }
    }

    //PIDO TECLA PARA CONTINUAR
    public static void esperarTecla() {
        Scanner datos = new Scanner(System.in);
        System.out.println(ANSI_ORANGE + "Presiona Enter para continuar..." + ANSI_RESET);
        datos.nextLine();
        
    }

    //INTERFAS DE CADA MODO DE JUEGO
    public static void cartonCompleto(int can_car, int[][][] tablero, String[] nombres) {
        boolean hayGanador = false;//VARIABLE QUE ME DICE SI HAY GANADOR O NO
        boolean[] balotasExtraidas = new boolean[76];//ARREGLO CON TODAS LAS BALOTAS
        boolean[] ganadores = new boolean[can_car];

        //BUCLE QUE REPITE HASTA QUE SE ENCUENTRE UN GANARDOR
        while (!hayGanador) {
            int balota;//VARIABLE DONDE GUARDO CADA BALOTA QUE SALE

            //GENERO UNA BALOTA ALEATORIA QUE NO SE REPITA
            do {
                balota = (int) (Math.random() * 75) + 1;
            } while (balotasExtraidas[balota]);//SE REPITE SI YA SALIO ANTES ESA BALOTA

            //MARCAMOS LA BALOTA COMO QUE YA SALIO, PARA QUE NO VUELVA A SALIR
            balotasExtraidas[balota] = true;

            //IMPRIMO LA BALOTA QUE SALIO
            System.out.println();
            if (balota < 16) {
                System.out.println(ANSI_PURPLE + "Balota generada: B-" + balota + ANSI_RESET);
            } else if (balota < 31) {
                System.out.println(ANSI_PURPLE + "Balota generada: I-" + balota + ANSI_RESET);
            } else if (balota < 46) {
                System.out.println(ANSI_PURPLE + "Balota generada: N-" + balota + ANSI_RESET);
            } else if (balota < 61) {
                System.out.println(ANSI_PURPLE + "Balota generada: G-" + balota + ANSI_RESET);
            } else {
                System.out.println(ANSI_PURPLE + "Balota generada: O-" + balota + ANSI_RESET);
            }
            System.out.println();

            //LLAMO LA FUNCION QUE ME PINTA EL NUMERO, SI ESTA EN ALGUNO DE LOS CARTONES
            pintarNumero(can_car, tablero, balota, nombres);

            //MUESTRO EL HISTORIAL DE BALOTAS QUE YA HAN SALIDO CON UN MARCO
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                      " + ANSI_GREEN + "HISTORIAL DE BALOTAS" + ANSI_RESET + "                      |");
            System.out.println("+----------------------------------------------------------------+");

            //DECLARO MI VARIABLE COMO TRUE PARA ESTABLECER LA PRIMER BALOTA DE CADA LETRA COMO PRIMERA Y NO CONCATENARLE UNA "COMA"
            boolean primerNumero;

            //BALOTAS DE LA LETRA B
            primerNumero = true;
            System.out.print("| B - ");
            for (int i = 1; i < 16; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            //BALOTAS DE LA LETRA I
            primerNumero = true;
            System.out.print("| I - ");
            for (int i = 16; i < 31; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            //BALOTAS DE LA LETRA N
            primerNumero = true;
            System.out.print("| N - ");
            for (int i = 31; i < 46; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            //BALOTAS DE LA LETRA G
            primerNumero = true;
            System.out.print("| G - ");
            for (int i = 46; i < 61; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            //BALOTAS DE LA LETRA O
            primerNumero = true;
            System.out.print("| O - ");
            for (int i = 61; i < 76; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();
            System.out.println("+----------------------------------------------------------------+");

            //VERIFICO SI EL CARTON YA ESTA COMPLETO
            hayGanador = verificarCarton_completo(can_car, ganadores); // Ahora verifica y actualiza ganadores

            if (hayGanador) {
                System.out.println();
                System.out.println(ANSI_ORANGE + "¡Felicidades! ¡Hay ganador(es) con la forma CARTON COMPLETO!" + ANSI_RESET);
                imprimirCartonesGanadores(can_car, tablero, nombres, ganadores); // Imprimir cartones ganadores
            } else {
                System.out.println("Nadie ha ganado todavía.");
                System.out.println(ANSI_ORANGE + "SIGAMOS JUGANDO" + ANSI_RESET);
                esperarTecla();
            }
        }
    }
    public static void letra_O(int can_car, int[][][] tablero, String[] nombres) {
        boolean hayGanador = false;
        boolean[] balotasExtraidas = new boolean[76];
        boolean[] ganadores = new boolean[can_car];

        while (!hayGanador) {
            int balota;

            do {
                balota = (int) (Math.random() * 75) + 1;
            } while (balotasExtraidas[balota]);

            balotasExtraidas[balota] = true;

            System.out.println();
            if (balota < 16) {
                System.out.println(ANSI_PURPLE + "Balota generada: B-" + balota + ANSI_RESET);
            } else if (balota < 31) {
                System.out.println(ANSI_PURPLE + "Balota generada: I-" + balota + ANSI_RESET);
            } else if (balota < 46) {
                System.out.println(ANSI_PURPLE + "Balota generada: N-" + balota + ANSI_RESET);
            } else if (balota < 61) {
                System.out.println(ANSI_PURPLE + "Balota generada: G-" + balota + ANSI_RESET);
            } else {
                System.out.println(ANSI_PURPLE + "Balota generada: O-" + balota + ANSI_RESET);
            }
            System.out.println();

            pintarNumero(can_car, tablero, balota, nombres);

            // MUESTRO EL HISTORIAL DE BALOTAS QUE YA HAN SALIDO
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                      " + ANSI_GREEN + "HISTORIAL DE BALOTAS" + ANSI_RESET + "                      |");
            System.out.println("+----------------------------------------------------------------+");

            boolean primerNumero = true;

            System.out.print("| B - ");
            for (int i = 1; i < 16; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| I - ");
            for (int i = 16; i < 31; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| N - ");
            for (int i = 31; i < 46; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| G - ");
            for (int i = 46; i < 61; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| O - ");
            for (int i = 61; i < 76; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();
            System.out.println("+----------------------------------------------------------------+");

            hayGanador = verificarFormaO(can_car, ganadores);

            if (hayGanador) {
                System.out.println();
                System.out.println(ANSI_ORANGE + "¡Felicidades! ¡Hay ganador(es) con la forma O!" + ANSI_RESET);
                imprimirCartonesGanadores(can_car, tablero, nombres, ganadores);
            } else {
                System.out.println("Nadie ha ganado todavía.");
                System.out.println(ANSI_ORANGE + "SIGAMOS JUGANDO" + ANSI_RESET);
                esperarTecla();
            }
        }
    }
    public static void letra_X(int can_car, int[][][] tablero, String[] nombres) {
        boolean hayGanador = false;
        boolean[] balotasExtraidas = new boolean[76];
        boolean[] ganadores = new boolean[can_car];

        while (!hayGanador) {
            int balota;

            do {
                balota = (int) (Math.random() * 75) + 1;
            } while (balotasExtraidas[balota]);

            balotasExtraidas[balota] = true;

            //IMPRIMO LA BALOTA QUE ESTA SALIENDO
            System.out.println();
            if (balota < 16) {
                System.out.println(ANSI_PURPLE + "Balota generada: B-" + balota + ANSI_RESET);
            } else if (balota < 31) {
                System.out.println(ANSI_PURPLE + "Balota generada: I-" + balota + ANSI_RESET);
            } else if (balota < 46) {
                System.out.println(ANSI_PURPLE + "Balota generada: N-" + balota + ANSI_RESET);
            } else if (balota < 61) {
                System.out.println(ANSI_PURPLE + "Balota generada: G-" + balota + ANSI_RESET);
            } else {
                System.out.println(ANSI_PURPLE + "Balota generada: O-" + balota + ANSI_RESET);
            }
            System.out.println();

            //LLAMO AL METODO PINTAR NUMEROS
            pintarNumero(can_car, tablero, balota, nombres);

            // MUESTRO EL HISTORIAL DE BALOTAS QUE YA HAN SALIDO
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                      " + ANSI_GREEN + "HISTORIAL DE BALOTAS" + ANSI_RESET + "                      |");
            System.out.println("+----------------------------------------------------------------+");


            boolean primerNumero = true;

            System.out.print("| B - ");
            for (int i = 1; i < 16; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| I - ");
            for (int i = 16; i < 31; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| N - ");
            for (int i = 31; i < 46; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| G - ");
            for (int i = 46; i < 61; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();

            primerNumero = true;
            System.out.print("| O - ");
            for (int i = 61; i < 76; i++) {
                if (balotasExtraidas[i]) {
                    if (primerNumero) {
                        System.out.print(i);
                        primerNumero = false;
                    } else {
                        System.out.print(", " + i);
                    }
                }
            }

            System.out.println();
            System.out.println("+----------------------------------------------------------------+");

            hayGanador = verificarFormaX(can_car, ganadores);

            if (hayGanador) {
                System.out.println();
                System.out.println(ANSI_ORANGE + "¡Felicidades! ¡Hay ganador(es) con la forma X!" + ANSI_RESET);
                imprimirCartonesGanadores(can_car, tablero, nombres, ganadores);
            } else {
                System.out.println("Nadie ha ganado todavía.");
                System.out.println(ANSI_ORANGE + "SIGAMOS JUGANDO" + ANSI_RESET);
                esperarTecla();
            }
        }
    }

    //VERIFICO SI LA FIGURA DE CADA FORMA SE COMPLETA
    public static boolean verificarCarton_completo(int can_car, boolean[] ganadores) {
        boolean hayGanador = false;//INICIALIZO LA VARIABLE COMO FALSE (NO HAY GANADOR)

        //MIRO SI EL CARTON ESTA COMPLETO
        for (int i = 0; i < can_car; i++) { //RECORREMOS LOS CARTONES
            boolean cartonCompleto = true;

            //VERIFICO SI TODOS LOS NUMEROS DEL CARTON YA FUERON LLMADOS
            for (int j = 0; j < 5; j++) {//TIENE 5 FILAS EL CARTON
                for (int k = 0; k < 5; k++) {//TIENE 5 COLUMNAS
                    if (!estadoTablero[i][j][k]) {//SI NO LLEGA A ESTAR MARCADO
                        cartonCompleto = false;
                        break;
                    }
                }

                if (!cartonCompleto) break;
            }

            //SI ESTA LLENO EL CARTON, HAY UHN GANDOR
            if (cartonCompleto) {
                ganadores[i] = true; //MARCO COMO GANADOR
                hayGanador = true; //HAY ALMENOS UN GANADOR
            }
        }

        //SI NINGUN CARTON ESTA COMPLETO RETORNO FALSE Y SIGUE JUGANDO
        return hayGanador;//SI HAY GANADORES
    }
    public static boolean verificarFormaO(int can_car, boolean[] ganadores) {
        boolean hayGanador = false;

        for (int i = 0; i < can_car; i++) { // Recorremos los cartones
            boolean formaO = true;

            // Verificar el borde inferior y superior
            for (int j = 0; j < 5; j++) {
                if (!estadoTablero[i][0][j] || !estadoTablero[i][4][j]) { // Primera y última fila
                    formaO = false;
                    break;
                }
            }

            // Verificar los bordes laterales
            if (formaO) {
                for (int j = 1; j < 4; j++) { // Filas intermedias, primera y última columna
                    if (!estadoTablero[i][j][0] || !estadoTablero[i][j][4]) {
                        formaO = false;
                        break;
                    }
                }
            }

            if (formaO) {
                ganadores[i] = true; // Marcar como ganador
                hayGanador = true; // Hay al menos un ganador
            }
        }
        return hayGanador; // Retornar si hay ganadores
    }
    public static boolean verificarFormaX(int can_car, boolean[] ganadores) {
        boolean hayGanador = false;
        for (int i = 0; i < can_car; i++) {//RECORRO LOS CARTONES
            boolean formaX = true;

            //DIAGONALES
            for (int j = 0; j < 5; j++) { //CARTON 5*5
                if (!estadoTablero[i][j][j] || !estadoTablero[i][j][4 - j]) {//DIAGONALES PRINCIPAPALES
                    formaX = false;
                    break;
                }
            }

            if (formaX) {
                ganadores[i] = true; // Marcar como ganador
                hayGanador = true; // Hay al menos un ganador
            }
        }
        return hayGanador; // Retornar si hay ganadores
    }

    //FUNCION DONDE MANDO A IMPRIMIR TODOS LOS CARTONES DE VUELTA CON SUS RESPECTIVOS NUMEROS PINTADOS, LO USO EL PINTAR NUMERO
    public static void imprimirTablero(int can_car, int[][][] tablero, String[] nombres, boolean[][][] estadoTablero) {
        int cartonesPorFila = 4;//CARTONES POR FILA
        int contador = 1;//CONTADOR DE CARTONES

        for (int j = 0; j < can_car; j += cartonesPorFila) {

            //IMPRIMO LOS NOMBRES CON SU RESPECTIVO NUMERO
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {

                String nombre = nombres[c];

                //SI EL NUMERO TIENE MENOS DE 15 CARACTERES LE CONCATENO SUS RESPECTIVOAS ESPACIOS
                int espaciosFaltantes;

                if (contador < 10) {
                    espaciosFaltantes = 17 - nombre.length();
                } else {
                    espaciosFaltantes = 16 - nombre.length();
                }


                for (int i = 0; i < espaciosFaltantes; i++) {
                    nombre += " ";//AÑADO EL ESPACIO
                }

                //IMPRIMO EL NOMBRE CON LOS RESPECTIVOS ESPACIOS
                System.out.print(contador + " " + ANSI_ORANGE + nombre + ANSI_RESET + " ");
                contador++; // Aumenta el contador
            }
            System.out.println();

            //LINEA SUPERIOR DE LOS CARTONES
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("+----------------+  ");
            }
            System.out.println();

            //PALABRA "BINGO"
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("| " + ANSI_BLUE + "B  I  N  G  O" + ANSI_RESET + "  |  ");
            }
            System.out.println();

            //PARTE NUMERICA DEL CARTON
            for (int i = 0; i < 5; i++) {//IMPRIMO FILAS
                for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                    System.out.print("| ");//IMPRIMO BORDE IZQUIERDO
                    for (int k = 0; k < 5; k++) {//IMPRIMO LOS NUMEROS DE LAS COLUMNAS
                        int numero = tablero[c][i][k];

                        if (estadoTablero[c][i][k]) {//VERIFICO SI EL NUMERO ESTA PINTADO
                            if (numero < 10) {
                                System.out.print(ANSI_RED + numero + ANSI_RESET + "  ");//SI EL NUMERO ES MENOR QUE 10
                            } else {
                                System.out.print(ANSI_RED + numero + ANSI_RESET + " ");//SI EL NUMERO ES MAYOR A 10
                            }
                        } else {//SI NO ESTA PINTADO, LO IMPRIME DE NUEVO SIN PINTAR
                            if (numero < 10) {
                                System.out.print(numero + "  ");//SI EL NUMERO ES MENOR QUE 10
                            } else {
                                System.out.print(numero + " ");//SI EL NUMERO ES MAYOR A 10
                            }
                        }
                    }
                    System.out.print("|  ");//IMPRIMOS EL BORDE DERECHO
                }
                System.out.println();
            }

            //IMPRIMO LINEA INFERIOR DE LOS CARTONES
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                System.out.print("+----------------+  ");
            }
            System.out.println();
            System.out.println();
        }
    }

    //MARCO LOS NUMEROS YA PINTADOS, PARA QUE SE MANTENGAN PINTADOS SIEMPRE
    public static void pintarNumero(int can_car, int[][][] tablero, int balota, String[] nombres) {
        // RECORRO LOS CARTONES Y PINTO LA BALOTA SI YA SALIO
        for (int i = 0; i < tablero.length; i++) {//RECORRO EL CARTON
            for (int j = 0; j < tablero[i].length; j++) {//RECORRO LAS FILAS
                for (int k = 0; k < tablero[i][j].length; k++) {//RECORRO LAS COLUMNAS
                    int numero = tablero[i][j][k];
                    // VERIFICO SI EL NUMERO ES IGUAL AL DE LA BALOTA
                    if (numero == balota) {
                        //EN LA MATRIZ BOOLEANA LO PONGO COMO MARCADO(TRUE)
                        estadoTablero[i][j][k] = true;
                    }
                }
            }
        }
        // IMPRIMO EL TABLERO CON LOS NÚMEROS YA PINTADOS
        imprimirTablero(can_car, tablero, nombres, estadoTablero);
    }

    //IMPRIMO LOS CARTONES GANADORES
    public static void imprimirCartonesGanadores(int can_car, int[][][] tablero, String[] nombres, boolean[] ganadores) {
        boolean hayGanadores = false;
        int cartonesPorFila = 4; // Máximo de cartones por fila

        //IMPRIMO MARCO
        System.out.println();
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println("|                            " + ANSI_YELLOW + "CARTONES GANADORES" + ANSI_RESET + "                              |");
        System.out.println("+----------------------------------------------------------------------------+");
        System.out.println();

        // INTERAMOS POR LOS JUGADORES
        for (int i = 0; i < can_car; i++) {
            if (ganadores[i]) {//SOLO CONSIDERAMOS LOS CARTONES GANDORES
                hayGanadores = true;

                //IMPRIMO ENCABEZADO DEL CARTON GANADOR
                String nombre = nombres[i];
                int espaciosFaltantes = 17 - nombre.length();
                String nombreConEspacios = nombre;

                //AGRAGO LOS ESPACIOS RESTANTES PARA CADA NOMBRE
                for (int e = 0; e < espaciosFaltantes; e++) {
                    nombreConEspacios += " ";//CONCATENO UN ESPACIO
                }
                System.out.print((i + 1) + " " + ANSI_ORANGE + nombreConEspacios + ANSI_RESET + "  ");//IMPRIMO EL NUMERO CON EL NOMBRE
            }
        }
        System.out.println();

        //IMPRIMIMOS LOS CARTONES GANADORES
        for (int j = 0; j < can_car; j += cartonesPorFila) {
            boolean filaConGanadores = false;


            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                if (ganadores[c]) {
                    filaConGanadores = true;
                    System.out.print("+----------------+  ");
                }
            }
            if (filaConGanadores) System.out.println();


            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                if (ganadores[c]) {
                    System.out.print("| " + ANSI_BLUE + "B  I  N  G  O" + ANSI_RESET + "  |  ");
                }
            }
            if (filaConGanadores) System.out.println();

            //NUMEROS DE LOS CARTONES
            for (int i = 0; i < 5; i++) { //FILAS DE LOS CARTONES
                for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {//CARTONES POR FILA
                    if (ganadores[c]) {
                        System.out.print("| "); //BORDE IZQUIERDO
                        for (int k = 0; k < 5; k++) { //COLUMNAS
                            int numero = tablero[c][i][k];
                            if (estadoTablero[c][i][k]) {
                                if (numero < 10) {
                                    System.out.print(ANSI_RED + numero + ANSI_RESET + "  ");
                                } else {
                                    System.out.print(ANSI_RED + numero + ANSI_RESET + " ");
                                }
                            } else {
                                if (numero < 10) {
                                    System.out.print(numero + "  ");
                                } else {
                                    System.out.print(numero + " ");
                                }
                            }
                        }
                        System.out.print("|  ");
                    }
                }
                if (filaConGanadores) System.out.println();
            }

            //LINEA INFERIOR DE LOS CARTONES
            for (int c = j; c < j + cartonesPorFila && c < can_car; c++) {
                if (ganadores[c]) {
                    System.out.print("+----------------+  ");
                }
            }
            if (filaConGanadores) System.out.println();
            if (filaConGanadores) System.out.println();
        }

        if (!hayGanadores) {
            System.out.println("No hay ganadores.");
            
        }
    }

    //GRACIAS PROFE :)
    public static void GRACIAS() {
        System.out.println("GRACIAS PROFE POR EL CURSO <3");
        
    }
}
