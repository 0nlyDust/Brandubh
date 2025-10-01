package brandubh.util;

/**
 * Color - enumerado
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public enum Color {
	
	/**
	 * Valor Blanco del enum Color.
	 */
	BLANCO('B'),
	/**
	 * Valor Negro del enum Color.
	 */
	NEGRO('N');
	
	/**
	 * Atributo de la clase color.
	 */
	private char letra;
	
	/**
	 * Constructor de color.
	 * 
	 * @param letra - Asigna la letra correspondiente al color
	 */
	private Color(char letra){
		this.letra = letra;
	}
	
	/**
	 * Consulta el color del contrario.
	 * 
	 * @return NEGRO si la comparación es con blanco y viceversa
	 */
	public Color consultarContrario() {
		return this.equals(BLANCO)?NEGRO: BLANCO;
	}
	
	/**
	 * Consulta la letra.
	 * 
	 * @return letra correspondiente
	 */
	public char toChar() {
		return letra;
	}
	
}