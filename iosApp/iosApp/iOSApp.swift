import SwiftUI
import UserNotifications

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    var body: some Scene {
        WindowGroup {
            ContentView()
                    .environmentObject(TodoManager.shared) // Utilisez l'instance singleton
                .environmentObject(TodoManager.shared)
        }
    }
}

        // Gérer ici la réception des notifications
class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        // Demande d'autorisation pour les notifications
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { granted, _ in
            if granted {
                print("Autorisation pour les notifications accordée.")
            }
        }
        UNUserNotificationCenter.current().delegate = self
        return true
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        let title = notification.request.content.title
        let body = notification.request.content.body

                // Utilisez `TodoManager.shared` pour accéder au gestionnaire de todos
                TodoManager.shared.addReceivedNotification(title: title, body: "La todo \(title) ne reste que 24h !")
        // Utiliser `TodoManager.shared` pour ajouter la notification reçue
        TodoManager.shared.addReceivedNotification(title: title, body: body)

        completionHandler([.banner, .sound])
    }
}



