layui.use(['element','layer', 'table','upload'],
	function() {
		var layer = layui.layer,element = layui.element,table = layui.table,upload = layui.upload;
		upload.render({
				elem : '#importData' // 绑定元素
				,
				url :api.SparepartsExcel.importSparepart
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
						getSpList();
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
		
		
		
		getSpList();
		
		function getSpList(){
			var query_data={};
			setSpTable(query_data);
		}
		
		//查询
		 $(document).on('click','#search_button_search',function(){
			 
		 			  //判断申请人
		 			  var query_data={};
		 			  var sparePart={};
		 			  sparePart.spEncoding=$('#spEncoding').val();
		 			  sparePart.spName=$('#spName').val();
					 sparePart.type==$('#type').val();
		 			  query_data.sparePart=sparePart;
					  console.log(query_data)
		 			  setSpTable(query_data);
		 });
		
		
	function setSpTable(query_data){
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
						elem : '#sp_list',
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
		
		 //头工具栏事件
		  table.on('toolbar(sp)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
			  layer.open({
			    type: 2,
				title:'备件添加',
			    skin: 'layui-layer-rim', //加上边框
			    area: ['50%', '80%'], //宽高
			    content: 'addsp.html'
			  });
		      break;
		      case 'getCheckLength':
		        var data = checkStatus.data;
				console.log(data)
				if(data.length<=0){
					 layer.alert('请选择1条需要修改的数据', {icon: 5});
				}else if(data.length > 1){
				 layer.alert('请选择1条需要修改的数据', {icon: 5});
				}else{
					var da=data[0];
				layer.open({
				  type: 1,
								title:'备件修改',
				  skin: 'layui-layer-rim', //加上边框
				  area: ['50%', '80%'], //宽高
				  content:$('#editProductBox')
				});	
				}
		      break;
		      case 'isAll':
			  //删除
			   var data = checkStatus.data;
			   deletetable(data);
		      break;
		      
		      //自定义头工具栏右侧图标 - 提示
		      case 'LAYTABLE_TIPS':
		        layer.alert('这是工具栏右侧自定义的一个图标按钮');
		      break;
		    };
		  });
		
		
		function deletetable(query_data){
			$.ajax({
							url : api.sparepartsLedger.delSparePartsInfo,
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
										getSpList();
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