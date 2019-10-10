/**
 * 设备详情
 * 
 */
var rfid = GetQueryString("EQU_POSITION_NUM");

function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  $.trim(decodeURI(r[2])); return null;
}
 console.log("设备位号："+rfid);

function getdata(){
	var dat;
	 $.ajax({
	     type: "GET",
	     url:'/iot_equipment/equipmentdetails/equipmentdetailsbyall',
	     data: {
	    	 "rfid":rfid
	     },
	     async: false, //默认
	     cache: true, //默认
	     contentType: "application/x-www-form-urlencoded",//默认
	     dataType: "json",//必须指定，否则根据后端判断
	     success: function(json){
	    	 console.log(json.data.length);
	    	 dat = json.data;
	     },
	     error:function(){
	    	 layer.msg('请求失败');
	     }		       
	});
	 return dat;
}
 console.log(getdata());

layui.use(['table','laydate','layer', 'form'], function(){
	
  var table = layui.table
  	  ,laydate = layui.laydate
  	  ,layer = layui.layer
  	  ,form = layui.form;
  
  table.render({
    elem: '#equipment-details-table'
    ,url:'/iot_equipment/equipmentdetails/equipmentdetailsbyall'
    ,where:{
    	"rfid":rfid
    }
    ,page: true
    ,cols: [[
    	{field:'rn', title: '序号', width:"4%"}
     /* ,{field:'equ_name', title: '设备名称'}*/
      ,{field:'equ_position_num', title: '设备位号', width:"9%"}
      ,{field:'equ_model', title: '规格型号', width:"10%"}
      ,{field:'supervisorydate', title: '检修时间', width:"10%"}
      ,{field:'impacts', title: '检修原因', width:"12%"}
      ,{field:'action', title: '检修内容', width:"19%"}
      ,{field:'process_desc', title: '更换备品备件型号规格及数量', width:"20%"}
      ,{field:'supervisoryperson',title: '检修人员', width:"10%"}
      ,{field:'others', title: '备注', width:"6%"}
    ]]
   
  });

  
  
 
})
 
  /**
	 * 生成静态表格
	 */
	function generStaticTable(tableBodyData ){
		console.log( '生成静态表格……' );
		console.log( '生成静态表格数据……' );
		console.log(tableBodyData);
		var tableBefore = '<table  border="1" cellspacing="0" font-size="18px" text-align="center" >'
			,tableEnd = "</table>";	
		
		//生成标题
		var tableBody = '<tr style="height:40px;font-size:20px;font-weight:bold;text-align:center">';
			var equ_name = 	tableBodyData[0].equ_name;
			if (equ_name  == null) {
				equ_name ="";
			}
			
			var equ_position_num = tableBodyData[0].equ_position_num;
			if (  equ_position_num == null) {
				equ_position_num ="";
			}
			var equ_model = tableBodyData[0].equ_model;
			if (equ_model  == null) {
				equ_name ="";
			}
				tableBody = tableBody + 
				'<td style="text-align=center;min-width:150px;"> 名称 </td><td colspan="2" style="text-align=center;">'  
					+ equ_name
				+ ' </td>';	
				
				tableBody = tableBody + 
				'<td style="text-align=center;min-width:150px;"> 位号 </td><td style="text-align=center;">'  
					+ equ_position_num
				+ ' </td>';
				
				tableBody = tableBody + 
				'<td style="text-align=center;min-width:150px;"> 型号 </td><td colspan="2" style="text-align=center;">'  
					+ equ_model
				+ ' </td>';
	
			tableBody = tableBody + '</tr>';
			
				tableBody = tableBody + 
				'<td style="text-align=center;min-width:150px;"> 序号</td><td style="text-align=center;">检修时间</td>'	
				
				+'<td style="text-align=center;min-width:150px;"> 检修原因 </td><td style="text-align=center;">检修内容 </td>'
			
				+'<td colspan="2" style="text-align=center;min-width:150px;"> 更换备品备件型号规格和数量 </td><td style="text-align=center;">'  
				+ '检修人员 </td><td style="text-align=center;"> 备注 </td>';

			tableBody = tableBody + '</tr>';

		//生成表数据
		//console.log( '生成静态表格-生成表头……tableBody' + tableBody );
		var tableBodyLength  = tableBodyData.length;
		
		console.log(tableBodyLength);
		
		for(var  i = 0; i < tableBodyLength; i++){
			tableBody = tableBody + '<tr style="height:40px;font-size:24px;font-weight:bold;text-align:center">';
			var rn = tableBodyData[i].rn;
			if(rn == null){
				rn = "";
			}
			var supervisorydate = tableBodyData[i].supervisorydate;
			if(supervisorydate == null){
				supervisorydate = "";			
			}
			var impacts = tableBodyData[i].impacts;
			if(impacts == null ){
				impacts = "";
			}
			var action = tableBodyData[i].action;
			if(action == null){
				action ="";
			}
			var process_desc = tableBodyData[i].process_desc;
			if(process_desc == null){
				process_desc ="";
			}
			var supervisoryperson =  tableBodyData[i].supervisoryperson;
			if(supervisoryperson == null){
				supervisoryperson = "";
			}
			
			var others =  tableBodyData[i].others;
			if(others == null){
				others = "";
			}

			tableBody = tableBody + 
			'<td style="text-align=center;min-width:150px;"> '+rn
			+'</td><td style="text-align=center;">'+ supervisorydate
			+'</td><td style="text-align=center;min-width:150px;">'+impacts
			+' </td><td style="text-align=center;">'+ action
			+' </td><td colspan="2" style="text-align=center;min-width:150px;">'+process_desc
			+'</td><td style="text-align=center;">'+ supervisoryperson
			+' </td><td style="text-align=center;"> '+ others +' </td></tr>';
		}
		
		//console.log( '生成静态表格-生成数据……tableBody' + tableBody );	
		$( 'body' ).append( '<div id="excelTempDiv" style="display:none"></div>' );
		$( '#excelTempDiv' ).html('');
		$( '#excelTempDiv' ).append( tableBefore + tableBody + tableEnd );
		console.log($( '#excelTempDiv' ));
		return false;
	}
	
  /**hy150781 hy151422
	 * 导出报表按钮单击事件执行函数
	 */
	function exportExcelBCE(){
		console.log('导出报表按钮单击事件触发……');
		
		var tableBodyData = getdata();
		generStaticTable(tableBodyData);	
		// 使用outerHTML属性获取整个table元素的HTML代码（包括<table>标签），然后包装成一个完整的HTML文档，
		// 设置charset为urf-8以防止中文乱码
	    var html = "<html><head><meta charset='utf-8' /></head><body>" 
	    	+ $( "#excelTempDiv" ).html() + "</body></html>";
	    // 实例化一个Blob对象，其构造函数的第一个参数是包含文件内容的数组，第二个参数是包含文件类型属性的对象
	    var blob = new Blob( [html], { type: "application/vnd.ms-excel" });
	    $( 'body' ).append('<a id="aExport" style="display:none"></a>');
	    var a = $( '#aExport' )[0];
	    // 利用URL.createObjectURL()方法为a元素生成blob URL
	    a.href = URL.createObjectURL(blob);
	    // 设置文件名
	    a.download = "设备详情.xls";
	    document.getElementById("aExport").click();
	    //$( '#aExport' ).click();
		return false;
	}
	
	$('#export').on('click', function(){
		console.log("点击导出")
		exportExcelBCE();
		return false;
	
	});