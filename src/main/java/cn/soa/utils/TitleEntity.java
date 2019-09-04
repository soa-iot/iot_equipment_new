
/**
 * <一句话功能描述>
 * <p>
 * @author 陈宇林
 * @version [版本号, 2019年9月4日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.soa.entity.Column;

public class TitleEntity {

	public String t_id;
	public String t_pid;
	public String t_content;
	public String t_fielName;

	public TitleEntity() {
	}

	public TitleEntity(String t_id, String t_pid, String t_content, String t_fielName) {
		this.t_id = t_id;
		this.t_pid = t_pid;
		this.t_content = t_content;
		this.t_fielName = t_fielName;
	}

	public String getT_id() {
		return t_id;
	}

	public void setT_id(String t_id) {
		this.t_id = t_id;
	}

	public String getT_pid() {
		return t_pid;
	}

	public void setT_pid(String t_pid) {
		this.t_pid = t_pid;
	}

	public String getT_content() {
		return t_content;
	}

	public void setT_content(String t_content) {
		this.t_content = t_content;
	}

	public String getT_fielName() {
		return t_fielName;
	}

	public void setT_fielName(String t_fielName) {
		this.t_fielName = t_fielName;
	}
	
	public static void main(String[] args) throws Exception {
////    单级的表头
//    Map<String,String> map=new HashMap<String,String>();
//    map.put("登录名","u_login_id");
//    Map<String,String>  map1=new HashMap<String,String>();
//    map1.put("用户名","u_name");
//    Map<String,String>  map2=new HashMap<String,String>();
//    map2.put("角色","u_role");
//    Map<String,String>  map3=new HashMap<String,String>();
//    map3.put("部门","u_dep");//d_name
//    Map<String,String>  map4=new HashMap<String,String>();
//    map4.put("用户类型","u_type");
//    List<Map<String,String>> titleList=new ArrayList<>();
//    titleList.add(map); titleList.add(map1); titleList.add(map2); titleList.add(map3); titleList.add(map4);
//    //单级的 行内数据
//    List<Map<String,String>> rowList=new ArrayList<>();
//    for(int i=0;i<7;i++){
//        Map m= new HashMap<String,String>();
//        m.put("u_login_id","登录名"+i); m.put("u_name","张三"+i);
//        m.put("u_role","角色"+i); m.put("u_dep","部门"+i);
//        m.put("u_type","用户类型"+i);
//        rowList.add(m);
//    }
//    ExcelTool excelTool = new ExcelTool("单级表头的表格",15,20);
//    List<Column>  titleData=excelTool.columnTransformer(titleList);
//    excelTool.exportExcel(titleData,rowList,"D://outExcel.xls",true,false);

    //List<Map>数据 多级表头,数据如下:
   //        登录名  姓名       aa
   //                      角色    部门
//    List<Map<String,String>> titleList=new ArrayList<>();
//    Map<String,String> titleMap=new HashMap<String,String>();
//    titleMap.put("id","11");titleMap.put("pid","0");titleMap.put("content","登录名");titleMap.put("fielName","u_login_id");
//    Map<String,String> titleMap1=new HashMap<String,String>();
//    titleMap1.put("id","1");titleMap1.put("pid","0");titleMap1.put("content","姓名");titleMap1.put("fielName","u_name");
//    Map<String,String> titleMap2=new HashMap<String,String>();
//    titleMap2.put("id","2");titleMap2.put("pid","0");titleMap2.put("content","角色加部门");titleMap2.put("fielName",null);
//    Map<String,String> titleMap3=new HashMap<String,String>();
//    titleMap3.put("id","3");titleMap3.put("pid","2");titleMap3.put("content","角色");titleMap3.put("fielName","u_role");
//    Map<String,String> titleMap4=new HashMap<String,String>();
//    titleMap4.put("id","4");titleMap4.put("pid","2");titleMap4.put("content","部门");titleMap4.put("fielName","u_dep");
//    Map<String,String> titleMap5=new HashMap<String,String>();
//    titleMap5.put("id","22");titleMap5.put("pid","0");titleMap5.put("content","角色加部门1");titleMap5.put("fielName",null);
//    Map<String,String> titleMap6=new HashMap<String,String>();
//    titleMap6.put("id","22_1");titleMap6.put("pid","22");titleMap6.put("content","角色1");titleMap6.put("fielName","u_role");
//    Map<String,String> titleMap7=new HashMap<String,String>();
//    titleMap7.put("id","22_2");titleMap7.put("pid","22");titleMap7.put("content","部门1");titleMap7.put("fielName","u_dep");
//    titleList.add(titleMap); titleList.add(titleMap1); titleList.add(titleMap2); titleList.add(titleMap3); titleList.add(titleMap4);
//    titleList.add(titleMap5); titleList.add(titleMap6); titleList.add(titleMap7);
//   // 单级的 行内数据
//    List<Map<String,String>> rowList=new ArrayList<>();
//    for(int i=0;i<7;i++){
//        Map m= new HashMap<String,String>();
//        m.put("u_login_id","登录名"+i); m.put("u_name","张三"+i);
//        m.put("u_role","角色"+i); m.put("u_dep","部门"+i);
//        m.put("u_type","用户类型"+i);
//        rowList.add(m);
//    }
//    ExcelTool excelTool = new ExcelTool("List<Map>数据 多级表头表格",20,20);
//    List<Column>  titleData=excelTool.columnTransformer(titleList,"id","pid","content","fielName","0");
//    excelTool.exportExcel(titleData,rowList,"D://outExcel.xls",true,false);

    //实体类（entity）数据 多级表头,数据如下:
    //        登录名  姓名       aa
    //                      角色    部门
    List<TitleEntity> titleList=new ArrayList<>();
    TitleEntity titleEntity0=new TitleEntity("0",null,"总表",null);
    TitleEntity titleEntity=new TitleEntity("11","0","登录名2","u_login_id");
    TitleEntity titleEntity1=new TitleEntity("1","0","姓名","u_name");
    TitleEntity titleEntity11=new TitleEntity("1_1","1","姓名1","u_name");
    TitleEntity titleEntity2=new TitleEntity("2","0","角色加部门",null);
    TitleEntity titleEntity3=new TitleEntity("3","2","角色","u_role");
    TitleEntity titleEntity4=new TitleEntity("4","2","部门","u_dep");
    TitleEntity titleEntity5=new TitleEntity("33","0","角色加部门1",null);
    TitleEntity titleEntity6=new TitleEntity("33_1","33","角色33","u_role");
    TitleEntity titleEntity7=new TitleEntity("33_2","33_1","部门33","u_dep");
    TitleEntity titleEntity8=new TitleEntity("44","0","角色加部门2",null);
    TitleEntity titleEntity9=new TitleEntity("44_1","44","角色44","u_role");
    TitleEntity titleEntity10=new TitleEntity("44_2","44","部门44","u_dep");
    TitleEntity titleEntity12=new TitleEntity("44_3","44_2","44_2","u_dep");
    titleList.add(titleEntity0);
    titleList.add(titleEntity); titleList.add(titleEntity1); titleList.add(titleEntity2); titleList.add(titleEntity3); titleList.add(titleEntity4);
    titleList.add(titleEntity5); titleList.add(titleEntity6); titleList.add(titleEntity7);
    titleList.add(titleEntity8);
    titleList.add(titleEntity9);
    titleList.add(titleEntity10); titleList.add(titleEntity11); titleList.add(titleEntity12);
    //单级的 行内数据
    List<Map<String,String>> rowList=new ArrayList<>();
    for(int i=0;i<7;i++){
        Map m= new HashMap<String,String>();
        m.put("u_login_id","登录名"+i); m.put("u_name","张三"+i);
        m.put("u_role","角色"+i); m.put("u_dep","部门"+i);
        m.put("u_type","用户类型"+i);
        rowList.add(m);
    }
    ExcelTool excelTool = new ExcelTool("实体类（entity）数据 多级表头表格",20,20);
    List<Column>  titleData = excelTool.columnTransformer(titleList,"t_id","t_pid","t_content","t_fielName","0");
    excelTool.exportExcel(titleData,rowList,"D://outExcel.xls",true,true);

      //读取excel
//      ExcelTool excelTool = new ExcelTool();
//      List<List<String>> readexecl=excelTool.getExcelValues("D://outExcel.xls",1);
//      List<List<Map<String,String>>> readexeclC=excelTool.getExcelMapVal("D://outExcel.xls",1);
//      int count= excelTool.hasSheetCount("D://outExcel.xls");

}

}
