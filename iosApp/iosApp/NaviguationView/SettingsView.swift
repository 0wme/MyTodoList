import SwiftUI

struct SettingsView: View {
    @State private var showingResetAlert = false
    
    var body: some View {
        VStack {
            Text("Paramètres").font(.largeTitle)
            
            ScrollView {
                VStack(spacing: 20) {
                    Button(action: {
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
                    .padding(.horizontal)
                    
                    Button("FAQ") {
                        // Logique pour afficher la FAQ
                    }
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.green)
                    .cornerRadius(10)
                    .padding(.horizontal)
                    
                    Button("Réinitialisation") {
                        showingResetAlert = true
                    }
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.red)
                    .cornerRadius(10)
                    .padding(.horizontal)
                    .alert(isPresented: $showingResetAlert) {
                        Alert(
                            title: Text("Confirmer la réinitialisation"),
                            message: Text("Cette action supprimera toutes les données de l'application. Voulez-vous continuer ?"),
                            primaryButton: .destructive(Text("Réinitialiser")) {
                                resetDatabase()
                            },
                            secondaryButton: .cancel()
                        )
                    }
                }
            }
        }
        .padding(.top)
    }
    
    func resetDatabase() {
        do {
            try DatabaseManager.shared.resetDatabase()
            print("Base de données réinitialisée avec succès.")
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
