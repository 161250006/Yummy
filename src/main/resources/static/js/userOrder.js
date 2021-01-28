$(function(){
    $.post("/Index/init","text");
    $('#pageName').text('我的订单');

    $.ajax({ url: "/Order/getAllOrdersUser", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
        for (var i=0;i<data.length;i++){
            var date=new Date(data[i].orderTime);
            var sendTime=new Date(data[i].sendTime);
            if(data[i].type=="购买") {
                if (data[i].state == "待支付") {
                    $('#tableOrder tbody').append('            <tr>\n' +
                        '              <td>\n' +
                        '                ' + data[i].orderID + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + date.toLocaleString() + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + sendTime.toLocaleString() + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + data[i].restaurantName + '\n' +
                        '              </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].content + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyTotal + '\n' +
                        '            </td>\n' +
                        '            <td width="250">\n' +
                        '              待支付\n' +
                        '              <button style="position: relative ;left: 20px;font-size: 14px;padding-top: 1px;padding-bottom: 1px;padding-left: 10px;padding-right: 10px;" type="button" class="btn green" data-toggle="modal" data-target="#pay" onclick="payBefore(\'' + data[i].orderID + '\',\''+data[i].moneyTotal+ '\',\''+date+'\')">支付</button>\n' +
                        '              <button style="position: relative ;left: 30px;height:22px;font-size: 14px;padding-top: 1px;padding-bottom: 1px;padding-left: 10px;padding-right: 10px;" type="button" class="btn btn-primary" onclick="cancel(\'' + data[i].orderID + '\')">取消</button>\n' +
                        '            </td>\n' +
                        '            </tr>');
                }
                else if (data[i].state == "已支付") {
                    $('#payedOrder tbody').append('            <tr>\n' +
                        '              <td>\n' +
                        '                ' + data[i].orderID + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + date.toLocaleString()  + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + sendTime.toLocaleString() + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + data[i].restaurantName + '\n' +
                        '              </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].content + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyTotal + '\n' +
                        '            </td>\n' +
                        '            <td width="250">\n' +
                        '                    已支付\n' +
                        '                <button style="position: relative ;left: 20px;font-size: 14px;padding-top: 1px;padding-bottom: 1px;padding-left: 10px;padding-right: 10px;" type="button" class="btn green" onclick="confirm(\'' + data[i].orderID + '\')">确认送达</button>\n' +
                        '                <button style="position: relative ;left: 30px;height:22px;font-size: 14px;padding-top: 1px;padding-left: 10px;padding-right: 10px;padding-bottom: 1px" type="button" class="btn btn-primary" data-toggle="modal" data-target="#unsubscribe" onclick="unsubscribeBefore(\'' + data[i].orderID + '\',\''+data[i].moneyTotal+ '\',\''+sendTime+'\')">退订</button>' +
                        '            </td>\n' +
                        '            </tr>');
                }
                else if (data[i].state == "已取消") {
                    $('#cancelOrder tbody').append('            <tr>\n' +
                        '              <td>\n' +
                        '                ' + data[i].orderID + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + date.toLocaleString()  + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + sendTime.toLocaleString() + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + data[i].restaurantName + '\n' +
                        '              </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].content + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyTotal + '\n' +
                        '            </td>\n' +
                        '            <td width="250">\n' +
                        '              已取消\n' +
                        '            </td>\n' +
                        '            </tr>');
                }
                else {
                    $('#doneOrder tbody').append('<tr>\n' +
                        '              <td>\n' +
                        '                ' + data[i].orderID + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + date.toLocaleString()  + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + sendTime.toLocaleString() + '\n' +
                        '              </td>\n' +
                        '              <td>\n' +
                        '                ' + data[i].restaurantName + '\n' +
                        '              </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].content + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyTotal + '\n' +
                        '            </td>\n' +
                        '            <td width="250">\n' +
                        '              已完成\n' +
                        '            </td>\n' +
                        '            </tr>');
                }
            }
            else {
                $('#tableUnsubscribe tbody').append('            <tr>\n' +
                    '              <td>\n' +
                    '                ' + data[i].orderID + '\n' +
                    '              </td>\n' +
                    '              <td>\n' +
                    '                ' + date.toLocaleString()  + '\n' +
                    '              </td>\n' +
                    '              <td>\n' +
                    '                ' + sendTime.toLocaleString() + '\n' +
                    '              </td>\n' +
                    '              <td>\n' +
                    '                ' + data[i].restaurantName + '\n' +
                    '              </td>\n' +
                    '            <td>\n' +
                    '              ' + data[i].content + '\n' +
                    '            </td>\n' +
                    '            <td>\n' +
                    '              ' + data[i].moneyTotal + '\n' +
                    '            </td>\n' +
                    '            <td width="250">\n' +
                    '              已退订\n' +
                    '            </td>\n' +
                    '            </tr>');
            }
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



});


function close() {
    $('.modal').modal('hide');
}

function pay(orderID) {
    $.ajax({ url: "/Order/pay", data:{orderID:orderID},dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            alert("付款完成");
            window.location.reload(true);
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});
    close();
}

function payBefore(orderID,totalMoney,orderTimeString) {
    $.ajax({ url: "/User/getBalance", data:{orderID:orderID},dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            var balance=Number(data);
            $("#payMoney").text(totalMoney);
            $("#balance").text(balance);
            var date=new Date();
            var orderTime=new Date(orderTimeString);
            $("#timeLeft").text(Math.round(2-(date.getTime()-orderTime.getTime())/60000).toString());
            $("#payCommit").attr("onclick",'pay(\''+orderID+'\')');
            if (balance<totalMoney){
                $("#balanceNotEnough").show();
                $("#payCommit").attr("disabled","true");
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
}

function cancel(orderID) {

    $.ajax({ url: "/Order/cancel", data:{orderID:orderID},dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            alert("订单已取消");
           location.reload(true);
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

function unsubscribe(orderID,unsubscribeMoney) {
    $.ajax({ url: "/Order/unsubscribe", data:{orderID:orderID,unsubscribeMoney:unsubscribeMoney},dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            alert("订单已退订");
            window.location.reload(true);
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});
    close();
}

function unsubscribeBefore(orderID,totalMoney,sendTimeString) {
    $("#totalMoney").text(totalMoney);
    var getBackMoney=0;
    var date=new Date();
    var sendTime=new Date(sendTimeString);
    var minutesLeft=(sendTime.getTime()-date.getTime())/60000;
    if(minutesLeft>30){
        getBackMoney=Math.round(Number(totalMoney)*0.8);
    }
    else if(minutesLeft>15){
        getBackMoney=Math.round(Number(totalMoney)*0.5);
    }
    else if(minutesLeft>0){
        getBackMoney=Math.round(Number(totalMoney)*0.8);
    }
    $("#getBackMoney").text(getBackMoney);
    $("#unsubscribeCommit").attr("onclick",'unsubscribe(\''+orderID+'\',\''+getBackMoney+'\')');
}

function confirm(orderID) {
    $.ajax({ url: "/Order/confirm", data:{orderID:orderID},dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            alert("订单已确认送达");
            window.location.reload(true);
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