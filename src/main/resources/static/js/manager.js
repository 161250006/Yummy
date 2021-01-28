$(function(){
    $.post("/Index/init","text");


    $.ajax({ url: "/Manager/getName", dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            $('#pageName').text(data+'经理');

        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});

    $.ajax({ url: "/Manager/getData", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
            $('#userNumber').text(data['userNumber']);
            $('#restaurantNumber').text(data['restaurantNumber']);
            $('#inComeTotal').text(data['total']);
            $('#balanceTotal').text(data['income']);
            $('#balanceMonth').text(data['month']);
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});

});

function logout() {
    $.post("/managerLogout","text");
}

function close() {
    $('.modal').modal('hide');
}
