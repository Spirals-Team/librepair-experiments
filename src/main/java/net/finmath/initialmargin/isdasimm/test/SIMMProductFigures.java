package net.finmath.initialmargin.isdasimm.test;

import net.finmath.exception.CalculationException;
import net.finmath.initialmargin.isdasimm.changedfinmath.LIBORModelMonteCarloSimulationInterface;
import net.finmath.initialmargin.isdasimm.products.AbstractSIMMProduct;
import net.finmath.initialmargin.isdasimm.sensitivity.AbstractSIMMSensitivityCalculation.SensitivityMode;
import net.finmath.initialmargin.isdasimm.sensitivity.AbstractSIMMSensitivityCalculation.WeightMode;
import net.finmath.stochastic.RandomVariableInterface;

/**This class facilitates / expedites the initial margin calculation in the spreadsheet 
 * SIMMProductTest.xlsx
 * 
 * @author Mario Viehmann
 *
 */
public class SIMMProductFigures {

	private LIBORModelMonteCarloSimulationInterface model;
	private AbstractSIMMProduct product;
	private WeightMode weightMode;
	private SensitivityMode sensitivityMode;
	private double interpolationStep;
	private boolean isUseAnalyticSensitivities;
	private boolean isConsiderOISSensis;
	private double timeStep;
	private double finalTime;
	private RandomVariableInterface[] forwardIM = null;
	
	public SIMMProductFigures(LIBORModelMonteCarloSimulationInterface model, AbstractSIMMProduct product, WeightMode weightMode, SensitivityMode sensitivityMode,
			double interpolationStep, boolean isUseAnalyticSensitivities, boolean isConsiderOISSensis, double timeStep, double finalTime){
		this.model = model;
		this.product = product;
		this.weightMode = weightMode;
		this.sensitivityMode= sensitivityMode;
		this.interpolationStep= interpolationStep;
		this.isUseAnalyticSensitivities= isUseAnalyticSensitivities;
		this.isConsiderOISSensis = isConsiderOISSensis;
		this.timeStep= timeStep;
		this.finalTime=finalTime;
	}
	
	public double[] getExpectedIM() throws CalculationException{
		if(forwardIM==null) doCalculateIM();
		double[] expectedIM = new double[forwardIM.length];
		for(int i=0;i<expectedIM.length;i++) expectedIM[i] = forwardIM[i].getAverage();
		return expectedIM;
	}
	
	public double[] getQuantile(double quantile) throws CalculationException{
		if(forwardIM==null) doCalculateIM();
		double[] quantileIM = new double[forwardIM.length];
		for(int i=0;i<quantileIM.length;i++) quantileIM[i] = forwardIM[i].getQuantile(quantile);
		return quantileIM;
	}
	
	public double[] getPath(int pathIndex) throws CalculationException{
		if(forwardIM==null) doCalculateIM();
		double[] pathIM = new double[forwardIM.length];
		for(int i=0;i<pathIM.length;i++) pathIM[i] = forwardIM[i].get(pathIndex);
		return pathIM;
	}
	
	private void doCalculateIM() throws CalculationException{
	   this.forwardIM = new RandomVariableInterface[(int)(finalTime/timeStep)+1];
	   for(int i=0;i<=(int)(finalTime/timeStep);i++) {
		   forwardIM[i] = product.getInitialMargin(i*timeStep, model, "EUR", sensitivityMode, weightMode, interpolationStep, isUseAnalyticSensitivities, isConsiderOISSensis);
	   }
    }
	
}
