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

  // Filter state
  categories: string[] = [];
  selectedCategory: string = '';
  startDate: string = '';
  endDate: string = '';
  // Sorting
  sortOption: string = 'createdAt_desc';
  // Logged-in user email from session storage
  currentUserEmail: string | null = null;

  constructor(private blogService: BlogService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.currentUserEmail = sessionStorage.getItem('emailId');
    this.loadBlogs();
  }

  isOwner(blog: Blog): boolean {
    if (!blog) return false;
    const email = this.currentUserEmail || sessionStorage.getItem('emailId');
    return !!email && blog.authorName === email;
  }

  loadBlogs() {
    this.blogService.getAllBlogs().subscribe(
      (data) => {
        this.blogs = data || [];
        // Extract unique categories for filter dropdown
        const set = new Set<string>();
        (this.blogs || []).forEach(b => { if (b.category) set.add(b.category); });
        this.categories = Array.from(set).sort();
        this.applySort();
      },
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
      authorName: sessionStorage.getItem('emailId') || 'Unknown',
      article: this.newBlog.article || '',
      createdAt: '',
      updatedAt: '',
      message: ''
    };
    this.blogService.addBlog(payload).subscribe(
      (created) => {
        this.snackBar.open('Blog added', 'Close', { duration: 2000 });
        this.showAddForm = false;
        this.resetNewBlog();
        this.clearFilterAndReload();
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
        this.clearFilterAndReload();
      },
      (err) => this.snackBar.open('Failed to update', 'Close', { duration: 3000 })
    );
  }

  deleteBlog(id: number) {
    const ok = confirm('Are you sure you want to delete this blog?');
    if (!ok) return;
    this.blogService.deleteBlog(id).subscribe(
      (res: any) => {
        this.snackBar.open(res, 'Close', { duration: 2000 });
        this.clearFilterAndReload();
      },
      (err:any) => {
        this.snackBar.open('Failed to delete blog', 'Close', { duration: 3000 });
        console.log(err);
      }
    );
  }

  // Apply filters based on selectedCategory and date range (YYYY-MM-DD)
  applyFilter() {
    const cat = this.selectedCategory && this.selectedCategory !== 'ALL' ? this.selectedCategory : '';
    const start = this.startDate ? this.formatDate(this.startDate) : '';
    const end = this.endDate ? this.formatDate(this.endDate) : '';

    if (cat && start && end) {
      this.blogService.getBlogsByCategoryInRange(cat, start, end).subscribe(
        (data) => { this.blogs = data || []; this.applySort(); },
        () => this.snackBar.open('Failed to load filtered blogs', 'Close', { duration: 3000 })
      );
      return;
    }

    if (cat) {
      this.blogService.getBlogByCategory(cat).subscribe(
        (data) => { this.blogs = data || []; this.applySort(); },
        () => this.snackBar.open('Failed to load category blogs', 'Close', { duration: 3000 })
      );
      return;
    }

    if (start && end) {
      this.blogService.getBlogsByDateRange(start, end).subscribe(
        (data) => { this.blogs = data || []; this.applySort(); },
        () => this.snackBar.open('Failed to load date-range blogs', 'Close', { duration: 3000 })
      );
      return;
    }

    // no filter -> full list
    this.loadBlogs();
  }

  applySort() {
    if (!this.blogs || !this.blogs.length) return;
    const opt = this.sortOption || 'createdAt_desc';
    const [field, dir] = opt.split('_');
    const asc = dir === 'asc';
    if (field === 'createdAt') {
      this.blogs.sort((a, b) => {
        const ta = a.createdAt ? Date.parse(a.createdAt) : 0;
        const tb = b.createdAt ? Date.parse(b.createdAt) : 0;
        return asc ? ta - tb : tb - ta;
      });
    } else if (field === 'category') {
      this.blogs.sort((a, b) => {
        const ca = (a.category || '').toLowerCase();
        const cb = (b.category || '').toLowerCase();
        return asc ? ca.localeCompare(cb) : cb.localeCompare(ca);
      });
    }
  }

  clearFilter() {
    this.selectedCategory = '';
    this.startDate = '';
    this.endDate = '';
    this.loadBlogs();
  }

  clearFilterAndReload() {
    // reloads but preserves current filter values; if any filter active, reapply, else load all
    if (this.selectedCategory || this.startDate || this.endDate) {
      this.applyFilter();
    } else {
      this.loadBlogs();
    }
  }

  private formatDate(input: string) {
    // input is expected YYYY-MM-DD already from <input type=date>
    // ensure format and return
    if (!input) return '';
    const d = input.split('T')[0];
    return d;
  }

}
