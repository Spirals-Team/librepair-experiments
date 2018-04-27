package com.economizate.vistas;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.economizate.controladores.ControladorIngreso;
import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.UsuariosImpl;

public class Ingreso extends Home implements java.util.Observer{
	
	private static Logger logger = Logger.getLogger(Ingreso.class.getName());
	
	private JFrame ventana;
	private Home ventanaHome;
	
	public JButton botonIngreso, botonOk;
	private JLabel nombreUsuario;
	
	private JLabel saldoUsuario;
	
	private JLabel descripcionLabel, observacionLabel, importeLabel;
	private JTextField descripcion, observacion, importe;
	
	private JDialog dialogoOK;
	
	private String email;
	private double saldo;
	
	Usuarios usuarios;
	
	public Ingreso() {
		iniciarComponentes();
	}
	
	public Ingreso(Home ventanaHome, String email, double saldo) {
		usuarios = new UsuariosImpl(this);
		this.ventanaHome = ventanaHome;
		this.email = email;
		this.saldo = saldo;
		
		iniciarComponentes();
	}
	
	public void iniciarComponentes() {
		iniciarBotonIngreso();
		iniciarLabels();
		iniciarTextFields();
	}
	
	public void iniciarBotonIngreso() {
		botonIngreso =new JButton("Aceptar");
		botonIngreso.setBounds(50,400,100, 40); 
		botonIngreso.addActionListener(
				new ControladorIngreso(this.getUsuario(), this, ventanaHome, usuarios, this.saldos));
	}
	
	public void iniciarLabels() {
		saldoUsuario = new JLabel();
		saldoUsuario.setBounds(280,20, 250,20);  
		saldoUsuario.setText("Saldo: " + saldo);
		
		descripcionLabel = new JLabel("Descripcion");
		descripcionLabel.setBounds(50, 50, 100, 40);
		
		observacionLabel = new JLabel("Observacion");
		observacionLabel.setBounds(50, 150, 100, 40);
		
		importeLabel = new JLabel("Importe");
		importeLabel.setBounds(50, 250, 100, 40);
	}
	
	public void iniciarTextFields() {
		descripcion = new JTextField();
		descripcion.setBounds(150, 50, 200, 40);
		
		observacion = new JTextField();
		observacion.setBounds(150, 150, 200, 40);
		
		importe = new JTextField();
		importe.setBounds(150, 250, 100, 40);
		importe.setText("0");
	}
	
	public void iniciarVista() {
		logger.info("Iniciando vistas de Ingresos");
		
		ventana = new JFrame();
	    
		nombreUsuario = new JLabel();
		nombreUsuario.setBounds(50,50, 250,20);    
		          
		ventana.add(botonIngreso); 
		
		ventana.add(nombreUsuario);
		ventana.add(descripcionLabel);
		ventana.add(observacionLabel);
		ventana.add(importeLabel);
		ventana.add(saldoUsuario);
		
		ventana.add(descripcion);
		ventana.add(observacion);
		ventana.add(importe);
		
		ventana.setTitle("Ingreso");
		ventana.setSize(400,500);
		ventana.setLayout(null); 
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}
	
	public double getSaldo() {
		return saldo;
	}

	public void update(Observable o, Object arg) {
		logger.info("Update como observador : Observable es " 
				+ o.getClass() + ", objecto pasado es " + arg.getClass() + " " + arg + " y mi saldo es: " + saldo);
		
		botonOk = new JButton("OK");
		dialogoOK = new JDialog(ventana, "Transaccion OK", true);
		dialogoOK.setLayout( new FlowLayout() );
		
		dialogoOK.add(botonOk);
		botonOk.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e )  
            {  
				dialogoOK.setVisible(false);  
            }  
		});
		dialogoOK.setSize(200,100);    
		dialogoOK.setVisible(true);  
		
		//Esta es una función de simulación
		//saldoUsuario.setText("Saldo2: " +  saldo.getTotal());
	}
	
	public String getImporteTextFieldValue() {
		return importe.getText();
	}
	
	public String getDescricionTextFieldValue() {
		return descripcion.getText();
	}


	public JLabel getSaldoUsuario() {
		return saldoUsuario;
	}

	public void setSaldoUsuario(JLabel saldoUsuario) {
		this.saldoUsuario = saldoUsuario;
	}
	
	public void addController(ControladorIngreso controlador) {
		botonIngreso.addActionListener(controlador);
	}
	
	public JFrame getVentana() {
		return ventana;
	}

	public void setVentanaHome(Home ventanaHome) {
		this.ventanaHome = ventanaHome;
	}
	
	
	
}
