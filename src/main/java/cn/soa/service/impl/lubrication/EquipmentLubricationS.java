package cn.soa.service.impl.lubrication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.soa.dao.lubrication.EquipmentLubricationMapper;
import cn.soa.dao.lubrication.EquipmentLubricationOilMapper;
import cn.soa.entity.lubrication.EquipmentLubricationOil;
import cn.soa.entity.lubrication.EquipmentOilRecord;
import cn.soa.entity.lubrication.EquipmentOilRecordVO;
import cn.soa.entity.lubrication.LubricateEquipment;
import cn.soa.entity.lubrication.LubricateEquipmentPlace;
import cn.soa.service.intel.lubrication.EquipmentLubricationOilSI;
import cn.soa.service.intel.lubrication.EquipmentLubricationSI;
import lombok.extern.slf4j.Slf4j;

/**
 * 设备润滑 业务层
 * @author Luo Guimao
 * 
 */
@Service
@Slf4j
public class EquipmentLubricationS implements EquipmentLubricationSI{
	
	@Autowired
	private EquipmentLubricationMapper equipmentLubricationMapper;
	
	/**
	 * 新增油品
	 * @return
	 */
	@Override
	public Integer addLub(LubricateEquipmentPlace lubricateEquipmentPlace) {
		Integer row = 0;
		List<LubricateEquipment> lubricateEquipments = equipmentLubricationMapper.findLubEqui(new LubricateEquipment().setLnamekey(lubricateEquipmentPlace.getLnamekey()));
		
		log.info("========================lubricateEquipments:"+lubricateEquipments);
		
		if (lubricateEquipments != null && lubricateEquipments.size() > 0) {
			lubricateEquipmentPlace.setLid(lubricateEquipments.get(0).getLid());
		}else {
			row = equipmentLubricationMapper.insertLubEqui(lubricateEquipmentPlace);
		}
		
		String lid = lubricateEquipmentPlace.getLid();
		log.info("=======================lid:"+lid);
		if (lid !=null && !lid.equals("")) {
			return equipmentLubricationMapper.insertLubPlace(lubricateEquipmentPlace);
		}
		
		return 0;
	}
	
}
