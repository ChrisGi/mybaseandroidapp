import SwiftUI
import WeatherTomorrow

@main
struct iOSApp: App {

    init() {
        KoinHelperKt.InitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }

    typealias UIViewControllerType = UIViewController
}
