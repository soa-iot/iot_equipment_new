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
		elem: '#startYear',
		type: 'year'
	});
});

layui.use('laydate', function(){
	var laydate = layui.laydate;
	//常规用法
	laydate.render({
		elem: '#endYear',
		type: 'year'
	});
});

var legendList = [], xAxisList = [], seriesList = [], tempList = [];

//初始化echart图表的宽度width
var width = document.body.clientWidth*0.85+"px";
$("#main").css({"width": width});

//基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));
// 指定图表的配置项和数据
var option = {
	    title: {
	        text: ''
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:legendList
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '6%',
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: xAxisList
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: seriesList
};


//加载layui内置模块
layui.use(['jquery','form','layer','table','excel','upload'], function(){
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,$ = layui.$ //使用jQuery
	,excel = layui.excel
	,upload = layui.upload;
	
	/**
	 * 查询按钮点击触发事件
	 */
	$("#query-report").click(function(){
		
		if($("#positionnum").val() == ''){
			layer.msg("设备位号不能为空", {icon: 7, time: 2000, offset: '150px'});
			return;
		}
		if($("#startYear").val() > $("#endYear").val()){
			layer.msg("起始年份不能大于结束年份", {icon: 7, time: 3000, offset: '150px'});
			return;
		}
		
		$(".data-init").css({"display": "none"});
		$(".data-display").css({"display": "none"});
		$("#no-data").css({"display": "none"});
		
		//条件查询设备测厚数据
		$.ajax({
			async: false,
			type: 'GET',
			url: '/iot_equipment/equipment/thick/record/report',
			data: {
				positionnum: $("#positionnum").val(),
				startYear: $("#startYear").val(),
				endYear: $("#endYear").val()
			},
			dataType: 'JSON',
			success: function(json){
				if(json.state == 0){
					var data = json.data;
					if(data == null || data == '' ){
						$("#no-data").css({"display": "block"});
						$("#no-data").find("h1").text($("#positionnum").val()+" 设备位号不存在");
					}else if(data.measureRecord == null || data.measureRecord.length == 0){
						$("#no-data").css({"display": "block"});
						$("#no-data").find("h1").text($("#positionnum").val()+" 设备没有测厚记录");
					}else{
						$("#positionnum_1").text($("#positionnum").val()+" 设备信息");
						$("#positionnum_2").text($("#positionnum").val()+" 测厚数势");
						$("#tname").text(data.tname==null?'':data.tname);
						$("#equModel").text(data.equModel==null?'':data.equModel);
						$("#equMemoOne").text(data.equMemoOne==null?'':data.equMemoOne);
						$("#material").text(data.material==null?'':data.material);
						$("#equProducDate").text(data.equProducDate==null?'':data.equProducDate);
						$("#equCommissionDate").text(data.equCommissionDate==null?'':data.equCommissionDate);
						$("#meduimType").text(data.meduimType==null?'':data.meduimType);
						$("#measuretype").text(data.measuretype==null?'':data.measuretype);
						var src = (data.filepath == null || data.filepath == '')?null:(data.filepath.replace(/^[CDEFG]:\\equipment-thick\\/g,'/iot_equipment/picture/'));
						$("#thick-record-img").attr({"src": src});
						initData(data.measureRecord);
						 
						//使用刚指定的配置项和数据显示图表。
						myChart.setOption(option);
						$(".data-display").css({"display": "block"});
					}
				}
			},
			error: function(){
				
			}
		})
	});
	
	/**
	 * 初始化echart各个列表数据
	 */
	
	function initData(data){
		legendList.length = 0; 
		xAxisList.length = 0; 
		seriesList.length = 0; 
		tempList.length = 0;
		var count = 0;
		var obj = {};
		obj.type = 'line';
		obj.data = [];
		for(var i=0; i<data.length; i++){
			if(xAxisList.indexOf(data[i].measuretime) == -1){
				xAxisList.push(data[i].measuretime);
				if(count == 0){
					legendList.push("检测点1");
					obj.name = "检测点1";
					obj.stack = count;
					tempList.push(data[i].positionnum);
					count++;
				}
				obj.data.push(data[i].measurevalue);
				
			}else{
				if(tempList.indexOf(data[i].positionnum) == -1){
					count++;
					legendList.push("检测点"+count);
					tempList.push(data[i].positionnum);
					seriesList.push(obj);
					obj = {};
					obj.type = 'line';
					obj.data = [];
					obj.name = "检测点"+count;
				}
				obj.data.push(data[i].measurevalue);
			}
		}
		seriesList.push(obj);
	}
})