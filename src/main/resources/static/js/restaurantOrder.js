$(function(){
    $.post("/Index/init","text");
    $('#pageName').text('我的订单');

    $.ajax({ url: "/Order/getAllOrdersRestaurant", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
        for (var i=0;i<data.length;i++){
            var date=new Date(data[i].orderTime);
            var sendTime=new Date(data[i].sendTime);
            if(data[i].type=="购买") {
                if (data[i].state == "已支付") {
                    $('#buyTable tbody').append('            <tr>\n' +
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
                        '                ' + data[i].userName + '\n' +
                        '              </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].content + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyTotal + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyRestaurant + '\n' +
                        '            </td>\n' +
                        '            <td width="250">\n' +
                        '              待完成\n' +
                        '            </td>\n' +
                        '            </tr>');
                }

                else if(data[i].state == "已结束"){
                    $('#doneTable tbody').append('<tr>\n' +
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
                        '                ' + data[i].userName + '\n' +
                        '              </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].content + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyTotal + '\n' +
                        '            </td>\n' +
                        '            <td>\n' +
                        '              ' + data[i].moneyRestaurant + '\n' +
                        '            </td>\n' +
                        '            <td width="250">\n' +
                        '              已完成\n' +
                        '            </td>\n' +
                        '            </tr>');
                }
            }
            else {
                $('#unsubscribeTable tbody').append('            <tr>\n' +
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
                    '                ' + data[i].userName + '\n' +
                    '              </td>\n' +
                    '            <td>\n' +
                    '              ' + data[i].content + '\n' +
                    '            </td>\n' +
                    '            <td>\n' +
                    '              ' + data[i].moneyTotal + '\n' +
                    '            </td>\n' +
                    '            <td>\n' +
                    '              ' + data[i].moneyRestaurant + '\n' +
                    '            </td>\n' +
                    '            <td width="250">\n' +
                    '              已退款\n' +
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
