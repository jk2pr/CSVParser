package com.jk.csvparser

import android.content.Context
import java.io.*
import kotlin.math.log


object FileUtils {
    @Throws(IOException::class)
    fun getFileByResourceId(ctx: Context, id: Int): InputStream {
        return ctx.resources.openRawResource(id)
    }

    fun crateDataObjects(lineList: MutableList<String>): MutableList<Restaurant> {
        val dataList = mutableListOf<Restaurant>()
        lineList.forEach {
            var name: String = "No Name"
            var openingHours = mutableMapOf<String, String>()
            it.split(",".toRegex(), 2).let {
                name = it.get(0)
                val time = it[1]
                //  Mon-Thu, Sun 11:30 am - 9:00 pm  / Fri-Sat 11:30 am - 9:30 pmMon-Thu, Sun 11:30 am - 9:00 pm  / Fri-Sat 11:30 am - 9:30 pm
                openingHours = extractDay(time)
            }
            val restaurant = Restaurant(name, openingHours)
            dataList.add(restaurant)

        }
        return dataList
    }


    private fun extractDay(dayTime0: String): MutableMap<String, String> {
        val openingHours = mutableMapOf<String, String>()
        val dayTime = dayTime0.trim().removeSurrounding("\"")
        if (dayTime0.contains("Mon,")) {
            print(dayTime0)
        }
        dayTime.trim().split("/".toRegex()).forEach {
            // Array of Mon-Thu, Sun 11:30 am - 9:00 pm
            var dtArray = it.trim().split(",".toRegex(), 2)
            if (dtArray.size == 1)
                dtArray = dtArray[0].trim().split(" ".toRegex(), 2)
            // 0 = Mon-Thu,
            // 1= Sun 11:30 am - 9:00 pm

            val dayArray = dtArray[0].trim().split("-".toRegex(), 2)
            dayArray.forEach {
                val startDay = it
                val endDay: String
                if (dayArray.size == 1)
                    endDay = startDay
                else
                    endDay = dayArray[1]

                val indexOfStartDay = daylist.indexOf(startDay)
                val indexOfEndDay = daylist.indexOf(endDay)

                for (i in indexOfStartDay..indexOfEndDay) {
                    openingHours.put(daylist[i], dtArray[1])
                }
            }
            //openingHours.put(dtArray[0], dtArray[1])
        }

        return openingHours
    }


    val daylist = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
/*private fun File.copyInputStreamToFile(inputStream: InputStream) {
    inputStream.use { input ->
        this.outputStream().use { fileOut ->
            input.copyTo(fileOut)
        }
    }
}*/
}