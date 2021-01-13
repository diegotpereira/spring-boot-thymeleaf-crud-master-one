package br.com.java.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.java.domain.Contact;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Long>,
	JpaSpecificationExecutor<Contact>{}

