import SwiftUI

struct AddTodoView: View {
    @Environment(\.dismiss) var dismiss
    @State private var title: String = ""
    @State private var date: Date = Date()
    @State private var time: Date = Date()
    var onComplete: (String, Date, Date) -> Void
    
    var body: some View {
        NavigationView {
            Form {
                TextField("Quel todo on veut ajouter ?", text: $title)
                DatePicker("Date", selection: $date, displayedComponents: .date)
                DatePicker("Heure", selection: $time, displayedComponents: .hourAndMinute)
            }
            .navigationTitle("Ajouter un todo")
            .navigationBarItems(leading: Button("Annuler") {
                dismiss()
            }, trailing: Button("Ajouter") {
                onComplete(title, date, time)
                dismiss()
            })
        }
    }
}

