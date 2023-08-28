package com.riko.SigmaTechTest.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "blog")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BlogModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String title;
	private String body;
	private String author;
	private Date createDate;
	private Date updateDate;
	private Boolean isDelete;

}
