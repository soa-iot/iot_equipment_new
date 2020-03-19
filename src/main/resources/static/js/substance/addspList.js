layui.use(['table'],
	function() {
	 var table = layui.table;
		//备件分类ID
		var fid=SoaIot.getUrlParam('f_id');
		getEqOrSpData();
		
		function getEqOrSpData(){
			var query_data={};
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
		
		 //头工具栏事件
		  table.on('toolbar(equipment_list_table)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'getCheckData':
			    var data = checkStatus.data;
				console.log(data);
				var query_datas=[];
				$.each(data,function(i,e){
					var query_data={};
				query_data.cId=fid;
				query_data.spId=e.spId
					query_datas.push(query_data);
				});				
				console.log(query_datas);
				saveSpList(query_datas)
		      break;
		      //自定义头工具栏右侧图标 - 提示
		      case 'LAYTABLE_TIPS':
		        layer.alert('这是工具栏右侧自定义的一个图标按钮');
		      break;
		    };
		  }); 

//数据入库
		  function saveSpList(query_data){
			  $.ajax({
			  				url : api.sparePartType.addClassifySpRelation,
			  				type : 'post',
			  				data : JSON.stringify(query_data),
			  				dataType : 'json',
			  				contentType : 'application/json',
			  				success : function(res) {
								  console.log(res);
			  					if (res.code == 0) {
			  						layer.confirm('添加成功？', {
			  						  btn: ['确定'] //按钮
			  						}, function(){
										 parent.location.reload(); 
										//刷新主页面
										var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
										 parent.layer.close(index);
										return;
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