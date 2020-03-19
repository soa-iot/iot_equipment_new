
/**
 * <一句话功能描述>
 * <p>通用异常
 * @author 陈宇林
 * @version [版本号, 2020年3月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.exception;

public class CommonException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -40898626264817002L;
	
	public CommonException() {

	}

	public CommonException(String str) {
		// 此处传入的是抛出异常后显示的信息提示
		super(str);
	}

}
