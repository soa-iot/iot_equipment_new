/**
 * 
 * 油脂库存盘点
 * 
 */

var userid = "张三";


$.ajax({
	  type: 'post',
	  async: false,
	  url: '/iot_equipment/equipmentoil/queryoilallstock',
	  dataType : "json",
	  success: function(json){
		  var data = json.data;
		  
		  $.each(data, function (i, item) {
				var option = '<option  value="'+item.oname+'">'+item.oname+'</option>';
				$("#oname").append(option);
			});
	  }

})

layui.use(['table','laydate','layer', 'form'], function(){
	
	  var table = layui.table
	  	  ,laydate = layui.laydate
	  	  ,layer = layui.layer
	  	  ,form = layui.form
	  	  ,$ = layui.jquery;
	  
	  $("#userid").val(userid);

	  laydate.render({
	    elem: '#inv-time'
	    ,value:new Date()
	  	,type: 'date'
	  	,format:"yyyy-MM-dd"
	  });
	  
	  form.on('submit(sub)', function(obj){
		  console.log("提交盘点信息....");
		  
		  if ($("#oname").val() == null || $("#oname").val() == "" ) {
			  layer.msg("油品名称不能为空！！", {icon: 7, time: 2000, offset: '150px'});
			  return;
		}else if( $("#ramount").val()==0 || $("#ramount").val() == null){
			layer.msg("油品数量不能为0！！", {icon: 7, time: 2000, offset: '150px'});
			  return;
		}
		  
		  $.ajax({
				type: 'POST',
				async: false,
				url: '/iot_equipment/equipmentoil/oilstock',
				data:{ 
					"oname": $("#oname").val(),
					"ramount": $("#otype").val()=='加'?$("#ramount").val():"-"+$("#ramount").val(),
					"rnote": $("#rnote").val(),
					"userid":userid,
					"otype": $("#otype").val(),
					"calcType":$("#calc-type").val()
				},
				dataType: 'JSON',
				success: function(json){
					if(json.state == 0){
						$("#oname").val("")
						
						layer.msg("提交成功", {icon: 1, time: 2000, offset: '150px'});
					}else{
						layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
					}	
				},
				error: function(){
					layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
				}
			})
	  })

})