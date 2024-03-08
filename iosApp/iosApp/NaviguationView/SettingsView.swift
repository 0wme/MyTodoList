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
            
        }
        }
        
        
        struct SettingsView_Previews: PreviewProvider {
            static var previews: some View {
                SettingsView()
            }
        }
    }
