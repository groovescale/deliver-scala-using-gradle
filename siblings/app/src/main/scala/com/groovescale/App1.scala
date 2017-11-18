package com.groovescale

object App1 {
  def main(args:Array[String]) : Unit = {
    val msg = com.groovescale.Lib1.hello()
    println(s"App1: $msg")
  }
}
