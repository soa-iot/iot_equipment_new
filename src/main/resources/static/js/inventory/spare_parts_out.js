layui.use(['layer', 'tree','form','laydate','table'],
	function() {
		var layer = layui.layer,table = layui.table,
			tree = layui.tree,requestCode='',laydate = layui.laydate,
			form = layui.form;
		//出库登记单号
		var request = 'CQ' + Date.now(); 
		$('#requestCode').val(request);
		$('#proposer').val(SoaIot.getCookis('name'));
		 //常规用法
		
		  laydate.render({
		    elem: '#applicationDate'
		    ,range: true
		  });
		  getEqOrSpDatas();
		  function getEqOrSpDatas(){
			 var query_data={};
			  var spPutIn={};
			spPutIn.type='领用';
			query_data.spPutIn=spPutIn;  
			getEqOrSpData(query_data);
		  }
	
		//申请单
		
		
		//加载领用申请单
		$(document).on('click',"#selLyd",function(){
			layer.open({
			      type    : 1,
			      offset  : 'r',
			      area    : ['70%', '100%'],
			      title   : '领用申请单选择列表',
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
		 });
		
		getEqOrSpList();
		function getEqOrSpList(query_data){
				$.get(api.sparepartOutIn.getSpRecord, query_data, function(results) {
					console.log(results)
					var data=results.data;
					setEqOrSpList(data);
					});
		}
		
		setEqOrSpList({});
		
function setEqOrSpList(data){
	var cols=[[
	      {field:'spName', title:'备件名称', fixed: 'left'}
	      ,{field:'spInventory', title:'当前库存'}
	      ,{field:'quantity', title:'申请数量'}
	      ,{field:'unitCost', title:'备件单价'}
	      ,{field:'unit', title:'备件单位'}
	    ]];
				table.render({
								elem : '#equipment_list_table',
								data : data,
								height : TABLE_H-210,
								title : '领用申请单',
								method : 'post',
								// toolbar: '#toolbar222', //开启头部工具栏，并为其绑定左侧模板
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

		function getEqOrSpData(query_data){
			var cols=[[
			      {type:'radio'}
				 ,{field:'requestCode', title:'申请单号', width:180,}
			      ,{field:'proposer', title:'申请人', width:80,}
			      ,{field:'applicationDate', title:'申请日期', width:180,}
			      ,{field:'type', title:'申请状态', width:180,}
				  ,{field:'passDate', title:'审核通过日期', width:180,}
				  ,{field:'remark', title:'类型', width:80,}
			    ]];
			table.render({
							elem : '#eq_list_table',
							url : api.sparepartOutIn.getSparepartApply,
							height : TABLE_H-130,
							title : '领用申请单',
							method : 'post',
							toolbar: '#toolbar222', //开启头部工具栏，并为其绑定左侧模板
							contentType : 'application/json',
							where : query_data,
							cols : cols,
							page : false,
							// limits : [30, 60, 90, 120, 150],
							limit : 3000000,
							parseData : function(res) {
								console.log(res.data);
								}
						});
		}

//头工具栏事件
		  table.on('toolbar(eq_list_table)', function(obj){
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
					 getEqOrSpList(query_datas)
					 layer.closeAll(); 
				}
		      break;
		      //自定义头工具栏右侧图标 - 提示
		      case 'LAYTABLE_TIPS':
		        layer.alert('这是工具栏右侧自定义的一个图标按钮');
		      break;
		    };
		  }); 
		  
		  //监听搜索
		    form.on('submit(sel_button_search)', function(data){
				var str=$('#applicationDate').val();
				var arr= str.split(" - ");
				
		  			var query_data={};
		  			var spPutIn={};
		  			spPutIn.requestCode=$('#requestCodes').val();
		  			spPutIn.proposer=$('#proposers').val();
					spPutIn.type='领用';
					
					query_data.beginDate=arr[0];
					query_data.endDate=arr[1];
		  			query_data.spPutIn=spPutIn;
					console.log(query_data)
		  			getEqOrSpData(query_data)
		  		    return false;
		  		  });
		  
		  
		  //提交事件
		  
		  $(document).on('click','#search_button_search',function(){
			  //判断申请人
			  var proposer=$('#proposer').val();
			 
			  if(proposer.length<=0){
			  	layer.alert('请填写领料人', {icon: 5});  
			  }else if(requestCode==''){
				 layer.alert('请选择领用申请单', {icon: 5});  
			  }else{
				  
				 var spRegister={};
				 			  spRegister.registerCode=request;
				 			  spRegister.registerType='出库';
				 			  spRegister.explain='领用出库';
				 			  spRegister.requestCode=requestCode;
							  spRegister.registerPeople=proposer;
				 var query_data={};
				query_data.operateType='out';
				 query_data.spRegister=spRegister;
				 save(query_data); 
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
										//刷新页面
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
		  
	});


