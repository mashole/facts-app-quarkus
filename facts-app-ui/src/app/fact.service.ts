import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Fact} from './models/Fact';
import {FactAnalitycs} from './models/FactAnalitycs';

@Injectable({
  providedIn: 'root'
})
export class FactService {

  private apiUrl = 'http://localhost:8080'; // Endpoint for your backend

  constructor(private http: HttpClient) { }

  getAndCacheRandomFact(): Observable<Fact> {
    return this.http.post<Fact>(this.apiUrl + '/facts', null);
  }

  getAllFacts(): Observable<Fact[]> {
    return this.http.get<Fact[]>(this.apiUrl + '/facts');
  }

  getFactDetails(factId: string) {
    return this.http.get<Fact>(this.apiUrl + '/facts/' + factId);
  }

  getAnalitycsData() {
    return this.http.get<FactAnalitycs[]>(this.apiUrl + '/admin/statistics');
  }
}
