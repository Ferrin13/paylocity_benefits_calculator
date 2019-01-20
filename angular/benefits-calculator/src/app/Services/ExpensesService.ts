import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Employee} from "../entities/Employee";
import {SalarySummary} from "../entities/SalarySummary";

@Injectable({
  providedIn: 'root',
})

export class ExpensesService {
  constructor(
    private http: HttpClient
  ) {
  }

  baseURL = "http://localhost:8080/expenses";

  getSalarySummary(): Observable<SalarySummary> {
    return this.http.get<SalarySummary>(`${this.baseURL}/salary/summary`);
  }
}
