import SwiftUI

struct RealiseView: View {
    @EnvironmentObject var todoManager: TodoManager

    var body: some View {
        List {
            ForEach(todoManager.todosRealise) { todo in
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
            .onDelete(perform: removeTodoFromRealise)
        }
        .navigationTitle("Réalisé")
    }

    private func removeTodoFromRealise(at offsets: IndexSet) {
        withAnimation {
            offsets.forEach { index in
                let todo = todoManager.todosRealise[index]
                todoManager.removeTodoFromRealise(todo)
            }
        }
    }
}
