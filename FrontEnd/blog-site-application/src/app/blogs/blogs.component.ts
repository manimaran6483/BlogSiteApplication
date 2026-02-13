import { Component, OnInit } from '@angular/core';
import { Blog } from '../Blog';
import { BlogService } from '../services/blog.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-blogs',
  templateUrl: './blogs.component.html',
  styleUrls: ['./blogs.component.css']
})
export class BlogsComponent implements OnInit {
  blogs: Blog[] = [];
  showAddForm = false;
  editingId: number | null = null;
  newBlog: Partial<Blog> = { blogName: '', category: '', authorName: '', article: '' };

  constructor(private blogService: BlogService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.loadBlogs();
  }

  loadBlogs() {
    this.blogService.getAllBlogs().subscribe(
      (data) => this.blogs = data || [],
      (err) => this.snackBar.open('Failed loading blogs', 'Close', { duration: 3000 })
    );
  }

  toggleAddForm() {
    this.showAddForm = !this.showAddForm;
    if (!this.showAddForm) this.resetNewBlog();
  }

  resetNewBlog() {
    this.newBlog = { blogName: '', category: '', authorName: '', article: '' };
  }

  addBlog() {
    const payload: Blog = {
      id: 0,
      blogName: this.newBlog.blogName || '',
      category: this.newBlog.category || '',
      authorName: this.newBlog.authorName || '',
      article: this.newBlog.article || '',
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
      message: ''
    };
    this.blogService.addBlog(payload).subscribe(
      (created) => {
        this.snackBar.open('Blog added', 'Close', { duration: 2000 });
        this.showAddForm = false;
        this.resetNewBlog();
        this.loadBlogs();
      },
      (err) => this.snackBar.open('Failed to add blog', 'Close', { duration: 3000 })
    );
  }

  startEdit(blog: Blog) {
    this.editingId = blog.id;
  }

  cancelEdit() {
    this.editingId = null;
    this.loadBlogs();
  }

  saveEdit(blog: Blog) {
    this.blogService.updateBlog(blog).subscribe(
      (res) => {
        this.snackBar.open('Blog updated', 'Close', { duration: 2000 });
        this.editingId = null;
        this.loadBlogs();
      },
      (err) => this.snackBar.open('Failed to update', 'Close', { duration: 3000 })
    );
  }

  deleteBlog(id: number) {
    const ok = confirm('Are you sure you want to delete this blog?');
    if (!ok) return;
    this.blogService.deleteBlog(id).subscribe(
      () => {
        this.snackBar.open('Blog deleted', 'Close', { duration: 2000 });
        this.blogs = this.blogs.filter(b => b.id !== id);
      },
      () => this.snackBar.open('Failed to delete', 'Close', { duration: 3000 })
    );
  }

}
