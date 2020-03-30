//导入 用layui upload插件layui.use([ "element", "laypage", "layer", "upload"], function() {
layui.use(['layer', 'table','laydate'],
	function() {
		var layer = layui.layer,
			table = layui.table,laydate = layui.laydate;
 //日期范围
  laydate.render({
    elem: '#registerDate'
    ,range: true
  });
  
  //查询
  $(document).on('click','#search_button_search',function(){
	  var str=$('#registerDate').val();
	  var arr= str.split(" - ");
	   console.log($("select[name=registerType").val())
	 
  			  //判断申请人
  			  var query_data={};
  			  var spRegister={};
			  
  			  spRegister.registerCode=$('#registerCode').val();
  			  spRegister.registerType=$("select[name=registerType").val();
  			  spRegister.registerPeople=$('#registerPeople').val();
			 
			 spRegister.beginDate=arr[0];
			 spRegister.endDate=arr[1];
  			  query_data.spRegister=spRegister;
  			  getputoroutList(query_data);
  });
  
  
  
		//加载备件出入库登记单
		getputoroutList({});

		function getputoroutList(query_data) {
			var cols = [
				[{
					field: 'registerCode',
					title: '登记单号',
					width: '20%'
				}, {
					field: 'registerType',
					title: '登记类型',
					width: '20%'
				}, {
					field: 'explain',
					title: '具体描述',
					width: '20%'
				}, {
					field: 'registerPeople',
					title: '登记人',
					width: '20%'
				}, {
					field: 'registerDate',
					title: '登记时间',
					width: '20%'
				}]
			];
			table.render({
				elem: '#sp_list',
				url: api.sparepartOutIn.getOutInRegisterInfo,
				height: TABLE_H - 180,
				title: '出入库台账',
				method: 'post',
				contentType: 'application/json',
				where: query_data,
				cols: cols,
				page: true,
				limits: [30, 60, 90, 120, 150],
				limit: 30,
				parseData: function(res) {
					console.log(res.data);
				}
			});
		}
		
		var tmpobj=null;
		//数据行点击事件
		  table.on('row(sp_list)', function(obj){
		    var data = obj.data;
			console.log(data)
			getSpput(data.requestCode);
			getSpuTableList(data.requestCode);
			layer.open({
			      type    : 1,
			      offset  : 'r',
			      area    : ['50%', '100%'],
			      title   : '申请单详情',
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
		
		//加载申请单数据
		function getSpput(recode){
			var query_data={};
			var spPutIn={};
			spPutIn.requestCode=recode;
			query_data.spPutIn=spPutIn;
			$.ajax({
							url : api.sparepartOutIn.getSparepartApply,
							type : 'post',
							data : JSON.stringify(query_data),
							dataType : 'json',
							contentType : 'application/json',
							success : function(res) {
								console.log(res)
								if (res.code == 0) {
									var data=res.data;
									//页面信息
									$('#applicationDate').val(SoaIot.transitionDate(data[0].applicationDate));
									$('#applicationStatus').val(data[0].applicationStatus);
									$('#passDate').val(SoaIot.transitionDate(data[0].passDate));
									$('#proposer').val(data[0].proposer);
									$('#requestCode').val(data[0].requestCode);
									$('#type').val(data[0].type);
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
			      ,{field:'typr', title:'类型'}
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
		
	});
