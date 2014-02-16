import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/* esta clase presenta una ventana para agregar obstaculos, la salida y el robot 
 * tambien permite iniciar la animacion */

@SuppressWarnings("serial")
public class frameConfiguracion extends JFrame implements ActionListener{
	
	static final int ANCHO = 400;
	static final int LARGO = 210;
	
	private JLabel Agregar;
	private JLabel Animacion;
	private JButton BotonObstaculo;
	private JButton BotonSalida;
	private JButton BotonRobot;
	private JButton BotonCerrar;
	private JButton BotonIniciar;
	private static int opcion = 0;
	
	public frameConfiguracion(){
		this.setTitle("Panel de Configuracion");
		this.setBounds(100,100,ANCHO,LARGO);
		this.setVisible(true);
		this.setResizable(false);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(null);
		
		Agregar = new JLabel("AGREGAR ELEMENTO");
		Agregar.setBounds(ANCHO/3, 20, 120, 20);
		add(Agregar);
		BotonObstaculo = new JButton("Obstaculo");
		BotonObstaculo.setBounds(20, 55, 100, 35);
		add(BotonObstaculo);
		BotonSalida = new JButton("Salida");
		BotonSalida.setBounds(150, 55, 80, 35);
		add(BotonSalida);
		BotonRobot = new JButton("Robot");
		BotonRobot.setBounds(260, 55, 80, 35);
		add(BotonRobot);
		Animacion = new JLabel("ANIMACION");
		Animacion.setBounds(ANCHO/2-40, 100, 120, 20);
		add(Animacion);
		BotonIniciar = new JButton("Iniciar");
		BotonIniciar.setBounds(70, 130, 100, 35);
		add(BotonIniciar);
		BotonCerrar = new JButton("Cerrar");
		BotonCerrar.setBounds(200, 130, 100, 35);
		add(BotonCerrar);
		repaint();
		BotonObstaculo.addActionListener(this);
		BotonObstaculo.setActionCommand("obstaculo");
		BotonSalida.addActionListener(this);
		BotonSalida.setActionCommand("salida");
		BotonRobot.addActionListener(this);
		BotonRobot.setActionCommand("robot");
		BotonIniciar.addActionListener(this);
		BotonIniciar.setActionCommand("iniciar");
		BotonCerrar.addActionListener(this);
		BotonCerrar.setActionCommand("cerrar");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "obstaculo"){
			opcion = mapa.rObstaculo;			
		}
		if (e.getActionCommand() == "salida"){			
				opcion = mapa.rSalida;
				BotonSalida.setEnabled(false);										
		}
		if (e.getActionCommand() == "robot"){			
				opcion = mapa.rRobot;
				BotonRobot.setEnabled(false);								
		}
		if (e.getActionCommand() == "iniciar"){
			frameAnimacion.comenzarAnimacion();			
			opcion = 0;
		}
		if(e.getActionCommand() == "cerrar"){
			System.exit(0);
		}
		
	}
	
	public int getOpcion(){
		return opcion;
	}

	public static void resetOpcion(){
		opcion = 0;
	}
}
