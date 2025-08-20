/**
 * Copyright (c) 2025, Xianyi Ye
 *
 * This project includes software developed by Xianyi Ye
 * yexianyi@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.yxy.chukonu.java.dp.builder;
/**
 * This class is for demonstrating how does Builder Pattern work
 * @author xianyiye
 *
 */
public class Student {

	private String name;
	private int age;
	private String address;
	private String ID;
	
	
	private Student(Builder build) {
		this.name = build.name;
		this.age = build.age;
		this.address = build.address;
		this.ID = build.ID;
	}

	public String getName() {
		return this.name;
	}

	public int getAge() {
		return this.age;
	}

	public String getAddress() {
		return this.address;
	}

	public String getID() {
		return this.ID;
	}

	public void method() {
		System.out.println(name);
	}

	/**
	 * Builder class
	 *
	 */
	static class Builder {

		private String name = null;
		private int age = 0;
		private String address = null;
		private String ID = null;

		public Builder() {
		};

		public Builder(String name){
			this.name=name;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setAge(int age) {
			this.age = age;
			return this;
		}

		public Builder setAddress(String address) {
			this.address = address;
			return this;
		}

		public Builder setID(String ID) {
			this.ID = ID;
			return this;
		}

		public Student build(){
			return new Student(this);
		}

	}

	public static void main(String[] args) {
		
		Student student = new Student.Builder().setName("zhangsan").setAge(12).setAddress("beijing").build();

	}

}
