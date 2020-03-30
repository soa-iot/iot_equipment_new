//导入 用layui upload插件layui.use([ "element", "laypage", "layer", "upload"], function() {
layui.use(['layer', 'tree','table','form'],
	function() {
		var layer = layui.layer,table = layui.table,
			tree = layui.tree,dataList=[],form=layui.form,array = [];
var types=SoaIot.getUrlParam('type');
if(types==1){
	// button_return
	 $("#button_return").show();//表示为display：block，
}
 //返回
		 $(document).on('click',"#button_return",function(){
		        window.location.href='../eqorsp/purchaseList.html';
		  });
//采购申请单号
		var request = 'LY' + Date.now(); 
		$('#requestCode').val(request);
		$('#proposer').val(SoaIot.getCookis('name'));
		setEqOrSpList();

		getEqOrSpData({});
function setEqOrSpList(){
	var cols=[[
	      {type: 'checkbox', fixed: 'left'}
	      ,{field:'spName', title:'备件名称', fixed: 'left'}
	      ,{field:'spInventory', title:'当前库存'}
	      ,{field:'quantity', title:'申请数量',edit: 'text'}
	      ,{field:'unitCost', title:'备件单价'}
	      ,{field:'unit', title:'备件单位'},
		  ,{field:'subtotal', title:'小计'}
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
					limit : 30000,
					parseData : function(res) {
						console.log(res.data);
						}
				});
}

//监听搜索
  form.on('submit(sel_button_search)', function(data){
			var query_data={};
			var sparePart={};
			sparePart.spEncoding=$('#spEncoding').val();
			sparePart.spName=$('#spName').val();
			sparePart.type=$('#type').val();
			query_data.sparePart=sparePart;
			getEqOrSpData(query_data)
		    return false;
		  });

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
        array.forEach((item) => {
                                       item.remove(); //删除dom结构
                                       // obj.checked = 'false';
                                   })
      break;
      
      //自定义头工具栏右侧图标 - 提示
      case 'LAYTABLE_TIPS':
        layer.alert('这是工具栏右侧自定义的一个图标按钮');
      break;
    };
  });


		function getEqOrSpData(query_data){
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
					query_data.subtotal=0;
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
							  da.standby1=modules;
							  da.remark=$('#remark').val();
							  da.sppurpose=$('#sppurpose').val();
							  
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
		  
		  
		  table.on('edit(sp)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
		     var datas=obj.data;
		    datas.subtotal=datas.quantity * datas.unitCost;
		    obj.update(datas); //修改当前行数
		  });
		  
		  
		  table.on('checkbox(sp)', function (obj) {
		                  // console.log(obj.tr) //得到当前行元素对象
		                  var tr = obj.tr; //得到当前点击复选框的行元素对象
		                  if (obj.type == 'all') { //点击全选按钮触发此处
		                      var len = array.length;
		                      array.splice(0, len); //全选后删除数组项，重新添加全选的dom元素
		                      array.push(obj.tr.prevObject[0]);
		                  }
		                  else {
		                      if (obj.checked) { //多选框被选中则添加dom元素到数组
		                          array.push(tr);
		                      } else {//取消多选框的选中则在数组中删除自己
		                          var indexs = obj.tr[0].rowIndex;  //获取取消选中的元素对象下标
		                          array.forEach((item, index) => {
		                              //当数组中任意一个元素的rowIndex值与取消复选框的元素对象属性rowIndex的下标值相等，则在数组中删除该元素
		                              if (item[0].rowIndex == indexs) {
		                                  array.splice(index, 1);
		                              }
		                          })
		                      }
		                  }
		  });
	});


