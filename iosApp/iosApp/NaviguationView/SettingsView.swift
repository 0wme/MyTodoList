import SwiftUI


struct SettingsView: View {
    @State private var showingResetAlert = false
    @EnvironmentObject var todoManager: TodoManager
    
    var body: some View {
        VStack {
            Text("Paramètres")
                .font(.largeTitle)
                .padding(.top)
            
            Spacer()
            
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
                
                Button(action: {
                    // Logique pour afficher la FAQ
                }) {
                    HStack {
                        Spacer()
                        Text("FAQ")
                            .foregroundColor(.white)
                        Spacer()
                    }
                    .padding()
                    .background(Color.green)
                    .cornerRadius(10)
                }
                .padding(.horizontal)

                Button(action: {
                    showingResetAlert = true
                }) {
                    HStack {
                        Spacer()
                        Text("Réinitialisation")
                            .foregroundColor(.white)
                        Spacer()
                    }
                    .padding()
                    .background(Color.red)
                    .cornerRadius(10)
                }
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
            
            Spacer()
            
        }
    }
    
    func resetDatabase() {
        do {
            try DatabaseManager.shared.resetDatabase()
            todoManager.loadTodosFromDB()
            todoManager.loadNotificationsFromDB()
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
