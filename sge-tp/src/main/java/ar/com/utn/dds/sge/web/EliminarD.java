package ar.com.utn.dds.sge.web;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class EliminarD
 */
@WebServlet("/EliminarD")
public class EliminarD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PATH_JSON_CLIENTES = "C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/src/main/resources/Clientes.json";
	public static String usu, pass;
	JsonMapper mapper = new JsonMapper();
	List<Cliente> clientesJson;
	Dispositivo dispositivo = new Dispositivo();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminarD() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i, j = 0;
		HttpSession sesion = request.getSession();
		clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES));
		PrintWriter out = response.getWriter();
		
	    for (i = 0; i < clientesJson.size(); i++) {
			if(sesion.getAttribute("usuario").equals(clientesJson.get(i).getUserName())){j=i;};
			
					}
	    out.println("<h1>"+ clientesJson.get(j).getNombre()+ "</h1>");
	    out.println("<hr>" );
	    out.println("<h1>"+ "Eliminar Dispositivos" +"</h1>");
	    out.println("<hr>" );
		out.println("<select name=\"dispositivo\" size=\"8\">");
		
		for (i = 0; i < clientesJson.get(j).getDispositivos().size(); i++) {
			out.println("<option value=\""+ i +"\">"+ clientesJson.get(j).getDispositivos().get(i).getTipo()+ " " + clientesJson.get(j).getDispositivos().get(i).getNombre()+ "</option>");
			
			
		}
		out.println("</select>");
		
		
		out.println("<form action=\"EliminarDispositivos\" method=\"POST\">");
		out.println("<input type=\"submit\" value=\"Eliminar Dispositivos\" />");
		out.println("<input type=\"button\" value=\"ATRAS\" name=\"Back2\" onclick=\"history.back()\" />");	
		out.println("</form>");

	

		

		
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
