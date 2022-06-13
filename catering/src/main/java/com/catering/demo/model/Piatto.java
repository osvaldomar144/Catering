package com.catering.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Piatto {
	
	public static final String DIR_PAGES_PIATTO = "informations/piatto/";
	public static final String DIR_ADMIN_PAGES_PIATTO = "admin/piatto/";
	
	public static final String DIR_FOLDER_IMG = "piatto/profili";
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	private String descrizione;
	
	@ManyToOne
	private Buffet buffet;
	
	@OneToMany (cascade = CascadeType.ALL)
	private List<Ingrediente> ingredienti;

	private String img;
	
	/* methods */
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Buffet getBuffet() {
		return buffet;
	}
	
	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}
	
	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
}
