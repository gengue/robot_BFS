import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class frameAnimacion extends JFrame implements Runnable, MouseListener{
		
	private int ANCHO;
	private int LARGO;
	private mapa tablero;
	private boolean condicion;	
	private Thread hiloPrincipal;
	public static boolean start;
	private frameConfiguracion fc;
	private int velocidad = 500;
	
	public frameAnimacion(int filas, int columnas){
		
		this.ANCHO = columnas*30+10;
		this.LARGO = filas*30+25;
		this.setTitle("Animacion");
		this.setBounds(100,100,ANCHO,LARGO);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		//this.setIgnoreRepaint(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.addMouseListener(this);
		
		tablero = new mapa(filas, columnas);
		
		hiloPrincipal = new Thread(this);
		hiloPrincipal.start();
		start = false;
		fc = new frameConfiguracion();
	}
	
	/* Este hilo controla el loop principal de la animacion */
	public void run(){
		System.out.println("Hilo iniciado");
		condicion = true;
		while(condicion){
			update(); //actualizamos los datos (ejecutamos al algoritmo de busqueda por anchura)
			render(); //pintamos sobre la ventana
			//dormir
			try {Thread.sleep(velocidad);}
			catch (InterruptedException e) {}			
		}			
		System.exit(0);		
	}
	
	/* actualiza el estado de la animacion */
	private void update(){
		if(start){
			tablero.buscarSalida();
		}
		
	}
	
	/* dibuja en la pantalla el estado de la animacion */
	private void render(){
		Graphics g;
		g = this.getGraphics();
		
		if (g!= null)
		{		
			tablero.pintar(g);				
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
	}
	
	public void terminarAnimacion(){
		this.condicion = false;
	}
	public static void comenzarAnimacion(){
		start = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) { // si hace click sobre el tablero, capturamos su posicion
											// y la enviamos a la funcion que modificara el tablero
		tablero.modificarElementosTablero(e.getX(), e.getY(), fc.getOpcion());		
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {			
	}
	@Override
	public void mousePressed(MouseEvent e) {		
	}
	@Override
	public void mouseReleased(MouseEvent e) {		
	}

}
