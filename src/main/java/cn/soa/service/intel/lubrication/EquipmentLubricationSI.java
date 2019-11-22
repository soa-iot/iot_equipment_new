package cn.soa.service.intel.lubrication;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.soa.entity.LubricationMothlyReport;
import cn.soa.entity.LubricationRecordReport;
import cn.soa.entity.ResultJsonForTable;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;

/**
 * 设备润滑换油 业务层
 * @author Luo Guimao
 * 
 */
public interface EquipmentLubricationSI {
	
	/**
	 * 新增换油设备
	 * @return
	 */

	Integer addLub(LubricateEquipmentPlace lubricateEquipmentPlace);
	
	/**
	 * 按条件分页查询换油设备
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  换油设备数据列表
	 */
	ResultJsonForTable<List<LubricateEquipment>> findEquipLubricationByPage(
			LubricateEquipment equip, Integer page, Integer limit);
	
	
	/**
	 * 根据润滑设备lid查询设备换油记录
	 * @param lid 润滑设备lid
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  设备换油记录数据列表
	 */
	ResultJsonForTable<List<LubricateEquipmentRecord>> findEquipLubricationRecordByLid(
			String lid, Integer page, Integer limit);
	
	/**
	 * 临时换油日期跟踪
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param page 第几页
	 * @param limit 每页条数
	 * return 
	 */
	ResultJsonForTable<List<LubricateEquipmentPlace>> findEquipLubricationTrace(
			String positionnum, String tname, Integer page, Integer limit);
	
	/**
	 * 按月统计每种润滑油使用量
	 * @param year 查询年份
	 */
	List<LubricationMothlyReport> findRecordByYear(String year);
	
	/**
	 * 分页查询设备润滑油加油和换油记录
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param page 第几页
	 * @param limit 每页条数
	 */
	ResultJsonForTable<List<LubricationRecordReport>> findLubricationRecordByPage(
			String positionnum, String tname, String startDate, String endDate, Integer page, Integer limit);
	
	/**
	 * 查询设备润滑油加油和换油记录
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 */
	List<LubricationRecordReport> findLubricationRecord(
			String positionnum, String tname, String startDate, String endDate);
	
	/**
	 * 查询换油部位
	 * @param page
	 * @param limit
	 * @return
	 */
	List<LubricateEquipmentPlace> findLubPlace(
			@Param("page") Integer page,
			@Param("limit") Integer limit);
	
	/**
	 * 查询设备润滑部位
	 * @param page
	 * @param limit
	 * @return
	 */
	Integer findLubPlaceCount();
	
	/**
	 * 根据位号和换油部位查询换油部位
	 * @param lnamekey
	 * @param pplace
	 * @return
	 */
	LubricateEquipmentPlace findLubPlaceByNamekey(String lnamekey, String pplace);
	
	/**
	 * 更新润滑部位最后一次时间和下一次换油时间
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	Integer updateLuEqPlByPid(LubricateEquipmentRecord lubricateEquipmentRecord,String rtype);
	
}
