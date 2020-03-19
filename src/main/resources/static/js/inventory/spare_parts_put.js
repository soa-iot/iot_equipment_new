layui.use(['form','layer', 'tree','table'],
	function() {
		var form = layui.form,layer = layui.layer,table = layui.table,
			tree = layui.tree,requestCode='',type='';
		//入库登记单号
		var request = 'RQ' + Date.now(); 
		$('#requestCode').val(request);
		
		//------提交事件-------------------------------------------
		
		$(document).on('click','#search_button_search',function(){
					  //判断申请人
					  var proposer=$('#proposer').val();
					 
					  if(proposer.length<=0){
					  	layer.alert('请填写入库人', {icon: 5});  
					  }else if(type==''){
						 layer.alert('请选择入库类型', {icon: 5});   
						  }else{
						    var query_data={};
						    var spRegister={};
							  spRegister.registerCode=request;
							  spRegister.registerType='入库';
							   spRegister.registerPeople=proposer;
							   var spRecords=[];
						  if(type==0){
							  //普通入库 
							   spRegister.explain='普通入库';
							   
							   var dataLista=layui.table.cache["equipment_list_table"];
							   console.log(dataLista.length)
							   
							   if(dataLista.length<=0){
								   layer.alert('请添加备件信息', {icon: 5});   
							   }else{
								  var bool=true;
								   $.each(dataLista,function(i,t){
								   				  var quantity=t.quantity;
								   				  console.log(quantity)
								   				  if(quantity==0){
								   					 bool=false; 
								   				  }
								   });
								   if(bool){
								  								  // spRecords.push(dataLista);
								  								  query_data.spRecords=dataLista;
								  								  query_data.operateType='normalIn';
								  								  query_data.spRegister=spRegister;
																  
																  
								  								  console.log(query_data);
								  								   save(query_data);  
								   }else{
								  layer.alert('请输入申请数量', {icon: 5});   
								   } 
							   }
							   
						  }else if(type==1){
							  //采购入库 
							   spRegister.explain='采购入库';
							   spRegister.requestCode=requestCode;
							   query_data.operateType='in';
							   query_data.spRegister=spRegister;
							    save(query_data); 
							  }
						
							  
						
					  }
		});
		
		function save(query_data){
					  $.ajax({
					  				url : api.sparepartOutIn.doSparepartOutIn,
					  				type : 'post',
					  				data : JSON.stringify(query_data),
					  				dataType : 'json',
					  				contentType : 'application/json',
					  				success : function(res) {
					  					if (res.code == 0) {
					  						layer.confirm('添加成功？', {
					  						  btn: ['确定'] //按钮
					  						}, function(){
													window.location.reload();//刷新当前页面.
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
		
		//------数据加载--------------------------------------
		loading_spare_parts_list();//加载备件列表
		set_the_purchase_request_form();//设置采购申请单
		lode_the_purchase_request_form();//加载采购申请单列表
		
		function set_the_purchase_request_form(query_data){
				$.get(api.sparepartOutIn.getSpRecord, query_data, function(results) {
					console.log(results)
					var data=results.data;
					setup_register(data);
					});
		}
			
		function loading_spare_parts_list(){
			var query_data={};
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
							elem : '#spare_parts_table',
							url : api.sparepartsLedger.getSparePartsInfo,
							// height : 400,
							title : '备件台账',
							method : 'post',
							toolbar: '#sprequisitions', //开启头部工具栏，并为其绑定左侧模板
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
			
			
			
	function setup_register(data){
		var cols=[[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'spName', title:'备件名称', fixed: 'left'}
		      ,{field:'spInventory', title:'当前库存'}
		      ,{field:'quantity', title:'申请数量'}
		      ,{field:'unitCost', title:'备件单价'}
		      ,{field:'unit', title:'备件单位'}
		    ]];
					table.render({
									elem : '#equipment_list_table',
									data : data,
									height : TABLE_H-260,
									title : '领用申请单',
									method : 'post',
									toolbar: '#the_spare_parts_to_register', //开启头部工具栏，并为其绑定左侧模板
									contentType : 'application/json',
									cols : cols,
									page : true,
									limits : [30, 60, 90, 120, 150],
									limit : 30,
									parseData : function(res) {
										console.log(res.data);
										}
								});
	}
//普通入库选择的备件
function setup_register_common(data){
		var cols=[[
		      {type: 'checkbox', fixed: 'left'}
		      ,{field:'spName', title:'备件名称', fixed: 'left'}
		      ,{field:'spInventory', title:'当前库存'}
		      ,{field:'quantity', title:'申请数量',edit: 'text'}
		      ,{field:'unitCost', title:'备件单价'}
		      ,{field:'unit', title:'备件单位'}
		    ]];
					table.render({
									elem : '#equipment_list_table',
									data : data,
									height : TABLE_H-260,
									title : '领用申请单',
									method : 'post',
									toolbar: '#the_spare_parts_to_register', //开启头部工具栏，并为其绑定左侧模板
									contentType : 'application/json',
									cols : cols,
									page : true,
									limits : [30, 60, 90, 120, 150],
									limit : 30,
									parseData : function(res) {
										console.log(res.data);
										}
								});
	}
	
		function lode_the_purchase_request_form(){
			var query_data={};
			var spPutIn={};
			spPutIn.type='采购';
			query_data.spPutIn=spPutIn;
			var cols=[[
			      {type:'radio'}
				 ,{field:'requestCode', title:'申请单号', width:180,}
			      ,{field:'proposer', title:'申请人', width:80,}
			      ,{field:'applicationDate', title:'申请日期', width:180,}
			      ,{field:'type', title:'申请状态', width:180,}
				  ,{field:'passDate', title:'审核通过日期', width:180,}
			    ]];
			table.render({
							elem : '#purchase_table',
							url : api.sparepartOutIn.getSparepartApply,
							// height : 400,
							title : '采购申请单',
							method : 'post',
							toolbar: '#requisitions', //开启头部工具栏，并为其绑定左侧模板
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
		//---------------------------------------------------------
//采购单选择---头工具栏事件
		  table.on('toolbar(purchase_table1)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getChe':
			    var data = checkStatus.data;
				if(data.length==0){
			layer.alert('请选择领用申请单', {icon: 5});
				}else{
					 requestCode=data[0].requestCode;
					 $('#selLyd').html(requestCode);
					 var query_datas={};
					 query_datas.requestCode=requestCode;
					  set_the_purchase_request_form(query_datas)
					  layer.closeAll(); 
				}
		      break;
		      //自定义头工具栏右侧图标 - 提示
		      case 'LAYTABLE_TIPS':
		        layer.alert('这是工具栏右侧自定义的一个图标按钮');
		      break;
		    };
		  }); 
		  
			// //备件选择---头工具栏事件
		  		  table.on('toolbar(sp)', function(obj){
		  		    var checkStatus = table.checkStatus(obj.config.id);
		  		    switch(obj.event){
		  		      case 'getCheckData':
					  if(type==''){
						   layer.alert('请选择入库类型', {icon: 5});
						  }else if(type==1){
						  layer.alert('采购入库不可以操作', {icon: 5});
						  }else{
					   parent.layer.open({
					     type: 1,
					   	title:'备件添加',
					     skin: 'layui-layer-rim', //加上边框
					     area: ['50%', '400px'], //宽高
					   		 content:$('#spare_parts_to_register')
					   });
					   }
		  		      break;
		  		      //自定义头工具栏右侧图标 - 提示
		  		      case 'LAYTABLE_TIPS':
		  		        layer.alert('这是工具栏右侧自定义的一个图标按钮');
		  		      break;
		  		    };
		  		  }); 
				   //备件列表---头工具栏事件
				  	  table.on('toolbar(spare_parts_table)', function(obj){
				  	    var checkStatus = table.checkStatus(obj.config.id);
				  	    switch(obj.event){
				  	      case 'getChe':
						  var data = checkStatus.data;
						  var query_datas=[];
						  $.each(data,function(i,e){
						  	var query_data={};
						  	query_data.requestCode=request;
						  	query_data.type='出库';
						  	query_data.spId=e.spId;
						  	query_data.spName=e.spName;
						  	query_data.quantity=0;
						  	query_data.spInventory=e.spInventory;
						  	query_data.unitCost=e.unitCost;
						  	query_data.unit=e.unit;
						  	query_datas.push(query_data);
						  });		
						  setup_register_common(query_datas);
						   layer.closeAll(); 
				  	      break;
				  	      //自定义头工具栏右侧图标 - 提示
				  	      case 'LAYTABLE_TIPS':
				  	        layer.alert('这是工具栏右侧自定义的一个图标按钮');
				  	      break;
				  	    };
				  	  }); 
	//----------------------------------------------------------	  
		  //选择采购申请单按钮事件
		  $(document).on('click',"#selLyd",function(){
		  		parent.layer.open({
		  		  type: 1,
		  			title:'备件添加',
		  		  skin: 'layui-layer-rim', //加上边框
		  		  area: ['70%', '400px'], //宽高
		  		  // content: 'addspList.html?request='+request,
		  				 content:$('#purchase_requisitions')
		  		});
		   });
		  
		  //类型选择事件
		   form.on('select(aihao)', function (data) {
			    type=$("select[name=interest").val();
			   if(type==0){
				      $("#selLydDiv").hide();//表示为display:none;
			   }else{
				    $("#selLydDiv").show();//表示为display：block，
			   }
				   form.render('select');
		      	});
		  
	});


