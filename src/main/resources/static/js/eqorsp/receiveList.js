//导入 用layui upload插件layui.use([ "element", "laypage", "layer", "upload"], function() {
layui.use(['layer', 'tree', 'form', 'table', 'laydate'],
	function() {
		var $ = layui.jquery,
			layer = layui.layer,
			table = layui.table,
			tree = layui.tree,
			laydate = layui.laydate,
			form = layui.form;
		//日期范围
		 laydate.render({
		   elem: '#applicationDate'
		   ,range: true
		 });
		//申请单列表
		getreceiveList();
		function getreceiveList() {
			var query_data={};
			var spPutIn={};
			spPutIn.type='领用';
			query_data.spPutIn=spPutIn;
			setEqTable(query_data);
		}

 //查询
  $(document).on('click','#search_button_search',function(){
	  var str=$('#applicationDate').val();
	  var arr= str.split(" - ");
  			  //判断申请人
  			  var query_data={};
  			  var spPutIn={};
			  
  			  spPutIn.requestCode=$('#requestCode').val();
  			  spPutIn.proposer=$('#proposer').val();
			  
			 spPutIn.type='领用';
			 spPutIn.beginDate=arr[0];
			 spPutIn.endDate=arr[1];
  			  query_data.spPutIn=spPutIn;
			  console.log(query_data)
  			  setEqTable(query_data);
  });
  

		//加载设备列表信息
		function setEqTable(query_data) {
			var cols=[[
			     {field:'requestCode', title:'申请单号'}
			      ,{field:'proposer', title:'申请人'}
			      ,{field:'applicationDate', title:'申请日期'}
			      ,{field:'type', title:'申请状态'}
			      ,{field:'passDate', title:'审核通过日期'}
			      ,{field:'remark', title:'类型'}
			    ]];
			table.render({
							elem : '#sp_list',
							url : api.sparepartOutIn.getSparepartApply,
							height : TABLE_H-150,
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
		
		//采购申请
		 table.on('toolbar(sp_list)', function(obj){
		   var checkStatus = table.checkStatus(obj.config.id);
		   switch(obj.event){
		     case 'getCheckData':
			 console.log(11111)
			 window.open('../inventory/receive_request.html');    //跳转
		     break;
		     //自定义头工具栏右侧图标 - 提示
		     case 'LAYTABLE_TIPS':
		       layer.alert('这是工具栏右侧自定义的一个图标按钮');
		     break;
		   };
		 });
		
	function getSpuTableList(requestCode){
		$.ajax({
						url : api.sparepartOutIn.getSpRecord,
						type : 'get',
						data : {requestCode:requestCode},
						dataType : 'json',
						contentType : 'application/json',
						success : function(res) {
							console.log(res)
							if (res.code == 0) {
								var data=res.data;
								console.log(data);
								setSpuTableList(data)
								//页面信息
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
	
	function setSpuTableList(data){
		var query_data={};
		var cols=[[
		      {field:'spName', title:'备件名称'}
		      ,{field:'spInventory', title:'当前库存'}
		      ,{field:'quantity', title:'申请数量'}
		      ,{field:'unitCost', title:'备件单价'}
			  ,{field:'unit', title:'备件单位'}
		    ]];
		table.render({
						elem : '#eq_list_table',
						data :data,
						title : '备件台账',
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
//数据行点击事件
  table.on('row(sp_list)', function(obj){
    var data = obj.data;
	getSpuTableList(data.requestCode);
	layer.open({
	      type    : 1,
	      offset  : 'r',
	      area    : ['600px', '100%'],
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
		
		
	  });
	});
