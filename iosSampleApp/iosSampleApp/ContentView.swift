import SwiftUI
import MeasureConverter

struct ContentView: View {
	let measureConverter = MeasureConverterSdk()

	var body: some View {
		Text("Centimeter to meter:\(measureConverter.centimeterToMeter(centimeter: 1000))")
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
