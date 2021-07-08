package com.pecaja.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pecaja.app.enums.Status;
import com.pecaja.app.models.Produto;
import com.pecaja.app.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	public ProdutoService(ProdutoRepository produtoRepository) {
		this.repository = produtoRepository;
	}
	
	@Autowired
	private ProdutoRepository repository;

	public List<Produto> findAll() {
		return repository.findAll();
	}
	
	public List<Produto> findAllByRevendedor(Long revendedor_id, Status status){
		return repository.findAllByRevendedor(revendedor_id, status);
	}
	
	public Produto findById(Long id) {
		return repository.getOne(id);
	}

	public Produto save(Produto produto) {
		return repository.saveAndFlush(produto);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Produto findByCategoriaByMarcaByPesoByRevendedor(Long categoria_id, Long marca_id, double peso, Long revendedor_id) {
		return repository.findByCategoriaByMarcaByPesoByRevendedor(categoria_id, marca_id, peso,revendedor_id);
	}
	
	public int sumQuantidadeProdutosbyRevendedorwhereStatus(Long id, Status status) {
		return repository.sumProdutosbyRevendedorwhereStatus(id, status);
	}
}
