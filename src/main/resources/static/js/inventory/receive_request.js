//导入 用layui upload插件layui.use([ "element", "laypage", "layer", "upload"], function() {
layui.use(['layer', 'tree','table'],
	function() {
		var layer = layui.layer,table = layui.table,
			tree = layui.tree,dataList=[];

//采购申请单号
		var request = 'LY' + Date.now(); 
		$('#requestCode').val(request);
		setEqOrSpList();

		getEqOrSpData();
function setEqOrSpList(){
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
					data : dataList,
					height : TABLE_H-200,
					title : '备件台账',
					method : 'post',
					toolbar: '#toolbar', //开启头部工具栏，并为其绑定左侧模板
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

 //头工具栏事件
  table.on('toolbar(sp)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    switch(obj.event){
      case 'getCheckData':
	  layer.open({
	        type    : 1,
	        offset  : 'r',
	        area    : ['50%', '100%'],
	        title   : '备件选择列表',
	        shade   : 0.1,
	        anim   : -1,
	        skin:'layer-anim-07',
	        move    : false,
	        content:$('#openProductBox')
	        ,cancel  : function (index) {
	          var $layero = $('#layui-layer' + index);
	          $layero.animate({
	            left : $layero.offset().left + $layero.width()
	          }, 300, function () {
	            layer.close(index);
	          });
	          return false;
	        }
	      });
      break;
      case 'getCheckLength':
        var data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
      break;
      case 'isAll':
        layer.msg(checkStatus.isAll ? '全选': '未全选');
      break;
      
      //自定义头工具栏右侧图标 - 提示
      case 'LAYTABLE_TIPS':
        layer.alert('这是工具栏右侧自定义的一个图标按钮');
      break;
    };
  });


		function getEqOrSpData(){
			var query_data={};
			$.ajax({
							url : api.sparepartsLedger.getSparePartsInfo,
							type : 'post',
							data : JSON.stringify(query_data),
							dataType : 'json',
							contentType : 'application/json',
							success : function(res) {
								if (res.code == 0) {
									setTable(res.data)
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

function setTable(data){
	var cols=[[
	  {type: 'checkbox', fixed: 'left', width: 60}
	  ,{field:'spEncoding', title:'备件编码', width: 180}
	  ,{field:'spName', title:'备件名称', width: 180}
	  ,{field:'type', title:'类别', width: 80}
	  ,{field:'specification', title:'型号规格', width: 180}
	  ,{field:'spInventory', title:'当前库存数量', width: 220}
	  ,{field:'prewarningVal', title:'合理库存数量', width: 220}
	  ,{field:'brand', title:'品牌', width: 80}
	  ,{field:'manufactureFactory', title:'生产厂家', width: 180}
	  ,{field:'productionDate', title:'生产日期', width: 180}
	  ,{field:'unit', title:'单位', width: 80}
	  ,{field:'unitCost', title:'价格', width: 80}
	  ,{field:'labelCode', title:'标签码', width: 180}
	    ]];
		console.log(cols)
	table.render({
					elem : '#eq_list_table',
					data : data,
					height : TABLE_H-120,
					title : '备件台账',
					toolbar: '#toolbar222', //开启头部工具栏，并为其绑定左侧模板
					cols : cols
					,page : false
					// ,limits : [10, 20, 30, 40, 50]
					,limit : 3000000
				});
}

//头工具栏事件
		  table.on('toolbar(eq_list_table)', function(obj){
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
				saveSpList(query_datas);
		      break;
		      //自定义头工具栏右侧图标 - 提示
		      case 'LAYTABLE_TIPS':
		        layer.alert('这是工具栏右侧自定义的一个图标按钮');
		      break;
		    };
		  }); 
		  
		  function saveSpList(query_datas){
			  dataList=query_datas;
			  //设备基本信息列表数据获取
			  setEqOrSpList();
			  layer.closeAll(); 
		  }
		  
		  //提交事件
		  $(document).on('click','#search_button_search',function(){
			  //判断申请人
			  var proposer=$('#proposer').val();
			  console.log(proposer.length)
			 var modules = $("#modules option:selected").val();　
			  var dataLista=layui.table.cache["equipment_list_table"];
			  console.log(dataLista.length)
			  var bool=true;
			  $.each(dataLista,function(i,t){
				  var quantity=t.quantity;
				  console.log(quantity)
				  if(quantity==0){
					 bool=false; 
				  }
			  });
			  if(proposer.length<=0){
			  	layer.alert('请填写申请人', {icon: 5});  
			  }else if(modules==''){
				   layer.alert('请选择领用类型', {icon: 5}); 
			  }else if(dataLista.length==0){
				 layer.alert('请添加备件', {icon: 5});  
			  }else if(bool==false){
				layer.alert('请填写备件申请数量', {icon: 5});    
			  }else{
				 var da={};
				 			  da.requestCode=request;
				 			  da.proposer=proposer;
				 			  da.type='领用';
				 			  da.applicationStatus='申请中';
							  da.remark=modules;
				 var query_data={};
				 query_data.spPutIn=da;
				 query_data.spRecords=dataLista;
				 
				 console.log(query_data)
				 save(query_data); 
			  }
		  });
		  
		  function save(query_data){
			  $.ajax({
			  				url : api.sparepartApply.addSparepartApply,
			  				type : 'post',
			  				data : JSON.stringify(query_data),
			  				dataType : 'json',
			  				contentType : 'application/json',
			  				success : function(res) {
			  					if (res.code == 0) {
			  						layer.confirm('添加成功？', {
			  						  btn: ['确定'] //按钮
			  						}, function(){
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


