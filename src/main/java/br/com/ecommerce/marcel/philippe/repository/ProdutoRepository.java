package br.com.ecommerce.marcel.philippe.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.ecommerce.marcel.philippe.modelo.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query(value = "SELECT p "
			+ "FROM produto p "
			+ "JOIN categoria c on p.categoria.id = c.id "
			+ "WHERE c.id = :categoriaId ")
			public List<Produto> getProdutoByCategoria(
			@Param("categoriaId") long categoriaId);

	public Produto findByProdutoIdentifier(
			String produtoIdentifier);
}
