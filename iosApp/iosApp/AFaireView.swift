import SwiftUI

struct AFaireView: View {
    @State private var showingAddTodoSheet = false
    @State private var showingRenameView = false
    @State private var showingShareSheet = false
    @State private var itemsToShare: [Any] = []
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
                                    self.todoManager.cancel(todo: todo)
                                }) {
                                    Image(systemName: "xmark.circle.fill")
                                        .resizable()
                                        .scaledToFit()
                                        .frame(width: 40, height: 40)
                                        .foregroundColor(.red)
                                }
                                .buttonStyle(BorderlessButtonStyle())
                                Button(action: { self.todoManager.approve(todo: todo) }) {
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
                            .contextMenu {
                                Button("Renommer") {
                                    self.selectedTodo = todo
                                    self.showingRenameView = true
                                }
                                Button("Partager") {
                                    itemsToShare = ["Sharing todo: \(todo.title)"]
                                    showingShareSheet = true
                                }
                                Button("Supprimer", role: .destructive) {
                                    self.todoManager.removeTodoFromAFaire(todo)
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
                    let newTodo = Todo(title: title, date: date, time: time, state: "À Faire")
                    self.todoManager.addTodo(newTodo)
                    self.showingAddTodoSheet = false
                }
            }
            .sheet(isPresented: $showingRenameView) {
                RenameTodoView(newName: $newName, showingView: $showingRenameView, renameAction: { updatedName in
                    if let todo = self.selectedTodo {
                        var updatedTodo = todo
                        updatedTodo.title = updatedName
                        self.todoManager.removeTodoFromAFaire(todo)
                        self.todoManager.addTodo(updatedTodo)
                        self.selectedTodo = nil
                    }
                })
            }
            .sheet(isPresented: $showingShareSheet) {
                ShareSheet(activityItems: itemsToShare)
            }
        }
    }
}

struct FloatingActionButton: View {
    var action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Image(systemName: "plus.circle.fill")
                .resizable()
                .scaledToFit()
                .frame(width: 65, height: 65)
                .foregroundColor(Color.orange)
                .background(Color.white)
                .clipShape(Circle())
                .shadow(radius: 10)
        }
    }
}
