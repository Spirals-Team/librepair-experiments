package JoaoVFG.com.github.service.test;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Cargo;
import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.Endereco;
import JoaoVFG.com.github.entity.Funcionario;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.Telefone;
import JoaoVFG.com.github.entity.config.MapConfig;
import JoaoVFG.com.github.entity.security.Role;
import JoaoVFG.com.github.entity.security.User;
import JoaoVFG.com.github.repositories.CargoRepository;
import JoaoVFG.com.github.repositories.EmpresaRepository;
import JoaoVFG.com.github.repositories.EnderecoRepository;
import JoaoVFG.com.github.repositories.FuncionarioRepository;
import JoaoVFG.com.github.repositories.PessoaRepository;
import JoaoVFG.com.github.repositories.TelefoneRepository;
import JoaoVFG.com.github.repositories.TipoEmpresaRepository;
import JoaoVFG.com.github.repositories.TipoPessoaRepository;
import JoaoVFG.com.github.repositories.config.MapConfigRepository;
import JoaoVFG.com.github.repositories.security.RoleRepository;
import JoaoVFG.com.github.repositories.security.UserRepository;
import JoaoVFG.com.github.service.CepService;
import JoaoVFG.com.github.service.consultaCep.CreateCep;
import JoaoVFG.com.github.service.utils.GenerateRandom;

@Service
public class DBServiceTest {

	@Autowired
	CepService cepService;

	@Autowired
	CreateCep createCep;

	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	TipoPessoaRepository tipo;

	@Autowired
	TelefoneRepository telefoneRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	EmpresaRepository empresaRepository;

	@Autowired
	TipoEmpresaRepository tipoEmpresaRepository;

	@Autowired
	CargoRepository cargoRepository;

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	MapConfigRepository mapConfigRepository;

