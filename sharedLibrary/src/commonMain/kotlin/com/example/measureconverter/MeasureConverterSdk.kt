package com.example.measureconverter

class MeasureConverterSdk {
    fun meterToCentimeter(meter: Float): Float = meter * 100
    fun centimeterToMeter(centimeter: Float): Float = centimeter / 100

    fun literToCubicMeters(liter: Float): Float = liter / 1000
    fun celsiusToFahrenheit(celsius: Float): Float = celsius * (9/5) + 32
}