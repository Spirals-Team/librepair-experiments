package com.agiles.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.agiles.controladores.Ahorcado;


public class AgregarLetra extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public AgregarLetra() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Ahorcado juego;
		String imgHangman="images/playhangman1.png";
		if(session.getAttribute("juego") != null) {
			juego = (Ahorcado)session.getAttribute("juego");
		} else {
			juego = new Ahorcado();
			
		}
		
		String letra = (String)request.getParameter("letraInput");
		if(!(letra.isEmpty()||letra==null)){
			if(letra.equals("enie")){
				letra="ñ";
			}
			juego.ingresarLetra(letra.charAt(0));
			
		}
		switch(juego.getErroresRestantes()){
		case 4:imgHangman="images/playhangman1.png";break;
		case 3:imgHangman="images/playhangman2.png";break;
		case 2:imgHangman="images/playhangman3.png";break;
		case 1:imgHangman="images/playhangman4.png";break;
		case 0:imgHangman="images/playhangman5.png";break;
		case -1:imgHangman="images/playhangman6.png";break;
	
		}
		
		request.setAttribute("letrasPalabra", juego.mostrarPalabra());
		request.setAttribute("urlHangman", imgHangman);
		if(juego.isGanador()){
			request.setAttribute("ganador", true);
			juego=null;
			
		}
		else if(juego.getErroresRestantes()<0){
			request.setAttribute("perdedor", true);
			request.setAttribute("palabraera", juego.getPalabraActual());
			juego=null;
			
		}else{
			request.setAttribute("letrasUsadas", juego.getLetrasUsadas());
			request.setAttribute("errores", juego.getErroresRestantes());
		}
		
		session.setAttribute("juego", juego);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
	}
	

}
