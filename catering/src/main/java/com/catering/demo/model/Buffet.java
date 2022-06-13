package com.catering.demo.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Buffet {
	
	public static final String DIR_PAGES_BUFFET = "informations/buffet/";
	public static final String DIR_ADMIN_PAGES_BUFFET = "admin/buffet/";
	
	public static final String DIR_FOLDER_IMG = "buffet/profili";
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@Column
	private String descrizione;
	
	@ManyToOne
	private Chef chef;
	
	@OneToMany(mappedBy="buffet", cascade = CascadeType.ALL)
	private List<Piatto> piatti;
	
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

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
