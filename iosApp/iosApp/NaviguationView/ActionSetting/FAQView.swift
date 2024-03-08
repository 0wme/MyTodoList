import SwiftUI

struct FAQView: View {
    var body: some View {
        NavigationView {
            List {
                VStack(alignment: .leading, spacing: 8) {
                    Text("Qui a créé DailyDo ?")
                        .fontWeight(.bold)
                        .foregroundColor(.yellow)
                    Text("C'est Tom Vieira, un étudiant en 2eme année en BUT Informatique à Limoges qui a créé cette app pendant un projet en Mobile.")
                        .foregroundColor(.white)
                    
                    Text("Comment a-t-il fait DailyDo ?")
                        .fontWeight(.bold)
                        .foregroundColor(.yellow)
                    Text("Tom a fait l'application en Kotlin et peut-être Swift pour iOS.")
                        .foregroundColor(.white)
                    
                    Text("Comment on active/désactive les notifications ?")
                        .fontWeight(.bold)
                        .foregroundColor(.yellow)
                    Text("Pensez à aller dans les Paramètres de DailyDo et d'appuyer sur le bouton Open Settings pour vous emmener directement dans les Paramètres de l'application depuis votre Téléphone !")
                        .foregroundColor(.white)
                    
                    Text("DailyDo est-elle compliquée à prendre en main ?")
                        .fontWeight(.bold)
                        .foregroundColor(.yellow)
                    Text("Cela dépend de si vous vous y connaissez, mais Tom a fait en sorte que ce soit le plus facile et le plus clair possible pour tout utilisateur, débutant ou expérimenté !")
                        .foregroundColor(.white)
                }
                .padding(.vertical)
                
                Button("Contact") {
                    if let url = URL(string: "https://github.com/0wme") {
                        UIApplication.shared.open(url)
                    }
                }
                .foregroundColor(.blue)
                .frame(maxWidth: .infinity)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(10)
            }
            .listStyle(GroupedListStyle())
            .navigationTitle("FAQ")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Fermer") {
                    }
                }
            }
        }
    }
}

struct FAQView_Previews: PreviewProvider {
    static var previews: some View {
        FAQView()
    }
}
