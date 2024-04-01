import SwiftUI

struct HomeView: View {
    @State private var selectedTab = 1

    var body: some View {
        VStack {
            HStack {
                Spacer()
                Button(action: {
                    self.selectedTab = 0
                }) {
                    Text("Retard")
                        .padding()
                        .background(self.selectedTab == 0 ? Color.orange : Color.clear)
                        .cornerRadius(10)
                        .foregroundColor(.white)
                }
                Spacer()
                Button(action: {
                    self.selectedTab = 1
                }) {
                    Text("À Faire")
                        .padding()
                        .background(self.selectedTab == 1 ? Color.orange : Color.clear)
                        .cornerRadius(10)
                        .foregroundColor(.white)
                }
                Spacer()
                Button(action: {
                    self.selectedTab = 2
                }) {
                    Text("Réalisé")
                        .padding()
                        .background(self.selectedTab == 2 ? Color.orange : Color.clear)
                        .cornerRadius(10)
                        .foregroundColor(.white)
                }
                Spacer()
            }
            .frame(maxWidth: .infinity)
            .background(Color.gray.opacity(0.2))

            // Content views
            TabView(selection: $selectedTab) {
                RetardView()
                    .tag(0)
                AFaireView()
                    .tag(1)
                RealiseView()
                    .tag(2)
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

