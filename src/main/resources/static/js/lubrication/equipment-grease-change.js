/**
 * 
 * 油脂库存盘点
 * 
 */

var userid = getCookie("userID").replace(/"/g,'');//"张三";

console.log("userid:"+userid);

$.ajax({
	  type: 'post',
	  async: false,
	  url: '/iot_equipment/equipmentoil/queryoilallstock',
	  dataType : "json",
	  success: function(json){
		  var data = json.data;
		  
		  $.each(data, function (i, item) {
				var option = '<option  value="'+item.oname+'">'+item.oname+'</option>';
				$("#oname").append(option);
			});
	  }

})

layui.use(['table','laydate','layer', 'form'], function(){
	
	  var table = layui.table
	  	  ,laydate = layui.laydate
	  	  ,layer = layui.layer
	  	  ,form = layui.form
	  	  ,$ = layui.jquery
	  	  ,lastchangetime
	  	  ,pid;
	  
	  $("#userid").val(userid);

	  laydate.render({
	    elem: '#inv-time'
	   // ,value:new Date()
	  	,type: 'date'
	  	,format:"yyyy-MM-dd"
	  });
	  
	  form.on('submit(choose-equ)', function(obj){
		  console.log(obj)
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		  var ope= layer.open({
				type: 2
				,offset: 't' 
				,area: ['90%','80%']
				//,id: 'choose-equ' //防止重复弹出
				,key:'id'
				,title:"选择设备"
				,content: "../../html/equipment-management-location.html"
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
		        	
		        	 $.ajax({
		 				type: 'POST',
		 				async: false,
		 				url: '/iot_equipment/lubrication/findplaceandnamekey',
		 				data:{ 
		 					"lnamekey": value
		 					
		 				},
		 				dataType: 'JSON',
		 				success: function(json){
		 					console.log(json);
		 					if(json.state == 0){
		 						var data = json.data;
		 						lastchangetime = data;
		 						console.log(lastchangetime);
		 						$("#pplace").empty();
		 						  $.each(data, function (i, item) {
		 							  console.log(item.pplace);
		 							 //lastchangetime[item.pplace] = item.lastchangetime;
		 								var option = '<option  value="'+item.pplace+'">'+item.pplace+'</option>';
		 								$("#pplace").append(option);
		 							});
		 						  pid = data[0].pid;
		 						 $("#inv-time").val(data[0].nextchangetime.substring(0,10));
		 						// console.log(lastchangetime);
		 						 form.render();
		 						
		 					}else{
		 						layer.msg("请先添加此设备的润滑部位！！！", {icon: 7, time: 3000, offset: '150px'});
		 					}	
		 				},
		 				error: function(){
		 					layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
		 				}
		 			})
		        	
		        	
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
	  
	  //级联
	  form.on('select(pplace)', function(obj){
		  
		  $.each(lastchangetime, function (i, item) {
				 if (item.pplace == $("#pplace").val()) {
					 $("#inv-time").val(lastchangetime[i].nextchangetime.substring(0,10));
					pid = lastchangetime[i].pid;
				}
					
			});
		  
		  //$("#inv-time").val(lastchangetime[$("#pplace").val()].substring(0,10));
	  })
	  
	  //验证
	  function verify(){
		  if ($("#oname").val() == null || $("#oname").val() == "" ) {
			  layer.msg("油品名称不能为空！！", {icon: 7, time: 2000, offset: '150px'});
			  return false;
		}else if( $("#ramount").val()==0 || $("#ramount").val() == null || $("#ramount").val() < 0 ){
			layer.msg("油品数量必须大于0！！", {icon: 7, time: 2000, offset: '150px'});
			  return false;
		}else if($("#equPositionNum").val() == null || $("#equPositionNum").val() == ""){
			layer.msg("位号不能为空！！", {icon: 7, time: 2000, offset: '150px'});
			  return false;
		}
		  return true;
	  };
	  //提交
	  form.on('submit(sub)', function(obj){
		  console.log("提交盘点信息....");
		  console.log(pid);
		  
		 if (verify()) {
		  
		    $.ajax({
				type: 'get',
				async: false,
				url: '/iot_equipment/lubrication/lueqpladdchangeoil',
				data:{ 
					"pid":pid,
					"excutor":userid,
					"operatetype":"换脂",
					"ramount": $("#ramount").val(),
					"requireoil1": $("#oname").val(),
					"ptime":new Date()
				},
				dataType: 'JSON',
				success: function(json){
					if(json.state == 0){
						$('#inv-from')[0].reset(); 
						  $("#userid").val(userid);
						  $("#inv-time").val(new Date());
						layer.msg("提交成功", {icon: 1, time: 2000, offset: '150px'});
					}else{
						layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
					}	
				},
				error: function(){
					layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
				}
			});
		  }	
	  });
	  
	  //重置
	  form.on('submit(reset)', function(obj){
		  $('#inv-from')[0].reset(); 
		  $("#userid").val(userid);
		  $("#inv-time").val(new Date());
	  });

})