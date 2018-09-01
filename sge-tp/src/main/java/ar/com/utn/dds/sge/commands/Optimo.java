package ar.com.utn.dds.sge.commands;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import ar.com.utn.dds.sge.models.Cliente;



public class Optimo {
	public   Optimo(Cliente cliente, int tam)
	{
		double[] vectorMin = new double[tam];
		double[] vectorMax = new double[tam];
		double[] vectorConsumo = new double[tam];
		double[] vectorVariables = new double[tam];
		double[] vectorRestricciones = new double[tam];
		int j=0;
		for (int i = 0; i<tam; i++){
			   
			if (cliente.getDispositivos().get(i).getOptimizable()) {
				vectorMin[j]=cliente.getDispositivos().get(i).getMensualMin();
			   vectorMax[j]=cliente.getDispositivos().get(i).getMensualMin();
			   vectorConsumo[j]=cliente.getDispositivos().get(i).getConsumo();
			   vectorVariables[j]= 1;
			   vectorRestricciones[j]= 0; j++;}
			   
			   
			  }
		
		Simplex_Eficiente simplexFacade = new Simplex_Eficiente(GoalType.MAXIMIZE, true);
		simplexFacade.crearFuncionEconomica(vectorVariables);
		simplexFacade.agregarRestriccion(Relationship.LEQ, 612.0, vectorConsumo);
		
		for (int i = 0; i<j; i++){
			vectorRestricciones[i]= 1;
			simplexFacade.agregarRestriccion(Relationship.GEQ, vectorMin[i], vectorRestricciones);
			simplexFacade.agregarRestriccion(Relationship.LEQ, vectorMax[i], vectorRestricciones);
			vectorRestricciones[i]= 0;
			  }
		try{
			PointValuePair solucion = simplexFacade.resolver();
			cliente.setConsumoOptimo(solucion.getValue());
			int d =0;
			for (int i = 0; i<j; i++){
				if(cliente.getDispositivos().get(i).getOptimizable()) {  
				cliente.getDispositivos().get(i).setHsRestantes(solucion.getPoint()[d]);;
				d++;
				} 
				  }
			
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			cliente.setConsumoOptimo(0.0);
			for (int i = 0; i<tam; i++){
				   cliente.getDispositivos().get(i).setHsRestantes(0);;
				   
				  }
		}
		
		
	}

}
