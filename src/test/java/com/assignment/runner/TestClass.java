package com.assignment.runner;

import java.io.File;

public class TestClass {

	public static void main(String[] args) {
		File destinationFile=new File("src/../images"+System.currentTimeMillis()+".png");
		System.out.println(destinationFile.getAbsolutePath());
		
	}

}
