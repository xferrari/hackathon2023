import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Route } from '../model/route.model';

@Injectable()
export class BackendService {
  constructor(private http: HttpClient) {}

  getRoutes() {
    let url = 'http://localhost:8080/api/initial';
    return this.http.get<[Route]>(url);
  }

  optimizeRoutes(routes: Route[]) {
    let url = 'http://localhost:8080/api/mergeRoutes';
    console.log(routes);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post<[Route]>(url, routes, httpOptions);
  }
}
