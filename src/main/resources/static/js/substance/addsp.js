layui.use(['form', 'layedit', 'laydate'],
	function() {
	 var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
		
		//获取备件ID
		var spid=SoaIot.getUrlParam('spid');
		if(spid!=''){
			$('#title').html('备件信息修改');
				getSpData();
		}
		function getSpData(){
			var query_data={};
			var sparePart={};
			sparePart.spId=spid;
			query_data.sparePart=sparePart;
			$.ajax({
							url : api.sparepartsLedger.getSparePartsInfo,
							type : 'post',
							data : JSON.stringify(query_data),
							dataType : 'json',
							contentType : 'application/json',
							success : function(res) {
								var data=res.data;
							console.log(data)								
								$('#spEncoding').val(data[0].spEncoding);
								$('#spName').val(data[0].spName);
								$('#brand').val(data[0].brand);
								$('#type').val(data[0].type);
								$('#specification').val(data[0].specification);
								$('#unit').val(data[0].unit);
								$('#unitCost').val(data[0].unitCost);
								$('#spInventory').val(data[0].spInventory);
								$('#prewarningVal').val(data[0].prewarningVal);
								$('#procurementCycle').val(data[0].procurementCycle);
								$('#labelCode').val(data[0].labelCode);
								$('#manufactureFactory').val(data[0].manufactureFactory);
								$('#productionDate').val(data[0].productionDate);
							},
							error : function() {
								layer.msg('数据保存失败，请联系管理员！！！', {
											icon : 2
										});
							}
						});
		}
		
		
		 //日期
		//日期时间选择器
		  laydate.render({
		    elem: '#productionDate'
		  });
		
		//监听提交
		  form.on('submit(search_button_search)', function(data){
			 if(spid!=''){
				 var datas=data.field;
				 datas.spId=spid;
				  	 editSp(datas);
				 }else{
				 saveSp(data.field);
				 }
			
		    return false;
		  });
		  //返回
		 $(document).on('click',"#search_button_close",function(){
		        window.location.href='sparepartList.html';
		  });
		  
		 
		  
		  //数据入库
		  function saveSp(query_data){
			  $.ajax({
			  				url : api.sparepartsLedger.addSparePartsInfo,
			  				type : 'post',
			  				data : JSON.stringify([query_data]),
			  				dataType : 'json',
			  				contentType : 'application/json',
			  				success : function(res) {
								  console.log(res);
			  					if (res.code == 0) {
			  						layer.confirm('添加备件成功！', {
			  						  btn: ['确定'] //按钮
			  						}, function(){
										window.parent.location.reload();
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
		  
		  //数据入库
		  function editSp(query_data){
			  console.log(query_data)
		  			  $.ajax({
		  			  				url : api.sparepartsLedger.updateSparePartsInfo,
		  			  				type : 'put',
		  			  				data : JSON.stringify(query_data),
		  			  				dataType : 'json',
		  			  				contentType : 'application/json',
		  			  				success : function(res) {
		  								  console.log(res);
		  			  					if (res.code == 0) {
		  			  						layer.confirm('备件更新成功！', {
		  			  						  btn: ['确定'] //按钮
		  			  						}, function(){
												window.parent.location.reload();
												var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		  										//刷新主页面
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