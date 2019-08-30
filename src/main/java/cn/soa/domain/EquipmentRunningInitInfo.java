package cn.soa.domain;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.soa.entity.EquipmentBigEvent;
import cn.soa.service.impl.EquipmentMoveRunningTimeS;
import cn.soa.service.intel.EquipmentMoveRunningTimeSI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentRunningInitInfo {
	
	
	private Map<String,Map<String,Object>> equipmentInfoKeyPosition;
	
	private Map<String,Map<String,Object>> equipmentInfoKeyDcs;
	
	
}
