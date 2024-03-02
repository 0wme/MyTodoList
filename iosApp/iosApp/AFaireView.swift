import SwiftUI

struct AFaireView: View {
    @State private var showingAddTodoSheet = false
    @EnvironmentObject var todoManager: TodoManager

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
            VStack {
                List(todoManager.todosAFaire) { todo in
                    HStack {
                        VStack(alignment: .leading) {
                            Text(todo.title)
                                .foregroundColor(.primary)
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
                        Spacer()
                        // Bouton Cancel
                        Button(action: {
                            todoManager.cancel(todo: todo)
                        }) {
                            Image(systemName: "xmark.circle.fill")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 32, height: 32)
                                .foregroundColor(.red)
                        }
                        .buttonStyle(BorderlessButtonStyle())
                        // Bouton Approve
                        Button(action: {
                            todoManager.approve(todo: todo)
                        }) {
                            Image(systemName: "checkmark.circle.fill")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 32, height: 32)
                                .foregroundColor(.green)
                        }
                        .buttonStyle(BorderlessButtonStyle())
                    }
                    .padding()
                }
                .navigationTitle("À Faire")
                
                FloatingActionButton(action: {
                    showingAddTodoSheet = true
                })
                .padding()
            }
        }
        .sheet(isPresented: $showingAddTodoSheet) {
            AddTodoView { title, date, time in
                let newTodo = Todo(title: title, date: date, time: time)
                todoManager.addTodo(newTodo)
                showingAddTodoSheet = false
            }
        }
    }
}



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


struct FloatingActionButton: View {
    var action: () -> Void

    var body: some View {
        Button(action: action) {
            Image(systemName: "plus.circle.fill")
                .resizable()
                .scaledToFit()
                .frame(width: 60, height: 60)
                .foregroundColor(Color.orange)
                .background(Color.white)
                .clipShape(Circle())
                .shadow(radius: 10)
        }
    }
}



