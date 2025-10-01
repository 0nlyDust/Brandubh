package brandubh.modelo;
import java.util.Arrays;
import brandubh.util.Coordenada;
import brandubh.util.TipoCelda;
import brandubh.util.TipoPieza;

/**
 * Tablero
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public class Tablero {
	
	/**
	 * Matriz de celdas.
	 */
	private Celda[][] matriz;
	
	//Reserva de memoria
	/**
	 * Genera un tablero con las dimensiones especificadas.
	 * 
	 */
	public Tablero() {
		int fila = 7;
		int columna = 7;
		matriz = new Celda[fila][columna];
		for (int i = 0; i<matriz.length; i++) {
			for (int j = 0; j<matriz[i].length; j++) {
				Coordenada coordenada = new Coordenada(i,j);
				TipoCelda tipoCelda = TipoCelda.NORMAL;
		        if ((i == 0 && j == 0) || (i == 6 && j == 0) || (i == 0 && j == 6) || (i == 6 && j == 6)) {
		            tipoCelda = TipoCelda.PROVINCIA;
		        } else if (i == 3 && j == 3) {
		            tipoCelda = TipoCelda.TRONO;
		        }
		        Celda celda = new Celda(coordenada, tipoCelda);
					matriz[i][j] = celda;
				
			}
		}
	}
	
	/**
	 * Genera una cadena con el contenido de piezas actuales del tablero.
	 * 
	 * @return texto con el contenido del tablero
	 */
	public String aTexto() {
	    int filaNumber = 7;
	    String resultado = filaNumber + " ";
	    for (int i = 0; i < matriz.length; i++) {
	        for (int j = 0; j < matriz[i].length; j++) {
	            Celda celda = matriz[i][j];
	            Pieza pieza = celda.consultarPieza();
	            char representacion = pieza == null ? '-' : pieza.aTexto().charAt(0);
	            resultado += representacion + " ";
	        }
	        resultado += " \n";
	        if (filaNumber > 1) {
	            filaNumber--;
	            resultado += filaNumber + " ";
	        }
	    }
	    resultado += "  a b c d e f g";
	    return resultado;
	}

	/**
	 * Hace una clonacion profunda del tablero.
	 * 
	 * @return tablero clonado
	 */
	public Tablero clonar() {
	    Tablero tableroClonado = new Tablero(); 
	    for (int i = 0; i < matriz.length; i++) {
	        for (int j = 0; j < matriz[i].length; j++) {
	            Celda celdaOriginal = matriz[i][j];
	            Celda celdaClonada = celdaOriginal.clonar(); 
	            tableroClonado.matriz[i][j] = celdaClonada;
	        }
	    }
	    return tableroClonado;
	}

	/**
	 * Se encarga de colocar las piezas en la coordenada del tablero correspondiente.
	 * 
	 * @param pieza - pieza a colocar
	 * @param coordenada - lugar donde se va a colocar la pieza
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) {
	    if (coordenada != null && pieza != null) {
	        int fila = coordenada.fila();
	        int columna = coordenada.columna();
	        if (fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz[0].length) {
	            Celda celda = matriz[fila][columna];
	            celda.colocar(pieza);
	        }
	    }
	}

	/**
	 * Devuelve un clon en profundidad de la celda con la coordenada especifica.
	 * 
	 * @param coordenada - indica que celda hay que clonar
	 * @return celda clonada
	 */
	public Celda consultarCelda(Coordenada coordenada) {
	    int fila = coordenada.fila();
	    int columna = coordenada.columna();
	    if (fila >= 0 && fila <= 6 && columna >= 0 && columna <= 6) {
	       Celda celda = matriz[fila][columna];
	        return celda.clonar(); 
	    } else {
	        return null;
	    }
	}

	/**
	 * Devuelve un array de celdas.
	 * 
	 * @return Array de celdas
	 */
	public Celda[] consultarCeldas() {
	    int fila = matriz.length;
	    int columna = matriz[0].length;
	    Celda[] celdas = new Celda[fila * columna];
	    int indice = 0;
	    for (int i = 0; i < fila; i++) {
	        for (int j = 0; j < columna; j++) {
	        	Coordenada coord = new Coordenada(i,j);
	        	if(consultarCelda(coord) != null) {
	            celdas[indice] = consultarCelda(coord);
	            indice++;
	        	}
	        }
	    }
	    return celdas;
	}

	/**
	 * Devuelve un array con las celdas contiguas a la especificada.
	 * 
	 * @param coordenada - posicion de la celda especificada
	 * @return celdas contiguas a la especificada
	 */
	public Celda[] consultarCeldasContiguas(Coordenada coordenada) {
	    int fila = coordenada.fila();
	    int columna = coordenada.columna();
	    int contador = 0;
	    Celda[] celdasContiguas = new Celda[4]; 
	    if (fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz[0].length) {
	    	for (int i = columna - 1; i <= columna + 1; i++) {
	    		if (i >= 0 && i < matriz[0].length && i != columna) {
	    			celdasContiguas[contador] = consultarCelda(new Coordenada(fila, i));
	    			contador++;
	    		}
	    	}
	    	for (int i = fila - 1; i <= fila + 1; i++) {
	    		if (i >= 0 && i < matriz.length && i != fila) {
	    			celdasContiguas[contador] = consultarCelda(new Coordenada(i, columna));
	    			contador++;
	    		}
	    	}
	    }else {
	    	celdasContiguas[contador] = null;
	    }
	    Celda[] contiguas = new Celda[contador];
	    for (int i = 0; i < contador; i++) {
    	contiguas[i] = celdasContiguas[i];
		}
	    return contiguas;
	}

	/**
	 * Devuelve un array con las celdas contiguas a la especificada en vertical.
	 * 
	 * @param coordenada - posicion de la celda especificada
	 * @return celdas contiguas a la especificada
	 */
	public Celda[] consultarCeldasContiguasEnVertical(Coordenada coordenada) {
	    int fila = coordenada.fila();
	    int columna = coordenada.columna();
	    int contador = 0;
	    Celda[] celdasContiguasVertical = new Celda[2];
	    if (fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz[0].length) {
	    	if (fila - 1 >= 0) {
	        	Celda celdaSuperior = consultarCelda(new Coordenada(fila - 1, columna));
	        	if (celdaSuperior != null) {
	            	celdasContiguasVertical[contador++] = celdaSuperior;
	        	}
	    	}
	    	if (fila + 1 < matriz.length) {
	        	Celda celdaInferior = consultarCelda(new Coordenada(fila + 1, columna));
	        	if (celdaInferior != null) {
	            	celdasContiguasVertical[contador++] = celdaInferior;
	        	}
	    	}
	    }
	    Celda[] contiguasVertical = new Celda[contador];
	    for (int i = 0; i < contador; i++) {
	        contiguasVertical[i] = celdasContiguasVertical[i];
	    }
	    return contiguasVertical;
	}


	/**
	 * Devuelve un array con las celdas contiguas a la especificada en horizontal.
	 * 
	 * @param coordenada - posicion de la celda especificada
	 * @return celdas contiguas a la especificada
	 */
	public Celda[] consultarCeldasContiguasEnHorizontal(Coordenada coordenada) {
	    int fila = coordenada.fila();
	    int columna = coordenada.columna();
	    int contador = 0;
	    Celda[] celdasContiguasHorizontal = new Celda[2];
	    if (fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz[0].length) {
	    	if (columna - 1 >= 0) {
	        	Celda celdaIzquierda = consultarCelda(new Coordenada(fila, columna - 1));
	        	if (celdaIzquierda != null) {
	            	celdasContiguasHorizontal[contador++] = celdaIzquierda;
	        	}
	    	}
	    	if (columna + 1 < matriz[0].length) {
	        	Celda celdaDerecha = consultarCelda(new Coordenada(fila, columna + 1));
	        	if (celdaDerecha != null) {
	            	celdasContiguasHorizontal[contador++] = celdaDerecha;
	        	}
	    	}
	    }
	    Celda[] contiguasHorizontal = new Celda[contador];
	    for (int i = 0; i < contador; i++) {
	        contiguasHorizontal[i] = celdasContiguasHorizontal[i];
	    }
	    return contiguasHorizontal;
	}


	/**
	 * Devuelve el numero de columnas del tablero.
	 * 
	 * @return numero de columnas
	 */
	public int consultarNumeroColumnas() {
		return matriz[0].length;
	}
	
	/**
	 * Devuelve el numero de filas del tablero.
	 * 
	 * @return numero de filas
	 */
	public int consultarNumeroFilas() {
		return matriz.length;
	}
	
	/**
	 * Devuelve el numero de piezas que se encuentran en el tablero.
	 * 
	 * @param tipoPieza - El tipo de pieza que se va a contar
	 * @return contador - numero de piezas totales
	 */
	public int consultarNumeroPiezas(TipoPieza tipoPieza) {
	    int contador = 0;
	    for (int fila = 0; fila < matriz.length; fila++) {
	        for (int columna = 0; columna < matriz[0].length; columna++) {
	            Celda celda = matriz[fila][columna];
	            if (celda.consultarPieza() != null && celda.consultarPieza().consultarTipoPieza() == tipoPieza) {
	                contador++;
	            }
	        }
	    }
	    return contador;
	}

	/**
	 * Elimina una pieza del tablero.
	 * 
	 * @param coordenada - lugar de la pieza a eliminar
	 */
	public void eliminarPieza(Coordenada coordenada) {
	    if (coordenada != null) {
	        int fila = coordenada.fila();
	        int columna = coordenada.columna();
	        if (fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz[0].length) {
	            Celda celda = matriz[fila][columna];
	            celda.eliminarPieza();
	        }
	    }
	}
	
	/**
	 * Devuelve la celda correspondiente o null.
	 * 
	 * @param coordenada - posicion de la celda
	 * @return celda o null
	 */
	public Celda obtenerCelda(Coordenada coordenada) {
	    int fila = coordenada.fila();
	    int columna = coordenada.columna();
	    if (fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz[0].length) {
	        return matriz[fila][columna];
	    } else {
	        return null; 
	    }
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(matriz);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		return Arrays.deepEquals(matriz, other.matriz);
	}

	@Override
	public String toString() {
	    String resultado = " ";
	    for (int i = matriz.length - 1; i >= 0; i--) {
	        for (int j = 0; j < matriz[i].length; j++) {
	            Celda celda = matriz[i][j];
	            Pieza pieza = celda.consultarPieza();
	            char representacion = pieza == null ? '-' : pieza.aTexto().charAt(0);
	            resultado += representacion + " ";
	        }
	       
	    }
	    resultado += "  a b c d e f g";
	    return resultado;
	}

}