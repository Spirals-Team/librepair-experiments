package com.economizate.vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.economizate.batch.BackupArchivo;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Usuario;
import com.economizate.listeners.BackupListener;
import com.economizate.listeners.EgresoListener;
import com.economizate.listeners.IdiomaListener;
import com.economizate.listeners.IngresoListener;
import com.economizate.listeners.ReportesListener;
import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.CuentaImpl;
import com.economizate.servicios.impl.Idioma;
import com.economizate.servicios.impl.ManejadorEtiqueta;
import com.economizate.servicios.impl.ManejadorIdioma;
import com.economizate.servicios.impl.Propiedad;
import com.lowagie.text.Image;
import java.awt.BorderLayout;

public class Home implements ActionListener, java.util.Observer{
	
	private static Logger logger = Logger.getLogger(Home.class.getName());
	
	private JFrame ventana;
	private JButton botonLogin;
	public JButton botonIngreso;
	public JButton botonEgreso;
	private JButton botonEgresosPeriodicos;
	private JButton botonReportes;
	private JLabel nombreUsuario;
	private JLabel saldoUsuario = new JLabel();
	
	private JButton botonCastellano;
	private JButton botonIngles;
	
	//private InstanciasService instancias = new InstanciasService();
	private Usuarios usuarios;
	public Cuenta saldos;
	private Usuario usuario;
	private String email;
	
	public IngresoListener ingresoListener;
	
	private ManejadorEtiqueta etiquetas;
	
	public Home() {
		this.email = Propiedad.getInstance().getPropiedad("email");
		//usuarios = new ConectorUsuario(). instancias.getUsuariosObservadorService(this);
		saldos = new Cuenta();
		etiquetas = ManejadorEtiqueta.getInstance();
		//usuario = usuarios.buscarUsuarioPorEmail(email);
		iniciarComponentes();
		
	}
	
	public void iniciarListeners() {
		ingresoListener = new IngresoListener(this, email, saldos.getTotal());
		botonIngreso.addActionListener(ingresoListener);
	}
	
	public void actionPerformed(ActionEvent evento) {
		logger.info("Iniciando Acción Vista Home");
		
		nombreUsuario.setText("Bienvenido: " +  this.email);
		saldoUsuario.setText("Saldo: " + saldos.getTotal());
		nombreUsuario.setVisible(true);
		saldoUsuario.setVisible(true);
		botonLogin.setVisible(false);
		
		setVisibilidadBotones(true);
		iniciarEtiquetas();
	}
	
	public void iniciarComponentes() {
		ventana = new JFrame();
		
		iniciarBotones();
		iniciarJLabels();
		iniciarListeners();
	}
	
	public void iniciarBotones() {
		iniciarBotonCastellano();
		iniciarBotonIngles();
		iniciarBotonLogin();
		iniciarBotonIngreso();
		iniciarBotonEgreso();
		iniciarBotonEgresoPeriodico();
		iniciarBotonReportes();
	}
	
	public void iniciarEtiquetas() {
		botonLogin.setText(etiquetas.getMensaje("etiquetaLogin"));
		botonIngreso.setText(etiquetas.getMensaje("etiquetaIngreso"));
		botonEgreso.setText(etiquetas.getMensaje("etiquetaEgreso"));
		botonReportes.setText(etiquetas.getMensaje("etiquetaReporte"));
	}
	
