import { Injectable } from '@angular/core';
import { Employee } from '../Employee';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};
@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private appUrl = 'http://98.70.229.70/reverseproxy-secured/bsc/';
  constructor(private http: HttpClient) { }

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(this.appUrl);
  }
  deleteEmployee(id: number): Observable<Employee> {
    const url = `${this.appUrl}/${id}`;
    return this.http.delete<Employee>(url);
  }
  addEmployee(emp: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.appUrl, emp);
  }
  updateEmployee(emp: Employee): Observable<Employee> {
    const url = `${this.appUrl}/${emp.id}`;
    return this.http.put<Employee>(url, emp);
  }
  getEmployeeById(id: number): Observable<Employee> {
    const url = `${this.appUrl}/${id}`;
    return this.http.get<Employee>(url);
  }

  // create a function to search for employees
  searchEmployees(term: string): Observable<Employee[]> {
    if (!term.trim()) {
      return this.getEmployees();
    }
    return this.http.get<Employee[]>(`${this.appUrl}/search/${term}`);
  }

  // create a function to search employee by name
  searchEmployeeByName(name: string): Observable<Employee[]> {
    if (!name.trim()) {
      return this.getEmployees();
    }
    return this.http.get<Employee[]>(`${this.appUrl}/searchName/${name}`);
  }
  
  

  
}
