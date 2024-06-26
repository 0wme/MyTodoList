import SwiftUI

struct AddTodoView: View {
    @Environment(\.dismiss) var dismiss
    @State private var title: String = ""
    @State private var addDate: Bool = false
    @State private var addTime: Bool = false
    @State private var date: Date = Date()
    @State private var time: Date = Date()
    var onComplete: (String, Date?, Date?) -> Void
    
    var body: some View {
        NavigationView {
            Form {
                TextField("Quel todo on veut ajouter ?", text: $title)
                    .onChange(of: title) { newValue in
                        if newValue.count > 15 {
                            title = String(newValue.prefix(15))
                        }
                    }
                Toggle(isOn: $addDate) {
                    Text("Ajouter une date")
                }
                if addDate {
                    DatePicker("Date", selection: $date, displayedComponents: .date)
                }
                Toggle(isOn: $addTime) {
                    Text("Ajouter une heure")
                }
                if addTime {
                    DatePicker("Heure", selection: $time, displayedComponents: .hourAndMinute)
                }
            }
            .navigationTitle("Ajouter un todo")
            .navigationBarItems(
                leading: Button(action: {
                    dismiss()
                }) {
                    Text("Annuler")
                        .foregroundColor(.orange)
                },
                trailing: Button(action: {
                    onComplete(title.trimmingCharacters(in: .whitespaces), addDate ? date : nil, addTime ? time : nil)
                    dismiss()
                }) {
                    Text("Ajouter")
                        .foregroundColor(title.trimmingCharacters(in: .whitespaces).isEmpty ? .gray : .orange)
                }
                .disabled(title.trimmingCharacters(in: .whitespaces).isEmpty)
            )

        }
    }
}
