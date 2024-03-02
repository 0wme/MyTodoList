import SwiftUI

struct AFaireView: View {
    @State private var showingAddTodoSheet = false
    @State private var todos: [Todo] = []

    // Formateurs de date et d'heure
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
            ZStack(alignment: .bottom) {
                List {
                    ForEach(todos, id: \.id) { todo in
                        VStack(alignment: .leading) {
                            Text(todo.title)
                            if let date = todo.date {
                                Text("Date de fin : \(dateFormatter.string(from: date))")
                                    .font(.subheadline)
                                    .foregroundColor(.gray)
                            }
                            if let time = todo.time {
                                Text("Heure de fin : \(timeFormatter.string(from: time))")
                                    .font(.subheadline)
                                    .foregroundColor(.gray)
                            }
                        }
                    }

                .navigationTitle("À Faire")

                // Bouton flottant centré en bas
                HStack {
                    Spacer()
                    FloatingActionButton(action: {
                        showingAddTodoSheet = true
                    })
                    .padding(.bottom, 20) // Ajustez selon la marge souhaitée du bas
                    Spacer()
                }
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
                .background(Color.orange)
                .clipShape(Circle())
                .shadow(radius: 10)
        }
    }
}

    struct Todo: Identifiable {
        var id = UUID()
        var title: String
        var date: Date?
        var time: Date?
    }
}
