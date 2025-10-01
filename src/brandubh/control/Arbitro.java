package brandubh.control;
import brandubh.modelo.Celda;
import brandubh.util.Color;
import brandubh.modelo.Jugada;
import brandubh.modelo.Pieza;
import brandubh.modelo.Tablero;
import brandubh.util.Coordenada;
import brandubh.util.Sentido;
import brandubh.util.TipoCelda;
import brandubh.util.TipoPieza;

/**
 * Tablero
 * 
 * @author Maria Molina Goyena
 * @version 1.0
 * @since 1.0
 */
public class Arbitro {
	
	/**
	 * Atributo tablero de la clase Arbitro.
	 */
    private Tablero tablero;
    /**
	 * Atributo que contabiliza las jugadas totales de la clase Arbitro.
	 */
    private int numeroJugada;
    /**
	 * Atributo que dice a quien le toca jugar de la clase Arbitro.
	 */
    private Color turno;
    /**
	 * Atributo que guarda la jugada actual de la clase Arbitro.
	 */
    private Jugada jugadaActual;
    /**
	 * Atributo que guarda el numero total de posiblesSuicidios durante la partida de la clase Arbitro.
	 */
    private int posibleSuicidio = 0;
    /**
	 * Atributo que guarda el numero de jugada anterior de la clase Arbitro.
	 */
    private int contadorJugada = 0;

    /**
     * Constructor Arbitro.
     * 
     * @param tablero - El lugar de juego
     */
    public Arbitro(Tablero tablero) {
        this.tablero = tablero;
        this.numeroJugada = 0; 
        this.turno = null;
    }

    /**
     * Se encarga de cambiar el turno del jugador;
     */
    public void cambiarTurno() {
        if (turno == null) {
            turno = Color.BLANCO;
        } else if (turno == Color.NEGRO) {
            turno = Color.BLANCO;
        } else if (turno == Color.BLANCO){
            turno = Color.NEGRO;
        }
    }

    /**
     * Asigna las piezas a distintas celdas del tablero.
     * 
     * @param tipo - tipo de pieza 
     * @param coordenadas - coordenadas de la pieza
     * @param turnoActual - a quien le toca jugar en cada momento
     */
    public void colocarPiezas(TipoPieza[] tipo, int[][] coordenadas, Color turnoActual) {
    	
        for (int i = 0; i < coordenadas.length; i++) {
            TipoPieza tipoPieza = tipo[i];
            int[] coords = coordenadas[i];
            Coordenada coordenada = new Coordenada(coords[0], coords[1]);
            Pieza pieza = new Pieza(tipoPieza);
            if (pieza != null) {
  
            	tablero.colocar(pieza, coordenada);
            } 
            turno = Color.NEGRO;
        }
    }

    /**
     * Es la configuración incial para jugar.
     */
    public void colocarPiezasConfiguracionInicial() {
            int[][] coordenadas = {
                {3, 3},  // Rey
                {3, 4},  // Defensor
                {3, 2},  // Defensor
                {2, 3},  // Defensor
                {4, 3},  // Defensor
                {0, 3},  // Atacante
                {1, 3},  // Atacante
                {3, 0},  // Atacante
                {3, 1},  // Atacante
                {3, 5},  // Atacante
                {3, 6},  // Atacante
                {5, 3},  // Atacante
                {6, 3}   // Atacante
            };

            TipoPieza[] tipos = {TipoPieza.REY,TipoPieza.DEFENSOR,TipoPieza.DEFENSOR,TipoPieza.DEFENSOR,TipoPieza.DEFENSOR,TipoPieza.ATACANTE,TipoPieza.ATACANTE,TipoPieza.ATACANTE,TipoPieza.ATACANTE,TipoPieza.ATACANTE,TipoPieza.ATACANTE, TipoPieza.ATACANTE,TipoPieza.ATACANTE
            };
            colocarPiezas(tipos, coordenadas, turno);  
        }

    /**
     * Devuelve el numero de jugada actual de la partida.
     * 
     * @return numero de jugada
     */
    public int consultarNumeroJugada() {
        return numeroJugada;
    }

    /**
     * Devuelve un clon del tablero.
     * 
     * @return tablero clonado
     */
    public Tablero consultarTablero() {
        return tablero.clonar(); 
    }

    /**
     * Devuelve a quien le toca jugar en el turno actual.
     * 
     * @return color de turno
     */
    public Color consultarTurno() {
        return turno;
    }

