layui.use(['form', 'layedit', 'laydate'],
	function() {
	 var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
		
		//获取备件数据ID
		var spid=SoaIot.getUrlParam('spid');
		
		
		 //日期
		//日期时间选择器
		  laydate.render({
		    elem: '#productionDate'
		  });
		
		//监听提交
		  form.on('submit(search_button_search)', function(data){
			saveSp(data.field);
		    return false;
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
			  						layer.confirm('添加成功？', {
			  						  btn: ['确定'] //按钮
			  						}, function(){
										//刷新主页面
										layer.closeAll(); 
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