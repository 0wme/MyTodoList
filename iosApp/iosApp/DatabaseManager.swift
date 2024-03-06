import Foundation
import SQLite

class DatabaseManager {
    static let shared = DatabaseManager()
    private var db: Connection?
    
    private let todosTable = Table("todos")
    private let id = Expression<String>("id")
    private let title = Expression<String>("title")
    private let date = Expression<Date?>("date")
    private let time = Expression<Date?>("time")
    private let state = Expression<String>("state") // Par exemple, "Retard", "À Faire", "Réalisé"
    
    private let notificationsTable = Table("notifications")
    private let notificationId = Expression<String>("notificationId")
    private let notificationTitle = Expression<String>("notificationTitle")
    private let notificationBody = Expression<String>("notificationBody")
    
    init() {
        // Chemin pour la base de données dans le dossier Documents de l'application.
        do {
            let path = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first!
            db = try Connection("\(path)/db.sqlite3")
            try createTables()
        } catch {
            print("Unable to open database: \(error)")
        }
    }
    
    private func createTables() throws {
        try db?.run(todosTable.create(ifNotExists: true) { t in
            t.column(id, primaryKey: true)
            t.column(title)
            t.column(date)
            t.column(time)
            t.column(state)
        })
        
        try db?.run(notificationsTable.create(ifNotExists: true) { t in
            t.column(notificationId, primaryKey: true)
            t.column(notificationTitle)
            t.column(notificationBody)
        })
    }
    
    // CRUD operations for Todos
    
    func addTodo(title: String, date: Date?, time: Date?, state: String) throws {
        // Générer un nouvel UUID sous forme de String pour chaque nouveau todo.
        let newId = UUID().uuidString
        let insert = todosTable.insert(
            self.id <- newId,
            self.title <- title,
            self.date <- date,
            self.time <- time,
            self.state <- state
        )
        do {
            try db?.run(insert)
            print("Todo ajouté avec succès : \(newId)")
        } catch {
            print("Erreur lors de l'ajout du todo : \(error)")
            throw error
        }
    }


    
    func getAllTodos() throws -> [Todo] {
        guard let todos = try db?.prepare(todosTable) else { return [] }
        return try todos.map { row in
            // Assurez-vous que la conversion de la date et de l'heure est correcte.
            let todoId = UUID(uuidString: row[id]) ?? UUID()
            let todoTitle = row[title]
            let todoDate = row[date] // Vous devrez peut-être convertir ces dates depuis et vers des Strings.
            let todoTime = row[time] // Conversion similaire pour l'heure.
            let todoState = row[state]
            
            return Todo(id: todoId, title: todoTitle, date: todoDate, time: todoTime, state: todoState)
        }
    }


    
    func updateTodo(id: UUID, newTitle: String) throws {
        let todoIdString = id.uuidString // Convertir UUID en String
        let todo = todosTable.filter(self.id == todoIdString)
        try db?.run(todo.update(self.title <- newTitle))
    }

    func deleteTodo(id: UUID) throws {
        let todoIdString = id.uuidString // Convertir UUID en String
        let todo = todosTable.filter(self.id == todoIdString)
        try db?.run(todo.delete())
    }

    
    // CRUD operations for Notifications
    
    func addNotification(title: String, body: String) throws {
        let newId = UUID().uuidString // Générer un nouvel UUID pour la notification
        let insert = notificationsTable.insert(
            self.notificationId <- newId,
            self.notificationTitle <- title,
            self.notificationBody <- body
        )
        do {
            try db?.run(insert)
            print("Notification saved successfully")
        } catch {
            print("Error saving notification: \(error)")
            throw error
        }
    }

    
    func getAllNotifications() throws -> [ReceivedNotification] {
        guard let notifications = try db?.prepare(notificationsTable) else { return [] }
        return try notifications.map {
            ReceivedNotification(
                id: UUID(uuidString: $0[notificationId]) ?? UUID(), // Convertir de String à UUID
                title: $0[notificationTitle],
                body: $0[notificationBody]
            )
        }
    }

    
    func updateTodoState(id: UUID, newState: String) throws {
        let todoIdString = id.uuidString
        let todo = todosTable.filter(self.id == todoIdString)
        try db?.run(todo.update(self.state <- newState))
    }
}



