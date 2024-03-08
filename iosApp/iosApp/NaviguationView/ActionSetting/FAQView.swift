import SwiftUI

struct FAQView: View {
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        NavigationView {
            List {
                VStack(alignment: .leading, spacing: 8) {
                    Group {
                        Text("Qui a créé DailyDo ?")
                            .fontWeight(.bold)
                            .foregroundColor(.orange)
                        Text("C'est Tom Vieira, un étudiant en 2eme année en BUT Informatique à Limoges qui a créé cette app pendant un projet en Mobile.")
                            .foregroundColor(.white)
                        
                        Text("Comment a-t-il fait DailyDo ?")
                            .fontWeight(.bold)
                            .foregroundColor(.orange)
                        Text("Tom a fait l'application en Kotlin pour Android et Swift pour IOS.")
                            .foregroundColor(.white)
                        
                        Text("Comment on active/désactive les notifications ?")
                            .fontWeight(.bold)
                            .foregroundColor(.orange)
                        Text("Pensez à aller dans les Paramètres de DailyDo et d'appuyer sur le bouton Ouvrir les paramètres pour vous emmener directement dans les Paramètres de l'application depuis votre Téléphone !")
                            .foregroundColor(.white)
                        
                        Text("DailyDo est-elle compliquée à prendre en main ?")
                            .fontWeight(.bold)
                            .foregroundColor(.orange)
                        Text("Cela dépend de si vous vous y connaissez, mais j'ai fait en sorte que ce soit le plus facile et le plus clair possible pour tout utilisateur, débutant ou expérimenté !")
                            .foregroundColor(.white)
                    }
                    .padding(.vertical)
                }
                
                HStack {
                    Spacer()
                    Button("Contact") {
                        if let url = URL(string: "https://github.com/0wme") {
                            UIApplication.shared.open(url)
                        }
                    }
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.orange)
                    .cornerRadius(10)
                    Spacer()
                }
            }
            .listStyle(GroupedListStyle())
            .navigationTitle("FAQ")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        presentationMode.wrappedValue.dismiss()
                    }) {
                        Text("Fermer")
                            .foregroundColor(.orange)
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
