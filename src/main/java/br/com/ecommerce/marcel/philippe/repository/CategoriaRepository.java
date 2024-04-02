package br.com.ecommerce.marcel.philippe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.ecommerce.marcel.philippe.modelo.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
