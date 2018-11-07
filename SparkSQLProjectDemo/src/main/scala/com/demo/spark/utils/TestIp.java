package com.demo.spark.utils;

public class TestIp {

    private static String getCity(String ip) {
        return IpHelper.findRegionByIp(ip);
    }

    public static void main(String[] args) {
        System.out.println(getCity("58.30.15.255"));
    }
}
