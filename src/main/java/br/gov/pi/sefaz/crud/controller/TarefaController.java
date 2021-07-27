package br.gov.pi.sefaz.crud.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pi.sefaz.crud.modelo.Tarefa;
import br.gov.pi.sefaz.crud.repository.TarefaRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TarefaController {
	
	@Autowired
	TarefaRepository tarefaRepository;
	
	@GetMapping("/tutorials")
	  public ResponseEntity<Map<String, Object>> getAllTutorials(
	        @RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size
	      ) {

	    try {
	      List<Tarefa> tutorials = new ArrayList<Tarefa>();
	      Pageable paging = PageRequest.of(page, size);
	      
	      Page<Tarefa> pageTuts;
	      if (title == null)
	        pageTuts = tarefaRepository.findAll(paging);
	      else
	        pageTuts = tarefaRepository.findByTitleContaining(title, paging);

	      tutorials = pageTuts.getContent();

	      Map<String, Object> response = new HashMap<>();
	      response.put("t", tutorials);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());

	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@GetMapping("/tutorials/{id}")
	  public ResponseEntity<Tarefa> getTutorialById(@PathVariable("id") long id) {
	    Optional<Tarefa> tutorialData = tarefaRepository.findById(id);

	    if (tutorialData.isPresent()) {
	      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	@PostMapping("/tutorials")
	  public ResponseEntity<Tarefa> createTutorial(@RequestBody Tarefa tarefa) {
	    try {
	      Tarefa _tarefa = tarefaRepository
	          .save(new Tarefa(tarefa.getTitle(), tarefa.getDescription()));
	      
	      return new ResponseEntity<>(_tarefa, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	 @PutMapping("/tutorials/{id}")
	  public ResponseEntity<Tarefa> updateTutorial(@PathVariable("id") long id, @RequestBody Tarefa tarefa) {
	    Optional<Tarefa> tarefaData = tarefaRepository.findById(id);

	    if (tarefaData.isPresent()) {
	      Tarefa _tarefa = tarefaData.get();
	      _tarefa.setTitle(tarefa.getTitle());
	      _tarefa.setDescription(tarefa.getDescription());
	      _tarefa.setStatus(tarefa.getStatus());
	      _tarefa.setDataConclusao(tarefa.getDataConclusao());
	      return new ResponseEntity<>(tarefaRepository.save(_tarefa), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
//	 
	 @DeleteMapping("/tutorials/{id}")
	  public ResponseEntity<HttpStatus> deleteTarefa(@PathVariable("id") long id) {
	    try {
	      tarefaRepository.deleteById(id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
















