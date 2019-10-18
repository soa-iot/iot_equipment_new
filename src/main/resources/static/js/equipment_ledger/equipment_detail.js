var layer;
layui.use(['element', 'layer'], function() {
			var element = layui.element;
			layer = layui.layer;
			load_equ_detail_info();
			load_equ_maintenance_table();
			load_equ_spare_table();
			load_equ_picture();
		});

/**
 * 加载设备详情
 */
function load_equ_detail_info() {

	/**
	 * 请求显示数据
	 */
	$.ajax({
		url : '/iot_equipment/equipmentLedger/getEquipmentDisplayInfo',
		type : 'post',
		async : false,
		data : {
			equTypeId : getParams('equTypeId')
		},
		dataType : 'json',
		success : function(res) {
			if (res.code != 0) {
				layer.msg('服务器处理异常，请联系管理员！！！', {
							icon : 2
						});
				return;
			}
			var html = '';
			var num = 6;
			if (screen.availWidth < 1920) {
				num = 4;
			}
			$.each(res.data, function(index, item) {
						if (index % num == 0) {
							html += '<div class="layui-row layui-col-space10">'
						}

						html += '<div class="layui-col-xs12 layui-col-sm6 layui-col-md'
								+ (12 / num)
								+ '">'
								+ '<div id = "'
								+ item.field
								+ '">'
								+ item.formName
								+ ' : </div></div>';
						if (index % num == (num - 1)) {
							html += '</div>'
						}
					});
			$('#equ_detail').append(html);

		},
		error : function() {
			layer.msg('服务器处理异常，请联系管理员！！！', {
						icon : 2
					});
		}
	});

	/**
	 * 请求数据
	 * 
	 */
	$.ajax({
				url : '/iot_equipment/equipmentLedger/getEquipmentList',
				type : 'post',
				async : false,
				data : JSON.stringify({
							equTypeId : getParams('equTypeId'),
							equId : getParams('equId'),
							limit : 10,
							page : 1
						}),
				contentType : 'application/json',
				dataType : 'json',
				success : function(res) {
					if (res.code != 0) {
						layer.msg('服务器处理异常，请联系管理员！！！', {
									icon : 2
								});
						return;
					}
					if (res.data == null || res.data.length == 0) {
						layer.msg('未查询到数据，请联系管理员！！！', {
									icon : 2
								});
						return;
					}
					$.each(res.data[0], function(key, item) {
								var obj = $('#' + key);
								obj.text(obj.text() + item);
							});
					$.each(res.data[0].equipmentProperties, function(index,
									item) {
								var obj = $('#' + item.proNameEn);
								obj.text(obj.text() + item.proValue);
							});

				},
				error : function() {
					layer.msg('服务器处理异常，请联系管理员！！！', {
								icon : 2
							});
				}
			});

}

/**
 * 加载设备检维修表格
 */
function load_equ_maintenance_table() {
	layui.use(['table'], function() {
		var table = layui.table;
		table.render({
					elem : '#equ_maintenance_table',
					url : '/iot_equipment/equipmentDetail/getEquMaintenanceInfo',
					// toolbar : true,
					title : '检维修信息',
					where : {
						equId : getParams('equId')
					},
					contentType : 'application/json',
					method : 'post',
					height : 'full-400',
					page : true,
					loading : true,
					limits : [30, 60, 90, 120, 150],
					limit : 30,
					cols : [[{
								type : 'checkbox'
							}, {
								title : '序号',
								type : 'numbers'
							}, {
								field : 'equName',
								title : '设备名称'
							}, {
								field : 'equPositionNum',
								title : '设备位号'
							}, {
								field : 'problemDesc',
								title : '问题描述'
							}, {
								field : 'applyPeople',
								title : '上报人'
							}, {
								field : 'applyDate',
								title : '上报时间'
							}, {
								field : 'problemState',
								title : '问题状态'
							}]],
					parseData : function(res) { // 将原始数据解析成 table

					}
				});
	});
}

/**
 * 加载备品备件表格
 */
function load_equ_spare_table() {

	layui.use(['table'], function() {
				var table = layui.table;
				table.render({
							elem : '#equ_spare_table',
							url : '/iot_equipment/equipmentDetail/getEquSpareInfo',
							// toolbar : true,
							title : '备品备件',
							where : {
								equId : getParams('equId'),
								equPositionNum:getParams('equPositionNum')
							},
							contentType : 'application/json',
							method : 'post',
							height : 'full-400',
							page : true,
							loading : true,
							limits : [30, 60, 90, 120, 150],
							limit : 30,
							cols : [[{
										type : 'checkbox'
									}, {
										title : '序号',
										type : 'numbers'
									}, {
										field : 'materialName',
										title : '备件名称'
									}, {
										field : 'materialSpecifications',
										title : '备件型号'
									}, {
										field : 'unit',
										title : '单位'
									}, {
										field : 'reasonableInventory',
										title : '理想库存'
									}, {
										field : 'actualInventory',
										title : '实际库存'
									}, {
										field : 'remarkone',
										title : '备注'
									}, {
										field : 'sparePartsType',
										title : '设备类别'
									}, {
										field : 'remarktwo',
										title : '设备位号'
									}]],
							parseData : function(res) { // 将原始数据解析成 table

							}
						});
			});

}
/**
 * 加载图片
 */
function load_equ_picture() {

	layui.use(['carousel'], function() {
				var carousel = layui.carousel;
				carousel.render({
							elem : '#equ_picture',
							width : '778px',
							height : '440px',
							interval : 5000
						});
			});
}