package org.raml.simple.language.dto;

import java.io.Serializable;

public class AuthDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static class Basic implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		transient String user;
		transient String password;
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
	}
	
	protected Basic basic;

	public Basic getBasic() {
		return basic;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}
}
