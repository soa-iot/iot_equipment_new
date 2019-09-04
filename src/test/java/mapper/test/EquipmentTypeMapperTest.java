
/**
 * <一句话功能描述>
 * <p>设备分类mapper测试
 * @author 陈宇林
 * @version [版本号, 2019年8月28日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package mapper.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.soa.IotEquipmentApplication;
import cn.soa.dao.EquipmentTypeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { IotEquipmentApplication.class })
@WebAppConfiguration
public class EquipmentTypeMapperTest {

	@Autowired
	private EquipmentTypeMapper equipmentTypeMapper;

	@Test
	public void testSelectAllWithChidren() {
		System.out.println(equipmentTypeMapper.selectAllWithChidren());

	}

}
