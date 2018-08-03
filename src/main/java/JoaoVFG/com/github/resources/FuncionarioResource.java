package JoaoVFG.com.github.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/funcionario")
public class FuncionarioResource {

	@Autowired
	private FuncionarioResource funcionarioResource;
}
