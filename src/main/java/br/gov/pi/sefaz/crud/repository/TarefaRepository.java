package br.gov.pi.sefaz.crud.repository;

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pi.sefaz.crud.modelo.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	Page<Tarefa> findByTitleContaining(String title, Pageable pageable);
}
