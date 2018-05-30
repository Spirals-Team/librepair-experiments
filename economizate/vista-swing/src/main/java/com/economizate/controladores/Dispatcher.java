package com.economizate.controladores;

import com.economizate.vistas.Home;

public class Dispatcher {

	//private StudentView studentView;
    private Home homeView;
   
    public Dispatcher(){
       
       homeView = new Home();
    }

    public void dispatch(String request){
       if(request.equalsIgnoreCase("STUDENT")){
          //studentView.show();
       }
       else{
          homeView.iniciarVista();
       }	
   }
}
