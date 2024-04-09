package br.com.ecommerce.marcel.philippe.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;
import br.com.ecommerce.marcel.philippe.modelo.Produto;
import br.com.ecommerce.marcel.philippe.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<ProdutoDTO> getAll() {
		List<Produto> produtos = produtoRepository.findAll();
		return produtos.stream().map(ProdutoDTO::convert).collect(Collectors.toList());
	}

	public List<ProdutoDTO> getProdutoByCategoriaId(Long categoriaId) {
		List<Produto> produtos = produtoRepository.getProdutoByCategoria(categoriaId);
		return produtos.stream().map(ProdutoDTO::convert).collect(Collectors.toList());
	}

	public ProdutoDTO findByProdutoIdentifier(String productIdentifier) {
		Produto produto = produtoRepository.findByProdutoIdentifier(productIdentifier);
		if (produto != null) {
			return ProdutoDTO.convert(produto);
		}
		return null;
	}

	public ProdutoDTO save(ProdutoDTO productDTO) {
		Produto produto = produtoRepository.save(Produto.convert(productDTO));
		return ProdutoDTO.convert(produto);
	}

	public ProdutoDTO delete(long produtoId) throws ProdutoNotFoundException {
		Optional<Produto> produto = produtoRepository.findById(produtoId);
		if (produto.isPresent()) {
			produtoRepository.delete(produto.get());
			return ProdutoDTO.convert(produto.get());
		}
		throw new ProdutoNotFoundException();
	}
}
