package com.economizate.vistas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.Criterio;
import com.economizate.servicios.Usuarios;
import com.economizate.servicios.impl.EgresoCriterio;
import com.economizate.servicios.impl.IngresoCriterio;
import com.economizate.servicios.impl.TXTWriter;
import com.economizate.servicios.impl.UsuariosImpl;
import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Reportes extends JFrame implements ActionListener, java.util.Observer {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@SuppressWarnings("unused")
private static Logger logger = Logger.getLogger(Reportes.class.getName());
	
	private JFrame ventana;
	private JButton botonVolver;
	private JButton filtrar;
	private JButton filtrarEgresos;
	private JButton exportar;
	
	private JTextArea listaMovimientos;
	private JLabel lblFiltrar;
	private JDateChooser dateChooserDesde;
	private JDateChooser dateChooserHasta;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JScrollPane listaMovimientosPanel;
	
	private Home ventanaHome;
	
	private Usuarios usuarios;
	private Criterio criterio;
	private String email;
	
	
	public Reportes(String email, Usuarios usuarios, Home home) {
		ventanaHome = home;
		this.usuarios = usuarios;
		this.email = email;
		ventana = new JFrame();
		iniciarBotones();
		iniciarJLabels();
		iniciarJDChoosers();
	}
	
	public void iniciarVista() {
		
		
		ventana.getContentPane().add(botonVolver);
		ventana.getContentPane().add(filtrar);
		ventana.getContentPane().add(filtrarEgresos);
		ventana.getContentPane().add(exportar);
		ventana.getContentPane().add(listaMovimientos);
		ventana.getContentPane().add(listaMovimientosPanel);
		ventana.getContentPane().add(lblFiltrar);
		ventana.getContentPane().add(dateChooserDesde);
		ventana.getContentPane().add(dateChooserHasta);
		ventana.getContentPane().add(lblDesde);
		ventana.getContentPane().add(lblHasta);

		
		ventana.setTitle("Reporte de movimientos");
		ventana.setSize(600,500);
		ventana.getContentPane().setLayout(null); 
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	}
	
	private void iniciarBotones() {
		botonVolver =new JButton("Volver");
		botonVolver.setBounds(50,400,100, 40); 
		botonVolver.addActionListener(this);
		
		exportar =new JButton("Exportar");
		exportar.setBounds(350,400,100, 40); 
		exportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new TXTWriter("test").write(usuarios
							.buscarUsuarioPorEmail(email)
							.getSaldo()
							.getMovimientos().toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		filtrar =new JButton("Filtrar Ingresos");
		filtrar.setBounds(350, 15, 130, 30); 
		filtrar.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e )  
            {  
				criterio = new IngresoCriterio();
				List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(usuarios
						.buscarUsuarioPorEmail(email)
						.getSaldo()
						.getMovimientos());
				listaMovimientos.setText(listaFiltrada.toString());
            }
		});
		
		filtrarEgresos =new JButton("Filtrar Egresos");
		filtrarEgresos.setBounds(350, 50, 130, 30); 
		filtrarEgresos.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e )  
            {  
				criterio = new EgresoCriterio();
				List<MovimientoMonetario> listaFiltrada = criterio.filtrarMovimientos(usuarios
						.buscarUsuarioPorEmail(email)
						.getSaldo()
						.getMovimientos());
				listaMovimientos.setText(listaFiltrada.toString());
            }
		});
	}
	
	private void iniciarJLabels() {
		listaMovimientos = new JTextArea();
		listaMovimientos.setText("Historial movimientos: \n" +
				usuarios
				.buscarUsuarioPorEmail(email)
				.getSaldo()
				.getMovimientos());
		listaMovimientos.setBounds(20, 100, 550, 300);
		listaMovimientos.setEditable(false);
		
		listaMovimientosPanel = new JScrollPane(listaMovimientos);
		listaMovimientosPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		listaMovimientosPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
		getContentPane().add(listaMovimientosPanel);
		
		lblFiltrar = new JLabel("Filtrar:");
		lblFiltrar.setBounds(21, 5, 46, 14);
		getContentPane().add(lblFiltrar);
		
		lblDesde = new JLabel("Fecha desde:");
		lblDesde.setBounds(21, 34, 85, 14);
		getContentPane().add(lblDesde);
		
		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(21, 54, 37, 14);
		getContentPane().add(lblHasta);
		
	}
	
	private void iniciarJDChoosers() {
		dateChooserDesde = new JDateChooser();
		dateChooserDesde.setBounds(145, 30, 119, 20);
		getContentPane().add(dateChooserDesde);

		dateChooserHasta = new JDateChooser();
		dateChooserHasta.setBounds(145, 55, 119, 20);
		getContentPane().add(dateChooserHasta);
	}
	
	public Home getVentanaHome() {
		return ventanaHome;
	}

	public void setVentanaHome(Home ventanaHome) {
		this.ventanaHome = ventanaHome;
	}
	
	public void actionPerformed(ActionEvent e) {
		ventana.setVisible(false);
		ventanaHome.getVentana().setVisible(true);
	}

	public void update(Observable arg0, Object arg1) {
		// TODO Apéndice de método generado automáticamente
		
	}
}
