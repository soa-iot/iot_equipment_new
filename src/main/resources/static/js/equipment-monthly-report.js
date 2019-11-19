$(function(){
	
	/*
	 * 参数初始化
	 */
	var form = layui.form
	,layer = layui.layer
	,table = layui.table
	,laydate = layui.laydate
	,nowDate = new Date()
	
	,getEquipmentAllUrl = '/iot_equipment/equipmentRunning'
	,getEquipmentRunningMonthData = '/iot_equipment/equipmentRunning/runningTime'
		
	,startTime = ""
	,endTime = ""
	,searchedEquipment = getQueryUrlString("equipment_number") == null ? "":getQueryUrlString("equipment_number");
	
	console.log("searchedEquipment:"+searchedEquipment);
	
	
	//动态表头
	var cols = []
	,col = [
		{field:'id', title:'序号', sort:false, minWidth: 100,type:'numbers', fixed:'left', align:'center'}
		,{field:'position', title:'设备位号', minWidth: 120, fixed:'left', align:'center'}
		,{field:'monthTime', title:'当月累计运行时间', minWidth: 150, fixed:'right', sort:true, align:'center'}
		,{field:'repairTime', title:'大修后运行时间', minWidth: 150, fixed:'right', sort:true, align:'center'}
		,{field:'changeTime', title:'切换后运行时间', minWidth: 150, fixed:'right', sort:true, align:'center'}
		,{field:'allTime', title:'总运行时间', minWidth: 120,fixed:'right', sort:true, align:'center'}
	];
	cols.push( col );	
	var dateFlag = 31;
	while( dateFlag > 0 ){
		var temp = {};
		temp.field = dateFlag + '';
		temp.title = dateFlag + '日';
		temp.minWidth = 80;
		temp.sort = true;
		temp.align = 'center';
		col.splice( 2, 0, temp );
		dateFlag --;
	}
	p( col );
	var init = {
			getSearchCondition: function(){
				p('更新搜索条件……');
				var searchTime = $( '#querymonth' ).val();
				//searchedEquipment = $( '#position' ).val()//next().find( 'dl .layui-this' ).text();
				p(searchTime);
				if( !searchTime ) return false;				
				
				var index = judgeMonthHas31( searchTime );
				startTime = searchTime+"-01 00:00:00"
				endTime = searchTime + ( index == -1?"-30 23:59:59.99999":"-31 23:59:59.99999" );
				var $obj = $( '#position' ).next().find( 'dl .layui-this' );
				searchedEquipment = $obj.text();
				console.log(startTime + "," + endTime);
				console.log(searchedEquipment);
				return true;
			}
			,dataFormat: function( data ){
				console.log("数据格式化……");
				var tableData = []
				,otherKey = new Array(
						"position", "changeTime", "number", "repairTime", "monthTime", "allTime"
				);
				$.each(data, function(index, item){
					var tempTime = {};
					for( var key in item ){		
						
						if(key != "position" && key != "number" ){
							var num = item[key]/60
							item[key] = num.toFixed(2);
							
						}
						//console.log(item[key])
						if( $.inArray(key, otherKey) != -1) {
							var value = item[key];
							
							tempTime[key] = value==null?0:value;
							continue ;
						}					
						tempTime[key.split("-")[2]] = item[key];					
					}
					for(var i=1;i<=31;i++){
						if(tempTime[i]==null||tempTime[i]=="" ){
							tempTime[i]=0
						}
					}
					tableData.push( tempTime );
				})
				console.log(tableData);
				return tableData;
			}
			
	}
	
	
	
	
	/*
	 * 页面初始化
	 */
	//form.render(); 
	p("-------页面初始化------------");
	//日期
	var month = (nowDate.getMonth() + 1) > 9 ? nowDate.getMonth()+1 : "0" + (nowDate.getMonth()+1)
	,now = (nowDate.getDate()) > 9 ? nowDate.getDate() : "0" + (nowDate.getDate())
	,currMonth = nowDate.getFullYear() + "-" + month;
	p(currMonth);
	laydate.render({
		elem: '#querymonth'
		,type: 'month'
		,format: 'yyyy-MM'
		,value: currMonth
	});
	
	//设备
	ajax('GET', getEquipmentAllUrl, {}, function( data ){
		p("设备初始化数据回调函数……")
		p(data);
		$('#position').append( '<option value="" >请选择设备 </option>');
		$.each(data, function(index, item){
//			var flag = index==0?" selected=true":" ";
			var flag = '';
			$('#position').append(
					'<option value="' + item.rnumber+ '" >'
					+ item.positionNum + '</option>'		
			);
		})
	}, false)
	form.render();
	
	//初始化表格	
	init.getSearchCondition();
	console.log(searchedEquipment);
	var tableIns = table.render({
		elem: '#monthlyReport'
		,method: "GET"
	    ,url: getEquipmentRunningMonthData
	    
	    ,autoSort: false
	   // ,page: true
	    ,request: {
		    pageName: 'page' 
		    ,limitName: 'size' 
		}
		,where: {
			"startTime": startTime
			,"endTime": endTime
			,"equipment_number": searchedEquipment
		}
		,width:"4000"
		,toolbar: ['filter', 'exports', 'print']
		,parseData: function(res){ 
			var data = res.data;
				
			console.log(data);
			//生成表头，数据格式化
			var tableData = init.dataFormat(data);
			
		    return {
		      "code": res.code, 
		      "msg": res.msg, 
		      "count": res.count, 
		      "data": tableData      
		    };
		}
	    ,cols: cols
	});
	
	
	/*
	 * 事件绑定
	 */
	var cf = {
			searchCF: function searchF(){
				console.log('单击查询……');
				console.log(searchedEquipment);
				try{
					if ( !init.getSearchCondition() ) {
						layer.msg('请选择日期',);
						return;
					}
					var param = {
							"startTime": startTime
							,"endTime": endTime
					}
					if( searchedEquipment != "" && searchedEquipment != null && searchedEquipment != "请选择设备") {
							param.equipment_number = searchedEquipment
						}else{
							param.equipment_number = ""
							};
					console.log(param);
					tableIns.reload({
			    	    page: {
			    	    	curr: 1
			    	    }
						,where: param
			    	})
				}catch(err){
				  p(err);
				  p(err.message);
				}
		    	return false;
			}
			,exportExcelCF: function(){
				console.log('单击导出excel……');
				var param = {
						"startTime": startTime
						,"endTime": endTime
						,"page": 0
						,"size": 1000
				}				
				/*ajax( 'GET', getEquipmentRunningMonthData, param, function( data ){
					console.log(data);
					//exportExcel( htmlId, name, buttonId );
				})*/
				
				$.ajax({
				     type : "GET",
				     url : getEquipmentRunningMonthData,
				     data : param,
				     async : false, 
				     cache : true,
				     contentType : "application/x-www-form-urlencoded",
				     dataType : "json",
				     success : function( jsonData ){
				 		if( jsonData ){
				 			var data = jsonData.data;
				 			if( jsonData.code == 0 && data ){
				 				layer.msg( jsonData.msg, {icon:1} );
				 				var exl = ["设备位号"];
				 				for (var i = 1; i < 32; i++) {
				 					exl[exl.length]=i+"日"
								}
				 				exl[exl.length]="当月累计运行时间";
				 				exl[exl.length]="大修后运行时间";
				 				exl[exl.length]="切换后运行时间";
				 				exl[exl.length]="总运行时间";
				 				console.log(exl);
				 				
				 				var excldata = [];
				 				$.each(data, function(index, item){
									var tempTime = [];
									tempTime[0] = item["position"]
									var dae = {}
									for(var i=1;i<=31;i++){
										
										
									}
									for( var key in item ){	
										var da = 0;
										if (condition) {
											
										}
									
									}
									da = key.split("-")[2];	
									if (da==i) {
										console.log(item[key]);
										tempTime[tempTime.length]=item[key];
									}else{
										tempTime[tempTime.length]=0;
									}
									tempTime[tempTime.length]=item["monthTime"];
									tempTime[tempTime.length]=item["repairTime"];
									tempTime[tempTime.length]=item["changeTime"];
									tempTime[tempTime.length]=item["allTime"];
									excldata[excldata.length]= tempTime ;
								})
				 				
								console.log(excldata);
				 				//table.exportFile(col, data,"xls");
				 			}else{
				 				layer.msg( jsonData.msg, {icon:2} );
				 			}		
				 		}else{
				 			layer.msg( '请求失败', {icon:2} );
				 		}		
				     },
				     error:function(){
				    	 layer.msg('请求失败：');
				     }		       
				});
			}
	}
	$('#exportExcel').on('click', cf.exportExcelCF );
	$('#querydata').on('click', cf.searchCF );
	
	

			
	
})

