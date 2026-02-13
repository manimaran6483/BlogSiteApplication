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
  private appUrl = 'http://localhost:8082/api/v1.0/blogsite';
  constructor(private http: HttpClient) { }

  getAllBlogs(): Observable<Blog[]> {
    const url = `${this.appUrl}/user/getallblogs`;
    return this.http.get<Blog[]>(url);
  }
  deleteBlog(id: number): Observable<Blog> {
    const url = `${this.appUrl}/user/delete/${id}`;
    return this.http.delete<Blog>(url);
  }
  addBlog(blog: Blog): Observable<Blog> {
    const url = `${this.appUrl}/user/blog/add`;
    return this.http.post<Blog>(url, blog);
  }
  updateBlog(blog: Blog): Observable<Blog> {
    const url = `${this.appUrl}/user/blogs/update/${blog.id}`;
    return this.http.put<Blog>(url, blog);
  }
  getBlogByAuthorName(name: string): Observable<Blog> {
    const url = `${this.appUrl}/user/getall?authorName=${name}`;
    return this.http.get<Blog>(url);
  }  

  
}
