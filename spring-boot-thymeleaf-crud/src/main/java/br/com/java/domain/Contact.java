package br.com.java.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Validated
@Entity
@Table(name="tb_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4048798961366546485L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	private String name;
	
	@Pattern(regexp ="^\\+?[0-9. () - ] {7,25} $", message = "Phone Number")
	@Size
	private String phone;
	
	@Email(message = "Email Address")
	@Size(max =100)
	private String email;
	
	@Size(max = 50)
	private String address1;
	
	@Size
	private String address2;
	
	@Size
	private String address3;
	
	@Size
	private String postalcode;
	
	@Column
	private String note;
}
