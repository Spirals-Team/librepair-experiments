package edu.ifma.dcomp.topicos2.apipedidovendas.repository;

        import edu.ifma.dcomp.topicos2.apipedidovendas.model.Categoria;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Categoria findByNome(String nome);

}
