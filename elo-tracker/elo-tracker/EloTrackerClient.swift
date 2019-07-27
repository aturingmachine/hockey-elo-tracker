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

enum APIServiceError: Error {
    case apiError
    case invalidEndpoint
    case invalidResponse
    case noData
    case decodeError
}

enum EloTrackerUrl: String {
    case users = "/api/v1/users"
    case matches = "/api/v1/matches"
}

struct MatchRecords: Codable {
    var id: Int
    var gameType: String
    var playerOneId: Int
    var playerTwoId: Int
    var playerOneScore: Int
    var playerTwoScore: Int
    var winnerId: Int
}

class EloTrackerClient {
    
    static let shared = EloTrackerClient()
    private let urlSession = URLSession.shared
    private let baseURL = URL(string: "http://localhost:8080")
    
    private let jsonDecoder: JSONDecoder = {
        let jsonDecoder = JSONDecoder()
        jsonDecoder.keyDecodingStrategy = .convertFromSnakeCase
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-mm-dd"
        jsonDecoder.dateDecodingStrategy = .formatted(dateFormatter)
        return jsonDecoder
    }()
    
    private init() {}
    
    func requestMatches(result: @escaping (Result<MatchRecords, APIServiceError>) -> Void)  {
        let matchesURL = URL(string: EloTrackerUrl.matches.rawValue)
        if let baseURL = baseURL, let matchesURL = matchesURL {
            let url = URL(string: baseURL.absoluteString +
                matchesURL.absoluteString)
            
            if let url = url {
                fetchResources(url: url, completion: result)
            }
        }
    }
    
    private func fetchResources<T: Decodable>(url: URL, completion: @escaping (Result<T, APIServiceError>) -> Void) {
//        guard var urlComponents = URLComponents(url: url, resolvingAgainstBaseURL: true) else {
//            completion(.failure(.invalidEndpoint))
//            return
//        }
//        let queryItems = [URLQueryItem(name: "api_key", value: "apiKey")]
//        urlComponents.queryItems = queryItems
//        guard let url = urlComponents.url else {
//            completion(.failure(.invalidEndpoint))
//            return
//        }
        
        urlSession.dataTask(with: url) {
            result in
            
            switch result {
            case .success(let response, let data):
                guard let statusCode = (response as? HTTPURLResponse)?.statusCode, 200..<299 ~= statusCode else {
                    completion(.failure(.invalidResponse))
                    return
                }
                do {
                    let values = try self.jsonDecoder.decode(T.self, from: data)
                    completion(.success(values))
                } catch {
                    completion(.failure(.decodeError))
                }
            case .failure(_):
                completion(.failure(.apiError))
            }
            }
            .resume()
    }
    
}

extension URLSession {
    
    func dataTask(with url: URL, result: @escaping (Result<(URLResponse, Data), Error>) -> Void) -> URLSessionDataTask {
        return dataTask(with: url) { (data, response, error) in
            if let error = error {
                result(.failure(error))
                return
            }
            
            guard let response = response, let data = data else {
                let error = NSError(domain: "error", code: 0, userInfo: nil)
                result(.failure(error))
                return
            }
            result(.success((response, data)))
        }
    }
    
}
