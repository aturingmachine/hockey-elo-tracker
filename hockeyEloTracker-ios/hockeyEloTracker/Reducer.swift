//
//  Reducer.swift
//  hockeyEloTracker
//
//  Created by Don Johnson on 1/10/19.
//  Copyright © 2019 Solstice. All rights reserved.
//

import ReSwift

// MARK: - Reducers

struct AppReducer: AnyReducer {
    
    func _handleAction(action: Action, state: StateType?) -> StateType {
        guard (state != nil) else {
            return AppState()
        }
        
        var state = (state as! AppState)
        
        switch action {
        case let chooseMatchAction as ChooseMatchAction:
            state.match = chooseMatchAction.match
        case let choosePlayerOneAction as ChoosePlayerOneAction:
            state.playerOne = choosePlayerOneAction.playerOne
        case let choosePlayerTwoAction as ChoosePlayerTwoAction:
            state.playerTwo = choosePlayerTwoAction.playerTwo
        default:
            break
        }
        return state
    }
    
}
