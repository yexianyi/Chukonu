<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>chukonu_spring_mvc_mybatis Transaction</title>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(
		function(){
			$("#txCommit").click(function(){
				var params = $("#txForm").serialize();
				$.post({
					url:"./submitTx.do" ,
					data:params ,
					success: function(result){
						alert(result) ;
					}
				});
			});
		}		
	);

</script>
</head>
<body>
	<center>
		<p>Transaction<p>
		<form id="txForm">
			<table border="1">
				<tr>
					<td>Receiver Account:</td><td><input name="toAcct"></input></td>
				</tr>
				<tr>
					<td>Remitter Account:</td><td><input name="fromAcct"></input></td>
				</tr>
				<tr>
					<td>Amount:</td><td><input name="txAmount"></input></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<input id="txCommit" type="button" value="Submit"></input>
						&nbsp;
						<input type="button" value="Cancel"></input>
					</td>
				</tr>
			</table>
		</form>
	</center>	
</body>
</html>