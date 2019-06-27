//
//  EloTrackerClient.swift
//  elo-tracker
//
//  Created by Don Johnson on 6/27/19.
//  Copyright © 2019 Solstice. All rights reserved.
//

import Foundation

enum HttpMethod: String {
    case get = "GET"
    case post = "POST"
    case put = "PUT"
    case delete = "DELETE"
}

enum EloTrackerUrl: String {
    case users = "http://localhost:8080/api/v1/users"
    case matches = "http://localhost:8080/api/v1/matches"
}

struct MatchRecords: Decodable {
    var id: Int
    var gameType: String
    var playerOneId: Int
    var playerTwoId: Int
    var playerOneScore: Int
    var playerTwoScore: Int
    var winnerId: Int
}

class EloTrackerClient {
    
    func request() {
        let matchesURL = URL(string: EloTrackerUrl.matches.rawValue)
        if let matchesUrl = matchesURL {
            let task = URLSession.shared.dataTask(with: matchesUrl) { data, error, blah in
                if error == nil {
                    let matches = data?.base64EncodedData()
                }
            };
            task.resume()
        }
    }
}
