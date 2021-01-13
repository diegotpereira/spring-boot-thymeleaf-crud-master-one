package br.com.java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.java.domain.Contact;
import br.com.java.exception.BadResourceException;
import br.com.java.exception.ResourceAlreadyExistsException;
import br.com.java.exception.ResourceNotFoundException;
import br.com.java.repository.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	private ContactRepository rep;
	
	private boolean existsById(Long id) {
		return rep.existsById(id);
	}
	// Buscar por ID
	public Contact findById(long id) throws ResourceNotFoundException{
		Contact contact = rep.findById(id).orElse(null);
		
		if (contact == null) {
			throw new ResourceNotFoundException ("Não é possível encontrar contato com id?: " + id);
		}
		else return contact;
	}
	// listar todos os contatos
	public List <Contact> findAll (int pageNumber, int rowPerPage){
		
		List <Contact> contacts = new ArrayList<>();
		
		Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, 
                Sort.by("id").ascending());
		
		rep.findAll(sortedByIdAsc).forEach(contacts::add);
		
		return contacts;
	}
	public Contact save (Contact contact) throws BadResourceException, ResourceAlreadyExistsException{
		
		if (!StringUtils.isEmpty(contact.getName())) {
			if (contact.getId() != null && existsById(contact.getId())) {
				throw new ResourceAlreadyExistsException("Contato com id: " + contact.getId() + "já existe");
			}
			
			return rep.save(contact);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar contato");
			exc.addErrorMessage("O contato é nulo ou vazio");
			throw exc;
		}
		
	}
	public void update(Contact contact) throws BadResourceException, ResourceNotFoundException {
		if (!StringUtils.isEmpty(contact.getName())) {
			if (!existsById(contact.getId())) {
				throw new ResourceNotFoundException ("Não é possível encontrar contato com id:" + contact.getId());
			}
			rep.save(contact);
		}else {
			BadResourceException exc = new BadResourceException("Erro ao salvar contato");
			exc.addErrorMessage("O contato não está vazio");
			throw exc;
		}
	}
	public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) { 
            throw new ResourceNotFoundException("Cannot find contact with id: " + id);
        }
        else {
            rep.deleteById(id);
        }
    }
	public Long count() {
        return rep.count();
    }

}
