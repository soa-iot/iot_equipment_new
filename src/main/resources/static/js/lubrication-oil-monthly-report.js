//全局配置
layui.config({
	base: '../jsPackage/js/',
}).extend({
    excel: 'excel',
});

var isReload = false;

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
		url: '/iot_equipment/lubrication/year/report',
		autoSort: false,  //禁用前端自动排序
		cellMinWidth:80,
		toolbar: '#toolbar',
		defaultToolbar: [],
		page: false,   //关闭分页
		where: {
			year: function(){ return new Date().getFullYear();}
		},
		parseData: function(res){ //res 即为原始返回的数据
			var data = res.data     
			//转换数据格式
			data = parseData(data);
			if(!isReload){
				$("#title-h1").text(new Date().getFullYear()+"年润滑油月度记录表");
			}
		    return {
		      "code": res.state, //解析接口状态
		      "msg": res.msg, //解析提示文本
		      "data": data      //解析数据列表
		    };
		},
		cols: [[
			{field:'oname', title:'油品名称', width: 87, sort:false, align:'center'},
			{field:'jan', title:'1月', sort:false, align:'center'},
			{field:'feb', title:'2月', sort:false, align:'center'},
			{field:'mar', title:'3月', sort:false, align:'center'},
			{field:'apr', title:'4月', sort:false, align:'center'},
			{field:'may', title:'5月', sort:false, align:'center'},
			{field:'june', title:'6月', sort:false, align:'center'},
			{field:'july', title:'7月', sort:false, align:'center'},
			{field:'aug', title:'8月', sort:false, align:'center'},
			{field:'sept', title:'9月', sort:false, align:'center'},
			{field:'oct', title:'10月', sort:false, align:'center'},
			{field:'nov', title:'11月', sort:false, align:'center'},
			{field:'dec', title:'12月', sort:false, align:'center'},
			{field:'total', fixed:'right', title:'年累计(L)', width: 90, sort:false, align:'center'}]]
	});
	
	
	/**
	 * 监听表头下拉框选择事件
	 */
	table.on('toolbar(lubricationOil-report)', function(obj){
	  var year = $("#year").val();
	  if($("#year").val() == ''){
		  layer.msg("请选择一个年份", {icon: 7, time: 2000, offset: '130px'});
		  return;
	  }
	  
	  if(obj.event == 'query'){
		  lubricationTable.reload({
			  where: {
				  year: year
			  }
		  })
		  
	  }
	  if(obj.event == 'export'){
		  $("#export-report").attr({"href": "/iot_equipment/lubrication/export/report/"+year})
	  }
	  isReload = true;
	  $("#title-h1").text(year+"年润滑油月度记录表");
	  $("#year").val(year);
	});
	
	
	/**
	 * 月度数据进行转换满足表格要求
	 */
	function parseData(data){
		var obj = {}, result = [], total = 0;
		var month = ['jan', 'feb', 'mar', 'apr', 'may', 'june', 'july', 'aug', 'sept', 'oct', 'nov', 'dec'];
		if(data != null && data.length != 0){
			for(var i=0;i<data.length;i++){
				if(i == 0){
					obj.oid = data[i].oid;
					obj.oname = data[i].oname;
					var m = Number(data[i].month);
					obj[month[m-1]] = data[i].ramount;
					total += Number(data[i].ramount);
				}else{
					if(obj.oid == data[i].oid){
						var m = Number(data[i].month);
						obj[month[m-1]] = data[i].ramount;
						total += Number(data[i].ramount);
					}else{
						obj.total = total;
						total = 0;
						result.push(obj);
						obj = {};
						obj.oid = data[i].oid;
						obj.oname = data[i].oname;
						var m = Number(data[i].month);
						obj[month[m-1]] = data[i].ramount;
						total += Number(data[i].ramount);
					}
				}
			}
		}
		if(total != 0){
			obj.total = total;
			result.push(obj);
		}
		console.log(result);
		
		return result;
	}
	
})