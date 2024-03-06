import SwiftUI

struct RenameTodoView: View {
    @Binding var newName: String
    @Binding var showingView: Bool
    var renameAction: (String) -> Void  // Cette ligne indique que renameAction prend un String comme argument

    var body: some View {
        VStack(spacing: 20) {
            Text("Quel est le nouveau nom de la todo?")
            TextField("Nouveau nom", text: $newName)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            HStack {
                Button("Annuler") {
                    showingView = false
                }
                .foregroundColor(.red)
                
                Button("Valider") {
                    renameAction(newName) // Appelle renameAction avec newName comme argument
                    showingView = false
                }
                .foregroundColor(.blue)
            }
        }
        .padding()
    }
}
