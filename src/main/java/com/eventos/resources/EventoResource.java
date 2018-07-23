package com.eventos.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventos.model.Evento;
import com.eventos.repository.EventoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="API REST Eventos")
@RestController
@RequestMapping("/evento")
public class EventoResource {

	@Autowired
	private EventoRepository er;
	
	@ApiOperation(value="Retorna uma lista de Eventos")
	@GetMapping(produces="application/json")
	public @ResponseBody ArrayList<Evento> listaEventos() {
		Iterable<Evento> listaEventos = er.findAll();
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		for (Evento evento : listaEventos) {
			long codigo = evento.getCodigo();
			evento.add(linkTo(methodOn(EventoResource.class).evento(codigo)).withSelfRel());
			eventos.add(evento);
		}
		return eventos;
		
	}
	
	@ApiOperation(value="Retorna um Evento Especifico")
	@GetMapping(value="/{codigo}", produces="application/json")
	public @ResponseBody Evento evento(@PathVariable(value="codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		evento.add(linkTo(methodOn(EventoResource.class).listaEventos()).withRel("Lista de Eventos"));
		return evento;
		
	}

	@ApiOperation(value="Salva um Evento")
	@PostMapping()
	public Evento cadastraEvento(@RequestBody @Valid Evento evento) {
		return er.save(evento);
	}
	
	@ApiOperation(value="Deleta Evento")
	@DeleteMapping()
	public Evento deletaEvento(@RequestBody Evento evento) {
		er.delete(evento);
		return evento;
	}
	
	@ApiOperation(value="Atualiza Evento")
	@PutMapping("/{codigo}")
	public ResponseEntity<Evento> atualizaEvento (@PathVariable long codigo, @RequestBody Evento evento) {
		Evento eventoSalvo = er.findByCodigo(codigo);
		if (eventoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(evento, eventoSalvo, "codigo");
		er.save(eventoSalvo);
		return ResponseEntity.ok(eventoSalvo);
	}
	
	
}
