package it.terrinoni.springbatchtest.model;

public class Person {
	private String firstName;
	private Integer age;
	private String email;

	public Person(String firstName, Integer age, String email) {
		super();
		this.firstName = firstName;
		this.age = age;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person{" + "firstName='" + firstName + '\'' + ", age=" + age + ", email='" + email + '\'' + '}';
	}
}
