import SwiftUI

struct RetardView: View {
    @EnvironmentObject var todoManager: TodoManager

    var body: some View {
        List {
            ForEach(todoManager.todosRetard) { todo in
                HStack {
                    Text(todo.title)
                        .foregroundColor(.primary)
                    Spacer()
                    // Ajoutez ici des boutons ou des actions si vous souhaitez modifier le statut du todo ou le supprimer
                }
                .padding()
                .cornerRadius(10)
                .shadow(radius: 2)
            }
            .onDelete(perform: removeTodoFromRetard)
        }
        .navigationTitle("Retard")
    }

    private func removeTodoFromRetard(at offsets: IndexSet) {
        withAnimation {
            offsets.forEach { index in
                let todo = todoManager.todosRetard[index]
                todoManager.removeTodoFromRetard(todo)
            }
        }
    }
}