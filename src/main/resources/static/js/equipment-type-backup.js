//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});

/**
 * 设备类型数据
 */
var equipment_type = {
		"仪表设备":["压力表","压力差压变送器","温度计","温度变送器","气动切断阀","气动调节阀","液位计(含远程)",
			      "流量计","节流装置","在线分析仪","振动温度探头","DCS SIS系统","FGS系统","固定式报警仪","其他"],
		"机械设备":["C类设备","D类设备","E类设备","F类设备","H类设备","R类设备","K类设备","P类设备","机修类","车辆类","其他"],
		"电气设备":["电动机","UPS电源系统","EPS电源系统","干式变压器","低压配电柜","高压配电柜","直流电源系统","现场配电箱","其他"],
		"分析化验设备":["A类分析仪器"]
}

/**
 * 日期插件
 */
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#startDate',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});

layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#endDate',
		type: 'date',
		format: 'yyyy-MM-dd'
	});
});

//加载layui内置模块
layui.use(['jquery','form','layer','table','excel'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	var excel = layui.excel;
	
	/**
	 * 设备类型下拉框级联
	 */
	form.on('select(equipment-type)', function(data){
		 var value = data.value;
		 var equipment_type_detail = equipment_type[value];
		 console.log("设备具体类型："+equipment_type_detail);
		 $("#equipment-type-detail").empty();
		 $("#equipment-type-detail").append("<option value=''>请选具体类型</option>");
		 for(var i=0;i<equipment_type_detail.length;i++){
			 var $option = $("<option></option>");
			 $option.val(equipment_type_detail[i]);
			 $option.text(equipment_type_detail[i]);
			 $("#equipment-type-detail").append($option);
		 }
		 //刷新表单
		 form.render('select');
	});
	
	/**
	 * 设备导入备份记录表
	 */
	var backupTable = table.render({
		elem: '#backup-table',
		method: 'get',
		url: '/iot_equipment/equipmentinfo/query',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:70,
		page: true,   //开启分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data;
			
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'id', title:'序号', width:'7%', sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'bid', title:'备份主键id', hide: true, sort:false},
			{field:'bname', title:'设备分类', width:'16%', sort:false, align:'center'},
			{field:'bcreatetime', title:'操作时间', width:'15%', sort:false, align:'center'},
			{field:'bperson', title:'操作人', width:'10%', sort:false, align:'center'},
			{field:'bnote', title:'操作备注', width:'20%', sort:false, align:'center'},
			{field:'btype', title:'操作类型', width:'12%', sort:false, align:'center'},
			{fixed:'right', title:'操    作', width:'19.9%', sort:false, align:'center', toolbar:'#barBtn'}]]
	});

	
	/**
	 * 监听每一行工具事件
	 */
	table.on('tool(backup-table)', function(obj){
		console.log(obj);
	    var data = obj.data;
	    var tr = obj.tr;
	    if(obj.event === 'export'){
	    	tr.find("#export").attr({"href": "/iot_equipment/equipmentinfo/download/"+data.bid});
	    }
	    if(obj.event === 'rollback'){
	    	layer.confirm("是否确定还原数据？",{icon: 3, title:'提示',offset: '150px'}, function(index){
	    		$.ajax({
	    			type: 'GET',
	    			async: false,
	    			url: '/iot_equipment/equipmentinfo/rollback/'+data.bid,
	    			dataType: 'json',
	    			success: function(json){
	    				console.log("是否还原成功："+json.data);
	    				if(json.data){
	    					layer.msg("还原数据成功", {icon: 1, time: 2000, offset: '150px'});
	    				}else{
	    					layer.msg("还原数据失败", {icon: 2, time: 2000, offset: '150px'});
	    				}
	    			},
	    			error: function(){
	    				layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
	    			}
	    		})
	    		//layer.closeAll();
	    	});
	    }
	});
	
	/**
	 * 监听条件查询提交事件
	 */
	form.on('submit(backup-query)', function(data){
	  var field = data.field;
	  console.log('--------条件查询的条件为：');
	  console.log(field);
	  
	  backupTable.reload({
 	    page: {
 		   curr: 1 //重新从第 1 页开始
 	    }
	  	,where: {
	  		"bname": field.bname,
	  		"startDate": field.startDate,
	  		"endDate": field.endDate,
	  		"bperson": field.bperson
	  	}
 	  });
	  
	  return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});
})