    /**
     * Comprueba si el movimiento a realizar es valido.
     * 
     * @param jugada - coordenadas de la pieza a mover
     * @return true si el movimiento es valido, false en caso contrario
     */
    public boolean esMovimientoLegal(Jugada jugada) {
        Celda origen = jugada.origen();
        Celda destino = jugada.destino();
        if (origen.estaVacia()) { 
            return false;// Restricción: Celda de origen vacía
        }
        Color colorPiezaOrigen = origen.consultarColorDePieza();
        TipoPieza tipoPiezaOrigen = origen.consultarPieza().consultarTipoPieza();
        Coordenada coordenadaDestino = destino.consultarCoordenada();
        if (!jugada.esMovimientoHorizontalOVertical()) { 
            return false;// Restricción: Movimiento no es horizontal o vertical
        }
        if (colorPiezaOrigen != turno) {
            return false;// Restricción: Movimiento realizado fuera de turno
        }
        if (hayPiezaEnElCaminoHorizontalOVertical(jugada)) { 
            return false;// Restricción: Intento de saltar sobre otra pieza
        }
        if (tipoPiezaOrigen != TipoPieza.REY && tablero.obtenerCelda(new Coordenada(3, 3)).consultarPieza()
        	!= null && jugada.destino().consultarCoordenada().equals(new Coordenada(3, 3))) {
            return false;// Restricción: Intento de mover sobre una celda trono sin parar en ella
        }
       if (tipoPiezaOrigen == TipoPieza.DEFENSOR && coordenadaDestino.equals(new Coordenada(3, 3))) {
            return false;// Restricción: Movimiento de una pieza defensora al trono
        }
        if (tipoPiezaOrigen != TipoPieza.REY && (coordenadaDestino.fila() == 0 || coordenadaDestino.fila() == 6) && (coordenadaDestino.columna() == 0 || coordenadaDestino.columna() == 6)) {
            return false; // Restricción: Movimiento de una pieza a las esquinas del tablero
        }
        if(piezaSobrePiezaContigua(jugada)) {
        	return false;// Restricción: Movimiento de una pieza sobre otra pieza contigua adyacente
        }
        return true;
    }

    /**
     * Comprueba si hay alguna pieza en las celdas de alrededor, en sentido horizontal.
     * 
     * @param jugada - coordenadas de la pieza a mover
     * @return true si hay alguna pieza, false en caso contrario
     */
    private boolean hayPiezaEnElCaminoHorizontal(Jugada jugada) {
    	int direccion = 0;
    	Coordenada coordenadaOrigen = jugada.origen().consultarCoordenada();
        Coordenada coordenadaDestino = jugada.destino().consultarCoordenada();
        if (coordenadaOrigen.fila() != coordenadaDestino.fila()){
            return false; 
        }
        if (coordenadaDestino.columna() < coordenadaOrigen.columna()) {
            direccion = -1;
        } else if (coordenadaDestino.columna() > coordenadaOrigen.columna()) {
            direccion = 1;
        } else {
            direccion = 0; 
        }
        for (int i = coordenadaOrigen.columna() + direccion; i != coordenadaDestino.columna(); i += direccion){
            Coordenada coordenadaIntermedia = new Coordenada(coordenadaOrigen.fila(), i);
            Celda celdaIntermedia = tablero.obtenerCelda(coordenadaIntermedia);
            if (celdaIntermedia.consultarPieza() != null) {
                return true; 
            }
        }
        return false; 
    }

    /**
     * Comprueba si hay alguna pieza en las celdas de alrededor, en sentido vertical.
     * 
     * @param jugada - coordenadas de la pieza a mover
     * @return true si hay alguna pieza, false en caso contrario
     */
    private boolean hayPiezaEnElCaminoVertical(Jugada jugada) {
    	 int direccion = 0;
        Coordenada coordenadaOrigen = jugada.origen().consultarCoordenada();
        Coordenada coordenadaDestino = jugada.destino().consultarCoordenada();
        if (coordenadaOrigen.columna() != coordenadaDestino.columna()){
            return false;
        }
        if (coordenadaDestino.fila() < coordenadaOrigen.fila()) {
            direccion = -1;
        } else if (coordenadaDestino.fila() > coordenadaOrigen.fila()) {
            direccion = 1;
        } else {
            direccion = 0;
        }
        for (int i = coordenadaOrigen.fila() + direccion; i != coordenadaDestino.fila(); i += direccion){
            Coordenada coordenadaIntermedia = new Coordenada(i, coordenadaOrigen.columna());
            Celda celdaIntermedia = tablero.obtenerCelda(coordenadaIntermedia);
            if (celdaIntermedia.consultarPieza() != null) {
                return true; 
            }
        }
        return false; 
    }

