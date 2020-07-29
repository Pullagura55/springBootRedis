package com.bcbsma.rediscache.model;

import java.util.List;

public class User {

	private String id;
	private String name;
	private Long salary;
	private List<UserAddress> address;

	public User(String id, String name, Long salary, List<UserAddress> address) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.address = address;
	}

	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public List<UserAddress> getAddress() {
		return address;
	}

	public void setAddress(List<UserAddress> address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", salary=" + salary + ", address=" + address + "]";
	}

}
