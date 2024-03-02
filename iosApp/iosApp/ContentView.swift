import SwiftUI

struct ContentView: View {
    @State private var selectedTab = 1 // Supposons que 1 est le tag pour HomeView

    var body: some View {
        TabView(selection: $selectedTab) {
            NotificationView()
                .tabItem {
                    Image(systemName: "bell")
                    Text("Notification")
                }
                .tag(0) // Tag pour NotificationView
            HomeView()
                .tabItem {
                    Image(systemName: "house")
                    Text("Home")
                }
                .tag(1) // Tag pour HomeView, c'est notre vue par défaut
            SettingsView()
                .tabItem {
                    Image(systemName: "gear")
                    Text("Settings")
                }
                .tag(2) // Tag pour SettingsView
        }
    }
}

