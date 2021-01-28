$(function(){
    $.post("/Index/init","text");


    $.ajax({ url: "/Manager/getAll", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
            var result=data;
            var table=$('#apply tbody');
            for (var i=0;i<result.length;i++){
                if(result[i].state=='审核中') {
                    table.append('      <tr>\n' +
                        '        <td>\n' +
                        '          '+(i+1)+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].modifyID+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].restaurantID+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].time.substring(0,10)+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].restaurantName+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].address+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].type+'\n' +
                        '        </td>\n' +
                        '        <td width="200">\n' +
                        '          <button style="font-size: 14px;padding-top: 1px;padding-bottom: 1px;padding-left: 10px;padding-right: 10px;" type="button" class="btn green" onclick="pass('+result[i].modifyID+',\''+result[i].restaurantID+'\')">通过</button>\n' +
                        '          <button style="position: relative ;left: 10px;font-size: 14px;height:22px;padding-top: 1px;padding-bottom: 1px;padding-left: 10px;padding-right: 10px;" type="button" class="btn btn-primary" onclick="notPass('+result[i].modifyID+',\''+result[i].restaurantID+'\')">未通过</button>\n' +
                        '        </td>\n' +
                        '      </tr>');
                }
                else {
                    table.append('      <tr>\n' +
                        '        <td>\n' +
                        '          '+(i+1)+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].modifyID+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].restaurantID+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].time+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].restaurantName+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].address+'\n' +
                        '        </td>\n' +
                        '        <td>\n' +
                        '          '+result[i].type+'\n' +
                        '        </td>\n' +
                        '        <td width="200">\n' +
                        '          '+result[i].state+'\n' +
                        '        </td>\n' +
                        '      </tr>');
                }
            }
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败");
            }
        }});


});

function pass(modifyID,restaurantID) {
    $.ajax({ url: "/Manager/approve", dataType:"text" ,type:"POST",data:{messageID:modifyID,restaurantID:restaurantID,result:'通过'},timeout:3000, success: function(data){
        alert('审批通过');
            window.location.reload()
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});
}

function notPass(modifyID,restaurantID) {
    $.ajax({ url: "/Manager/approve", dataType:"text" ,type:"POST",data:{messageID:modifyID,restaurantID:restaurantID,result:'未通过'},timeout:3000, success: function(data){
            alert('审批未通过');
            window.location.reload()
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});
}

function close() {
    $('.modal').modal('hide');
}