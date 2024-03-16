import SwiftUI

struct NotificationView: View {
    @EnvironmentObject var todoManager: TodoManager
    
    var body: some View {
        VStack {
            Text("Notifications").font(.largeTitle)
            
            List(todoManager.receivedNotifications) { notification in
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
        }
    }
}
