var equipment_list_table;
var equ_type_info = {
	equTypeId : getParams('equTypeId') != null
			? getParams('equTypeId').trim()
			: null,
	equType : getParams('equType') != null ? getParams('equType').trim() : null
};
var golbal_common_property = [];
var global_trdata;
layui.use(['tree', 'util', 'table', 'layer', 'upload', 'form'], function() {
	var tree = layui.tree, layer = layui.layer, util = layui.util;
	var layer = layui.layer;
	var table = layui.table;
	var $ = layui.jquery;
	var upload = layui.upload;
	var form = layui.form;

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

	// 监听表单提交事件
	form.on('submit(search_button_search)', function(data) {
				if (!equ_type_info.equTypeId) {
					layer.msg('设备分类为空，请选择设备分类！！！！', {
								icon : 2
							});
					return false;
				}
				var form_data = data.field;
				var query_data = {};
				query_data.equTypeId = equ_type_info.equTypeId;
				query_data.keyWord = form_data.key_word
				var equipmentCommonInfo = {};
				var equipmentProperties = [];
				$.each(form_data, function(key, item) {
							console.log(golbal_common_property);
							console.log(key);
							if (item == '') {
								return true;
							}

							if (golbal_common_property.indexOf(key) < 0) {
								if (key != "key_word") {
									var equipmentProperty = {};
									equipmentProperty.proNameEn = key;
									equipmentProperty.proValue = item;
									equipmentProperties.push(equipmentProperty);
								}

							} else {
								equipmentCommonInfo[key] = item;
							}
						});
				if (equipmentProperties.length > 0) {
					equipmentCommonInfo.equipmentProperties = equipmentProperties;
				}
				console.log(equipmentCommonInfo.length);
				if (equipmentCommonInfo.length != {}) {
					query_data.equipmentCommonInfo = equipmentCommonInfo;
				}
				query_data.form_data = form_data;
				// console.log(query_data);
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
						layuer.close(index);
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

	/**
	 * 注册tab右键菜单点击事件
	 */
	$(".rightmenu li").click(function() {
		if ($(this).attr("data-type") == "equ_detail") {
			// 查看设备详情
			// layer.msg('equ_detail');
			window.location.href = 'equipment_detail.html?equId='
					+ escape(global_trdata.equId) + '&equTypeId='
					+ escape(global_trdata.equTypeId) + '&equPositionNum='
					+ escape(global_trdata.equPositionNum);
		} else if ($(this).attr("data-type") == "equ_accessory") {
			// 查看设备附件
			// layer.msg('equ_accessory');
			var url = "http://192.168.18.114:10238/soa-elfinder-web/?dirClass="
					+ global_trdata.equId + "#elf_A_aGVscA_E_E";
			window.location.href = url;

		} else if ($(this).attr("data-type") == "equ_history") {
			// 查看设备历史
			// layer.msg('equ_history');
			window.location.href = 'equipment_history.html?equId='
					+ escape(global_trdata.equId) + '&equTypeId='
					+ escape(global_trdata.equTypeId) + '&equPositionNum='
					+ escape(global_trdata.equPositionNum);
		}
		$('.rightmenu').hide();
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
						// fixed : 'left',
						rowspan : 2

					}, {
						title : '序号',
						type : 'numbers',
						// fixed : 'left',
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

			table.render({
				elem : '#equipment_list_table',
				url : '/iot_equipment/equipmentLedger/getEquipmentList',
				height : '790',
				toolbar : '#toolbar',
				title : '设备台账',
				method : 'post',
				contentType : 'application/json',
				where : query_data,
				cols : cols,
				page : true,
				loading : true,
				limits : [30, 60, 90, 120, 150],
				limit : 30,
				parseData : function(res) {
					var data = res.data;
					console.log(res.data);
					if (data) {
						$.each(data, function(index, item) {
									$.each(item.equipmentProperties, function(
													index1, item1) {
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
					load_upload();// 初始化导入
					load_rightMenu(res.data)// 初始化右键菜单
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

	var query_data = {};

	query_data.equipmentList = data;
	query_data.operatePeople = decodeURI(getCookie('name')).replace(/\"/g, '');
	query_data.operateType = "2";// 2-删除

	$.ajax({
				url : '/iot_equipment/equipmentLedger/delEquipmentRecord',
				type : 'post',
				data : JSON.stringify(query_data),
				dataType : 'json',
				contentType : 'application/json',
				success : function(res) {
					if (res.code == 0) {
						layer.close(del_msg);
						layer.msg('数据删除成功！！！');
						load_table(table, {
									"equTypeId" : equ_type_info.equTypeId
								});
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

/**
 * 下载文件
 * 
 * @param {}
 *            options
 */
function DownLoadFile(options) {
	var config = $.extend(true, {
				method : 'post'
			}, options);
	var $iframe = $('<iframe id="down-file-iframe" />');
	var $form = $('<form target="down-file-iframe" method="' + config.method
			+ '" />');
	$form.attr('action', config.url);
	for (var key in config.data) {
		$form.append('<input type="hidden" name="' + key + '" value="'
				+ config.data[key] + '" />');
	}
	$iframe.append($form);
	$(document.body).append($iframe);
	$form[0].submit();
	$iframe.remove();

}

/**
 * 加载文件上传
 */
function load_upload() {

	layui.use(['tree', 'util', 'table', 'layer', 'upload', 'element'],
			function() {
				var layer = layui.layer;
				var upload = layui.upload;
				var table = layui.table;
				var element = layui.element;
				var upload_msg;
				var uploadInst = upload.render({
							elem : '#upload' // 绑定元素
							,
							url : '/iot_equipment/equipmentLedger/importEquipment' // 上传接口
							,
							data : {
								equTypeId : equ_type_info.equTypeId
							},
							accept : 'file',
							exts : 'xls',
							field : 'exportFile',
							choose : function() {
								upload_msg = layer.load();
							},
							done : function(res) {
								// 上传完毕回调
								layer.close(upload_msg);
								if (res.code == 0) {
									// 导入数据成功
									layer.msg(res.data);
									load_table(table, {
												"equTypeId" : equ_type_info.equTypeId
											});
								} else {
									// 导入数据失败
									layer.msg(res.data);
								}
							},
							error : function() {
								// 请求异常回调
								layer.close(upload_msg);
								layer.msg(res.data);
							}
						});

			});

}

/**
 * 加载高级搜索表单
 * 
 * @param {}
 *            data
 */
function load_search_form(data) {

	var html = '';
	var data_picker_ids = [];
	// 将通用属性放入
	golbal_common_property = [];

	var num = 3;
	if (screen.availWidth < 1920) {
		num = 2;
	}
	$.each(data, function(index, item) {

		if (item.propertyType == 1) {
			golbal_common_property.push(item.field);
		}
		if (index % num == 0) {
			html += '<div class="layui-form-item">'
		}
		var obj = {
			'1' : '在用',
			'2' : '备用',
			'3' : '停用',
			'4' : '闲置',
			'5' : '报废'
		};
		console.log(JSON.stringify(obj));

		switch (item.columnType) {
			case 1 :
				// 字符串格式
				html += '<div class="layui-inline">'
						+ '<label class="layui-form-label" style = "width:200px;">'
						+ item.formName + '</label>'
						+ '<div class="layui-input-inline">'
						+ '<input type="text" name="' + item.field
						+ '" placeholder=""' + '	class="layui-input">'
						+ '</div>' + '</div>';
				break;

			case 2 :
				// 日期格式(yyyy-MM-dd)
				html += '<div class="layui-inline">'
						+ '<label class="layui-form-label" style = "width:200px;">'
						+ item.formName + '</label>'
						+ '<div class="layui-input-inline">'
						+ '<input type="text" class="layui-input" id="'
						+ item.field + '"' + 'name="' + item.field
						+ '"placeholder="yyyy-MM-dd">' + '</div>' + '</div>';
				data_picker_ids.push(item.field);
				break;
			case 3 :
				var key_value = JSON.parse(item.standby1);
				html += '<div class="layui-inline">'
						+ '<label class="layui-form-label" style = "width:200px;">'
						+ item.formName + '</label>'
						+ '<div class="layui-input-inline"><select name="'
						+ item.field + '" id = "' + item.field
						+ '" lay-verify="' + item.field + '">';
				html += '<option value="">请选择</option>';
				$.each(key_value, function(key, value) {
							html += '<option value="' + key + '">' + value
									+ '</option>';
						});
				html += '</select> </div>' + '</div>';

				break;
		}
		if (index % num == (num - 1)) {
			html += '</div>';
		}
	});
	$('#key_word_search').css('display', 'none');
	$('#advanced_search_form').html(html);
	$('#advanced_search_btn').find('i').html('&#xe619;');

	layui.use(['form', 'layer', 'laydate'], function() {
				var form = layui.form;
				var laydate = layui.laydate;
				form.render();
				$.each(data_picker_ids, function(index, item) {
							// console.log(item);
							laydate.render({
										elem : '#' + item
									});

						});
			});

}
/**
 * 加载右键菜单
 * 
 * @param {}
 *            data
 */
function load_rightMenu(data) {
	$(document).bind("contextmenu", function(e) {
				return false;
			});
	// 表单鼠标右键操作
	$('.layui-table-body tr').mousedown(function(e) {
				var index = $(this).attr('data-index'); // 获取该表格行的数据
				if (e.which == 3) { // 判断时鼠标右键按下
					$(".rightmenu").show(); // 显示鼠标右键菜单列表
					var x = e.originalEvent.x + 'px'; // 获取鼠标位置x坐标
					var y = e.originalEvent.y + 'px'; // 获取鼠标位置y坐标
					$(".rightmenu").css({
								top : y, // 定位右键菜单的位置
								left : x
							});
					global_trdata = data[index]; // 将该行的数据存放到自己定义的变量中
					console.log(global_trdata);
				}
				if (e.which == 1) {
					$(".rightmenu").hide(); // 如果是点击的鼠标左键，则隐藏菜单
				}
				return false;
			});
	$(document).click(function() {
				$('.rightmenu').hide();
			});
}
