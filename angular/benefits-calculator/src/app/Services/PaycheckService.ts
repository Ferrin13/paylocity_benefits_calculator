import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root',
})

export class PaycheckService {
  constructor(
    private http: HttpClient
  ) {
  }

  baseURL = "http://localhost:8080/paycheck";

  getAmountPerPaycheck(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/amount`);
  }

  setAmountPerPaycheck(amount: number): Observable<number> {
    return this.http.put<number>(`${this.baseURL}/amount`, amount);
  }

  getNumYearlyPaychecks(): Observable<number> {
    return this.http.get<number>(`${this.baseURL}/num-yearly`);
  }

  setNumYearlyPaychecks(yearlyNum: number): Observable<number> {
    return this.http.put<number>(`${this.baseURL}/num-yearly`, yearlyNum);
  }
}
