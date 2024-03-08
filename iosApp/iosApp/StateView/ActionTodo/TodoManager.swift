import Foundation
import Combine
import UserNotifications

class TodoManager: ObservableObject {
    static let shared = TodoManager() // Singleton instance
    
    @Published var todosAFaire: [Todo] = []
    @Published var todosRealise: [Todo] = []
    @Published var todosRetard: [Todo] = []
    @Published var receivedNotifications: [ReceivedNotification] = []

    init() {
        loadTodosFromDB()
    }

    func loadTodosFromDB() {
        do {
            let allTodos = try DatabaseManager.shared.getAllTodos()
            DispatchQueue.main.async {
                self.todosAFaire = allTodos.filter { $0.state == "À Faire" }
                self.todosRealise = allTodos.filter { $0.state == "Réalisé" }
                self.todosRetard = allTodos.filter { $0.state == "Retard" }
            }
        } catch {
            print("Erreur lors du chargement des todos depuis la base de données : \(error)")
        }
    }



    func addTodo(_ todo: Todo) {
        DispatchQueue.main.async {
            self.todosAFaire.append(todo)
        }
        scheduleNotification(for: todo)
        do {
            try DatabaseManager.shared.addTodo(title: todo.title, date: todo.date, time: todo.time, state: "À Faire")
        } catch {
            print("Error saving todo to DB: \(error)")
        }
    }

    func approve(todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            do {
                try DatabaseManager.shared.updateTodoState(id: todo.id, newState: "Réalisé")
                loadTodosFromDB() // Recharge les todos après mise à jour
            } catch {
                print("Erreur lors de la mise à jour de l'état du todo : \(error)")
            }
        }
    }

    func cancel(todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            do {
                try DatabaseManager.shared.updateTodoState(id: todo.id, newState: "Retard")
                loadTodosFromDB() // Recharge les todos après mise à jour
            } catch {
                print("Erreur lors de la mise à jour de l'état du todo : \(error)")
            }
        }
    }


    func removeTodoFromAFaire(_ todo: Todo) {
        if let index = todosAFaire.firstIndex(where: { $0.id == todo.id }) {
            DispatchQueue.main.async {
                self.todosAFaire.remove(at: index)
            }
            do {
                try DatabaseManager.shared.deleteTodo(id: todo.id)
            } catch {
                print("Error deleting todo from DB: \(error)")
            }
        }
    }

    // Supprimer une todo de la liste 'Réalisé'
    func removeTodoFromRealise(_ todo: Todo) {
        if let index = todosRealise.firstIndex(where: { $0.id == todo.id }) {
            DispatchQueue.main.async {
                self.todosRealise.remove(at: index)
            }
            do {
                try DatabaseManager.shared.deleteTodo(id: todo.id)
            } catch {
                print("Error deleting todo from DB: \(error)")
            }
        }        
    }

    // Supprimer une todo de la liste 'Retard'
    func removeTodoFromRetard(_ todo: Todo) {
        if let index = todosRetard.firstIndex(where: { $0.id == todo.id }) {
            DispatchQueue.main.async {
                self.todosRetard.remove(at: index)
            }
            do {
                try DatabaseManager.shared.deleteTodo(id: todo.id)
            } catch {
                print("Error deleting todo from DB: \(error)")
            }
        }
    }
    
    // Ajouter une notification reçue à la liste
    func addReceivedNotification(title: String, body: String) {
        let newNotification = ReceivedNotification(id: UUID(), title: title, body: body)
        DispatchQueue.main.async {
            self.receivedNotifications.append(newNotification)
        }
        do {
            try DatabaseManager.shared.addNotification(title: title, body: body)
        } catch {
            print("Error saving notification to DB: \(error)")
        }
    }

    
    func loadNotificationsFromDB() {
        do {
            let allNotifications = try DatabaseManager.shared.getAllNotifications()
            DispatchQueue.main.async {
                self.receivedNotifications = allNotifications
            }
        } catch {
            print("Error loading notifications from DB: \(error)")
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
    var state: String
    
    init(id: UUID = UUID(), title: String, date: Date? = nil, time: Date? = nil, state: String) {
        self.id = id
        self.title = title
        self.date = date
        self.time = time
        self.state = state 
    }
}


struct ReceivedNotification: Identifiable {
    let id: UUID
    let title: String
    let body: String
}
