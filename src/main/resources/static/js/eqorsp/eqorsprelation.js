layui.use(['element','layer', 'tree','table','upload'],
	function() {
		var layer = layui.layer,table = layui.table,
			element = layui.element,tree = layui.tree,eqId='',upload = layui.upload;
			//excal导入
	upload.render({
		elem : '#importData' // 绑定元素
		,
		url :api.SparepartsExcel.importEqOrSpRe
		,
		data : {},
		accept : 'file',
		exts : 'xls|xlsx|xlsm|xlt|xltx|xltm',
		field : 'exportFile',
		choose : function() {
			upload_msg = layer.load();
		},
		done : function(res) {
			// 上传完毕回调
			layer.close(upload_msg);
			if (res.code == 0) {
				// 导入数据成功
				layer.msg(res.data);
				load_table(table, {
							"equTypeId" : equ_type_info.equTypeId
						});
			} else {
				// 导入数据失败
				layer.msg(res.data);
			}
		},
		error : function() {
			// 请求异常回调
			layer.close(upload_msg);
			layer.msg(res.data);
		}
	});  

		//设备分类数据数据获取
		getEqclassifyTree();
		//设备基本信息列表数据获取
		getEqList();
		getEqOrSpData({eqId:'111'});

		function getEqclassifyTree() {
			$.get(api.equipmentLedger.getSparepartsClassInfoAsTree, null, function(results) {
				console.log(results)
			// if(results.code==0){
				setTree(results.data);
				// }
			});
		}
		
function getEqList(){
	var query_data={};
		setEqTable(query_data);
}

function getEqOrSpData(query_data){
	$.get(api.sparepatsManager.getEquSpareRe, query_data, function(results) {
		setEqOrSpList(results.data);
	});
}

 //查询
  $(document).on('click','#search_button_search',function(){
  			  //判断申请人
  			  var query_data={};
  			  var equipmentCommonInfo={};
			  
  			  equipmentCommonInfo.equName=$('#equName').val();
  			  equipmentCommonInfo.equPositionNum=$('#equPositionNum').val();
			  
  			  query_data.equipmentCommonInfo=equipmentCommonInfo;
			  console.log(query_data)
  			  setEqTable(query_data);
  });
  


function setEqOrSpList(data){
	var cols=[[
	      {type: 'checkbox', fixed: 'left'}
	      ,{type: 'numbers',fixed: 'left', title: '序号'}
	      ,{field:'eqFieid', title:'字段'}
	      ,{field:'remark', title:'类型'}
	      ,{field:'costomValue', title:'值'}
	    ]];
		console.log(data)
	table.render({
					elem : '#eqorspList',
					data : data,
					height : TABLE_H/2-80,
					toolbar : '#toolbarDemo',
					cols : cols,
					page : true,
					loading : true,
					limits : [30, 60, 90, 120, 150],
					limit : 30,
					parseData : function(res) {
						console.log(res.data);
						}
				});
}

//加载设备列表信息
function setEqTable(query_data){
	console.log(query_data)
	var cols=[[
	      {field:'equName', title:'设备名称', fixed: 'left'}
	      ,{field:'equModel', title:'设备型号'}
	      ,{field:'equPositionNum', title:'设备位号'}
	      ,{field:'equManufacturer', title:'设备类型'}
	      ,{field:'equStatus', title:'使用情况'}
	    ]];
	table.render({
					elem : '#eqsp_list_table',
					url : api.sparepatsManager.getEquInfo,
					height : TABLE_H-130,
					method : 'post',
					contentType : 'application/json',
					where : query_data,
					cols : cols,
					page : true,
					loading : true,
					limits : [30, 60, 90, 120, 150],
					limit : 30,
					parseData : function(res) {
						console.log(res.data);
						}
				});
}
var tmpobj=null;
  //监听行单击事件（双击事件为：rowDouble）
  table.on('row(eqsp_list_table)', function(obj){
	 if (tmpobj!=null){
	 tmpobj.tr[0].style='background-color: #ffffff;';
	 if (tmpobj.tr[1]){
	 tmpobj.tr[1].style='background-color: #ffffff;';
	 }
	 }
	 tmpobj=obj;
	 obj.tr[0].style='background-color: #00801c61;';
	 if (obj.tr[1]){
	 obj.tr[1].style='background-color: #00801c61;';
	 }
	var data= obj.data;
	eqId=data.equId;
	
	var query_data={eqId:eqId};
		getEqOrSpData(query_data);

		layer.open({
		      type    : 1,
		      offset  : 'r',
		      area    : ['50%', '100%'],
		      title   : '采购备件详情',
		      shade   : 0,
		      anim   : -1,
		      skin:'layer-anim-07',
		      move    : false,
		      content:$('#openProductBox'),
		      cancel  : function (index) {
		        var $layero = $('#layui-layer' + index);
		        $layero.animate({
		          left : $layero.offset().left + $layero.width()
		        }, 300, function () {
		          layer.close(index);
		        });
		        return false;
		      }
		    });
  });
		//设置显示设备分类树
		function setTree(data) {
			// 设备分类树
			tree.render({
						elem : '#equipment_type',
						data : data,
						showCheckbox : false // 是否显示复选框
						,
						id : 'id',
						isJump : true // 是否允许点击节点时弹出新窗口跳转
						,
						click : function(obj) {
							var data = obj.data; // 获取当前点击的节点数据
							console.log(data.id);
							var query_data={};
							query_data.equTypeId =data.id;
								setEqTable(query_data);
						}
					});
		}
		
		
		  //头工具栏事件
		  table.on('toolbar(equipment_list_table)', function(obj){
			  console.log(obj)
		    var checkStatus = table.checkStatus(obj.config.id);
			console.log(checkStatus)
		    switch(obj.event){
		      case 'getCheckData':
			  var Fieid=$('#modules option:selected').val();
			   var FieidTxt=$('#modules option:selected').text();
			  console.log("Fieid==>"+Fieid)
			  if(eqId==''){
			  				  layer.alert('请先选择设备', {icon: 5});
			  }else  if(Fieid==''){
				layer.alert('请先选择字段', {icon: 5});
				}else{
					
					$('#add').attr("checked", true); //注意这里使用的是attr()
					layui.form.render(); //重新渲染显示效果
			  var query_data={
			  	eqFieid:Fieid,
			  	type:'1',
			  	costomValue:FieidTxt,
			  	remark:'表字段',
			  	remark1:'',
			  	eqId:eqId
			  }; 
			    addData(query_data);
				}
		      break;
		      case 'getCheckLength':
			  //删除
			   var data = checkStatus.data;
			   console.log(data)
			  deletetable(data)
		      break;
		      case 'isAll':
			  console.log(eqId)
			  if(eqId==''){
				  layer.alert('请先选择设备', {icon: 5});
			  }else{
				  layer.prompt({title: '请输入自定义值', formType: 3}, function(pass, index){
				  var query_data={
				  	eqFieid:'',
				  	type:'0',
				  	costomValue:pass,
				  	remark:'自定义',
				  	remark1:'',
				  	eqId:eqId
				  }; 
				    addData(query_data);
				  });
			  }
		      break;
		    };
		  });
		
		//添加关系
		function addData(query_data){
			$.ajax({
							url : api.sparepatsManager.addEquSpareRe,
							type : 'post',
							data : JSON.stringify([query_data]),
							dataType : 'json',
							contentType : 'application/json',
							success : function(res) {
								if (res.code == 0) {
									var a=layer.confirm('添加成功？', {
									  btn: ['确定'] //按钮
									}, function(){
									var query_data={eqId:eqId};
										getEqOrSpData(query_data);
										layer.close(a)
										// layer.closeAll(); 
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
		
		//数据删除
		function deletetable(query_data){
			$.ajax({
							url : api.sparepatsManager.delEquSpareRe,
							type : 'delete',
							data : JSON.stringify(query_data),
							dataType : 'json',
							contentType : 'application/json',
							success : function(res) {
								console.log(res)
								if (res.code == 0) {
									var a=layer.confirm('删除成功？', {
									  btn: ['确定'] //按钮
									}, function(){
									var query_data={eqId:eqId};
										getEqOrSpData(query_data);
										layer.close(a)
										// layer.closeAll(); 
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


