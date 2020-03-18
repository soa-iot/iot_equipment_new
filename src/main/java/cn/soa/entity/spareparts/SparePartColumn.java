
/**
 * <一句话功能描述>
 * <p>备件表字段信息
 * @author 陈宇林
 * @version [版本号, 2020年3月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.entity.spareparts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SparePartColumn {

	private String columnNameCn;//字段中文名
	
	private String columnName;//字段名
	
	
}
