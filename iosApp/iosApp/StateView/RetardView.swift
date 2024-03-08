import SwiftUI

struct RetardView: View {
    @EnvironmentObject var todoManager: TodoManager

    var body: some View {
        ScrollView {
            LazyVStack(spacing: 10) {
                ForEach(todoManager.todosRetard) { todo in
                    VStack(alignment: .leading) {
                        Text(todo.title).font(.headline)
                    }
                    .padding()
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(Color.gray.opacity(0.2))
                    .cornerRadius(8)
                    .contextMenu {
                        Button("Supprimer", role: .destructive) {
                            removeTodoFromRetard(todo)
                        }
                    }
                }
            }
            .padding(.horizontal)
        }
        .navigationTitle("Retard")
    }

    private func removeTodoFromRetard(_ todo: Todo) {
        withAnimation {
            todoManager.removeTodoFromRetard(todo)
        }
    }
}
