$(document).ready(function () {
    $.post("/Index/init","text");


    $.ajax({ url: "/Restaurant/getName", dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            $('#pageName').text(data+'餐厅');
        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});

    $.ajax({ url: "/Restaurant/Inform", dataType:"text" ,type:"POST",timeout:3000, success: function(data){
            if (data=="pass"){
                alert("您的修改信息申请已通过，可以去修改界面查看");
            }
            else if (data=="notPass"){
                alert("您的修改信息申请未通过，可以重新申请");
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

    $.ajax({ url: "/Restaurant/getInformation", dataType:"json" ,type:"POST",timeout:3000, success: function(data){
            var result=data;
            $('#codeID').text(result['restaurantID']);
            $('#restaurantName').val(result['restaurantName']);
            $('#password').val(result['password']);
            $('#type').val(result['type']);
            $('#address').val(result['address']);

        }
        ,error:function (XMLHttpRequest, textStatus, errorThrown) {
            if(textStatus==408){
                alert("请求超时");
            }
            else {
                alert("请求失败")
            }
        }});

    //得到菜单
    $.ajax({ url: "/Restaurant/getMenu",dataType:"json" ,type:"POST",timeout:3000, success: function(data) {
        var result=data;
        var typeList=new Array();
        for (var i=0;i<result.length;i++){
            if (typeList.indexOf(data[i].type)==-1){
                typeList.push(data[i].type);
            }
        }
        //创建类别
        for (var i=0;i<typeList.length;i++){
            var name=typeList[i];
            var cat=$("#bar li");
            var category=$("#add");
            category.before('            <li  id="'+name+'栏">\n' +
                '              <a href="#'+name+'" data-toggle="tab" style="display: inline">'+name+'\n' +
                '              <button  style="display: inline;margin-right: 10px" type="button" class="btn little" onclick="deleteCategory(\''+name+'\')"><i class="icon-minus2"></i></button>\n' +
                '              </a>\n' +
                '            </li>');

            var table=$("#content");
            table.append('<div class="tab-pane" id="'+name+'">\n' +
                '              <table class="table table-striped">\n' +
                '                <thead>\n' +
                '                <tr>\n' +
                '                  <th>\n' +
                '                    菜品序号\n' +
                '                  </th>\n' +
                '                  <th>\n' +
                '                    截止时间\n' +
                '                  </th>\n' +
                '                  <th>\n' +
                '                    菜品名\n' +
                '                  </th>\n' +
                '                  <th>\n' +
                '                    单价\n' +
                '                  </th>\n' +
                '                  <th>\n' +
                '                    删除\n' +
                '                  </th>\n' +
                '                </tr>\n' +
                '                </thead>\n' +
                '                <tbody>\n' +
                '                </tbody>\n' +
                '              </table>\n' +
                '              <button  style="margin-bottom: 20px" type="button" class="btn little" onclick="addOne(\''+name+'\')"><i class="icon-plus2"></i></button>\n' +
                '            </div>');
        }

        //加入数据
            for (var i=0;i<result.length;i++){
                var tableName=result[i].type;
                var rows=$("#"+tableName+" table tbody");
                var num=$("#"+tableName+" table tbody tr").length;
                rows.append('                <tr>\n' +
                    '                  <td>\n' +
                    '                    '+(num+1)+'\n' +
                    '                  </td>\n' +
                    '                  <td>\n' +
                    '                    <input  type="date" class="form-control" value="'+result[i].endTime.substring(0,10)+'">\n' +
                    '                  </td>\n' +
                    '                  <td>\n' +
                    '                    <input  type="text" class="form-control" value="'+result[i].name+'">\n' +
                    '                  </td>\n' +
                    '                  <td>\n' +
                    '                    <input  type="text" class="form-control" value="'+result[i].price+'">\n' +
                    '                  </td>\n' +
                    '                  <td width="100">\n' +
                    '                    <button  type="button" class="btn little" onclick="deleteOne('+(num+1)+',\''+tableName+'\')"><i class="icon-minus2"></i></button>\n' +
                    '                  </td>\n' +
                    '                </tr>');
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
        else if(result['result']=='fail'){
            alert('存在审核中申请');
        }
        else {
            alert('修改申请成功');
        }
    });

});


function deleteOne(id,tableName) {

    var rows=document.getElementById(tableName).children[0].rows;
    for(var i=0;i<rows.length;i++){
        if(rows[i].cells[0].innerHTML==id){
                rows[i].parentNode.removeChild(rows[i]);
                break;
        }
    }
    for(var i=0;i<rows.length;i++){
        if (i>0) {
            rows[i].cells[0].innerHTML = i.toString();
            rows[i].cells[4].innerHTML = '<button  type="button" class="btn little" onclick="deleteOne('+i+',\''+tableName+'\')"><i class="icon-minus2"></i></button>';
        }
    }
}

function logout() {
    $.post("/restaurantLogout","text");
}

function addCategory() {
    var name=$("#name").val();
    $("#name").val("");
    var cat=$("#bar li");
    var category=$("#add");
        category.before('            <li  id="'+name+'栏">\n' +
            '              <a href="#'+name+'" data-toggle="tab" style="display: inline">'+name+'\n' +
            '              <button  style="display: inline;margin-right: 10px" type="button" class="btn little" onclick="deleteCategory(\''+name+'\')"><i class="icon-minus2"></i></button>\n' +
            '              </a>\n' +
            '            </li>');

    var table=$("#content");
    table.append('<div class="tab-pane" id="'+name+'">\n' +
        '              <table class="table table-striped">\n' +
        '                <thead>\n' +
        '                <tr>\n' +
        '                  <th>\n' +
        '                    菜品序号\n' +
        '                  </th>\n' +
        '                  <th>\n' +
        '                    截止时间\n' +
        '                  </th>\n' +
        '                  <th>\n' +
        '                    菜品名\n' +
        '                  </th>\n' +
        '                  <th>\n' +
        '                    单价\n' +
        '                  </th>\n' +
        '                  <th>\n' +
        '                    删除\n' +
        '                  </th>\n' +
        '                </tr>\n' +
        '                </thead>\n' +
        '                <tbody>\n' +
        '                <tr>\n' +
        '                  <td>\n' +
        '                    1\n' +
        '                  </td>\n' +
        '                  <td>\n' +
        '                    <input  type="date" class="form-control" placeholder="">\n' +
        '                  </td>\n' +
        '                  <td>\n' +
        '                    <input  type="text" class="form-control" placeholder="">\n' +
        '                  </td>\n' +
        '                  <td>\n' +
        '                    <input  type="text" class="form-control" placeholder="">\n' +
        '                  </td>\n' +
        '                  <td width="100">\n' +
        '                    <button  type="button" class="btn little" onclick="deleteOne(1,\''+name+'\')"><i class="icon-minus2"></i></button>\n' +
        '                  </td>\n' +
        '                </tr>\n' +
        '                </tbody>\n' +
        '              </table>\n' +
        '              <button  style="margin-bottom: 20px" type="button" class="btn little" onclick="addOne(\''+name+'\')"><i class="icon-plus2"></i></button>\n' +
        '            </div>');
close();
}

function deleteCategory(name) {
    var li=$("#"+name+"栏");
    var table=$("#"+name+"");
    li.remove();
    table.remove();
}

function addOne(tableName) {
    var rows=$("#"+tableName+" table tbody");
    var num=$("#"+tableName+" table tbody tr").length;
    rows.append('                <tr>\n' +
        '                  <td>\n' +
        '                    '+(num+1)+'\n' +
        '                  </td>\n' +
        '                  <td>\n' +
        '                    <input  type="date" class="form-control" placeholder="">\n' +
        '                  </td>\n' +
        '                  <td>\n' +
        '                    <input  type="text" class="form-control" placeholder="">\n' +
        '                  </td>\n' +
        '                  <td>\n' +
        '                    <input  type="text" class="form-control" placeholder="">\n' +
        '                  </td>\n' +
        '                  <td width="100">\n' +
        '                    <button  type="button" class="btn little" onclick="deleteOne('+(num+1)+',\''+tableName+'\')"><i class="icon-minus2"></i></button>\n' +
        '                  </td>\n' +
        '                </tr>');
}

function close() {
    $('.modal').modal('hide');
}

function updateMenu() {
    var tables=$(".tab-pane");
    var menuList=new Array();
    for (var i=0;i<tables.length;i++){
        var tr=tables.eq(i).find("tr");
        var type=tables.eq(i).attr("id");

        for (var j=1;j<tr.length;j++) {
            var menu = {
                menuID: null,
                restaurantID: "",
                name: "",
                price: 0,
                type: "",
                endTime: ""
            }
            var td=tr.eq(j).find("input");
            if((td.eq(0).val()=="")||(td.eq(1).val()=="")||(td.eq(2).val()=="")){
                continue;
            }
            menu.name=td.eq(1).val();
            menu.price=td.eq(2).val();
            menu.type=type;
            menu.endTime=td.eq(0).val();
            menuList.push(menu);
        }
    }

    $.ajax({ url: "/Restaurant/updateMenu",contentType : 'application/json',data:JSON.stringify(menuList), dataType:"json" ,type:"POST",timeout:3000});
    alert("修改菜单成功");
}
