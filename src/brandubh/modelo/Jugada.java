package brandubh.modelo;
import brandubh.util.Coordenada;
import brandubh.util.Sentido;

/**
 * Jugada - record
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 * @param origen - celda de donde parte la pieza que va a moverse
 * @param destino - a donde se dirige la pieza que va a moverse
 */
public record Jugada (Celda origen, Celda destino) {
	
	/**
	 * Consulta hacia donde se dirige al pieza.
	 * 
	 * @return dirrecion a donde se dirige la pieza
	 */
	public Sentido consultarSentido() {
		if( esMovimientoHorizontalOVertical() == true ) {
			Coordenada coordenadaOrigen = origen.consultarCoordenada();
			int coordenadaOrigenFila = coordenadaOrigen.fila();
			int coordenadaOrigenColumna = coordenadaOrigen.columna();
			Coordenada coordenadaDestino = destino.consultarCoordenada();
			int coordenadaDestinoFila = coordenadaDestino.fila();
			int coordenadaDestinoColumna = coordenadaDestino.columna();
	
			if (coordenadaOrigenFila < coordenadaDestinoFila) {
	            return Sentido.VERTICAL_S;
	        } else if (coordenadaOrigenFila > coordenadaDestinoFila) {
	            return Sentido.VERTICAL_N;
	        } else if (coordenadaOrigenColumna < coordenadaDestinoColumna) {
	            return Sentido.HORIZONTAL_E;
	        } else {
	            return Sentido.HORIZONTAL_O;
	        }
			
		}else {
		return null;
		}
	}
	
	/**
	 * Comprueba si un movimiento es horizontal, vertical o diagonal.
	 * 
	 * @return true si el movimiento es horizontal o vertical, false si es diagonal
	 */
	public boolean esMovimientoHorizontalOVertical() {
	    Coordenada coordenadaOrigen = origen.consultarCoordenada();
	    int coordenadaOrigenFila = coordenadaOrigen.fila();
	    int coordenadaOrigenColumna = coordenadaOrigen.columna();
	    Coordenada coordenadaDestino = destino.consultarCoordenada();
	    int coordenadaDestinoFila = coordenadaDestino.fila();
	    int coordenadaDestinoColumna = coordenadaDestino.columna();   
	    int diferenciaFilas = Math.abs(coordenadaDestinoFila - coordenadaOrigenFila);
	    int diferenciaColumnas = Math.abs(coordenadaDestinoColumna - coordenadaOrigenColumna);
	    if (diferenciaFilas == diferenciaColumnas || (diferenciaFilas > 0 && diferenciaColumnas > 0)) {
	        return false;
	    }
	    return true;
	}	
}