/**
 * register.html的逻辑
 */
function checkUsername(){
	if($("#username").val().length<3){
		return false;
	}
	else{
		return true;
	}
}
	
function checkPassword(){
	if($("#password").val().length < 6){
		return false;
	}
	else{
		return true;
	}
}

function checkPasswordAssure(){
	if($("#passwordAssure").val() === $("#password").val()){
		return true;
	}
	else{
		return false;
	}
}

$(document).ready(function(){
	$("#username").on("input",function(){//检查用户名，规范未定
		if(!checkUsername()){
			$("#usernameCheck").show();
		}
		else{
			$("#usernameCheck").hide();
		}
	});
	
	$("#password").on("input",function(){//检查密码，长度大于6
		if(!checkPassword()){
			$("#passwordCheck").show();
		}
		else{
			$("#passwordCheck").hide();
		}
		$("#passwordAssure").val("");
	});
		
	$("#passwordAssure").on("input",function(){//检查密码确认
		//输入检查
		if(checkPasswordAssure()){
			$("#passwordAssureCheck").hide();
		}
		else{
			$("#passwordAssureCheck").show();
		}
	});
});
	
 function onRegister(){
	if(checkPassword()&&checkPasswordAssure()&&checkUsername()){
		//输入合法 进行下一步
		$.ajax({
			
		})
	}
	else{
		//如何提醒用户？？
	}
}