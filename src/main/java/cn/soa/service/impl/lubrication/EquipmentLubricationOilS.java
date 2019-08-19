package cn.soa.service.impl.lubrication;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.lubrication.EquipmentLubricationOilMapper;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;
import cn.soa.service.intel.lubrication.EquipmentLubricationOilSI;

/**
 * 设备润滑 业务层
 * @author Luo Guimao
 * 
 */
@Service
public class EquipmentLubricationOilS implements EquipmentLubricationOilSI{
	
	@Autowired
	private EquipmentLubricationOilMapper equipmentLubricationOilMapper;
	
	/**
	 * 新增油品
	 * @return
	 */
	@Override
	@Transactional
	public Integer addOil(EquipmentLubricationOil equipmentLubricationOil,String userid,String rnote)  {
		
		
			Integer row = insertOil(equipmentLubricationOil);
			
			System.err.println("equipmentLubricationOil:"+ equipmentLubricationOil.toString());
			EquipmentOilRecord equipmentOilRecord = new EquipmentOilRecord();
			
			equipmentOilRecord.setOid(equipmentLubricationOil.getOid());
			equipmentOilRecord.setRtime(new Date());
			equipmentOilRecord.setRinout("加");
			equipmentOilRecord.setRamount(equipmentLubricationOil.getOstock());
			equipmentOilRecord.setUserid(userid);
			equipmentOilRecord.setRtype("油品入库");
			equipmentOilRecord.setRnote(rnote);
			
			
			if (row > 0) {
				Integer row1 = insertRecord(equipmentOilRecord);
				if (row1 > 0) {
					return row1;
				}else {
					return -1;
				}
			}else {
				return -1;
			}
	}
	/**
	 * 查询出入库记录
	 */
	@Override
	public List<EquipmentOilRecordVO> queryOilAll(Integer page) {
		
		return findOilAll(page);
	};
	
	
	/**
	 * 查询所有油品
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	private List<EquipmentOilRecordVO> findOilAll(Integer page){
		return equipmentLubricationOilMapper.findOilAll(page);
		
	}
	/**
	 * 新增油品 持久层实现方法
	 * @return
	 */
	private Integer insertOil(EquipmentLubricationOil equipmentLubricationOil) {
		
		return equipmentLubricationOilMapper.insertOil(equipmentLubricationOil);
	}
	
	/**
	 * 设备润滑新增油品出入库记录
	 * @author Luo Guimao
	 * @param equipmentLubricationOil
	 * @return
	 */
	private Integer insertRecord(EquipmentOilRecord equipmentOilRecord) {
		return equipmentLubricationOilMapper.insertRecord(equipmentOilRecord);
	}

}
