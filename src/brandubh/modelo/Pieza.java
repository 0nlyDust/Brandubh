package brandubh.modelo;
import brandubh.util.TipoPieza;
import java.util.Objects;
import brandubh.util.Color;

/**
 * Pieza
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public class Pieza implements Cloneable {
	
	/**
	 * Atributo tipoPieza de la clase pieza.
	 */
	private TipoPieza tipoPieza;
	/**
	 * Atributo color de la clase pieza.
	 */
	private Color color;
	
	/**
	 * Constructor de pieza.
	 * 
	 * @param tipoPieza - el tipo de la pieza
	 */
	public Pieza(TipoPieza tipoPieza){
		this.tipoPieza = tipoPieza;
		if( color == null ) {
			if (tipoPieza == TipoPieza.ATACANTE) {
		        this.color  = Color.NEGRO;
		    } else {
		        this.color = Color.BLANCO;
		    }
		}
	}
	
	/**
	 * Clonacion profunda de pieza.
	 * 
	 * @return pieza clonada
	 */
	public Pieza clonar() {
		 try {
	            Pieza piezaClonada = (Pieza) super.clone();
	            return piezaClonada;
	        } catch (CloneNotSupportedException e) {
	            return null;
	        }
	}
	
	/**
	 * Devuelve el olor de la pieza.
	 * 
	 * @return color pieza
	 */
	public Color consultarColor() {
	    return color;
	}
	
	/**
	 * Devuelve el tipo de la pieza.
	 * 
	 * @return tipo pieza
	 */
	public TipoPieza consultarTipoPieza() {
		char caracter = tipoPieza.toChar();
		if( caracter == 'A' ) {
			tipoPieza = TipoPieza.ATACANTE;
		}else if( caracter == 'D' ) {
			tipoPieza = TipoPieza.DEFENSOR;
		}else if( caracter == 'R' ) {
			tipoPieza = TipoPieza.REY;
		}
		return tipoPieza;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, tipoPieza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieza other = (Pieza) obj;
		return color == other.color && tipoPieza == other.tipoPieza;
	}

	/**
	 * Devuelve el caracter correspondiente al tipo de pieza.
	 * 
	 * @return letra o simbolo
	 */
	public String aTexto() {
	        switch (tipoPieza) {
	            case ATACANTE:
	                return "A";
	            case DEFENSOR:
	                return "D";
	            case REY:
	                return "R";
	            default:
	                return "-";
	        }
	    }
}