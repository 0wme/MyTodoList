import SwiftUI

struct NotificationView: View {
    @EnvironmentObject var todoManager: TodoManager
    
    var body: some View {
        VStack {
            Text("Notifications").font(.largeTitle)
            
            HStack {
                Spacer()
                Button("Clear all") {
                    clearAllNotifications()
                }
                .padding(.bottom, 5)

            }
            
            ScrollView {
                LazyVStack(spacing: 10) {
                    ForEach(todoManager.receivedNotifications) { notification in
                        VStack(alignment: .leading) {
                            Text(notification.title).font(.headline)
                            Text(notification.body).font(.subheadline)
                        }
                        .padding()
                        .background(Color.gray.opacity(0.2))
                        .cornerRadius(8)
                    }
                }
                .padding(.horizontal)
            }
        }
        .padding(.horizontal)
        .onAppear {
            todoManager.loadNotificationsFromDB()
        }
    }
    
    private func clearAllNotifications() {
        do {
            try DatabaseManager.shared.deleteAllNotifications()
            todoManager.receivedNotifications.removeAll()
        } catch {
            print("Error deleting all notifications: \(error)")
        }
    }
}
