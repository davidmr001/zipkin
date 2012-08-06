/*
* Copyright 2012 Twitter Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.twitter.zipkin.hadoop

import collection.mutable.{HashMap, HashSet}
import java.util.{ArrayList}
import scala.math.Ordering.String
import org.apache.commons.lang.StringUtils

/**
 * A basic data structure used to store service names
 */

class ServiceNameList() {

  protected var serviceNames = new ArrayList[String]()
  protected var serviceNamesSet = new HashSet[String]()

  def add(name: String) {
    if (!serviceNamesSet.contains(name)) {
      serviceNames.add(name)
      serviceNamesSet += name
    }
  }

  def getName(s: String) : String = s

  def getAllNames() = new ArrayList[String](serviceNames)

}

/**
 * A data structure used to store service names which detects similar names and clumps them together
 */

class StandardizedServiceNameList() extends ServiceNameList {

  val DISTANCE = 2

  val originalToStandardizedIndex = new HashMap[String, Int]()

  override def add(name: String) {
    val trimmed = name.trim()
    if (originalToStandardizedIndex.contains(trimmed)) return
    var foundMatch = false
    for (index <- 0 until serviceNames.size()) {
      val key = serviceNames.get(index)
      // If these are actually just near spellings of the same service
      if (StringUtils.getLevenshteinDistance(key.toLowerCase, trimmed.toLowerCase) <= DISTANCE) {
        foundMatch = true
        if (String.lteq(trimmed, key)) {
          serviceNames.set(index, trimmed)
        }
        originalToStandardizedIndex += trimmed -> index
      }
    }
    if (!foundMatch) {
      serviceNames.add(trimmed)
      originalToStandardizedIndex += trimmed -> (serviceNames.size() - 1)
    }
  }

  override def getName(s: String) = {
    if (!originalToStandardizedIndex.contains(s)) {
      throw new IllegalArgumentException("Input not in map: " + s)
    }
    serviceNames.get(originalToStandardizedIndex(s))
  }

}