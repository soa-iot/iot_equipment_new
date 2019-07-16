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
		elem: '#startdate',
		type: 'year'
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
	 * 设备运行统计年报信息展示表
	 */
	var yearlyTable = table.render({
		elem: '#yearlyReport',
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
		cols: [[{field:'id', title:'序号', width:60, sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'rfid', title:'设备位号', width:120, sort:true, align:'center'},
			{field:'applypeople', title:'1月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'2月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'3月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'4月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'5月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'6月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'7月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'8月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'9月', width:60, sort:true, align:'center'},
			{field:'applypeople', title:'10月', width:70, sort:true, align:'center'},
			{field:'applypeople', title:'11月', width:70, sort:true, align:'center'},
			{field:'applypeople', title:'12月', width:70, sort:true, align:'center'},
			{field:'applypeople', title:'当年累计运行时间', width:150, sort:true, align:'center'},
			{field:'applypeople', title:'大修后运行时间', width:140, sort:true, align:'center'},
			{field:'applypeople', title:'设备更换后运行时间', width:150, sort:true, align:'center'},
			{field:'applypeople', title:'总运行时间', width:120, sort:true, align:'center'}
			]]
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