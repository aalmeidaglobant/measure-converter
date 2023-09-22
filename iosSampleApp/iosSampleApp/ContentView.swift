import SwiftUI
import MeasureConverter

struct ContentView: View {
	let measureConverter = MeasureConverterSdk()

	var body: some View {
		Text("Celsius to Fahrenheit:\(measureConverter.celsiusToFahrenheit(celsius: 50.0))")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
