//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});
//从cookie中获取当前登录用户
var resavepeople = getCookie1("name").replace(/"/g,'');

/**
 * 日期插件
 */
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#eventTime',
		type: 'datetime',
		format: 'yyyy-MM-dd HH:mm:ss'
	});
});

//加载layui内置模块
layui.use(['jquery','form','layer','table','excel'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	var excel = layui.excel;
	
	//当前选中的设备位号
	var positionNum = null;
	
	/**
	 * 设备运行信息展示表
	 */
	var yearlyTable = table.render({
		elem: '#equipmentInfo',
		method: 'post',
		url: '/iot_equipment/equipment/list',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		page: true,   //开启分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data     
			console.log(data);
			if(data != null && data != '' ){
				positionNum = data[0].positionNum;
			}else{
				positionNum = null;
			}
			console.log("positionNum="+positionNum);
			$("#rfid").text(positionNum+" 大修/更换时间");
			showBigEvent();
			
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'id', title:'序号', width:'11%', sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'positionNum', title:'设备位号', width:'30%', sort:false, align:'center'},
			{field:'name', title:'设备名称', width:'35%', sort:false, align:'center'},
			{fixed:'right', title:'大修/更换时间', width:'24%', sort:false, align:'center', toolbar:'#barBtn'}]]
	});
	
	/**
	 * 设备大修和更换时间信息展示表
	 */
	var eventTable;
	function showBigEvent(){
	eventTable = table.render({
			elem: '#equipmentUpdateDate',
			method: 'post',
			url: '/iot_equipment/equipment/event/'+positionNum,
			autoSort: false,  //禁用前端自动排序
			cellMinWidth:60,
			page: true,   //开启分页
			request: {
			    pageName: 'page' //页码的参数名称，默认：page
			    ,limitName: 'limit' //每页数据量的参数名，默认：limit
			},
			parseData: function(res){ //res 即为原始返回的数据
				var data = res.data     
			    return {
			      "code": res.code, //解析接口状态
			      "msg": res.msg, //解析提示文本
			      "count": res.count, //解析数据长度
			      "data": data      //解析数据列表
			    };
			},
			cols: [[{field:'id', title:'序号', width:'20%', sort:false, type:'numbers', fixed:'left', align:'center'},
				{field:'event', title:'类别', width:'35%', sort:false, align:'center'},
				{field:'eventTime', title:'时间', width:'45%', sort:false, align:'center'}]]
		});
	}
	
	/**
	 * 重新加载动设备信息表
	 */
	function reloadEquipMoveTable(){
		yearlyTable.reload({
    		url: '/iot_equipment/equipment/list'
    	   ,page: {
    		   curr: 1 //重新从第 1 页开始
    	   }
    	   ,where: {
    			'positionNum': $("#positionNum").val(),
    			'equipType': $("#equipType").val(),
    			'name': $("#name").val(),
    	   }
    	})
	}
	
	/**
	 * 监听每一行工具事件
	 */
	table.on('tool(equipmentInfo)', function(obj){
		console.log(obj);
	    var data = obj.data;
	    if(obj.event === 'bigevent'){
	      positionNum = data.positionNum;
	      $("#rfid").text(positionNum+" 大修/更换时间");
	      eventTable.reload({
	    		url: '/iot_equipment/equipment/event/'+positionNum
	    	   ,page: {
	    		   curr: 1 //重新从第 1 页开始
	    	   }
	    	})
	    }
	});
	
	/**
	 * 动设备查询事件
	 */
	$("#query-equipment").click(function(){
		reloadEquipMoveTable();
	})
	
	/**
	 * 动设备大事件添加事件
	 */
	$('#add-event').click(function(){
		var event = $("#event").val();
		var eventTime = $("#eventTime").val();
		if(event == ""){
			layer.msg("类别不能为空", {icon: 7, time: 2000, offset: '150px'});
			return false;
		}
		if(eventTime == ""){
			layer.msg("时间不能为空", {icon: 7, time: 2000, offset: '150px'});
			return false;
		}
		layer.confirm("是否确定添加？",{icon: 3, title:'提示',offset: '150px'}, function(index){
			$.ajax({
				async: false,
				type: "POST",
				url: "/iot_equipment/equipment/event/add",
				data:{'positionNum':positionNum, 'event':event, 'eventTime':eventTime},
				dataType: "json",
				timeout:2000,
				success: function(json){
					if(json.state == 0){
						layer.msg("添加数据成功", {icon: 1, time: 2000, offset: '150px'});
						$("#event").val("");
						$("#div-event .layui-input").val("");
						$("#eventTime").val("");
						eventTable.reload({
				    		url: '/iot_equipment/equipment/event/'+positionNum
				    	   ,page: {
				    		   curr: 1 //重新从第 1 页开始
				    	   }
				    	});
					}else{
						layer.msg("添加数据失败", {icon: 2, time: 2000, offset: '150px'});
					}
				},
				error: function(){
					layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
				}
			})
		});
	})
	
	/**
	 * 添加设备弹窗
	 */
	var equipName;
	$('#add-equipment').click(function(){
		//显示设备添加弹窗
		layer.open({
			type: 1,
			id:"addEquipment",
			title: '添加动设备',
			content: $('#addequipment-window'),
			area: ['400px','330px'],
			offset: '120px',
			btn: ['添&nbsp;&nbsp;加', '取消'],
			btnAlign: 'c', //按钮居中对齐
			yes: function(index, layero){
				
				if($("#equipType_").val() == ''){
					layer.msg("设备类别不能为空", {icon: 7, offset: '150px'});
					return false;
				}
				if($("#positionNum_").val() == ''){
					layer.msg("设备位号不能为空", {icon: 7, offset: '150px'});
					return false;
				}
				if($("#dcsPositionNum_").val() == ''){
					layer.msg("DCS点位号不能为空", {icon: 7, offset: '150px'});
					return false;
				}
				//同步请求后台添加设备数据
				$.ajax({
					type: 'POST',
					async: false,
					url: '/iot_equipment/equipment/move/add',
					data:{ 
						"equipType": $("#equipType_").val(),
						"name": equipName,
						"positionNum": $("#positionNum_").val(),
						"dcsPositionNum": $("#dcsPositionNum_").val()
					},
					dataType: 'JSON',
					success: function(json){
						if(json.state == 0){
							layer.msg("添加动设备信息成功", {icon: 1, time: 2000, offset: '150px'});
							cleanForm();
							layer.close(index);
						}else{
							layer.msg(json.message, {icon: 2, time: 2000, offset: '150px'});
						}	
					},
					error: function(){
						layer.msg("连接服务器失败，请检查网络是否正常", {icon: 7, time: 2000, offset: '150px'});
					}
				})
			},
			success: function(index, layero){
				cleanForm();	
			}
		})
		
		/**
		 * 清空表单
		 */
		function cleanForm(){
			$("#equipType_").val('');
			equipName = '';
			$("#equipType-div").find("input").val('');
			$("#positionNum_").val('');
			$("#dcsPositionNum_").val('');
		}
	})
	
	/**
	 * 从设备台账中定位K类、P类设备
	 */
	$("#location-btn").click(function(){
		//显示设备定位弹窗
		layer.open({
			type: 2,
			id:"locationEquipment",
			title: '设备定位',
			content: './equipment-time-location.html?equMemoOne='+$("#equipType_").val(),
			offset: ['50px','15%'],
	    	area: ['68%','82%'],
			btn: ['添&nbsp;&nbsp;加', '取消'],
			btnAlign: 'c', //按钮居中对齐
			yes: function(index, layero){
				//获取iframe窗口的body对象
	        	var body = layer.getChildFrame('body', index);
	        	//找到body对象下被选中的设备位号值
	        	var value = body.find(".layui-table-click td[data-field='equPositionNum']").find("div").text();
	        	equipName = body.find(".layui-table-click td[data-field='equName']").find("div").text()
	        	$("#positionNum_").val(value);
	        	layer.close(index); //如果设定了yes回调，需进行手工关闭
			}
		})	
	})
})