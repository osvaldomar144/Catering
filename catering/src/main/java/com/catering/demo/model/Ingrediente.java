package com.catering.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.catering.demo.utility.FileStore;

@Entity
public class Ingrediente {
	
	public static final String DIR_PAGES_INGREDIENTE = "informations/ingrediente/";
	public static final String DIR_ADMIN_PAGES_INGREDIENTE = "admin/ingrediente/";
	
	public static final String DIR_FOLDER_IMG = "ingrediente/profili";
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String origine;
	
	private String descrizione;

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

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public void eliminaImmagine() {
		FileStore.removeImg(DIR_FOLDER_IMG, this.getImg());
	}
}
