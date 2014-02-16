import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class frameGenerador extends JFrame implements ActionListener{
	
	static final int ANCHO = 250;
	static final int LARGO = 170;
	
	private JFormattedTextField tf_filas;
	private JFormattedTextField tf_columnas;
	private JLabel filasLabel;
	private JLabel columnasLabel;
	private JButton botonSiguiente;
	private JButton botonCerrar;
	
	
	public frameGenerador(){
		this.setTitle("Inteligencia Artificial - Primer paso");
		this.setBounds(100,100,ANCHO,LARGO);
		this.setVisible(true);
		this.setResizable(false);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(null);
		
		filasLabel = new JLabel("Numero De Filas");
		filasLabel.setBounds(20, 20, 120, 20);
		add(filasLabel);
		tf_filas = new JFormattedTextField();
		tf_filas.setBounds(150, 20, 40, 20);			
		add(tf_filas);		
		columnasLabel = new JLabel("numero de Columnas");
		columnasLabel.setBounds(20, 50, 140, 20);
		add(columnasLabel);
		tf_columnas = new JFormattedTextField();
		tf_columnas.setBounds(150, 50, 40, 20);
		add(tf_columnas);
		botonSiguiente = new JButton("Siguiente");
		botonSiguiente.setBounds(20, 90, 100, 35);
		add(botonSiguiente);
		botonCerrar = new JButton("Cerrar");
		botonCerrar.setBounds(140, 90, 80, 35);
		add(botonCerrar);
		tf_filas.requestFocus();
		repaint();
		botonSiguiente.addActionListener(this);
		botonSiguiente.setActionCommand("siguiente");
		botonCerrar.addActionListener(this);
		botonCerrar.setActionCommand("cerrar");
	}
     
	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "siguiente"){ //captura las filas y las columnas deseadas y las envia
												//para generar el tablero.
			frameAnimacion fa = new frameAnimacion(Integer.parseInt(tf_filas.getText()), 
					                               Integer.parseInt(tf_columnas.getText()));
			this.dispose();
		}
		if(e.getActionCommand() == "cerrar"){
			System.exit(0);
		}
		
	}

	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		frameGenerador ventana = new frameGenerador();
	}

	
}
