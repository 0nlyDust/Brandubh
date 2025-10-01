package brandubh.textui;

import java.util.Scanner;

import brandubh.control.Arbitro;
import brandubh.modelo.Celda;
import brandubh.modelo.Jugada;
import brandubh.modelo.Tablero;
import brandubh.util.Color;
import brandubh.util.Coordenada;
import brandubh.util.Traductor;

/**
 * Brandubh en modo texto.
 * 
 * Se abusa del uso del modificador static tanto en atributos como en metodos
 * para comprobar su similitud a variables globales y funciones globales de
 * otros lenguajes.
 * 
 * La programación en este codigo sigue mas el paradigma de programacion
 * estructurada en mayor medida que la orientación a objetos.
 * 
 * En algunoss casos los metodos estaticos son meros envoltorios o "wrappers"
 * de invocaciones a metodos del arbitro.
 *
 * @author <a href="rmartico@ubu.es">Raul Marticorena</a>
 * @author Maria Molina Goyena
 * @since 1.0
 * @version 1.0
 * @see brandubh.modelo
 * @see brandubh.control
 * @see brandubh.util
 */
public class Brandubh {

	/** Tamanyo en caracteres de una jugada. */
	private static final int TAMANYO_JUGADA = 4;

	/** Posicion en el texto de una jugada de la coordenada destino. */
	private static final int INICIO_COORDENADA_DESTINO = 2;

	/** Texto para interrumpir la partida. */
	private static final String TEXTO_SALIR = "salir";

	/** Tablero. */
	private static Tablero tablero;

	/** Arbitro. */
	private static Arbitro arbitro;

	/** Lector por teclado. */
	private static Scanner scanner;

	/** Oculta el constructor por defecto. */
	private Brandubh() {
	}

	/**
	 * Metodo raiz con el algoritmo principal en modo texto.
	 * 
	 * @param args argumentos de entrada en linea de comandos
	 */
	public static void main(String[] args) {
	    inicializarPartida();
	    mostrarMensajeBienvenida();
	    mostrarTablero();
	    juego: while(!comprobarFinalizacionPartida()) {
	        turno: while(true) {
	            String textoJugada = recogerTextoDeJugadaPorTeclado();
	            if (comprobarSalir(textoJugada)) {
	                break juego; 
	            }
	            if (validarFormato(textoJugada)) {
	                Jugada jugada = extraerJugada(textoJugada);
	                if (esLegal(jugada)) {
	                    realizarMovimientoYCapturas(jugada);
	                    break turno; 
	                } else {
	                    mostrarErrorPorMovimientoIlegal(textoJugada);
	                }
	            } else {
	                mostrarErrorEnFormatoDeEntrada();
	            }
	        }
	        mostrarTablero();
	        if (!comprobarFinalizacionPartida()) {
	        	cambiarTurnoPartida();
	        }
	    }
	    finalizarPartida();
	    mostrarGanador();
	}

	/**
	 * Inicializa el estado de los elementos de la partida.
	 */
	private static void inicializarPartida() {
		// Inicializaciones
		tablero = new Tablero();
		arbitro = new Arbitro(tablero);
		// Cargar piezas con la configuracion inicial...
		arbitro.colocarPiezasConfiguracionInicial();
		// Abrir la lectura desde teclado...
		scanner = new Scanner(System.in);
	}
	
	/**
	 * Recoge el texto de la jugada por teclado.
	 * 
	 * @return jugada jugada en formato texto
	 */
	private static String recogerTextoDeJugadaPorTeclado() {
		System.out
				.print("Introduce jugada turno con piezas de color " + arbitro.consultarTurno() + " (formato cfcf): ");
		return scanner.next();
	}
	

	/**
	 * Comprueba si se quiere finalizar la partida por parte de los usuarios.
	 * 
	 * @param jugada jugada en formato texto
	 * @return true si el usuario introduce salir, false en caso contrario
	 */
	private static boolean comprobarSalir(String jugada) {
		return jugada.equalsIgnoreCase(TEXTO_SALIR);
	}	
	
	/**
	 * Valida la correccion del formato de la jugada. Solo comprueba la correccion
	 * del formato de entrada en cuanto al tablero, no la validez de la jugada en
	 * cuanto a las reglas del juego.
	 * 
	 * La jugada tiene que tener cuatro caracteres y contener letras y números de
	 * acuerdo a las reglas de la notación algebraica.
	 * 
	 * Otra mejor solucion alternativa es el uso de expresiones regulares (se verán
	 * en la asignatura de 3º Procesadores del Lenguaje).
	 * 
	 * @param textoJugada a validar
	 * @return true si el formato de la jugada es correcta segun las coordenadas
	 *         disponibles del tablero
	 */
	private static boolean validarFormato(String textoJugada) {
		if (textoJugada.length() == TAMANYO_JUGADA) {
			String origen = textoJugada.substring(0, INICIO_COORDENADA_DESTINO);
			String destino = textoJugada.substring(INICIO_COORDENADA_DESTINO, TAMANYO_JUGADA);
			// comprobar si ambos textos son correctos
			return Traductor.esTextoCorrectoParaCoordenada(origen) && Traductor.esTextoCorrectoParaCoordenada(destino);
		}
		return false;
	}
	
