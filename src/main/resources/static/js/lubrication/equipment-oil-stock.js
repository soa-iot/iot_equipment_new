/**
 * 所有油品
 * @returns
 */
var userid = "张三";

 $("#barDemo").hide();
 $("#add-oil-div").hide();
 $("#oil-stock-div").hide();
 $("#warning").hide();
 $("#warning1").hide();
 $("#warningnum").hide();
 $("#warningnum1").hide();
 
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
	  
	  form.on('submit(add-oil)', function(obj){
		  console.log(obj)
		  var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
		  var ope= layer.open({
				type: 1
				,offset: 't' 
				,area: ['450px','620px;']
				,id: 'coordinate' //防止重复弹出
				,key:'id'
				,title:"新增油品"
				,content: $("#add-oil-div")
				,btn: ['提交',"取消"]
				,btnAlign: 'c' //按钮居中
				,yes: function(){
					
					$(".layui-layer-btn0").off('click');
					var ch = $("#warning").attr("name");
					var chnum = $("#warningnum").attr("name");
					
					if (ch == "check" && chnum == "check" && $("#oname").val() != '') {
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
								"userid":userid
							},
							dataType: 'JSON',
							success: function(json){
								if(json.state == 0){
									$("#oname").val("")
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
						
					}else{
						if ($("#oname").val() == '') {
						  $("#warning").html("*油品名称不能为空*")
						}
						$("#warning").show();
						if (chnum != "check") {
							$("#warningnum").show();
						}
					}
					
				}
		  });
	  });
	  
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