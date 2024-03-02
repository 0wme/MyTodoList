import SwiftUI

struct AFaireView: View {
    @State private var showingAddTodoSheet = false
    @State private var todos: [Todo] = []

    var body: some View {
        NavigationView {
            ZStack(alignment: .bottomTrailing) {
                List {
                    ForEach(todos, id: \.id) { todo in
                        VStack(alignment: .leading) {
                            Text(todo.title)
                            // Utilisez votre propre format de date ici
                            Text("\(todo.date), \(todo.time)").font(.subheadline).foregroundColor(.gray)
                        }
                    }
                }
                .navigationTitle("À Faire")

                // Bouton flottant +
                FloatingActionButton(action: {
                    showingAddTodoSheet = true
                })
                .padding()
            }
        }
        .sheet(isPresented: $showingAddTodoSheet) {
            AddTodoView { title, date, time in
                let newTodo = Todo(id: UUID(), title: title, date: date, time: time)
                todos.append(newTodo)
                showingAddTodoSheet = false
            }
        }
    }
}

struct FloatingActionButton: View {
    var action: () -> Void

    var body: some View {
        Button(action: action) {
            Image(systemName: "plus")
                .font(.largeTitle)
                .frame(width: 70, height: 70)
                .foregroundColor(Color.white)
                .background(Color.blue)
                .clipShape(Circle())
                .shadow(radius: 10)
        }
    }
}

struct Todo: Identifiable {
    var id = UUID()
    var title: String
    var date: Date
    var time: Date
}

