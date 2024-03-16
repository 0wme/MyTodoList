import Foundation
import Combine
import UserNotifications

// Structure pour représenter les notifications reçues
struct ReceivedNotification: Identifiable {
    let id: UUID
    let title: String
    let body: String
}

class TodoManager: ObservableObject {
    static let shared = TodoManager() // Singleton instance
    
    @Published var todosAFaire: [Todo] = []
    @Published var todosRealise: [Todo] = []
    @Published var todosRetard: [Todo] = []
    @Published var receivedNotifications: [ReceivedNotification] = []

    // Ajouter une nouvelle todo
    func addTodo(_ todo: Todo) {
        todosAFaire.append(todo)
        scheduleNotification(for: todo) // Planifier une notification si nécessaire
        scheduleNotification(for: todo)
    }

    // Approuver une todo
    func approve(todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            let approvedTodo = todosAFaire.remove(at: index)
            todosRealise.append(approvedTodo)
        }
    }

    // Annuler une todo
    func cancel(todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            let cancelledTodo = todosAFaire.remove(at: index)
            todosRetard.append(cancelledTodo)
        }
    }

    // Supprimer une todo de la liste 'À Faire'
    func removeTodoFromAFaire(_ todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            todosAFaire.remove(at: index)
        }
    }

    // Supprimer une todo de la liste 'Réalisé'
    func removeTodoFromRealise(_ todo: Todo) {
        if let index = todosRealise.firstIndex(where: { $0.id == todo.id }) {
            todosRealise.remove(at: index)
        }
    }

    // Supprimer une todo de la liste 'Retard'
    func removeTodoFromRetard(_ todo: Todo) {
        if let index = todosRetard.firstIndex(where: { $0.id == todo.id }) {
            todosRetard.remove(at: index)
        }
    }
    
    // Ajouter une notification reçue à la liste
    func addReceivedNotification(title: String, body: String) {
        let newNotification = ReceivedNotification(id: UUID(), title: title, body: body)
        DispatchQueue.main.async {
            self.receivedNotifications.append(newNotification)
        }
    }

    // Planifier une notification pour une todo
    private func scheduleNotification(for todo: Todo) {
        guard let deadline = todo.date, deadline.timeIntervalSinceNow <= 86400 else { return }

        let content = UNMutableNotificationContent()
        content.title = "Rappel de Todo"
        content.body = "Il reste moins de 24h pour accomplir '\(todo.title)'."
        content.sound = UNNotificationSound.default

        let timeInterval = max(deadline.timeIntervalSinceNow - 3600, 1) 
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: timeInterval, repeats: false)

        let request = UNNotificationRequest(identifier: UUID().uuidString, content: content, trigger: trigger)
        UNUserNotificationCenter.current().add(request) { error in
            if let error = error {
                print("Erreur lors de la planification de la notification: \(error.localizedDescription)")
            }
        }
    }
}

struct Todo: Identifiable {
    let id: UUID
    var title: String
    var date: Date?
    var time: Date?
    
    init(id: UUID = UUID(), title: String, date: Date? = nil, time: Date? = nil) {
        self.id = id
        self.title = title
        self.date = date
        self.time = time
    }
}

