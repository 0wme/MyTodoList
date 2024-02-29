import SwiftUI

struct HomeView: View {
    @State private var selectedTab = 0

    var body: some View {
        VStack {
            // Custom navigation tabs
            HStack {
                Spacer()
                Button(action: {
                    self.selectedTab = 0
                }) {
                    Text("Retard")
                        .padding()
                        .background(self.selectedTab == 0 ? Color.blue : Color.clear)
                        .cornerRadius(10)
                        .foregroundColor(.white)
                }
                Spacer()
                Button(action: {
                    self.selectedTab = 1
                }) {
                    Text("À Faire")
                        .padding()
                        .background(self.selectedTab == 1 ? Color.blue : Color.clear)
                        .cornerRadius(10)
                        .foregroundColor(.white)
                }
                Spacer()
                Button(action: {
                    self.selectedTab = 2
                }) {
                    Text("Réalisé")
                        .padding()
                        .background(self.selectedTab == 2 ? Color.blue : Color.clear)
                        .cornerRadius(10)
                        .foregroundColor(.white)
                }
                Spacer()
            }
            .frame(maxWidth: .infinity)
            .background(Color.gray.opacity(0.2))

            // Content views
            TabView(selection: $selectedTab) {
                Text("Contenu pour Retard")
                    .tag(0)
                    .tabItem {
                        Text("Retard")
                    }
                Text("Contenu pour À Faire")
                    .tag(1)
                    .tabItem {
                        Text("À Faire")
                    }
                Text("Contenu pour Réalisé")
                    .tag(2)
                    .tabItem {
                        Text("Réalisé")
                    }
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
