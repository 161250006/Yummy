$(function(){
    $.post("/Index/init","text");


    $("#register").ajaxForm(function(data){
        $('.modal').modal('hide');
        var result=data;
    	if(result['result']=='uncertain'){
    		alert('请重新确认密码');
		}
		else if(result['result']=='codeFalse'){
            alert('验证码错误');
        }
        else if(result['result']=='fail'){
            alert('邮箱已注册');
        }
		else {
            alert('注册成功');
		}
    });

    $("#rsignup").ajaxForm(function(data){
        $('.modal').modal('hide');
        var result=data;
        if(result['result']=='uncertain'){
            alert('请重新确认密码');
        }
        else {
            alert('注册成功\n这是您餐厅的识别码，您将用此来登录：\n'+result['codeID']);
        }
    });

    $("#login").ajaxForm(function(data){
        $('.modal').modal('hide');
        var result=data;
        if(result['result']=='notFound'){
            alert('不存在该餐厅');
        }
        else if(result['result']=='wrong'){
            alert('密码错误');
        }
        else {
            alert('登录成功');
            window.location.href="/user";
        }
    });

    $("#rlogin").ajaxForm(function(data){
        $('.modal').modal('hide');
        var result=data;
        if(result['result']=='notFound'){
            alert('不存在该用户');
        }
        else if(result['result']=='wrong'){
            alert('密码错误');
        }
        else {
            alert('登录成功');
            window.location.href="/restaurant";
        }
    });

    $("#mlogin").ajaxForm(function(data){
        var result=data;
        if(result['result']=='notFound'){
            alert('不存在该用户');
        }
        else if(result['result']=='wrong'){
            alert('密码错误');
        }
        else {
            alert('登录成功');
            window.location.href="/manager";
        }
    });

});

function addAddress(){
    var num=$('#address').children("div").length+1;
    alert(num);
    $('#address').append('                    <div id="address'+num+'">\n' +
        '                      <input name="address" type="text" list="itemList" class="form-control" placeholder="地址" style="display: inline;max-width: 230px">\n' +
        '                      <button style="display: inline;border-top: 10px ;border-left: 20px" type="button" class="addAddress" onclick="deleteAddress(\''+num+'\')"><i style="font-size: 30px"  class="icon-circle-with-minus"></i></button>\n' +
        '                    </div>');
}

function getCode() {
	var userName=$('#mail').val();
	$.ajax({ url: "/Index/getMail", dataType:"text" ,data:{userName:userName},type:"POST",timeout:3000 });
}

function deleteAddress(id) {
    if(id==1){
        return;
    }
    $('#address'+id).remove();
    var alter=$('#'+id).nextAll();
    for (var i=0;i<alter.length;i++){
        alter[i].attr('id','address'+id);
        id++;
    }
}

function close() {
    $('.modal').modal('hide');
}