	public void iniciarBotonCastellano() {
		botonCastellano =new JButton();
		botonCastellano.setBounds(200,10,50, 40); 
		try {
			ImageIcon warnIcon = new ImageIcon(this.getClass().getResource("/imagenes/if_flag-spain_748120.png"));
			
			botonCastellano.setIcon(warnIcon);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		botonCastellano.addActionListener(new IdiomaListener(Idioma.ESPANIOL));
	}
	
	public void iniciarBotonIngles() {
		botonIngles =new JButton();
		botonIngles.setBounds(260,10,50, 40); 
		try {
			ImageIcon warnIcon = new ImageIcon(this.getClass().getResource("/imagenes/if_flag-united-kingdom_748024.png"));
			
			botonIngles.setIcon(warnIcon);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		botonIngles.addActionListener(new IdiomaListener(Idioma.INGLES));
	}
	
	public void iniciarBotonLogin() {
		botonLogin =new JButton(etiquetas.getMensaje("etiquetaLogin"));
		botonLogin.setBounds(130,100,100, 40); 
		botonLogin.addActionListener(this);
	}
	
	public void iniciarBotonIngreso() {
		botonIngreso =new JButton(etiquetas.getMensaje("etiquetaBienvenido"));
		botonIngreso.setBounds(70,100,100, 40); 
		botonIngreso.addActionListener(ingresoListener);
	}
	
	public void iniciarBotonEgreso() {
		botonEgreso =new JButton("Egreso");
		botonEgreso.setBounds(250,100,100, 40); 
		botonEgreso.addActionListener(
				new EgresoListener(this, email, saldos.getTotal()));
		
	}
	
	public void iniciarBotonEgresoPeriodico() {
		botonEgresosPeriodicos =new JButton("Backup");
		botonEgresosPeriodicos.setBounds(70,200,100, 40); 
		botonEgresosPeriodicos.addActionListener(new BackupListener(new BackupArchivo("src/test/java/com/economizate/")));
	}
	
	public void iniciarBotonReportes() {
		botonReportes =new JButton("Reportes");
		botonReportes.setBounds(250,200,100, 40); 
		botonReportes.addActionListener(new ReportesListener(this, email));
		
	}
	
	public void iniciarJLabels() {
		nombreUsuario = new JLabel();
		nombreUsuario.setBounds(20,50, 250,20);      
		nombreUsuario.setVisible(false);
		
		//saldoUsuario = new JLabel();
		saldoUsuario.setBounds(280,50, 250,20);      
		saldoUsuario.setVisible(false);
	}
	
	public void iniciarVista() {
		logger.info("Iniciando Vista Home");
		
		if(botonLogin.isVisible()) {
			setVisibilidadBotones(false);
			ventana.repaint();
			ventana.validate();
			ventana.getContentPane().add(botonCastellano);
			ventana.getContentPane().add(botonIngles);
			ventana.getContentPane().add(botonLogin); 
			ventana.getContentPane().add(botonIngreso); 
			ventana.getContentPane().add(botonEgreso); 
			ventana.getContentPane().add(botonEgresosPeriodicos); 
			ventana.getContentPane().add(botonReportes); 
			
			ventana.getContentPane().add(nombreUsuario);
			ventana.getContentPane().add(saldoUsuario);
			
			ventana.setTitle("Home");
			ventana.setSize(400,500);
			ventana.getContentPane().setLayout(null); 
			ventana.setVisible(true);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		}
		
		iniciarEtiquetas();
	}
	
	public void setVisibilidadBotones(boolean visible) {
		//botonLogin.setVisible(!visible);
		botonIngreso.setVisible(visible);
		botonEgreso.setVisible(visible);
		botonEgresosPeriodicos.setVisible(visible);
		botonReportes.setVisible(visible);
	}
	
	public void update(Observable o, Object arg) {
		logger.info("Inicio acción update observable Home");
		Cuenta cuenta = (Cuenta) o;
		double total = (Double) arg;
		
		saldoUsuario.setText("Saldo2: " +  total);
	}
	
	public JFrame getVentanaHome() {
		return ventana;
	}
	
	public void setSaldoUsuario(String nuevoSaldo) {
		saldoUsuario.setText(nuevoSaldo);
	}
	
	public JFrame getVentana() {
		return ventana;
	}
	
	public Usuarios getServicioUsuario() {
		return usuarios;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setServicioUsuario(Usuarios usuarios) {
		this.usuarios = usuarios;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public String getEmail() {
		return this.email;
	}
}
