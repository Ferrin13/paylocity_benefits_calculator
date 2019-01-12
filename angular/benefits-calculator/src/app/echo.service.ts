import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root',
})

class EchoService {
  constructor(
    private http: HttpClient
  ) {}

  echoUrl = "localhost:8080/test";

  getEcho(inputString: string): Observable<string> {
    const options = {params: new HttpParams().set('echo', inputString)};
    return this.http.get<string>(this.echoUrl, options)
  }
}
