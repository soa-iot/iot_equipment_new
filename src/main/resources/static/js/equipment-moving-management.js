//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});

/**
 * 日期插件
 */
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#startDate',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});

layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#endDate',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});

layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#repairTime',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});

//人员组织树url
var treeUrl = "http://10.89.90.118:10238/iot_process/userOrganizationTree/userOrganizationTreeData"

var fileList = null, equipNum = repairTime = null;
var infoData = null;
//加载layui内置模块
layui.use(['jquery','form','tree','layer','table','excel','upload'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,tree = layui.tree
	,upload = layui.upload
	,$ = layui.$ //使用jQuery
	var excel = layui.excel;
	

	/**
	 * 动设备检维修信息记录表
	 */
	var equInfoTable = table.render({
		elem: '#equ-moving-table',
		method: 'get',
		url: '/iot_equipment/maintenance/equipment/move/showlist',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:70,
		page: true,   //开启分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data;
			infoData = data;
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{type:'checkbox'},
			{field:'mid', title:'主键id', hide:true, align:'center'},
			{field:'equipName', title:'设备名称', width:'16%', sort:false,  align:'center'},
			{field:'positionNum', title:'设备位号', width:'15%', sort:false, align:'center'},
			{field:'maintenanceTime', title:'检修时间', width:'15%', sort:false, align:'center'},
			{field:'maintenancePerson', title:'维修人', width:'15%', sort:false, align:'center'},
			{fixed:'right', title:'操    作', width:'35%', sort:false, align:'center', toolbar:'#barBtn'}]]
	});

	var fileTable = table.render({
		elem: '#equ-filelist-table',
		method: 'get',
		url: '/iot_equipment/maintenance/equipment/move/file/showlist',
		autoSort: true,  //禁用前端自动排序
		initSort: {
		    field: 'date' //排序字段，对应 cols 设定的各字段名
		    ,type: 'desc' //排序方式  asc: 升序、desc: 降序、null: 默认排序
		  },
		cellMinWidth:70,  
		text: {
		    none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
		 },
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data;
			
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'id', title:'序号', type:'numbers',width:100, align:'center'},
			{field:'filename', title:'文件名', width:500, sort:false,  align:'center'},
			/*{field:'date', title:'上传日期', width:145, sort:false, align:'center'},*/
			{fixed:'right', title:'操    作', width:191, sort:false, align:'center', toolbar:'#fileBtn'}]]
	});
	
	table.on('tool(equ-filelist-table)', function(obj){
		console.log(obj);
	    var data = obj.data;
	    filename = data.filename;
	    
	    //下载
	    if(obj.event === 'download'){
	    	var url = '/iot_equipment/maintenance/equipment/move/filedownload';
	    	url += "?positionNum="+equipNum+"&maintenanceTime="+repairTime+"&filename="+filename;
	    	console.log("url: "+url);
	    	
	    	$("#files-download a[lay-event='download']").attr("href",url);
	    }else if(obj.event === 'displayed'){
	    	$("#displayedshow").empty();
	    	var img = '';
	    	if(filename.lastIndexOf(".pdf") != -1){
	    		img += '<a class="media" href="/iot_equipment/picture1/'+equipNum+'/'+repairTime+'/'+filename+'" ></a>'
	    	}else if(filename.lastIndexOf(".jpeg") != -1 || filename.lastIndexOf(".jpg") != -1
	    			|| filename.lastIndexOf(".png") != -1 || filename.lastIndexOf(".gif") != -1){
	    		img += '<img alt="图片不存在" src="/iot_equipment/picture1/'+equipNum+'/'+repairTime+'/'+filename+'"  style="height: 400px">'
	    		//img += '<a class="media" href="/iot_equipment/picture1/'+equipNum+'/'+repairTime+'/'+filename+'" style="width: 100px;height: 100px"></a>'
	    	}else{
	    		layer.msg("此文件不能预览",{icon: 7, time:2000, offset: '150px'});
		    	return;
	    	}
	    	
	    	console.log(img);
	    	$("#displayedshow").append(img);
	    	$('a.media').media({width:'99%',height:400});  
	        //$('a.mediase').media({width:1100, height:900}); 
	    	layer.open({
   		    	title: '动设备检维修文件查看',
   		    	type: 1,
   		    	btn: ['关&nbsp;&nbsp;闭'],
   		    	closeBtn: 0,
   		    	btnAlign: 'c',
   		    	area: ['850px','510px'],
   		        content: $("#displayedshow"),
   		        yes: function(index, layero){
   		      
   		        	layer.close(index); //如果设定了yes回调，需进行手工关闭
   		        },
   		  		success: function(){
   		  		}
   		  });
	    }
	    
	})
	
	
	/**
	 * 监听每一行工具事件
	 */
	table.on('tool(equ-moving-table)', function(obj){
		console.log(obj);
	    var data = obj.data;
	    var tr = obj.tr;
	    
	    //下载
	    if(obj.event === 'download'){
	    	console.log("动设备检维修文件下载...");

	    	layer.open({
   		    	title: '动设备检维修文件下载',
   		    	type: 1,
   		    	id: 'fileDownload',
   		    	btn: ['关&nbsp;&nbsp;闭'],
   		    	closeBtn: 0,
   		    	btnAlign: 'c',
   		    	offset: '45px',
   		    	area: ['800px','500px'],
   		        content: $("#files-download"),
   		        yes: function(index, layero){
   		      
   		        	layer.close(index); //如果设定了yes回调，需进行手工关闭
   		        },
   		  		success: function(layero){
   		  			equipNum = data.positionNum;
   		  			repairTime = data.maintenanceTime;
   		  			
	   		  		fileTable.reload({
				 	    where: {
				 	    	positionNum: function(){
				 	    		return data.positionNum;
				 	    	},
				 	    	maintenanceTime: function(){
				   		  		return data.maintenanceTime;
				   		  	}
				 	    }
				 
				 	  });
   		  		}
   		  });
	    }
	    
	    //上传
	    if(obj.event === 'upload'){
	    	console.log("动设备检维修文件上传...");
	    	equipNum = data.positionNum;
	    	repairTime = data.maintenanceTime;
	    	layer.open({
   		    	title: '动设备检维修文件上传',
   		    	type: 1,
   		    	id: 'fileUpload',
   		    	btn: ['关&nbsp;&nbsp;闭'],
   		    	closeBtn: 0,
   		    	btnAlign: 'c',
   		    	offset: '45px',
   		    	area: ['800px','500px'],
   		        content: $("#files-upload"),
   		        yes: function(index, layero){
   		        	if(fileList != null){
   		        		for(index1 in fileList){
   		        			delete fileList[index1];
   		        			uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
   		        		}
   		        	}
   		        	$("#fileList").empty();
   		        	layer.close(index); //如果设定了yes回调，需进行手工关闭
   		        },
   		  		success: function(){
   		  			
   		  		}
   		  });
	    }
	    
	    //编辑
	    if(obj.event === 'edit'){
	    	console.log("动设备检维修信息编辑...");
	    	layer.open({
   		    	title: '动设备检维修信息编辑',
   		    	type: 1,
   		    	id: 'repairInfoEdit',
   		    	btn: ['确&nbsp;&nbsp;认','取&nbsp;&nbsp;消'],
   		    	closeBtn: 0,
   		    	btnAlign: 'c',
   		    	offset: '45px',
   		    	area: ['800px','85%'],
   		        content: $("#repair-info-edit"),
   		        yes: function(index, layero){
   		        	
   		        	//检修原因和检修内容非空判断
		        	if($.trim($("#repairReasonC").val()) == '' || $.trim($("#repairReasonC").val()) == null){
		        		layer.msg("检修原因不能为空",{icon: 7, time:2000, offset: '150px'});
		        		return;
		        	}
		        	
		        	if($.trim($("#repairContentC").val()) == '' || $.trim($("#repairContentC").val()) == null){
		        		layer.msg("检修内容不能为空",{icon: 7, time:2000, offset: '150px'});
		        		return;
		        	}
   		        	
		        	//检修人员非空判断
		        	if($.trim($("#repairPersonC").val()) == '' || $.trim($("#repairPersonC").val()) == null){
		        		layer.msg("检修人员不能为空",{icon: 7, time:2000, offset: '150px'});
		        		return;
		        	}
		        	
		        	//保存检维修信息
			       	var replacement = '';
			       	$("#repairInfoEdit td[data-field='content'] .layui-table-cell").each(function(index,element){
			       		if($.trim($(this).text()) != ''){
			       		   replacement += $.trim($(this).text()) + ':' + $.trim($("#repairInfoEdit td[data-field='number'] .layui-table-cell")[index].innerText) + ";";
			       		}
			       	})
		        	
		        	//同步请求保存更新
		        	$.ajax({
						url : '/iot_equipment/maintenance/equipment/move/update',
						async: false,
						type : 'post',
						data: {
							"mid": data.mid,
							"positionNum": data.positionNum,
							"equipName": data.equipName,
							"equipVersion": data.equipVersion,
							"maintenancePerson": $.trim($("#repairPersonC").val()).replace(/，/g, ','),
							"maintenanceReason": $("#repairReasonC").val(),
							"maintenanceContent": $("#repairContentC").val(),
							"specificationAndNumber": replacement.replace(/;$/g, ''),
							"comment": $("#repairCommentC").val()
						},
						dataType : 'json',
						success : function(res) {
							if(res.state == 0){
								layer.msg("修改成功",{icon: 1, time:2000, offset: '150px'});
								equInfoTable.reload({
							 	    page: {
							 		   curr: 1 //重新从第 1 页开始
							 	    }
							 	  });
							}else{
								layer.msg("修改失败",{icon: 2, time:2000, offset: '150px'});
							}
						},
						error : function() {
							layer.msg('请求失败，请联系管理员！！！', {icon: 2, time:8000, offset:'150px'});
						}
					});	
		        	
			       	$("#repairInfoEdit td[data-field='content'] .layui-table-cell").each(function(index,element){
   		        		$(this).text("");
						$("#repairInfoEdit td[data-field='number'] .layui-table-cell")[index].innerText = "";
					});
			       	
   		        	layer.close(index); //如果设定了yes回调，需进行手工关闭
   		        },
   		        btn2: function(index, layero){
   		        	$("#repairInfoEdit td[data-field='content'] .layui-table-cell").each(function(index,element){
   		        		$(this).text("");
						$("#repairInfoEdit td[data-field='number'] .layui-table-cell")[index].innerText = "";
					});
   		        },
   		  		success: function(){
	   		  		$("#positionNumC").val(data.positionNum);
					$("#serialNumC").val(data.equipVersion);
					$("#equipNameC").val(data.equipName);
					$("#repairCommentC").val(data.comment);
					$("#repairContentC").val(data.maintenanceContent);
					$("#repairReasonC").val(data.maintenanceReason);
					$("#repairPersonC").val(data.maintenancePerson);
					
					var arr = parseString(data.specificationAndNumber);
					$("#repairInfoEdit td[data-field='content'] .layui-table-cell").each(function(index,element){
						if(arr.length > index){
							$(this).text(arr[index].split(":")[0]);
							$("#repairInfoEdit td[data-field='number'] .layui-table-cell")[index].innerText = arr[index].split(":")[1];
						}
					})
   		  		}
   		  });
	    }
	    
	    //删除
	    if(obj.event === 'delete'){
	    	console.log("动设备检维修信息删除...");
	    	layer.confirm("确认是否删除？",function(){
	    		$.ajax({
					url : '/iot_equipment/maintenance/equipment/move/delete',
					type : 'post',
					data: {"mid": data.mid},
					dataType : 'json',
					success : function(res) {
						if(res.state == 0){
							layer.msg("删除成功",{icon: 1, time:2000, offset: '150px'});
							equInfoTable.reload({
						 	    page: {
						 		   curr: 1 //重新从第 1 页开始
						 	    }
						 	  });
						}else{
							layer.msg("删除失败",{icon: 2, time:2000, offset: '150px'});
						}
					},	
					error : function() {
						layer.msg('请求失败，请联系管理员！！！');
					}
				});	
	    	});
	    }
	    
	    //详情
	    if(obj.event === 'detail'){
	    	/**
	         * 动设备检维修信息查看弹窗
	         */
	    	console.log("动设备检维修信息详情...");
       	 	layer.open({
   		    	title: '动设备检维修信息详情',
   		    	type: 1,
   		    	id: 'repairInfoDetail',
   		    	btn: ['关&nbsp;&nbsp;闭'],
   		    	closeBtn: 0,
   		    	btnAlign: 'c',
   		    	offset: '45px',
   		    	area: ['800px','85%'],
   		        content: $("#repair-info-detail"),
   		        yes: function(index, layero){
   		        	$("#repairInfoDetail td[data-field='content']").each(function(index,element){
   		        		$(this).text("");
   		        		$("#repairInfoDetail td[data-field='number']")[index].innerText = "";
   		        		$(this).css({'width':'200px','height':'33px'});
						$("#repairInfoDetail td[data-field='number']").css({'width':'200px','height':'33px'});
   		        	})
   		 
   		        	layer.close(index); //如果设定了yes回调，需进行手工关闭
   		        },
   		  		success: function(){
	   		  		$("#positionNumS").val(data.positionNum);
					$("#serialNumS").val(data.equipVersion);
					$("#equipNameS").val(data.equipName);
					$("#repairCommentS").val(data.comment);
					$("#repairContentS").val(data.maintenanceContent);
					$("#repairReasonS").val(data.maintenanceReason);
					$("#repairPersonS").val(data.maintenancePerson);
					
					var arr = parseString(data.specificationAndNumber);
					$("#repairInfoDetail td[data-field='content']").each(function(index,element){
						if(arr.length > index){
							$(this).text(arr[index].split(":")[0]);
							$(this).css({'width':'300px'});
							$("#repairInfoDetail td[data-field='number']").css({'width':'200px'});
							$("#repairInfoDetail td[data-field='number']")[index].innerText = arr[index].split(":")[1];
						}
					})
   		  		}
   		  });
	    }
	});
	
	/**
	 * 解析更换备品备件型号规格及数量
	 */
	function parseString(str){
		var arr = "";
		if(str != null){
			arr = str.split(";");
		}
		
		return arr;
	}
	
	/**
	 * 监听条件查询提交事件
	 */
	form.on('submit(info-query)', function(data){
	  var field = data.field;
	  console.log('--------条件查询的条件为：');
	  console.log(field);
	  
	  equInfoTable.reload({
 	    page: {
 		   curr: 1 //重新从第 1 页开始
 	    }
	  	,where: {
	  		"equipName": $.trim(field.equipName),
	  		"startDate": $.trim(field.startDate),
	  		"endDate": $.trim(field.endDate),
	  		"positionNum": $.trim(field.positionNum)
	  	}
 	  });
	  checkedList.length = 0;
	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
	

	/**
	 * 监听导出按钮事件
	 */
	$("#info-export").click(function(){
	  console.log("-----开始动设备检维修信息导出-------");
	  
	  if(checkedList == null || checkedList == undefined || checkedList.length == 0){
		  console.log("-----没有选择导出内容-------");
		  return;
	  }
	  
	  var pn = checkedList[0].positionNum;
	  for(var i=0;i<checkedList.length;i++){
		  if(pn != checkedList[i].positionNum){
			  layer.msg("只能勾选相同设备位号的检维修记录",{icon: 7, time:3000, offset: '150px'});
			  return;
		  }
	  }
	  
	  var url = "/iot_equipment/maintenance/equipment/move/export?";
	  for(var i=0;i<checkedList.length;i++){
		  url += "mid="+checkedList[i].mid;
		  url += "&";
	  }
  	
	  url = url.replace(/&$/, "");
	  $("#info-export").attr("href", url);
	});
	
	
	/**
	* 监听复选框选择
	*/
	var checkedList = new Array();
	table.on('checkbox(equ-moving-table)', function(obj){
		//console.log(obj.checked); //当前是否选中状态
		//console.log(obj.data); //选中行的相关数据
		//console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
		
		//全选
		if(obj.checked == true && obj.type == "all"){
			checkedList.length = 0;
			checkedList = checkedList.concat(infoData);
		}
		//全不选
		if(obj.checked == false && obj.type == "all"){
			checkedList.length = 0;
		}
		//单选
		if(obj.checked == true && obj.type == "one"){
			checkedList.push(obj.data);
		}
		//单不选
		if(obj.checked == false && obj.type == "one"){
			checkedList.remove(obj.data);
		}
		
		console.log(checkedList); //选中行的相关数据
	});
	
	/**
     * 动设备检维修信息录入弹窗
     */
    $("#repair-info-btn").click(function(){
    	console.log("动设备检维修信息录入弹窗...");
    	 layer.open({
		    	title: '动设备检维修信息录入',
		    	type: 1,
		    	id: 'repairInfo',
		    	btn: ['确&nbsp;&nbsp;认','取&nbsp;&nbsp;消'],
		    	closeBtn: 0,
		    	btnAlign: 'c',
		    	offset: '45px',
		    	area: ['800px','85%'],
		        content: $("#repair-info-window"),
		        yes: function(index, layero){
		        	
		        	//检修原因和检修内容非空判断
		        	if($.trim($("#repairReason").val()) == '' || $.trim($("#repairReason").val()) == null){
		        		layer.msg("检修原因不能为空",{icon: 7, time:2000, offset: '150px'});
		        		return;
		        	}
		        	
		        	if($.trim($("#repairContent").val()) == '' || $.trim($("#repairContent").val()) == null){
		        		layer.msg("检修内容不能为空",{icon: 7, time:2000, offset: '150px'});
		        		return;
		        	}
		        	
		        	//检修人员非空判断
		        	if($.trim($("#repairPerson").val()) == '' || $.trim($("#repairPerson").val()) == null){
		        		layer.msg("检修人员不能为空",{icon: 7, time:2000, offset: '150px'});
		        		return;
		        	}
		        	
		        	//检修时间非空判断
		        	if($.trim($("#repairTime").val()) == '' || $.trim($("#repairTime").val()) == null){
		        		layer.msg("检修时间不能为空",{icon: 7, time:2000, offset: '150px'});
		        		return;
		        	}
		        	
		        	//保存检维修信息
		           	var replacement = '';
		           	$("#repairInfo td[data-field='content'] .layui-table-cell").each(function(index,element){
		           		if($.trim($(this).text()) != ''){
		           		   replacement += $.trim($(this).text()) + ':' + $.trim($("#repairInfo td[data-field='number'] .layui-table-cell")[index].innerText) + ";";
		           		}
		           	})
		    		
		    		//同步请求保存更新
		        	$.ajax({
		    			url : '/iot_equipment/maintenance/equipment/move/add',
		    			async: false,
		    			type : 'post',
		    			data: {
		    				"positionNum": $("#positionNum").val(),  //$("#positionNum").val()
		    				"equipName": $("#equipName").val(),      //$("#equipName").val()
		    				"equipVersion": $("#serialNum").val(),   //$("#serialNum").val()
		    				"maintenancePerson": $.trim($("#repairPerson").val()).replace(/，/g, ','),
		    				"maintenanceReason": $("#repairReason").val(),
		    				"maintenanceTime": $("#repairTime").val(),
		    				"maintenanceContent": $("#repairContent").val(),
		    				"specificationAndNumber": replacement.replace(/;$/g, ''),
		    				"comment": $("#repairComment").val()
		    			},
		    			dataType : 'json',
		    			success : function(res) {
		    				if(res.state == 0){
		    					layer.msg("添加检维修信息成功",{icon: 1, time:2000, offset: '150px'});
		    					equInfoTable.reload({
		    				 	    page: {
		    				 		   curr: 1 //重新从第 1 页开始
		    				 	    }
		    				 	  });
		    		        	
		    		        	layer.close(index); //如果设定了yes回调，需进行手工关闭
		    				}else{
		    					layer.msg("添加检维修信息失败",{icon: 2, time:2000, offset: '150px'});
		    				}
		    			},
		    			error : function() {
		    				layer.msg('请求失败，请联系管理员！！！', {icon: 2, time:8000, offset:'150px'});
		    			}
		    		});	
		        },
		  		success: function(){
		  		
		  		}
		  });
    })
    
    /**
     * 人员组织树弹窗
     */
    $("#chose-user-btn").click(function(){
    	console.log("人员组织树弹窗...");
    	//弹出层
		layer.open({
			type: 1
			,offset: '45px'
			,area: ['300px','400px;']
			,id: 'user_organization' //防止重复弹出
			,content: $("#task_tree")
			,btn: ['确认',"取消"]
			,btnAlign: 'c' //按钮居中
			,yes: function(index, layero){
				//确认按钮的回调函数
				var currentPerson = $.trim($("#repairPerson").val());
				if(assignUsers != null && assignUsers.length != 0){
					if(currentPerson.match(/[,，]$/) == null && currentPerson != ""){
						currentPerson += ",";
					}
					$("#repairPerson").val(currentPerson+assignUsers.toString());
				}
				
				layer.close(index);
			}
			,success:function(){
				assignUsers.length = 0;
				//同步请求人员组织树数据
				$.ajax({
					url : treeUrl,
					type : 'post',
					dataType : 'json',
					success : function(res) {
						if(res != null && res.code == 0){
							var data = buildTree(res.data);
							//单选框
				  			tree.render({
				  				elem: '#task_tree'
				  				,data: data
				  				,showCheckbox: true
				  				,oncheck:function(obj){
				  					parseTree(obj);
				  				}
				  			})
						}
					},
					error : function() {
						layer.msg('请求失败，请联系管理员！！！', {icon: 2, time:8000, offset:'150px'});
					}
				});	
		    	
				// 人名前面 显示人形图标
				$("i.layui-icon-file").addClass("layui-icon-user");
			}
		});

    })
    
    /**
	 * 解析树型结构,获取选中人员信息
	 */
    var assignUsers = [];
	function parseTree(obj){
		 console.log(obj.data); //得到当前点击的节点数据
		 var data = obj.data;
		 //选中就添加人员
		 if(obj.checked){
			 getUser(data);
		 }else{
			 //去掉选中就删除人员
			 removeUser(data);
		 }
	}
	
	function getUser(data){
		if(data.children == null || data.children == undefined || data.children.length == 0){
			assignUsers.push(data.label);
		 }else{
			for(var i=0;i<data.children.length;i++){
				getUser(data.children[i]);
			}
		 }
	}
	
	function removeUser(data){
		if(data.children == null || data.children == undefined || data.children.length == 0){
			for(var i=0;i<assignUsers.length;i++){
				if(assignUsers[i] == data.label){
					assignUsers.splice(i,1);
				}
			}
	    }else{
	    	for(var i=0;i<data.children.length;i++){
	    		removeUser(data.children[i]);
			}
	    }
	}
    
	/**
	 * 更换备品备件表格
	 */
	table.render({
	    elem: '#replacement'
	    ,cols: [[ //标题栏
	      {field:'content', title:'备件型号规格', width:300, edit: 'text',align:'center'}
	      ,{field:'number', title:'备件数量', width:200, edit: 'text', align:'center'}
	    ]]
	    ,data: [{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""}]
	  });
	
	
	table.render({
	    elem: '#replacement-detail'
	    ,cols: [[ //标题栏
	      {field:'content', title:'备件型号规格', width:300, align:'center'}
	      ,{field:'number', title:'备件数量', width:200, align:'center'}
	    ]]
	    ,data: [{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""}]
	  });
	
	table.render({
	    elem: '#replacement-edit'
	    ,cols: [[ //标题栏
	      {field:'content', title:'备件型号规格', width:300, edit: 'text', align:'center'}
	      ,{field:'number', title:'备件数量', width:200, edit: 'text', align:'center'}
	    ]]
	    ,data: [{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""},
	    		{"content": "","number": ""}]
	  });
	
	/**
	 * 从设备台账中定位设备
	 */
	$("#location-btn").click(function(){
		//显示设备定位弹窗
		layer.open({
			type: 2,
			id:"locationEquipment",
			title: '设备定位',
			content: './equipment-management-location.html',
			offset: ['50px','15%'],
	    	area: ['78%','82%'],
			btn: ['添&nbsp;&nbsp;加', '取&nbsp;&nbsp;消'],
			btnAlign: 'c', //按钮居中对齐
			yes: function(index, layero){
				//获取iframe窗口的body对象
	        	var body = layer.getChildFrame('body', index);
	        	//找到body对象下被选中的设备位号值
	        	var positionNum = body.find(".layui-table-click td[data-field='equPositionNum']").find("div").text();
	        	var equipName = body.find(".layui-table-click td[data-field='equName']").find("div").text();
	        	var equModel = body.find(".layui-table-click td[data-field='equModel']").find("div").text();

	        	$("#positionNum").val(positionNum);
	        	$("#equipName").val(equipName);
	        	$("#serialNum").val(equModel);
	        	layer.close(index); //如果设定了yes回调，需进行手工关闭
			}
		})	
	})
	
	
	//多文件列表示例
	var demoListView = $('#fileList')
	,uploadListIns = upload.render({
	      elem: '#fileList-btn'
	      ,url: '/iot_equipment/maintenance/equipment/move/fileupload'
	      ,accept: 'file'
	      ,multiple: true
	      ,data: {
	    	  positionNum: function(){
	    	    return equipNum;
	    	  },
	    	  maintenanceTime: function(){
		    	    return repairTime;
	    	  }
	    	}
	      ,auto: false
	      ,bindAction: '#fileupload-btn'
	      ,choose: function(obj){   
	        var files = fileList = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
	        
	        //读取本地文件
	        obj.preview(function(index, file, result){
	          var tr = $(['<tr id="upload-'+ index +'">'
	            ,'<td>'+ file.name +'</td>'
	            ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
	            ,'<td>等待上传</td>'
	            ,'<td>'
	              ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
	              ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
	            ,'</td>'
	          ,'</tr>'].join(''));
	          
	          //单个重传
	          tr.find('.demo-reload').on('click', function(){
	            obj.upload(index, file);
	          });
	          
	          //删除
	          tr.find('.demo-delete').on('click', function(){
	            delete files[index]; //删除对应的文件
	            tr.remove();
	            uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
	          });
	          
	          demoListView.append(tr);
	        });
	      }
	      ,done: function(res, index, upload){
	        if(res.state == 0){ //上传成功
	          var tr = demoListView.find('tr#upload-'+ index)
	          ,tds = tr.children();
	          tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
	          tds.eq(3).html(''); //清空操作

	          return delete this.files[index]; //删除文件队列已经上传成功的文件
	        }
	        this.error(index, upload);
	      }
	      ,error: function(index, upload){
	        var tr = demoListView.find('tr#upload-'+ index)
	        ,tds = tr.children();
	        tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
	        tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
	      }
	});
	
	Array.prototype.indexOf = function(val) { 
		for (var i = 0; i < this.length; i++) { 
		if (this[i].mid == val.mid) return i; 
		} 
		return -1; 
	};
	
	Array.prototype.remove = function(val) { 
		var index = this.indexOf(val); 
		if (index > -1) { 
		this.splice(index, 1); 
		} 
	};
})