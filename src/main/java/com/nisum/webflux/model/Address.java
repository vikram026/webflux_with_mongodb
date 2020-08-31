package com.nisum.webflux.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
//@ToString(exclude= {"id","dob"})
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	private String addressId;
	private String zipCode;
	private List<City> city;
//	public Address() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public Address(String addressId, String zipCode, List<City> city) {
//		super();
//		this.addressId = addressId;
//		this.zipCode = zipCode;
//		this.city = city;
//	}
//
//	public String getAddressId() {
//		return addressId;
//	}
//	@Override
//	public String toString() {
//		return "Address [addressId=" + addressId + ", city=" + city + ", zipCode=" + zipCode + "]";
//	}
//	public void setAddressId(String addressId) {
//		this.addressId = addressId;
//	}
//	public List<City> getCity() {
//		return city;
//	}
//	public void setCity(List<City> city) {
//		this.city = city;
//	}
//	public String getZipCode() {
//		return zipCode;
//	}
//	public void setZipCode(String zipCode) {
//		this.zipCode = zipCode;
//	}

}