    /**
     * Comprueba si hay alguna pieza en las celdas de alrededor, en sentido horizontal y vertical.
     * 
     * @param jugada - coordenadas de la pieza a mover
     * @return true si hay alguna pieza, false en caso contrario
     */
    private boolean hayPiezaEnElCaminoHorizontalOVertical(Jugada jugada) {
        Coordenada coordenadaOrigen = jugada.origen().consultarCoordenada();
        Coordenada coordenadaDestino = jugada.destino().consultarCoordenada();
        if (coordenadaOrigen.fila() == coordenadaDestino.fila()) {
            return hayPiezaEnElCaminoHorizontal(jugada); // Movimiento horizontal
        } else if (coordenadaOrigen.columna() == coordenadaDestino.columna()) {
            return hayPiezaEnElCaminoVertical(jugada);  // Movimiento vertical
        }
        return false;
    }

    /**
     * Comprueba si hay alguna pieza en las celdas contiguas.
     * 
     * @param jugada - coordenadas de la pieza a mover
     * @return true si hay alguna pieza, en las celdas contiguas, de distinto color, false en caso contrario
     */
    private boolean piezaSobrePiezaContigua(Jugada jugada) {
        Celda origen = jugada.origen();
        Celda destino = jugada.destino();
        if (origen.consultarPieza() != null && destino.consultarPieza() != null) {
            Celda[] celdasOrigen = tablero.consultarCeldasContiguas(origen.consultarCoordenada());
            Celda[] celdasDestino = tablero.consultarCeldasContiguas(destino.consultarCoordenada());
            for (Celda celdaContigua1 : celdasOrigen) {
                for (Celda celdaContigua2 : celdasDestino) {
                    if (celdaContigua1.consultarCoordenada().equals(celdaContigua2.consultarCoordenada()) && celdaContigua1.consultarPieza() != null && celdaContigua1.consultarPieza().consultarColor() != jugada.origen().consultarPieza().consultarColor()) {
                    	return true;   
                    }
                }
            }
        }
        if (destino.consultarPieza() != null) {
        	return true;
        }
        return false;
    }

    /**
     * Verifica que ha ganado el atacante.
     * 
     * @return true si ha ganado, false en caso contrario
     */
    public boolean haGanadoAtacante() {
    	if (numeroJugada > 0) {
    		int contadorPiezasDistintasF = 0, contadorPiezasDistintasC = 0, provincia = 0, trono = 0, estaEnTrono = 0;
    		Celda celdaActualDestino = jugadaActual.destino();
    		Celda[] celdasActualesDestino = tablero.consultarCeldasContiguas(celdaActualDestino.consultarCoordenada());
    		for (Celda celdaContigua1 : celdasActualesDestino) {
    			if(celdaContigua1.consultarPieza() != null) {
    				if(celdaContigua1.consultarPieza().consultarTipoPieza() == TipoPieza.REY) {
    				Coordenada coordenadaRey = celdaContigua1.consultarCoordenada();
    				Celda celdaRey = tablero.obtenerCelda(coordenadaRey);
    				 return comprobarCeldasRey(celdaRey, contadorPiezasDistintasF, contadorPiezasDistintasC, provincia, trono, estaEnTrono, coordenadaRey);
    				}
    	    	}
    		}
    	}
    	  return false;
    }
    
    /**
     * Contabiliza lo que hay alrededor del rey.
     * 
     * @param celda - celda en donde se encuentra el rey
     * @param contadorPiezasDistintasF - contabiliza el número de piezas distintas en la misma fila que el rey
     * @param contadorPiezasDistintasC - contabiliza el número de piezas distintas en la misma columna que el rey
     * @param provincia - contabiliza si hay alguna provincia alrededor del rey
     * @param trono - contabiliza si hay un trono alrededor del rey
     * @param estaEnTrono - mira si el rey se encuentra en el trono
     * @param coordenadaRey - es la coordenada donde se encuentra el rey
     * @return true si se cumplen las condiciones para que gane el atacante, false en caso contrario
     */
    private boolean comprobarCeldasRey(Celda celda, int contadorPiezasDistintasF, int contadorPiezasDistintasC, int provincia, int trono, int estaEnTrono, Coordenada coordenadaRey) {
    	Celda celdaRey = celda;
    	int filaRey = celdaRey.consultarCoordenada().fila();
		int columnaRey = celdaRey.consultarCoordenada().columna();
		Celda[] celdasRey =  tablero.consultarCeldasContiguas(coordenadaRey);
		if (celdaRey.consultarTipoCelda() == TipoCelda.TRONO) {
			estaEnTrono++;
		}
    	for (Celda celdaContiguaRey : celdasRey) {	
			int filaContiguaRey = celdaContiguaRey.consultarCoordenada().fila();
			int columnaContiguaRey = celdaContiguaRey.consultarCoordenada().columna();
			if (celdaContiguaRey.consultarTipoCelda() == TipoCelda.PROVINCIA) {
				provincia++;
			}
			if (celdaContiguaRey.consultarTipoCelda() == TipoCelda.TRONO) {
				trono++;
			}
			if (celdaContiguaRey.consultarPieza() != null && celdaContiguaRey.consultarPieza().consultarColor() == Color.NEGRO && (filaRey == filaContiguaRey) ) {
				contadorPiezasDistintasF++;
			}
			if (celdaContiguaRey.consultarPieza() != null && celdaContiguaRey.consultarPieza().consultarColor() == Color.NEGRO && (columnaRey == columnaContiguaRey) ) {
				contadorPiezasDistintasC++;
			}
			if (comprobarSiHaGanadoAtacante(contadorPiezasDistintasF, contadorPiezasDistintasC, provincia, trono, estaEnTrono) == true ) {
				return true;
			}
		}
    	return false;
    }
    
