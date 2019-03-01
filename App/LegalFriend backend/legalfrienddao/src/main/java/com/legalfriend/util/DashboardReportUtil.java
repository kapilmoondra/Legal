package com.legalfriend.util;

import java.util.ArrayList;
import java.util.List;

import com.legalfriend.entities.DashboardReport;

public class DashboardReportUtil {

public static List<DashboardReport> getDashboardReportList(List<Object[]> objects) {
		
		List<DashboardReport> dashboardReportList = new ArrayList<>();
		
		objects.forEach(data -> {
			DashboardReport ob= new DashboardReport();
			ob.setX(data[0]!=null?data[0].toString():null);
			ob.setY(data[1]!=null?Double.valueOf(data[1].toString()):null);
			if(ob.getX()!=null) {
				dashboardReportList.add(ob);
			}
		});
		
		return dashboardReportList;
	}
}
