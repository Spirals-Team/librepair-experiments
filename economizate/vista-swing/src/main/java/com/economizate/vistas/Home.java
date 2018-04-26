package com.economizate.vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.economizate.batch.BackupArchivo;
import com.economizate.entidades.Cuenta;
import com.economizate.entidades.Usuario;
import com.economizate.listeners.BackupListener;
import com.economizate.listeners.EgresoListener;
import com.economizate.listeners.IngresoListener;
import com.economizate.listeners.ReportesListener;
import com.economizate.servicios.InstanciasService;
import com.economizate.servicios.Saldos;
import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.Propiedad;
import com.economizate.servicios.impl.UsuariosImpl;

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
	
	private InstanciasService instancias = new InstanciasService();
	private Usuarios usuarios;
	public Saldos saldos;
	private Usuario usuario;
	private String email;
	
	public IngresoListener ingresoListener;
	
	public Home() {
		this.email = Propiedad.getInstance().getPropiedad("email");
		usuarios = instancias.getUsuariosObservadorService(this);
		saldos = instancias.getSaldosService();
		usuario = usuarios.buscarUsuarioPorEmail(email);
		iniciarComponentes();
		
	}
	
	public void iniciarListeners() {
		ingresoListener = new IngresoListener(this, email, usuarios.obtenerSaldoUsuario(email).getTotal());
		botonIngreso.addActionListener(ingresoListener);
	}
	
	public void actionPerformed(ActionEvent evento) {
		logger.info("Iniciando Acción Vista Home");
		
		nombreUsuario.setText("Bienvenido: " +  usuario.getEmail());
		saldoUsuario.setText("Saldo: " + usuario.getSaldo().getTotal());
		nombreUsuario.setVisible(true);
		saldoUsuario.setVisible(true);
		botonLogin.setVisible(false);
		setVisibilidadBotones(true);
	}
	
	public void iniciarComponentes() {
		ventana = new JFrame();
		iniciarBotones();
		iniciarJLabels();
		iniciarListeners();
	}
	
	public void iniciarBotones() {
		iniciarBotonLogin();
		iniciarBotonIngreso();
		iniciarBotonEgreso();
		iniciarBotonEgresoPeriodico();
		iniciarBotonReportes();
	}
	
	public void iniciarBotonLogin() {
		botonLogin =new JButton("Login");
		botonLogin.setBounds(130,100,100, 40); 
		botonLogin.addActionListener(this);
	}
	
	public void iniciarBotonIngreso() {
		botonIngreso =new JButton("Ingreso");
		botonIngreso.setBounds(70,100,100, 40); 
		botonIngreso.addActionListener(ingresoListener);
	}
	
	public void iniciarBotonEgreso() {
		botonEgreso =new JButton("Egreso");
		botonEgreso.setBounds(250,100,100, 40); 
		botonEgreso.addActionListener(
				new EgresoListener(this, email, usuarios.buscarUsuarioPorEmail(email).getSaldo().getTotal()));
		
	}
	
	public void iniciarBotonEgresoPeriodico() {
		botonEgresosPeriodicos =new JButton("Backup");
		botonEgresosPeriodicos.setBounds(70,200,100, 40); 
		botonEgresosPeriodicos.addActionListener(new BackupListener(new BackupArchivo()));
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
		
		setVisibilidadBotones(false);
		    
		ventana.add(botonLogin); 
		ventana.add(botonIngreso); 
		ventana.add(botonEgreso); 
		ventana.add(botonEgresosPeriodicos); 
		ventana.add(botonReportes); 
		
		ventana.add(nombreUsuario);
		ventana.add(saldoUsuario);
		
		ventana.setTitle("Home");
		ventana.setSize(400,500);
		ventana.setLayout(null); 
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
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
