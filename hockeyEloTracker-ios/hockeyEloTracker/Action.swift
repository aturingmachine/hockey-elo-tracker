//
//  Action.swift
//  hockeyEloTracker
//
//  Created by Don Johnson on 1/10/19.
//  Copyright © 2019 Solstice. All rights reserved.
//

import ReSwift

struct ChooseMatchAction: Action {
    
    var match: Match
    
}

struct ChoosePlayerOneAction: Action {
    
    var playerOne: Player
    
}

struct ChoosePlayerTwoAction: Action {
    
    var playerTwo: Player
    
}
