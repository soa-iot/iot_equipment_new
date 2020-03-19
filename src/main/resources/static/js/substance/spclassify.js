//导入 用layui upload插件layui.use([ "element", "laypage", "layer", "upload"], function() {
layui.use(['layer', 'tree','table'],
	function() {
		var layer = layui.layer,table = layui.table,
			tree = layui.tree,f_id='';

		//设备分类数据数据获取
		getspClassifyTree();
		//设备基本信息列表数据获取
		getEqOrSpData();


//http://192.168.18.211:8080/iotapp-admin/html/equ_monitor.html

		function getspClassifyTree() {
			
		$.get(api.sparePartType.getSparepartsClassInfoAsTree, null, function(results) {
			console.log(results)
		// if(results.code==0){
			setTree(results.data);
			// }
		});
		}

function getEqOrSpData(){
	var query_data={};
	query_data.sparepartTypeId=f_id;
		setEqOrSpList(query_data);
}

function setEqOrSpList(query_data){
	var cols=[[
	      {type: 'checkbox', fixed: 'left'}
	      ,{field:'spEncoding', title:'备件编码', fixed: 'left'}
	      ,{field:'spName', title:'备件名称'}
	      ,{field:'type', title:'类别'}
	      ,{field:'specification', title:'型号规格'}
	      ,{field:'spInventory', title:'当前库存数量'}
		  ,{field:'prewarningVal', title:'合理库存数量'}
		  ,{field:'brand', title:'品牌'}
		  ,{field:'manufactureFactory', title:'生产厂家'}
		  ,{field:'productionDate', title:'生产日期'}
		  ,{field:'unit', title:'单位'}
		  ,{field:'unitCost', title:'价格'}
		  ,{field:'labelCode', title:'标签码'}
		  ,{field:'remark', title:'备注'}
	    ]];
	table.render({
					elem : '#equipment_list_table',
					url : api.sparepartsLedger.getSparePartsInfo,
					height : TABLE_H-130,
					title : '备件台账',
					method : 'post',
					toolbar: '#toolbar', //开启头部工具栏，并为其绑定左侧模板
					contentType : 'application/json',
					where : query_data,
					cols : cols,
					page : true,
					limits : [30, 60, 90, 120, 150],
					limit : 30,
					parseData : function(res) {
						console.log(res.data);
						}
				});
}


		//设置显示设备分类树
		function setTree(data) {
			// 设备分类树
			tree.render({
						elem : '#spClassify',
						data : data,
						showCheckbox : false // 是否显示复选框
						,
						id : 'id',
						isJump : true // 是否允许点击节点时弹出新窗口跳转
						,edit: ['add', 'update', 'del'] //操作节点的图标
						,operate: function(obj){
						    var type = obj.type; //得到操作类型：add、edit、del
						    var data = obj.data; //得到当前节点的数据
						    var elem = obj.elem; //得到当前节点元素
						    console.log(data);
							console.log(elem);
						    //Ajax 操作
						    var id = data.id; //得到节点索引
						    if(type === 'add'){ //增加节点
							//添加节点
							layer.prompt({title: '请输入分类名称', formType: 3}, function(pass, index){
							  var query_data={};
							  query_data.classifyName=pass;
							  query_data.classifyValue=pass;
							  query_data.pId=id;
							  addClassf(query_data);
							});
						    } else if(type === 'update'){ //修改节点
							layer.prompt({title: '请输入分类名称', formType: 3}, function(pass, index){
							  var query_data={};
							  query_data.classifyName=pass;
							  query_data.classifyValue=pass;
							  query_data.cId=id;
							  updateClassf(query_data);
							});
						    } else if(type === 'del'){ //删除节点
							
							 console.log();
							if(data.children.length>0){
								layer.alert('改节点下有子节点，不可删除', {icon: 5});  
									getspClassifyTree();
								return false;
							}else{
								var query_data={};
								 query_data.cId=id;
								// delClassf(query_data);
							}
						    };
							}
						,click : function(obj) {
							var data = obj.data; // 获取当前点击的节点数据
							console.log(data);
						var query_data={};
							f_id=data.id;
						query_data.sparepartTypeId=data.id;
							setEqOrSpList(query_data);
						}
					});
		}
		
		//头工具栏事件
		 table.on('toolbar(equipment_list_table)', function(obj){
		   var checkStatus = table.checkStatus(obj.config.id);
		   switch(obj.event){
		     case 'getCheckData':
					  layer.open({
					    type: 2,
						title:'备件添加',
					    skin: 'layui-layer-rim', //加上边框
					    area: ['50%', '80%'], //宽高
					    content: 'addspList.html?f_id='+f_id
					  });
		     break;
		     case 'isAll':
			  var data = checkStatus.data;
			  console.log(data)
					  //删除
					  if(f_id==''){
						  layer.alert('请选择分类', {icon: 5});  
						  }else if(data.length<=0){
							  layer.alert('请选择需要删除的备件', {icon: 5});  
						  }else{
							  var a=[];
							  $.each(data,function(i,t){
								  var query_data={};
								  query_data.cId=f_id;
								  query_data.spId=t.spId;
								  a.push(query_data);
							  });
							deletetable(a);
						  }
		     break;
		     
		     //自定义头工具栏右侧图标 - 提示
		     case 'LAYTABLE_TIPS':
		       layer.alert('这是工具栏右侧自定义的一个图标按钮');
		     break;
		   };
		 });
		
		function deletetable(query_data){
			$.ajax({
							url : api.sparePartType.delClassifySpRelation,
							type : 'delete',
							data : JSON.stringify(query_data),
							dataType : 'json',
							contentType : 'application/json',
							success : function(res) {
								console.log(res)
								if (res.code == 0) {
									layer.confirm('删除成功？', {
									  btn: ['确定'] //按钮
									}, function(){
										getEqOrSpData();
									layer.closeAll(); 
									});
								} else {
									layer.msg('数据保存失败，请联系管理员！！！', {
												icon : 2
											});
								}
							},
							error : function() {
								layer.msg('数据保存失败，请联系管理员！！！', {
											icon : 2
										});
							}
						});
		}
		
		//添加分类
		$(document).on('click',"#addClass",function(){
			//prompt层
			
			layer.prompt({title: '请输入分类名称', formType: 3}, function(pass, index){
			  var query_data={};
			  query_data.classifyName=pass;
			  query_data.classifyValue=pass;
			  query_data.pId=0;
			  addClassf(query_data);
			});
		 });
		 //添加备件分类
	function addClassf(query_data){
		$.ajax({
						url : api.sparePartType.addSparepartsClassInfo,
						type : 'post',
						data : JSON.stringify([query_data]),
						dataType : 'json',
						contentType : 'application/json',
						success : function(res) {
							console.log(res)
							if (res.code == 0) {
								layer.confirm('添加成功？', {
								  btn: ['确定'] //按钮
								}, function(){
									getspClassifyTree();
								layer.closeAll(); 
								});
							} else {
								layer.msg('数据保存失败，请联系管理员！！！', {
											icon : 2
										});
							}
						},
						error : function() {
							layer.msg('数据保存失败，请联系管理员！！！', {
										icon : 2
									});
						}
					});
	}	 
		 
		 //修改备件分类信息
		 function updateClassf(query_data){
		 	$.ajax({
		 					url : api.sparePartType.updateSparepartsClassInfo,
		 					type : 'put',
		 					data : JSON.stringify(query_data),
		 					dataType : 'json',
		 					contentType : 'application/json',
		 					success : function(res) {
		 						console.log(res)
		 						if (res.code == 0) {
		 							layer.confirm('删除成功？', {
		 							  btn: ['确定'] //按钮
		 							}, function(){
		 								getspClassifyTree();
		 							layer.closeAll(); 
		 							});
		 						} else {
		 							layer.msg('数据保存失败，请联系管理员！！！', {
		 										icon : 2
		 									});
		 						}
		 					},
		 					error : function() {
		 						layer.msg('数据保存失败，请联系管理员！！！', {
		 									icon : 2
		 								});
		 					}
		 				});
		 }	 
		 	 
			 //添加备件分类
			 function delClassf(query_data){
			 	$.ajax({
			 					url : api.sparePartType.delSparepartsClassInfo,
			 					type : 'delete',
			 					data : JSON.stringify([query_data]),
			 					dataType : 'json',
			 					contentType : 'application/json',
			 					success : function(res) {
			 						console.log(res)
			 						if (res.code == 0) {
			 							layer.confirm('添加成功？', {
			 							  btn: ['确定'] //按钮
			 							}, function(){
			 								getspClassifyTree();
			 							layer.closeAll(); 
			 							});
			 						} else {
			 							layer.msg('数据保存失败，请联系管理员！！！', {
			 										icon : 2
			 									});
			 						}
			 					},
			 					error : function() {
			 						layer.msg('数据保存失败，请联系管理员！！！', {
			 									icon : 2
			 								});
			 					}
			 				});
			 }	 
			 	 
		 
	});


