package brandubh.util;

/**
 * TipoCelda - enumerado
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public class Traductor {

	/**
	 * Atributo que indica el número de filas máximo de la clase Traductor.
	 */
    private static final int NUM_FILAS = 7;

    /**
     * Comprueba que los valores de las coordenadas son correctos.
     * 
     * @param fila - numero correspondiente a la fila de la coordenada
     * @param columna - numero correspondiente a la fila de la coordenada
     * @return true si es valido, false en caso contrario
     */
    public static boolean sonCorrectos(int fila, int columna) {
        return (fila >= 0 && fila < NUM_FILAS) && (columna >= 0 && columna < NUM_FILAS);
    }

    /**
     * Comprueba si la coordenada es correcta para traducirla en notacion algebraica.
     * 
     * @param texto - texto que hay que traducir
     * @return coordenada valida o null
     */
    public static Coordenada consultarCoordenadaParaNotacionAlgebraica(String texto) {
    	int longitud = texto.length();
    	if ( longitud == 2 && texto.charAt(0) >= 'a' && texto.charAt(0) <= 'g' && texto.charAt(1) <= '7' && texto.charAt(1) >= '1') {
    		int columna = texto.charAt(0) - 'a';
        	int fila = '7' -  texto.charAt(1);
        	return new Coordenada(fila, columna);
    	}else {
    		return null;
    	}
    }

    /**
     * Comprueba si la coordenada es correcta para traducirla en notacion algebraica.
     * 
     * @param coordenada - coordenada que hay que traducir
     * @return texto con notacion algebraica o null
     */
    public static String consultarTextoEnNotacionAlgebraica(Coordenada coordenada) {
        if (coordenada.columna() >= 0 && coordenada.columna() <= 6 && coordenada.fila() >= 0 && coordenada.fila() <= 6) {
            char columna = (char) ('a' + coordenada.columna());
            int fila = 7 - coordenada.fila();
            return "" + columna + fila;
        } else {
            return null;
        }
    }

    /**
     * Comprueba si el texto es correcto para traducirlo a coordenada.
     * 
     * @param texto - texto que hay que traducir
     * @return true si es valido, false en caso contrario
     */
    public static boolean esTextoCorrectoParaCoordenada(String texto) {
    	int longitud = texto.length();
    	if ( longitud == 2 && texto.charAt(0) >= 'a' && texto.charAt(0) <= 'g' && texto.charAt(1) <= '7' && texto.charAt(1) >= '1') {
    		return true;
    	}else {
    		return false;
    	}
    }
    
}