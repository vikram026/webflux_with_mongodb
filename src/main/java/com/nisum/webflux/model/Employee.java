package com.nisum.webflux.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
//@ToString(exclude= {"id","dob"})
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Employee {
	@Id
	private String id;
	private String name;
	private List<Address> address;	
	private  List<Hobby> hobbies;
//	public Employee(String id, String name, List<Address> address, List<Hobby> hobbies) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.address = address;
//		this.hobbies = hobbies;
//	}
//	public String getId() {
//		return id;
//	}
//	public Employee() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	@Override
//	public String toString() {
//		return "Employee [id=" + id + ", name=" + name + ", address=" + address + ", hobbies=" + hobbies + "]";
//	}
//	public List<Address> getAddress() {
//		return address;
//	}
//	public void setAddress(List<Address> address) {
//		this.address = address;
//	}
//	public List<Hobby> getHobbies() {
//		return hobbies;
//	}
//	public void setHobbies(List<Hobby> hobbies) {
//		this.hobbies = hobbies;
//	}

}
