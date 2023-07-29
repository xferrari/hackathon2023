import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Res } from '../model/res.model';
import { Route } from '../model/route.model';

@Injectable()
export class BackendService {
  constructor(private http: HttpClient) {}

  getRoutes() {
    let url = 'http://localhost:8080/api/initial2';
    return this.http.get<[Route]>(url);
  }
}
