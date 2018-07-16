package br.com.rodolfo.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.rodolfo.pontointeligente.api.entities.Funcionario;

//Por se tratar de métodos apenas de consulta (faz um select no banco) eles não precisam de transação,
//não precisa bloquear o banco de dados
@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findByCpf(String cpf);

    Funcionario findByEmail(String email);

    Funcionario findByCpfOrEmail(String cpf, String email);
}