    /**
     * Comprueba si se cumplen las condicioes necesarias para que gane el atacante.
     * 
     * @param contadorPiezasDistintasF - contabiliza el número de piezas distintas en la misma fila que el rey
     * @param contadorPiezasDistintasC - contabiliza el número de piezas distintas en la misma columna que el rey
     * @param provincia - contabiliza si hay alguna provincia alrededor del rey
     * @param trono - contabiliza si hay un trono alrededor del rey
     * @param estaEnTrono - mira si el rey se encuentra en el trono
     * @return true en caso de que gane el atacante, false en caso contrario
     */
    private boolean comprobarSiHaGanadoAtacante(int contadorPiezasDistintasF, int contadorPiezasDistintasC, int provincia, int trono, int estaEnTrono) {
    	if((contadorPiezasDistintasF >= 2 || contadorPiezasDistintasC >= 2) && trono == 0  && provincia == 0 && estaEnTrono == 0) {
			return true;
		}
    	if(((contadorPiezasDistintasF >= 2 && contadorPiezasDistintasC >= 1) || (contadorPiezasDistintasF >= 1 && contadorPiezasDistintasC >= 2)) && trono == 1  && provincia == 0 && estaEnTrono == 0) {
			return true;
		}
    	if(contadorPiezasDistintasF >= 2 && contadorPiezasDistintasC >= 2 && trono == 0  && provincia == 0 && estaEnTrono == 1) {
			return true;
		}
    	if( (contadorPiezasDistintasF == 1 || contadorPiezasDistintasC == 1) && provincia == 1 && trono == 0 && estaEnTrono == 0) {
			return true;
		}
    	return false;
    }
    
