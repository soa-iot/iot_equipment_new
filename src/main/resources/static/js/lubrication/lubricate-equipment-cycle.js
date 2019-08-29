/**
 * 所有油品
 * @returns
 */
var userid = "张三";

 $("#add-lub-div").hide();
 $("#oil-stock-div").hide();
 $("#warning").hide();
 $("#warning1").hide();
 $("#warningnum").hide();
 $("#warningnum1").hide();
 
 $.ajax({
	  type: 'post',
	  async: false,
	  url: '/iot_equipment/equipmentoil/queryoilallstock',
	  dataType : "json",
	  success: function(json){
		  console.log(json)
		  var data = json.data;
		  console.log($(".layui-anim").html())
		  
		  $.each(data, function (i, item) {
				var option = '<option  value="'+item.oname+'">'+item.oname+'</option>';
				$("#oname1").append(option);
				$("#oname").append(option);
			});
	  }

})
 
layui.use(['table','laydate','layer', 'form'], function(){
	
	
  var table = layui.table
  	  ,laydate = layui.laydate
  	  ,layer = layui.layer
  	  ,form = layui.form;
  
  table.render({
    elem: '#record-table'
    ,url:'/iot_equipment/equipmentoil/queryoilallstock'
    ,limit:10
    ,page: true
    ,type:"numbers"
    ,cols: [[
    	{field:'zizeng', width:"10%", title: '序号',templet:'#numb'}
      ,{field:'oname', width:"30%", title: '油品名称'}
      ,{field:'ostock', width:"20%", title: '库存量'}
      ,{field:'ounit', width:"20%", title: '单位'}
      ,{fixed: '', title:'操作', toolbar: '#barDemo', width:"20%"}
    ]]
   
  });
  
  //监听行工具事件
	  table.on('tool(record-table)', function(obj){
	    var data = obj.data
	    ,oid = data.oid;
	    console.log(data.oid)
	    top.location.href = "../../html/lubrication/equipment-oil-record.html?oid="+oid;
	  });
	  
	  $("#oname").blur(function(){
		  console.log($("#oname").val())
		  if ($("#oname").val() != "") {
			  nameCheck("oname",$("#oname").val());
		}else{
			$("#warning").html("*油品名称不能为空*")
			$("#warning").attr("name","uncheck")
			$("#warning").show();
		}
		  console.log($("#warning").attr("name"));
		 
	  });
	  
	  $("#oname1").blur(function(){
		  console.log($("#oname1").val())
		  if ($("#oname1").val() != "") {
			  nameCheck("oname1",$("#oname1").val());
		}else{
			$("#warning1").html("*油品名称不能为空*")
			$("#warning1").attr("name","uncheck")
			$("#warning1").show();
		}
		  console.log($("#warning1").attr("name"));
		 
	  });
	  
	  $("#ostock").blur(function(){
		  console.log($("#ostock").val())
		  console.log(/(^[0-9]*(.[0-9]+)?)$/.test($("#ostock").val()))
		  
		  if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#ostock").val()) && $("#ostock").val() >= 0) {
			  $("#warningnum").attr("name","check");
			  $("#warningnum").hide();
		}else{
			  $("#warningnum").attr("name","uncheck")
			  $("#warningnum").show();
		}
		  console.log($("#warningnum").attr("name"));
		 
	  });
		  
	 $("#ramount").blur(function(){
			console.log($("#ramount").val())
			console.log(/(^[0-9]*(.[0-9]+)?)$/.test($("#ramount").val()))
				  
			if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#ramount").val()) && $("#ramount").val() >= 0) {
				 $("#warningnum1").attr("name","check");
				 $("#warningnum1").hide();
			}else{             
				 $("#warningnum1").attr("name","uncheck")
				 $("#warningnum1").show();
			}
			 console.log($("#warningnum1").attr("name"));
				 
	 });
	  
	 form.on('submit(choose-equ)', function(obj){
		  console.log(obj)
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		  var ope= layer.open({
				type: 2
				,offset: 't' 
				,area: ['650px','620px;']
				//,id: 'choose-equ' //防止重复弹出
				,key:'id'
				,title:"选择设备"
				,content: "../../html/equipment-record-location.html"
				,btn: ['确认',"取消"]
				,btnAlign: 'c' //按钮居中
				,yes: function(index){
					console.log(index);
					//获取iframe窗口的body对象
		        	var body = layer.getChildFrame('body', index);
		        	//找到body对象下被选中的设备位号值
		        	var value = body.find(".layui-table-click td[data-field='equPositionNum']").find("div").text();
		        	//找到body对象下被选中的设备名称值
		        	var key = body.find(".layui-table-click td[data-field='equName']").find("div").text();
		        	
		        	$("#equName").val(key);
		        	$("#equPositionNum").val(value);
		        	$("#equName").attr('disabled','disabled');
		        	
		        	console.log(key);
		        	console.log(value);
		        	layer.close(index);
				}
		  		/*,btn3:function(index){
		  			console.log("其他设备");
		  			$("#equPositionNum").val("其他设备");
		  			$("#equName").val("");
		  			$("#equName").attr('disabled',false);
		        	layer.close(index);
		  		}*/
		  });
	  });
	 
	  form.on('submit(add-oil)', function(obj){
		  console.log(obj)
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		  var ope= layer.open({
				type: 1
				,offset: 't' 
				,area: ['450px','650px;']
				,id: 'coordinate' //防止重复弹出
				,key:'id'
				,title:"新增换油设备"
				,content: $("#add-lub-div")
				,btn: ['提交',"取消"]
				,btnAlign: 'c' //按钮居中
				,yes: function(){
					
					$(".layui-layer-btn0").off('click');
					var ch = $("#warning").attr("name");
					var chnum = $("#warningnum").attr("name");
					
					//if (ch == "check" && chnum == "check" && $("#oname").val() != '') {
						$.ajax({
							type: 'POST',
							async: false,
							url: '/iot_equipment/lubrication/addlub',
							data:{ 
								"lnamekey":$("#equPositionNum").val(),
								"lname":$("#equName").val(),
								"pplace": $("#pplace").val(),
								"requireoil1": $("#oname").val(),
								"requireoil2": $("#oname1").val(),
								"pamount": $("#pamount").val(),
								"pfrequency": $("#pfrequency").val(),
								"punit": $("#punit").val()
							},
							dataType: 'JSON',
							success: function(json){
								if(json.state == 0){
									$("#oname").val("")
									layer.msg("新增成功", {icon: 1, time: 2000, offset: '150px'});
									layer.close(ope);
								}else{
									layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
								}	
							},
							error: function(){
								layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
							}
						})
						
					/*}else{
						if ($("#oname").val() == '') {
						  $("#warning").html("*油品名称不能为空*")
						}
						$("#warning").show();
						if (chnum != "check") {
							$("#warningnum").show();
						}
					}*/
					
				}
		  });
	  });
	  
	  /**
		 * 设备信息展示表
		 */
		/*var equipmentTable = table.render({
			elem: '#equipmentInfo',
			method: 'post',
			url: '/iot_equipment/equipmentinfo/show',
			toolbar: '#toolbarBtn',
			defaultToolbar: [''],
			totalRow: true,
			page: true,   //开启分页
			cellMinWidth: 130,
			where: {
				'equMemoOne': equMemoOne,
			},
			request: {
			    pageName: 'page' //页码的参数名称，默认：page
			    ,limitName: 'limit' //每页数据量的参数名，默认：limit
			},
			parseData: function(res){ //res 即为原始返回的数据
			    return {
			      "code": res.code, //解析接口状态
			      "msg": res.msg, //解析提示文本
			      "count": res.count, //解析数据长度
			      "data": res.data      //解析数据列表
			    };
			},
			cols: [[
				{type:'radio'},
				{field:'welName', title:'装置列名', align:'center'},    //, templet:"<div>{{layui.util.toDateString(d.applydate,'yyyy-MM-dd HH:mm:ss')}}</div>"
				{field:'equMemoOne', title:'设备类别', align:'center'},
				{field:'equPositionNum', title:'设备位号', align:'center'},
				{field:'equName', title:'设备名称', align:'center'}]]
		});*/
	  
	  form.on('submit(oil-stock)', function(obj){

		  console.log(obj)
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		  var ope= layer.open({
				type: 1
				,offset: 't' 
				,area: ['450px','450px;']
				,id: 'coordinate' //防止重复弹出
				,key:'id'
				,title:"油品入库"
				,content: $("#oil-stock-div")
				,btn: ['提交',"取消"]
				,btnAlign: 'c' //按钮居中
				,yes: function(){
					
					$(".layui-layer-btn0").off('click');
					var ch = $("#warning1").attr("name");
					var chnum = $("#warningnum1").attr("name");
					
					if (ch == "uncheck" && chnum == "check"  && $("#oname").val() != '') {
						$.ajax({
							type: 'POST',
							async: false,
							url: '/iot_equipment/equipmentoil/oilstock',
							data:{ 
								"oname": $("#oname1").val(),
								"ramount": $("#ramount").val(),
								"rnote": $("#rnote").val(),
								"userid":userid
							},
							dataType: 'JSON',
							success: function(json){
								if(json.state == 0){
									$("#oname1").val("")
									layer.msg("入库成功", {icon: 1, time: 2000, offset: '150px'});
									layer.close(ope);
								}else{
									layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
								}	
							},
							error: function(){
								layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
							}
						})
						
					}else{
						if ($("#oname1").val() == '') {
						  $("#warning1").html("*油品名称不能为空*")
						}
						$("#warning1").show();
						if (chnum != "check") {
							$("#warningnum1").show();
						}
					}
				}
		  });
	  });
	  
  });

function nameCheck(str,oname) {
	$.ajax({
		type: 'POST',
		async: false,
		url: '/iot_equipment/equipmentoil/findoilbyconditions',
		data:{ 
			"oname": oname
		},
		dataType: 'JSON',
		success: function(json){
			console.log(json);
			
			if(json.state == 0){
				var leng = json.data.length;
				if (str == 'oname') {
					if (leng > 0) {
						$("#warning").html("*此油品名称已存在*");
						$("#warning").show();
						$("#warning").attr("name","uncheck")
					}else{
						 $("#warning").hide();
						 $("#warning").attr("name","check")
					}
				} else if(str == 'oname1'){
					if (leng > 0) {
						
						$("#warning1").hide();
						$("#warning1").attr("name","uncheck")
					}else{
						$("#warning1").html("*此油品名称不存在*");
						$("#warning1").show();
						$("#warning1").attr("name","check")
					}
				}
				
			}else{
				layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
			}	
		},
		error: function(){
			layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
		}
	})
}
