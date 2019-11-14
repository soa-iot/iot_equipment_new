package cn.soa.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStringOfLong {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
	
	
	public Long getTime (String strTime) {
		Date date = null;
		
		try {
			//System.err.println("-----------------strTime----------------:"+strTime);
			date= sdf.parse(strTime);
			if (date != null) {
				Long time = date.getTime()*1000000;
			//	System.out.println("-----------------longTime----------------:"+time);
				return time;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	public Long getTimeYear (String strTime) {
		Date date = null;
		
		try {
			System.err.println("-----------------strTime----------------:"+strTime);
			date= sdf1.parse(strTime);
			if (date != null) {
				Long time = date.getTime()*1000000;
				System.out.println("-----------------longTime----------------:"+time);
				return time;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}

}
