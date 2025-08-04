import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = '/api/accounts';

  constructor(private http: HttpClient) {}

  createAccount(): Observable<any> {
    return this.http.post(this.baseUrl, {}, { observe: 'response' });
  }

  getStatement(id: string): Observable<any> {
    return this.http.get(`/api/accounts/${id}/statement`);
  }
}
