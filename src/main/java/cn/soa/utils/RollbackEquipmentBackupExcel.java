package cn.soa.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 还原设备台账备份数据还原操作
 * @author Jiang, Hang
 *
 */
public class RollbackEquipmentBackupExcel {
	
	
	/**
	 * 根据设备类型获取excel头信息对应的数据库表字段名
	 * @param equipmentType
	 */
	public static void getHeaderConfigInfo(String equipmentType) {

		Map<String, LinkedHashMap<String, String>> excelHeadConfig = new HashMap<String, LinkedHashMap<String, String>>();

		//压力表具体表头配置
		LinkedHashMap<String, String> excelHeadDetailConfig = new LinkedHashMap<>();
		excelHeadDetailConfig.put("order", "序号");
		excelHeadDetailConfig.put("WEL_NAME", "装置列名");
		excelHeadDetailConfig.put("WEL_UNIT", "装置单元");
		excelHeadDetailConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDetailConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadDetailConfig.put("EQU_NAME", "设备名称");
		excelHeadDetailConfig.put("MANAGE_TYPE", "器具类别");
		excelHeadDetailConfig.put("EQU_MODEL", "规格型号");
		excelHeadDetailConfig.put("MEARING_RANGE", "测量范围");
		excelHeadDetailConfig.put("MEASURE_ACC", "准确等级");
		excelHeadDetailConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadDetailConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadDetailConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadDetailConfig.put("CHECK_CYCLE", "检定周期");
		excelHeadDetailConfig.put("CHECK_TIME", "检定日期");
		excelHeadDetailConfig.put("NEXT_CHECK_TIME", "下次检定");
		excelHeadDetailConfig.put("REMARK1", "备注信息");

		//压力差压变送器具体表头配置
		LinkedHashMap<String, String> excelHeadYCBConfig = new LinkedHashMap<>();
		excelHeadYCBConfig.put("order", "序号");
		excelHeadYCBConfig.put("WEL_NAME", "装置列名");
		excelHeadYCBConfig.put("WEL_UNIT", "装置单元");
		excelHeadYCBConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadYCBConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadYCBConfig.put("EQU_NAME", "设备名称");
		excelHeadYCBConfig.put("MANAGE_TYPE", "器具类别");
		excelHeadYCBConfig.put("EQU_MODEL", "规格型号");
		excelHeadYCBConfig.put("MEARING_RANGE", "测量范围");
		excelHeadYCBConfig.put("MEASURE_ACC", "精 度");
		excelHeadYCBConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadYCBConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadYCBConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadYCBConfig.put("CHECK_CYCLE", "检定周期");
		excelHeadYCBConfig.put("CHECK_TIME", "检定日期");
		excelHeadYCBConfig.put("NEXT_CHECK_TIME", "有效期");
		excelHeadYCBConfig.put("REMARK1", "备注信息");

		//温度计具体表头配置
		LinkedHashMap<String, String> excelHeadWDJConfig = new LinkedHashMap<>();
		excelHeadWDJConfig.put("order", "序号");
		excelHeadWDJConfig.put("WEL_NAME", "装置列名");
		excelHeadWDJConfig.put("WEL_UNIT", "装置单元");
		excelHeadWDJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadWDJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadWDJConfig.put("EQU_NAME", "设备名称");
		excelHeadWDJConfig.put("EQU_MODEL", "规格型号");
		excelHeadWDJConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadWDJConfig.put("DEEP_LENGTH", "插深");
		excelHeadWDJConfig.put("INTER_SIZE", "接口尺寸");
		excelHeadWDJConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadWDJConfig.put("MEARING_RANGE", "测量范围");
		excelHeadWDJConfig.put("MEASURE_ACC", "精度");
		excelHeadWDJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadWDJConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadWDJConfig.put("CHECK_CYCLE", "检定周期");
		excelHeadWDJConfig.put("CHECK_TIME", "检定日期");
		excelHeadWDJConfig.put("NEXT_CHECK_TIME", "有效期");
		excelHeadWDJConfig.put("REMARK1", "备注信息");
		
		//温度变送器具体表头配置
		LinkedHashMap<String, String> excelHeadWBConfig = new LinkedHashMap<>();
		excelHeadWBConfig.put("order", "序号");
		excelHeadWBConfig.put("WEL_NAME", "装置列名");
		excelHeadWBConfig.put("WEL_UNIT", "装置单元");
		excelHeadWBConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadWBConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadWBConfig.put("EQU_NAME", "设备名称");
		excelHeadWBConfig.put("ORDER_NUM", "分度号");
		excelHeadWBConfig.put("EQU_MODEL", "规格型号");
		excelHeadWBConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadWBConfig.put("DEEP_LENGTH", "插深");
		excelHeadWBConfig.put("INTER_SIZE", "接口尺寸");
		excelHeadWBConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadWBConfig.put("MEARING_RANGE", "测量范围");
		excelHeadWBConfig.put("MEASURE_ACC", "精度");
		excelHeadWBConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadWBConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadWBConfig.put("CHECK_TIME", "测试日期");
		excelHeadWBConfig.put("CHECK_CYCLE", "周期");
		excelHeadWBConfig.put("REMARK1", "备注信息");
		
		//气动切断阀具体表头配置
		LinkedHashMap<String, String> excelHeadQDFConfig = new LinkedHashMap<>();
		excelHeadQDFConfig.put("order", "序号");
		excelHeadQDFConfig.put("WEL_NAME", "装置列名");
		excelHeadQDFConfig.put("WEL_UNIT", "装置单元");
		excelHeadQDFConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadQDFConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadQDFConfig.put("EQU_NAME", "设备名称");
		excelHeadQDFConfig.put("EQU_NAME", "设备名称");
		excelHeadQDFConfig.put("EQU_MODEL", "规格型号");
		excelHeadQDFConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadQDFConfig.put("MEDUIM_TYPE", "介质");
		excelHeadQDFConfig.put("FLA_SIZE", "法兰规格");
		excelHeadQDFConfig.put("ACTION_MODLE", "作用方式");
		excelHeadQDFConfig.put("HAVE_NOT", "有无手轮");
		excelHeadQDFConfig.put("ACTUAL_MODEL", "执行机构型号");
		excelHeadQDFConfig.put("VAVLE_TYPE", "电磁阀型号");
		excelHeadQDFConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadQDFConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadQDFConfig.put("REMARK1", "备注信息");
		
		//气动调节阀具体表头配置
		LinkedHashMap<String, String> excelHeadTJFConfig = new LinkedHashMap<>();
		excelHeadTJFConfig.put("order", "序号");
		excelHeadTJFConfig.put("WEL_NAME", "装置列名");
		excelHeadTJFConfig.put("WEL_UNIT", "装置单元");
		excelHeadTJFConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadTJFConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadTJFConfig.put("EQU_MODEL", "规格型号");
		excelHeadTJFConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadTJFConfig.put("MEDUIM_TYPE", "介质");
		excelHeadTJFConfig.put("MEARING_RANGE", "行程");
		excelHeadTJFConfig.put("CV", "CV值");
		excelHeadTJFConfig.put("FLA_SIZE", "法兰规格");
		excelHeadTJFConfig.put("ACTION_MODLE", "作用方式");
		excelHeadTJFConfig.put("HAVE_NOT", "有无手轮");
		excelHeadTJFConfig.put("ACTUAL_MODEL", "执行机构型号");
		excelHeadTJFConfig.put("GAS_SOURCE", "气源Mpa");
		excelHeadTJFConfig.put("POSITIONER", "定位器");
		excelHeadTJFConfig.put("VAVLE_TYPE", "电磁阀");
		excelHeadTJFConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadTJFConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadTJFConfig.put("REMARK1", "备注");
		
		//液位计(含远程)具体表头配置
		LinkedHashMap<String, String> excelHeadYWJConfig = new LinkedHashMap<>();
		excelHeadYWJConfig.put("order", "序号");
		excelHeadYWJConfig.put("WEL_NAME", "装置列名");
		excelHeadYWJConfig.put("WEL_UNIT", "装置单元");
		excelHeadYWJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadYWJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadYWJConfig.put("EQU_MODEL", "规格型号");
		excelHeadYWJConfig.put("DEEP_LENGTH", "中心距离");
		excelHeadYWJConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadYWJConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadYWJConfig.put("PROCE_LINK_TYPE", "过程连接尺寸");
		excelHeadYWJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadYWJConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadYWJConfig.put("REMARK1", "备注");
		
		//流量计具体表头配置
		LinkedHashMap<String, String> excelHeadLLJConfig = new LinkedHashMap<>();
		excelHeadLLJConfig.put("order", "序号");
		excelHeadLLJConfig.put("WEL_NAME", "装置列名");
		excelHeadLLJConfig.put("WEL_UNIT", "装置单元");
		excelHeadLLJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadLLJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadLLJConfig.put("EQU_NAME", "设备名称");
		excelHeadLLJConfig.put("EQU_MODEL", "规格型号");
		excelHeadLLJConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadLLJConfig.put("MEDUIM_TYPE", "介质");
		excelHeadLLJConfig.put("ACTUAL", "流量(max)");
		excelHeadLLJConfig.put("FLUX", "流量(正常)");
		excelHeadLLJConfig.put("MEASURE_ACC", "精 度");
		excelHeadLLJConfig.put("MEARING_RANGE", "测量范围");
		excelHeadLLJConfig.put("PROCE_LINK_TYPE", "过程安装方式");
		excelHeadLLJConfig.put("SERIAL_NUM", "编号");
		excelHeadLLJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadLLJConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadLLJConfig.put("REMARK1", "备注");
		
		//节流装置具体表头配置
		LinkedHashMap<String, String> excelHeadJLConfig = new LinkedHashMap<>();
		excelHeadJLConfig.put("order", "序号");
		excelHeadJLConfig.put("WEL_NAME", "装置列名");
		excelHeadJLConfig.put("WEL_UNIT", "装置单元");
		excelHeadJLConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadJLConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadJLConfig.put("EQU_NAME", "设备名称");
		excelHeadJLConfig.put("EQU_MODEL", "规格型号");
		excelHeadJLConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadJLConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadJLConfig.put("MEARING_RANGE", "量程");
		excelHeadJLConfig.put("PRESSURE_RANGE", "压力MPa");
		excelHeadJLConfig.put("EQU_WORK_TEMP", "温度");
		excelHeadJLConfig.put("FLA_SIZE", "法兰规格");
		excelHeadJLConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadJLConfig.put("REMARK1", "备注");
		
		//在线分析仪具体表头配置
		LinkedHashMap<String, String> excelHeadFXYConfig = new LinkedHashMap<>();
		excelHeadFXYConfig.put("order", "序号");
		excelHeadFXYConfig.put("WEL_NAME", "装置列名");
		excelHeadFXYConfig.put("WEL_UNIT", "装置单元");
		excelHeadFXYConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadFXYConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadFXYConfig.put("EQU_NAME", "设备名称");
		excelHeadFXYConfig.put("EQU_MODEL", "规格型号");
		excelHeadFXYConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadFXYConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadFXYConfig.put("MEARING_RANGE", "量程");
		excelHeadFXYConfig.put("MEASURE_ACC", "精 度");
		excelHeadFXYConfig.put("MEASURE_PRIN", "测量原理");
		excelHeadFXYConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadFXYConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadFXYConfig.put("REMARK1", "备注");
		
		//振动温度探头具体表头配置
		LinkedHashMap<String, String> excelHeadZDTTConfig = new LinkedHashMap<>();
		excelHeadZDTTConfig.put("order", "序号");
		excelHeadZDTTConfig.put("WEL_NAME", "装置列名");
		excelHeadZDTTConfig.put("WEL_UNIT", "装置单元");
		excelHeadZDTTConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadZDTTConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadZDTTConfig.put("EQU_NAME", "设备名称");
		
		//DCS/SIS系统具体表头配置
		LinkedHashMap<String, String> excelHeadDSConfig = new LinkedHashMap<>();
		excelHeadDSConfig.put("order", "序号");
		excelHeadDSConfig.put("WEL_NAME", "装置列名");
		excelHeadDSConfig.put("WEL_UNIT", "装置单元");
		excelHeadDSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDSConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadDSConfig.put("EQU_NAME", "设备名称");
		excelHeadDSConfig.put("ELEC_MODEL", "控制器电源");
		excelHeadDSConfig.put("EQU_COMMISSION_DATE", "控制器MD");
		excelHeadDSConfig.put("MEDUIM_TYPE", "控制器MQ");
		excelHeadDSConfig.put("EQU_WORK_TEMP", "AI16");
		excelHeadDSConfig.put("EQU_LASTPERIODIC_DATE", "AI18-R");
		excelHeadDSConfig.put("EQU_PERIODIC_CYCLE", "AI08-R");
		excelHeadDSConfig.put("EQU_PERIODIC_WARNDAYS", "DI32");
		excelHeadDSConfig.put("MEARING_RANGE", "DO32");
		excelHeadDSConfig.put("PRESSURE_RANGE", "串口卡(个)");
		excelHeadDSConfig.put("MANAGE_TYPE", "串口卡(对)");
		excelHeadDSConfig.put("SERIAL_NUM", "SIS卡(对)");
		excelHeadDSConfig.put("CHECK_CYCLE", "24VDC/20A(对)");
		excelHeadDSConfig.put("CHECK_TIME", "24VDC/40A(对)");
		excelHeadDSConfig.put("NEXT_CHECK_TIME", "12VDC/15A(对)");
		excelHeadDSConfig.put("EXPERY_TIME", "中继器");
		excelHeadDSConfig.put("INTER_SIZE", "AI8");
		excelHeadDSConfig.put("MEASURE_ACC", "AI浪涌");
		excelHeadDSConfig.put("DEEP_LENGTH", "DI浪涌");
		excelHeadDSConfig.put("ORDER_NUM", "继电器");
		excelHeadDSConfig.put("REMARK1", "备注");
		
		//FGS系统具体表头配置
		LinkedHashMap<String, String> excelHeadFGSConfig = new LinkedHashMap<>();
		excelHeadFGSConfig.put("order", "序号");
		excelHeadFGSConfig.put("WEL_NAME", "装置列名");
		excelHeadFGSConfig.put("WEL_UNIT", "装置单元");
		excelHeadFGSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadFGSConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadFGSConfig.put("EQU_NAME", "设备名称");
		excelHeadFGSConfig.put("MEDUIM_TYPE", "控制器8851");
		excelHeadFGSConfig.put("EQU_WORK_TEMP", "模拟量卡件8810");
		excelHeadFGSConfig.put("EQU_LASTPERIODIC_DATE", "数字量卡件8811");
		excelHeadFGSConfig.put("EQU_PERIODIC_CYCLE", "电源881312VDC/5A");
		excelHeadFGSConfig.put("EQU_PERIODIC_WARNDAYS", "电源RM240-24VDC/40A");
		excelHeadFGSConfig.put("MEARING_RANGE", "电源RM120-24VDC/20A");
		excelHeadFGSConfig.put("REMARK1", "备注");
		
		//固定式报警仪具体表头配置
		LinkedHashMap<String, String> excelHeadBJConfig = new LinkedHashMap<>();
		excelHeadBJConfig.put("order", "序号");
		excelHeadBJConfig.put("WEL_NAME", "装置列名");
		excelHeadBJConfig.put("WEL_UNIT", "装置单元");
		excelHeadBJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadBJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadBJConfig.put("EQU_NAME", "设备名称");
		excelHeadBJConfig.put("MANAGE_TYPE", "器具类别");
		excelHeadBJConfig.put("EQU_MODEL", "规格型号");
		excelHeadBJConfig.put("EQU_INSTALL_POSITION", "安装使用地点");
		excelHeadBJConfig.put("ACTUAL", "用途");
		excelHeadBJConfig.put("ACTION_MODLE", "安装方式");
		excelHeadBJConfig.put("MEASURE_ACC", "精 度");
		excelHeadBJConfig.put("MEARING_RANGE", "测量范围");
		excelHeadBJConfig.put("ELEC", "供电");
		excelHeadBJConfig.put("CV", "输出信号(mA)");
		excelHeadBJConfig.put("ORDER_NUM", "报警值");
		excelHeadBJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadBJConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadBJConfig.put("NEXT_CHECK_TIME", "有效期");
		excelHeadBJConfig.put("REMARK1", "备注");
		
		//其它具体表头配置
		LinkedHashMap<String, String> excelHeadQTConfig = new LinkedHashMap<>();
		excelHeadQTConfig.put("order", "序号");
		excelHeadQTConfig.put("WEL_NAME", "装置列名");
		excelHeadQTConfig.put("WEL_UNIT", "装置单元");
		excelHeadQTConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadQTConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadQTConfig.put("EQU_NAME", "设备名称");
		
		//P类具体表头配置
		LinkedHashMap<String, String> excelHeadPConfig = new LinkedHashMap<>();
		excelHeadPConfig.put("order", "序号");
		excelHeadPConfig.put("WEL_NAME", "装置列名");
		excelHeadPConfig.put("WEL_UNIT", "装置单元");
		excelHeadPConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadPConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadPConfig.put("EQU_NAME", "设备名称");
		excelHeadPConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadPConfig.put("MANAGE_TYPE", "类别");
		excelHeadPConfig.put("EQU_MODEL", "规格型号");
		excelHeadPConfig.put("WEIGHT", "重量");
		excelHeadPConfig.put("FLUX", "扬程");
		excelHeadPConfig.put("COUNT", "排量");
		excelHeadPConfig.put("ELECTRIC_PRES", "电压");
		excelHeadPConfig.put("POWER_RATE", "功率");
		excelHeadPConfig.put("SPEED_RAT", "转速");
		excelHeadPConfig.put("MEDUIM_TYPE", "介质");
		excelHeadPConfig.put("CAPCITY", "泵壳");
		excelHeadPConfig.put("BEFORE_BEARING1", "叶轮");
		excelHeadPConfig.put("BEFORE_BEARING2", "主轴");
		excelHeadPConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadPConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadPConfig.put("EQU_COMMISSION_DATE", "投用年月");
		excelHeadPConfig.put("REMARK1", "备注");
		
		//K类具体表头配置
		LinkedHashMap<String, String> excelHeadKConfig = new LinkedHashMap<>();
		excelHeadKConfig.put("order", "序号");
		excelHeadKConfig.put("WEL_NAME", "装置列名");
		excelHeadKConfig.put("WEL_UNIT", "装置单元");
		excelHeadKConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadKConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadKConfig.put("EQU_NAME", "设备名称");
		excelHeadKConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadKConfig.put("MANAGE_TYPE", "类别");
		excelHeadKConfig.put("EQU_MODEL", "规格型号");
		excelHeadKConfig.put("WEIGHT", "重量");
		excelHeadKConfig.put("WIND_PRESSURE", "出口风压");
		excelHeadKConfig.put("COUNT", "排量");
		excelHeadKConfig.put("SPEED_RAT", "性能转速");
		excelHeadKConfig.put("ELECTRIC_PRES", "电压");
		excelHeadKConfig.put("POWER_RATE", "功率");
		excelHeadKConfig.put("SPINDLE_SPEED", "电机转速");
		excelHeadKConfig.put("MEDUIM_TYPE", "介质");
		excelHeadKConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadKConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadKConfig.put("EQU_COMMISSION_DATE", "投用年月");
		excelHeadKConfig.put("REMARK1", "备注");
		
		//C类具体表头配置
		LinkedHashMap<String, String> excelHeadCConfig = new LinkedHashMap<>();
		excelHeadCConfig.put("order", "序号");
		excelHeadCConfig.put("WEL_NAME", "装置列名");
		excelHeadCConfig.put("WEL_UNIT", "装置单元");
		excelHeadCConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadCConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadCConfig.put("EQU_NAME", "设备名称");
		excelHeadCConfig.put("EQU_MODEL", "规格型号(φ×δ×h)");
		excelHeadCConfig.put("EQU_COMMISSION_DATE", "投用年月 ");
		excelHeadCConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadCConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadCConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadCConfig.put("MEDUIM_TYPE", "介质");
		excelHeadCConfig.put("MEARING_RANGE", "容积(m3)");
		excelHeadCConfig.put("MANAGE_TYPE", "类别");
		excelHeadCConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadCConfig.put("MEASURE_ACC", "腐蚀裕度(mm)");
		excelHeadCConfig.put("ACTION_MODLE", "铭牌位号");
		excelHeadCConfig.put("COUNT", "塔盘层数");
		excelHeadCConfig.put("WEIGHT", "重量(Kg)");
		excelHeadCConfig.put("MATERIAL", "主体材质");
		excelHeadCConfig.put("DESIGN_TUBE_TEMP", "设计条件温度(℃)");
		excelHeadCConfig.put("DESIGN_TUBE_PRES", "设计条件压力(MPa)");
		excelHeadCConfig.put("OPTION_SHELL_PRESS", "操作条件压力(MPa)");
		excelHeadCConfig.put("OPTION_SHELL_IN_TEMP", "操作条件温度(℃)");
		excelHeadCConfig.put("REMARK1", "备注");
		
		//D类具体表头配置
		LinkedHashMap<String, String> excelHeadDConfig = new LinkedHashMap<>();
		excelHeadDConfig.put("order", "序号");
		excelHeadDConfig.put("WEL_NAME", "装置列名");
		excelHeadDConfig.put("WEL_UNIT", "装置单元");
		excelHeadDConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadDConfig.put("EQU_NAME", "设备名称");
		excelHeadDConfig.put("EQU_MODEL", "规格型号(φ×δ×h)");
		excelHeadDConfig.put("EQU_COMMISSION_DATE", "投用年月 ");
		excelHeadDConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadDConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadDConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDConfig.put("MEDUIM_TYPE", "介质");
		excelHeadDConfig.put("MEARING_RANGE", "容积(m3)");
		excelHeadDConfig.put("MANAGE_TYPE", "类别");
		excelHeadDConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadDConfig.put("MEASURE_ACC", "腐蚀裕度(mm)");
		excelHeadDConfig.put("ACTION_MODLE", "铭牌位号");
		excelHeadDConfig.put("WEIGHT", "重量(Kg)");
		excelHeadDConfig.put("MATERIAL", "主体材质");
		excelHeadDConfig.put("DESIGN_TUBE_TEMP", "设计条件温度(℃)");
		excelHeadDConfig.put("DESIGN_TUBE_PRES", "设计条件压力(MPa)");
		excelHeadDConfig.put("OPTION_SHELL_PRESS", "操作条件压力(MPa)");
		excelHeadDConfig.put("OPTION_SHELL_IN_TEMP", "操作条件温度(℃)");
		excelHeadDConfig.put("REMARK1", "备注");
		
		//E类具体表头配置
		LinkedHashMap<String, String> excelHeadEConfig = new LinkedHashMap<>();
		excelHeadEConfig.put("order", "序号");
		excelHeadEConfig.put("WEL_NAME", "装置列名");
		excelHeadEConfig.put("WEL_UNIT", "装置单元");
		excelHeadEConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadEConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadEConfig.put("EQU_NAME", "设备名称");
		excelHeadEConfig.put("EQU_MODEL", "型式");
		excelHeadEConfig.put("EQU_COMMISSION_DATE", "投用年月");
		excelHeadEConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadEConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadEConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadEConfig.put("MEARING_RANGE", "换热面积(m2)");
		excelHeadEConfig.put("MANAGE_TYPE", "类别");
		excelHeadEConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadEConfig.put("WEIGHT", "重量(Kg)");
		excelHeadEConfig.put("DESIGN_SHELL_PRES", "设计条件管程压力(MPa)");
		excelHeadEConfig.put("DESIGN_SHELL_TEMP", "设计条件管程温度(℃)");
		excelHeadEConfig.put("DESIGN_TUBE_TEMP", "设计条件壳程温度(℃)");
		excelHeadEConfig.put("DESIGN_TUBE_PRES", "设计条件壳程压力(MPa)");
		excelHeadEConfig.put("OPTION_SHELL_PRESS", "操作条件管程压力(MPa)");
		excelHeadEConfig.put("OPTION_SHELL_IN_TEMP", "操作条件管程进口温度(℃)");
		excelHeadEConfig.put("OPTION_SHELL_OUT_TEMP", "操作条件管程出口温度(℃)");
		excelHeadEConfig.put("OPTION_SHELL_MEDUIM", "操作条件管程介质");
		excelHeadEConfig.put("OPTION_TUBE_IN_TEMP", "操作条件进口温度(℃)");
		excelHeadEConfig.put("OPTION_TUBE_OUT_TEMP", "操作条件管程出口温度(℃)");
		excelHeadEConfig.put("OPTION_TUBE_MEDUIM", "操作条件管程介质");
		excelHeadEConfig.put("SHELL_MATERIAL", "主体材质壳体");
		excelHeadEConfig.put("TUBE_MATERIAL", "主体材质列管");
		excelHeadEConfig.put("REMARK1", "备注");
		
		//F类具体表头配置
		LinkedHashMap<String, String> excelHeadFConfig = new LinkedHashMap<>();
		excelHeadFConfig.put("order", "序号");
		excelHeadFConfig.put("WEL_NAME", "装置列名");
		excelHeadFConfig.put("WEL_UNIT", "装置单元");
		excelHeadFConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadFConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadFConfig.put("EQU_NAME", "设备名称");
		excelHeadFConfig.put("EQU_MODEL", "规格型号");
		excelHeadFConfig.put("EQU_COMMISSION_DATE", "投用年月");
		excelHeadFConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadFConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadFConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadFConfig.put("MEDUIM_TYPE", "介质");
		excelHeadFConfig.put("MEARING_RANGE", "容积(m3)");
		excelHeadFConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadFConfig.put("MEASURE_ACC", "腐蚀裕度(mm)");
		excelHeadFConfig.put("ACTION_MODLE", "铭牌位号");
		excelHeadFConfig.put("WEIGHT", "重量(Kg)");
		excelHeadFConfig.put("MATERIAL", "主体材质");
		excelHeadFConfig.put("DESIGN_TUBE_TEMP", "设计条件温度(℃)");
		excelHeadFConfig.put("DESIGN_TUBE_PRES", "设计条件压力(MPa)");
		excelHeadFConfig.put("OPTION_SHELL_PRESS", "操作条件压力(MPa)");
		excelHeadFConfig.put("OPTION_SHELL_IN_TEMP", "操作条件温度(℃)");
		excelHeadFConfig.put("REMARK1", "备注");
		
		//H类具体表头配置
		LinkedHashMap<String, String> excelHeadHConfig = new LinkedHashMap<>();
		excelHeadHConfig.put("order", "序号");
		excelHeadHConfig.put("WEL_NAME", "装置列名");
		excelHeadHConfig.put("WEL_UNIT", "装置单元");
		excelHeadHConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadHConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadHConfig.put("EQU_NAME", "设备名称");
		excelHeadHConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadHConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadHConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadHConfig.put("MEDUIM_TYPE", "介质");
		excelHeadHConfig.put("MEARING_RANGE", "容积(m3)");
		excelHeadHConfig.put("MANAGE_TYPE", "类别");
		excelHeadHConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadHConfig.put("MEASURE_ACC", "腐蚀裕度(mm)");
		excelHeadHConfig.put("ACTION_MODLE", "铭牌位号");
		excelHeadHConfig.put("WEIGHT", "重量(Kg)");
		excelHeadHConfig.put("MATERIAL", "主体材质");
		excelHeadHConfig.put("DESIGN_TUBE_TEMP", "设计条件温度(℃)");
		excelHeadHConfig.put("DESIGN_TUBE_PRES", "设计条件压力(MPa)");
		excelHeadHConfig.put("OPTION_SHELL_PRESS", "操作条件压力(MPa)");
		excelHeadHConfig.put("OPTION_SHELL_IN_TEMP", "操作条件温度(℃)");
		excelHeadHConfig.put("REMARK1", "备注");
		
		//R类具体表头配置
		LinkedHashMap<String, String> excelHeadRConfig = new LinkedHashMap<>();
		excelHeadRConfig.put("order", "序号");
		excelHeadRConfig.put("WEL_NAME", "装置列名");
		excelHeadRConfig.put("WEL_UNIT", "装置单元");
		excelHeadRConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadRConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadRConfig.put("EQU_NAME", "设备名称");
		excelHeadRConfig.put("EQU_MODEL", "规格型号(φ×δ×h)");
		excelHeadRConfig.put("EQU_COMMISSION_DATE", "投用年月 ");
		excelHeadRConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadRConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadRConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadRConfig.put("MEARING_RANGE", "容积(m3)");
		excelHeadRConfig.put("MANAGE_TYPE", "类别");
		excelHeadRConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadRConfig.put("MEASURE_ACC", "腐蚀裕度(mm)");
		excelHeadRConfig.put("WEIGHT", "重量(Kg)");
		excelHeadRConfig.put("MATERIAL", "主体材质");
		excelHeadRConfig.put("DESIGN_TUBE_TEMP", "设计条件温度(℃)");
		excelHeadRConfig.put("DESIGN_TUBE_PRES", "设计条件压力(MPa)");
		excelHeadRConfig.put("OPTION_SHELL_PRESS", "操作条件压力(MPa)");
		excelHeadRConfig.put("OPTION_SHELL_IN_TEMP", "操作条件温度(℃)");
		excelHeadRConfig.put("REMARK1", "备注");
		
		//机修类具体表头配置
		LinkedHashMap<String, String> excelHeadJXConfig = new LinkedHashMap<>();
		excelHeadJXConfig.put("order", "序号");
		excelHeadJXConfig.put("WEL_NAME", "装置列名");
		excelHeadJXConfig.put("WEL_UNIT", "装置单元");
		excelHeadJXConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadJXConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadJXConfig.put("EQU_NAME", "设备名称");
		excelHeadJXConfig.put("EQU_MODEL", "规格型号");
		excelHeadJXConfig.put("EQU_COMMISSION_DATE", "投用年月");
		excelHeadJXConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadJXConfig.put("MANAGE_TYPE", "类别");
		excelHeadJXConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadJXConfig.put("COUNT", "数量");
		excelHeadJXConfig.put("REMARK1", "备注");
		
		//车辆类具体表头配置
		LinkedHashMap<String, String> excelHeadCLConfig = new LinkedHashMap<>();
		excelHeadCLConfig.put("order", "序号");
		excelHeadCLConfig.put("WEL_NAME", "装置列名");
		excelHeadCLConfig.put("WEL_UNIT", "装置单元");
		excelHeadCLConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadCLConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadCLConfig.put("EQU_NAME", "车辆名称");
		excelHeadCLConfig.put("EQU_MODEL", "规格型号");
		excelHeadCLConfig.put("EQU_COMMISSION_DATE", "投用年月");
		excelHeadCLConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadCLConfig.put("MANAGE_TYPE", "类别");
		excelHeadCLConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadCLConfig.put("CAPCITY", "能力");
		excelHeadCLConfig.put("ENGINE_NUMBER", "发动机号");
		excelHeadCLConfig.put("LICENSE_NUMBER", "车牌号");
		excelHeadCLConfig.put("CHASSIS_NUMBER", "底盘号");
		excelHeadCLConfig.put("ENERGY_CONSUMPTION", "能耗(L)");
		excelHeadCLConfig.put("ENERGY_CONSUMPTION_CAT", "耗能种类");
		excelHeadCLConfig.put("REMARK1", "备注");
		
		//其他类具体表头配置
		LinkedHashMap<String, String> excelHeadQConfig = new LinkedHashMap<>();
		excelHeadQConfig.put("order", "序号");
		excelHeadQConfig.put("WEL_NAME", "装置列名");
		excelHeadQConfig.put("WEL_UNIT", "装置单元");
		excelHeadQConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadQConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadQConfig.put("EQU_NAME", "设备名称");
		excelHeadQConfig.put("EQU_COMMISSION_DATE", "投用年月");
		excelHeadQConfig.put("EQU_MANUFACTURER", "制造单位");
		excelHeadQConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadQConfig.put("MANAGE_TYPE", "类别");
		excelHeadQConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadQConfig.put("COUNT", "数量");
		excelHeadQConfig.put("HEIGHT_ELECTRIC_TENSION", "主要技术参数");
		excelHeadQConfig.put("REMARK1", "备注");
		
		//A类分析仪器
		LinkedHashMap<String, String> excelHeadAConfig = new LinkedHashMap<>();
		excelHeadAConfig.put("order", "序号");
		excelHeadAConfig.put("WEL_NAME", "装置列名");
		excelHeadAConfig.put("WEL_UNIT", "装置单元");
		excelHeadAConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadAConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadAConfig.put("EQU_NAME", "设备名称");
		excelHeadAConfig.put("EQU_MODEL", "规格型号");
		excelHeadAConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadAConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadAConfig.put("EQU_POSITION_NUM", "位号");
		excelHeadAConfig.put("MEDUIM_TYPE", "介质");
		excelHeadAConfig.put("MEARING_RANGE", "测量范围");
		excelHeadAConfig.put("MANAGE_TYPE", "计量器具管理类别");
		excelHeadAConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadAConfig.put("CHECK_CYCLE", "检定周期(月)");
		excelHeadAConfig.put("CHECK_TIME", "检定日期");
		excelHeadAConfig.put("NEXT_CHECK_TIME", "下次检定日期");
		excelHeadAConfig.put("MEASURE_ACC", "准确度等级");
		excelHeadAConfig.put("ACTION_MODLE", "检定单位");
		excelHeadAConfig.put("REMARK1", "备注");
		
		//EPS电源系统
		LinkedHashMap<String, String> excelHeadEPSConfig = new LinkedHashMap<>();
		excelHeadEPSConfig.put("order", "序号");
		excelHeadEPSConfig.put("WEL_NAME", "装置列名");
		excelHeadEPSConfig.put("WEL_UNIT", "装置单元");
		excelHeadEPSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadEPSConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadEPSConfig.put("EQU_NAME", "设备名称");
		excelHeadEPSConfig.put("EQU_MODEL", "规格型号");
		excelHeadEPSConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadEPSConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadEPSConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadEPSConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadEPSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadEPSConfig.put("MEDUIM_TYPE", "工作性质");
		excelHeadEPSConfig.put("EQU_WORK_TEMP", "蓄电池型号");
		excelHeadEPSConfig.put("EQU_LASTPERIODIC_DATE", "蓄电池品牌");
		excelHeadEPSConfig.put("EQU_PERIODIC_CYCLE", "蓄电池容量");
		excelHeadEPSConfig.put("MEARING_RANGE", "防护等级");
		excelHeadEPSConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadEPSConfig.put("COUNT", "蓄电池数量");
		excelHeadEPSConfig.put("POWER_RATE", "功率");
		excelHeadEPSConfig.put("ELECTRIC_PRES", "输入电压");
		excelHeadEPSConfig.put("ELECTRIC_TENSION", "输出电压");
		excelHeadEPSConfig.put("FREQUENCY", "频率");
		excelHeadEPSConfig.put("REMARK1", "备注");
		
		//UPS电源系统具体表头配置
		LinkedHashMap<String, String> excelHeadUPSConfig = new LinkedHashMap<>();
		excelHeadUPSConfig.put("order", "序号");
		excelHeadUPSConfig.put("WEL_NAME", "装置列名");
		excelHeadUPSConfig.put("WEL_UNIT", "装置单元");
		excelHeadUPSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadUPSConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadUPSConfig.put("EQU_NAME", "设备名称");
		excelHeadUPSConfig.put("EQU_MODEL", "规格型号");
		excelHeadUPSConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadUPSConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadUPSConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadUPSConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadUPSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadUPSConfig.put("MEDUIM_TYPE", "工作性质");
		excelHeadUPSConfig.put("EQU_WORK_TEMP", "蓄电池型号");
		excelHeadUPSConfig.put("EQU_LASTPERIODIC_DATE", "蓄电池品牌");
		excelHeadUPSConfig.put("EQU_PERIODIC_CYCLE", "蓄电池容量");
		excelHeadUPSConfig.put("MEARING_RANGE", "防护等级");
		excelHeadUPSConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadUPSConfig.put("COUNT", "蓄电池数量");
		excelHeadUPSConfig.put("ELECTRIC_PRES", "输入电压");
		excelHeadUPSConfig.put("ELECTRIC_TENSION", "输出电压");
		excelHeadUPSConfig.put("FREQUENCY", "频率");
		excelHeadUPSConfig.put("REMARK1", "备注");
		
		//低压配电柜具体表头配置
		LinkedHashMap<String, String> excelHeadDPDConfig = new LinkedHashMap<>();
		excelHeadDPDConfig.put("order", "序号");
		excelHeadDPDConfig.put("WEL_NAME", "装置列名");
		excelHeadDPDConfig.put("WEL_UNIT", "装置单元");
		excelHeadDPDConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDPDConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadDPDConfig.put("EQU_NAME", "设备名称");
		excelHeadDPDConfig.put("EQU_MODEL", "规格型号");
		excelHeadDPDConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadDPDConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadDPDConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadDPDConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadDPDConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDPDConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadDPDConfig.put("ELECTRIC_PRES", "额定电压");
		excelHeadDPDConfig.put("ELECTRIC_TENSION", "额定电流");
		excelHeadDPDConfig.put("REMARK1", "备注");
		
		//电动机具体表头配置  电动机
		LinkedHashMap<String, String> excelHeadDDJConfig = new LinkedHashMap<>();
		excelHeadDDJConfig.put("order", "序号");
		excelHeadDDJConfig.put("WEL_NAME", "装置列名");
		excelHeadDDJConfig.put("WEL_UNIT", "装置单元");
		excelHeadDDJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDDJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadDDJConfig.put("EQU_NAME", "设备名称");
		excelHeadDDJConfig.put("EQU_MODEL", "规格型号");
		excelHeadDDJConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadDDJConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadDDJConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadDDJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadDDJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDDJConfig.put("MEDUIM_TYPE", "工作性质");
		excelHeadDDJConfig.put("EQU_WORK_TEMP", "蓄电池型号");
		excelHeadDDJConfig.put("EQU_LASTPERIODIC_DATE", "蓄电池品牌");
		excelHeadDDJConfig.put("EQU_PERIODIC_CYCLE", "蓄电池容量");
		excelHeadDDJConfig.put("MEARING_RANGE", "防护等级");
		excelHeadDDJConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadDDJConfig.put("COUNT", "蓄电池数量");
		excelHeadDDJConfig.put("ELECTRIC_PRES", "输入电压");
		excelHeadDDJConfig.put("ELECTRIC_TENSION", "输出电压");
		excelHeadDDJConfig.put("FREQUENCY", "频率");
		excelHeadDDJConfig.put("REMARK1", "备注");
		
		//干式变压器具体表头配置
		LinkedHashMap<String, String> excelHeadGBYQConfig = new LinkedHashMap<>();
		excelHeadGBYQConfig.put("order", "序号");
		excelHeadGBYQConfig.put("WEL_NAME", "装置列名");
		excelHeadGBYQConfig.put("WEL_UNIT", "装置单元");
		excelHeadGBYQConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadGBYQConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadGBYQConfig.put("EQU_NAME", "设备名称");
		excelHeadGBYQConfig.put("EQU_MODEL", "规格型号");
		excelHeadGBYQConfig.put("EQU_PRODUC_DATE", "出厂时间");
		excelHeadGBYQConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadGBYQConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadGBYQConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadGBYQConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadGBYQConfig.put("EQU_PERIODIC_CYCLE", "容量");
		excelHeadGBYQConfig.put("MEARING_RANGE", "绝缘等级");
		excelHeadGBYQConfig.put("MANAGE_TYPE", "接线组别");
		excelHeadGBYQConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadGBYQConfig.put("COUNT", "相数");
		excelHeadGBYQConfig.put("ELECTRIC_PRES", "高压侧电压");
		excelHeadGBYQConfig.put("ELECTRIC_TENSION", "高压侧电流");
		excelHeadGBYQConfig.put("FREQUENCY", "低压侧电压");
		excelHeadGBYQConfig.put("BRAND", "低压侧电流");
		excelHeadGBYQConfig.put("WEIGHT", "重量");
		excelHeadGBYQConfig.put("REMARK1", "备注");
		
		//高压配电柜具体表头配置
		LinkedHashMap<String, String> excelHeadGPDGConfig = new LinkedHashMap<>();
		excelHeadGPDGConfig.put("order", "序号");
		excelHeadGPDGConfig.put("WEL_NAME", "装置列名");
		excelHeadGPDGConfig.put("WEL_UNIT", "装置单元");
		excelHeadGPDGConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadGPDGConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadGPDGConfig.put("EQU_NAME", "设备名称");
		excelHeadGPDGConfig.put("EQU_MODEL", "规格型号");
		excelHeadGPDGConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadGPDGConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadGPDGConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadGPDGConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadGPDGConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadGPDGConfig.put("MEARING_RANGE", "防护等级");
		excelHeadGPDGConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadGPDGConfig.put("ACTION_MODLE", "对应设备");
		excelHeadGPDGConfig.put("ELECTRIC_PRES", "额定短时工频耐受电压");
		excelHeadGPDGConfig.put("ELECTRIC_TENSION", "额定雷电冲击耐受电压");
		excelHeadGPDGConfig.put("FREQUENCY", "额定短时耐受电流");
		excelHeadGPDGConfig.put("BRAND", "额定峰值耐受电流");
		excelHeadGPDGConfig.put("WEIGHT", "相数");
		excelHeadGPDGConfig.put("REMARK1", "备注");
		
		//现场配电箱具体表头配置
		LinkedHashMap<String, String> excelHeadXPDXConfig = new LinkedHashMap<>();
		excelHeadXPDXConfig.put("order", "序号");
		excelHeadXPDXConfig.put("WEL_NAME", "装置列名");
		excelHeadXPDXConfig.put("WEL_UNIT", "装置单元");
		excelHeadXPDXConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadXPDXConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadXPDXConfig.put("EQU_NAME", "设备名称");
		excelHeadXPDXConfig.put("EQU_MODEL", "规格型号");
		excelHeadXPDXConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadXPDXConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadXPDXConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadXPDXConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadXPDXConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadXPDXConfig.put("MEDUIM_TYPE", "设备用途");
		excelHeadXPDXConfig.put("MEARING_RANGE", "防爆区域等级");
		excelHeadXPDXConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadXPDXConfig.put("HEIGHT_ELECTRIC_TENSION", "主要运行参数");
		excelHeadXPDXConfig.put("WEIGHT", "防爆合格证号");
		excelHeadXPDXConfig.put("MATERIAL", "防爆标志");
		excelHeadXPDXConfig.put("REMARK1", "备注");
		
		//直流电源系统具体表头配置
		LinkedHashMap<String, String> excelHeadZLDYConfig = new LinkedHashMap<>();
		excelHeadZLDYConfig.put("order", "序号");
		excelHeadZLDYConfig.put("WEL_NAME", "装置列名");
		excelHeadZLDYConfig.put("WEL_UNIT", "装置单元");
		excelHeadZLDYConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadZLDYConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadZLDYConfig.put("EQU_NAME", "设备名称");
		excelHeadZLDYConfig.put("EQU_MODEL", "规格型号");
		excelHeadZLDYConfig.put("EQU_PRODUC_DATE", "生产日期");
		excelHeadZLDYConfig.put("EQU_COMMISSION_DATE", "投运日期");
		excelHeadZLDYConfig.put("EQU_INSTALL_POSITION", "安装位置");
		excelHeadZLDYConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadZLDYConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadZLDYConfig.put("EQU_WORK_TEMP", "蓄电池型号");
		excelHeadZLDYConfig.put("EQU_LASTPERIODIC_DATE", "品牌");
		excelHeadZLDYConfig.put("EQU_PERIODIC_CYCLE", "蓄电池容量");
		excelHeadZLDYConfig.put("MEARING_RANGE", "防护等级");
		excelHeadZLDYConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadZLDYConfig.put("COUNT", "蓄电池数量");
		excelHeadZLDYConfig.put("ELECTRIC_PRES", "额定交流电压");
		excelHeadZLDYConfig.put("ELECTRIC_TENSION", "额定电流");
		excelHeadZLDYConfig.put("FREQUENCY", "频率");
		excelHeadZLDYConfig.put("BRAND", "额定直流电压");
		excelHeadZLDYConfig.put("REMARK1", "备注");
		

		excelHeadConfig.put("压力表", excelHeadDetailConfig);
		excelHeadConfig.put("压力差压变送器", excelHeadYCBConfig);
		excelHeadConfig.put("温度计", excelHeadWDJConfig);
		excelHeadConfig.put("温度变送器", excelHeadWBConfig);
		excelHeadConfig.put("气动切断阀", excelHeadQDFConfig);
		excelHeadConfig.put("气动调节阀", excelHeadTJFConfig);
		excelHeadConfig.put("液位计(含远程)", excelHeadYWJConfig);
		excelHeadConfig.put("流量计", excelHeadLLJConfig);
		excelHeadConfig.put("节流装置", excelHeadJLConfig);
		excelHeadConfig.put("在线分析仪", excelHeadFXYConfig);
		excelHeadConfig.put("振动温度探头", excelHeadZDTTConfig);
		excelHeadConfig.put("DCS SIS系统", excelHeadDSConfig);
		excelHeadConfig.put("FGS系统", excelHeadFGSConfig);
		excelHeadConfig.put("固定式报警仪", excelHeadBJConfig);
		excelHeadConfig.put("其他", excelHeadQTConfig);
		excelHeadConfig.put("C类设备", excelHeadCConfig);
		excelHeadConfig.put("D类设备", excelHeadDConfig);
		excelHeadConfig.put("E类设备", excelHeadEConfig);
		excelHeadConfig.put("F类设备", excelHeadFConfig);
		excelHeadConfig.put("H类设备", excelHeadHConfig);
		excelHeadConfig.put("R类设备", excelHeadRConfig);
		excelHeadConfig.put("K类设备", excelHeadKConfig);
		excelHeadConfig.put("P类设备", excelHeadPConfig);
		excelHeadConfig.put("机修类", excelHeadJXConfig);
		excelHeadConfig.put("车辆类", excelHeadCLConfig);
		excelHeadConfig.put("其他", excelHeadQTConfig);
		excelHeadConfig.put("A类分析仪器", excelHeadAConfig);
		excelHeadConfig.put("EPS电源系统", excelHeadEPSConfig);
		excelHeadConfig.put("UPS电源系统", excelHeadUPSConfig);
		excelHeadConfig.put("低压配电柜", excelHeadDPDConfig);
		excelHeadConfig.put("电动机", excelHeadDDJConfig);
		excelHeadConfig.put("干式变压器", excelHeadGBYQConfig);
		excelHeadConfig.put("高压配电柜", excelHeadGPDGConfig);
		excelHeadConfig.put("现场配电箱", excelHeadXPDXConfig);
		excelHeadConfig.put("直流电源系统", excelHeadZLDYConfig);

	}
}
