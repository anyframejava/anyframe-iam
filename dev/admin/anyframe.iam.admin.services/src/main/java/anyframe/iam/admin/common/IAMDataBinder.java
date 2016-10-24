package anyframe.iam.admin.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import anyframe.common.util.StringUtil;

public class IAMDataBinder {
	
	@SuppressWarnings("unchecked")
	public static List bindSheet(Sheet sheet) throws Exception {

		String sheetName = sheet.getName();
		String packageName = "anyframe.iam.admin.domain.Temp";

		Class c = Class.forName(packageName + sheetName);

		int rows = sheet.getRows();

		if (rows > 0) {

			int cellLength = 0;
			List objectList = new ArrayList();

			if (rows != 0)
				cellLength = sheet.getRow(0).length;

			String[] columnName = new String[cellLength];

			for (int i = 0; i < rows; i++) {
				Cell[] cellList = sheet.getRow(i);
				Object o;

				if (i != 0) {
					o = c.newInstance();
					Class tempClass = Class.forName("java.lang.String");

					Object[] arg = new Object[1];
					arg[0] = i + "";

					for (int j = 0; j < cellList.length; j++) {
						Method m = c
								.getMethod("set" + columnName[j], tempClass);

						arg = new Object[1];
						arg[0] = StringUtil.null2str(cellList[j].getContents());
						m.invoke(o, arg);
					}
					
					objectList.add(o);

				} else {
					for (int j = 0; j < cellList.length; j++) {
						columnName[j] = cellList[j].getContents();
					}
				}

			}
			return objectList;
		} else
			return null;
	}
	
}
