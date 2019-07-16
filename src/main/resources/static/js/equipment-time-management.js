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
		elem: '#updatedate',
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
	 * 设备运行信息展示表
	 */
	var yearlyTable = table.render({
		elem: '#equipmentInfo',
		method: 'post',
		url: '#',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		totalRow: true,
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
			{field:'id', title:'序号', width:'11%', sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'rfid', title:'设备位号', width:'25%', sort:true, align:'center'},
			{field:'applypeople', title:'设备名称', width:'35%', sort:true, align:'center'},
			{field:'applypeople', title:'大修/切换时间', width:'30%', sort:true, align:'center'}]]
	});
	
	/**
	 * 设备大修和更换时间信息展示表
	 */
	table.render({
		elem: '#equipmentUpdateDate',
		method: 'post',
		url: '#',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		totalRow: true,
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
			{field:'rfid', title:'类别', width:'35%', sort:false, align:'center'},
			{field:'applypeople', title:'时间', width:'45%', sort:false, align:'center'}]]
	});
	
	/**
	 * 重新加载表
	 */
	function reloadTable(sortField, sortType, piids){
		problemTable.reload({
    		url: '/iot_process/report/showproblembycondition'
    	   ,page: {
    		   curr: 1 //重新从第 1 页开始
    	   }
    	   ,where: {
    			'welName': $("#welName").val(),
    			'problemclass': $("#problemclass").val(),
    			'profession': $("#profession").val(),
    			'problemtype': $("#problemtype").val(),
    			'problemdescribe': $("#problemdescribe").val(),
    			'problemstate': $("#problemstate").val(),
    			'startTime': $("#startdate").val(),
    			'endTime': $("#enddate").val(),
    			'schedule': $("#schedule").val(),
    			'maintenanceman': $("#maintenanceman").val(),
    			'applypeople': $("#applypeople").val(),
    			'sortField': sortField,
    			'sortType': sortType,
    			'piidArray': piids
    	   }
    	})
	}
	
	 /**
	  * 监听排序事件
	  */
	 table.on('sort(reportTrace)', function(obj){
		 console.log(obj.field); //当前排序的字段名
		 console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
		 reloadTable(obj.field, obj.type, null);
	 });
})