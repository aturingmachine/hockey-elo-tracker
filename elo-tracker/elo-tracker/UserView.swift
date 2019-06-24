//
//  ContentView.swift
//  elo-tracker
//
//  Created by Don Johnson on 6/12/19.
//  Copyright © 2019 Solstice. All rights reserved.
//

import SwiftUI

struct UserView : View {
    
    @State var user: User
    
    var body: some View {
        VStack {
            UserHeader(userName: user.name, userEmail: user.email)
            NavigationView {
                List(user.games) { game in
                    NavigationButton(destination: GameDetails(history: game.history)) {
                        GameRow(game: game)
                    }
                }
            }.navigationBarTitle(Text("Games"))
        }
    }
}

struct UserHeader : View {
    
    @State var userName: String
    @State var userEmail: String
    
    var body: some View {
        VStack {
            Text("\(userName)")
                .font(.largeTitle)
                .bold()
            Text("\(userEmail)")
                .font(.headline)
        }.padding()
    }
    
}

struct GameRow : View {
    
    @State var game: Game
    
    var body: some View {
        VStack(alignment: .leading) {
            Text("\(game.id.name())").font(.title).bold()
            VStack(alignment: .leading) {
                Text("ELO Rating: \(game.elo)").font(.subheadline)
                Spacer()
                Text("Wins: \(game.wins)").font(.subheadline)
                Text("Losses: \(game.losses)").font(.subheadline)
            }.padding()
        }
    }
}

struct GameDetails : View {
    
    @State var history: [StatisticsHistory]
    
    var body: some View {
        VStack {
            Text("Game Details")
            List(history.identified(by: \.date)) { record in
                HStack {
                    Text("\(record.date)")
                    Spacer()
                    Text("\(record.elo)")
                }
            }
        }
    }
}

#if DEBUG
struct ContentView_Previews : PreviewProvider {
    static var previews: some View {
        UserView(user: User(id: 1,
                            name: "Rancor",
                            email: "rancor@gmail.com",
                            games: []))
    }
}
#endif
