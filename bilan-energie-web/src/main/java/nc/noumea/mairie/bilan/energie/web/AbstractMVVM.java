package nc.noumea.mairie.bilan.energie.web;

import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;
import nc.noumea.mairie.bilan.energie.web.validator.DtoValidator;

import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

/**
 * Class Abstraite de controller MVVM
 * 
 * @author Greg Dujardin
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class AbstractMVVM {
	
	@WireVariable
	private DtoValidator dtoValidator;

	/**
	 * @return Retourne le Window Manager
	 * 
	 * @throws TechnicalException Exception technique
	 */
	protected MainComposer getWindowManager() throws TechnicalException {

		Window win = (Window) Selectors.find(Path.getComponent("/"),
				"window[id=main]").get(0);

		MainComposer comp = (MainComposer) win
				.getAttribute("main$composer");

		return comp;
	}
	
	/**
	 * @return Retourne le validateur d'une DTO
	 */
	public DtoValidator getDtoValidator(){
		return dtoValidator;
	}
}
