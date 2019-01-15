//
//  State.swift
//  hockeyEloTracker
//
//  Created by Don Johnson on 1/10/19.
//  Copyright © 2019 Solstice. All rights reserved.
//

import ReSwift

struct AppState: StateType {
    
    var playerOne: Player
    var playerTwo: Player
    var match: Match
    
    init() {
        self.playerOne = .one
        self.playerTwo = .two
        self.match = .hockey
    }
    
}

// MARK: Models & Options

enum Player: String {
    
    case one = "Player1"
    case two = "Player2"
    
}

enum Match: String {
    
    case hockey = "Hockey"
    case pingpong = "Ping Pong"
    
}
