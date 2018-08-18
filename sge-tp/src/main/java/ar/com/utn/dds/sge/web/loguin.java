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

/**
 * Servlet implementation class loguin
 */
@WebServlet("/loguin")
public class loguin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PATH_JSON_CLIENTES = "C:/Users/s.agosta/Desktop/SGE-DDS/sge-tp/src/main/resources/Clientes.json";
	public static String usu, pass;
	JsonMapper mapper = new JsonMapper();
	List<Cliente> clientesJson;
	

    /**
     * @see HttpServlet#HttpServlet()
     */
    public loguin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		
		HttpSession sesion = request.getSession();
        
        usu = request.getParameter("user");
        pass = request.getParameter("password");
        boolean encontrado = false;
        boolean correcto = false;
        int i, j = 0;
    	
        clientesJson = mapper.extraerClientesJson(mapper.leerArchivo(PATH_JSON_CLIENTES));
        
        for (i = 0; i < clientesJson.size(); i++) {
			if(usu.equals(clientesJson.get(i).getUserName())){j=i; encontrado = true;};
			
					}
        if (encontrado && pass.equals(clientesJson.get(j).getPassword())) {correcto =true;};
        
        if(correcto && sesion.getAttribute("usuario") == null){
            //si coincide usuario y password y ademas no hay sesion iniciada mas adelante voy a controlar que si ya tiene sesion que de otro mensaje de error
            sesion.setAttribute("usuario", usu);
            //redirijo a pagina con informacion de login exitoso
            response.sendRedirect("loginExito.jsp");
        }else{
        	sesion.setAttribute("usuario", null);
        	response.sendRedirect("loginInvalido.jsp");
        	
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
