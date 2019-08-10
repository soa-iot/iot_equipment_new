package cn.soa.service.intel;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import cn.soa.entity.EquipmentTypeBackup;
import cn.soa.entity.ResultJsonForTable;

/**
 * 设备台账导入备份 业务层
 * @author Jiang, Hang
 * @since 2019-08-01
 */
public interface EquipmentTypeBackupSI {
	
	/**
	 * 添加一条设备台账导入备份信息
	 * @param equipBackup 设备台账导入备份信息
	 * @return bid
	 */
	String addEquipmentBackupInfo(EquipmentTypeBackup equipBackup);
	
	/**
	 * 条件查询设备台账导入备份信息
	 * @param bname 设备类型
	 * @param startDate 开始时间
	 * @param endDate  结束时间
	 * @param bperson  操作人 
	 * @return 设备台账导入备份信息列表
	 */
	ResultJsonForTable<List<EquipmentTypeBackup>> findByCondition( String bname, String startDate, 
			String endDate, String bperson, Integer page, Integer limit);
	
	/**
	 * 根据bid查询设备台账导入备份信息
	 * @param bid 主键id
	 * @return 设备台账导入备份信息
	 */
	ResponseEntity<byte[]> findByBid(String bid);
	
	/**
	 * 根据bid删除设备台账导入备份信息
	 * @param bid 主键id
	 * @return 受影响行数
	 */
	Integer deleteByBid(String bid);
	
	/**
	 * 还原设备台账导入备份信息
	 * @param bid 主键id
	 * @return 是否还原成功
	 */
	boolean RollbackData(String bid);
}
