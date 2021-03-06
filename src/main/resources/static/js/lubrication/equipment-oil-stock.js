/**
 * 所有油品
 * @returns
 */
var userid =getCookie("userID").replace(/"/g,'');//"张三";

 $("#barDemo").hide();
 $("#add-oil-div").hide();
 $("#edit-oil-div").hide();
 $("#oil-stock-div").hide();
 $("#warning").hide();
 $("#warning1").hide();
 $("#warningnum").hide();
 $("#warningnum1").hide();
 $("#edit-warningnum").hide();
 
 var table_stock = null;
layui.use(['table','laydate','layer', 'form'], function(){
	
  var table = layui.table
  	  ,laydate = layui.laydate
  	  ,layer = layui.layer
  	  ,form = layui.form
  	  ,$ = layui.jquery
  	  ,onames = []
  	  ,edit_oname;
  
  initOname();
  function initOname(){
	  $.ajax({
		  type: 'post',
		  async: false,
		  url: '/iot_equipment/equipmentoil/queryoilallstock',
		  dataType : "json",
		  success: function(json){
			  $("#oname1").empty();
			  onames.length = 0;
			  console.log(json)
			  var data = json.data;
			  console.log($(".layui-anim").html())
			  
			  $.each(data, function (i, item) {
				  var option = '<option  value="'+item.oname+'">'+item.oname+'</option>';
				  onames[onames.length]=item.oname;
				  $("#oname1").append(option);
			  });
			  form.render('select');
		  }
	  
	  })
  }
  
   table_stock = table.render({
    elem: '#record-table'
    ,url:'/iot_equipment/equipmentoil/queryoilallstock'
    ,limit:10
    ,page: true
    ,type:"numbers"
    ,cols: [[
    	{field:'rn', width:"10%", title: '序号',templet:'#numb', align:'center'}
      ,{field:'oname', width:"20%", title: '油品名称', align:'center'}
      ,{field:'otype', width:"15%", title: '油品类型', align:'center'}
      ,{field:'ostock', width:"15%", title: '库存量', align:'center'}
      ,{field:'ounit', width:"20%", title: '单位', align:'center'}
      ,{fixed: '', title:'操作', toolbar: '#barDemo', width:"20%", align:'center'}
    ]]
   
  });
  
  //监听行工具事件
	  table.on('tool(record-table)', function(obj){
		  console.log(obj);
	    var data = obj.data
	    ,oid = data.oid;
	    if(obj.event == 'record'){
	    	location.href = "../../html/lubrication/equipment-oil-record.html?oid="+oid;
	    }else if (obj.event == 'delelio') {
	    	layer.confirm("是否确认删除", {icon: 7, offset: '150px'},function(index){
	    		$.ajax({
					type: 'POST',
					async: false,
					url: '/iot_equipment/equipmentoil/editoil',
					data:{ 
						"oid": data.oid,
						"oremark1":'1',
						"userid":userid,
						"rtype":"删除"
					},
					dataType: 'JSON',
					success: function(json){
						if(json.state == 0){
							initOname();
							if (table_stock != null) {
								table_stock.reload('#record-table',null)
							}
							layer.msg("油品删除成功", {icon: 1, time: 2000, offset: '150px'});
						}else{
							layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
						}	
					},
					error: function(){
						layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
					}
				})
				
	    		layer.close(index);
	    	});
		} else if (obj.event == 'edit'){
					
			edit_oname = data.oname;
			 $("#edit-oname").val(data.oname);
			 $("#edit-ostock").val(data.ostock);
			// $("#otype").val(data.otype);
			 $("#edit-manufacture").val(data.manufacture);
			 $("#edit-osign").val(data.osign);
			 $("#edit-odescribe").val(data.odescribe);
				  
			 var ope= layer.open({
					type: 1
					,offset: 't' 
					,area: ['550px','460px;']
					,id: 'coordinate' //防止重复弹出
					,key:'id'
					,title:"修改油品"
					,content: $("#edit-oil-div")
					,btn: ['提交',"取消"]
					,btnAlign: 'c' //按钮居中
					,yes: function(){
							
						if ($("#edit-oname").val() == null || $("#edit-oname").val() == "") {
							$("#edit-warning").html("*油品名称不能为空*")
							$("#edit-warning").show();
						} else if ( $("#edit-ostock").val() == null || $("#edit-ostock").val() == '' || $("#edit-ostock").val() < 0) {
							$("#edit-warningnum").show();
						} else {
							  var ona = true;
							  $.each(onames, function (i, item) {
								  if($("#edit-oname").val() != edit_oname && $("#edit-oname").val() == item){
									  ona = false;
									  $("#edit-warning").html("*不能修改为此名称！*");
									  $("#edit-warning").show();
									  return false;
								  }
								  ona = true;
							  });
							  if (ona) {
								  $("span").hide();
								  $(".layui-layer-btn0").off('click');
								  $.ajax({
									  type: 'POST',
									  async: false,
									  url: '/iot_equipment/equipmentoil/editoil',
									  data:{ 
										  "oname": $("#edit-oname").val(),
										  "ostock": $("#edit-ostock").val(),
										  "rtype": "修改",
										  "manufacture": $("#edit-manufacture").val(),
										  "osign": $("#edit-osign").val(),
										  "odescribe": $("#edit-odescribe").val(),
										  "oremark1":'0',
										  "oid":data.oid,
										  "userid":userid
									  },
									  dataType: 'JSON',
									  success: function(json){
										  if(json.state == 0){
											  
											  if (table_stock != null) {
												  table_stock.reload('#record-table',null)
											  }
											  initOname();
											  layer.msg("油品修改成功", {icon: 1, time: 2000, offset: '150px'});
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
		}
	    console.log(data.oid)
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
	  
	  $("#edit-oname").blur(function(){
		  if ($("#edit-oname").val() != "") {
			  var ona = true;
			  $.each(onames, function (i, item) {
				  if($("#edit-oname").val() != edit_oname && $("#edit-oname").val() == item){
					  ona = false;
					  console.log($("#edit-oname").val());
					  $("#edit-warning").html("*不能修改为此名称,此名称已存在*");
					  $("#edit-warning").show();
					  return ona;
				  }
				  ona = true;
			  });
			  console.log("ona:"+ona)
			  if (ona) {
				  $("#edit-warning").hide();
			}
		}else{
			$("#edit-warning").html("*油品名称不能为空*")
			$("#edit-warning").show();
		}
		 
	  });
	  
	//油品名称验证
	  form.on('select(oname1)', function(data){
		  if(data.value != ""){
				 nameCheck("oname1",$("#oname1").val());
			}else{
				$("#warning1").html("*油品名称不能为空*")
				$("#warning1").attr("name","uncheck")
				$("#warning1").show();
			}
		  
		}); 	  
	  
	  $("#ostock").blur(function(){
		  console.log($("#ostock").val())
		  console.log(/(^[0-9]*(.[0-9]+)?)$/.test($("#ostock").val()))
		  
		  if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#ostock").val()) && $("#ostock").val() > 0) {
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
				  
			if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#ramount").val()) && $("#ramount").val() > 0) {
				 $("#warningnum1").attr("name","check");
				 $("#warningnum1").hide();
			}else{             
				 $("#warningnum1").attr("name","uncheck")
				 $("#warningnum1").show();
			}
			 console.log($("#warningnum1").attr("name"));
				 
	 });
	  
	//油品入库验证
	 function oilStockCheck(){
		 if ($("#oname1").val() != "") {
			  nameCheck("oname1",$("#oname1").val());
		}else{
			$("#warning1").html("*油品名称不能为空*")
			$("#warning1").attr("name","check")
			$("#warning1").show();
		}
		 if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#ramount").val()) && $("#ramount").val() > 0) {
			 $("#warningnum1").attr("name","check");
			 $("#warningnum1").hide();
		}else{             
			 $("#warningnum1").attr("name","uncheck")
			 $("#warningnum1").show();
		}
		 
	 }
	 
	//新增油品验证
	 function addOilCheck(){
		 if ($("#oname").val() != "") {
			  nameCheck("oname",$("#oname").val());
		}else{
			$("#warning").html("*油品名称不能为空*")
			$("#warning").attr("name","uncheck")
			$("#warning").show();
		}
		 if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#ostock").val()) && $("#ostock").val() > 0) {
			  $("#warningnum").attr("name","check");
			  $("#warningnum").hide();
		}else{
			  $("#warningnum").attr("name","uncheck")
			  $("#warningnum").show();
		}
		 
	 }
	 
	 function addOilCheck1(){
		 if ($("#oname").val() != "") {
			 $("#warning").attr("name","check");
		}else{
			$("#warning").html("*油品名称不能为空*")
			$("#warning").attr("name","uncheck")
			$("#warning").show();
		}
		 if (/(^[\-0-9][0-9]*(.[0-9]+)?)$/.test($("#ostock").val()) && $("#ostock").val() > 0) {
			  $("#warningnum").attr("name","check");
			  $("#warningnum").hide();
		}else{
			  $("#warningnum").attr("name","uncheck")
			  $("#warningnum").show();
		}
		 
	 }
	 
	  form.on('submit(add-oil)', function(obj){
		  console.log(obj)
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		  
		//  $(".layui-input").val("");
		  
		  var ope= layer.open({
				type: 1
				,offset: 't' 
				,area: ['550px','460px;']
				,id: 'coordinate' //防止重复弹出
				,key:'id'
				,title:"新增油品"
				,content: $("#add-oil-div")
				,btn: ['提交',"取消"]
				,btnAlign: 'c' //按钮居中
				,yes: function(){
					
					addOilCheck();
					
					var ch = $("#warning").attr("name");
					var chnum = $("#warningnum").attr("name");
					
					if (ch == "check" && chnum == "check" && $("#oname").val() != '') {
						$(".layui-layer-btn0").off('click');
						$.ajax({
							type: 'POST',
							async: false,
							url: '/iot_equipment/equipmentoil/addoil',
							data:{ 
								"oname": $("#oname").val(),
								"ostock": $("#ostock").val(),
								"otype": $("#otype").val(),
								"manufacture": $("#manufacture").val(),
								"osign": $("#osign").val(),
								"odescribe": $("#odescribe").val(),
								"oremark1":'0',
								"userid":userid
							},
							dataType: 'JSON',
							success: function(json){
								if(json.state == 0){
									$("#oname").val("")
									initOname();
									if (table_stock != null) {
										table_stock.reload('#record-table',null)
									}
									
									layer.msg("油品新增成功", {icon: 1, time: 2000, offset: '150px'});
									layer.close(ope);
								}else{
									layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
								}	
							},
							error: function(){
								layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
							}
						})
						
					}/*else{
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
	  
	  form.on('submit(oil-stock)', function(obj){
		  

		  console.log(obj)
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		 
		  console.log($(".layui-select-none").html());
		  
		  var ope= layer.open({
				type: 1
				,offset: 't' 
				,area: ['550px','450px;']
				,id: 'coordinate' //防止重复弹出
				,key:'id'
				,title:"油品入库"
				,content: $("#oil-stock-div")
				,btn: ['提交',"取消"]
				,btnAlign: 'c' //按钮居中
				,yes: function(){
					
					oilStockCheck();
					var ch = $("#warning1").attr("name");
					var chnum = $("#warningnum1").attr("name");
					
					console.log("oname1:"+ch);
					console.log("num:"+chnum);
					
					if (ch == "uncheck" && chnum == "check"  && $("#oname1").val() != '') {
						
						console.log("正在提交........")
						$(".layui-layer-btn0").off('click');
						$.ajax({
							type: 'POST',
							async: false,
							url: '/iot_equipment/equipmentoil/oilstock',
							data:{ 
								"oname": $("#oname1").val(),
								"ramount": $("#ramount").val(),
								"rnote": $("#rnote").val(),
								"userid":userid,
								"otype":"油品入库",
								"calcType":"加"
							},
							dataType: 'JSON',
							success: function(json){
								if(json.state == 0){
									$("#oname1").val("")
									
									console.log("table_stock:"+table_stock);
									if (table_stock != null) {
										table_stock.reload('#record-table',null)
									}
									
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
						
					}/*else{
						if ($("#oname1").val() == '') {
						  $("#warning1").html("*油品名称不能为空*")
						}
						$("#warning1").show();
						if (chnum != "check") {
							$("#warningnum1").show();
						}
					}*/
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
					if (leng > 0 && json.data[0].oremark1==0) {
						$("#warning").html("*此油品名称已存在*");
						$("#warning").show();
						$("#warning").attr("name","uncheck")
					}else{
						 $("#warning").hide();
						 $("#warning").attr("name","check")
					}
				} else if(str == 'oname1'){
					if (leng > 0 && json.data[0].oremark1==0) {
						
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
