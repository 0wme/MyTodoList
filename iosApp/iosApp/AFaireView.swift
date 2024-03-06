import SwiftUI

struct AFaireView: View {
    @State private var showingAddTodoSheet = false
    @State private var showingRenameView = false
    @State private var newName: String = ""
    @State private var selectedTodo: Todo?
    @EnvironmentObject var todoManager: TodoManager
    
    private let dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yy"
        return formatter
    }()
    
    private let timeFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "HH:mm"
        return formatter
    }()
    
    var body: some View {
        NavigationView {
            VStack {
                ScrollView {
                    LazyVStack(spacing: 20) {
                        ForEach(todoManager.todosAFaire) { todo in
                            HStack {
                                VStack(alignment: .leading) {
                                    Text(todo.title).foregroundColor(.primary)
                                    if let date = todo.date {
                                        Text("Date de fin : \(dateFormatter.string(from: date))")
                                            .font(.subheadline).foregroundColor(.gray)
                                    }
                                    if let time = todo.time {
                                        Text("Heure de fin : \(timeFormatter.string(from: time))")
                                            .font(.subheadline).foregroundColor(.gray)
                                    }
                                }
                                Spacer()
                                Button(action: {
                                    todoManager.cancel(todo: todo)
                                }) {
                                    Image(systemName: "xmark.circle.fill")
                                        .resizable()
                                        .scaledToFit()
                                        .frame(width: 40, height: 40)
                                        .foregroundColor(.red)
                                }
                                .buttonStyle(BorderlessButtonStyle())
                                Button(action: { todoManager.approve(todo: todo) }) {
                                    Image(systemName: "checkmark.circle.fill")
                                        .resizable()
                                        .scaledToFit()
                                        .frame(width: 40, height: 40)
                                        .foregroundColor(.green)
                                }
                            }
                            .padding()
                            .background(Color.gray.opacity(0.2))
                            .cornerRadius(8)
                            .contextMenu { // Ajout du contextMenu ici
                                Button("Renommer") {
                                    self.selectedTodo = todo
                                    self.showingRenameView = true
                                }
                                Button("Partager") {
                                    // Logique de partage à implémenter
                                }
                                Button("Supprimer", role: .destructive) {
                                    guard let index = todoManager.todosAFaire.firstIndex(where: { $0.id == todo.id }) else { return }
                                    todoManager.todosAFaire.remove(at: index)
                                }
                            }
                        }
                    }
                    .padding(.horizontal)
                }
                
                FloatingActionButton(action: {
                    showingAddTodoSheet = true
                })
                .padding()
            }
            .sheet(isPresented: $showingAddTodoSheet) {
                AddTodoView { title, date, time in
                    let newTodo = Todo(title: title, date: date, time: time)
                    todoManager.addTodo(newTodo)
                    showingAddTodoSheet = false
                }
            }
            .sheet(isPresented: $showingRenameView) {
                RenameTodoView(newName: $newName, showingView: $showingRenameView, renameAction: { updatedName in
                    if let todo = selectedTodo {
                        renameTodo(todo, with: updatedName)  // Ici, updatedName est le nouveau nom du todo
                        selectedTodo = nil // Réinitialiser selectedTodo après le renommage
                    }
                })
            }
        }
    }
    
    private func deleteTodo(at offsets: IndexSet) {
        offsets.forEach { index in
            todoManager.todosAFaire.remove(at: index)
        }
    }
    
    private func renameTodo(_ todo: Todo, with newName: String) {
        guard let index = todoManager.todosAFaire.firstIndex(where: { $0.id == todo.id }), !newName.isEmpty else { return }
        todoManager.todosAFaire[index].title = newName
    }
}

    
    private func shareTodo(_ todo: Todo) {
        print("Sharing todo: \(todo.title)")
        // Voir Swift doc ET/OU tuto Youtube
        // The actual sharing functionality requires integration with UIKit's UIActivityViewController or similar.
    }

        struct FloatingActionButton: View {
            var action: () -> Void
            
            var body: some View {
                Button(action: action) {
                    Image(systemName: "plus.circle.fill")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 70, height: 70)
                        .foregroundColor(Color.orange)
                        .background(Color.white)
                        .clipShape(Circle())
                        .shadow(radius: 10)
                }
            }
            
        }
