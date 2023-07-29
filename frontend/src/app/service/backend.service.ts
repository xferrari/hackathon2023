import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Route } from '@angular/router';
import { Res } from '../model/res.model';

@Injectable()
export class BackendService {
  constructor(private http: HttpClient) {}

  getRoutes() {
    let url = 'http://localhost:8080/api/initial';
    return this.http.get<[Route]>(url);
  }
}
