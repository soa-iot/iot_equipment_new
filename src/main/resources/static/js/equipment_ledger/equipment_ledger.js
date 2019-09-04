var equipment_list_table;
var equ_type_info = {};
var golbal_equId = '79EF88AEBCFB41DC98AE1AF2F4483F9A';
layui.use(['tree', 'util', 'table', 'layer'], function() {
	var tree = layui.tree, layer = layui.layer, util = layui.util;
	var layer = layui.layer;
	var table = layui.table;
	var $ = layui.jquery;

	$.ajax({
				url : '/iot_equipment/equipmentLedger/getAllEquipmentType',
				type : 'get',
				dataType : 'json',
				success : function(res) {
					load_tree(tree, res.data, table);
				},
				error : function() {

				}
			});
	load_table(table, golbal_equId);

	// 监听事件
	table.on('toolbar(equipment_list_table)', function(obj) {
		var select_data = table.checkStatus(obj.config.id).data;
		switch (obj.event) {
			case 'add' :
				// layer.msg('新增');
				// console.log(equ_type_info);
				window.location.href = './add_equipment_record.html?equType='
						+ escape(equ_type_info.equType) + '&equTypeId= '
						+ escape(equ_type_info.equTypeId);
				break;
			case 'edit' :
				// layer.msg('修改');
				if (select_data.length != 1) {
					layer.msg('请至多或至少选择一条数据！！！！', {
								icon : 2
							});
					return;
				}
				window.location.href = './edit_equipment_record.html?equId='
						+ escape(select_data[0].equId);
				break;
			case 'del' :
				if (select_data.length == 0) {
					layer.msg('请至少选择一条数据！！！！', {
								icon : 2
							});
					return;
				}
				layer.confirm('真的删除数据么', function(index) {
							layer.close(index);
							// 向服务端发送删除指令
							del_data(table, select_data);
						});
				break;
			case 'import' :
				layer.msg('导入');
				break;
			case 'export' :
				layer.msg('导出');
				$.ajax({
							url : '/iot_equipment/equipmentLedger/exportEquipment',
							dataType : 'json',
							data : JSON.stringify({
										exportType : '3',
										equType : equ_type_info.equType,
										equTypeId : equ_type_info.equTypeId
									}),
							type : 'post',
							contentType : 'application/json',
							success : function(res) {

							},
							error : function() {

							}
						});

				break;
			default :
				break;

		};
	});

});

/**
 * 加载右侧设备分类树
 * 
 * @param {}
 *            tree
 * @param {}
 *            data
 */
function load_tree(tree, data, table) {

	// 基本演示
	tree.render({
				elem : '#equipment_type',
				data : data,
				showCheckbox : false // 是否显示复选框
				,
				// id : 'demoId1',
				isJump : true // 是否允许点击节点时弹出新窗口跳转
				,
				click : function(obj) {
					var data = obj.data; // 获取当前点击的节点数据
					// console.log(data);
					equ_type_info.equType = data.title;
					equ_type_info.equTypeId = data.id;
					load_table(table, data.id);
				}
			});
}

/**
 * 加载设备台账表格
 * 
 * @param {}
 *            table
 * @param {}
 *            equ_type
 */
function load_table(table, equ_typeId) {

	/**
	 * 请求表头数据
	 */
	$.ajax({
		url : '/iot_equipment/equipmentLedger/getEquipmentDisplayInfo',
		type : 'get',
		dataType : 'json',
		data : {
			equTypeId : equ_typeId
		},
		success : function(res) {
			console.log(res.data);
			var cols = [[{
						type : 'checkbox',
						// fixed : 'left',
						rowspan : 2

					}, {
						title : '序号',
						type : 'numbers',
						// fixed : 'left',
						rowspan : 2
					}]];

			var data_item = {};
			/**
			 * 循环表头数据，生成layui要求格式的表头数据
			 */
			$.each(res.data, function(index, item) {

						// console.log(index);
						var cols_index = parseInt(item.classNum) - 1;
						// console.log(item);
						var cols_item = {};
						cols_item.title = item.title;
						cols_item.field = item.field;
						cols_item.colspan = item.colspan;
						cols_item.rowspan = item.rowspan;
						cols_item.fixed = item.fixed;
						cols_item.sort = item.sort;
						cols_item.type = item.displayType;
						cols_item.width = parseInt(item.width);

						if (item.colspan != 1) {
							cols_item.unresize = true;
						} else {
							cols_item.unresize = false;
						}

						data_item[item.field] = item.field;

						if (!cols[parseInt(item.classNum) - 1]) {
							cols.push([]);
						}

						cols[cols_index][cols[cols_index].length] = cols_item;

					});

			table.render({
				elem : '#equipment_list_table',
				url : '/iot_equipment/equipmentLedger/getEquipmentList',
				height : '790',
				toolbar : '#toolbar',
				title : '设备台账',
				method : 'post',
				contentType : 'application/json',
				where : {
					"equTypeId" : equ_typeId
				},
				cols : cols,
				page : true,
				loading : true,
				limits : [30, 60, 90, 120, 150],
				limit : 30,
				parseData : function(res) {
					var data = res.data;
					console.log(res.data);
					$.each(data, function(index, item) {
								$.each(item.equipmentProperties, function(
												index1, item1) {
											item[item1.proNameEn] = item1.proValue;
										});
							});

					return {
						"code" : res.code, // 解析接口状态
						"msg" : res.msg, // 解析提示文本
						"count" : res.count, // 解析数据长度
						"data" : data
					};

				}

			});
		},
		error : function() {

		}

	});

}

/**
 * 删除数据
 * 
 * @param {}
 *            data
 */
function del_data(table, data) {

	var del_msg = layer.msg('数据删除中。。。');

	$.ajax({
				url : '/iot_equipment/equipmentLedger/delEquipmentRecord',
				type : 'post',
				data : JSON.stringify(data),
				dataType : 'json',
				contentType : 'application/json',
				success : function(res) {
					if (res.code == 0) {
						layer.close(del_msg);
						layer.msg('数据删除成功！！！');
						load_table(table, golbal_equId);
					} else {
						layer.close(del_msg);
						layer.msg('数据保存失败，请联系管理员！！！', {
									icon : 2
								});
					}

				},
				error : function() {
					layer.close(del_msg);
					layer.msg('数据保存失败，请联系管理员！！！', {
								icon : 2
							});
				}
			});

}
