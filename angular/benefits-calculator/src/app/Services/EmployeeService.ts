import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Employee} from "../entities/Employee";
import {Dependent} from "../entities/Dependent";
import {PayPeriodLength, SalarySummary} from "../entities/SalarySummary";

@Injectable({
  providedIn: 'root',
})

export class EmployeeService {
  constructor(
    private http: HttpClient
  ) {}

  baseURL = "http://localhost:8080/employee";

  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.baseURL)
  }

  getEmployee(employeeId: number): Observable<Employee> {
    // const options = {params: new HttpParams().set('echo', inputString)};
    return this.http.get<Employee>(`${this.baseURL}/${employeeId}`)
  }

  addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.baseURL, employee);
  }

  deleteEmployee(employeeId: number): Observable<Object> {
    return this.http.delete(`${this.baseURL}/${employeeId}`)
  }

  addDependent(employeeId: number, dependent: Dependent): Observable<Dependent> {
    return this.http.post<Dependent>(`${this.baseURL}/${employeeId}/dependent`, dependent);
  }

  getDependent(employeeId: number, dependentId: number): Observable<Dependent> {
    return this.http.get<Dependent>(`${this.baseURL}/${employeeId}/dependent/${dependentId}`);
  }

  deleteDependent(employeeId: number, dependentId: number): Observable<Object> {
    return this.http.delete(`${this.baseURL}/${employeeId}/dependent/${dependentId}`);
  }

  getEmployeeSalarySummary(employeeId: number) : Observable<SalarySummary> {
    return this.http.get<SalarySummary>(`${this.baseURL}/${employeeId}/salary/summary`)
  }
}
