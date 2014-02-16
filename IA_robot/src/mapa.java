import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class mapa {
	
	private int matriz[][];
	private Rectangle rectangulos[][];
	private Image bloqueSalida;
	private Image bloqueObstaculo;
	private Image bloqueCamino;
	private Image bloqueRobot;
	private Image bloqueRuta;
	/* estos atributos representan los elementos del teclado */
	public static final int rCamino = 0;
	public static final int rObstaculo = 1;
	public static final int rSalida = 2;	
	public static final int rRobot = 3;
	public static final int rRuta = 4;
	private BPA bpa; //objeto que ejecututara el algoritmo de busqueda	
	private int[] ruta; //orden de los pasos de la ruta.
	private int contador = 0; //contador auxiliar para pintar la ruta
	boolean detenerPintado = false; //para el final de la animacion
	
	public mapa(int filas, int columnas){	
				
		matriz = new int[filas][columnas];
		rectangulos = new Rectangle[filas][columnas];
		
		for(int x=0;x<filas;x++){
			for(int y=0;y<columnas;y++){
				matriz[x][y] = rCamino;
			}
		}		
		
		bpa = new BPA(matriz);
		// cargamos las imagenes
		ImageIcon img1 = new ImageIcon("images/piso2.png");
		ImageIcon img2 = new ImageIcon("images/pared2.png");
		ImageIcon img3 = new ImageIcon("images/salida.png");
		ImageIcon img4 = new ImageIcon("images/robot.png");	
		ImageIcon img5 = new ImageIcon("images/ruteBlock.png");
		
		bloqueSalida = img3.getImage();
		bloqueObstaculo = img2.getImage();
		bloqueCamino = img1.getImage();
		bloqueRobot = img4.getImage();
		bloqueRuta = img5.getImage();
		
		llenarMatrizRectangulos();		
	}
	
	/* esta funcion crea un conjunto de rectangulos en semejanza a la matriz 
	 * de las imagenes, esto con el fin de capturar con precision cuando el usuario
	 * hace click sobre el tablero para modificar un elemento */
	private void llenarMatrizRectangulos() {
		int dx=0, dy=16;
		
		for(int x=0;x<matriz.length;x++){
			for(int y=0;y<matriz[0].length;y++){
				rectangulos[x][y] = new Rectangle(dx,dy, bloqueCamino.getWidth(null), bloqueCamino.getHeight(null));
				dx+=31;
			}
			dx = 0;
			dy+=31;
		}		
	}
	
	public void pintar(Graphics g) {
		int dx= 0, dy=16;		
		//dibujamos las imagenes en la pantalla como un tablero (matriz)
		if(!detenerPintado){
			for(int x=0;x<matriz.length;x++){
				for(int y=0;y<matriz[0].length;y++){
					switch(matriz[x][y]){
						case 0:
							g.drawImage(getBloqueCamino(), dx, dy, null);
							break;
						case 1:
							g.drawImage(getBloqueObstaculo(), dx, dy, null);
							break;	
						case 2:
							g.drawImage(getBloqueSalida(), dx, dy, null);
							break;	
						case 3:
							g.drawImage(getBloqueRobot(), dx, dy, null);
							break;
						case 4:							
							if(bpa.getResul()){
								g.setColor(Color.WHITE);
								g.drawImage(getBloqueRuta(), dx, dy, null);
								g.drawString(ruta[contador]+"", dx+10, dy+20); //pintamos el numero del paso
								contador++;	
								detenerPintado = true;
							}											
							break;	
					}
					dx+=31;
				}//fin del primer for
				dx = 0;
				dy+=31;
			}//fin del segundo for		
		}		
	}
	
	/* Esta clase recibe el punto capturado por el mouse y el valor del elemento que desea poner 
	 * el punto se convierte en un rectangulo de 1/5 del tamaño del de las imagenes para luego buscar
	 * si esta contenido en el conjunto de rectangulos creados anteriormente */
	public void modificarElementosTablero(int f, int c, int valor){		
		
		Rectangle r = new Rectangle(f,c, bloqueCamino.getWidth(null)/5, bloqueCamino.getHeight(null)/5);		
		boolean contenido = false;
		
		if(valor != rCamino){  //si el valor no representa un camino libre			
			while(!contenido){  
				for(int x=0;x<matriz.length && !contenido;x++){
					for(int y=0;y<matriz[0].length && !contenido;y++){
						/* si el rectangulo de prueba esta contenido  en la matriz de rectangulos*/
						if(rectangulos[x][y].contains(r)){ 
							if(valor==rSalida || valor==rRobot){
								frameConfiguracion.resetOpcion(); //reset porque solo se puede poner una
																//salida y un robot
							}
							matriz[x][y] = valor; //modificamos el valor en la matriz real
							contenido = true;
						}
					}
				}
			}
		}		
	}

	
	public void buscarSalida(){
		bpa.actualizarMatriz(matriz);
		bpa.buscarSalida();
		this.matriz = bpa.getMatriz();
		
		if(bpa.getResul()){  //si ya encontro la salida, ponemos en el mapa las casillas recorridas
							// es decir, la ruta.
			trazarRuta();
		}
	}
	
	public void trazarRuta(){
		
		int[][] cola = getBpa().getCola();	
		int c = 0; //var auxiliar
		ruta = new int[cola.length];
					
		for(int x=0;x<cola.length && cola[x][0] != -1;x++){
			matriz[cola[x][0]][cola[x][1]]= rRuta;  //fijamos los bloques rojos en el tablero
		}
		
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[0].length;j++){  //recorremos el tablero
				/* donde se encuentren bloques de ruta (rojos) obtenemos sus coordenadas y buscamos
				 * en la cola, para asignarle su numero de orden correspondiente almacendandolo en 
				 * el arreglo ruta[]
				 */
				if(matriz[i][j] == rRuta){  
					for(int k=0;k<cola.length && cola[k][0] != -1;k++){
						if(cola[k][0] == i && cola[k][1] == j){
							ruta[c] = k+1;  //almacenamos el numero de orden
							c++;
						}
					}
				}
			}
		}
	}
	public int[][] getMatriz() {
		return matriz;
	}
	
	public Image getBloqueSalida() {
		return bloqueSalida;
	}

	public Image getBloqueObstaculo() {
		return bloqueObstaculo;
	}

	public Image getBloqueCamino() {
		return bloqueCamino;
	}
	public Image getBloqueRuta() {
		return bloqueRuta;
	}
	public Image getBloqueRobot() {
		return bloqueRobot;
	}
	public BPA getBpa(){
		return bpa;
	}
	
}
