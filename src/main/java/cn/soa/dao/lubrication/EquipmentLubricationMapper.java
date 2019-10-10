package cn.soa.dao.lubrication;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import cn.soa.entity.LubricationMothlyReport;
import cn.soa.entity.LubricationRecordReport;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.entity.lubrication.LubricateEquipmentRecord;

/**
 * 设备大事件 持久层
 * @author Luo Guimao
 * Storage 入库
 */
@Mapper
public interface EquipmentLubricationMapper {
	
	/**
	 * 新增润滑换油设备
	 * @author Luo Guimao
	 * @param LubricateEquipment
	 */
	Integer insertLubEqui(@Param("lubequi")LubricateEquipment lubricateEquipment);
	
	/**
	 * 新增换油部位
	 * @param LubricateEquipmentPlace
	 */
	Integer insertLubPlace(@Param("lubequipl")LubricateEquipmentPlace lubricateEquipmentPlace);
	
	/**
	 * 新增换油记录
	 * @param LubricateEquipmentRecord
	 */
	Integer insertLubRecord(@Param("lubequire")LubricateEquipmentRecord lubricateEquipmentRecord);
	
	/**
	 * 查询换油设备
	 * @param lubricateEquipment
	 * @return
	 */
	List<LubricateEquipment> findLubEqui(LubricateEquipment lubricateEquipment);
	
	/**
	 * 查询换油部位
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	List<LubricateEquipmentPlace> findLubPlace(
			@Param("page") Integer page,
			@Param("limit") Integer limit);
	
	/**
	 *查询换油记录 
	 * @param lubricateEquipmentRecord
	 * @return
	 */
	List<LubricateEquipmentRecord> findLubRecord(LubricateEquipmentRecord lubricateEquipmentRecord);
	
	/**
	 * 按条件分页查询换油设备
	 * @param equip 查询条件
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  换油设备数据列表
	 */
	List<LubricateEquipment> findEquipLubricationByPage(
			@Param("equip") LubricateEquipment equip,
			@Param("page") Integer page,
			@Param("limit") Integer limit);
	
	/**
	 * 统计按条件分页查询换油设备的总条数
	 * @param equip 查询条件
	 * @return
	 */
	Integer countEquipLubricationByPage(LubricateEquipment equip);
	
	/**
	 * 根据润滑设备lid查询设备换油记录
	 * @param lid 润滑设备lid
	 * @param page 第几页
	 * @param limit 每页条数
	 * @return  设备换油记录数据列表
	 */
	List<LubricateEquipmentRecord> findEquipLubricationRecordByLid(
			@Param("lid") String lid,
			@Param("page") Integer page,
			@Param("limit") Integer limit);
	
	/**
	 * 根据润滑设备lid查询设备换油记录
	 * @param lid 润滑设备lid
	 * @return
	 */
	Integer countEquipLubricationRecordByLid(@Param("lid") String lid);
	
	/**
	 * 临时换油日期跟踪
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param page 第几页
	 * @param limit 每页条数
	 * return 
	 */
	List<LubricateEquipmentPlace> findEquipLubricationTrace(
			@Param("positionnum") String positionnum,
			@Param("tname") String tname,
			@Param("page") Integer page,
			@Param("limit") Integer limit);
	
	Integer countEquipLubricationTrace(
			@Param("positionnum") String positionnum,
			@Param("tname") String tname);
	
	/**
	 * 按月统计每种润滑油使用量
	 */
	List<LubricationMothlyReport> findRecordByYear(@Param("year") String year);

	/**
	 * 分页查询设备润滑油加油和换油记录
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param page 第几页
	 * @param limit 每页条数
	 */
	List<LubricationRecordReport> findLubricationRecordByPage(
			@Param("positionnum") String positionnum,
			@Param("tname") String tname,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate,
			@Param("page") Integer page,
			@Param("limit") Integer limit);
	
	/**
	 * 统计设备润滑油加油和换油记录总数
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 */
	Integer countLubricationRecord(
			@Param("positionnum") String positionnum,
			@Param("tname") String tname,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
	/**
	 * 设备润滑油加油和换油记录
	 * @param positionnum 设备位号
	 * @param tname 设备名称
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 */
	List<LubricationRecordReport> findLubricationRecord(
			@Param("positionnum") String positionnum,
			@Param("tname") String tname,
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
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
	 * 根据润滑部位条件查询数据
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	LubricateEquipmentPlace findLuEqPlByAll(LubricateEquipmentPlace lubricateEquipmentPlace);
	
	/**
	 * 更新润滑部位最后一次时间和下一次换油时间
	 * @param lubricateEquipmentPlace
	 * @return
	 */
	Integer updateLuEqPlByPid(LubricateEquipmentPlace lubricateEquipmentPlace);

}
