//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});

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
	var lubricationTable = table.render({
		elem: '#lubricationOil-report',
		method: 'get',
		url: '/iot_equipment/lubrication/add/change/record/report',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:80,
		page: true,   //关闭分页
		request: {
		    pageName: 'page' //页码的参数名称，默认：page
		    ,limitName: 'limit' //每页数据量的参数名，默认：limit
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data     
			console.log(data);
			if(data != null && data.length != 0){
				for(var i=0;i<data.length;i++){
					if(data[i].operatetype == "加油"){
						data[i].addAmount = data[i].ramount;
					}else if(data[i].operatetype == "换油"){
						data[i].changeAmount = data[i].ramount;
					}
				}
			}
			
		    return {
		      "code": res.code, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "count": res.count,  //解析数据总量
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'positionnum', title:'设备位号', rowspan: 2, sort:false, align:'center'},
			{field:'pplace', title:'润滑部位', rowspan: 2, sort:false, align:'center'},
			{title:'润滑油(脂)', colspan: 2, align:'center'},
			{title:'数量', colspan: 2, align:'center'},
			{field:'ptime', title:'时间', rowspan: 2, sort:false, align:'center'},
			{field:'excutor', title:'操作人员', rowspan: 2, sort:false, align:'center'},
			{field:'rnote', title:'备注', rowspan: 2, sort:false, align:'center'}
		 ],[
			 {field:'osign', title:'牌号', sort:false, align:'center'},
			 {field:'manufacture', title:'生产厂家', sort:false, align:'center'},
			 {field:'addAmount', title:'补充', sort:false, align:'center'},
			 {field:'changeAmount', title:'更换', sort:false, align:'center'}
		 ]]
	});
	
	/**
	 * 表格重载
	 */
	function reloadTable(){
		lubricationTable.reload({
			page: {
			    curr: 1 //重新从第 1 页开始
			},
			where: {
				'positionnum': $("#positionnum").val(),
				'tname': $("#tname").val(),
				'startDate': $("#startDate").val(),
				'endDate': $("#endDate").val()
			}
		})
	}
	
	/**
	 * 监听查询按钮事件
	 */
	$("#query-record").click(function(){
		reloadTable();
	})
	
	/**
	 * 监听导出按钮事件
	 */
	$("#export-record").click(function(){
		$("#export-record").attr({"href": "/iot_equipment/lubrication/export/report/record?"
			+'positionnum='+$("#positionnum").val()+'&tname='+$("#tname").val()+'&startDate='+$("#startDate").val()+'&endDate='+$("#endDate").val()})
	})
})