	/**
	 * Extrae la jugada a partir del texto introducido por teclado.
	 * 
	 * @param jugadaTexto texto con la jugada
	 * @return jugada
	 * @see #extraerCoordenada(String, int, int)
	 */
	private static Jugada extraerJugada(String jugadaTexto) {
		Coordenada coordenadaOrigen = extraerCoordenada(jugadaTexto, 0, INICIO_COORDENADA_DESTINO);
		Coordenada coordenadaDestino = extraerCoordenada(jugadaTexto, INICIO_COORDENADA_DESTINO, TAMANYO_JUGADA);
		Celda origen = tablero.consultarCelda(coordenadaOrigen);
		Celda destino = tablero.consultarCelda(coordenadaDestino);
		return new Jugada(origen, destino);
	}	

	/**
	 * Extrae una coordenada a partir del texto de entrada y de las posiciones
	 * [incio, fin) indicadas.
	 * 
	 * Dada una jugada en texto, extraera la coordenada origen o destino, en funcion
	 * de la posicion de inicio y fin dada.
	 * 
	 * @param jugada texto en formato notacion algebraica (e.g. a1a3)
	 * @param inicio posicion en el texto a partir del cual leer
	 * @param fin    posicion final - 1, hasta donde leer el texto
	 * @return coordenada o null, si no es posible extraerla
	 */
	private static Coordenada extraerCoordenada(String jugada, int inicio, int fin) {
		if (jugada.length() != TAMANYO_JUGADA)
			return null;
		String textoExtraido = jugada.substring(inicio, fin);
		return Traductor.consultarCoordenadaParaNotacionAlgebraica(textoExtraido);
	}
	
	/**
	 * Comprueba la legalidad de la jugada.
	 * 
	 * @param jugada jugada
	 * @return true si es legal, false en caso contrario
	 */
	private static boolean esLegal(Jugada jugada) {
		return arbitro.esMovimientoLegal(jugada);
	}

	/**
	 * Realizar la jugada completando el movimiento y las capturas correspondientes.
	 * 
	 * @param jugada jugada
	 */
	private static void realizarMovimientoYCapturas(Jugada jugada) {
		arbitro.mover(jugada);
		arbitro.realizarCapturasTrasMover();
	}	

	/**
	 * Cambia el turno de la partida.
	 */
	private static void cambiarTurnoPartida() {
		arbitro.cambiarTurno();
	}
		
	/**
	 * Comprueba si esta finalizada la partida.
	 * 
	 * @return true si hay victoria de atacante o defensor, false en caso contrario
	 */
	private static boolean comprobarFinalizacionPartida() {
		return arbitro.haGanadoAtacante() || arbitro.haGanadoRey();
	}		
	
	/**
	 * Finaliza la partida cerrando recursos abiertos como el teclado.
	 */
	private static void finalizarPartida() {
		System.out.println("Partida finalizada.");
		scanner.close();
	}		
	
	/**
	 * Muestra el mensaje de bienvenida con instrucciones para finalizar la partida.
	 */
	private static void mostrarMensajeBienvenida() {
		System.out.println("Bienvenido al juego del Brandubh 1.0");
		System.out.println(
				"Atacan piezas de color " + Color.NEGRO + " y defienden piezas de color " + Color.BLANCO + ".");
		System.out.println("Para interrumpir partida introduzca \"salir\".");
		System.out.println("Disfrute de la partida...");
	}

	/**
	 * Muestra la informacion de error en el formato de entrada, mostrando ejemplos.
	 */
	private static void mostrarErrorEnFormatoDeEntrada() {
		System.out.println();
		System.out.println("Error en el formato de entrada.");
		System.out.println(
				"El formato debe ser letranumeroletranumero, por ejemplo a7a5 o g2e2, o bien introducir la cadena \"salir\" para finalizar la partida.");
		System.out.println("Las letras deben estar en el rango [a,g] y los números en el rango [1,7].");
	}
	
	/**
	 * Informa de la ilegalidad de la jugada intentada.
	 * 
	 * @param textoJugada texto de la jugada introducido por teclado
	 */
	private static void mostrarErrorPorMovimientoIlegal(String textoJugada) {
		System.out.printf("%nLa jugada %s es ilegal.%nRevise las reglas del juego.%n", textoJugada);
	}

	/**
	 * Muestra el estado del tablero con sus piezas actuales en pantalla.
	 */
	private static void mostrarTablero() {
		System.out.println();
		System.out.println(tablero.aTexto());
	}

	/**
	 * Muestra el ganador de la partida en pantalla.
	 */
	private static void mostrarGanador() {
		if (arbitro.haGanadoAtacante()) {
			System.out.printf("%nHa ganado la partida el jugador atacante con piezas de color %s.%n",
					arbitro.consultarTurno());
		} else if (arbitro.haGanadoRey()) {
			System.out.printf("%nHa ganado la partida el jugador defensor con piezas de color %s.%n",
					arbitro.consultarTurno());
		} else {
			System.out.println("\nNo hay ganador.");
		}
	}
}
