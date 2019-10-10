/**
 * 所有油品
 * @returns
 */
var userid = "张三";

 $("#add-lub-div").hide();
 $("#oil-stock-div").hide();
 $("#warningpplace").hide();
 $("#warning").hide();
 $("#warningoname").hide();
 $("#warningpfrequency").hide();
 $("#warningpuint").hide();
 $("#warningpamount").hide();
 $("#warninglasttime").hide();
 
 /**
  * 日期插件
  */
 layui.use('laydate', function(){
 	var laydate = layui.laydate;

 	//常规用法
 	laydate.render({
 		elem: '#lastchangetime'
 		,max:0
 		,format:'yyyy/MM/dd'
 		//日期验证
 		,done: function(value, date, endDate){
 			if(value == ""){
 				$("#warninglasttime").html("*时间不能为空*");
 				$("#warninglasttime").attr("name","uncheck")
 				$("#warninglasttime").show();
 			}else{
 				$("#warninglasttime").attr("name","check")
 				$("#warninglasttime").hide();
 			}
 		}
 	});

 });
 
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
  	  ,form = layui.form
  	  ,rn = 1;
  
  var tab = table.render({
	    elem: '#record-table'
	    ,url:'/iot_equipment/lubrication/lubplace'
	    ,limit:10
	    ,page: true
	    ,cols: [[
	    	{field:'rn', title: '序号', width:'6%'}
	      ,{field:'lnamekey', title: '设备位号', width:'11%'}
	      ,{field:'lname', title: '设备名称', width:'11%'}
	      ,{field:'pplace', title: '润滑部位', width:'11%'}
	      ,{field:'requireoil1', title:'油品', width:'11%'}
	      ,{field: 'pamount', title:'加油量', width:'10%'}
	      ,{field: 'pfrequency', title:'润滑周期', width:'10%'}
	      ,{field: 'nextchangetime', title:'下一次换油时间', width:'15%', templet:function(d){
	    	  var date = '';
	    	  if (d.nextchangetime != null && d.nextchangetime != '') {
	    		  date = d.nextchangetime.substring(0,10);
			}
				return date;
			}}
	      ,{field: 'lastchangetime', title:'最后一次换油时间', templet:function(d){
	    	  var date = '';
	    	  if (d.lastchangetime != null && d.lastchangetime != '') {
	    		  date = d.lastchangetime.substring(0,10);
			}
				return date;
			}}
	    ]]
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
		        	
		        	if($("#equPositionNum").val() != ''){
		        		$("#warning").attr("name","check")
		        		$("#warning").hide();
		        	}
		        	
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
				,area: ['550px','600px;']
				,id: 'coordinate' //防止重复弹出
				,key:'id'
				,title:"新增换油设备"
				,content: $("#add-lub-div")
				,btn: ['提交',"取消"]
				,btnAlign: 'c' //按钮居中
				,yes: function(){
					
					var ch = $("#warning").attr("name");
					var chnum = $("#warningnum").attr("name");
					 var d= inputCheck();
					 
					if (d ==0) {
						nameCheck();
						if($("#warningpplace").attr("name")=="check"){
						$(".layui-layer-btn0").off('click');
						
						$.ajax({
							type: 'POST',
							async: false,
							url: '/iot_equipment/lubrication/addlub',
							data:{ 
								"lnamekey":$("#equPositionNum").val(),
								"lname":$("#equName").val(),
								"pplace": $("#pplace").val(),
								"requireoil1": $("#oname").val(),
								//"requireoil2": $("#oname1").val(),
								"pamount": $("#pamount").val(),
								"pfrequency": $("#pfrequency").val(),
								"lastchangetime":$("#lastchangetime").val(),
								"punit":$("#punit").val()
							},
							dataType: 'JSON',
							success: function(json){
								if(json.state == 0){
									$("#pplace").val("");
									//$("#oname").val("");
									$("#lastchangetime").val("");
									layer.msg("新增成功", {icon: 1, time: 2000, offset: '150px'});
									tab.reload();
									layer.close(ope);
								}else{
									layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
								}	
							},
							error: function(){
								layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
							}
						})
				     	}
					}
					
				}
		  });
	  });
	  
	  //油品名称验证
	  form.on('select(oname)', function(data){
		  if(data.value == ""){
				$("#warningoname").html("*请选择油品*");
				$("#warningoname").attr("name","uncheck")
				$("#warningoname").show();
			}else{
				$("#warningoname").attr("name","check")
				$("#warningoname").hide();
			}
		  
		}); 
	  
	  //润滑周期单位验证
	  form.on('select(punit)', function(data){
		  if(data.value == ""){
				$("#warningpuint").html("*请选择单位*");
				$("#warningpuint").attr("name","uncheck")
				$("#warningpuint").show();
			}else{
				$("#warningpuint").attr("name","check")
				$("#warningpuint").hide();
			}
		}); 
  });

 //验证
 $("#pplace").blur(function(){
	 console.log($("#pfrequency").val())
	if ($("#pplace").val() != "" ) {
		$("#warningpplace").hide();
		if ($("#equPositionNum").val() != "") {
			nameCheck();
		}
	}else{
		$("#warningpplace").html("*润滑部位不能为空*")
		$("#warningpplace").attr("name","uncheck")
		$("#warningpplace").show();
	}
	  console.log($("#warningpplace").attr("name"));
	 
 });
 
 //润滑周期验证
 $("#pfrequency").change(function(){
	 if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#pfrequency").val()) && $("#pfrequency").val() > 0) {
		 $("#warningpfrequency").attr("name","check");
		 $("#warningpfrequency").hide();
	}else{  
		 $("#warningpfrequency").html("*润滑周期要 > 0*");
		 $("#warningpfrequency").attr("name","uncheck")
		 $("#warningpfrequency").show();
	}
 })
  
 //标准加油量验证
 $("#pamount").change(function(){
	 if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#pamount").val()) && $("#pamount").val() > 0) {
		 $("#warningpamount").attr("name","check");
		 $("#warningpamount").hide();
	}else{     
		 $("#warningpamount").html("*加油量要 > 0*");
		 $("#warningpamount").attr("name","uncheck")
		 $("#warningpamount").show();
	}
 })

 
  function inputCheck() {
	  var c = 0;
	  
	  if ($("#pplace").val() == "") {
		  c++;
		  $("#warningpplace").html("*润滑部位不能为空*")
			$("#warningpplace").attr("name","uncheck")
			$("#warningpplace").show();
		}else{
			$("#warningpplace").attr("name","check")
			$("#warningpplace").hide();
		}
	  
	if($("#equPositionNum").val() == ""){
		c++;
		$("#warning").html("*设备位号不能为空*");
		$("#warning").attr("name","uncheck")
		$("#warning").show();
	}else{
		$("#warning").attr("name","check")
		$("#warning").hide();
	}
	
	if($("#oname").val() == ""){
		c++;
		$("#warningoname").html("*请选择油品*");
		$("#warningoname").attr("name","uncheck")
		$("#warningoname").show();
	}else{
		$("#warningoname").attr("name","check")
		$("#warningoname").hide();
	}
	
	if($("#punit").val() == ""){
		c++;
		$("#warningpuint").html("*请选择单位*");
		$("#warningpuint").attr("name","uncheck")
		$("#warningpuint").show();
	}else{
		$("#warningpuint").attr("name","check")
		$("#warningpuint").hide();
	}
	
	if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#pfrequency").val()) && $("#pfrequency").val() > 0) {
		 $("#warningpfrequency").attr("name","check");
		 $("#warningpfrequency").hide();
	}else{  
		c++;
		 $("#warningpfrequency").html("*润滑周期要 > 0*");
		 $("#warningpfrequency").attr("name","uncheck")
		 $("#warningpfrequency").show();
	}
	
	if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#pamount").val()) && $("#pamount").val() > 0) {
		 $("#warningpamount").attr("name","check");
		 $("#warningpamount").hide();
	}else{     
		c++;
		 $("#warningpamount").html("*加油量要 > 0*");
		 $("#warningpamount").attr("name","uncheck")
		 $("#warningpamount").show();
	}
	
	if($("#lastchangetime").val() == ""){
		c++;
		$("#warninglasttime").html("*时间不能为空*");
		$("#warninglasttime").attr("name","uncheck")
		$("#warninglasttime").show();
	}else{
		$("#warninglasttime").attr("name","check")
		$("#warninglasttime").hide();
	}
	return c;
}
 
function nameCheck() {
	console.log($("#equPositionNum").val());
	console.log($("#pplace").val());
	$.ajax({
		type: 'POST',
		async: false,
		url: '/iot_equipment/lubrication/findplaceandnamekey',
		data:{ 
			"lnamekey": $("#equPositionNum").val()
			,"pplace": $("#pplace").val()
		},
		dataType: 'JSON',
		success: function(json){
			console.log(json);
			
			if(json.state == 0){
				$("#warningpplace").html("*此润滑部位已存在*");
				$("#warningpplace").attr("name","uncheck")
				$("#warningpplace").show();
			}else{
				$("#warningpplace").attr("name","check")
				 $("#warningpplace").hide();
				//layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
			}	
		},
		error: function(){
			layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
		}
	})
}
