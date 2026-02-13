import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({ providedIn: 'root' })
export class UserService {
  // Placeholder base URL - replace with real backend url
  private baseUrl = 'http://localhost:8085/api/v1.0/users';

  constructor(private http: HttpClient) { }

  register(user: { name: string; email: string; password: string }): Observable<any> {
    const url = `${this.baseUrl}/register`;
    return this.http.post(url, user, httpOptions);
  }

  login(payload: { email: string; password: string }): Observable<any> {
    const url = `${this.baseUrl}/login`;
    return this.http.post(url, payload, httpOptions);
  }
}
