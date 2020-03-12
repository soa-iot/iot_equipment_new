var equipment_list_table;
var equ_type_info = {
	equTypeId : getParams('equTypeId') != null
			? getParams('equTypeId').trim()
			: null,
	equType : getParams('equType') != null ? getParams('equType').trim() : null
};
var golbal_common_property = [];
var global_trdata;
layui.use(['tree', 'util', 'table', 'layer', 'upload', 'form','laydate'], function() {
	var tree = layui.tree, layer = layui.layer, util = layui.util;
	var layer = layui.layer;
	var table = layui.table;
	var $ = layui.jquery;
	var upload = layui.upload;
	var form = layui.form;

	var laydate = layui.laydate;

	// 执行一个laydate实例
	laydate.render({
				value : new Date(),
				elem : '#beginDate' // 指定元素
			});
	laydate.render({
				value : new Date(),
				elem : '#endDate' // 指定元素
			});

	console.log(equ_type_info);
	var equ_types_query = $.ajax({
				url : '/iot_equipment/equipmentLedger/getAllEquipmentType',
				type : 'get',
				async : false,
				dataType : 'json',
				success : function(res) {
					if (!equ_type_info.equTypeId || !equ_type_info.equType) {
						equ_type_info.equTypeId = res.data[0].id;
						equ_type_info.equType = res.data[0].title;
					}
					console.log(equ_type_info.equTypeId);
					load_tree(tree, res.data, table);
				},
				error : function() {

				}
			});
	load_table(table, {
				"equTypeId" : equ_type_info.equTypeId
			});

	// 监听工具条
	table.on('tool(equ_history)', function(obj) {
				var tr = obj.tr; // 获得当前行 tr 的 DOM 对象（如果有的话）
				var layEvent = obj.event;
				if (layEvent === 'detail') { // 查看
					// do somehing
				} else if (layEvent === 'recovery') { // 恢复
					layer.confirm('真的要恢复此行数据么', function(index) {
								layer.close(index);
								recovery_data(obj.data);
							});
				} else if (layEvent === 'edit') { // 编辑
					// do something
				} else if (layEvent === 'LAYTABLE_TIPS') {
					// do something
				}
			});

	// 监听表单提交事件
	form.on('submit(search_button_search)', function(data) {
				if (!equ_type_info.equTypeId) {
					layer.msg('设备分类为空，请选择设备分类！！！！', {
								icon : 2
							});
					return false;
				}
				var form_data = data.field;
				var query_data = form_data;
				query_data.equTypeId = equ_type_info.equTypeId;
				load_table(table, query_data);
				return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。

			});
	// 监听展开高级搜索按钮事件
	form.on('submit(open_advanced_search_btn)', function(data) {

				if (!equ_type_info.equTypeId) {
					layer.msg('设备分类为空，请选择设备分类！！！！', {
								icon : 2
							});
					return false;
				}
				$.ajax({
							url : '/iot_equipment/equipmentLedger/getSearchFormInfo',
							type : 'post',
							dataType : 'json',
							data : {
								equTypeId : equ_type_info.equTypeId
							},
							success : function(res) {
								if (res.code != 0) {
									layer.msg('表单数据获取失败，请联系管理员！！！！', {
												icon : 2
											});
									return false;
								}
								if (res.data.length == 0) {
									layer.msg('未配置高级搜索字段！！！！', {
												icon : 2
											});
									return false;
								}
								$(data.elem).attr('lay-filter',
										'close_advanced_search_btn');
								// layer.msg("高级搜索");
								// 加载高级搜索表单
								load_search_form(res.data);
							},
							error : function() {
								layer.msg('表单数据获取失败，请联系管理员！！！！', {
											icon : 2
										});
							}
						});

				// load_table(table, equ_type_info.equTypeId,
				// data.field.key_word);

				return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。

			});

	/**
	 * 关闭高级搜索
	 */
	form.on('submit(close_advanced_search_btn)', function(data) {
				$('#advanced_search_form').html('');
				$('#key_word_search').css('display', '');
				$(data.elem).attr('lay-filter', 'open_advanced_search_btn');
				$('#advanced_search_btn').find('i').html('&#xe61a;');
				return false;
			});

	// 监听事件
	table.on('toolbar(equipment_list_table)', function(obj) {
		console.log(obj);
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
						+ escape(select_data[0].equId) + '&equType='
						+ escape(equ_type_info.equType) + '&equTypeId= '
						+ escape(equ_type_info.equTypeId);
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
				// 详见upload
				break;
			case 'export' :
				// layer.msg('导出');

				if (!equ_type_info.equTypeId) {
					layer.msg('设备类型为空，无法导出！！！', {
								icon : 2
							});
				}

				layer.msg('请选择导出类型', {
					time : 2000000, // 20s后自动关闭
					btn : ['导出模板', '导出数据', '取消'],
					yes : function(index, layero) {
						DownLoadFile({
							url : '/iot_equipment/equipmentLedger/exportEquipment',
							data : {
								exportType : '1',
								equType : equ_type_info.equType,
								equTypeId : equ_type_info.equTypeId
							}
						});
						layer.close(index);
					},
					btn2 : function(index, layero) {
						DownLoadFile({
							url : '/iot_equipment/equipmentLedger/exportEquipment',
							data : {
								exportType : '2',
								equType : equ_type_info.equType,
								equTypeId : equ_type_info.equTypeId
							}
						});
						layuer.close(index);

					}
				});
				break;
			case 'change' :
				console.log();
				if (select_data.length != 1) {
					layer.msg('请至多或至少选择一条数据！！！！', {
								icon : 2
							});
					return;
				}
				if (select_data[0].equStatus != '报废') {
					layer.msg('请先将设备状态修改为报废！！！！', {
								icon : 2
							});
					return;
				}

				window.location.href = './change_equipment_record.html?equId='
						+ escape(select_data[0].equId) + '&equType='
						+ escape(equ_type_info.equType) + '&equTypeId= '
						+ escape(equ_type_info.equTypeId);
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
				id : 'id',
				isJump : true // 是否允许点击节点时弹出新窗口跳转
				,
				click : function(obj) {
					var data = obj.data; // 获取当前点击的节点数据
					// console.log(data);
					equ_type_info.equType = data.title;
					equ_type_info.equTypeId = data.id;
					var query_data = {
						"equTypeId" : data.id
					};
					$('#advanced_search_form').html('');
					$('#key_word_search').css('display', '');
					$('#advanced_search_btn').attr('lay-filter',
							'open_advanced_search_btn');
					load_table(table, query_data);
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
function load_table(table, query_data) {

	query_data.operateType = '2';

	/**
	 * 请求表头数据
	 */
	$.ajax({
		url : '/iot_equipment/equipmentLedger/getEquipmentDisplayInfo',
		type : 'get',
		dataType : 'json',
		data : {
			equTypeId : query_data.equTypeId
		},
		success : function(res) {
			console.log(res.data);
			var cols = [[{
						type : 'checkbox',
						fixed : 'left',
						rowspan : 2

					}, {
						title : '序号',
						type : 'numbers',
						fixed : 'left',
						rowspan : 2
					}, {
						title : '操作类型',
						field : 'operateType',
						width : '100',
						fixed : 'left',
						rowspan : 2,
						templet : function(d) {
							return d.operateType;

						}
					}, {
						title : '操作时间',
						field : 'operateTime',
						width : '120',
						fixed : 'left',
						rowspan : 2
					}, {
						title : '操作人员',
						field : 'operatePeople',
						width : '100',
						fixed : 'left',
						rowspan : 2
					}]];

			var data_item = {};
			var maxClass = 1;
			/**
			 * 循环表头数据，生成layui要求格式的表头数据
			 */
			$.each(res.data, function(index, item) {

						if (item.classNum > maxClass) {
							maxClass = item.classNum;
						}

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

			cols[0][0].rowspan = maxClass;
			cols[0][1].rowspan = maxClass;
			cols[0][2].rowspan = maxClass;
			cols[0][3].rowspan = maxClass;
			cols[0][4].rowspan = maxClass;

			cols[0].push({
						title : '恢复操作',
						width : '100',
						align : 'center',
						fixed : 'right',
						toolbar : '#recovery',
						rowspan : maxClass
					});
			/*
			 * cols[0].push({ title : '操作', fixed : 'right', rowspan : 2 });
			 */

			table.render({
				elem : '#equ_history',
				url : '/iot_equipment/equipmentHistory/getEquHistoryList',
				height : 'full-150',
				toolbar : '#toolbar',
				title : '设备历史版本',
				method : 'post',
				contentType : 'application/json',
				where : query_data,
				cols : cols,
				page : true,
				loading : true,
				limits : [30, 60, 90, 120, 150],
				limit : 30,
				initSort : {
					field : 'operateTime' // 排序字段，对应 cols 设定的各字段名
					,
					type : 'desc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
				},
				parseData : function(res) {
					var data = res.data;
					console.log(res.data);
					if (data) {
						$.each(data, function(index, item) {
									$.each(item.equipmentPropertiesBack,
											function(index1, item1) {
												item[item1.proNameEn] = item1.proValue;
											});
								});
					}

					return {
						"code" : res.code, // 解析接口状态
						"msg" : res.msg, // 解析提示文本
						"count" : res.count, // 解析数据长度
						"data" : data
					};

				},
				done : function(res, curr, count) {

				}

			});

		},
		error : function() {

		}

	});

}

/**
 * 恢复设备数据
 * 
 * @param {}
 *            data
 */
function recovery_data(data) {

	$.ajax({
				url : '/iot_equipment/equipmentHistory/recoveryEquInfo',
				type : 'post',
				dataType : 'json',
				contentType : 'application/json',
				data : JSON.stringify({
							equId : data.equId,
							backId : data.backId
						}),
				success : function(res) {

				},
				error : function() {

				}

			});

}
