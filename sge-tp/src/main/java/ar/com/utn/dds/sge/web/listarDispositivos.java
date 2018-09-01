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

/**
 * Servlet implementation class listarDispositivos
 */
@WebServlet("/listarDispositivos")
public class listarDispositivos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PATH_JSON_CLIENTES = "C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/src/main/resources/Clientes.json";
	JsonMapper mapper = new JsonMapper();
	List<Cliente> clientesJson;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listarDispositivos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
	PrintWriter out = response.getWriter();
	int i, j = 0;
	clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES));
    
	out.println("<style>");
	out.println("table {");
	out.println("border-collapse: collapse;");
	out.println("width: 100%;");
	out.println("}");

	out.println("th, td {");
	out.println("    text-align: left;");
	out.println("    padding: 8px;");
	out.println("}");

	out.println("tr:nth-child(even){background-color: #f2f2f2}");

	out.println("th {");
	out.println("    background-color: #4CAF50;");
	out.println("    color: white;");
	out.println("}");
	out.println("</style>");
	
	
	
	
	
	
	
    for (i = 0; i < clientesJson.size(); i++) {
		if(sesion.getAttribute("usuario").equals(clientesJson.get(i).getUserName())){j=i;};
		
				}
    out.println("<h1>"+ clientesJson.get(j).getNombre()+ "</h1>");
    out.println("<hr>" );
    out.println("<h1>"+ sesion.getAttribute("usuario") +"</h1>");
    out.println("<hr>" );
	
    out.println("<table class=\"Dispositivos\">");
    out.println("<tr>");
    out.println("<th>Especie</th>");
    out.println("<th>Tipo</th>");
    out.println("<th>Nombre</th>");
    out.println("<th>Consumo(KW/h)</th>");
    out.println("</tr>");
	for (i = 0; i < clientesJson.get(j).getDispositivos().size(); i++) {
		out.println("</tr>");
		out.println("<td>"+ clientesJson.get(j).getDispositivos().get(i).getTipo()+ "</td>");
		out.println("<td>"+ clientesJson.get(j).getDispositivos().get(i).getNombre()+ "</td>");
		out.println("<td>"+  clientesJson.get(j).getDispositivos().get(i).getConsumo()+ "</td>");
		out.println("</tr>");
		
		
	}
	out.println("</table>");
	
	
	out.println("<form action=\"AgregarDispositivos\" method=\"POST\">");
	out.println("<input type=\"submit\" value=\"Agregar Dispositivos\" />");
	out.println("<input type=\"button\" value=\"ATRAS\" name=\"Back2\" onclick=\"history.back()\" />");	
	out.println("</form>");
	out.println("<form action=\"EliminarD\" method=\"POST\">");
	out.println("<input type=\"submit\" value=\"Eliminar Dispositivos\" />");
	out.println("</form>");
	
	
	
	
	clientesJson.get(0).setNombre("Sebastian");
	mapper.escribirArchivo(PATH_JSON_CLIENTES, (mapper.crearClientesJson(clientesJson)));
	
	}

}
