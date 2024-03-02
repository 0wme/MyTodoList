import Foundation
import Combine

class TodoManager: ObservableObject {
    @Published var todosAFaire: [Todo] = []
    @Published var todosRealise: [Todo] = []
    @Published var todosRetard: [Todo] = []

    func addTodo(_ todo: Todo) {
        todosAFaire.append(todo)
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
