import SwiftUI

struct SettingsView: View {
    // Utilisé pour afficher une alerte de confirmation avant de réinitialiser la base de données
    @State private var showingResetAlert = false
    
    var body: some View {
        VStack {
            Text("Paramètres").font(.largeTitle)
                .frame(maxWidth: .infinity, alignment: .center)
                .padding()
            
        }
        
    }
    
    
    struct SettingsView_Previews: PreviewProvider {
        static var previews: some View {
            SettingsView()
        }
    }
}
