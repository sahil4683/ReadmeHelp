package com.as.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "UserPrivileges")
@AllArgsConstructor
@NoArgsConstructor
public class Privileges {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@JsonIgnore
	private Integer id;
	
	private Integer i;

//	@JsonBackReference
//	@OneToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "user_id", nullable = false)
//	private User user;

//	@ManyToOne(fetch = FetchType.EAGER, optional = false)
//	@JoinColumn(name = "user_id", nullable = false)
////	@JsonIgnore
//	private User user;

//	private String menuAccess;
//	private boolean editAccess;
//	private boolean deleteAccess;
//	
	@Column(name = "menu_name")
	private String menu_name;
	
	@Column(name = "menu_var")
	private String menu_var;
	
	@Column(name = "menu_action")
	private Boolean menu_action;
	
	@Column(name = "edit_action")
	private Boolean edit_action;
	
	@Column(name = "delete_action")
	private Boolean delete_action;

	
//	public Privileges(String menu_name, String menu_var, Boolean menu_action, Boolean edit_action,
//			Boolean delete_action,User user) {
//		super();
//		this.menu_name = menu_name;
//		this.menu_var = menu_var;
//		this.menu_action = menu_action;
//		this.edit_action = edit_action;
//		this.delete_action = delete_action;
//		this.user = user;
//	}

	
	
}
