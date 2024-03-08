import SwiftUI

struct SettingsView: View {
    // Utilisé pour afficher une alerte de confirmation avant de réinitialiser la base de données
    @State private var showingResetAlert = false
    
    var body: some View {
        VStack {
            Text("Paramètres").font(.largeTitle)
                .frame(maxWidth: .infinity, alignment: .center)
                .padding()
            
            Button(action: {
                // Ouvrir les paramètres de l'application
                if let url = URL(string: UIApplication.openSettingsURLString), UIApplication.shared.canOpenURL(url) {
                    UIApplication.shared.open(url)
                }
            }) {
                Text("Ouvrir les paramètres")
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.blue)
                    .cornerRadius(10)
            }
            .padding()
            
            Button("FAQ") {
                // Logique pour afficher la FAQ
            }
            .foregroundColor(.white)
            .frame(maxWidth: .infinity)
            .padding()
            .background(Color.green)
            .cornerRadius(10)
            .padding()
            
            Button("Réinitialisation") {
                // Afficher l'alerte de confirmation
                showingResetAlert = true
            }
            .foregroundColor(.white)
            .frame(maxWidth: .infinity)
            .padding()
            .background(Color.red)
            .cornerRadius(10)
            .padding()
            .alert(isPresented: $showingResetAlert) {
                Alert(
                    title: Text("Confirmer la réinitialisation"),
                    message: Text("Cette action supprimera toutes les données de l'application. Voulez-vous continuer ?"),
                    primaryButton: .destructive(Text("Réinitialiser")) {
                        // Plus tard appeler la fonction pour réinitialiser la base de données
                        resetDatabase()
                    },
                    secondaryButton: .cancel()
                )
            }
        }
        .padding()
    }
    
    func resetDatabase() {
        do {
            try DatabaseManager.shared.resetDatabase()
            // Mettre à jour l'UI si nécessaire, par exemple, en vidant les listes affichées
        } catch {
            print("Erreur lors de la réinitialisation de la base de données : \(error)")
        }
    }
}



struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
