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
                .ignoresSafeArea(.all, edges: .all)
        }
    }
}

struct ContentView: UIViewControllerRepresentable {
    @Environment(\.colorScheme) var colorScheme

    func makeUIViewController(context: Context) -> UIViewController {
        let controller = MainViewControllerKt.MainViewController()
        controller.view.backgroundColor = .clear
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }

    typealias UIViewControllerType = UIViewController
}
