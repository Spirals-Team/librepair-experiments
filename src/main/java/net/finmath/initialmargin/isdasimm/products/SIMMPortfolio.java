package net.finmath.initialmargin.isdasimm.products;
import net.finmath.exception.CalculationException;
import net.finmath.initialmargin.isdasimm.aggregationscheme.CalculationSchemeInitialMarginISDA;
import net.finmath.initialmargin.isdasimm.changedfinmath.LIBORModelMonteCarloSimulationInterface;
import net.finmath.initialmargin.isdasimm.sensitivity.AbstractSIMMSensitivityCalculation;
import net.finmath.initialmargin.isdasimm.sensitivity.AbstractSIMMSensitivityCalculation.SensitivityMode;
import net.finmath.initialmargin.isdasimm.sensitivity.AbstractSIMMSensitivityCalculation.WeightMode;
import net.finmath.initialmargin.isdasimm.sensitivity.SIMMSensitivityCalculation;
import net.finmath.stochastic.RandomVariableInterface;

/** This class is a wrapper of single <code> AbstractSIMMProduct </code> s into one portfolio. 
 *  Within the portfolio all products share the same <code> LIBORModelMonteCarloSimulationInterface </code>,
 *  have the same sensitivity mapping <code> SIMMSensitivityMapping </code> (i.e. the weights used for converting 
 *  LIBOR sensitivities into Swap-rate sensitivities are the same for all products. Moreover, the WeightMode and 
 *  SensitivityMode (Exact, Melting, Interpolation) are the same for all products.
 * 
 * @author Mario Viehmann
 *
 */
public class SIMMPortfolio {

	private AbstractSIMMProduct[] products;
	private AbstractSIMMSensitivityCalculation sensitivityCalculationScheme;  // WeightMode and SensitivityMode are set in the class SIMMSensitivityMapping
	private CalculationSchemeInitialMarginISDA SIMMScheme;
	private LIBORModelMonteCarloSimulationInterface model;


	/**Construct a <code> SIMMPortfolio </code>. 
	 * Default values <code> SensitivityMode.Exact <code> and <code> WeightMode.Constant <code> and interpolation step size 0.5 are 
	 * set for the <code> SIMMSensitivityMapping <code>.
	 * 
	 * @param products The products of which the portfolio consists 
	 * @param calculationCurrency The calculation currency
	 * @throws CalculationException 
	 */
	public SIMMPortfolio(AbstractSIMMProduct[] products, String currency) throws CalculationException{
		this.products=products;
		this.SIMMScheme = new CalculationSchemeInitialMarginISDA(this, currency);

	}	

	public AbstractSIMMProduct[] getProducts(){
		return this.products;
	}


	/**Calculate the forward initial margin of the portfolio.
	 * 
	 * @param evaluationTime The forward initial margin time
	 * @param model The Libor market model
	 * @param calculationCCY The currency in which the IM is calculated
	 * @param sensitivityMode The method to be used for sensitivity calculation (Exact, LinearMelting or Interpolation)
	 * @param liborWeightMode The method to be used for converting the libor sensitivities to swap sensitivities (Constant or Stochastic)
	 * @return The forward initial margin for given time and model
	 * @throws CalculationException
	 */
	public RandomVariableInterface getInitialMargin(double evaluationTime, 
			LIBORModelMonteCarloSimulationInterface model, 
			String calculationCCY,
			SensitivityMode sensitivityMode,
			WeightMode liborWeightMode,
			double interpolationStep) throws CalculationException{
		return getInitialMargin(evaluationTime, model, calculationCCY, sensitivityMode,liborWeightMode,interpolationStep,false,true);
	}


	/**Calculate the forward initial margin of the portfolio.
	 * 
	 * @param evaluationTime The forward initial margin time
	 * @param model The Libor market model
	 * @param calculationCCY The currency in which the IM is calculated
	 * @param sensitivityMode The method to be used for sensitivity calculation (Exact, LinearMelting or Interpolation)
	 * @param liborWeightMode The method to be used for converting the libor sensitivities to swap sensitivities (Constant or Stochastic)
	 * @param isUseAnalyticSwapSensitivities true if for swaps we use analytic sensitivities
	 * @param isUseTimeGridAdjustment true if we do the time grid adjustment dL/dL
	 * @param isConsiderOISSensis true if we consider OIS sensitivities for the SIMM calculation
	 * @return The forward initial margin for given time and model
	 * @throws CalculationException
	 */
	public RandomVariableInterface getInitialMargin(double evaluationTime, 
			LIBORModelMonteCarloSimulationInterface model, 
			String calculationCCY,
			SensitivityMode sensitivityMode,
			WeightMode liborWeightMode,
			double interpolationStep,         										            								
			boolean isUseAnalyticSwapSensis,
			boolean isConsiderOISSensis) throws CalculationException{


		if(this.model==null || !model.equals(this.model) || (sensitivityCalculationScheme!=null && (sensitivityMode !=sensitivityCalculationScheme.getSensitivityMode() || liborWeightMode !=sensitivityCalculationScheme.getWeightMode()))) { // At inception (t=0) or if the model is reset            

			this.sensitivityCalculationScheme = new SIMMSensitivityCalculation(sensitivityMode, liborWeightMode, interpolationStep, model, isUseAnalyticSwapSensis, isConsiderOISSensis);
			setModel(model); // Set the (new) model. The method setModel also clears the sensitivity maps and the gradient.
			this.SIMMScheme= new CalculationSchemeInitialMarginISDA(this,calculationCCY);
		}  


		return SIMMScheme.getValue(evaluationTime);
	}


	/**Set the LIBOR market model for all products and clear some maps.
	 * 
	 * @param model The LIBOR market model
	 * @throws CalculationException 
	 */
	private void setModel(LIBORModelMonteCarloSimulationInterface model) throws CalculationException{

		this.model = model;
		for(AbstractSIMMProduct product : products){
			// Within the method setModel sensitivity maps are cleared and the gradient of each product is set to null.
			product.setGradient(model);
			product.clearDeltaCache();
			product.setNullExerciseIndicator();
			product.setSIMMSensitivityCalculation(sensitivityCalculationScheme);
		}

	}

} 