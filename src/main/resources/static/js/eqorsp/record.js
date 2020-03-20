layui.use(['element','layer', 'tree','table','upload'],
	function() {
		var layer = layui.layer,table = layui.table,
			element = layui.element,tree = layui.tree,eqId='',upload = layui.upload;
//--------分类树-------------------------------
//设备分类数据数据获取
		getEqclassifyTree();
function getEqclassifyTree() {
			$.get(api.equipmentLedger.getSparepartsClassInfoAsTree, null, function(results) {
				console.log(results)
			// if(results.code==0){
				setTree(results.data);
				// }
			});
		}
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

		//---------加载设备------------------------------------
		getEqList();
function getEqList(){
	var query_data={};
		setEqTable(query_data);
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

//-----------------------------
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
  //获取关系
function getEqOrSpData(query_data){
	setEqOrSpList(query_data);
	layer.open({
	      type    : 1,
	      offset  : 'r',
	      area    : ['70%', '100%'],
	      title   : '设备备件列表',
	      shade   : 0.1,
		  shadeClose:true,
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
}

function setEqOrSpList(query_data){
	console.log(query_data)
	console.log(api.sparepartsLedger.getSparePartsInfoByEquId)
	var cols=[[
	      {field:'spEncoding', title:'备件编码', fixed: 'left'}
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
	    ]];
	table.render({
					elem : '#eq_list_table',
					url : api.sparepartsLedger.getSparePartsInfoByEquId,
					height : TABLE_H-100,
					title : '备件台账',
					method : 'post',
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
	
	var query_data={equId:eqId};
		getEqOrSpData(query_data);
  });
		
	});


