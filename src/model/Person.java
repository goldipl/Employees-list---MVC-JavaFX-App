package model;

public class Person {
	
	private String name;
	private String surname;
	private String room;
	private String startHour;
	private String endHour;
	private int workTime;
	
	public Person(String name, String surname, String room, String startHour, String endHour) {
		
		this.name = name;
		this.surname = surname;
		this.room = room;
		this.startHour = startHour;
		this.endHour = endHour;
		workTime = Integer.parseInt(endHour)-Integer.parseInt(startHour);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public int getWorkTime() {
		return workTime;
	}
	

	
}
