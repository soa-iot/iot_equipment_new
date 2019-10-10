package mapper.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.soa.IotEquipmentApplication;
import cn.soa.dao.equipment.EquipmentDetailsMapper;
import cn.soa.entity.equipment.EquipmentDetails;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class EquipmentDetailsMapperTest {
	
	@Autowired
	private EquipmentDetailsMapper equipmentDetailsMapper;
	
	@Test
	public void findLubEquiTest() {
		  List<EquipmentDetails> equipmentDetails = equipmentDetailsMapper.findEquipmentDetails("K-1505C-Ⅶ",null,null);
		System.err.println(equipmentDetails);
	}
	
}