	public void instantiateTesteDataBase() {

		createCep.generateCep("12288560");
		createCep.generateCep("12281350");
		createCep.generateCep("12285020");
		createCep.generateCep("12295370");
		createCep.generateCep("12281460");
		createCep.generateCep("12288460");
		createCep.generateCep("12281420");

		Pessoa pessoaf1 = new Pessoa(null, tipo.findByid(1), "JV", "45567860889", "21/07/1996", "M");
		Pessoa pessoaf2 = new Pessoa(null, tipo.findByid(1), "JJ", "11593054807", "01/11/1967", "M");
		Pessoa pessoaf3 = new Pessoa(null, tipo.findByid(1), "Ingrid", "89988998899", "11/08/1993", "F");
		Pessoa pessoaf4 = new Pessoa(null, tipo.findByid(1), "carla", "58963221474", "13/04/1970", "F");
		Pessoa pessoaj1_0 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "trans A", "4478969850008");
		Pessoa pessoaj1_1 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "trans A unidade 2", "4478969850009");
		Pessoa pessoaj1_2 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "trans A unidade 3", "4478969850010");
		Pessoa pessoaj2_0 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "trans B", "4485478220008");
		Pessoa pessoaj2_1 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "trans B unidade 2", "4485478220009");
		Pessoa pessoaj3_1 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "ENviadora A", "485874810008");
		Pessoa pessoaj3_2 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "ENviadora A unidade 2", "485874810009");
		Pessoa pessoaj3_3 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "ENviadora A unidade 3", "485874810018");
		Pessoa pessoaj3_4 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "ENviadora A unidade 4", "485874810088");
		Pessoa pessoaj4_0 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "mandou X", "898725950008");
		Pessoa pessoaj4_1 = new Pessoa(null, tipo.findBydescricao("JURIDICA"), "mandou X 2 ", "4478969850008");
		pessoaRepository.saveAll(
				Arrays.asList(pessoaf1, pessoaf2, pessoaf3, pessoaf4, pessoaj1_0, pessoaj1_2, pessoaj1_1, pessoaj2_0,
						pessoaj2_1, pessoaj3_4, pessoaj3_1, pessoaj3_2, pessoaj3_3, pessoaj4_0, pessoaj4_1));

		Telefone tel1 = new Telefone(null, "Celular", "12991157861", pessoaf1);
		Telefone tel2 = new Telefone(null, "Celular", "12996260032", pessoaf1);
		Telefone tel3 = new Telefone(null, "Celular", "12991450102", pessoaf2);
		Telefone tel4 = new Telefone(null, "Celular", "12981345477", pessoaf3);
		Telefone tel5 = new Telefone(null, "Celular", "12991292612", pessoaf4);
		Telefone tel6 = new Telefone(null, "Fixo", "1236525626", pessoaf4);
		Telefone tel7 = new Telefone(null, "Fixo", "1236524048", pessoaf4);
		Telefone tel8 = new Telefone(null, "Fixo", "1185988587", pessoaj2_1);
		Telefone tel9 = new Telefone(null, "Fixo", "1188996633", pessoaj2_0);
		Telefone tel10 = new Telefone(null, "Celular", "11955889966", pessoaj3_1);
		Telefone tel11 = new Telefone(null, "Fixo", "1256566336", pessoaj4_1);
		Telefone tel12 = new Telefone(null, "Celular", "12988774455", pessoaj4_0);

		telefoneRepository
				.saveAll(Arrays.asList(tel1, tel2, tel3, tel4, tel5, tel6, tel7, tel8, tel9, tel10, tel11, tel12));

		Endereco end1 = new Endereco(null, pessoaf1, cepService.findByCep("12288560"), 718, "");
		Endereco end2 = new Endereco(null, pessoaf2, cepService.findByCep("12288560"), 718, "");
		Endereco end3 = new Endereco(null, pessoaf4, cepService.findByCep("12288560"), 718, "");
		Endereco end4 = new Endereco(null, pessoaf3, cepService.findByCep("12290379"), 50, "");
		Endereco end5 = new Endereco(null, pessoaj1_0, cepService.findByCep("12289368"), 30, "");
		Endereco end6 = new Endereco(null, pessoaj4_0, cepService.findByCep("12289085"), 127, "");
		Endereco end7 = new Endereco(null, pessoaj2_0, cepService.findByCep("12287360"), 88, "");
		Endereco end8 = new Endereco(null, pessoaj3_1, cepService.findByCep("12216300"), 666, "");
		Endereco end9 = new Endereco(null, pessoaj1_1, cepService.findByCep("04021001"), 1, "");
		Endereco end10 = new Endereco(null, pessoaj2_1, cepService.findByCep("12209060"), 15, "");
		Endereco end11 = new Endereco(null, pessoaj3_2, cepService.findByCep("12010070"), 1654, "");
		Endereco end12 = new Endereco(null, pessoaj3_3, cepService.findByCep("12500140"), 898, "");
		Endereco end13 = new Endereco(null, pessoaj3_4, cepService.findByCep("12301040"), 15, "");
		Endereco end14 = new Endereco(null, pessoaj4_1, cepService.findByCep("11660070"), 507, "");
		Endereco end15 = new Endereco(null, pessoaj1_2, cepService.findByCep("12209310"), 502, "");

		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3, end4, end5, end6, end7, end8, end9, end10, end11,
				end12, end13, end14, end15));

		Empresa empresa1 = new Empresa(null, pessoaj1_0, tipoEmpresaRepository.buscaPorId(1), 1, null);
		Empresa empresa2 = new Empresa(null, pessoaj2_0, tipoEmpresaRepository.buscaPorId(1), 1, null);
		Empresa empresa3 = new Empresa(null, pessoaj3_1, tipoEmpresaRepository.buscaPorId(1), 1, null);
		Empresa empresa4 = new Empresa(null, pessoaj4_0, tipoEmpresaRepository.buscaPorId(1), 1, null);
		Empresa empresa5 = new Empresa(null, pessoaj1_1, tipoEmpresaRepository.buscaPorId(2), 1, pessoaj1_0.getId());
		Empresa empresa6 = new Empresa(null, pessoaj1_2, tipoEmpresaRepository.buscaPorId(2), 1, pessoaj1_0.getId());
		Empresa empresa7 = new Empresa(null, pessoaj2_1, tipoEmpresaRepository.buscaPorId(2), 1, pessoaj2_0.getId());
		Empresa empresa8 = new Empresa(null, pessoaj3_2, tipoEmpresaRepository.buscaPorId(2), 1, pessoaj3_1.getId());
		Empresa empresa9 = new Empresa(null, pessoaj3_3, tipoEmpresaRepository.buscaPorId(2), 1, pessoaj3_1.getId());
		Empresa empresa10 = new Empresa(null, pessoaj3_4, tipoEmpresaRepository.buscaPorId(2), 1, pessoaj3_1.getId());
		Empresa empresa11 = new Empresa(null, pessoaj4_1, tipoEmpresaRepository.buscaPorId(2), 1, pessoaj4_0.getId());

		empresaRepository.saveAll(Arrays.asList(empresa1, empresa2, empresa3, empresa4, empresa5, empresa6, empresa7,
				empresa8, empresa9, empresa10, empresa11));

		Cargo cargo1 = new Cargo(null, "CARGO 1");
		Cargo cargo2 = new Cargo(null, "CARGO 2");
		Cargo cargo3 = new Cargo(null, "CARGO 3");
		Cargo cargo4 = new Cargo(null, "CARGO 4");
		Cargo cargo5 = new Cargo(null, "CARGO 5");
		Cargo cargo6 = new Cargo(null, "CARGO 6");
		Cargo cargo7 = new Cargo(null, "CARGO A");
		Cargo cargo8 = new Cargo(null, "CARGO ABC");
		Cargo cargo9 = new Cargo(null, "CARGO A5");

		cargoRepository.saveAll(Arrays.asList(cargo1, cargo2, cargo3, cargo4, cargo5, cargo6, cargo7, cargo8, cargo9));

		Pessoa pessoafun01 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 01", "00000000001", "01/01/90", "M");
		Pessoa pessoafun02 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 02", "00000000002", "01/01/90", "F");
		Pessoa pessoafun03 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 03", "00000000003", "01/01/90", "M");
		Pessoa pessoafun04 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 04", "00000000004", "01/01/90", "F");
		Pessoa pessoafun05 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 05", "00000000005", "01/01/90", "M");
		Pessoa pessoafun06 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 06", "00000000006", "01/01/90", "F");
		Pessoa pessoafun07 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 07", "00000000007", "01/01/90", "M");
		Pessoa pessoafun08 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 08", "00000000008", "01/01/90", "F");
		Pessoa pessoafun09 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 09", "00000000009", "01/01/90", "M");
		Pessoa pessoafun10 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 10", "00000000010", "01/01/90", "F");
		Pessoa pessoafun11 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 11", "00000000011", "01/01/90", "M");
		Pessoa pessoafun12 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 12", "00000000012", "01/01/90", "F");
		Pessoa pessoafun13 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 13", "00000000013", "01/01/90", "M");
		Pessoa pessoafun14 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 14", "00000000014", "01/01/90", "F");
		Pessoa pessoafun15 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 15", "00000000015", "01/01/90", "M");
		Pessoa pessoafun16 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 16", "00000000016", "01/01/90", "F");
		Pessoa pessoafun17 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 17", "00000000017", "01/01/90", "M");
		Pessoa pessoafun18 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 18", "00000000018", "01/01/90", "F");
		Pessoa pessoafun19 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 19", "00000000019", "01/01/90", "M");
		Pessoa pessoafun20 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 20", "00000000020", "01/01/90", "F");
		Pessoa pessoafun21 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 21", "00000000021", "01/01/90", "M");
		Pessoa pessoafun22 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 22", "00000000022", "01/01/90", "F");
		Pessoa pessoafun23 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 23", "00000000023", "01/01/90", "M");
		Pessoa pessoafun24 = new Pessoa(null, tipo.findByid(1), "FUNCIONARIO 24", "00000000024", "01/01/90", "F");

		pessoaRepository.saveAll(Arrays.asList(pessoafun01, pessoafun02, pessoafun03, pessoafun04, pessoafun05,
				pessoafun06, pessoafun07, pessoafun08, pessoafun09, pessoafun10, pessoafun11, pessoafun12, pessoafun13,
				pessoafun14, pessoafun15, pessoafun16, pessoafun17, pessoafun18, pessoafun19, pessoafun20, pessoafun21,
				pessoafun22, pessoafun23, pessoafun24));

		Funcionario funcionario01 = new Funcionario(null, pessoafun01, empresa1, cargo1);
		Funcionario funcionario02 = new Funcionario(null, pessoafun02, empresa2, cargo9);
		Funcionario funcionario03 = new Funcionario(null, pessoafun03, empresa2, cargo2);
		Funcionario funcionario04 = new Funcionario(null, pessoafun04, empresa2, cargo8);
		Funcionario funcionario05 = new Funcionario(null, pessoafun05, empresa3, cargo3);
		Funcionario funcionario06 = new Funcionario(null, pessoafun06, empresa4, cargo7);
		Funcionario funcionario07 = new Funcionario(null, pessoafun07, empresa4, cargo4);
		Funcionario funcionario08 = new Funcionario(null, pessoafun08, empresa5, cargo6);
		Funcionario funcionario09 = new Funcionario(null, pessoafun09, empresa5, cargo5);
		Funcionario funcionario10 = new Funcionario(null, pessoafun10, empresa6, cargo3);
		Funcionario funcionario11 = new Funcionario(null, pessoafun11, empresa6, cargo5);
		Funcionario funcionario12 = new Funcionario(null, pessoafun12, empresa7, cargo7);
		Funcionario funcionario13 = new Funcionario(null, pessoafun13, empresa7, cargo8);
		Funcionario funcionario14 = new Funcionario(null, pessoafun14, empresa8, cargo1);
		Funcionario funcionario15 = new Funcionario(null, pessoafun15, empresa8, cargo9);
		Funcionario funcionario16 = new Funcionario(null, pessoafun16, empresa9, cargo2);
		Funcionario funcionario17 = new Funcionario(null, pessoafun17, empresa9, cargo4);
		Funcionario funcionario18 = new Funcionario(null, pessoafun18, empresa10, cargo3);
		Funcionario funcionario19 = new Funcionario(null, pessoafun19, empresa10, cargo6);
		Funcionario funcionario20 = new Funcionario(null, pessoafun20, empresa11, cargo7);
		Funcionario funcionario21 = new Funcionario(null, pessoafun21, empresa11, cargo1);
		Funcionario funcionario22 = new Funcionario(null, pessoafun22, empresa1, cargo6);
		Funcionario funcionario23 = new Funcionario(null, pessoafun23, empresa3, cargo8);
		Funcionario funcionario24 = new Funcionario(null, pessoafun24, empresa3, cargo9);

		funcionarioRepository.saveAll(Arrays.asList(funcionario01, funcionario02, funcionario03, funcionario04,
				funcionario05, funcionario06, funcionario07, funcionario08, funcionario09, funcionario10, funcionario11,
				funcionario12, funcionario13, funcionario14, funcionario15, funcionario16, funcionario17, funcionario18,
				funcionario19, funcionario20, funcionario21, funcionario22, funcionario23, funcionario24));
		
		
		Role role = roleRepository.buscaPorId(1);
		HashSet<Role> roles = new HashSet<>(Arrays.asList(role));
		User user = new User(null, "adm@adm.com.br", passwordEncoder.encode("adm"), pessoaf1, roles);
		userRepository.save(user);
		
		GenerateRandom gr = new GenerateRandom();
		
		System.out.println(gr.newRandom(10));
		System.out.println(gr.newRandom(100));
		
		MapConfig mapConfig = mapConfigRepository.findBynameKey("JWTSECRET");
		mapConfig.setValue(passwordEncoder.encode(gr.newRandom(20)));
		
		mapConfigRepository.save(mapConfig);
	}

}
