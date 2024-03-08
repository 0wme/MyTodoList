import SwiftUI

struct SettingsView: View {
    @State private var showingResetAlert = false
    @State private var showingFAQ = false
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
                    HStack {
                        Spacer()
                        Text("Ouvrir les paramètres")
                            .foregroundColor(.white)
                        Spacer()
                    }
                    .padding()
                    .background(Color.blue)
                    .cornerRadius(10)
                }
                .padding(.horizontal)
                
                Button(action: {
                    showingFAQ = true
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
                .sheet(isPresented: $showingFAQ) {
                    FAQView()
                }
                
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
        SettingsView().environmentObject(TodoManager())
    }
}
