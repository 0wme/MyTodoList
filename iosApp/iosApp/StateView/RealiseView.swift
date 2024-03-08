import SwiftUI

struct RealiseView: View {
    @EnvironmentObject var todoManager: TodoManager

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 10) {
                ForEach(todoManager.todosRealise) { todo in
                    VStack(alignment: .leading) {
                        Text(todo.title).font(.headline)
                    }
                    .padding()
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color.gray.opacity(0.2))
                    .cornerRadius(8)
                    .contextMenu {
                        Button("Supprimer", role: .destructive) {
                            removeTodoFromRealise(todo)
                        }
                    }
                }
            }
            .padding(.horizontal)
        }
        .navigationTitle("Réalisé")
    }

    private func removeTodoFromRealise(_ todo: Todo) {
        withAnimation {
            todoManager.removeTodoFromRealise(todo)
        }
    }
}
