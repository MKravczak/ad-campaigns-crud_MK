import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/api/accounts';

  constructor(private http: HttpClient) {}

  getBalance(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/balance`);
  }

  addFunds(amount: number): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}/addFunds?amount=${amount}`, {});
  }
}
