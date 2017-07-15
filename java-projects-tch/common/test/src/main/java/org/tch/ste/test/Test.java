package org.tch.ste.test;

public class Test {
	public void display() {
		System.out.println("Display method");
	}

	public static void main(String args[]) throws InstantiationException,
			IllegalAccessException {
		Test.class.newInstance().display();
	}
}
