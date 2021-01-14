package br.com.java.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.java.domain.Contact;
import br.com.java.service.ContactService;

@Controller
public class ContactController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final int ROW_PER_PAGE = 5;

	@Autowired
	private ContactService service;

//	@Value("${msg.title}")
	private String title;

	@GetMapping(value = { "/", "/index" })
	public String index(Model model) {

		model.addAttribute("title", title);
		return "index";
	}

	@GetMapping(value = "/contacts")
	public String getContacts(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
		List<Contact> contacts = service.findAll(pageNumber, ROW_PER_PAGE);

		long count = service.count();
		boolean hasPrev = pageNumber > 1;
		boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
		model.addAttribute("contacts", contacts);
		model.addAttribute("hasPrev", hasPrev);
		model.addAttribute("prev", pageNumber - 1);
		model.addAttribute("hasNext", hasNext);
		model.addAttribute("next", pageNumber + 1);
		return "contact-list";
	}
	@GetMapping(value= {"/contacts/add"})
	public String ShowAddContact(Model model) {
		Contact contact = new Contact();
		model.addAttribute("add", true);
		model.addAttribute("contact", contact);
		
		return "contact-edit";
	}
	@PostMapping(value="/contacts/add")
	public String addContact(Model model, 
			@ModelAttribute("contact") Contact contact) {
		try {
			Contact newContact = service.save(contact);
			return "redirect:/contacts/" + String.valueOf(newContact.getId());
		} catch (Exception e) {
			// TODO: handle exception
			String errorMessage = e.getMessage();
			logger.error(errorMessage);
			model.addAttribute("errorMessage", errorMessage);
			
			model.addAttribute("add", true);
			return "Contact-edit";
		}
	}

}
