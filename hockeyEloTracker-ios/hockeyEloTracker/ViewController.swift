//
//  ViewController.swift
//  hockeyEloTracker
//
//  Created by Don Johnson on 1/10/19.
//  Copyright © 2019 Solstice. All rights reserved.
//

import UIKit
import ReSwift

class ViewController: UIViewController, StoreSubscriber {
    
    @IBOutlet weak var playerOne: UILabel!
    @IBOutlet weak var playerTwo: UILabel!
    @IBOutlet weak var match: UILabel!
    
    private var isPlayer1 = true
    private var isPlayer2 = true
    private var isHockey = true
    
    @IBAction func choosePlayerOne(_ sender: Any) {
        if isPlayer1 {
            let playerOne = Player.one
            store.dispatch(ChoosePlayerOneAction(playerOne: playerOne))
            isPlayer1 = false
        } else {
            let playerTwo = Player.two
            store.dispatch(ChoosePlayerTwoAction(playerTwo: playerTwo))
            isPlayer1 = true
        }
    }

    @IBAction func choosePlayerTwo(_ sender: Any) {
        if isPlayer2 {
            let playerTwo = Player.two
            store.dispatch(ChoosePlayerTwoAction(playerTwo: playerTwo))
        } else {
            let playerTwo = Player.two
            store.dispatch(ChoosePlayerTwoAction(playerTwo: playerTwo))
        }
    }
    
    @IBAction func chooseMatch(_ sender: Any) {
        if isHockey {
            let match = Match.hockey
            store.dispatch(ChooseMatchAction(match: match))
            isHockey = false
        } else {
            let match = Match.pingpong
            store.dispatch(ChooseMatchAction(match: match))
            isHockey = true
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        store.subscribe(self)
    }
    
    func newState(state: AppState) {
        playerOne.text = "\(state.playerOne.rawValue)"
        playerTwo.text = "\(state.playerTwo.rawValue)"
        match.text = "\(state.match.rawValue)"
    }
    
}
