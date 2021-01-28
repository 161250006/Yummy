$(function () {
    $.post("/Index/init","text");

    $("#pageName").text(sessionStorage.getItem("restaurantName")+'餐厅');

    //展示菜单
    $.ajax({ url: "/ShowMenu/showMenu", dataType:"json" ,data:{restaurantID:sessionStorage.getItem("restaurantID")},type:"POST",timeout:3000, success: function(data){
            var result=data;
            var typeList=new Array();
            for (var i=0;i<result.length;i++){
                if (typeList.indexOf(data[i].type)==-1){
                    typeList.push(data[i].type);
                }
            }
            for (var i=0;i<typeList.length;i++){
                var name=typeList[i];
                var category=$("#bar");
                category.append('            <li  id="'+name+'栏">\n' +
                    '              <a href="#'+name+'" data-toggle="tab">'+name+'\n' +
                    '              </a>\n' +
                    '            </li>');

                $("#contentTable").append('<div class="tab-pane active" id="'+name+'">\n' +
                    '                <table class="table table-striped">\n' +
                    '                  <thead>\n' +
                    '                    <th>\n' +
                    '                      序号\n' +
                    '                    </th>\n' +
                    '                    <th>\n' +
                    '                      菜品名\n' +
                    '                    </th>\n' +
                    '                    <th>\n' +
                    '                      金额/元\n' +
                    '                    </th>\n' +
                    '                    <th>\n' +
                    '                      加入购物车\n' +
                    '                    </th>\n' +
                    '                  </thead>\n' +
                    '                  <tbody>\n' +
                    '                  </tbody>\n' +
                    '                </table>\n' +
                    '              </div>');
            }

            for (var i=0;i<result.length;i++){
                var tableName=result[i].type;
                var rows=$("#"+tableName+" table tbody");
                rows.append('                  <tr>\n' +
                    '                    <td>\n' +
                    '                      '+(i+1)+'\n' +
                    '                    </td>\n' +
                    '                    <td>\n' +
                    '                      '+result[i].name+'\n' +
                    '                    </td>\n' +
                    '                    <td>\n' +
                    '                      '+result[i].price+'\n' +
                    '                    </td>\n' +
                    '                    <td width="100">\n' +
                    '                      <button  type="button" class="btn green little" onclick="add('+(i+1)+',\''+result[i].name+'\',\''+result[i].price+'\')"><i class="icon-plus2"></i></button>\n' +
                    '                    </td>\n' +
                    '                  </tr>');
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

    $.ajax({ url: "/User/getAddress", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
            for(var i=0;i<data.length;i++){
                $("#address").append('<option>'+data[i]+'</option>');
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


function add(id,name,price)
{
    var total= Number(document.getElementById("total").innerHTML);
    total=total+Number(price);
    document.getElementById("total").innerHTML=total.toString();

    var table=document.getElementById("list").rows;
    var k=0;
    for(var i=0;i<table.length;i++){
        if(table[i].cells[0].innerHTML==id){
            k=1;
            table[i].cells[2].innerHTML=String(Number(table[i].cells[2].innerHTML)+1);
            break;
        }
    }
    if(k==0){
        var insert=document.getElementById("list").insertRow(document.getElementById("list").rows.length);
        var td1=insert.insertCell(0);
        var td2=insert.insertCell(1);
        var td3=insert.insertCell(2);
        var td4=insert.insertCell(3);
        td1.innerHTML=id;
        td2.innerHTML=name;
        td3.innerHTML="1";
        td4.innerHTML='<button  type="button" class="btn green little" onclick="deleteOne('+id+','+price+')"><i class="icon-minus2"></i></button>'
    }
}

function deleteOne(id,price) {
    var rows=document.getElementById("list").rows;
    for(var i=0;i<rows.length;i++){
        if(rows[i].cells[0].innerHTML==id){
            if(Number(rows[i].cells[2].innerHTML)==1){
                rows[i].parentNode.removeChild(rows[i]);
            }
            else{
                rows[i].cells[2].innerHTML=String(Number(rows[i].cells[2].innerHTML)-1);
            }
        }
    }
    var total= Number(document.getElementById("total").innerHTML);
    total=total-price;
    document.getElementById("total").innerHTML=total.toString();
}
function close() {
    $('.modal').modal('hide');
}

function confirmOrder() {
    if ($("#list tr").length==1){
        alert("您还没有点单");
    }
    var level=0;
    var totalMoney=Number($("#total").text());
    $.ajax({ url: "/User/getLevel", dataType:"text" ,type:"POST",timeout:3000, success: function(data){
           level=Number(data);
            var discount=getDiscount(totalMoney,level);
            var content="";
            var table=$("#list tr");
            for (var i=1;i<table.length;i++){
                var td=table.eq(i).children();
                content=content+td.eq(1).text()+"×"+td.eq(2).text()+";";
            }

            $('#restaurant').text(sessionStorage.getItem("restaurantName"));
            var myDate = new Date();
            var sendTime=new Date(myDate.getTime()+3000000);
            $("#level").text(level);
            $('#time').text(myDate.toLocaleString());
            $('#content').text(content);
            $('#totalMoney').text(totalMoney);
            $('#payMoney').text(totalMoney-discount);
            $("#sendTime").val(sendTime.getHours()+":"+sendTime.getMinutes());
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

function getDiscount(money,level) {
    var discount=0;
    money=Number(money);
    if(level==2){
        discount=money*0.05;
    }
    else if(level==3){
        discount=money*0.1;
    }
    else if(level==4){
        discount=money*0.15;
    }
    else if(level==5){
        discount=money*0.2;
    }
    return Math.round(discount);
}

function insertOrder() {
    var addressJudge=judgeAddress($("#address").val(),sessionStorage.getItem("address"));
    if (addressJudge==false){
        alert("选择地址超出配送距离，请重新选择或添加新地址");
        return;
    }
    var myDate = new Date();
    var sendTime=new Date();
    sendTime.setHours(Number($("#sendTime").val().substring(0,2)));
    sendTime.setMinutes(Number($("#sendTime").val().substring(3,5)));
    if((sendTime.getTime()-myDate.getTime())<=2700000){
        alert("送餐时间必须在点餐时间后45分钟,请重新选择");
        return;
    }
    var orders={
        orderID:null,
        orderTime:myDate,
        moneyTotal:$("#payMoney").text(),
        moneyRestaurant:Math.round(Number($("#payMoney").text())*0.9),
        state:"待支付",
        userID:"",
        userName:"",
        restaurantID:sessionStorage.getItem("restaurantID"),
        restaurantName:sessionStorage.getItem("restaurantName"),
        type:"购买",
        content:$("#content").text(),
        sendTime:sendTime
    };
    $.ajax({ url: "/ShowMenu/insertOrder", contentType : 'application/json',data:JSON.stringify(orders),dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            alert("订单已下达");
            window.location.href="/userOrder";
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

function judgeAddress(userAddress,restaurantAddress) {
    if ((userAddress=="南京大学仙林校区")||(userAddress=="南京市仙林中心")){
        if (restaurantAddress=="南京大学仙林校区"){
            return true;
        }
        else if(restaurantAddress=='南京大学鼓楼校区'){
            return false;
        }
        else if(restaurantAddress=='南京市新街口'){
            return false;
        }
        else if(restaurantAddress=='南京市仙林中心'){
            return true;
        }
    }
    else if((userAddress=='南京大学鼓楼校区')||(userAddress=="南京市新街口")){
        if (restaurantAddress=="南京大学仙林校区"){
            return false;
        }
        else if(restaurantAddress=='南京大学鼓楼校区'){
            return true;
        }
        else if(restaurantAddress=='南京市新街口'){
            return true;
        }
        else if(restaurantAddress=='南京市仙林中心'){
            return false;
        }
    }
    return false;
}