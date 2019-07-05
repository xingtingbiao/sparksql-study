//package com.demo.spark.utils;
//
//import com.ggstar.util.ip.IpRelation;
//import com.ggstar.util.ip.IpTree;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.*;
//
//public class IpHelper {
//    private static IpTree ipTree = IpTree.getInstance();
//    private static final String ipFile = "ipDatabase.csv";
//    private static final String regionFile = "ipRegion.xlsx";
//
//    public IpHelper() {
//    }
//
//    private static void buildTrain() {
//        try {
//            List<IpRelation> ipRelationList = getIpRelation();
//            int count = 0;
//            Iterator var2 = ipRelationList.iterator();
//
//            while(var2.hasNext()) {
//                IpRelation ipRelation = (IpRelation)var2.next();
//                ipTree.train(ipRelation.getIpStart(), ipRelation.getIpEnd(), ipRelation.getProvince());
//                if (count > 10) {
//                    break;
//                }
//            }
//        } catch (Exception var4) {
//            var4.printStackTrace();
//        }
//
//    }
//
//    public static String findRegionByIp(String ip) {
//        return ipTree.findIp(ip);
//    }
//
//    public static List<IpRelation> getIpRelation() throws Exception {
//        Map<Integer, String> regionRelationMap = getRegionRelationMap();
//        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ipDatabase.csv");
//        // String file = com.ggstar.util.ip.IpHelper.class.getClassLoader().getResource("ipDatabase.csv").getFile();
//        BufferedReader ipRelationReader = new BufferedReader(new InputStreamReader(resourceAsStream, FileUtil.codeString(resourceAsStream)));
//        List<IpRelation> list = new ArrayList<IpRelation>();
//
//        String line;
//        while((line = ipRelationReader.readLine()) != null && !line.trim().equals("")) {
//            String[] split = line.split(",");
//            if (split.length > 2) {
//                String ipStart = split[0];
//                String ipEnd = split[1];
//                Integer ipCode = Integer.valueOf(split[2]);
//                String province = (String)regionRelationMap.get(ipCode);
//                IpRelation ipRelation = new IpRelation();
//                ipRelation.setIpStart(ipStart);
//                ipRelation.setIpEnd(ipEnd);
//                ipRelation.setProvince(province);
//                list.add(ipRelation);
//            }
//        }
//        return list;
//    }
//
//    public static Map<Integer, String> getRegionRelationMap() {
//        // URL resource = Thread.currentThread().getContextClassLoader().getResource("ipRegion.xlsx");
//        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("ipRegion.xlsx");
//        // System.out.println("URL: " + resource.toString());
//        // String file = resource.getFile();
//        // Workbook workbook = PoiUtil.getWorkbook(file);
//        Workbook workbook = null;
//        try {
//            workbook = WorkbookFactory.create(resourceAsStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvalidFormatException e) {
//            e.printStackTrace();
//        }
//        Map<Integer, String> map = new HashMap<Integer, String>();
//        if (null != workbook) {
//            Sheet sheet = workbook.getSheetAt(0);
//            int rowLen = sheet.getPhysicalNumberOfRows();
//            for(int i = 1; i < rowLen; ++i) {
//                Row row = sheet.getRow(i);
//                String province = row.getCell(0).getStringCellValue();
//                Double a = row.getCell(2).getNumericCellValue();
//                Integer ipCode = a.intValue();
//                map.put(ipCode, province);
//            }
//        }
//        return map;
//    }
//
//    static {
//        buildTrain();
//    }
//}
