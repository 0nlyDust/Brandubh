package brandubh.util;

/**
 * Sentido - enumerado
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public enum  Sentido {
	
	/**
	 * Valor Vertical norte de la clase Sentido.
	 */
	VERTICAL_N (-1,0),
	/**
	 * Valor Vertical sur de la clase Sentido.
	 */
	VERTICAL_S (+1,0),
	/**
	 * Valor Horizontal este de la clase Sentido.
	 */
	HORIZONTAL_E (0,+1),
	/**
	 * Valor Horizontal oeste de la clase Sentido.
	 */
	HORIZONTAL_O (0,-1);
	
	/**
	 * Atributo desplazamiento de filas de la clase Sentido.
	 */
	private int desplazamientoEnFilas;
	/**
	 * Atributo desplazamiento de columnas de la clase Sentido.
	 */
	private int desplazamientoEnColumnas;
	
	/**
	 * Constructor de Sentido.
	 * 
	 * @param desplazamientoEnFilas
	 * @param desplazamientoEnColumnas
	 */
	private Sentido(int desplazamientoEnFilas,int desplazamientoEnColumnas) {
		establecerDesplazamientoEnFilas(desplazamientoEnFilas);
		establecerDesplazamientoEnColumnas(desplazamientoEnColumnas);
	}
	
	/**
	 * Consulta el desplazamiento de las filas.
	 * 
	 * @return desplazamiento de las filas
	 */
	public int consultarDesplazamientoEnFilas() {
		return desplazamientoEnFilas;
	}
	
	/**
	 * Consulta el desplazamiento de las columnas.
	 * 
	 * @return desplazamiento de las columnas
	 */
	public int consultarDesplazamientoEnColumnas() {
		return desplazamientoEnColumnas;
	}
	
	/**
	 * Inicializa desplazamiento en filas.
	 * 
	 * @param desplazamientoEnFilas
	 */
	private void establecerDesplazamientoEnFilas(int desplazamientoEnFilas) {
		this.desplazamientoEnFilas = desplazamientoEnFilas;
	}
	
	/**
	 * Inicializa desplazamiento en columnas.
	 * 
	 * @param desplazamientoEnColumnas
	 */
	private void establecerDesplazamientoEnColumnas(int desplazamientoEnColumnas) {
		this.desplazamientoEnColumnas = desplazamientoEnColumnas;
	}
}