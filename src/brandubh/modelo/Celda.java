package brandubh.modelo;
import brandubh.util.Coordenada;
import brandubh.util.TipoCelda;
import brandubh.util.TipoPieza;
import brandubh.util.Color;
import java.util.Objects;

/**
 * Celda
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public class Celda  implements Cloneable{
	
	/**
	 * Atributo tipo celda de la clase celda.
	 */
	private TipoCelda tipoCelda;
	/**
	 * Atributo coordenada de la clase celda.
	 */
	private Coordenada coordenada;
	/**
	 * Atributo pieza de la clase celda.
	 */
	private Pieza pieza;
	
	/**
	 * Constructor para celda.
	 * 
	 * @param coordenada - el lugar de la celda a instanciar
	 */
	public Celda(Coordenada coordenada){
		this.coordenada = coordenada;
		this.tipoCelda = TipoCelda.NORMAL;
	}
	
	/**
	 * Constructor para celda con el tipo de celda.
	 * 
	 * @param coordenada - el lugar de la celda a instanciar
	 * @param tipoCelda - el tipo de la celda a instanciar
	 */
	public Celda(Coordenada coordenada,TipoCelda tipoCelda){
		this.coordenada = coordenada;
		this.tipoCelda = tipoCelda;
	}
	
	/**
	 * Realiza una clonacion profunda de la celda especificada.
	 * 
	 * @return celda clonada o null
	 */
	public Celda clonar() {
	    try {
	        Celda celdaClonada = (Celda) super.clone();
	        if (this.pieza != null) {
	            Pieza piezaClonada = this.pieza.clonar(); 
	            celdaClonada.colocar(piezaClonada);
	        }

	        return celdaClonada;
	    } catch (CloneNotSupportedException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	/**
	 * Asigna la pieza a la celda que toque.
	 * 
	 * @param pieza - pieza a colocar
	 */
	public void colocar(Pieza pieza) {
		this.pieza = pieza;
	}
	
	/**
	 * Devuelve el color de la pieza.
	 * 
	 * @return color pieza
	 */
	public Color consultarColorDePieza() {
		if (pieza != null) {
			TipoPieza tipoPieza = pieza.consultarTipoPieza();
			if ( tipoPieza == TipoPieza.ATACANTE ) {
				return Color.NEGRO;
			} else {
				return Color.BLANCO;
			}
		}else {
			return null;
		}
	}
	
	/**
	 * Devuelve la coordenada de la celda.
	 * 
	 * @return coordenada
	 */
	public Coordenada consultarCoordenada(){
		return coordenada;
	}
	
	/**
	 * Comprueba si la celda contiene una pieza o no 
	 * 
	 * @return pieza correspondiente o null
	 */
	public Pieza consultarPieza(){
		if(pieza != null) {
			return pieza;
		}else {
			return null;
		}
	}
	
	/**
	 * Devuelve el tipo de celda.
	 * 
	 * @return tipo de celda
	 */
	public TipoCelda consultarTipoCelda(){
		return tipoCelda;
	}
	
	/**
	 * Se encarga de eliminar la pieza de la celda.
	 */
	public void eliminarPieza() {
		if ( !estaVacia() ) {
			pieza = null;
		}
	}
	
	/**
	 * Comprueba si la celda esta vacía
	 * 
	 * @return true si esta vacia, false en caso contrario
	 */
	public boolean estaVacia(){
		return pieza == null;
	}
    
    @Override
    public String toString() {
        return "Celda [pieza=" + pieza + ", coordenada=" + coordenada + "]";
    }

	@Override
	public int hashCode() {
		return Objects.hash(coordenada, pieza, tipoCelda);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celda other = (Celda) obj;
		return Objects.equals(coordenada, other.coordenada) && Objects.equals(pieza, other.pieza)
				&& tipoCelda == other.tipoCelda;
	}
}