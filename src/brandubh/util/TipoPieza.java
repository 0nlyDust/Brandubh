package brandubh.util;

/**
 * TipoPieza - enumerado
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public enum TipoPieza {
	
	/**
	 * Valor Defensor del enum.
	 */
	DEFENSOR('D',Color.BLANCO),
	/**
	 * Valor Atacante del enum.
	 */
	ATACANTE('A',Color.NEGRO),
	/**
	 * Valor Rey del enum.
	 */
	REY('R',Color.BLANCO);	
	
	/**
	 * Atributo tipo caracter de la clase TipoPieza
	 */
	private char caracter;
	/**
	 * Atributo tipo color de la clase TipoPieza
	 */
	private Color color;
	
	/**
	 * Contructor de TipoPieza.
	 * 
	 * @param caracter - letra del color correspondiente
	 * @param color - color asignado
	 */
	private TipoPieza(char caracter, Color color){
		this.caracter=caracter;
		this.color=color;
		
	}
	
	/**
	 * Consulta el caracter.
	 * 
	 * @return letra correpondiente al tipo de pieza
	 */
	public char toChar() {
		return caracter;
	}
	
	/**
	 * Consulta el color.
	 * 
	 * @return color correspondiente al tipo de pieza
	 */
	public Color consultarColor() {
		return color;
	}
}