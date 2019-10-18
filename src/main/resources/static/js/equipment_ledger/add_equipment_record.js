layui.use(['tree', 'util', 'table', 'layer', 'form', 'laydate'], function() {
	var tree = layui.tree, layer = layui.layer, util = layui.util;
	var layer = layui.layer;
	var table = layui.table;
	var $ = layui.jquery;
	var laydate = layui.laydate;
	var form = layui.form;

	// 出厂时间
	laydate.render({
				elem : '#equProducDate'
			});

	// 投用时间
	laydate.render({
				elem : '#equCommissionDate'
			});
	/**
	 * 设备类型加载
	 */
	$('#equTypeId').html('<option selected="selected" value="'
			+ getParams('equTypeId').trim() + '">' + getParams('equType')
			+ '</option>');

	form.render('select');

	/**
	 * 获取扩属性表单信息并加载
	 */
	$.ajax({
				url : '/iot_equipment/equipmentLedger/getFormInfo',
				data : {
					equTypeId : getParams('equTypeId').trim()
				},
				dataType : 'json',
				type : 'post',
				success : function(res) {
					// console.log(res.data);
					load_form(res.data);
				},
				error : function() {

				}
			});

	/**
	 * 监听提交
	 */
	form.on('submit(search_button_save)', function(data) {

		var save_msg = layer.open({
					type : 3
				});

		var form_data = data.field;
		var common_properties = ['equName', 'equStatus', 'equPositionNum',
				'processUnits', 'equModel', 'equTypeId', 'assetValue',
				'equManufacturer', 'equProducDate', 'equCommissionDate',
				'equInstallPosition'];

		var save_data = {};
		var equId = guid();
		save_data.equId = equId;
		var equipmentProperties = [];
		for (key in form_data) {
			if (common_properties.indexOf(key) >= 0) {
				// console.log(data[key]);
				save_data[key] = form_data[key];
			} else {
				var obj = {};
				obj.equId = equId;
				obj.proNameCn = '';
				obj.proNameEn = key;
				obj.proValue = form_data[key];
				equipmentProperties.push(obj);
			}
		}
		save_data.equipmentProperties = equipmentProperties;
		save_data.equType = getParams('equType');
		console.log(save_data);
		$.ajax({
					url : '/iot_equipment/equipmentLedger/addEquipmentRecord',
					type : 'post',
					data : JSON.stringify(save_data),
					dataType : 'json',
					contentType : 'application/json',
					success : function(res) {
						if (res.code == 0) {
							layer.close(save_msg);
							layer.msg('数据保存成功！！！');
							window.location.href = './equipment_ledger.html?equType='
									+ escape(getParams('equType').trim())
									+ '&equTypeId='
									+ escape(getParams('equTypeId').trim());

						} else {
							layer.close(save_msg);
							layer.msg('数据保存失败，请联系管理员！！！', {
										icon : 2
									});
						}

					},
					error : function() {
						layer.close(save_msg);
						layer.msg('数据保存失败，请联系管理员！！！', {
									icon : 2
								});
					}
				});

		return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
	});

	/**
	 * 监听取消按钮
	 */

	$('#search_button_cancel').on('click', function() {

		window.location.href = './equipment_ledger.html?equType='
				+ escape(getParams('equType').trim()) + '&equTypeId='
				+ escape(getParams('equTypeId').trim());
		return false;
	});

	/** *****************************************************function******************************************************************************** */
	/**
	 * 加载表单数据
	 */
	function load_form(data) {

		var data_picker_ids = [];

		var html = '';
		$.each(data, function(index, item) {
					if (index % 2 == 0) {
						html += '<div class="layui-form-item">'
					}

					switch (item.columnType) {
						case 1 :
							// 字符串格式
							html += '<div class="layui-inline">'
									+ '<label class="layui-form-label">'
									+ item.formName + '</label>'
									+ '<div class="layui-input-inline">'
									+ '<input type="text" name="' + item.field
									+ '" placeholder=""'
									+ '	class="layui-input">' + '</div>'
									+ '</div>';
							break;

						case 2 :
							// 日期格式(yyyy-MM-dd)
							html += '<div class="layui-inline">'
									+ '<label class="layui-form-label">'
									+ item.formName
									+ '</label>'
									+ '<div class="layui-input-inline">'
									+ '<input type="text" class="layui-input" id="'
									+ item.field + '"' + 'name="' + item.field
									+ '"placeholder="yyyy-MM-dd">' + '</div>'
									+ '</div>';
							data_picker_ids.push(item.field);
							break;
					}
					if (index % 2 == 1) {
						html += '</div>';
					}
				});
		$('#equipment_properties').after(html);
		$.each(data_picker_ids, function(index, item) {
					console.log(item);
					laydate.render({
								elem : '#' + item
							});

				});
		$('#equipment_add_form').css('display', 'block');
	}

});