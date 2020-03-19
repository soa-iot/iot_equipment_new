layui.use(['form', 'layer', 'table'],
	function() {
		var form = layui.form,layer = layui.layer,table = layui.table;
		//加载备件的信息列表
		getSpList();
		
	function getSpList(){
			var query_data={};
			query_data.alarm='true';
			setSpTable(query_data);
		}
		
		  //查询
		//提交事件
		$(document).on('click','#search_button_search',function(){
					  //判断申请人
					  var query_data={};
					  var sparePart={};
					  sparePart.spEncoding=$('#spEncoding').val();
					  sparePart.spName=$('#spName').val();
					  sparePart.labelCode=$('#labelCode').val();
					  query_data.alarm='true';
					  query_data.sparePart=sparePart;
					  setSpTable(query_data);
		});
		
		//采购申请
		 table.on('toolbar(sp_list1)', function(obj){
		   var checkStatus = table.checkStatus(obj.config.id);
		   switch(obj.event){
		     case 'getCheckData':
			 window.open('../inventory/purchase_request.html');    //跳转
		     break;
		     //自定义头工具栏右侧图标 - 提示
		     case 'LAYTABLE_TIPS':
		       layer.alert('这是工具栏右侧自定义的一个图标按钮');
		     break;
		   };
		 });
		
	function setSpTable(query_data){
		var cols=[[
		      {field:'spEncoding', title:'备件编码', fixed: 'left'}
		      ,{field:'spName', title:'备件名称'}
		      ,{field:'type', title:'类别'}
		      ,{field:'specification', title:'型号规格'}
		      ,{field:'spInventory', title:'当前库存数量',
			templet: function(d) {
				return '<div style="color:red">'+d.spInventory + '</div>';
			}}
			  ,{field:'prewarningVal', title:'合理库存数量'}
			  ,{field:'brand', title:'品牌'}
			  ,{field:'manufactureFactory', title:'生产厂家'}
			  ,{field:'productionDate', title:'生产日期'}
			  ,{field:'unit', title:'单位'}
			  ,{field:'unitCost', title:'价格'}
			  ,{field:'labelCode', title:'标签码'}
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
						},
						done:function(res,curr,count){
							var arr=res.data;
							// $.each(arr,function(i,t){
							// 	if(t.spInventory<t.prewarningVal){
							// 		 $("tr").eq(i+1).css("background-color","red");
							// 		}
							// });
							}
					});
		}
});

