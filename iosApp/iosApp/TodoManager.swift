import Foundation
import Combine
import UserNotifications

class TodoManager: ObservableObject {
    @Published var todosAFaire: [Todo] = []
    @Published var todosRealise: [Todo] = []
    @Published var todosRetard: [Todo] = []

    func addTodo(_ todo: Todo) {
        todosAFaire.append(todo)
        scheduleNotification(for: todo)
    }
    
    private func scheduleNotification(for todo: Todo) {
        guard let deadline = todo.date, deadline.timeIntervalSinceNow <= 86400 else { return }

        let content = UNMutableNotificationContent()
        content.title = "Rappel de Todo"
        content.body = "Il reste moins de 24h pour accomplir '\(todo.title)'."
        content.sound = UNNotificationSound.default

        let timeInterval = max(deadline.timeIntervalSinceNow - 3600, 1) // Planifier pour 1 heure avant l'échéance, ou immédiatement si dans moins d'une heure.
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: timeInterval, repeats: false)

        let request = UNNotificationRequest(identifier: UUID().uuidString, content: content, trigger: trigger)
        UNUserNotificationCenter.current().add(request) { error in
            if let error = error {
                print("Erreur lors de la planification de la notification: \(error.localizedDescription)")
            }
        }
    }

    func approve(todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            let approvedTodo = todosAFaire.remove(at: index)
            todosRealise.append(approvedTodo)
        }
    }

    func cancel(todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            let cancelledTodo = todosAFaire.remove(at: index)
            todosRetard.append(cancelledTodo)
        }
    }
    
    func removeTodoFromAFaire(_ todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            todosAFaire.remove(at: index)
        }
    }

    
    func removeTodoFromRealise(_ todo: Todo) {
        if let index = todosRealise.firstIndex(where: { $0.id == todo.id }) {
            todosRealise.remove(at: index)
        }
    }
    
    func removeTodoFromRetard(_ todo: Todo) {
        if let index = todosRetard.firstIndex(where: { $0.id == todo.id }) {
            todosRetard.remove(at: index)
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
