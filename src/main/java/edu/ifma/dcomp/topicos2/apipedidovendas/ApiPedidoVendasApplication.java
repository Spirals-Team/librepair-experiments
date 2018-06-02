package edu.ifma.dcomp.topicos2.apipedidovendas;

import edu.ifma.dcomp.topicos2.apipedidovendas.model.Categoria;
import edu.ifma.dcomp.topicos2.apipedidovendas.model.Produto;
import edu.ifma.dcomp.topicos2.apipedidovendas.service.CategoriaService;
import edu.ifma.dcomp.topicos2.apipedidovendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class ApiPedidoVendasApplication {

   public static void main(String[] args) {

       SpringApplication.run(ApiPedidoVendasApplication.class, args);
    }

}
