import axios from 'axios'

export const http = axios.create({
  baseURL: 'http://localhost:8080/', //base URL goes here,
  headers: {
    'Accept': 'application/json'
  },
  withCredentials: true
})