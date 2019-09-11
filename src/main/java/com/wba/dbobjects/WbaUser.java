package com.wba.dbobjects;

public class WbaUser 
{
		private String firstName;
		private String lastName;
		private String misc;
		
		public WbaUser(String firstName, String lastName, String misc) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.misc = misc;
		}
		
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getMisc() {
			return misc;
		}
		public void setMisc(String misc) {
			this.misc = misc;
		}
}
