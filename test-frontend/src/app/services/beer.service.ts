import { Injectable } from '@angular/core';
import { Beer } from '../models/beer';
import { BeerBox } from '../models/beerbox';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BeerService {

  private urlEndPoint: string = 'http://localhost:8080/beers';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getBeers(): Observable<Beer[]> {
    return this.http.get(this.urlEndPoint).pipe(
      map(response => response as Beer[])
    );
  }

  getBeer(id): Observable<any>{
    return this.http.get<any>(`${this.urlEndPoint}/${id}`);
  }

  create(beer: any) : Observable<any> {
    return this.http.post<any>(this.urlEndPoint, beer, {headers: this.httpHeaders})
  }

  update(beer: Beer): Observable<any>{
    return this.http.put<any>(`${this.urlEndPoint}/${beer.id}`, beer, {headers: this.httpHeaders})
  }

  delete(id: number): Observable<Beer>{
    return this.http.delete<Beer>(`${this.urlEndPoint}/${id}`, {headers: this.httpHeaders})
  }

  calculate(beerBox : BeerBox, id : number): Observable<any>{
    return this.http.get<any>(`${this.urlEndPoint}/${id}/boxprice?currency=${beerBox.currency}&quantity=${beerBox.quantity}`);
  }
}
