//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});

//设备id
var equID;


//加载layui内置模块
layui.use(['jquery','form','layer','table','excel','upload'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	,excel = layui.excel
	,upload = layui.upload;
	
	/**
	 * 设备信息展示表
	 */
	var equipTable = table.render({
		elem: '#equipLubrication',
		method: 'get',
		url: '/iot_equipment/lubrication/management/query',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:80,
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
		cols: [[
			{field:'id', title:'序号', width: '24.99%', sort:false, type:'numbers', align:'center'},
			{field:'lid', title:'设备lid', hide: true},
			{field:'lnamekey', title:'设备位号', width: '25%', sort:false, align:'center'},
			{field:'lname', title:'设备名称', width: '25%', sort:false, align:'center'},
			{fixed:'right', title:'换油记录', minWidth: 95, width: '25%', sort:false, align:'center', toolbar:'#barBtn'}]]
	});
	
	/**
	 * 重新加载设备测厚管理表
	 */
	function reloadEquipTable(){
		equipTable.reload({
    	   page: {
    		   curr: 1 //重新从第 1 页开始
    	   }
    	   ,where: {
    		  'lnamekey': $.trim($("#positionnum").val()),
    		  'lname': $.trim($("#equipname").val())
    	   }
    	})
	}
	
	/**
	 * 条件查询事件
	 */
	$("#query-equipment").click(function(){
		reloadEquipTable();
	})
	
	
	/**
	 * 监听每一行工具事件
	 */
	table.on('tool(equipLubrication)', function(obj){
		console.log(obj);
	    var data = obj.data;
	    if(obj.event === 'query'){
	    	//查看换油记录
	    	console.log("lid = "+data.lid);
	    	console.log(data);
	    	$("#record-div").css({"display": "block"});
	    	$("#record-legend").text(data.lnamekey+" 设备换油记录");
	    	equipRecordTable.reload({
	    	   url: '/iot_equipment/lubrication/record/query/lid'
	     	   ,page: {
	    		   curr: 1 //重新从第 1 页开始
	    	   }
	    	   ,where: {
	    		  'lid': data.lid
	    	   }
	        })
	    }
	});
	
	/**
	 * 设备换油记录信息展示表
	 */
	var equipRecordTable = table.render({
		elem: '#equipLubricationRecord',
		method: 'get',
		url: '#',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:70,
		page: true,   //开启分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data     
			if(data != null && data.length != 0){
				for(var i=0; i<data.length; i++){
					data[i].id = "第"+(i+1)+"次";
					data[i].ptime = (data[i].ptime == null)?'':(data[i].ptime.substring(0,10));
				}
			}
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count, //解析数据长度
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'id', title:'序号', minWidth: 95, sort:false, fixed:'left', align:'center'},
			{field:'pplace', title:'润滑部位', sort:false, align:'center'},
			{field:'requireoil', title:'润滑油品', sort:false, align:'center'},
			{field:'ramount', title:'更换量（L）', sort:false, align:'center'},
			{field:'ptime', title:'更换时间', sort:false, align:'center'},
			{field:'excutor', title:'操作人员', sort:false, align:'center'}]]
	});
})