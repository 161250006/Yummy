$(function(){
    $.post("/Index/init","text");

    $.ajax({ url: "/User/getName", dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            $('#pageName').text('你好，'+data);
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});


    $.ajax({ url: "/User/showRestaurant", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
        for (var i=0;i<data.length;i++) {
            $('#content').append('      <div class="block col-md-4 column">\n' +
                '        <h1>\n' +
                '          '+data[i].name+'\n' +
                '        </h1>\n' +
                '        <h3 style="position: relative ;top: 20px">\n' +
                '          地点：<span>'+data[i].address+'</span>\n' +
                '        </h3>\n' +
                '        <h3 style="position: relative ;top: 20px">\n' +
                '          类型：<span>'+data[i].type+'</span>\n' +
                '        </h3>\n' +
                '        <p>\n' +
                '          <button class="btn btn-block green"  onclick="getIntoRestaurant(\''+data[i].restaurantID+'\',\''+data[i].name+'\',\''+data[i].address+'\')" style="position: relative ;top: 30px;margin:0 auto;">进入餐厅 »</button>\n' +
                '        </p>\n' +
                '      </div>');
        }
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});

    $.ajax({ url: "/User/getInformation", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
        var result=data;
            $('#userName').val(result['userName']);

            $('#password').val(result['password']);
            $('#level').text(result['level']);
            $('#name').val(result['name']);
            $('#phoneNumber').val(result['phoneNumber']);
            $('#balance').text(result['balance']);
            var address=result['address'];
            var i=1;
            while (i<=address.length){
                $('#address').append('                    <div id="address'+i+'">\n' +
                    '                      <input name="address" type="text" list="itemList" class="form-control" placeholder="地址" value="'+address[i-1]+'" style="display: inline;max-width: 230px">\n' +
                    '                      <button style="display: inline;border-top: 10px ;border-left: 20px" type="button" class="addAddress" onclick="deleteAddress(\''+i+'\')"><i style="font-size: 30px"  class="icon-circle-with-minus"></i></button>\n' +
                    '                    </div>');
                i++;
            }

        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});

    $("#modifyForm").ajaxForm(function(data){
        $('.modal').modal('hide');
        var result=data;
    	if(result['result']=='uncertain'){
    		alert('请重新确认密码');
		}
		else {
            alert('修改成功');
            window.reload();
		}
    });

    $("#input").ajaxForm(function(data){
        $('#content').empty();
        for (var i=0;i<data.length;i++) {
            $('#content').append('      <div class="block col-md-4 column">\n' +
                '        <h1>\n' +
                '          '+data[i].name+'\n' +
                '        </h1>\n' +
                '        <h3 style="position: relative ;top: 20px">\n' +
                '          地点：<span>'+data[i].address+'</span>\n' +
                '        </h3>\n' +
                '        <h3 style="position: relative ;top: 20px">\n' +
                '          类型：<span>'+data[i].type+'</span>\n' +
                '        </h3>\n' +
                '        <p>\n' +
                '          <button class="btn btn-block green" onclick="getIntoRestaurant(\''+data[i].restaurantID+'\',\''+data[i].name+'\')" style="position: relative ;top: 30px;margin:0 auto;">进入餐厅 »</button>\n' +
                '        </p>\n' +
                '      </div>');
        }
    });


});

function addAddress(){
    var num=$('#address').children("div").length+1;
    $('#address').append('                    <div id="address'+num+'">\n' +
        '                      <input name="address" type="text" list="itemList" class="form-control" placeholder="地址" style="display: inline;max-width: 230px">\n' +
        '                      <button style="display: inline;border-top: 10px ;border-left: 20px" type="button" class="addAddress" onclick="deleteAddress(\''+num+'\')"><i style="font-size: 30px"  class="icon-circle-with-minus"></i></button>\n' +
        '                    </div>');
}


function getCode() {
	var userName=$('#mail').val();
	alert(userName);
	$.ajax({ url: "/Index/getMail", dataType:"text" ,data:{userName:userName},type:"POST",timeout:3000 });
}

function logout() {
    $.post("/userLogout","text");
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

function deleteUser() {
    var result=confirm("确定注销该账号？");
    if (result==true){
        $.ajax({ url: "/User/deleteUser", dataType:"text" ,type:"POST",timeout:3000 });
    }
    window.location.href='/';
}

function getIntoRestaurant(restaurantID,restaurantName,address) {
    sessionStorage.setItem('restaurantID',restaurantID);
    sessionStorage.setItem('restaurantName',restaurantName);
    sessionStorage.setItem('address',address);
    window.location.href='/showMenu';
}