    /**
     * Verifica que ha ganado el rey.
     * 
     * @return true si ha ganado, false en caso contrario
     */
    public boolean haGanadoRey() {
        Coordenada[] esquinas = {
            new Coordenada(0, 0),
            new Coordenada(0, 6),
            new Coordenada(6, 0),
            new Coordenada(6, 6)
        };
        for (Coordenada c : esquinas) {
            Celda cel = tablero.obtenerCelda(c); // usar obtenerCelda como en otras partes
            if (cel != null && cel.consultarPieza() != null) {
                if (cel.consultarPieza().consultarTipoPieza() == TipoPieza.REY) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Se encarga de mover las piezas en el tablero.
     * 
     * @param jugada - coordenadas de la pieza a mover
     */
    public void mover(Jugada jugada) {
        Coordenada coordenadaOrigen = jugada.origen().consultarCoordenada();
        Coordenada coordenadaDestino = jugada.destino().consultarCoordenada();
        Celda celdaOrigen = tablero.consultarCelda(coordenadaOrigen);
        Pieza piezaAMover = celdaOrigen.consultarPieza();
        tablero.eliminarPieza(coordenadaOrigen);
        tablero.colocar(piezaAMover, coordenadaDestino);
        this.jugadaActual = jugada;
        numeroJugada++;
    }

    /**
     * Comienza la verificación de si hay que hacer alguna captura tras un movimiento.
     */
    public void realizarCapturasTrasMover() {
        if (numeroJugada > 0) {
            Celda celdaActualDestino = jugadaActual.destino();
            Celda celdaActualOrigen = jugadaActual.origen();
            Celda[] celdasActualesDestino = tablero.consultarCeldasContiguas(celdaActualDestino.consultarCoordenada());
            int contadorPiezasDistintas = 0, provincia = 0, trono = 0;
            for (Celda celdaContigua1 : celdasActualesDestino) {
                    if (celdaContigua1.consultarTipoCelda() == TipoCelda.PROVINCIA) {
                        provincia++;
                    }
                    if (celdaContigua1.consultarTipoCelda() == TipoCelda.TRONO) {
                        trono++;
                    }
                    if (celdaContigua1.consultarPieza() != null && celdaActualOrigen.consultarPieza() != null) {
                        if (celdaContigua1.consultarPieza().consultarColor() != celdaActualOrigen.consultarPieza().consultarColor()) {
                            contadorPiezasDistintas++;
                        }
                    }
                    consultarSuicidios(contadorPiezasDistintas, trono, provincia);
                    if (celdaContigua1.consultarPieza() != null) {
                        if( (contadorJugada + 1) == numeroJugada && posibleSuicidio != 0 ) {
                        	posibleSuicidio--;
                        	continue;
                        }else {
                        	consultarCeldasPiezaAComer(celdaContigua1, celdaActualDestino); 
                        }
                   }
            }
        }     
    }
    
    /**
     * Se contabiliza el número de posibles suicidios durante la partida.
     * 
     * @param contadorPiezasDistintas - cuenta número de piezas de distinto color alrededor de la última pieza movida
     * @param trono - cuenta si hay un trono alrededor de la última pieza movida
     * @param provincia - cuenta si hay una provincia alrededor de la última pieza movida
     */
    private void consultarSuicidios( int contadorPiezasDistintas, int trono, int provincia) {
    	if (contadorPiezasDistintas >= 3 && trono == 1) {
            posibleSuicidio++;
            contadorJugada = numeroJugada;
        }
        if (contadorPiezasDistintas >= 1 && (provincia == 1 || trono == 1)) {
        	 posibleSuicidio++;
        	 contadorJugada = numeroJugada;
        }
        if (contadorPiezasDistintas >= 2) {
        	 posibleSuicidio++;
        	 contadorJugada = numeroJugada;
        } 
    }
    
    

    /**
     * Consulta las celdas de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la ultima pieza que se ha movido
     * @param celda2 - celda de destino de la ultima pieza movida
     * @return true si hay alguna captura, false en caso contrario
     */
    private boolean consultarCeldasPiezaAComer(Celda celda1, Celda celda2) {
        Celda celdaContigua1 = celda1;
        Celda celdaActualDestino = celda2;
        Sentido sentido = null;
        int contadorPiezasDistintasF = 0, contadorPiezasDistintasC = 0, contadorPiezasIgualesC = 0, contadorPiezasIgualesF = 0, provinciaF = 0, provinciaC = 0, tronoF = 0, tronoC = 0, rey = 0;
            Coordenada coordenadaPiezaAComer = celdaContigua1.consultarCoordenada();
       	    Celda celdaPiezaAComer = tablero.obtenerCelda(coordenadaPiezaAComer);
            Celda[] celdasPiezaAComer = tablero.consultarCeldasContiguas(coordenadaPiezaAComer);
            for (Celda celdaContiguasAComer : celdasPiezaAComer) {
            	contadorPiezasDistintasF = consultarNumeroPiezasDistintasFPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, contadorPiezasDistintasF);
            	contadorPiezasDistintasC = consultarNumeroPiezasDistintasCPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, contadorPiezasDistintasC);
            	contadorPiezasIgualesF = consultarNumeroPiezasIgualesFPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, contadorPiezasIgualesF);
            	contadorPiezasIgualesC = consultarNumeroPiezasIgualesCPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, contadorPiezasIgualesC);
            	provinciaF = consultarProvinciaFPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, provinciaF);
            	provinciaC = consultarProvinciaCPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, provinciaC);
            	tronoF =consultarTronoFPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, tronoF);
            	tronoC =consultarTronoCPiezaAComer(celdaContiguasAComer, celdaPiezaAComer, tronoC);
            	rey = consultarReyPiezaAComer(celdaContiguasAComer, rey);
            	sentido = consultarSentidoUltimaPiezaMovida(celdaContiguasAComer, celdaActualDestino, celdaPiezaAComer, sentido);
            }
            if (consultarCondicionesCapturaProvincia(celdaPiezaAComer, contadorPiezasDistintasF, contadorPiezasDistintasC, contadorPiezasIgualesF, contadorPiezasIgualesC, provinciaF, provinciaC) == true) {
            	return true;
            }else if (consultarCondicionesCapturaTrono(celdaPiezaAComer, contadorPiezasDistintasF, contadorPiezasDistintasC, contadorPiezasIgualesF, contadorPiezasIgualesC, tronoF, tronoC, rey, sentido) == true) {
            	return true;
            }else if (consultarCondicionesCapturaNormal(celdaPiezaAComer, contadorPiezasDistintasF, contadorPiezasDistintasC) == true) {
            	return true;
            }
            return false;
    }
    
    /**
     * Comprueba en que posición relativa se encuentra la ultima pieza movida en comparación a la pieza que puede ser capturada.
     * 
     * @param celda1 - la celda contigua de la ultima pieza que se ha movido
     * @param celda2 - celda de destino de la ultima pieza movida
     * @param celda3 - la celda de la pieza a comer
     * @param sentido - la variable sentido inicializada a null
     * @return sentidoL
     */
    private Sentido consultarSentidoUltimaPiezaMovida(Celda celda1, Celda celda2, Celda celda3, Sentido sentido) {
    	Celda celdaContiguasAComer= celda1;
    	Celda celdaActualDestino = celda2;
    	Celda celdaPiezaAComer = celda3;
    	Sentido sentidoL = sentido;
    	if(celdaContiguasAComer.consultarCoordenada() == celdaActualDestino.consultarCoordenada() && celdaContiguasAComer.consultarPieza() != null) {
    		if(celdaContiguasAComer.consultarCoordenada().fila() == celdaPiezaAComer.consultarCoordenada().fila() ) {
    			if (celdaContiguasAComer.consultarCoordenada().fila() > celdaPiezaAComer.consultarCoordenada().fila()) {
    				sentidoL = Sentido.VERTICAL_S;
    			}else {
    				sentidoL = Sentido.VERTICAL_N;
    			}
    		}else {
    			if (celdaContiguasAComer.consultarCoordenada().columna() > celdaPiezaAComer.consultarCoordenada().columna()) {
    				sentidoL = Sentido.HORIZONTAL_E;
    			}else {
    				sentidoL = Sentido.HORIZONTAL_O;
    			}
    		}
    	}
    	return sentidoL;
    }
    /**
     * Cuenta el numero de piezas de distinto color en la fila de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param contadorPiezasDistintasF - La variable inicializada a 0
     * @return contadorPiezasDistintasF
     */
    private int consultarNumeroPiezasDistintasFPiezaAComer(Celda celda1, Celda celda2, int contadorPiezasDistintasF) {     	
        Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;
                if (celdaContiguasAComer.consultarPieza() != null && celdaPiezaAComer.consultarPieza() != null) {
                    if (celdaContiguasAComer.consultarPieza().consultarColor() != celdaPiezaAComer.consultarPieza().consultarColor()) {
                        if(celdaContiguasAComer.consultarCoordenada().fila() == celdaPiezaAComer.consultarCoordenada().fila() ) {
                        	contadorPiezasDistintasF++;	
                        }
                    }
                }
         return contadorPiezasDistintasF;   
    }
    
    /**
     * Cuenta el numero de piezas de distinto color en la columna de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param contadorPiezasDistintasC - La variable inicializada a 0
     * @return contadorPiezasDistintasC
     */
    private int consultarNumeroPiezasDistintasCPiezaAComer(Celda celda1, Celda celda2,int contadorPiezasDistintasC) {     	
        Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;       
                if (celdaContiguasAComer.consultarPieza() != null && celdaPiezaAComer.consultarPieza() != null) {
                    if (celdaContiguasAComer.consultarPieza().consultarColor() != celdaPiezaAComer.consultarPieza().consultarColor()) {
                        if(celdaContiguasAComer.consultarCoordenada().columna() == celdaPiezaAComer.consultarCoordenada().columna() ) {
                        	contadorPiezasDistintasC++;	
                        }
                    }
                }
         return contadorPiezasDistintasC;
    }
    
    /**
     * Cuenta el numero de piezas del mismo color en la fila de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param contadorPiezasIgualesF - La variable inicializada a 0
     * @return contadorPiezasIgualesF
     */
    private int consultarNumeroPiezasIgualesFPiezaAComer(Celda celda1, Celda celda2,int contadorPiezasIgualesF) {     	
        Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;       
                if (celdaContiguasAComer.consultarPieza() != null && celdaPiezaAComer.consultarPieza() != null) {
                    if (celdaContiguasAComer.consultarPieza().consultarColor() == celdaPiezaAComer.consultarPieza().consultarColor()) {
                    	if(celdaContiguasAComer.consultarCoordenada().fila() == celdaPiezaAComer.consultarCoordenada().fila() ) {
                    		contadorPiezasIgualesF++;	
                        }
                    }
                }
         return contadorPiezasIgualesF;      
    }
    
    
    /**
     * Cuenta el numero de piezas del mismo color en la columna de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param contadorPiezasIgualesC - La variable inicializada a 0
     * @return contadorPiezasIgualesC
     */
    private int consultarNumeroPiezasIgualesCPiezaAComer(Celda celda1, Celda celda2,int contadorPiezasIgualesC ) {     	
        Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;       
                if (celdaContiguasAComer.consultarPieza() != null && celdaPiezaAComer.consultarPieza() != null) {
                    if (celdaContiguasAComer.consultarPieza().consultarColor() == celdaPiezaAComer.consultarPieza().consultarColor()) {
                        if(celdaContiguasAComer.consultarCoordenada().columna() == celdaPiezaAComer.consultarCoordenada().columna() ) {
                        	contadorPiezasIgualesC++;	
                        }
                    }
                }
         return contadorPiezasIgualesC;          
    }
    
    
    /**
     * Cuenta si hay alguna provincia en la fila de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param provinciaF - La variable inicializada a 0
     * @return provinciaF
     */
     private int consultarProvinciaFPiezaAComer(Celda celda1, Celda celda2, int provinciaF) {     	
        Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;       
    	if (celdaContiguasAComer.consultarTipoCelda() == TipoCelda.PROVINCIA) {
                	if(celdaContiguasAComer.consultarCoordenada().fila() == celdaPiezaAComer.consultarCoordenada().fila() ) {
                    	provinciaF++;	
                    }
                }
        return provinciaF;      
    }
    
    
    /**
     * Cuenta si hay alguna provincia en la columna de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param provinciaC - La variable inicializada a 0
     * @return provinciaC
     */
    private int consultarProvinciaCPiezaAComer(Celda celda1, Celda celda2, int provinciaC) {     	
        Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;       
    	if (celdaContiguasAComer.consultarTipoCelda() == TipoCelda.PROVINCIA) {
                    if(celdaContiguasAComer.consultarCoordenada().columna() == celdaPiezaAComer.consultarCoordenada().columna() ) {
                    	provinciaC++;	
                    }
                }
        return provinciaC;       
    }
    
    /**
     * Cuenta si hay algun trono en la misma fila de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param tronoF - La variable inicializada a 0
     * @return tronoF
     */
    private int consultarTronoFPiezaAComer(Celda celda1, Celda celda2, int tronoF) {     	
    	Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;        
        if (celdaContiguasAComer.consultarTipoCelda() == TipoCelda.TRONO) {
        	if(celdaContiguasAComer.consultarCoordenada().fila() == celdaPiezaAComer.consultarCoordenada().fila() ) {
            	tronoF++;	
            }
        }
        return tronoF;        
    }
    
    /**
     * Cuenta si hay algun trono en la misma columna de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param celda2 - la celda donde se encuentra la la pieza a comer
     * @param tronoF - La variable inicializada a 0
     * @return tronoF
     */
    private int consultarTronoCPiezaAComer(Celda celda1, Celda celda2, int tronoC) {     	
    	Celda celdaContiguasAComer = celda1;
        Celda celdaPiezaAComer = celda2;        
        if (celdaContiguasAComer.consultarTipoCelda() == TipoCelda.TRONO) {
        	if(celdaContiguasAComer.consultarCoordenada().columna() == celdaPiezaAComer.consultarCoordenada().columna() ) {
            	tronoC++;	
            }
        }
        return tronoC;        
    }
    
    /**
     * Mira si el rey se encuentra en el trono de alrededor de la pieza a comer.
     * 
     * @param celda1 - la celda contigua de la pieza a comer
     * @param rey - La variable inicializada a 0
     * @return rey
     */
    private int consultarReyPiezaAComer(Celda celda1, int rey) {     	
        Celda celdaContiguasAComer = celda1;      
        if (celdaContiguasAComer.consultarTipoCelda() == TipoCelda.TRONO) {
          if (celdaContiguasAComer.consultarPieza() != null) {
              rey++;
          }
        }
        return rey;        
    }
    
    /**
     * Verifica si las condiciones de captura se cumplen para una captura con provincia.
     * 
     * @param celda1 - la celda donde se encuentra la la pieza a comer
     * @param contadorPiezasDistintasF - contabiliza el numero de piezas de distinto color en la misma fila que la pieza a comer
     * @param contadorPiezasDistintasC - contabiliza el numero de piezas de distinto color en la misma columna que la pieza a comer
     * @param contadorPiezasIgualesF - contabiliza el numero de piezas del mismo color en la misma fila que la pieza a comer
     * @param contadorPiezasIgualesC - contabiliza el numero de piezas del mismo color en la misma columna que la pieza a comer
     * @param provinciaF - contabiliza el numero de provincias en la misma fila que la pieza a comer
     * @param provinciaC - contabiliza el numero de provincias en la misma columna que la pieza a comer
     * @return true si se hace alguna captura y false en caso contrario
     */
    private boolean consultarCondicionesCapturaProvincia(Celda celda1, int contadorPiezasDistintasF, int contadorPiezasDistintasC, int contadorPiezasIgualesF, int contadorPiezasIgualesC, int provinciaF, int provinciaC ) {
        Celda celdaPiezaAComer = celda1;
    	Coordenada coordenadaPiezaAComer = celda1.consultarCoordenada();     
    	if(celdaPiezaAComer.consultarPieza() != null) {
            if (provinciaC == 1 && contadorPiezasDistintasC == 1) {
            	if( contadorPiezasDistintasF != 0 || contadorPiezasIgualesF != 0 ) {
            		return false;
            	}else {
            		if (celdaPiezaAComer.consultarPieza().consultarTipoPieza() != TipoPieza.REY) {
                        tablero.eliminarPieza(coordenadaPiezaAComer);
                        return true;
                    }
            	}
            }
            if (contadorPiezasDistintasF == 1 && provinciaF == 1) {
            	if( contadorPiezasDistintasC != 0 || contadorPiezasIgualesC != 0 ) {
            		return false;
            	}else {
            		if (celdaPiezaAComer.consultarPieza().consultarTipoPieza() != TipoPieza.REY) {
                        tablero.eliminarPieza(coordenadaPiezaAComer);
                        return true;
                    }
            	}
            }	
        }
    	return false;
    }
    
    
    /**
     * Verifica si las condiciones de captura se cumplen para una captura con trono.
     * 
     * @param celda1 - la celda donde se encuentra la la pieza a comer
     * @param contadorPiezasDistintasF - contabiliza el numero de piezas de distinto color en la misma fila que la pieza a comer
     * @param contadorPiezasDistintasC - contabiliza el numero de piezas de distinto color en la misma columna que la pieza a comer
     * @param contadorPiezasIgualesF - contabiliza el numero de piezas del mismo color en la misma fila que la pieza a comer
     * @param contadorPiezasIgualesC - contabiliza el numero de piezas del mismo color en la misma columna que la pieza a comer
     * @param tronoF - contabiliza si hay trono en la fila de la pieza a comer
     * @param tronoC - contabiliza si hay trono en la columna de la pieza a comer
     * @param rey - mira si el rey esta en el trono
     * @paran sentido - el sentido de la ultima pieza movida
     * @return true si se hace alguna captura y false en caso contrario
     */
    private boolean consultarCondicionesCapturaTrono(Celda celda1, int contadorPiezasDistintasF, int contadorPiezasDistintasC, int contadorPiezasIgualesF, int contadorPiezasIgualesC, int tronoF, int tronoC, int rey, Sentido sentido) {
        Celda celdaPiezaAComer = celda1;
    	Coordenada coordenadaPiezaAComer = celda1.consultarCoordenada();     
    	if(celdaPiezaAComer.consultarPieza() != null) {
    		if ( contadorPiezasDistintasC == 1 && tronoF == 0 && tronoC == 1 && rey == 0 ) {
            	if( (contadorPiezasDistintasF != 0 || contadorPiezasIgualesF != 0) && (sentido == Sentido.VERTICAL_S || sentido == Sentido.VERTICAL_N) ) {
            			return false;
                }else {
                	if (celdaPiezaAComer.consultarPieza().consultarTipoPieza() != TipoPieza.REY) {
                        tablero.eliminarPieza(coordenadaPiezaAComer);
                        return true;
                	}
            	}
            }
    		if (contadorPiezasDistintasF == 1 && tronoF == 1 && tronoC == 0 && rey == 0 ) {
            	if( (contadorPiezasDistintasC != 0 || contadorPiezasIgualesC != 0) && (sentido == Sentido.HORIZONTAL_E || sentido == Sentido.HORIZONTAL_O) ) {
            			return false;
                }else {
                	if (celdaPiezaAComer.consultarPieza().consultarTipoPieza() != TipoPieza.REY) {
                        tablero.eliminarPieza(coordenadaPiezaAComer);
                        return true;
                	}
            	}
            } 	
    	}
    	return false;
    }
    
    /**
     * Verifica si las condiciones de captura se cumplen para una captura normal.
     * 
     * @param celda1 - la celda donde se encuentra la la pieza a comer
     * @param contadorPiezasDistintasF - contabiliza el numero de piezas de distinto color en la misma fila que la pieza a comer
     * @param contadorPiezasDistintasC - contabiliza el numero de piezas de distinto color en la misma columna que la pieza a comer
     * @return true si se hace alguna captura y false en caso contrario
     */
    private boolean consultarCondicionesCapturaNormal(Celda celda1, int contadorPiezasDistintasF, int contadorPiezasDistintasC) {
        Celda celdaPiezaAComer = celda1;
    	Coordenada coordenadaPiezaAComer = celda1.consultarCoordenada();     
    	if(celdaPiezaAComer.consultarPieza() != null) {
            if (contadorPiezasDistintasC == 2 || contadorPiezasDistintasF == 2) {
                if (celdaPiezaAComer.consultarPieza().consultarTipoPieza() != TipoPieza.REY) {
                    tablero.eliminarPieza(coordenadaPiezaAComer);
                    return true;
                }
            } 	
        }
    	return false;
    }
    
}