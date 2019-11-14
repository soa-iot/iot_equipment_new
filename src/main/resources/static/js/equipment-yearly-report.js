//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});
//从cookie中获取当前登录用户
var resavepeople = getCookie1("name").replace(/"/g,'');

//加载layui内置模块
layui.use(['jquery','form','layer','table','excel','laydate'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	getEquipmentAllUrl = '/iot_equipment/equipmentRunning',
	 excel = layui.excel,
	laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#startdate',
		type: 'year',
		trigger: 'click',
		value: new Date(),
		min: '2019-01-01'
	});
	
	/**
	 * 设备运行统计年报信息展示表
	 */
	var yearlyTable = table.render({
		elem: '#yearlyReport',
		method: 'post',
		url: '/iot_equipment/equipmen_influx/query?',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:60,
		totalRow: true,
		where: {
			"Time": $('#startdate').val()
		  // ,"equipment_number": $('#position').val()
		},
		page: true,   //开启分页
		cols: [[{field:'id', title:'序号', width:60, sort:false, type:'numbers', fixed:'left', align:'center'},
			{field:'position', title:'设备位号', width:120,  align:'center'},
			{field:'January', title:'1月', width:60,  align:'center'},
			{field:'February', title:'2月', width:60,  align:'center'},
			{field:'March', title:'3月', width:60,  align:'center'},
			{field:'April', title:'4月', width:60,  align:'center'},
			{field:'May', title:'5月', width:60,  align:'center'},
			{field:'June', title:'6月', width:60,  align:'center'},
			{field:'July', title:'7月', width:60,  align:'center'},
			{field:'August', title:'8月', width:60,  align:'center'},
			{field:'September', title:'9月', width:60,  align:'center'},
			{field:'October', title:'10月', width:70,  align:'center'},
			{field:'November', title:'11月', width:70,  align:'center'},
			{field:'December', title:'12月', width:70,  align:'center'},
			{field:'Total_time', title:'当年累计运行时间', width:150,  align:'center'},
			{field:'Modify_time', title:'大修后运行时间', width:140,  align:'center'},
			{field:'Chang_time', title:'设备更换后运行时间', width:150,  align:'center'},
			{field:'Total', title:'总运行时间', width:120,  align:'center'}
			]]
	});
	//设备
	ajax('GET', getEquipmentAllUrl, {}, function( data ){
		p("设备初始化数据回调函数……")
		p(data);
		$('#position').append( '<option value="" > 请选择设备 </option>');
		$.each(data, function(index, item){
//			var flag = index==0?" selected=true":" ";
			var flag = '';
			$('#position').append(
					'<option value="' + item.positionNum+ '" '+ flag + '>'
					+ item.positionNum + '</option>'		
			);
		})
	}, false)
	form.render();
	
	/**
	 * 监听查询功能
	 */
	form.on('submit(querydata)',function(){
		
		console.log($('#startdate').val())
		yearlyTable.reload({
    	    page: {
    	    	curr: 1
    	    }
			,where: {
				"Time": $('#startdate').val()
				 ,"equipment_number": $('#position').val()
			}
    	})
		return false;
	});
	
})