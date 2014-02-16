import javax.swing.JOptionPane;

/* Busqueda por Amplitud */

public class BPA {
	
	private int matriz[][];	
	private int cola[][];
	private int cx=0, n=0; //cx es el contador que ayudara a las inserciones en la cola. n es cada paso del robot
	private boolean Esprimero = true;
	private boolean resul = false;
	
	public BPA(int[][] matriz){
		this.matriz = matriz;		
		this.cola = new int[(matriz.length)*(matriz[0].length)][2];
		for(int x=0;x<cola.length;x++){
			for(int y=0;y<cola[0].length;y++){
				cola[x][y] = -1;
			}
		}
	}
	
	public int[][] buscarNodoRaiz(){ //buscamos la posicion del robot (raiz) en el tablero
		
		int[][] coordenadas = new int[1][2];
		boolean flag = false;
		
		for(int x=0;x<matriz.length && !flag;x++){
			for(int y=0;y<matriz[0].length && !flag ;y++){
				if(matriz[x][y] == mapa.rRobot){
					coordenadas[0][0] = x;
					coordenadas[0][1] = y;
					flag = true;
				}
			}
		}		
		return coordenadas;
	}
		
	public void buscarSalida(){  //esta es la funcion principal, que se invoca desde la clase mapa
		int[][] raizCoord = buscarNodoRaiz();
		
		if(Esprimero){ //cuando es primera vez, usamos el nodo raiz original
			cola[cx][0] = raizCoord[0][0];
			cola[cx][1] = raizCoord[0][1];
			cx++;
			Esprimero=false;
		}else{  //en caso contrario, movemos la raiz a la posicion que vamos a visitar
			matriz[ raizCoord[0][0] ][ raizCoord[0][1] ] = mapa.rCamino;
			try{
				if(matriz[ cola[n-1][0] ][ cola[n-1][1] ] == mapa.rSalida){ //si encontramos la salida
					frameAnimacion.start = false;
					resul = true;
					JOptionPane.showMessageDialog(null, "El robot ha encontrado la salida en "+(n+1)+" pasos");
				}else{				
					matriz[ cola[n-1][0] ][ cola[n-1][1] ] = mapa.rRobot;
					raizCoord[0][0]=cola[n-1][0];          
					raizCoord[0][1]=cola[n-1][1];
				}				
			}catch(Exception e){ // si no podemos acceder es porque ya no hay casillas que se puedan revisar
				JOptionPane.showMessageDialog(null, "no se puede llegar a la salida");
				frameAnimacion.start = false;
				resul = true;
				return;
			};
		}		
		
		if(n < cx){ //verificamos las posiciones adyacentes al nodo mientras haya elementos en la cola
			visitar(raizCoord[0][0], raizCoord[0][1]+1); //derecha
			visitar(raizCoord[0][0]-1, raizCoord[0][1]); //arriba
			visitar(raizCoord[0][0], raizCoord[0][1]-1); //izquierda
			visitar(raizCoord[0][0]+1, raizCoord[0][1]); //abajo
		}		
		n++; //el grado de la busqueda	
	}
		
	public void visitar(int x, int y){		
		if(x < 0 || x >= matriz.length) {return;}  //si es una pared
		if(y < 0 || y >= matriz[0].length) {return;}  // si es una pared
		if(matriz[x][y] == mapa.rObstaculo) {return;} // si hay un obstacuo
		
		for(int i=0;i<cola.length;i++){  //si ya la hemos visitado
			if(cola[i][0] == x && cola[i][1] == y){
				return;
			}
		}
		//como todo anda bien, agregamos a la cola
		cola[cx][0] = x;
	    cola[cx][1] = y;
	    cx++; //preparamos para la siguiente insercion
	}
	
	public void actualizarMatriz(int[][] matriz){
		this.matriz = matriz;
	}
	public int[][] getMatriz(){
		return this.matriz;
	}
	public int[][] getCola(){
		return this.cola;
	}
	public boolean getResul(){
		return this.resul;
	}
	public void setResul(boolean r){
		this.resul = r;
	}
}
