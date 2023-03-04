package com.App.LojaVirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.App.LojaVirtual.model.Acesso;


@Repository
@Transactional
public interface AcessoRepository extends CrudRepository<Acesso, Long>{
	
	@Query("select a from Acesso a where upper(trim(a.descricao)) like %?1%")
	List<Acesso> buscaAcessoDesc(String descricao);
}
