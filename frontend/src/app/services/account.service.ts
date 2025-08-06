import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {User} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = '/api/accounts';

  constructor(private http: HttpClient) {}

  createAccount(user:User): Observable<any> {
    return this.http.post(this.baseUrl, user, { observe: 'response' });
  }

  getStatement(id: string): Observable<any> {
    return this.http.get(`/api/accounts/${id}/statement`);
  }

  deposit(accountId: string, amount: number): Observable<void> {
    return this.http.post<void>(`/api/accounts/${accountId}/deposit`, { amount });
  }

  withdraw(accountId: string, amount: number): Observable<void> {
    return this.http.post<void>(`/api/accounts/${accountId}/withdraw`, { amount });
  }
}
