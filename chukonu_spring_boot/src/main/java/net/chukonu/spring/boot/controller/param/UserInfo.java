package net.chukonu.spring.boot.controller.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfo {

	private String fname ;
	private String lname ;
	private List<String> contactMethods ;
	private String car ;
	
}
