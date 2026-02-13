import { Injectable } from '@angular/core';
import { Blog } from '../Blog';
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
export class BlogService {
  private appUrl = 'localhost:8082/api/v1.0/blogsite';
  constructor(private http: HttpClient) { }

  getAllBlogs(): Observable<Blog[]> {
    const url = `${this.appUrl}/user/getall`;
    return this.http.get<Blog[]>(this.appUrl);
  }
  deleteBlog(id: number): Observable<Blog> {
    const url = `${this.appUrl}/${id}`;
    return this.http.delete<Blog>(url);
  }
  addBlog(emp: Blog): Observable<Blog> {
    return this.http.post<Blog>(this.appUrl, emp);
  }
  updateBlog(emp: Blog): Observable<Blog> {
    const url = `${this.appUrl}/${emp.id}`;
    return this.http.put<Blog>(url, emp);
  }
  getBlogByAuthorName(name: string): Observable<Blog> {
    const url = `${this.appUrl}/user/getall?authorName=${name}`;
    return this.http.get<Blog>(url);
  }

  // create a function to search for employees
  searchBlogs(term: string): Observable<Blog[]> {
    if (!term.trim()) {
      return this.getAllBlogs();
    }
    return this.http.get<Blog[]>(`${this.appUrl}/search/${term}`);
  }

  // create a function to search employee by name
  searchBlogByAuthorName(name: string): Observable<Blog[]> {
    if (!name.trim()) {
      return this.getAllBlogs();
    }
    return this.http.get<Blog[]>(`${this.appUrl}/searchName/${name}`);
  }
  
  

  
}
