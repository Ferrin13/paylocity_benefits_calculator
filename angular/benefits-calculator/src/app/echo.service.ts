import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Employee} from "./entities/Employee";

@Injectable({
  providedIn: 'root',
})

export class EchoService {
  constructor(
    private http: HttpClient
  ) {}

  echoUrl = "localhost:8080/test";

  getEcho(inputString: string): Observable<Employee> {
    const options = {params: new HttpParams().set('echo', inputString)};
    // return this.http.get<string>(this.echoUrl, options)
    // return this.http.get<string>("https://www.googleapis.com/discovery/v1/apis?fields=")
    return this.http.get<Employee>("http://localhost:8080/test?echo=Boom")
  }
}
