import SwiftUI

@main
struct iOSApp: App {
    var todoManager = TodoManager()

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(todoManager)
        }
    }
}
