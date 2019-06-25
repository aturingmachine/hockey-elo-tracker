//
//  User.swift
//  elo-tracker
//
//  Created by Don Johnson on 6/20/19.
//  Copyright © 2019 Solstice. All rights reserved.
//

import SwiftUI

struct User {
    
    let id: Double
    let name: String
    let email: String
    let games: [Game]
    
}

struct Game: Identifiable {
    
    let id: GameType
    let elo: Double
    let losses: Int
    let wins: Int
    let history: [StatisticsHistory]
    
}

struct StatisticsHistory {
    
    let date: Date
    let elo: Double
    
}

enum GameType: String {
    
    case bubbleHockey = "Bubble Hockey"
    case pingPong = "Ping Pong"
    
    func name() -> String {
        return self.rawValue
    }
    
}
