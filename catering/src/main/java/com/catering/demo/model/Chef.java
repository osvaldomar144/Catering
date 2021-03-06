package com.catering.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Chef {
	
	public static final String DIR_PAGES_CHEF = "informations/chef/";
	public static final String DIR_ADMIN_PAGES_CHEF = "admin/chef/";
	
	public static final String DIR_FOLDER_IMG = "chef/profili";
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String cognome;
	
	@NotBlank
	private String nazionalita;

	@OneToMany (mappedBy="chef", cascade=CascadeType.ALL)
	private List<Buffet> buffets;
	
	private String img;
	

	/* methods */
	public Long getId() {
		return id;
	}

	public void setId(Long id) { this.id = id; }
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<Buffet> getBuffets() {
		return buffets;
	}

	public void setBuffets(List<Buffet> buffets) {
		this.buffets = buffets;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
