import SwiftUI

struct ContentView: View {
    var body: some View {
        TabView {
            NotificationView()
                .tabItem {
                    VStack {
                        Image(systemName: "bell")
                        Text("Notification")
                    }
                }
            HomeView()
                .tabItem {
                    VStack {
                        Image(systemName: "house")
                        Text("Home")
                    }
                }
            SettingsView()
                .tabItem {
                    VStack {
                        Image(systemName: "gear")
                        Text("Settings")
                    }
                }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
