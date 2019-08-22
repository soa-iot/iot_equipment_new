package cn.soa.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备台账备份excel表对应的实体类
 * @author Jiang, Hang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentTypeForExcel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String EQU_ID;
	private String WEL_NAME;
	private String WEL_UNIT;
	private String EQU_POSITION_NUM;
	private String EQU_MEMO_ONE;
	private String EQU_NAME;
	private String MANAGE_TYPE;
	private String EQU_MODEL;
	private String MEARING_RANGE;
	private String MEASURE_ACC;
	private String EQU_INSTALL_POSITION;
	private String EQU_MANUFACTURER;
	private String SERIAL_NUM;
	private String CHECK_CYCLE;
	private String CHECK_TIME;
	private String NEXT_CHECK_TIME;
	private String REMARK1;
	private String DEEP_LENGTH;
	private String INTER_SIZE;
	private String MEDUIM_TYPE;
	private String ORDER_NUM; 
	private String FLA_SIZE;
	private String ACTION_MODLE;
	private String HAVE_NOT;
	private String ACTUAL_MODEL;
	private String VAVLE_TYPE;
	private String CV;
	private String GAS_SOURCE;
	private String POSITIONER;
	private String PROCE_LINK_TYPE;
	private String ACTUAL;
	private String FLUX;
	private String EQU_PRODUC_DATE;
	private String PRESSURE_RANGE;
	private String EQU_WORK_TEMP;
	private String MEASURE_PRIN;
	private String ELEC_MODEL;
	private String EQU_COMMISSION_DATE;
	private String EQU_LASTPERIODIC_DATE;
	private String EQU_PERIODIC_CYCLE;
	private String EQU_PERIODIC_WARNDAYS;
	private String EXPERY_TIME;
	private String ELEC;
	private String COUNT;
	private String WEIGHT;
	private String ELECTRIC_PRES;
	private String POWER_RATE;
	private String SPEED_RAT;
	private String CAPCITY;
	private String BEFORE_BEARING1;
	private String BEFORE_BEARING2;
	private String SPINDLE_SPEED;
	private String MATERIAL;
	private String DESIGN_TUBE_TEMP;
	private String DESIGN_TUBE_PRES;
	private String OPTION_SHELL_PRESS;
	private String OPTION_SHELL_IN_TEMP;
	private String DESIGN_SHELL_PRES;
	private String DESIGN_SHELL_TEMP;
	private String OPTION_SHELL_OUT_TEMP;
	private String OPTION_SHELL_MEDUIM;
	private String OPTION_TUBE_IN_TEMP;
	private String OPTION_TUBE_OUT_TEMP;
	private String OPTION_TUBE_MEDUIM;
	private String SHELL_MATERIAL;
	private String TUBE_MATERIAL;
	private String ENGINE_NUMBER;
	private String LICENSE_NUMBER;
	private String CHASSIS_NUMBER;
	private String ENERGY_CONSUMPTION;
	private String ENERGY_CONSUMPTION_CAT;
	private String HEIGHT_ELECTRIC_TENSION;
	private String ELECTRIC_TENSION;
	private String FREQUENCY;
	private String BRAND;
	/* 新增字段 */
	private String PIPE_OUTER;
	private String PIPE_THICK;
	private String UNIT;
	private String AFTER_BEARING1;
	private String GREASE_INTERV;
	private String GREASE_QUAN;
	private String INSULATION_RATE;
	private String PROTECTION_RATE;
	private String EXPLOSION_RATE;
	private String DEVICE_TYPE;
	private String PHASE_NUMBER;
	private String CONNECTION_GROUP;
	private String HEIGHT_ELECTRIC_PRES;
	private String SNATCH_ELECTRIC_PRES;
	private String THUNDERSTRIKE_ELECTRIC_PRES;
	private String SNATCH_ELECTRIC_TENSION;
	private String PEAK_TENSION;
	private String CATEGORY;
	private String CORROSION_FATIGUE;
	private String EQU_DESIGN_TEMP;
	private String DESIGN_PRESSURE_RANGE;
	private String SURFACE_HEAT_TRANSFER;
	private String HIGH_LEFT;
	private String DISPLACEMENT;
	private String IMPELLER_MEDUIM;
	private String SPINDLE_MEDUIM;
	private String PUMP_MEDUIM;
	private String WIND_PRESSURE;
	private String EQU_MEMO_THREE;
	private String EQU_MEMO_TWO;
	private String EQU_WHETHER_PERIODIC;
	private String SECONDCLASS_EQUIPMENT;
	private String EQUIPMENT_ATTACH_URL;

}
