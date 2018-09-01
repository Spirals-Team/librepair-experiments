package ar.com.utn.dds.sge.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ar.com.utn.dds.sge.mappers.JsonMapper;
import ar.com.utn.dds.sge.models.Cliente;
import ar.com.utn.dds.sge.models.Dispositivo;
import ar.com.utn.dds.sge.models.DispositivoEstandar;
import ar.com.utn.dds.sge.models.DispositivoInteligente;
/**
 * Servlet implementation class AgregarD
 */
@WebServlet("/AgregarD")
public class AgregarD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PATH_JSON_CLIENTES = "C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/src/main/resources/Clientes.json";
	public static String usu, pass;
	JsonMapper mapper = new JsonMapper();
	List<Cliente> clientesJson;
	DispositivoEstandar dispositivo = new DispositivoEstandar(null, null, null);
    DispositivoInteligente dispositivo1 =  new DispositivoInteligente(null, null, null); 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgregarD() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Creo el Dispositivo
		int i, j = 0;
		HttpSession sesion = request.getSession();
		clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES));
	    
		
		
		for (i = 0; i < clientesJson.size(); i++) {
			if(sesion.getAttribute("usuario").equals(clientesJson.get(i).getUserName())){j=i;};
			
					}
		
		if(request.getParameter("id_categoria")=="1") {
			dispositivo.setTipo(request.getParameter("Tipo"));
			dispositivo.setNombre(request.getParameter("Nombre"));
			dispositivo.setConsumo(Float.parseFloat(request.getParameter("Consumo")));
			clientesJson.get(j).agregarDispositivoEstandar(dispositivo);
				
		}else {
			dispositivo1.setTipo(request.getParameter("Tipo"));
			dispositivo1.setNombre(request.getParameter("Nombre"));
			dispositivo1.setConsumo(Float.parseFloat(request.getParameter("Consumo")));
			dispositivo1.setHumedad(Float.parseFloat(request.getParameter("Humedad")));
//			dispositivo1.setEstado(Boolean.parseBoolean(request.getParameter("Estado")));
			dispositivo1.setMovimiento(Boolean.parseBoolean(request.getParameter("Movimiento")));
			dispositivo1.setTemperatura(Float.parseFloat(request.getParameter("Temperatura")));
			clientesJson.get(j).agregarUnDispositivoInteligente(dispositivo1);
		}
		
		
		//
	    
	    clientesJson.get(j).agregarDispositivoEstandar(dispositivo);
	    mapper.escribirArchivo(PATH_JSON_CLIENTES, (mapper.crearClientesJson(clientesJson)));
		response.sendRedirect("loginExito.jsp");
	    
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
