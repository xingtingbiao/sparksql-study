package com.demo.spark

import org.junit._
import Assert._
import com.ggstar.util.ip.IpHelper

@Test
class AppTest {

  @Test
  def testOK() = assertTrue(true)

  //    @Test
  //    def testKO() = assertTrue(false)
  @Test
  def testIp() = {
    val ip = "58.30.15.255"
    val str = IpHelper.findRegionByIp(ip)
    println(str)
  }
}


