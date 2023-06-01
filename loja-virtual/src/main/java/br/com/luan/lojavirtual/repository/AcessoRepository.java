package br.com.luan.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.luan.lojavirtual.model.Acesso;

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long> {
	
	@Query("SELECT a FROM Acesso a WHERE UPPER(trim(a.descricao)) LIKE %?1%")
	List<Acesso> buscaAcessoDescricao(String descricao);
	
}
